package com.rpgpack.core;

public class StatCalculator {

    private StatCalculator() {}

    public static DerivedStats calculate(PlayerData data) {
        int str = data.getStr();
        int vit = data.getVit();
        int end = data.getEnd();
        int agi = data.getAgi();
        int dex = data.getDex();
        int intel = data.getInt();
        int wis = data.getWis();
        int luk = data.getLuk();

        float maxHp = 100f + (vit * 10f);
        float maxMana = 50f + (intel * 5f);
        float maxStamina = 100f + (end * 8f);

        float physicalDamage = (float) (str * 0.5);
        float magicDamage = (float) (intel * 0.8);

        float physicalDefense = vit * 2f;
        float magicDefense = wis * 1.5f;

        float critChance = (float) (dex * 0.25);
        float critDamage = (float) (150.0 + luk * 0.5);

        float attackSpeed = agi * 0.5f;
        float moveSpeed = agi * 0.3f;
        float cooldownReduction = (float) (wis * 0.3);

        float elementalBonus = intel * 0.4f + wis * 0.2f;
        float manaRegen = 1f + wis * 0.1f;
        float staminaRegen = 1f + end * 0.1f;

        return new DerivedStats.Builder()
                .maxHp(maxHp)
                .maxMana(maxMana)
                .maxStamina(maxStamina)
                .physicalDamage(physicalDamage)
                .magicDamage(magicDamage)
                .physicalDefense(physicalDefense)
                .magicDefense(magicDefense)
                .critChance(critChance)
                .critDamage(critDamage)
                .attackSpeed(attackSpeed)
                .moveSpeed(moveSpeed)
                .cooldownReduction(cooldownReduction)
                .elementalBonus(elementalBonus)
                .manaRegen(manaRegen)
                .staminaRegen(staminaRegen)
                .build();
    }
}
