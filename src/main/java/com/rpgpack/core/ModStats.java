package com.rpgpack.core;

public enum ModStats {

    STR("Strength"),
    VIT("Vitality"),
    END("Endurance"),
    AGI("Agility"),
    DEX("Dexterity"),
    INT("Intelligence"),
    WIS("Wisdom"),
    LUK("Luck");

    private final String displayName;

    ModStats(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
