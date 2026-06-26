package com.rpgpack.loot;

import com.rpgpack.RPGPack;
import com.rpgpack.core.PlayerCapability;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * Applies RPG rarity + stats to crafted items based on player level and LUK.
 *
 * Two-pronged approach:
 * 1. {@code ItemCraftedEvent} for immediate application (normal click-craft).
 * 2. Periodic inventory scanner as safety net (shift-click, recipe book).
 */
@Mod.EventBusSubscriber(modid = RPGPack.MODID)
public class CraftLootHandler {

    @SubscribeEvent
    public static void onItemCrafted(PlayerEvent.ItemCraftedEvent event) {
        if (event.getEntity().level().isClientSide()) return;
        ItemStack stack = event.getCrafting();
        if (stack.isEmpty()) return;
        if (!LootHandler.isRpgItem(stack)) return;

        var data = event.getEntity().getCapability(PlayerCapability.PLAYER_DATA).orElse(null);
        float luckBonus = data != null ? data.getLuk() * 0.02f : 0f;
        int playerLevel = data != null ? data.getLevel() : 1;

        ItemRarity rarity = ItemRarity.roll(luckBonus);
        if (luckBonus > 0.3f && rarity.tier < 3 && Math.random() < luckBonus * 0.5f) {
            rarity = ItemRarity.EPIC;
        }

        int itemLevel = Math.max(1, playerLevel / 5 + (int)(Math.random() * 4));
        LootGenerator.applyRandomStats(stack, rarity, itemLevel);
        LootHandler.applyRarityName(stack, rarity);
    }

    /**
     * Safety net: periodically scan every player's inventory for RPG-type items
     * that don't have the {@code rpg_rarity} tag yet (e.g. shift-clicked crafts).
     * Runs once per second to keep latency low.
     */
    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        if (event.getServer().getTickCount() % 20 != 0) return;

        for (ServerPlayer player : event.getServer().getPlayerList().getPlayers()) {
            scanInventory(player);
        }
    }

    private static void scanInventory(ServerPlayer player) {
        var data = player.getCapability(PlayerCapability.PLAYER_DATA).orElse(null);
        float luckBonus = data != null ? data.getLuk() * 0.02f : 0f;
        int playerLevel = data != null ? data.getLevel() : 1;

        var inv = player.getInventory();
        // Scan main inventory (0–35) + armor (36–39) + offhand (40)
        for (int i = 0; i < inv.getContainerSize(); i++) {
            ItemStack stack = inv.getItem(i);
            if (stack.isEmpty()) continue;
            if (!LootHandler.isRpgItem(stack)) continue;
            if (stack.getTag() != null && stack.getTag().contains("rpg_rarity")) continue;

            ItemRarity rarity = ItemRarity.roll(luckBonus);
            if (luckBonus > 0.3f && rarity.tier < 3 && Math.random() < luckBonus * 0.5f) {
                rarity = ItemRarity.EPIC;
            }
            int itemLevel = Math.max(1, playerLevel / 5 + (int)(Math.random() * 4));
            LootGenerator.applyRandomStats(stack, rarity, itemLevel);
            LootHandler.applyRarityName(stack, rarity);
        }
    }
}
