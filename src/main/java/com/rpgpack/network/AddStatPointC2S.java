package com.rpgpack.network;

import com.rpgpack.RPGPack;
import com.rpgpack.core.PlayerCapability;
import com.rpgpack.core.PlayerData;
import com.rpgpack.init.ModMessages;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class AddStatPointC2S {

    private final String statName;

    public AddStatPointC2S(String statName) {
        this.statName = statName;
    }

    public static void encode(AddStatPointC2S msg, FriendlyByteBuf buf) {
        buf.writeUtf(msg.statName);
    }

    public static AddStatPointC2S decode(FriendlyByteBuf buf) {
        return new AddStatPointC2S(buf.readUtf());
    }

    public static void handle(AddStatPointC2S msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            if (player == null) return;

            player.getCapability(PlayerCapability.PLAYER_DATA).ifPresent(data -> {
                if (data.getStatPoints() <= 0) return;

                boolean valid = switch (msg.statName.toLowerCase()) {
                    case "str" -> { data.addStr(1); yield true; }
                    case "vit" -> { data.addVit(1); yield true; }
                    case "end" -> { data.addEnd(1); yield true; }
                    case "agi" -> { data.addAgi(1); yield true; }
                    case "dex" -> { data.addDex(1); yield true; }
                    case "int" -> { data.addInt(1); yield true; }
                    case "wis" -> { data.addWis(1); yield true; }
                    case "luk" -> { data.addLuk(1); yield true; }
                    default -> false;
                };

                if (!valid) return;

                data.setStatPoints(data.getStatPoints() - 1);
                data.markStatsDirty();

                // Update vanilla max health attribute
                var stats = data.getCachedStats(player);
                player.getAttribute(Attributes.MAX_HEALTH).setBaseValue(stats.maxHp);

                RPGPack.LOGGER.info("[STAT] {} allocated -> {}, remaining points: {}",
                        player.getName().getString(), msg.statName.toUpperCase(), data.getStatPoints());

                // Sync immediately back to client
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
