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

public class ManaBurstSkill extends BaseSkill {

    public ManaBurstSkill() {
        super("mana_burst", "Mana Burst", 15, 0, 100, "MAGIC", 1, ClassType.MAGE);
    }

    @Override
    public float calculateDamage(Player player) {
        var data = player.getCapability(PlayerCapability.PLAYER_DATA).orElse(null);
        if (data == null) return 15f;
        return 15f + data.getInt() * 3.0f;
    }

    @Override
    public void execute(Player player, Level level) {
        float damage = calculateDamage(player);
        Vec3 look = player.getLookAngle();
        Vec3 center = player.position().add(look.scale(3.0));
        var aabb = new AABB(center.subtract(2, 2, 2), center.add(2, 2, 2));
        level.getEntitiesOfClass(LivingEntity.class, aabb, e -> !e.is(player) && e.isAlive())
                .forEach(t -> t.hurt(player.damageSources().indirectMagic(player, player), damage));
        for (int i = 0; i < 10; i++)
            level.addParticle(ParticleTypes.ENCHANT, center.x, center.y, center.z,
                    level.random.nextGaussian() * 0.2, level.random.nextGaussian() * 0.2, level.random.nextGaussian() * 0.2);
        player.playSound(SoundEvents.ENCHANTMENT_TABLE_USE, 0.6f, 1.4f);
    }
}
