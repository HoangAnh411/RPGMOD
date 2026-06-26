package com.rpgpack.gui;

import com.rpgpack.classes.ClassType;
import com.rpgpack.core.PlayerCapability;
import com.rpgpack.core.PlayerData;
import com.rpgpack.skills.BaseSkill;
import com.rpgpack.skills.SkillRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.*;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = "rpgpack", bus = Mod.EventBusSubscriber.Bus.MOD)
public class SkillHotbarOverlay {

    private static final Map<String, Integer> clientCooldowns = new HashMap<>();
    private static final String[] KEY_LABELS = {"R", "G", "C", "V"};
    private static final int SLOT_W = 34;
    private static final int SLOT_H = 34;
    private static final int SLOT_GAP = 3;

    public static void setClientCooldown(String skillId, int ticks) {
        if (ticks <= 0) clientCooldowns.remove(skillId);
        else clientCooldowns.put(skillId, ticks);
    }

    public static int getClientCooldown(String skillId) {
        return clientCooldowns.getOrDefault(skillId, 0);
    }

    public static void clientTick() {
        var iter = clientCooldowns.entrySet().iterator();
        while (iter.hasNext()) {
            var e = iter.next();
            int r = e.getValue() - 1;
            if (r <= 0) iter.remove();
            else e.setValue(r);
        }
    }

    private static PlayerData cachedData;
    private static Player cachedPlayer;
    private static String cachedClassName;
    private static ClassType cachedClassType;
    private static List<BaseSkill> cachedSkills;

    public static final IGuiOverlay SKILL_HOTBAR = (gui, g, partialTick, screenWidth, screenHeight) -> {
        var player = Minecraft.getInstance().player;
        if (player == null || Minecraft.getInstance().options.hideGui) return;

        if (player != cachedPlayer) {
            cachedPlayer = player;
            cachedData = null;
            cachedClassName = null;
            cachedClassType = null;
            cachedSkills = null;
        }

        var data = cachedData;
        if (data == null) {
            data = player.getCapability(PlayerCapability.PLAYER_DATA).orElse(null);
            cachedData = data;
        }
        if (data == null) return;

        String cn = data.getSelectedClass();
        if ("NONE".equals(cn)) return;

        ClassType ct;
        if (cn.equals(cachedClassName) && cachedClassType != null) {
            ct = cachedClassType;
        } else {
            try { ct = ClassType.valueOf(cn); }
            catch (IllegalArgumentException e) { return; }
            cachedClassName = cn;
            cachedClassType = ct;
            cachedSkills = SkillRegistry.getSkillsForClass(ct);
        }

        List<BaseSkill> skills = cachedSkills;
        if (skills == null || skills.isEmpty()) return;

        int count = Math.min(skills.size(), KEY_LABELS.length);
        int totalH = count * SLOT_H + (count - 1) * SLOT_GAP;
        int x = screenWidth - SLOT_W - 8;
        int startY = screenHeight - 88 - totalH;

        for (int i = 0; i < count; i++) {
            int y = startY + i * (SLOT_H + SLOT_GAP);
            renderSlot(g, x, y, skills.get(i), KEY_LABELS[i], data);
        }
    };

    private static void renderSlot(GuiGraphics g, int x, int y, BaseSkill skill, String key, PlayerData data) {
        var font = Minecraft.getInstance().font;
        int cd = getClientCooldown(skill.getSkillId());
        int rank = data.getSkillRank(skill.getSkillId());
        boolean onCd = cd > 0;
        boolean locked = data.getLevel() < skill.getUnlockLevel();

        int bg = onCd ? 0xCC_1A1A2E : (locked ? 0x88_1A1A1A : 0x88_162447);
        g.fill(x, y, x + SLOT_W, y + SLOT_H, bg);

        int borderC = onCd ? 0xFF_333355 : (locked ? 0xFF_333333 : 0xFF_5566AA);
        g.fill(x, y, x + SLOT_W, y + 1, borderC);
        g.fill(x + SLOT_W - 1, y, x + SLOT_W, y + SLOT_H, borderC);
        g.fill(x, y + SLOT_H - 1, x + SLOT_W, y + SLOT_H, borderC);
        g.fill(x, y, x + 1, y + SLOT_H, borderC);

        // Key label
        g.drawCenteredString(font, key, x + SLOT_W/2, y + 2, onCd ? 0xFF_886600 : 0xFF_FFCC00);

        // Rank badge
        String rankName = PlayerData.RANK_NAMES[rank];
        g.drawString(font, rankName, x + SLOT_W - font.width(rankName) - 2, y + 2, 0xFF_88CC88);

        // Cooldown overlay + timer
        if (onCd) {
            float pct = (float) cd / Math.max(skill.getScaledCooldown(rank), 1);
            int oh = (int) (SLOT_H * pct);
            g.fill(x, y, x + SLOT_W, y + oh, 0x88_000000);

            String cdText;
            if (cd >= 20) {
                cdText = (cd / 20) + "s";
            } else {
                float secs = cd / 20f;
                int whole = (int) secs;
                int frac = (int) (secs * 10f) % 10;
                cdText = whole + "." + frac + "s";
            }
            g.drawCenteredString(font, cdText, x + SLOT_W/2, y + SLOT_H/2 - 4, 0xFF_FF4444);
        } else if (locked) {
            g.drawCenteredString(font, "Lv" + skill.getUnlockLevel(), x + SLOT_W/2, y + SLOT_H/2 - 4, 0xFF_888888);
        }

        // Resource cost at bottom
        String cost = "";
        if (skill.getManaCost() > 0) cost = skill.getManaCost() + "M";
        if (!cost.isEmpty()) {
            boolean enough = skill.getManaCost() == 0 || data.getCurrentMana() >= skill.getManaCost();
            g.drawCenteredString(font, cost, x + SLOT_W/2, y + SLOT_H - 9,
                    enough ? 0xFF_AAAAFF : 0xFF_FF4444);
        }
    }

    @SubscribeEvent
    public static void onRegisterOverlays(RegisterGuiOverlaysEvent event) {
        event.registerAbove(VanillaGuiOverlay.HOTBAR.id(), "skill_hotbar", SKILL_HOTBAR);
    }
}
