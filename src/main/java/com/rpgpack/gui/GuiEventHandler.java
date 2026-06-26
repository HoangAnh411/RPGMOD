package com.rpgpack.gui;

import com.rpgpack.RPGPack;
import com.rpgpack.classes.ClassType;
import com.rpgpack.core.PlayerCapability;
import com.rpgpack.init.ModKeybinds;
import com.rpgpack.init.ModMessages;
import com.rpgpack.network.UseSkillC2S;
import com.rpgpack.skills.BaseSkill;
import com.rpgpack.skills.SkillRegistry;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

import java.util.List;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = "rpgpack")
public class GuiEventHandler {

    private static final KeyMapping[] SKILL_KEYS = {
        ModKeybinds.SKILL_1, ModKeybinds.SKILL_2,
        ModKeybinds.SKILL_3, ModKeybinds.SKILL_4
    };
    private static int castCooldown = 0;

    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {
        var client = Minecraft.getInstance();
        if (client.player == null || client.screen != null) return;

        // Character screen: P
        if (ModKeybinds.CHARACTER_SCREEN.consumeClick()) {
            client.setScreen(new CharacterScreen());
            return;
        }

        // Only handle on PRESS (not repeat or release)
        if (event.getAction() != GLFW.GLFW_PRESS) return;
        if (castCooldown > 0) return;

        int index = -1;
        for (int i = 0; i < SKILL_KEYS.length; i++) {
            if (event.getKey() == SKILL_KEYS[i].getKey().getValue()) { index = i; break; }
        }
        if (index < 0) return;

        var data = client.player.getCapability(PlayerCapability.PLAYER_DATA).orElse(null);
        if (data == null) return;
        String className = data.getSelectedClass();
        if ("NONE".equals(className)) return;

        ClassType classType;
        try { classType = ClassType.valueOf(className); }
        catch (IllegalArgumentException e) { return; }

        List<BaseSkill> skills = SkillRegistry.getSkillsForClass(classType);
        if (index >= skills.size()) return;

        BaseSkill skill = skills.get(index);

        // Unlock level check
        if (data.getLevel() < skill.getUnlockLevel()) {
            client.player.displayClientMessage(
                    Component.literal("§cRequires Level " + skill.getUnlockLevel() + "!").withStyle(s -> s.withBold(true)), true);
            return;
        }

        if (skill.getManaCost() > 0 && data.getCurrentMana() < skill.getManaCost()) {
            client.player.displayClientMessage(
                    Component.literal("§cNot enough Mana!").withStyle(s -> s.withBold(true)), true);
            return;
        }

        RPGPack.LOGGER.info("[SKILL] {} -> {}", (char)('R' + index), skill.getSkillId());
        ModMessages.CHANNEL.sendToServer(new UseSkillC2S(skill.getSkillId()));
        castCooldown = 4;
    }

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            if (castCooldown > 0) castCooldown--;
            SkillHotbarOverlay.clientTick();
        }
    }
}
