package com.rpgpack.skills;

import com.rpgpack.core.PlayerCapability;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class ShadowStrikeSkill extends BaseSkill {

    public ShadowStrikeSkill() {
        super("shadow_strike", "Shadow Strike", 0, 15, 100, "PHYSICAL", 1);
    }

    @Override
    public float calculateDamage(Player player) {
        var data = player.getCapability(PlayerCapability.PLAYER_DATA).orElse(null);
        if (data == null) return 12f;
        float base = 12f + (data.getDex() * 2.0f);
        if (isBackstab(player)) {
            base *= 1.5f;
        }
        return base;
    }

    private boolean isBackstab(Player player) {
        // TODO: Check angle relative to target's facing
        return false;
    }

    @Override
    public void execute(Player player, Level level) {
        float damage = calculateDamage(player);
        var target = (LivingEntity) player.getLastHurtMob();
        if (target != null && target.isAlive()) {
            target.hurt(player.damageSources().playerAttack(player), damage);
        }
    }
}
