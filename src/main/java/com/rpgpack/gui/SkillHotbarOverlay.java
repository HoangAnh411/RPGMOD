package com.rpgpack.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import com.rpgpack.core.PlayerCapability;
import com.rpgpack.init.ModMessages;
import com.rpgpack.network.UseSkillC2S;
import com.rpgpack.skills.BaseSkill;
import com.rpgpack.skills.SkillRegistry;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = "rpgpack", bus = Mod.EventBusSubscriber.Bus.MOD)
public class SkillHotbarOverlay {

    private static final Map<String, Integer> clientCooldowns = new ConcurrentHashMap<>();

    public static void setClientCooldown(String skillId, int ticks) {
        if (ticks <= 0) {
            clientCooldowns.remove(skillId);
        } else {
            clientCooldowns.put(skillId, ticks);
        }
    }

    public static int getClientCooldown(String skillId) {
        return clientCooldowns.getOrDefault(skillId, 0);
    }

    public static void clientTick() {
        clientCooldowns.entrySet().removeIf(e -> {
            int remaining = e.getValue() - 1;
            if (remaining <= 0) return true;
            e.setValue(remaining);
            return false;
        });
    }

    public static final IGuiOverlay SKILL_HOTBAR = (gui, ps, partialTick, screenWidth, screenHeight) -> {
        var player = Minecraft.getInstance().player;
        if (player == null) return;
        if (Minecraft.getInstance().options.hideGui) return;

        var data = player.getCapability(PlayerCapability.PLAYER_DATA).orElse(null);
        if (data == null) return;
        if (data.getSelectedClass().equals("NONE")) return;

        List<BaseSkill> skills = new ArrayList<>(SkillRegistry.getAll().values());
        int skillCount = Math.min(skills.size(), 5);
        if (skillCount == 0) return;

        int barWidth = skillCount * 42 + (skillCount - 1) * 4;
        int startX = (screenWidth - barWidth) / 2;
        int y = screenHeight - 60;

        for (int i = 0; i < skillCount; i++) {
            int x = startX + i * 46;
            renderSlot(ps, x, y, skills.get(i), i + 1);
        }
    };

    private static void renderSlot(PoseStack ps, int x, int y, BaseSkill skill, int keyNum) {
        var font = Minecraft.getInstance().font;
        int cdRemaining = getClientCooldown(skill.getSkillId());
        boolean onCd = cdRemaining > 0;

        // Slot background (darker if on cooldown)
        int bgColor = onCd ? 0xCC000000 : 0x88000000;
        fill(ps, x, y, x + 42, y + 42, bgColor);

        // Border (gold if ready, grey if on cd)
        int borderColor = onCd ? 0xFF_444444 : 0xFF_888888;
        fill(ps, x, y, x + 42, y + 1, borderColor);
        fill(ps, x + 41, y, x + 42, y + 42, borderColor);
        fill(ps, x, y + 41, x + 42, y + 42, borderColor);
        fill(ps, x, y, x + 1, y + 42, borderColor);

        // Key number
        font.drawShadow(ps, String.valueOf(keyNum), x + 3, y + 2, 0xFF_FFAA00);

        // Skill name
        String name = skill.getSkillName();
        if (name.length() > 8) name = name.substring(0, 7) + ".";
        font.draw(ps, name, x + 3, y + 26, onCd ? 0x666666 : 0xFFFFFF);

        // Cooldown overlay and timer
        if (onCd) {
            float cdSeconds = cdRemaining / 20f;
            String cdText = cdSeconds >= 1f ? String.format("%.0f", cdSeconds) : String.format("%.1f", cdSeconds);
            font.drawShadow(ps, cdText, x + 3, y + 12, 0xFF_FF4444);
        }

        // Cost indicator
        String cost = "";
        if (skill.getManaCost() > 0) cost = skill.getManaCost() + " MP";
        else if (skill.getStaminaCost() > 0) cost = skill.getStaminaCost() + " SP";
        font.draw(ps, cost, x + 3, y + 34, onCd ? 0x446688 : 0x88AAFF);
    }

    @SubscribeEvent
    public static void onRegisterOverlays(RegisterGuiOverlaysEvent event) {
        event.registerAbove(VanillaGuiOverlay.HOTBAR.id(), "skill_hotbar", SKILL_HOTBAR);
    }
}
