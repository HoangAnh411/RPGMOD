package com.rpgpack.loot;

import com.rpgpack.RPGPack;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * Applies RPG rarity to items in naturally-generated chests.
 *
 * Force-generates loot via {@code unpackLootTable()} on first right-click,
 * then applies RPG stats immediately — player sees RPG items on first open.
 */
@Mod.EventBusSubscriber(modid = RPGPack.MODID)
public class ChestLootHandler {

    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        if (event.getLevel().isClientSide()) return;
        if (!(event.getLevel().getBlockEntity(event.getPos()) instanceof ChestBlockEntity chest)) return;

        // Force-generate loot now (idempotent — no-op if already generated)
        chest.unpackLootTable(event.getEntity());

        // Only roll RPG stats once per chest
        if (chest.getPersistentData().getBoolean("rpg_looted")) return;
        chest.getPersistentData().putBoolean("rpg_looted", true);

        float luckBonus = event.getEntity().getLuck();

        for (int i = 0; i < chest.getContainerSize(); i++) {
            var stack = chest.getItem(i);
            if (stack.isEmpty()) continue;
            if (!LootHandler.isRpgItem(stack)) continue;

            ItemRarity rarity = ItemRarity.roll(luckBonus);
            if (luckBonus > 0.5f && rarity.tier < 2 && Math.random() < luckBonus * 0.15f) {
                rarity = ItemRarity.RARE;
            }
            int mobLevel = Math.max(1, (int)(event.getLevel().getDifficulty().getId() * 3 + Math.random() * 8));
            LootGenerator.applyRandomStats(stack, rarity, mobLevel);
            LootHandler.applyRarityName(stack, rarity);
        }

        chest.setChanged();
    }
}
