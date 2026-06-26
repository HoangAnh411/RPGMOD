package com.rpgpack.combat;

import com.rpgpack.RPGPack;
import com.rpgpack.core.PlayerCapability;
import com.rpgpack.core.PlayerData;
import com.rpgpack.loot.ItemStatApplier;
import com.rpgpack.init.ModMessages;
import com.rpgpack.network.SyncPlayerDataS2C;
import com.rpgpack.network.SyncVitalsS2C;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerXpEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.NetworkDirection;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = RPGPack.MODID)
public class PlayerTickHandler {

    private static final Map<UUID, Float> lastSyncedMana = new HashMap<>();
    private static final Map<UUID, Float> lastSyncedHp = new HashMap<>();
    private static final Map<UUID, Integer> lastSyncedExp = new HashMap<>();

    @SubscribeEvent
    public static void onEquipmentChange(LivingEquipmentChangeEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            player.getCapability(PlayerCapability.PLAYER_DATA).ifPresent(data -> {
                data.markStatsDirty();
                ItemStatApplier.apply(player); // recalculate item bonuses immediately
                var stats = data.getCachedStats(player);
                if (data.getCurrentHp() > stats.maxHp) data.setCurrentHp(stats.maxHp);
                if (data.getCurrentMana() > stats.maxMana) data.setCurrentMana(stats.maxMana);
            });
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        if (!(event.player instanceof ServerPlayer player)) return;

        player.getCapability(PlayerCapability.PLAYER_DATA).ifPresent(data -> {
            var stats = data.getCachedStats(player);
            float maxMana = stats.maxMana;
            float maxHp = stats.maxHp;
            UUID uuid = player.getUUID();

            // Mana regen — always active
            if (data.getCurrentMana() < maxMana) {
                data.setCurrentMana(Math.min(data.getCurrentMana() + stats.manaRegen / 20f, maxMana));
            }

            // HP regen — out of combat only
            int lastHurt = player.getLastHurtByMobTimestamp();
            boolean inCombat = lastHurt > 0 && lastHurt + 100 > player.tickCount;
            if (!inCombat && data.getCurrentHp() < maxHp) {
                data.setCurrentHp(Math.min(data.getCurrentHp() + 0.5f / 20f, maxHp));
            }

            // Dirty sync every 0.5s — only when values changed
            if (player.tickCount % 10 == 0) {
                Float lm = lastSyncedMana.get(uuid);
                float lastMana = lm != null ? lm : -1f;
                Float lh = lastSyncedHp.get(uuid);
                float lastHp = lh != null ? lh : -1f;
                Integer le = lastSyncedExp.get(uuid);
                int lastExp = le != null ? le : -1;

                float curMana = data.getCurrentMana();
                float curHp = data.getCurrentHp();
                int curExp = data.getExperience();

                if (curMana != lastMana || curHp != lastHp || curExp != lastExp) {
                    lastSyncedMana.put(uuid, curMana);
                    lastSyncedHp.put(uuid, curHp);
                    lastSyncedExp.put(uuid, curExp);
                    syncVitals(player, curHp, curMana, curExp);
                }
            }
        });
    }

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            player.getCapability(PlayerCapability.PLAYER_DATA).ifPresent(data -> {
                data.setCurrentHp(player.getHealth());
                syncData(player, data);
            });
        }
    }

    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        if (event.getSource().getEntity() instanceof ServerPlayer player) {
            LivingEntity target = event.getEntity();
            player.getCapability(PlayerCapability.PLAYER_DATA).ifPresent(data -> {
                int expGain = MobScaler.getExpReward(target);
                expGain = (int) (expGain * (1f + data.getLuk() * 0.01f));
                data.addExperience(expGain);
                syncData(player, data);
            });
        }
    }

    @SubscribeEvent
    public static void onPickupXp(PlayerXpEvent.PickupXp event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            player.getCapability(PlayerCapability.PLAYER_DATA).ifPresent(data -> {
                int orbValue = event.getOrb().getValue();
                data.addExperience(orbValue);
                syncData(player, data);
            });
        }
    }

    @SubscribeEvent
    public static void onPlayerLogout(PlayerEvent.PlayerLoggedOutEvent event) {
        UUID uuid = event.getEntity().getUUID();
        lastSyncedMana.remove(uuid);
        lastSyncedHp.remove(uuid);
        lastSyncedExp.remove(uuid);
    }

    private static void syncData(ServerPlayer player, PlayerData data) {
        PlayerData snap = new PlayerData();
        snap.copyFrom(data);
        ModMessages.CHANNEL.sendTo(
                new SyncPlayerDataS2C(snap),
                player.connection.connection,
                NetworkDirection.PLAY_TO_CLIENT
        );
    }

    private static void syncVitals(ServerPlayer player, float hp, float mana, int exp) {
        ModMessages.CHANNEL.sendTo(
                new SyncVitalsS2C(hp, mana, exp),
                player.connection.connection,
                NetworkDirection.PLAY_TO_CLIENT
        );
    }
}
