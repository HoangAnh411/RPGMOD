package com.rpgpack.network;

import com.rpgpack.RPGPack;
import com.rpgpack.core.PlayerCapability;
import com.rpgpack.core.PlayerData;
import com.rpgpack.init.ModMessages;
import com.rpgpack.skills.BaseSkill;
import com.rpgpack.skills.SkillCooldownManager;
import com.rpgpack.skills.SkillRegistry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class UseSkillC2S {

    private final String skillId;

    public UseSkillC2S(String skillId) {
        this.skillId = skillId;
    }

    public static void encode(UseSkillC2S msg, FriendlyByteBuf buf) {
        buf.writeUtf(msg.skillId);
    }

    public static UseSkillC2S decode(FriendlyByteBuf buf) {
        return new UseSkillC2S(buf.readUtf());
    }

    public static void handle(UseSkillC2S msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            if (player == null) return;

            BaseSkill skill = SkillRegistry.get(msg.skillId);
            if (skill == null) {
                RPGPack.LOGGER.warn("[COMBAT] Unknown skill: {}", msg.skillId);
                return;
            }

            player.getCapability(PlayerCapability.PLAYER_DATA).ifPresent(data -> {
                // Cooldown check
                if (SkillCooldownManager.isOnCooldown(player, msg.skillId)) return;

                // Resource checks
                if (skill.getManaCost() > 0 && data.getCurrentMana() < skill.getManaCost()) return;
                if (data.getLevel() < skill.getUnlockLevel()) return;

                // Consume resources
                if (skill.getManaCost() > 0)
                    data.setCurrentMana(data.getCurrentMana() - skill.getManaCost());

                // Apply cooldown with CDR and rank scaling
                var derived = data.getCachedStats(player);
                int rank = data.getSkillRank(msg.skillId);
                int baseCd = skill.getScaledCooldown(rank);
                int finalCd = (int) (baseCd * (1f - derived.cooldownReduction / 100f));
                finalCd = Math.max(finalCd, 20);
                SkillCooldownManager.setCooldown(player, msg.skillId, finalCd);

                // Track target before execution for mastery
                var beforeTarget = player.getLastHurtMob();

                // Execute skill with rank-scaled damage
                float dmg = skill.calculateDamage(player) * skill.getDamageMultiplier(rank);
                skill.execute(player, player.level());

                // Mastery gain — award if skill hit anything (even if target died)
                var afterTarget = player.getLastHurtMob();
                boolean hitSomething = (afterTarget != null && afterTarget != beforeTarget) || beforeTarget != null;
                if (hitSomething) {
                    var masteryTarget = afterTarget != null ? afterTarget : beforeTarget;
                    int masteryGain = masteryTarget != null ? BaseSkill.getMasteryGain(masteryTarget) : 1;
                    data.addSkillMastery(msg.skillId, masteryGain);
                    RPGPack.LOGGER.info("[MASTERY] +{} on {} (total: {})",
                            masteryGain, skill.getSkillName(), data.getSkillMastery(msg.skillId));
                }

                // Combat debug log
                RPGPack.LOGGER.info("[COMBAT] Caster={} | Skill={} | Damage={:.1f} | Mana={} | CD={}s",
                        player.getName().getString(), skill.getSkillName(), dmg,
                        skill.getManaCost(), finalCd / 20f);

                // Sync data to client
                PlayerData snap = new PlayerData();
                snap.copyFrom(data);
                ModMessages.CHANNEL.sendTo(
                        new SyncPlayerDataS2C(snap),
                        player.connection.connection,
                        NetworkDirection.PLAY_TO_CLIENT
                );

                // Sync cooldown
                int remaining = SkillCooldownManager.getCooldown(player, msg.skillId);
                ModMessages.CHANNEL.sendTo(
                        new CooldownSyncS2C(msg.skillId, remaining),
                        player.connection.connection,
                        NetworkDirection.PLAY_TO_CLIENT
                );
            });
        });
        ctx.get().setPacketHandled(true);
    }
}
