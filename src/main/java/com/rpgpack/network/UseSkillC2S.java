package com.rpgpack.network;

import com.rpgpack.core.PlayerCapability;
import com.rpgpack.core.StatCalculator;
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
            if (skill == null) return;

            player.getCapability(PlayerCapability.PLAYER_DATA).ifPresent(data -> {
                // Check cooldown
                if (SkillCooldownManager.isOnCooldown(player, msg.skillId)) return;

                // Check mana
                if (skill.getManaCost() > 0 && data.getCurrentMana() < skill.getManaCost()) return;

                // Check stamina
                if (skill.getStaminaCost() > 0 && data.getCurrentStamina() < skill.getStaminaCost()) return;

                // Check level
                if (data.getLevel() < skill.getUnlockLevel()) return;

                // Consume resources
                if (skill.getManaCost() > 0) {
                    data.setCurrentMana(data.getCurrentMana() - skill.getManaCost());
                }
                if (skill.getStaminaCost() > 0) {
                    data.setCurrentStamina(data.getCurrentStamina() - skill.getStaminaCost());
                }

                // Apply cooldown (with CDR)
                var derived = StatCalculator.calculate(data);
                int finalCd = (int) (skill.getCooldownTicks() * (1f - derived.cooldownReduction / 100f));
                finalCd = Math.max(finalCd, 20); // min 1 second
                SkillCooldownManager.setCooldown(player, msg.skillId, finalCd);

                // Execute skill
                skill.execute(player, player.level());

                // Sync data back
                var snap = new com.rpgpack.core.PlayerData();
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
