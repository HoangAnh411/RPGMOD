package com.rpgpack.skills;

import java.util.HashMap;
import java.util.Map;

public class SkillRegistry {

    private static final Map<String, BaseSkill> SKILLS = new HashMap<>();

    public static void register(BaseSkill skill) {
        SKILLS.put(skill.getSkillId(), skill);
    }

    public static BaseSkill get(String id) {
        return SKILLS.get(id);
    }

    public static Map<String, BaseSkill> getAll() {
        return Map.copyOf(SKILLS);
    }

    public static void init() {
        register(new GroundSmashSkill());
        register(new ManaBurstSkill());
        register(new ShadowStrikeSkill());
        register(new ShieldBreakSkill());
    }
}
