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

    public static final KeyMapping SKILL_1 = new KeyMapping(
            "key.rpgpack.skill1",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_R,
            CATEGORY
    );

    public static final KeyMapping SKILL_2 = new KeyMapping(
            "key.rpgpack.skill2",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_G,
            CATEGORY
    );

    public static final KeyMapping SKILL_3 = new KeyMapping(
            "key.rpgpack.skill3",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_C,
            CATEGORY
    );

    public static final KeyMapping SKILL_4 = new KeyMapping(
            "key.rpgpack.skill4",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_V,
            CATEGORY
    );

    @SubscribeEvent
    public static void onRegisterKeyMappings(RegisterKeyMappingsEvent event) {
        event.register(CHARACTER_SCREEN);
        event.register(SKILL_1);
        event.register(SKILL_2);
        event.register(SKILL_3);
        event.register(SKILL_4);
    }
}
