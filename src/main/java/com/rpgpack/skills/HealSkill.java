package com.rpgpack.skills;

import com.rpgpack.classes.ClassType;
import com.rpgpack.core.PlayerCapability;
import com.rpgpack.init.ModMessages;
import com.rpgpack.network.FloatingDamageS2C;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.PacketDistributor;

public class HealSkill extends BaseSkill {

    public HealSkill() {
        super("heal", "Heal", 20, 0, 160, "HEAL", 1, ClassType.CLERIC);
    }

    @Override
    public float calculateDamage(Player player) { return 0; }

    @Override
    public void execute(Player player, Level level) {
        var data = player.getCapability(PlayerCapability.PLAYER_DATA).orElse(null);
        if (data == null) return;
        var stats = data.getCachedStats(player);
        float healAmount = 15f + data.getWis() * 2.0f;
        data.setCurrentHp(Math.min(data.getCurrentHp() + healAmount, stats.maxHp));

        // Floating heal text
        if (player instanceof ServerPlayer sp) {
            ModMessages.CHANNEL.send(
                    PacketDistributor.TRACKING_ENTITY.with(() -> sp),
                    new FloatingDamageS2C(sp.getId(), healAmount, FloatingDamageS2C.TYPE_HEAL,
                            sp.position().add(0, sp.getBbHeight() * 0.7, 0)));
        }

        var pos = player.position();
        for (int i = 0; i < 15; i++)
            level.addParticle(ParticleTypes.HEART, pos.x, pos.y + 1 + i * 0.1, pos.z, 0.1, 0.1, 0.1);
        player.playSound(SoundEvents.EXPERIENCE_ORB_PICKUP, 0.6f, 1.5f);
    }
}
