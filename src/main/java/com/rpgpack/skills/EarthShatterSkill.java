package com.rpgpack.skills;

import com.rpgpack.classes.ClassType;
import com.rpgpack.core.PlayerCapability;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

public class EarthShatterSkill extends BaseSkill {

    public EarthShatterSkill() {
        super("earth_shatter", "Earth Shatter", 100, 50, 800, "PHYSICAL", 5, ClassType.WARRIOR);
    }

    @Override
    public float calculateDamage(Player player) {
        var data = player.getCapability(PlayerCapability.PLAYER_DATA).orElse(null);
        if (data == null) return 20f;
        return 20f + data.getStr() * 4.0f;
    }

    @Override
    public void execute(Player player, Level level) {
        float damage = calculateDamage(player);
        var aabb = player.getBoundingBox().inflate(6.0);
        level.getEntitiesOfClass(LivingEntity.class, aabb, e -> !e.is(player) && e.isAlive())
                .forEach(target -> target.hurt(player.damageSources().playerAttack(player), damage));
        var pos = player.position();
        for (int i = 0; i < 40; i++) {
            double dx = (level.random.nextDouble() - 0.5) * 12;
            double dz = (level.random.nextDouble() - 0.5) * 12;
            level.addParticle(ParticleTypes.EXPLOSION, pos.x + dx, pos.y, pos.z + dz, 0, 0.1, 0);
        }
        player.playSound(SoundEvents.GENERIC_EXPLODE, 1.0f, 0.6f);
    }
}
