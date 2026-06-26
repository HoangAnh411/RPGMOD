package com.rpgpack.loot;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;
import java.util.*;

public class LootGenerator {

    private static final Random RNG = new Random();

    private static final String[] STAT_NAMES = {
        "phys_dmg", "magic_dmg", "str", "vit", "end", "agi", "dex", "int", "wis", "luk",
        "crit_chance", "crit_dmg", "atk_speed", "max_hp", "max_mana"
    };
    private static final String[] STAT_LABELS = {
        "Phys DMG", "Magic DMG", "STR", "VIT", "END", "AGI", "DEX", "INT", "WIS", "LUK",
        "Crit Chance%", "Crit DMG%", "Atk Speed%", "Max HP", "Max Mana"
    };

    private static final String[][] AFFIXES = {
        {"of Fury", "of Rage", "of the Berserker"},
        {"of Wisdom", "of the Arcane", "of the Archmage"},
        {"of Might", "of the Titan", "of the Colossus"},
        {"of Vitality", "of the Guardian", "of the Immortal"},
        {"of Stamina", "of the Runner", "of the Marathon"},
        {"of Swiftness", "of the Wind", "of the Storm"},
        {"of Precision", "of the Hawk", "of the Sniper"},
        {"of Intellect", "of the Scholar", "of the Genius"},
        {"of the Wise", "of the Sage", "of the Oracle"},
        {"of Fortune", "of the Lucky", "of the Blessed"},
        {"of Destruction", "of Annihilation", "of the Reaper"},
        {"of Blood", "of the Vampire", "of the Crimson"},
        {"of Haste", "of Speed", "of the Lightning"},
        {"of the Heart", "of the Giant", "of the Behemoth"},
        {"of the Mind", "of the Spirit", "of the Soul"},
    };

    public static void applyRandomStats(ItemStack stack, ItemRarity rarity, int mobLevel) {
        CompoundTag tag = stack.getOrCreateTag();
        tag.putString("rpg_rarity", rarity.name());
        tag.putInt("rpg_level", mobLevel);

        int statCount = rarity.tier + 1;
        Set<Integer> picked = new HashSet<>();

        for (int i = 0; i < statCount && i < STAT_NAMES.length; i++) {
            int idx;
            do { idx = RNG.nextInt(STAT_NAMES.length); } while (picked.contains(idx));
            picked.add(idx);

            float value = rollStatValue(idx, rarity, mobLevel);
            tag.putFloat("rpg_" + STAT_NAMES[idx], value);
        }

        // Affix: pick one random stat that was rolled as the "affix" name suffix
        if (!picked.isEmpty()) {
            int affixIdx = picked.iterator().next();
            int affixTier = Math.min(rarity.tier / 2, 2);
            tag.putString("rpg_affix", AFFIXES[affixIdx][affixTier]);
        }
    }

    private static float rollStatValue(int statIdx, ItemRarity rarity, int mobLevel) {
        float base;
        if (statIdx <= 1) base = 2f + rarity.tier * 3f;       // phys/magic dmg
        else if (statIdx <= 9) base = 1f + rarity.tier;        // stats
        else if (statIdx <= 11) base = 1f + rarity.tier * 1.5f; // crit
        else if (statIdx == 12) base = 0.5f + rarity.tier;      // atk speed
        else base = 5f + rarity.tier * 10f;                      // hp/mana

        base *= (1f + mobLevel * 0.05f);
        float variance = base * 0.3f;
        return Math.round((base + (RNG.nextFloat() * 2 - 1) * variance) * 10f) / 10f;
    }

    public static void addLore(ItemStack stack, List<Component> tooltip) {
        CompoundTag tag = stack.getTag();
        if (tag == null || !tag.contains("rpg_rarity")) return;

        String rarityName = tag.getString("rpg_rarity");
        ItemRarity rarity;
        try { rarity = ItemRarity.valueOf(rarityName); }
        catch (IllegalArgumentException e) { return; }

        int level = tag.getInt("rpg_level");
        String affix = tag.getString("rpg_affix");

        // Rarity line
        String title = rarity.name + " Item";
        if (level > 0) title += " (Lv" + level + ")";
        tooltip.add(Component.literal(title).setStyle(Style.EMPTY.withColor(rarity.color).withBold(true)));

        // Stat lines
        for (int i = 0; i < STAT_NAMES.length; i++) {
            if (tag.contains("rpg_" + STAT_NAMES[i])) {
                float val = tag.getFloat("rpg_" + STAT_NAMES[i]);
                String text = "+" + (val == (int)val ? String.valueOf((int)val) : String.format("%.1f", val))
                        + " " + STAT_LABELS[i];
                tooltip.add(Component.literal("  " + text).setStyle(Style.EMPTY.withColor(0xFF_AAAAAA)));
            }
        }

        // Affix
        if (!affix.isEmpty()) {
            tooltip.add(Component.literal("\"" + affix + "\"").setStyle(Style.EMPTY.withColor(0xFF_FFCC00).withItalic(true)));
        }
    }
}
