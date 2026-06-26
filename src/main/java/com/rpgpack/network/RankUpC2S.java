package com.rpgpack.network;

import com.rpgpack.RPGPack;
import com.rpgpack.core.PlayerCapability;
import com.rpgpack.core.PlayerData;
import com.rpgpack.init.ModMessages;
import com.rpgpack.skills.BaseSkill;
import com.rpgpack.skills.SkillRegistry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class RankUpC2S {

    private final String skillId;

    public RankUpC2S(String skillId) {
        this.skillId = skillId;
    }

    public static void encode(RankUpC2S msg, FriendlyByteBuf buf) {
        buf.writeUtf(msg.skillId);
    }

    public static RankUpC2S decode(FriendlyByteBuf buf) {
        return new RankUpC2S(buf.readUtf());
    }

    public static void handle(RankUpC2S msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            if (player == null) return;

            BaseSkill skill = SkillRegistry.get(msg.skillId);
            if (skill == null) return;

            player.getCapability(PlayerCapability.PLAYER_DATA).ifPresent(data -> {
                if (!data.canRankUp(msg.skillId, data.getLevel())) return;

                int newRank = data.getSkillRank(msg.skillId) + 1;
                data.setSkillRank(msg.skillId, newRank);

                RPGPack.LOGGER.info("[RANK_UP] {} {} → Rank {}",
                        player.getName().getString(), skill.getSkillName(),
                        PlayerData.RANK_NAMES[newRank]);

                PlayerData snap = new PlayerData();
                snap.copyFrom(data);
                ModMessages.CHANNEL.sendTo(
                        new SyncPlayerDataS2C(snap),
                        player.connection.connection,
                        NetworkDirection.PLAY_TO_CLIENT
                );
            });
        });
        ctx.get().setPacketHandled(true);
    }
}
