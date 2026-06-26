package com.rpgpack.skills;

import com.rpgpack.classes.ClassType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class DivineBlessingSkill extends BaseSkill {

    public DivineBlessingSkill() {
        super("divine_blessing", "Divine Blessing", 80, 0, 800, "BUFF", 5, ClassType.CLERIC);
    }

    @Override public float calculateDamage(Player player) { return 0; }

    @Override
    public void execute(Player player, Level level) {
        player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 200, 1));      // 10s regen II
        player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 200, 1)); // 10s res II
        player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 200, 1));        // 10s absorb II
        player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 200, 0));   // 10s fire res

        var pos = player.position();
        for (int i = 0; i < 40; i++) {
            level.addParticle(ParticleTypes.ENCHANT, pos.x, pos.y + i * 0.05, pos.z, 0.5, 0.1, 0.5);
            level.addParticle(ParticleTypes.HAPPY_VILLAGER, pos.x, pos.y + i * 0.05, pos.z, 0.3, 0.1, 0.3);
        }
        player.playSound(SoundEvents.PLAYER_LEVELUP, 0.8f, 0.5f);
        player.playSound(SoundEvents.BELL_BLOCK, 0.6f, 1.2f);
    }
}
