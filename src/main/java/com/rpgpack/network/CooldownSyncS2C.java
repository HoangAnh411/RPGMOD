package com.rpgpack.network;

import com.rpgpack.gui.SkillHotbarOverlay;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class CooldownSyncS2C {

    private final Map<String, Integer> cooldowns;

    public CooldownSyncS2C(String skillId, int remainingTicks) {
        this.cooldowns = new HashMap<>();
        this.cooldowns.put(skillId, remainingTicks);
    }

    public CooldownSyncS2C(Map<String, Integer> cooldowns) {
        this.cooldowns = cooldowns;
    }

    public static void encode(CooldownSyncS2C msg, FriendlyByteBuf buf) {
        buf.writeVarInt(msg.cooldowns.size());
        for (var e : msg.cooldowns.entrySet()) {
            buf.writeUtf(e.getKey());
            buf.writeVarInt(e.getValue());
        }
    }

    public static CooldownSyncS2C decode(FriendlyByteBuf buf) {
        int count = buf.readVarInt();
        Map<String, Integer> map = new HashMap<>();
        for (int i = 0; i < count; i++) {
            map.put(buf.readUtf(), buf.readVarInt());
        }
        return new CooldownSyncS2C(map);
    }

    public static void handle(CooldownSyncS2C msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            for (var e : msg.cooldowns.entrySet()) {
                SkillHotbarOverlay.setClientCooldown(e.getKey(), e.getValue());
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
