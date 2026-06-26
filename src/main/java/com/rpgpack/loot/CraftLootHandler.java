package com.rpgpack.loot;

import com.rpgpack.RPGPack;
import com.rpgpack.core.PlayerCapability;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Applies RPG rarity + stats to crafted items based on player level and LUK.
 *
 * Two-pronged approach:
 * 1. {@code ItemCraftedEvent} for immediate application (normal click-craft).
 * 2. Periodic inventory scanner as safety net (shift-click, recipe book edge cases).
 *    Scanner only runs for players who recently crafted — zero cost when idle.
 */
@Mod.EventBusSubscriber(modid = RPGPack.MODID)
public class CraftLootHandler {

    // Only scan players who recently crafted something
    private static final Set<UUID> dirtyPlayers = new HashSet<>();

    @SubscribeEvent
    public static void onItemCrafted(PlayerEvent.ItemCraftedEvent event) {
        if (event.getEntity().level().isClientSide()) return;
        ItemStack stack = event.getCrafting();
        if (stack.isEmpty()) return;
        if (!LootHandler.isRpgItem(stack)) return;

        var data = event.getEntity().getCapability(PlayerCapability.PLAYER_DATA).orElse(null);
        float luckBonus = data != null ? data.getLuk() * 0.02f : 0f;
        int playerLevel = data != null ? data.getLevel() : 1;

        applyRpgToStack(stack, luckBonus, playerLevel);

        // Mark player for safety-net scan (catches shift-click edge cases)
        dirtyPlayers.add(event.getEntity().getUUID());
    }

    /**
     * Safety net: periodically scan inventory of players who recently crafted.
     * Idle cost: 0 players → 0 scans. Runs every 5 seconds.
     */
    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        if (event.getServer().getTickCount() % 100 != 0) return;
        if (dirtyPlayers.isEmpty()) return;

        for (ServerPlayer player : event.getServer().getPlayerList().getPlayers()) {
            if (dirtyPlayers.remove(player.getUUID())) {
                scanInventory(player);
            }
        }
        // In case any UUIDs remain for offline players, clear them
        dirtyPlayers.clear();
    }

    private static void scanInventory(ServerPlayer player) {
        var data = player.getCapability(PlayerCapability.PLAYER_DATA).orElse(null);
        float luckBonus = data != null ? data.getLuk() * 0.02f : 0f;
        int playerLevel = data != null ? data.getLevel() : 1;

        var inv = player.getInventory();
        for (int i = 0; i < inv.getContainerSize(); i++) {
            ItemStack stack = inv.getItem(i);
            if (stack.isEmpty()) continue;
            if (!LootHandler.isRpgItem(stack)) continue;
            if (stack.getTag() != null && stack.getTag().contains("rpg_rarity")) continue;

            applyRpgToStack(stack, luckBonus, playerLevel);
        }
    }

    private static void applyRpgToStack(ItemStack stack, float luckBonus, int playerLevel) {
        ItemRarity rarity = ItemRarity.roll(luckBonus);
        if (luckBonus > 0.3f && rarity.tier < 3 && Math.random() < luckBonus * 0.5f) {
            rarity = ItemRarity.EPIC;
        }
        int itemLevel = Math.max(1, playerLevel / 5 + (int)(Math.random() * 4));
        LootGenerator.applyRandomStats(stack, rarity, itemLevel);
        LootHandler.applyRarityName(stack, rarity);
    }
}
