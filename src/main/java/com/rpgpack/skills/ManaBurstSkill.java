package com.rpgpack.skills;

import com.rpgpack.core.PlayerCapability;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class ManaBurstSkill extends BaseSkill {

    public ManaBurstSkill() {
        super("mana_burst", "Mana Burst", 30, 0, 120, "MAGIC", 1);
    }

    @Override
    public float calculateDamage(Player player) {
        var data = player.getCapability(PlayerCapability.PLAYER_DATA).orElse(null);
        if (data == null) return 15f;
        return 15f + (data.getInt() * 3.0f);
    }

    @Override
    public void execute(Player player, Level level) {
        float damage = calculateDamage(player);
        Vec3 look = player.getLookAngle();
        Vec3 center = player.position().add(look.scale(3.0));
        var aabb = new AABB(center.subtract(2, 2, 2), center.add(2, 2, 2));
        level.getEntitiesOfClass(LivingEntity.class, aabb, e -> e != player && e.isAlive())
                .forEach(target -> {
                    target.hurt(player.damageSources().indirectMagic(player, player), damage);
                });
    }
}
