package com.rpgpack.loot;

import com.rpgpack.RPGPack;
import com.rpgpack.combat.MobScaler;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = RPGPack.MODID)
public class LootHandler {

    // Only these item types get RPG rarity
    public static boolean isRpgItem(ItemStack stack) {
        Item item = stack.getItem();
        return item instanceof SwordItem || item instanceof ArmorItem
                || item instanceof BowItem || item instanceof CrossbowItem
                || item instanceof TridentItem || item instanceof ShieldItem
                || item instanceof TieredItem; // tools (pickaxe, axe, shovel, hoe)
    }

    @SubscribeEvent
    public static void onMobDrops(LivingDropsEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity.level().isClientSide) return;

        int mobLevel = MobScaler.getLevel(entity);
        MobScaler.BossTier tier = MobScaler.getTier(entity);

        float luckBonus = 0;
        if (event.getSource().getEntity() instanceof net.minecraft.world.entity.player.Player player) {
            var data = player.getCapability(
                    com.rpgpack.core.PlayerCapability.PLAYER_DATA).orElse(null);
            if (data != null) luckBonus = data.getLuk() * 0.01f;
        }

        // Only process RPG items (weapons/armor/tools) — skip vanilla materials
        for (ItemEntity ie : event.getDrops()) {
            ItemStack stack = ie.getItem();
            if (stack.isEmpty() || !isRpgItem(stack)) continue;

            ItemRarity rarity = ItemRarity.roll(luckBonus);
            if (tier.tier >= 4 && rarity.tier < 2) rarity = ItemRarity.RARE;
            else if (tier.tier >= 3 && rarity.tier < 1) rarity = ItemRarity.UNCOMMON;

            LootGenerator.applyRandomStats(stack, rarity, mobLevel);
            applyRarityName(stack, rarity);
        }

        // Outdoor boss: small chance of bonus loot (no Mythic, no endgame)
        if (tier.tier >= 3 && Math.random() < 0.15) {
            ItemStack bonus = createOutdoorBossDrop(tier, mobLevel);
            event.getDrops().add(new ItemEntity(entity.level(),
                    entity.getX(), entity.getY() + 0.5, entity.getZ(), bonus));
        }
    }

    private static ItemStack createOutdoorBossDrop(MobScaler.BossTier tier, int mobLevel) {
        ItemStack stack;
        ItemRarity rarity;
        double r = Math.random();

        if (tier.tier >= 5) { // Boss
            if (r < 0.01)      { stack = new ItemStack(Items.DIAMOND_SWORD); rarity = ItemRarity.LEGENDARY; }
            else if (r < 0.08) { stack = new ItemStack(Items.DIAMOND_AXE); rarity = ItemRarity.EPIC; }
            else if (r < 0.25) { stack = new ItemStack(Items.IRON_SWORD); rarity = ItemRarity.RARE; }
            else               { stack = new ItemStack(Items.IRON_AXE); rarity = ItemRarity.UNCOMMON; }
        } else if (tier.tier >= 4) { // Elite
            if (r < 0.05) { stack = new ItemStack(Items.IRON_SWORD); rarity = ItemRarity.RARE; }
            else          { stack = new ItemStack(Items.STONE_SWORD); rarity = ItemRarity.UNCOMMON; }
        } else { // Rare
            stack = new ItemStack(Items.STONE_AXE);
            rarity = ItemRarity.UNCOMMON;
        }

        LootGenerator.applyRandomStats(stack, rarity, mobLevel);
        applyRarityName(stack, rarity);
        return stack;
    }

    public static void applyRarityName(ItemStack stack, ItemRarity rarity) {
        String baseName = stack.getHoverName().getString();
        if (!baseName.startsWith("[")) {
            stack.setHoverName(net.minecraft.network.chat.Component.literal(
                    "[" + rarity.name + "] " + baseName)
                    .withStyle(s -> s.withColor(rarity.color)));
        }
    }

    @Mod.EventBusSubscriber(value = Dist.CLIENT, modid = RPGPack.MODID)
    public static class ClientHandler {
        @SubscribeEvent
        public static void onTooltip(ItemTooltipEvent event) {
            LootGenerator.addLore(event.getItemStack(), event.getToolTip());
        }
    }
}
