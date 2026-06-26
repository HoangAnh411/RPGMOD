package com.rpgpack.skills;

import com.rpgpack.classes.ClassType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class ShieldWallSkill extends BaseSkill {

    public ShieldWallSkill() {
        super("shield_wall", "Shield Wall", 40, 0, 400, "BUFF", 3, ClassType.WARRIOR);
    }

    @Override public float calculateDamage(Player player) { return 0; }

    @Override
    public void execute(Player player, Level level) {
        player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 100, 2));
        var pos = player.position();
        for (int i = 0; i < 20; i++)
            level.addParticle(ParticleTypes.ENCHANTED_HIT, pos.x, pos.y + i * 0.1, pos.z, 0.3, 0.1, 0.3);
        player.playSound(SoundEvents.ANVIL_LAND, 0.6f, 0.8f);
    }
}
