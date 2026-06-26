package com.rpgpack.loot;

import net.minecraft.world.item.ItemStack;

public enum ItemRarity {
    COMMON(0, "Common", 0xFF_FFFFFF, 0.60f),
    UNCOMMON(1, "Uncommon", 0xFF_55FF55, 0.25f),
    RARE(2, "Rare", 0xFF_5555FF, 0.10f),
    EPIC(3, "Epic", 0xFF_AA44FF, 0.04f),
    LEGENDARY(4, "Legendary", 0xFF_FF8800, 0.01f),
    MYTHIC(5, "Mythic", 0xFF_FF4444, 0.0f); // manual only

    public final int tier;
    public final String name;
    public final int color;
    public final float dropChance;

    ItemRarity(int tier, String name, int color, float dropChance) {
        this.tier = tier;
        this.name = name;
        this.color = color;
        this.dropChance = dropChance;
    }

    public static ItemRarity roll(float luckBonus) {
        float roll = (float) Math.random();
        float cumulative = 0;
        for (ItemRarity r : values()) {
            cumulative += r.dropChance * (1f + luckBonus * 0.5f);
            if (roll <= cumulative) return r;
        }
        return COMMON;
    }

    public static ItemRarity fromTier(int tier) {
        for (ItemRarity r : values()) if (r.tier == tier) return r;
        return COMMON;
    }
}
