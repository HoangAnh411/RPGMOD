package com.rpgpack.classes;

public enum ClassType {

    NONE(0, 0, 0, 0, 0, 0, 0, 0, "None", "None"),
    WARRIOR(10, 8, 8, 5, 5, 2, 2, 2, "Warrior", "Tank / Frontliner"),
    BERSERKER(12, 7, 9, 4, 4, 1, 1, 2, "Berserker", "Heavy Melee DPS"),
    ASSASSIN(5, 4, 5, 10, 12, 3, 2, 4, "Assassin", "Burst DPS / Crit"),
    MAGE(1, 3, 4, 4, 3, 12, 10, 3, "Mage", "Magic DPS / Caster"),
    CLERIC(3, 5, 6, 4, 3, 8, 12, 5, "Cleric", "Support / Healer");

    private final int baseStr, baseVit, baseEnd, baseAgi, baseDex, baseInt, baseWis, baseLuk;
    private final String displayName;
    private final String role;

    ClassType(int str, int vit, int end, int agi, int dex, int intel, int wis, int luk,
              String displayName, String role) {
        this.baseStr = str;
        this.baseVit = vit;
        this.baseEnd = end;
        this.baseAgi = agi;
        this.baseDex = dex;
        this.baseInt = intel;
        this.baseWis = wis;
        this.baseLuk = luk;
        this.displayName = displayName;
        this.role = role;
    }

    public int getBaseStr() { return baseStr; }
    public int getBaseVit() { return baseVit; }
    public int getBaseEnd() { return baseEnd; }
    public int getBaseAgi() { return baseAgi; }
    public int getBaseDex() { return baseDex; }
    public int getBaseInt() { return baseInt; }
    public int getBaseWis() { return baseWis; }
    public int getBaseLuk() { return baseLuk; }
    public String getDisplayName() { return displayName; }
    public String getRole() { return role; }
}
