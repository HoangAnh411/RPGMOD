package com.rpgpack.core;

import net.minecraft.nbt.CompoundTag;

public class PlayerData {

    private int level = 1;
    private int experience;
    private int statPoints;
    private int skillPoints;

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

    public void copyFrom(PlayerData other) {
        this.level = other.level;
        this.experience = other.experience;
        this.statPoints = other.statPoints;
        this.skillPoints = other.skillPoints;
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
    }

    public CompoundTag toNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putInt("level", level);
        tag.putInt("experience", experience);
        tag.putInt("statPoints", statPoints);
        tag.putInt("skillPoints", skillPoints);
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
        return tag;
    }

    public void fromNBT(CompoundTag tag) {
        this.level = tag.getInt("level");
        this.experience = tag.getInt("experience");
        this.statPoints = tag.getInt("statPoints");
        this.skillPoints = tag.getInt("skillPoints");
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
        this.selectedClass = tag.getString("selectedClass");
        this.currentVision = tag.getString("currentVision");
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
        this.skillPoints += 1;
    }

    // Getters and setters
    public int getLevel() { return level; }
    public void setLevel(int level) { this.level = level; }

    public int getExperience() { return experience; }
    public void setExperience(int experience) { this.experience = experience; }

    public int getStatPoints() { return statPoints; }
    public void setStatPoints(int statPoints) { this.statPoints = statPoints; }

    public int getSkillPoints() { return skillPoints; }
    public void setSkillPoints(int skillPoints) { this.skillPoints = skillPoints; }

    public int getStr() { return str; }
    public void setStr(int str) { this.str = str; }
    public void addStr(int amount) { this.str += amount; }

    public int getVit() { return vit; }
    public void setVit(int vit) { this.vit = vit; }
    public void addVit(int amount) { this.vit += amount; }

    public int getEnd() { return end; }
    public void setEnd(int end) { this.end = end; }
    public void addEnd(int amount) { this.end += amount; }

    public int getAgi() { return agi; }
    public void setAgi(int agi) { this.agi = agi; }
    public void addAgi(int amount) { this.agi += amount; }

    public int getDex() { return dex; }
    public void setDex(int dex) { this.dex = dex; }
    public void addDex(int amount) { this.dex += amount; }

    public int getInt() { return intel; }
    public void setInt(int intel) { this.intel = intel; }
    public void addInt(int amount) { this.intel += amount; }

    public int getWis() { return wis; }
    public void setWis(int wis) { this.wis = wis; }
    public void addWis(int amount) { this.wis += amount; }

    public int getLuk() { return luk; }
    public void setLuk(int luk) { this.luk = luk; }
    public void addLuk(int amount) { this.luk += amount; }

    public float getCurrentHp() { return currentHp; }
    public void setCurrentHp(float currentHp) { this.currentHp = currentHp; }

    public float getCurrentMana() { return currentMana; }
    public void setCurrentMana(float currentMana) { this.currentMana = currentMana; }

    public float getCurrentStamina() { return currentStamina; }
    public void setCurrentStamina(float currentStamina) { this.currentStamina = currentStamina; }

    public String getSelectedClass() { return selectedClass; }
    public void setSelectedClass(String selectedClass) { this.selectedClass = selectedClass; }

    public String getCurrentVision() { return currentVision; }
    public void setCurrentVision(String currentVision) { this.currentVision = currentVision; }
}
