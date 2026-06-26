package com.rpgpack.skills;

import com.rpgpack.classes.ClassType;
import com.rpgpack.core.PlayerCapability;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class MeteorSkill extends BaseSkill {

    public MeteorSkill() {
        super("meteor", "Meteor", 150, 0, 900, "MAGIC", 5, ClassType.MAGE);
    }

    @Override
    public float calculateDamage(Player player) {
        var data = player.getCapability(PlayerCapability.PLAYER_DATA).orElse(null);
        if (data == null) return 30f;
        return 30f + data.getInt() * 5.0f;
    }

    @Override
    public void execute(Player player, Level level) {
        float damage = calculateDamage(player);
        Vec3 look = player.getLookAngle();
        Vec3 center = player.position().add(look.scale(5.0));
        var aabb = new AABB(center.subtract(4, 4, 4), center.add(4, 4, 4));
        level.getEntitiesOfClass(LivingEntity.class, aabb, e -> !e.is(player) && e.isAlive())
                .forEach(t -> t.hurt(player.damageSources().indirectMagic(player, player), damage));
        for (int i = 0; i < 50; i++) {
            double dx = (level.random.nextDouble() - 0.5) * 8;
            double dz = (level.random.nextDouble() - 0.5) * 8;
            level.addParticle(ParticleTypes.FLAME, center.x + dx, center.y, center.z + dz, 0, 0.1, 0);
            level.addParticle(ParticleTypes.LAVA, center.x + dx, center.y, center.z + dz, 0, 0.05, 0);
            level.addParticle(ParticleTypes.EXPLOSION, center.x + dx, center.y + 1, center.z + dz, 0, 0.1, 0);
        }
        player.playSound(SoundEvents.GENERIC_EXPLODE, 1.2f, 0.5f);
        player.playSound(SoundEvents.LIGHTNING_BOLT_THUNDER, 0.8f, 0.3f);
    }
}
