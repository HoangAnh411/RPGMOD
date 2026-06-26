package com.rpgpack.skills;

import com.rpgpack.classes.ClassType;
import com.rpgpack.core.PlayerCapability;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

public class ShieldBashSkill extends BaseSkill {

    public ShieldBashSkill() {
        super("shield_bash", "Shield Bash", 15, 20, 120, "PHYSICAL", 1, ClassType.WARRIOR);
    }

    @Override
    public float calculateDamage(Player player) {
        var data = player.getCapability(PlayerCapability.PLAYER_DATA).orElse(null);
        if (data == null) return 8f;
        return (8f + data.getStr() * 1.5f);
    }

    @Override
    public void execute(Player player, Level level) {
        float damage = calculateDamage(player);
        LivingEntity target = (LivingEntity) player.getLastHurtMob();
        if (target == null || !target.isAlive()) {
            // Find nearest entity instead of self
            var aabb = player.getBoundingBox().inflate(3.0);
            var nearest = level.getEntitiesOfClass(LivingEntity.class, aabb,
                    e -> !e.is(player) && e.isAlive());
            if (!nearest.isEmpty()) target = nearest.get(0);
        }
        if (target == null || target == player) return;

        target.hurt(player.damageSources().playerAttack(player), damage);
        target.knockback(1.5f, player.getX() - target.getX(), player.getZ() - target.getZ());

        var pos = target.position();
        level.addParticle(ParticleTypes.EXPLOSION, pos.x, pos.y + 1, pos.z, 0, 0, 0);
        player.playSound(SoundEvents.SHIELD_BLOCK, 0.8f, 1.0f);
    }
}
