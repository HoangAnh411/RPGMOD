package com.rpgpack.combat;

import com.rpgpack.core.PlayerCapability;
import com.rpgpack.core.PlayerData;
import com.rpgpack.core.DerivedStats;
import com.rpgpack.core.StatCalculator;
import com.rpgpack.RPGPack;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Random;

@Mod.EventBusSubscriber(modid = RPGPack.MODID)
public class CombatHandler {

    private static final Random RANDOM = new Random();

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        if (event.getSource().getEntity() instanceof Player attacker) {
            handlePlayerAttack(event, attacker);
        }

        if (event.getEntity() instanceof Player defender) {
            handlePlayerDefense(event, defender);
        }
    }

    private static void handlePlayerAttack(LivingHurtEvent event, Player attacker) {
        var data = attacker.getCapability(PlayerCapability.PLAYER_DATA).orElse(null);
        if (data == null) return;

        DerivedStats stats = StatCalculator.calculate(data);
        LivingEntity target = event.getEntity();

        // Check if this is physical or magic damage
        boolean isMagic = event.getSource().isMagic();
        float bonus;

        if (isMagic) {
            bonus = stats.magicDamage;
        } else {
            bonus = stats.physicalDamage;
        }

        // Critical hit check
        boolean crit = RANDOM.nextFloat() * 100f < stats.critChance;
        if (crit) {
            bonus *= (stats.critDamage / 100f);
        }

        event.setAmount(event.getAmount() + bonus);

        // Award EXP to player's weapon mastery (future)
        // Award EXP on kill (handled in LivingDeathEvent - future)
    }

    private static void handlePlayerDefense(LivingHurtEvent event, Player defender) {
        var data = defender.getCapability(PlayerCapability.PLAYER_DATA).orElse(null);
        if (data == null) return;

        DerivedStats stats = StatCalculator.calculate(data);

        boolean isMagic = event.getSource().isMagic();
        float defense = isMagic ? stats.magicDefense : stats.physicalDefense;

        // Damage reduction formula: defense / (defense + 100 + level * 10)
        float reduction = defense / (defense + 100f + data.getLevel() * 10f);
        reduction = Math.min(reduction, 0.8f); // cap at 80%

        event.setAmount(event.getAmount() * (1f - reduction));
    }
}
