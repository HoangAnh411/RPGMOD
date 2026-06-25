package com.rpgpack.init;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = "rpgpack", bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModKeybinds {

    public static final String CATEGORY = "key.categories.rpgpack";

    public static final KeyMapping CHARACTER_SCREEN = new KeyMapping(
            "key.rpgpack.character",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_P,
            CATEGORY
    );

    public static final KeyMapping SKILLS_SCREEN = new KeyMapping(
            "key.rpgpack.skills",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_K,
            CATEGORY
    );

    @SubscribeEvent
    public static void onRegisterKeyMappings(RegisterKeyMappingsEvent event) {
        event.register(CHARACTER_SCREEN);
        event.register(SKILLS_SCREEN);
    }
}
