package com.rpgpack.combat;

import com.rpgpack.core.PlayerCapability;
import com.rpgpack.core.StatCalculator;
import com.rpgpack.RPGPack;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = RPGPack.MODID)
public class PlayerTickHandler {

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        if (!(event.player instanceof ServerPlayer player)) return;

        player.getCapability(PlayerCapability.PLAYER_DATA).ifPresent(data -> {
            var stats = StatCalculator.calculate(data);
            boolean changed = false;

            // Stamina regen
            float maxStamina = stats.maxStamina;
            if (data.getCurrentStamina() < maxStamina) {
                float regen = stats.staminaRegen / 20f; // per tick (20 ticks/sec)
                data.setCurrentStamina(Math.min(data.getCurrentStamina() + regen, maxStamina));
                changed = true;
            }

            // Mana regen
            float maxMana = stats.maxMana;
            if (data.getCurrentMana() < maxMana) {
                float regen = stats.manaRegen / 20f;
                data.setCurrentMana(Math.min(data.getCurrentMana() + regen, maxMana));
                changed = true;
            }

            // HP regen (slow, only out of combat)
            float maxHp = stats.maxHp;
            if (data.getCurrentHp() < maxHp && player.getLastHurtByMobTimestamp() + 100 < player.tickCount) {
                float regen = 0.5f / 20f; // 0.5 HP/sec out of combat
                data.setCurrentHp(Math.min(data.getCurrentHp() + regen, maxHp));
                changed = true;
            }

            // Sync every 2 seconds if changed
            if (changed && player.tickCount % 40 == 0) {
                var snap = new com.rpgpack.core.PlayerData();
                snap.copyFrom(data);
                com.rpgpack.init.ModMessages.CHANNEL.sendTo(
                        new com.rpgpack.network.SyncPlayerDataS2C(snap),
                        player.connection.connection,
                        net.minecraftforge.network.NetworkDirection.PLAY_TO_CLIENT
                );
            }
        });
    }

    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        if (event.getSource().getEntity() instanceof ServerPlayer player) {
            LivingEntity target = event.getEntity();

            player.getCapability(PlayerCapability.PLAYER_DATA).ifPresent(data -> {
                // EXP based on target max health
                int expGain = (int) (target.getMaxHealth() * 2f);

                // LUK bonus
                expGain = (int) (expGain * (1f + data.getLuk() * 0.01f));

                data.addExperience(expGain);

                // Sync
                var snap = new com.rpgpack.core.PlayerData();
                snap.copyFrom(data);
                com.rpgpack.init.ModMessages.CHANNEL.sendTo(
                        new com.rpgpack.network.SyncPlayerDataS2C(snap),
                        player.connection.connection,
                        net.minecraftforge.network.NetworkDirection.PLAY_TO_CLIENT
                );
            });
        }
    }
}
