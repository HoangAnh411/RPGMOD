package com.rpgpack.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ClientProxy {

    @OnlyIn(Dist.CLIENT)
    public static Player getPlayer() {
        return Minecraft.getInstance().player;
    }
}
