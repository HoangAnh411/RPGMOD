package com.rpgpack.skills;

import com.rpgpack.classes.ClassType;
import com.rpgpack.core.PlayerCapability;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

public class PoisonBladeSkill extends BaseSkill {

    public PoisonBladeSkill() {
        super("poison_blade", "Poison Blade", 25, 0, 240, "PHYSICAL", 1, ClassType.ASSASSIN);
    }

    @Override
    public float calculateDamage(Player player) {
        var data = player.getCapability(PlayerCapability.PLAYER_DATA).orElse(null);
        if (data == null) return 6f;
        return 6f + data.getDex() * 1.2f;
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
        target.addEffect(new MobEffectInstance(MobEffects.POISON, 80, 0));
        var pos = player.position();
        level.addParticle(ParticleTypes.ITEM_SLIME, pos.x, pos.y + 1, pos.z, 0, 0, 0);
        player.playSound(SoundEvents.SPIDER_HURT, 0.4f, 1.8f);
    }
}
