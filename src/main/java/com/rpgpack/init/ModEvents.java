package com.rpgpack.init;

import com.rpgpack.RPGPack;
import com.rpgpack.command.ClassCommand;
import com.rpgpack.command.DebugCommand;
import com.rpgpack.combat.MobScaler;
import com.rpgpack.core.PlayerCapability;
import com.rpgpack.core.PlayerData;
import com.rpgpack.network.CooldownSyncS2C;
import com.rpgpack.network.OpenClassSelectionS2C;
import com.rpgpack.network.SyncPlayerDataS2C;
import com.rpgpack.skills.SkillCooldownManager;
import com.rpgpack.skills.SkillRegistry;

import java.util.Map;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.NetworkDirection;

@Mod.EventBusSubscriber(modid = RPGPack.MODID)
public class ModEvents {

    public static void register() {}

    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        ClassCommand.register(event.getDispatcher());
        DebugCommand.register(event.getDispatcher());
    }

    @SubscribeEvent
    public static void onPlayerJoin(EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            player.getCapability(PlayerCapability.PLAYER_DATA).ifPresent(data -> {
                String playerName = player.getName().getString();
                boolean isNew = "NONE".equals(data.getSelectedClass());
                RPGPack.LOGGER.info("[CLASS] onPlayerJoin: {} class={} isNew={}", playerName, data.getSelectedClass(), isNew);

                var stats = data.getCachedStats(player);

                // Only full heal on FIRST join (new player), not on reconnect
                if (isNew) {
                    data.setCurrentHp(stats.maxHp);
                    data.setCurrentMana(stats.maxMana);
                } else {
                    if (data.getCurrentMana() <= 1f) {
                        data.setCurrentMana(stats.maxMana * 0.2f);
                    }
                }

                // Always sync vanilla max health to RPG maxHp
                player.getAttribute(Attributes.MAX_HEALTH).setBaseValue(stats.maxHp);
                if (player.getHealth() > stats.maxHp) {
                    player.setHealth(stats.maxHp);
                }

                // Only open class selection if player has never chosen a class
                if (isNew) {
                    RPGPack.LOGGER.info("[CLASS] Opening selection screen for {}", playerName);
                    ModMessages.CHANNEL.sendTo(
                            new OpenClassSelectionS2C(),
                            player.connection.connection,
                            NetworkDirection.PLAY_TO_CLIENT
                    );
                }

                // Sync data to client
                PlayerData snap = new PlayerData();
                snap.copyFrom(data);
                ModMessages.CHANNEL.sendTo(
                        new SyncPlayerDataS2C(snap),
                        player.connection.connection,
                        NetworkDirection.PLAY_TO_CLIENT
                );

                // Sync cooldowns (single batch packet)
                Map<String, Integer> batchCd = new java.util.HashMap<>();
                for (var skill : SkillRegistry.getAll().values()) {
                    int cd = SkillCooldownManager.getCooldown(player, skill.getSkillId());
                    if (cd > 0) batchCd.put(skill.getSkillId(), cd);
                }
                if (!batchCd.isEmpty()) {
                    ModMessages.CHANNEL.sendTo(
                            new CooldownSyncS2C(batchCd),
                            player.connection.connection,
                            NetworkDirection.PLAY_TO_CLIENT
                    );
                }
            });
        }

        // Scale hostile mobs on spawn
        if (event.getEntity() instanceof Mob mob && !(event.getEntity() instanceof Player)) {
            MobScaler.applyScaling(mob);
        }
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        if (!event.isWasDeath()) return;

        Player original = event.getOriginal();
        original.reviveCaps();

        original.getCapability(PlayerCapability.PLAYER_DATA).ifPresent(oldData -> {
            event.getEntity().getCapability(PlayerCapability.PLAYER_DATA).ifPresent(newData -> {
                newData.copyFrom(oldData);
                // Clamp mana after death so player doesn't respawn at 0
                var stats = newData.getCachedStats(event.getEntity());
                if (newData.getCurrentMana() < stats.maxMana * 0.3f) {
                    newData.setCurrentMana(stats.maxMana * 0.3f);
                }
                RPGPack.LOGGER.info("[CLASS] onPlayerClone (death): class={} copied to new entity",
                        oldData.getSelectedClass());
            });
        });

        original.invalidateCaps();
    }
}
