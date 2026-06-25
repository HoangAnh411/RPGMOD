package com.rpgpack.init;

import com.rpgpack.RPGPack;
import com.rpgpack.core.PlayerCapability;
import com.rpgpack.core.PlayerData;
import com.rpgpack.core.StatCalculator;
import com.rpgpack.network.CooldownSyncS2C;
import com.rpgpack.network.OpenClassSelectionS2C;
import com.rpgpack.network.SyncPlayerDataS2C;
import com.rpgpack.skills.SkillCooldownManager;
import com.rpgpack.skills.SkillRegistry;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.NetworkDirection;

@Mod.EventBusSubscriber(modid = RPGPack.MODID)
public class ModEvents {

    public static void register() {}

    @SubscribeEvent
    public static void onPlayerJoin(EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            player.getCapability(PlayerCapability.PLAYER_DATA).ifPresent(data -> {
                if (data.getSelectedClass().equals("NONE")) {
                    ModMessages.CHANNEL.sendTo(
                            new OpenClassSelectionS2C(),
                            player.connection.connection,
                            net.minecraftforge.network.NetworkDirection.PLAY_TO_CLIENT
                    );
                }
                var stats = StatCalculator.calculate(data);
                data.setCurrentHp(stats.maxHp);
                data.setCurrentMana(stats.maxMana);
                data.setCurrentStamina(stats.maxStamina);

                // Sync data to client
                PlayerData snap = new PlayerData();
                snap.copyFrom(data);
                ModMessages.CHANNEL.sendTo(
                        new SyncPlayerDataS2C(snap),
                        player.connection.connection,
                        NetworkDirection.PLAY_TO_CLIENT
                );

                // Sync cooldowns
                for (var skill : SkillRegistry.getAll().values()) {
                    int cd = SkillCooldownManager.getCooldown(player, skill.getSkillId());
                    if (cd > 0) {
                        ModMessages.CHANNEL.sendTo(
                                new CooldownSyncS2C(skill.getSkillId(), cd),
                                player.connection.connection,
                                NetworkDirection.PLAY_TO_CLIENT
                        );
                    }
                }
            });
        }
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        Player original = event.getOriginal();
        original.reviveCaps();
        original.getCapability(PlayerCapability.PLAYER_DATA).ifPresent(oldData -> {
            event.getEntity().getCapability(PlayerCapability.PLAYER_DATA).ifPresent(newData -> {
                newData.copyFrom(oldData);
            });
        });
        original.invalidateCaps();
    }
}
