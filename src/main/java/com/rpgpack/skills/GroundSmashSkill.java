package com.rpgpack.skills;

import com.rpgpack.core.PlayerCapability;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

public class GroundSmashSkill extends BaseSkill {

    public GroundSmashSkill() {
        super("ground_smash", "Ground Smash", 0, 20, 160, "PHYSICAL", 1);
    }

    @Override
    public float calculateDamage(Player player) {
        var data = player.getCapability(PlayerCapability.PLAYER_DATA).orElse(null);
        if (data == null) return 10f;
        return 10f + (data.getStr() * 2.5f);
    }

    @Override
    public void execute(Player player, Level level) {
        float damage = calculateDamage(player);
        var aabb = player.getBoundingBox().inflate(4.0);
        level.getEntitiesOfClass(LivingEntity.class, aabb, e -> e != player && e.isAlive())
                .forEach(target -> {
                    target.hurt(player.damageSources().playerAttack(player), damage);
                });
    }
}
