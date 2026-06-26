package com.rpgpack.network;

import com.rpgpack.gui.SkillHotbarOverlay;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class CooldownSyncS2C {

    private final String skillId;
    private final int remainingTicks;

    public CooldownSyncS2C(String skillId, int remainingTicks) {
        this.skillId = skillId;
        this.remainingTicks = remainingTicks;
    }

    public static void encode(CooldownSyncS2C msg, FriendlyByteBuf buf) {
        buf.writeUtf(msg.skillId);
        buf.writeInt(msg.remainingTicks);
    }

    public static CooldownSyncS2C decode(FriendlyByteBuf buf) {
        return new CooldownSyncS2C(buf.readUtf(), buf.readInt());
    }

    public static void handle(CooldownSyncS2C msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            SkillHotbarOverlay.setClientCooldown(msg.skillId, msg.remainingTicks);
        });
        ctx.get().setPacketHandled(true);
    }
}
