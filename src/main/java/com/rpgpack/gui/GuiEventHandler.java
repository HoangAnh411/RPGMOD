package com.rpgpack.gui;

import com.rpgpack.init.ModKeybinds;
import com.rpgpack.init.ModMessages;
import com.rpgpack.network.UseSkillC2S;
import com.rpgpack.skills.SkillRegistry;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = "rpgpack")
public class GuiEventHandler {

    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {
        var client = Minecraft.getInstance();
        if (client.player == null || client.screen != null) return;

        // Character screen: P
        if (ModKeybinds.CHARACTER_SCREEN.consumeClick()) {
            client.setScreen(new CharacterScreen());
            return;
        }

        // Skill hotkeys: 1-5
        int key = event.getKey();
        if (key >= GLFW.GLFW_KEY_1 && key <= GLFW.GLFW_KEY_5) {
            int index = key - GLFW.GLFW_KEY_1;
            var skills = SkillRegistry.getAll().values().stream().toList();
            if (index < skills.size()) {
                String skillId = skills.get(index).getSkillId();
                ModMessages.CHANNEL.sendToServer(new UseSkillC2S(skillId));
            }
        }
    }

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            SkillHotbarOverlay.clientTick();
        }
    }
}
