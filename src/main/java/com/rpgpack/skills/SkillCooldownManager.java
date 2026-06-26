package com.rpgpack.skills;

import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Mod.EventBusSubscriber(modid = "rpgpack")
public class SkillCooldownManager {

    private static final Map<UUID, Map<String, Integer>> cooldowns = new ConcurrentHashMap<>();
    private static int serverTick = 0;

    public static void setCooldown(ServerPlayer player, String skillId, int ticks) {
        cooldowns.computeIfAbsent(player.getUUID(), k -> new ConcurrentHashMap<>())
                .put(skillId, serverTick + ticks);
    }

    public static int getCooldown(ServerPlayer player, String skillId) {
        var map = cooldowns.get(player.getUUID());
        if (map == null) return 0;
        int endTick = map.getOrDefault(skillId, 0);
        return Math.max(0, endTick - serverTick);
    }

    public static boolean isOnCooldown(ServerPlayer player, String skillId) {
        return getCooldown(player, skillId) > 0;
    }

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        serverTick++;

        // Periodic cleanup: remove expired cooldowns and empty maps
        if (serverTick % 100 == 0) {
            for (var entry : cooldowns.entrySet()) {
                var map = entry.getValue();
                map.values().removeIf(v -> v <= serverTick);
                if (map.isEmpty()) cooldowns.remove(entry.getKey());
            }
        }
    }
}
