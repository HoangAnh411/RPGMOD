package com.rpgpack.skills;

import com.rpgpack.classes.ClassType;
import com.rpgpack.core.PlayerCapability;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

public class WhirlwindSkill extends BaseSkill {

    public WhirlwindSkill() {
        super("whirlwind", "Whirlwind", 30, 35, 360, "PHYSICAL", 3, ClassType.BERSERKER);
    }

    @Override
    public float calculateDamage(Player player) {
        var data = player.getCapability(PlayerCapability.PLAYER_DATA).orElse(null);
        if (data == null) return 6f;
        return 6f + data.getStr() * 1.2f;
    }

    @Override
    public void execute(Player player, Level level) {
        float damage = calculateDamage(player);
        var aabb = player.getBoundingBox().inflate(3.0);
        var entities = level.getEntitiesOfClass(LivingEntity.class, aabb, e -> !e.is(player) && e.isAlive());
        for (int spin = 0; spin < 3; spin++)
            entities.forEach(t -> t.hurt(player.damageSources().playerAttack(player), damage));
        var pos = player.position();
        for (int i = 0; i < 30; i++) {
            double a = level.random.nextDouble() * Math.PI * 2;
            double r = 2.5;
            level.addParticle(ParticleTypes.SWEEP_ATTACK, pos.x + Math.cos(a) * r, pos.y + 1, pos.z + Math.sin(a) * r, 0, 0, 0);
        }
        player.playSound(SoundEvents.PLAYER_ATTACK_SWEEP, 0.8f, 1.2f);
    }
}
