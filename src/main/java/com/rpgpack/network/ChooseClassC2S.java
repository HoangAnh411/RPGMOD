package com.rpgpack.network;

import com.rpgpack.classes.ClassManager;
import com.rpgpack.classes.ClassType;
import com.rpgpack.core.PlayerCapability;
import com.rpgpack.init.ModMessages;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ChooseClassC2S {

    private final String className;

    public ChooseClassC2S(String className) {
        this.className = className;
    }

    public static void encode(ChooseClassC2S msg, FriendlyByteBuf buf) {
        buf.writeUtf(msg.className);
    }

    public static ChooseClassC2S decode(FriendlyByteBuf buf) {
        return new ChooseClassC2S(buf.readUtf());
    }

    public static void handle(ChooseClassC2S msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            if (player == null) return;

            player.getCapability(PlayerCapability.PLAYER_DATA).ifPresent(data -> {
                if (!data.getSelectedClass().equals("NONE")) return;

                try {
                    ClassType type = ClassType.valueOf(msg.className.toUpperCase());
                    ClassManager.applyClass(data, type);

                    // Sync back to client
                    PlayerData snap = new PlayerData();
                    snap.copyFrom(data);
                    ModMessages.CHANNEL.sendTo(
                            new SyncPlayerDataS2C(snap),
                            player.connection.connection,
                            NetworkDirection.PLAY_TO_CLIENT
                    );
                } catch (IllegalArgumentException ignored) {}
            });
        });
        ctx.get().setPacketHandled(true);
    }
}
