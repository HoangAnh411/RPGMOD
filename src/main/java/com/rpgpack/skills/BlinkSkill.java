package com.rpgpack.skills;

import com.rpgpack.classes.ClassType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class BlinkSkill extends BaseSkill {

    public BlinkSkill() {
        super("blink", "Blink", 30, 0, 240, "UTILITY", 3, ClassType.MAGE);
    }

    @Override public float calculateDamage(Player player) { return 0; }

    @Override
    public void execute(Player player, Level level) {
        Vec3 look = player.getLookAngle();
        Vec3 dest = player.position().add(look.scale(8.0));
        var src = player.position();
        for (int i = 0; i < 15; i++)
            level.addParticle(ParticleTypes.PORTAL, src.x, src.y + 1, src.z, 0, 0, 0);
        player.teleportTo(dest.x, dest.y, dest.z);
        for (int i = 0; i < 15; i++)
            level.addParticle(ParticleTypes.PORTAL, dest.x, dest.y + 1, dest.z, 0, 0, 0);
        player.playSound(SoundEvents.ENDERMAN_TELEPORT, 0.7f, 1.0f);
    }
}
