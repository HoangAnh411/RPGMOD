package com.rpgpack.skills;

import com.rpgpack.classes.ClassType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

public class SmokeBombSkill extends BaseSkill {

    public SmokeBombSkill() {
        super("smoke_bomb", "Smoke Bomb", 35, 0, 400, "UTILITY", 3, ClassType.ASSASSIN);
    }

    @Override public float calculateDamage(Player player) { return 0; }

    @Override
    public void execute(Player player, Level level) {
        var aabb = player.getBoundingBox().inflate(5.0);
        level.getEntitiesOfClass(LivingEntity.class, aabb, e -> !e.is(player) && e.isAlive())
                .forEach(target -> {
                    target.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 80, 0));
                    target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 80, 1));
                });
        var pos = player.position();
        for (int i = 0; i < 40; i++) {
            double dx = (level.random.nextDouble() - 0.5) * 10;
            double dz = (level.random.nextDouble() - 0.5) * 10;
            level.addParticle(ParticleTypes.LARGE_SMOKE, pos.x + dx, pos.y, pos.z + dz, 0, 0.05, 0);
        }
        player.playSound(SoundEvents.CREEPER_PRIMED, 0.6f, 0.5f);
    }
}
