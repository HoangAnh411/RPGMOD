package com.rpgpack.core;

public class DerivedStats {

    public final float maxHp;
    public final float maxMana;
    public final float maxStamina;
    public final float physicalDamage;
    public final float magicDamage;
    public final float physicalDefense;
    public final float magicDefense;
    public final float critChance;
    public final float critDamage;
    public final float attackSpeed;
    public final float moveSpeed;
    public final float cooldownReduction;
    public final float elementalBonus;
    public final float manaRegen;
    public final float staminaRegen;

    private DerivedStats(Builder builder) {
        this.maxHp = builder.maxHp;
        this.maxMana = builder.maxMana;
        this.maxStamina = builder.maxStamina;
        this.physicalDamage = builder.physicalDamage;
        this.magicDamage = builder.magicDamage;
        this.physicalDefense = builder.physicalDefense;
        this.magicDefense = builder.magicDefense;
        this.critChance = builder.critChance;
        this.critDamage = builder.critDamage;
        this.attackSpeed = builder.attackSpeed;
        this.moveSpeed = builder.moveSpeed;
        this.cooldownReduction = builder.cooldownReduction;
        this.elementalBonus = builder.elementalBonus;
        this.manaRegen = builder.manaRegen;
        this.staminaRegen = builder.staminaRegen;
    }

    public static class Builder {
        float maxHp, maxMana, maxStamina;
        float physicalDamage, magicDamage;
        float physicalDefense, magicDefense;
        float critChance, critDamage;
        float attackSpeed, moveSpeed;
        float cooldownReduction, elementalBonus;
        float manaRegen, staminaRegen;

        public Builder maxHp(float v) { this.maxHp = v; return this; }
        public Builder maxMana(float v) { this.maxMana = v; return this; }
        public Builder maxStamina(float v) { this.maxStamina = v; return this; }
        public Builder physicalDamage(float v) { this.physicalDamage = v; return this; }
        public Builder magicDamage(float v) { this.magicDamage = v; return this; }
        public Builder physicalDefense(float v) { this.physicalDefense = v; return this; }
        public Builder magicDefense(float v) { this.magicDefense = v; return this; }
        public Builder critChance(float v) { this.critChance = v; return this; }
        public Builder critDamage(float v) { this.critDamage = v; return this; }
        public Builder attackSpeed(float v) { this.attackSpeed = v; return this; }
        public Builder moveSpeed(float v) { this.moveSpeed = v; return this; }
        public Builder cooldownReduction(float v) { this.cooldownReduction = v; return this; }
        public Builder elementalBonus(float v) { this.elementalBonus = v; return this; }
        public Builder manaRegen(float v) { this.manaRegen = v; return this; }
        public Builder staminaRegen(float v) { this.staminaRegen = v; return this; }

        public DerivedStats build() {
            return new DerivedStats(this);
        }
    }
}
