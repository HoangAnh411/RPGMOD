package com.rpgpack.skills;

import com.rpgpack.classes.ClassType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class PurifySkill extends BaseSkill {

    public PurifySkill() {
        super("purify", "Purify", 30, 0, 400, "HEAL", 3, ClassType.CLERIC);
    }

    @Override public float calculateDamage(Player player) { return 0; }

    @Override
    public void execute(Player player, Level level) {
        // Remove negative effects
        player.removeEffect(MobEffects.POISON);
        player.removeEffect(MobEffects.WITHER);
        player.removeEffect(MobEffects.WEAKNESS);
        player.removeEffect(MobEffects.MOVEMENT_SLOWDOWN);
        player.removeEffect(MobEffects.BLINDNESS);

        // Apply regen
        player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 80, 0)); // 4s regen

        var pos = player.position();
        for (int i = 0; i < 20; i++)
            level.addParticle(ParticleTypes.HAPPY_VILLAGER, pos.x, pos.y + i * 0.1, pos.z, 0.2, 0.1, 0.2);
        player.playSound(SoundEvents.ENCHANTMENT_TABLE_USE, 0.6f, 1.0f);
    }
}
