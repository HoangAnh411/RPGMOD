package com.rpgpack.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class FloatingDamageS2C {

    private final int entityId;
    private final float amount;
    private final byte type; // 0=normal, 1=crit, 2=heal, 3=dodge, 4=miss
    private final double x, y, z;

    public static final byte TYPE_DAMAGE = 0;
    public static final byte TYPE_CRIT = 1;
    public static final byte TYPE_HEAL = 2;
    public static final byte TYPE_DODGE = 3;
    public static final byte TYPE_MISS = 4;

    public FloatingDamageS2C(int entityId, float amount, byte type, Vec3 pos) {
        this.entityId = entityId;
        this.amount = amount;
        this.type = type;
        this.x = pos.x;
        this.y = pos.y;
        this.z = pos.z;
    }

    public static void encode(FloatingDamageS2C msg, FriendlyByteBuf buf) {
        buf.writeVarInt(msg.entityId);
        buf.writeFloat(msg.amount);
        buf.writeByte(msg.type);
        buf.writeDouble(msg.x);
        buf.writeDouble(msg.y);
        buf.writeDouble(msg.z);
    }

    public static FloatingDamageS2C decode(FriendlyByteBuf buf) {
        return new FloatingDamageS2C(
                buf.readVarInt(),
                buf.readFloat(),
                buf.readByte(),
                new Vec3(buf.readDouble(), buf.readDouble(), buf.readDouble())
        );
    }

    public static void handle(FloatingDamageS2C msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            var player = com.rpgpack.proxy.ClientProxy.getPlayer();
            if (player != null && player.level().getEntity(msg.entityId) != null) {
                com.rpgpack.gui.FloatingDamageRenderer.add(
                        msg.entityId, msg.amount, msg.type, msg.x, msg.y, msg.z);
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
