package com.rpgpack.core;

import net.minecraft.nbt.CompoundTag;
import java.util.HashMap;
import java.util.Map;

public class PlayerData {

    private int level = 1;
    private int experience;
    private int statPoints;

    private int str = 1;
    private int vit = 1;
    private int end = 1;
    private int agi = 1;
    private int dex = 1;
    private int intel = 1;
    private int wis = 1;
    private int luk = 1;

    private float currentHp;
    private float currentMana;
    private float currentStamina;

    private String selectedClass = "NONE";
    private String currentVision = "NONE";

    // Item bonuses — synced to client so CharacterScreen shows correct derived stats
    private float bonusStr, bonusVit, bonusEnd, bonusAgi, bonusDex, bonusInt, bonusWis, bonusLuk;
    private float bonusPhysDmg, bonusMagicDmg, bonusCritChance, bonusCritDmg, bonusAtkSpd, bonusHp, bonusMana;

    // skillId → mastery XP
    private Map<String, Integer> skillMastery = new HashMap<>();
    // skillId → rank (0=E, 1=D, 2=C, 3=B, 4=A, 5=S, 6=SS)
    private Map<String, Integer> skillRank = new HashMap<>();

    // Transient stat cache — recalculated on demand, invalidated on changes
    private transient DerivedStats cachedStats;
    private transient boolean statsDirty = true;

    public static final String[] RANK_NAMES = {"E", "D", "C", "B", "A", "S", "SS"};
    public static final int[] RANK_MASTERY = {0, 100, 300, 700, 1500, 3000, 6000};

    public void copyFrom(PlayerData other) {
        this.level = other.level;
        this.experience = other.experience;
        this.statPoints = other.statPoints;
        this.str = other.str;
        this.vit = other.vit;
        this.end = other.end;
        this.agi = other.agi;
        this.dex = other.dex;
        this.intel = other.intel;
        this.wis = other.wis;
        this.luk = other.luk;
        this.currentHp = other.currentHp;
        this.currentMana = other.currentMana;
        this.currentStamina = other.currentStamina;
        this.selectedClass = other.selectedClass;
        this.currentVision = other.currentVision;
        this.bonusStr = other.bonusStr; this.bonusVit = other.bonusVit;
        this.bonusEnd = other.bonusEnd; this.bonusAgi = other.bonusAgi;
        this.bonusDex = other.bonusDex; this.bonusInt = other.bonusInt;
        this.bonusWis = other.bonusWis; this.bonusLuk = other.bonusLuk;
        this.bonusPhysDmg = other.bonusPhysDmg; this.bonusMagicDmg = other.bonusMagicDmg;
        this.bonusCritChance = other.bonusCritChance; this.bonusCritDmg = other.bonusCritDmg;
        this.bonusAtkSpd = other.bonusAtkSpd; this.bonusHp = other.bonusHp;
        this.bonusMana = other.bonusMana;
        this.skillMastery.clear();
        this.skillMastery.putAll(other.skillMastery);
        this.skillRank.clear();
        this.skillRank.putAll(other.skillRank);
        this.statsDirty = true;
    }

    public CompoundTag toNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putInt("level", level);
        tag.putInt("experience", experience);
        tag.putInt("statPoints", statPoints);
        tag.putInt("str", str);
        tag.putInt("vit", vit);
        tag.putInt("end", end);
        tag.putInt("agi", agi);
        tag.putInt("dex", dex);
        tag.putInt("int", intel);
        tag.putInt("wis", wis);
        tag.putInt("luk", luk);
        tag.putFloat("currentHp", currentHp);
        tag.putFloat("currentMana", currentMana);
        tag.putFloat("currentStamina", currentStamina);
        tag.putString("selectedClass", selectedClass);
        tag.putString("currentVision", currentVision);

        CompoundTag bonus = new CompoundTag();
        bonus.putFloat("str", bonusStr); bonus.putFloat("vit", bonusVit);
        bonus.putFloat("end", bonusEnd); bonus.putFloat("agi", bonusAgi);
        bonus.putFloat("dex", bonusDex); bonus.putFloat("int", bonusInt);
        bonus.putFloat("wis", bonusWis); bonus.putFloat("luk", bonusLuk);
        bonus.putFloat("physDmg", bonusPhysDmg); bonus.putFloat("magicDmg", bonusMagicDmg);
        bonus.putFloat("critChance", bonusCritChance); bonus.putFloat("critDmg", bonusCritDmg);
        bonus.putFloat("atkSpd", bonusAtkSpd); bonus.putFloat("hp", bonusHp);
        bonus.putFloat("mana", bonusMana);
        tag.put("itemBonus", bonus);

        CompoundTag sm = new CompoundTag();
        for (var e : skillMastery.entrySet()) sm.putInt(e.getKey(), e.getValue());
        tag.put("skillMastery", sm);

        CompoundTag sr = new CompoundTag();
        for (var e : skillRank.entrySet()) sr.putInt(e.getKey(), e.getValue());
        tag.put("skillRank", sr);

        return tag;
    }

    public void fromNBT(CompoundTag tag) {
        this.level = tag.getInt("level");
        this.experience = tag.getInt("experience");
        this.statPoints = tag.getInt("statPoints");
        this.str = tag.getInt("str");
        this.vit = tag.getInt("vit");
        this.end = tag.getInt("end");
        this.agi = tag.getInt("agi");
        this.dex = tag.getInt("dex");
        this.intel = tag.getInt("int");
        this.wis = tag.getInt("wis");
        this.luk = tag.getInt("luk");
        this.currentHp = tag.getFloat("currentHp");
        this.currentMana = tag.getFloat("currentMana");
        this.currentStamina = tag.getFloat("currentStamina");
        this.selectedClass = tag.contains("selectedClass") ? tag.getString("selectedClass") : "NONE";
        this.currentVision = tag.contains("currentVision") ? tag.getString("currentVision") : "NONE";

        if (tag.contains("itemBonus")) {
            CompoundTag b = tag.getCompound("itemBonus");
            bonusStr = b.getFloat("str"); bonusVit = b.getFloat("vit");
            bonusEnd = b.getFloat("end"); bonusAgi = b.getFloat("agi");
            bonusDex = b.getFloat("dex"); bonusInt = b.getFloat("int");
            bonusWis = b.getFloat("wis"); bonusLuk = b.getFloat("luk");
            bonusPhysDmg = b.getFloat("physDmg"); bonusMagicDmg = b.getFloat("magicDmg");
            bonusCritChance = b.getFloat("critChance"); bonusCritDmg = b.getFloat("critDmg");
            bonusAtkSpd = b.getFloat("atkSpd"); bonusHp = b.getFloat("hp");
            bonusMana = b.getFloat("mana");
        }

        this.skillMastery.clear();
        if (tag.contains("skillMastery")) {
            CompoundTag sm = tag.getCompound("skillMastery");
            for (String key : sm.getAllKeys()) skillMastery.put(key, sm.getInt(key));
        }

        this.skillRank.clear();
        if (tag.contains("skillRank")) {
            CompoundTag sr = tag.getCompound("skillRank");
            for (String key : sr.getAllKeys()) skillRank.put(key, sr.getInt(key));
        }

        // Migrate old skillLevels → skillRank if present
        if (tag.contains("skillLevels") && this.skillRank.isEmpty()) {
            CompoundTag sl = tag.getCompound("skillLevels");
            for (String key : sl.getAllKeys()) {
                int oldLevel = sl.getInt(key);
                this.skillRank.put(key, Math.min(oldLevel - 1, 6));
                this.skillMastery.put(key, RANK_MASTERY[Math.min(oldLevel - 1, 6)]);
            }
        }

        this.cachedStats = null;
        this.statsDirty = true;
    }

    public int getExperienceToNextLevel() {
        return (int) (100 * Math.pow(1.5, level - 1));
    }

    public void addExperience(int amount) {
        this.experience += amount;
        while (this.experience >= getExperienceToNextLevel() && level < 100) {
            this.experience -= getExperienceToNextLevel();
            levelUp();
        }
    }

    private void levelUp() {
        this.level++;
        this.statPoints += 5;
    }

    // === Getters/Setters ===
    public int getLevel() { return level; }
    public void setLevel(int level) { this.level = level; }

    public int getExperience() { return experience; }
    public void setExperience(int experience) { this.experience = experience; }

    public int getStatPoints() { return statPoints; }
    public void setStatPoints(int statPoints) { this.statPoints = statPoints; }

    public int getStr() { return str; }
    public void setStr(int str) {
        if (this.str != str) { this.str = str; markStatsDirty(); }
    }
    public void addStr(int v) {
        if (v != 0) { this.str += v; markStatsDirty(); }
    }
    public int getVit() { return vit; }
    public void setVit(int vit) {
        if (this.vit != vit) { this.vit = vit; markStatsDirty(); }
    }
    public void addVit(int v) {
        if (v != 0) { this.vit += v; markStatsDirty(); }
    }
    public int getEnd() { return end; }
    public void setEnd(int end) {
        if (this.end != end) { this.end = end; markStatsDirty(); }
    }
    public void addEnd(int v) {
        if (v != 0) { this.end += v; markStatsDirty(); }
    }
    public int getAgi() { return agi; }
    public void setAgi(int agi) {
        if (this.agi != agi) { this.agi = agi; markStatsDirty(); }
    }
    public void addAgi(int v) {
        if (v != 0) { this.agi += v; markStatsDirty(); }
    }
    public int getDex() { return dex; }
    public void setDex(int dex) {
        if (this.dex != dex) { this.dex = dex; markStatsDirty(); }
    }
    public void addDex(int v) {
        if (v != 0) { this.dex += v; markStatsDirty(); }
    }
    public int getInt() { return intel; }
    public void setInt(int v) {
        if (this.intel != v) { this.intel = v; markStatsDirty(); }
    }
    public void addInt(int v) {
        if (v != 0) { this.intel += v; markStatsDirty(); }
    }
    public int getWis() { return wis; }
    public void setWis(int v) {
        if (this.wis != v) { this.wis = v; markStatsDirty(); }
    }
    public void addWis(int v) {
        if (v != 0) { this.wis += v; markStatsDirty(); }
    }
    public int getLuk() { return luk; }
    public void setLuk(int v) {
        if (this.luk != v) { this.luk = v; markStatsDirty(); }
    }
    public void addLuk(int v) {
        if (v != 0) { this.luk += v; markStatsDirty(); }
    }

    // Item bonus getters — synced to client
    public float getBonusStr() { return bonusStr; }
    public float getBonusVit() { return bonusVit; }
    public float getBonusEnd() { return bonusEnd; }
    public float getBonusAgi() { return bonusAgi; }
    public float getBonusDex() { return bonusDex; }
    public float getBonusInt() { return bonusInt; }
    public float getBonusWis() { return bonusWis; }
    public float getBonusLuk() { return bonusLuk; }
    public float getBonusPhysDmg() { return bonusPhysDmg; }
    public float getBonusMagicDmg() { return bonusMagicDmg; }
    public float getBonusCritChance() { return bonusCritChance; }
    public float getBonusCritDmg() { return bonusCritDmg; }
    public float getBonusAtkSpd() { return bonusAtkSpd; }
    public float getBonusHp() { return bonusHp; }
    public float getBonusMana() { return bonusMana; }
    public void setBonus(int idx, float value) {
        switch (idx) {
            case 0 -> bonusStr = value; case 1 -> bonusVit = value;
            case 2 -> bonusEnd = value; case 3 -> bonusAgi = value;
            case 4 -> bonusDex = value; case 5 -> bonusInt = value;
            case 6 -> bonusWis = value; case 7 -> bonusLuk = value;
            case 8 -> bonusPhysDmg = value; case 9 -> bonusMagicDmg = value;
            case 10 -> bonusCritChance = value; case 11 -> bonusCritDmg = value;
            case 12 -> bonusAtkSpd = value; case 13 -> bonusHp = value;
            case 14 -> bonusMana = value;
        }
        markStatsDirty();
    }

    public float getCurrentHp() { return currentHp; }
    public void setCurrentHp(float v) { this.currentHp = v; }
    public float getCurrentMana() { return currentMana; }
    public void setCurrentMana(float v) { this.currentMana = v; }
    public float getCurrentStamina() { return currentStamina; }
    public void setCurrentStamina(float v) { this.currentStamina = v; }

    public String getSelectedClass() { return selectedClass; }
    public void setSelectedClass(String v) {
        if (!this.selectedClass.equals(v)) { this.selectedClass = v; markStatsDirty(); }
    }

    public String getCurrentVision() { return currentVision; }
    public void setCurrentVision(String v) { this.currentVision = v; }

    // Skill Mastery & Rank
    public int getSkillMastery(String skillId) { return skillMastery.getOrDefault(skillId, 0); }
    public void setSkillMastery(String skillId, int v) { skillMastery.put(skillId, v); }
    public void addSkillMastery(String skillId, int v) {
        skillMastery.merge(skillId, v, Integer::sum);
    }

    public int getSkillRank(String skillId) { return skillRank.getOrDefault(skillId, 0); }
    public void setSkillRank(String skillId, int v) { skillRank.put(skillId, v); }

    public int getMasteryForNextRank(String skillId) {
        int currentRank = getSkillRank(skillId);
        if (currentRank >= 6) return -1;
        return RANK_MASTERY[currentRank + 1];
    }

    public boolean canRankUp(String skillId, int playerLevel) {
        int currentRank = getSkillRank(skillId);
        if (currentRank >= 6) return false;
        int requiredMastery = RANK_MASTERY[currentRank + 1];
        int requiredLevel = (currentRank + 1) * 10;
        return getSkillMastery(skillId) >= requiredMastery && level >= requiredLevel;
    }

    // === Stat Cache ===

    public DerivedStats getCachedStats(net.minecraft.world.entity.player.Player player) {
        if (statsDirty || cachedStats == null) {
            // Client needs fresh item bonuses (server ItemStatApplier doesn't run here)
            if (player.level().isClientSide) {
                StatCalculator.applyItemBonuses(this, player);
            }
            cachedStats = StatCalculator.calculate(this, player);
            statsDirty = false;
        }
        return cachedStats;
    }

    public void markStatsDirty() {
        this.statsDirty = true;
    }

    public Map<String, Integer> getSkillMasteryMap() { return skillMastery; }
    public Map<String, Integer> getSkillRankMap() { return skillRank; }
}
