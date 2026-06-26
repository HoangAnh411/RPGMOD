package com.rpgpack.skills;

import com.rpgpack.classes.ClassType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class BerserkModeSkill extends BaseSkill {

    public BerserkModeSkill() {
        super("berserk_mode", "Berserk Mode", 80, 40, 800, "BUFF", 5, ClassType.BERSERKER);
    }

    @Override public float calculateDamage(Player player) { return 0; }

    @Override
    public void execute(Player player, Level level) {
        player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 200, 1));
        player.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 200, 2));
        player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 200, 0));
        var pos = player.position();
        for (int i = 0; i < 30; i++)
            level.addParticle(ParticleTypes.FLAME, pos.x, pos.y + i * 0.1, pos.z, 0.4, 0.1, 0.4);
        player.playSound(SoundEvents.WITHER_SPAWN, 0.8f, 0.6f);
    }
}
