package com.rpgpack.skills;

import com.rpgpack.classes.ClassType;
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
    protected final ClassType classType;

    public BaseSkill(String skillId, String skillName, int manaCost, int staminaCost,
                     int cooldownTicks, String damageType, int unlockLevel, ClassType classType) {
        this.skillId = skillId;
        this.skillName = skillName;
        this.manaCost = manaCost;
        this.staminaCost = staminaCost;
        this.cooldownTicks = cooldownTicks;
        this.damageType = damageType;
        this.unlockLevel = unlockLevel;
        this.classType = classType;
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
    public ClassType getClassType() { return classType; }

    public String getResourceCost() {
        if (manaCost > 0) return manaCost + " MP";
        if (staminaCost > 0) return staminaCost + " SP";
        return "";
    }

    // === RANK SYSTEM (E→D→C→B→A→S→SS) ===
    public static final String[] RANK_NAMES = {"E", "D", "C", "B", "A", "S", "SS"};
    public static final float[] RANK_DAMAGE = {1.0f, 1.2f, 1.4f, 1.7f, 2.0f, 2.5f, 3.5f};
    public static final float[] RANK_COOLDOWN = {1.0f, 0.92f, 0.85f, 0.78f, 0.72f, 0.67f, 0.60f};

    public float getDamageMultiplier(int rank) {
        int r = Math.max(0, Math.min(rank, 6));
        return RANK_DAMAGE[r];
    }

    public int getScaledCooldown(int rank) {
        int r = Math.max(0, Math.min(rank, 6));
        return (int) (cooldownTicks * RANK_COOLDOWN[r]);
    }

    public static int getMasteryGain(net.minecraft.world.entity.LivingEntity target) {
        float mh = target.getMaxHealth();
        if (mh >= 100) return 5;
        if (mh >= 30) return 3;
        return 1;
    }
}
