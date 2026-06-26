package com.rpgpack.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * Lightweight vitals sync (HP, Mana, XP) — avoids full NBT serialization
 * for the frequent 0.5s periodic sync.
 */
public class SyncVitalsS2C {

    private final float hp;
    private final float mana;
    private final int exp;

    public SyncVitalsS2C(float hp, float mana, int exp) {
        this.hp = hp;
        this.mana = mana;
        this.exp = exp;
    }

    public static void encode(SyncVitalsS2C msg, FriendlyByteBuf buf) {
        buf.writeFloat(msg.hp);
        buf.writeFloat(msg.mana);
        buf.writeVarInt(msg.exp);
    }

    public static SyncVitalsS2C decode(FriendlyByteBuf buf) {
        return new SyncVitalsS2C(buf.readFloat(), buf.readFloat(), buf.readVarInt());
    }

    public static void handle(SyncVitalsS2C msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            var player = com.rpgpack.proxy.ClientProxy.getPlayer();
            if (player != null) {
                player.getCapability(com.rpgpack.core.PlayerCapability.PLAYER_DATA)
                        .ifPresent(data -> {
                            data.setCurrentHp(msg.hp);
                            data.setCurrentMana(msg.mana);
                            data.setExperience(msg.exp);
                        });
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
