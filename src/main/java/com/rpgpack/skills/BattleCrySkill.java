package com.rpgpack.skills;

import com.rpgpack.classes.ClassType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class BattleCrySkill extends BaseSkill {

    public BattleCrySkill() {
        super("battle_cry", "Battle Cry", 30, 0, 240, "BUFF", 1, ClassType.WARRIOR);
    }

    @Override public float calculateDamage(Player player) { return 0; }

    @Override
    public void execute(Player player, Level level) {
        player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 100, 0));
        player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 100, 0));
        var pos = player.position();
        level.addParticle(ParticleTypes.ENCHANT, pos.x, pos.y + 1, pos.z, 0.5, 0.5, 0.5);
        player.playSound(SoundEvents.EVOKER_CAST_SPELL, 0.7f, 1.2f);
    }
}
