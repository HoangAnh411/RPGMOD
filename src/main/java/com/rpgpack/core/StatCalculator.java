package com.rpgpack.core;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class StatCalculator {

    private StatCalculator() {}

    public static DerivedStats calculate(PlayerData data) {
        // Try client player for item bonuses on client side
        Player player = null;
        try {
            var mc = net.minecraft.client.Minecraft.getInstance();
            if (mc.player != null) player = mc.player;
        } catch (Exception ignored) {}
        return calculate(data, player);
    }

    public static DerivedStats calculate(PlayerData data, Player player) {
        int str = data.getStr();
        int vit = data.getVit();
        int end = data.getEnd();
        int agi = data.getAgi();
        int dex = data.getDex();
        int intel = data.getInt();
        int wis = data.getWis();
        int luk = data.getLuk();

        // Item bonuses from PlayerData (synced to client via SyncPlayerDataS2C)
        float bStr = data.getBonusStr();
        float bVit = data.getBonusVit();
        float bEnd = data.getBonusEnd();
        float bAgi = data.getBonusAgi();
        float bDex = data.getBonusDex();
        float bInt = data.getBonusInt();
        float bWis = data.getBonusWis();
        float bLuk = data.getBonusLuk();
        float bPhys = data.getBonusPhysDmg();
        float bMagic = data.getBonusMagicDmg();
        float bCritC = data.getBonusCritChance();
        float bCritD = data.getBonusCritDmg();
        float bAtkSpd = data.getBonusAtkSpd();
        float bHp = data.getBonusHp();
        float bMana = data.getBonusMana();

        float maxHp = 100f + (vit + bVit) * 8f + (end + bEnd) * 2f + bHp;
        float maxMana = 50f + (intel + bInt) * 4f + (end + bEnd) * 1f + bMana;

        float physicalDamage = (str + bStr) * 0.5f + bPhys;
        float magicDamage = (intel + bInt) * 0.8f + bMagic;

        float physicalDefense = (vit + bVit) * 1.5f + (end + bEnd) * 0.5f;
        float magicDefense = (wis + bWis) * 1.2f + (end + bEnd) * 0.3f;

        float critChance = (dex + bDex) * 0.25f + bCritC;
        float critDamage = 150f + (dex + bDex) * 0.5f + bCritD;

        float attackSpeed = (agi + bAgi) * 0.5f + bAtkSpd;
        float moveSpeed = (agi + bAgi) * 0.3f;
        float cooldownReduction = (wis + bWis) * 0.3f;

        float elementalBonus = (intel + bInt) * 0.4f + (wis + bWis) * 0.2f;
        float manaRegen = 3f + (wis + bWis) * 0.15f;

        return new DerivedStats.Builder()
                .maxHp(maxHp).maxMana(maxMana)
                .physicalDamage(physicalDamage).magicDamage(magicDamage)
                .physicalDefense(physicalDefense).magicDefense(magicDefense)
                .critChance(critChance).critDamage(critDamage)
                .attackSpeed(attackSpeed).moveSpeed(moveSpeed)
                .cooldownReduction(cooldownReduction).elementalBonus(elementalBonus)
                .manaRegen(manaRegen)
                .build();
    }

    /** Read current equipment and write item bonuses directly to PlayerData. */
    public static void applyItemBonuses(PlayerData data, Player player) {
        float[] totals = new float[15];
        for (var slot : player.getInventory().armor) sumItem(totals, slot);
        sumItem(totals, player.getMainHandItem());
        sumItem(totals, player.getOffhandItem());

        data.setBonus(0, totals[0]); data.setBonus(1, totals[1]);
        data.setBonus(2, totals[2]); data.setBonus(3, totals[3]);
        data.setBonus(4, totals[4]); data.setBonus(5, totals[5]);
        data.setBonus(6, totals[6]); data.setBonus(7, totals[7]);
        data.setBonus(8, totals[8]); data.setBonus(9, totals[9]);
        data.setBonus(10, totals[10]); data.setBonus(11, totals[11]);
        data.setBonus(12, totals[12]); data.setBonus(13, totals[13]);
        data.setBonus(14, totals[14]);
    }

    private static void sumItem(float[] totals, ItemStack stack) {
        CompoundTag tag = stack.getTag();
        if (tag == null || !tag.contains("rpg_rarity")) return;
        totals[0] += tag.getFloat("rpg_str");
        totals[1] += tag.getFloat("rpg_vit");
        totals[2] += tag.getFloat("rpg_end");
        totals[3] += tag.getFloat("rpg_agi");
        totals[4] += tag.getFloat("rpg_dex");
        totals[5] += tag.getFloat("rpg_int");
        totals[6] += tag.getFloat("rpg_wis");
        totals[7] += tag.getFloat("rpg_luk");
        totals[8] += tag.getFloat("rpg_phys_dmg");
        totals[9] += tag.getFloat("rpg_magic_dmg");
        totals[10] += tag.getFloat("rpg_crit_chance");
        totals[11] += tag.getFloat("rpg_crit_dmg");
        totals[12] += tag.getFloat("rpg_atk_speed");
        totals[13] += tag.getFloat("rpg_max_hp");
        totals[14] += tag.getFloat("rpg_max_mana");
    }
}
