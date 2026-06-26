package com.rpgpack.network;

import com.rpgpack.RPGPack;
import com.rpgpack.classes.ClassManager;
import com.rpgpack.classes.ClassType;
import com.rpgpack.core.PlayerCapability;
import com.rpgpack.core.PlayerData;
import com.rpgpack.init.ModMessages;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.Attributes;
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
        NetworkEvent.Context context = ctx.get();

        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (player == null) {
                RPGPack.LOGGER.warn("[CLASS] Packet received but sender is null");
                return;
            }

            player.getCapability(PlayerCapability.PLAYER_DATA).ifPresent(data -> {
                // Already chose a class
                if (!"NONE".equals(data.getSelectedClass())) {
                    RPGPack.LOGGER.warn("[CLASS] Player {} already has class {}, ignoring selection of {}",
                            player.getName().getString(), data.getSelectedClass(), msg.className);
                    return;
                }

                try {
                    ClassType classType = ClassType.valueOf(msg.className.toUpperCase());
                    ClassManager.applyClass(data, classType);

                    // Sync vanilla max health to RPG maxHp
                    var stats = data.getCachedStats(player);
                    player.getAttribute(Attributes.MAX_HEALTH).setBaseValue(stats.maxHp);
                    player.setHealth(stats.maxHp);

                    RPGPack.LOGGER.info("[CLASS] Applied on server: {} -> {}",
                            player.getName().getString(), classType.getDisplayName());

                    // Sync updated data back to client
                    PlayerData syncData = new PlayerData();
                    syncData.copyFrom(data);
                    ModMessages.CHANNEL.sendTo(
                            new SyncPlayerDataS2C(syncData),
                            player.connection.connection,
                            NetworkDirection.PLAY_TO_CLIENT
                    );
                    RPGPack.LOGGER.info("[CLASS] Synced to client: {}", player.getName().getString());
                } catch (IllegalArgumentException e) {
                    RPGPack.LOGGER.error("[CLASS] Invalid class name: {}", msg.className);
                }
            });
        });

        context.setPacketHandled(true);
    }
}
