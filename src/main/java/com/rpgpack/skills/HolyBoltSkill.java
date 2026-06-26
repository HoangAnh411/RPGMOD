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

public class HolyBoltSkill extends BaseSkill {

    public HolyBoltSkill() {
        super("holy_bolt", "Holy Bolt", 25, 0, 200, "MAGIC", 1, ClassType.CLERIC);
    }

    @Override
    public float calculateDamage(Player player) {
        var data = player.getCapability(PlayerCapability.PLAYER_DATA).orElse(null);
        if (data == null) return 8f;
        return 8f + data.getWis() * 1.5f;
    }

    @Override
    public void execute(Player player, Level level) {
        float damage = calculateDamage(player);
        Vec3 start = player.getEyePosition();
        Vec3 look = player.getLookAngle();
        for (double d = 0; d < 12; d += 0.5) {
            Vec3 point = start.add(look.scale(d));
            level.addParticle(ParticleTypes.ENCHANT, point.x, point.y, point.z, 0, 0, 0);
            var entities = level.getEntitiesOfClass(LivingEntity.class,
                    new AABB(point.subtract(0.5, 0.5, 0.5), point.add(0.5, 0.5, 0.5)),
                    e -> !e.is(player) && e.isAlive());
            if (!entities.isEmpty()) {
                if (!(entities.get(0) instanceof Player))
                    entities.get(0).hurt(player.damageSources().indirectMagic(player, player), damage);
                break;
            }
        }
        player.playSound(SoundEvents.ARROW_SHOOT, 0.5f, 1.8f);
    }
}
