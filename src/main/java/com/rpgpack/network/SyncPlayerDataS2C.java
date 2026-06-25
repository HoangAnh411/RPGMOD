package com.rpgpack.network;

import com.rpgpack.core.PlayerData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SyncPlayerDataS2C {

    private final PlayerData data;

    public SyncPlayerDataS2C(PlayerData data) {
        this.data = data;
    }

    public static void encode(SyncPlayerDataS2C msg, FriendlyByteBuf buf) {
        buf.writeNbt(msg.data.toNBT());
    }

    public static SyncPlayerDataS2C decode(FriendlyByteBuf buf) {
        PlayerData data = new PlayerData();
        var tag = buf.readNbt();
        if (tag != null) {
            data.fromNBT(tag);
        }
        return new SyncPlayerDataS2C(data);
    }

    public static void handle(SyncPlayerDataS2C msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            var player = com.rpgpack.proxy.ClientProxy.getPlayer();
            if (player != null) {
                player.getCapability(com.rpgpack.core.PlayerCapability.PLAYER_DATA)
                        .ifPresent(data -> data.copyFrom(msg.data));
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
