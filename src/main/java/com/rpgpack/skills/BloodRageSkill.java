package com.rpgpack.skills;

import com.rpgpack.classes.ClassType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class BloodRageSkill extends BaseSkill {

    public BloodRageSkill() {
        super("blood_rage", "Blood Rage", 35, 0, 280, "BUFF", 1, ClassType.BERSERKER);
    }

    @Override public float calculateDamage(Player player) { return 0; }

    @Override
    public void execute(Player player, Level level) {
        player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 160, 0));
        player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 160, 0));
        var pos = player.position();
        level.addParticle(ParticleTypes.FLAME, pos.x, pos.y + 1, pos.z, 0.3, 0.3, 0.3);
        player.playSound(SoundEvents.RAVAGER_ROAR, 0.7f, 0.9f);
    }
}
