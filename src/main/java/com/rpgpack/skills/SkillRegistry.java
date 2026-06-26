package com.rpgpack.skills;

import com.rpgpack.classes.ClassType;

import java.util.*;

public class SkillRegistry {

    private static final Map<String, BaseSkill> SKILLS = new LinkedHashMap<>();
    private static final Map<ClassType, List<String>> CLASS_SKILLS = new EnumMap<>(ClassType.class);

    public static void register(BaseSkill skill) {
        SKILLS.put(skill.getSkillId(), skill);
        CLASS_SKILLS.computeIfAbsent(skill.getClassType(), k -> new ArrayList<>())
                .add(skill.getSkillId());
    }

    public static BaseSkill get(String id) {
        return SKILLS.get(id);
    }

    public static Map<String, BaseSkill> getAll() {
        return Collections.unmodifiableMap(SKILLS);
    }

    public static List<BaseSkill> getSkillsForClass(ClassType type) {
        var ids = CLASS_SKILLS.getOrDefault(type, List.of());
        return ids.stream().map(SKILLS::get).filter(Objects::nonNull).toList();
    }

    public static void init() {
        // Warrior
        register(new ShieldBashSkill());
        register(new BattleCrySkill());
        register(new ShieldWallSkill());
        register(new EarthShatterSkill());

        // Berserker
        register(new GroundSmashSkill());
        register(new BloodRageSkill());
        register(new WhirlwindSkill());
        register(new BerserkModeSkill());

        // Assassin
        register(new ShadowStrikeSkill());
        register(new PoisonBladeSkill());
        register(new SmokeBombSkill());
        register(new ExecutionSkill());

        // Mage
        register(new ManaBurstSkill());
        register(new ArcaneMissileSkill());
        register(new BlinkSkill());
        register(new MeteorSkill());

        // Cleric
        register(new HealSkill());
        register(new HolyBoltSkill());
        register(new PurifySkill());
        register(new DivineBlessingSkill());
    }
}
