package com.rpgpack.network;

import com.rpgpack.core.PlayerCapability;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
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

                switch (msg.statName.toLowerCase()) {
                    case "str" -> data.addStr(1);
                    case "vit" -> data.addVit(1);
                    case "end" -> data.addEnd(1);
                    case "agi" -> data.addAgi(1);
                    case "dex" -> data.addDex(1);
                    case "int" -> data.addInt(1);
                    case "wis" -> data.addWis(1);
                    case "luk" -> data.addLuk(1);
                    default -> { return; }
                }
                data.setStatPoints(data.getStatPoints() - 1);
            });
        });
        ctx.get().setPacketHandled(true);
    }
}
