package com.rpgpack.combat;

import com.rpgpack.core.PlayerCapability;
import com.rpgpack.core.DerivedStats;
import com.rpgpack.RPGPack;
import com.rpgpack.init.ModMessages;
import com.rpgpack.network.FloatingDamageS2C;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = RPGPack.MODID)
public class CombatHandler {

    private static final Random RANDOM = new Random();
    // Track pending attacks for MISS detection (UUID -> server tick when attack started)
    private static final Map<UUID, Integer> pendingAttacks = new HashMap<>();
    private static int serverTickCounter = 0;

    @SubscribeEvent
    public static void onAttackEntity(AttackEntityEvent event) {
        if (event.getTarget() instanceof LivingEntity target && !event.getEntity().level().isClientSide) {
            pendingAttacks.put(target.getUUID(), serverTickCounter);
        }
    }

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        // Remove from pending — attack landed
        pendingAttacks.remove(event.getEntity().getUUID());

        boolean crit = false;
        if (event.getSource().getEntity() instanceof Player attacker) {
            crit = handlePlayerAttack(event, attacker);
        }
        if (event.getEntity() instanceof Player defender) {
            handlePlayerDefense(event, defender);
        }

        // Broadcast floating damage
        LivingEntity target = event.getEntity();
        if (!target.level().isClientSide && event.getAmount() > 0) {
            byte type = crit ? FloatingDamageS2C.TYPE_CRIT : FloatingDamageS2C.TYPE_DAMAGE;
            Vec3 pos = target.position().add(0, target.getBbHeight() * 0.7, 0);
            ModMessages.CHANNEL.send(
                    PacketDistributor.TRACKING_ENTITY.with(() -> target),
                    new FloatingDamageS2C(target.getId(), event.getAmount(), type, pos));
        }
    }

    private static boolean handlePlayerAttack(LivingHurtEvent event, Player attacker) {
        var data = attacker.getCapability(PlayerCapability.PLAYER_DATA).orElse(null);
        if (data == null) return false;

        DerivedStats stats = data.getCachedStats(attacker);
        LivingEntity target = event.getEntity();

        String msgId = event.getSource().getMsgId();
        boolean isMagic = "magic".equals(msgId) || "indirectMagic".equals(msgId);

        // Class modifier
        String cls = data.getSelectedClass();
        float classMult = 1f;
        if ("WARRIOR".equals(cls) || "BERSERKER".equals(cls)) classMult = isMagic ? 1.0f : 1.2f;
        else if ("MAGE".equals(cls)) classMult = isMagic ? 1.25f : 0.9f;
        else if ("ASSASSIN".equals(cls)) classMult = 1.15f;
        else if ("CLERIC".equals(cls)) classMult = isMagic ? 1.1f : 0.85f;

        // Stat bonus
        float statBonus = isMagic ? stats.magicDamage : stats.physicalDamage;

        // Crit
        boolean crit = RANDOM.nextFloat() * 100f < stats.critChance;
        float critMult = crit ? (stats.critDamage / 100f) : 1f;

        // Element bonus placeholder (until vision system)
        float elementMult = 1f;

        // Mob defense reduction
        float mobDefReduction = MobScaler.getDefenseReduction(target);
        float defenseMult = 1f - mobDefReduction;

        // Final calculation
        float rawDmg = event.getAmount();
        float finalDmg = (rawDmg + statBonus) * classMult * critMult * elementMult * defenseMult;

        event.setAmount(Math.max(1, finalDmg));
        return crit;
    }

    private static void handlePlayerDefense(LivingHurtEvent event, Player defender) {
        var data = defender.getCapability(PlayerCapability.PLAYER_DATA).orElse(null);
        if (data == null) return;

        DerivedStats stats = data.getCachedStats(defender);

        String msgId = event.getSource().getMsgId();
        boolean isMagic = "magic".equals(msgId) || "indirectMagic".equals(msgId);
        float defense = isMagic ? stats.magicDefense : stats.physicalDefense;

        // Mob damage bonus (from level scaling)
        float mobBonus = 0;
        if (event.getSource().getEntity() instanceof LivingEntity mob) {
            mobBonus = MobScaler.getDamageBonus(mob);
        }

        float reduction = defense / (defense + 100f + data.getLevel() * 10f);
        reduction = Math.min(reduction, 0.8f);

        float original = event.getAmount();
        float finalDmg = (original + mobBonus) * (1f - reduction);

        // DODGE: if defense blocked >95% of damage
        if (original > 1f && finalDmg < original * 0.05f && defender instanceof ServerPlayer sp) {
            ModMessages.CHANNEL.send(
                    PacketDistributor.TRACKING_ENTITY.with(() -> sp),
                    new FloatingDamageS2C(sp.getId(), 0, FloatingDamageS2C.TYPE_DODGE,
                            sp.position().add(0, sp.getBbHeight() * 0.7, 0)));
        }

        event.setAmount(finalDmg);
    }

    // MISS detection: attacks that didn't land within 1 tick
    @SubscribeEvent
    public static void onServerTick(net.minecraftforge.event.TickEvent.ServerTickEvent event) {
        if (event.phase != net.minecraftforge.event.TickEvent.Phase.END) return;
        serverTickCounter++;

        var iter = pendingAttacks.entrySet().iterator();
        while (iter.hasNext()) {
            var e = iter.next();
            if (serverTickCounter - e.getValue() > 1) {
                iter.remove();
                UUID id = e.getKey();
                var entity = event.getServer().getLevel(net.minecraft.world.level.Level.OVERWORLD).getEntity(id);
                if (entity == null) {
                    entity = event.getServer().getLevel(net.minecraft.world.level.Level.NETHER).getEntity(id);
                }
                if (entity == null) {
                    entity = event.getServer().getLevel(net.minecraft.world.level.Level.END).getEntity(id);
                }
                if (entity instanceof LivingEntity le) {
                    ModMessages.CHANNEL.send(
                            PacketDistributor.TRACKING_ENTITY.with(() -> le),
                            new FloatingDamageS2C(le.getId(), 0, FloatingDamageS2C.TYPE_MISS,
                                    le.position().add(0, le.getBbHeight() * 0.7, 0)));
                }
            }
        }
    }
}
