package com.rpgpack.skills;

import com.rpgpack.classes.ClassType;
import com.rpgpack.core.PlayerCapability;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

public class ExecutionSkill extends BaseSkill {

    public ExecutionSkill() {
        super("execution", "Execution", 90, 0, 800, "PHYSICAL", 5, ClassType.ASSASSIN);
    }

    @Override
    public float calculateDamage(Player player) {
        var data = player.getCapability(PlayerCapability.PLAYER_DATA).orElse(null);
        if (data == null) return 20f;
        float base = 20f + data.getDex() * 3.5f;
        LivingEntity target = (LivingEntity) player.getLastHurtMob();
        if (target != null && target.getHealth() / target.getMaxHealth() < 0.3f) base *= 2.0f;
        return base;
    }

    @Override
    public void execute(Player player, Level level) {
        float damage = calculateDamage(player);
        LivingEntity target = (LivingEntity) player.getLastHurtMob();
        if (target == null || !target.isAlive()) {
            var aabb = player.getBoundingBox().inflate(3.0);
            var nearest = level.getEntitiesOfClass(LivingEntity.class, aabb,
                    e -> !e.is(player) && e.isAlive());
            if (!nearest.isEmpty()) target = nearest.get(0);
        }
        if (target == null || target == player) return;
        target.hurt(player.damageSources().playerAttack(player), damage);
        var pos = player.position();
        level.addParticle(ParticleTypes.SWEEP_ATTACK, pos.x, pos.y + 1, pos.z, 0, 0, 0);
        player.playSound(SoundEvents.PLAYER_ATTACK_CRIT, 0.9f, 0.8f);
    }
}
