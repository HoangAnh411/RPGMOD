package com.rpgpack.network;

import com.rpgpack.gui.ClassSelectionScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class OpenClassSelectionS2C {

    // No data needed — just a signal to open the GUI

    public static void encode(OpenClassSelectionS2C msg, FriendlyByteBuf buf) {}

    public static OpenClassSelectionS2C decode(FriendlyByteBuf buf) {
        return new OpenClassSelectionS2C();
    }

    public static void handle(OpenClassSelectionS2C msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> openScreen());
        ctx.get().setPacketHandled(true);
    }

    @OnlyIn(Dist.CLIENT)
    private static void openScreen() {
        Minecraft.getInstance().setScreen(new ClassSelectionScreen());
    }
}
