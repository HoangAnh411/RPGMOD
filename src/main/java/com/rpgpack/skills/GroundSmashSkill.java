package com.rpgpack.skills;

import com.rpgpack.classes.ClassType;
import com.rpgpack.core.PlayerCapability;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

public class GroundSmashSkill extends BaseSkill {

    public GroundSmashSkill() {
        super("ground_smash", "Ground Smash", 20, 25, 140, "PHYSICAL", 1, ClassType.BERSERKER);
    }

    @Override
    public float calculateDamage(Player player) {
        var data = player.getCapability(PlayerCapability.PLAYER_DATA).orElse(null);
        if (data == null) return 10f;
        return 10f + data.getStr() * 2.0f;
    }

    @Override
    public void execute(Player player, Level level) {
        float damage = calculateDamage(player);
        var aabb = player.getBoundingBox().inflate(4.0);
        level.getEntitiesOfClass(LivingEntity.class, aabb, e -> !e.is(player) && e.isAlive())
                .forEach(target -> target.hurt(player.damageSources().playerAttack(player), damage));
        var pos = player.position();
        for (int i = 0; i < 20; i++) {
            double dx = (level.random.nextDouble() - 0.5) * 8;
            double dz = (level.random.nextDouble() - 0.5) * 8;
            level.addParticle(ParticleTypes.EXPLOSION, pos.x + dx, pos.y, pos.z + dz, 0, 0.05, 0);
        }
        player.playSound(SoundEvents.GENERIC_EXPLODE, 0.8f, 1.0f);
    }
}
