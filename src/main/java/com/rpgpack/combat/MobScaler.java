package com.rpgpack.combat;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Enemy;

public class MobScaler {

    public enum BossTier {
        NONE   (0, "",       0xFF_888888, 1.0f,  0.0f),
        COMMON (1, "",       0xFF_888888, 1.2f,  0.85f),
        UNCOMMON(2,"Uncommon",0xFF_55FF55, 2.0f,  0.10f),
        RARE    (3,"Rare",   0xFF_5555FF, 4.0f,  0.04f),
        ELITE   (4,"Elite",  0xFF_FF8800, 7.0f,  0.009f),
        BOSS    (5,"Boss",   0xFF_FF4444, 12.0f, 0.001f);

        public static final BossTier[] VALUES = values();

        public final int tier;
        public final String name;
        public final int color;
        public final float hpMult;
        public final float baseChance;

        BossTier(int t, String n, int c, float h, float ch) {
            tier = t; name = n; color = c; hpMult = h; baseChance = ch;
        }

        public static BossTier roll(double distFromSpawn, boolean isHostile) {
            if (!isHostile) return NONE;
            float distBonus = (float) Math.min(distFromSpawn / 3000.0, 0.5);
            float roll = (float) Math.random();
            float cumulative = 0;
            for (int i = VALUES.length - 1; i >= 1; i--) {
                BossTier t = VALUES[i];
                float chance = t.baseChance * (1f + distBonus * 2f);
                if (roll >= (1f - chance)) return t;
            }
            return COMMON;
        }
    }

    public static void applyScaling(LivingEntity entity) {
        if (entity.level().isClientSide) return;

        double dist = Math.sqrt(entity.getX() * entity.getX() + entity.getZ() * entity.getZ());
        boolean isHostile = entity instanceof Enemy;
        BossTier tier = BossTier.roll(dist, isHostile);

        int level = Math.max(1, (int)(dist / 50) + entity.getRandom().nextInt(4));
        float baseHp = (float) entity.getAttributeValue(Attributes.MAX_HEALTH);
        float scaledHp = baseHp * (1f + level * 0.15f) * tier.hpMult;

        entity.getAttribute(Attributes.MAX_HEALTH).setBaseValue(scaledHp);
        entity.setHealth(scaledHp);
        entity.getPersistentData().putInt("rpg_level", level);
        entity.getPersistentData().putInt("rpg_tier", tier.tier);

        // NO glow, NO custom name — prevents wall-hack visibility
    }

    public static int getLevel(LivingEntity entity) {
        int lv = entity.getPersistentData().getInt("rpg_level");
        return lv > 0 ? lv : 1;
    }

    public static BossTier getTier(LivingEntity entity) {
        int t = entity.getPersistentData().getInt("rpg_tier");
        for (BossTier bt : BossTier.VALUES) if (bt.tier == t) return bt;
        return BossTier.NONE;
    }

    public static float getDamageBonus(LivingEntity entity) {
        int level = getLevel(entity);
        return level * 0.5f * getTier(entity).hpMult * 0.25f;
    }

    public static float getDefenseReduction(LivingEntity entity) {
        int level = getLevel(entity);
        if (level <= 1) return 0;
        float def = level * 0.6f;
        return def / (def + 120f);
    }

    public static int getExpReward(LivingEntity entity) {
        int level = getLevel(entity);
        float baseExp = entity.getMaxHealth() * 2f;
        return (int)(baseExp * (1f + level * 0.2f) * (1f + getTier(entity).tier * 0.3f));
    }
}
