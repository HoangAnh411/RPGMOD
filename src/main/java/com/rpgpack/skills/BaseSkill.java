package com.rpgpack.skills;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public abstract class BaseSkill {

    protected final String skillId;
    protected final String skillName;
    protected final int manaCost;
    protected final int staminaCost;
    protected final int cooldownTicks;
    protected final String damageType;
    protected final int unlockLevel;

    public BaseSkill(String skillId, String skillName, int manaCost, int staminaCost,
                     int cooldownTicks, String damageType, int unlockLevel) {
        this.skillId = skillId;
        this.skillName = skillName;
        this.manaCost = manaCost;
        this.staminaCost = staminaCost;
        this.cooldownTicks = cooldownTicks;
        this.damageType = damageType;
        this.unlockLevel = unlockLevel;
    }

    public abstract void execute(Player player, Level level);

    public abstract float calculateDamage(Player player);

    public String getSkillId() { return skillId; }
    public String getSkillName() { return skillName; }
    public int getManaCost() { return manaCost; }
    public int getStaminaCost() { return staminaCost; }
    public int getCooldownTicks() { return cooldownTicks; }
    public String getDamageType() { return damageType; }
    public int getUnlockLevel() { return unlockLevel; }
}
