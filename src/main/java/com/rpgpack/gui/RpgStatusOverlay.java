package com.rpgpack.gui;

import com.rpgpack.core.PlayerCapability;
import com.rpgpack.core.PlayerData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class RpgStatusOverlay {

    private static final int BAR_WIDTH = 65;
    private static final int BAR_HEIGHT = 7;
    private static final int BAR_GAP = 2;
    private static final int MARGIN = 10;

    private static PlayerData cachedData;
    private static net.minecraft.world.entity.player.Player cachedPlayer;

    public static final IGuiOverlay RPG_BARS = (gui, g, partialTick, screenWidth, screenHeight) -> {
        var player = Minecraft.getInstance().player;
        if (player == null) return;
        if (Minecraft.getInstance().options.hideGui) return;

        if (player != cachedPlayer) {
            cachedPlayer = player;
            cachedData = null;
        }

        var data = cachedData;
        if (data == null) {
            data = player.getCapability(PlayerCapability.PLAYER_DATA).orElse(null);
            cachedData = data;
        }
        if (data == null) return;
        if ("NONE".equals(data.getSelectedClass())) return;

        var stats = data.getCachedStats(player);
        int barTotalH = (BAR_HEIGHT + BAR_GAP) * 2;
        int x = MARGIN;
        int y = screenHeight - barTotalH - MARGIN;

        renderBar(g, x, y, BAR_WIDTH, BAR_HEIGHT,
                player.getHealth(), player.getMaxHealth(),
                0xFF_CC3333, "❤", 0xFF_661111);
        y += BAR_HEIGHT + BAR_GAP;

        renderBar(g, x, y, BAR_WIDTH, BAR_HEIGHT,
                data.getCurrentMana(), stats.maxMana,
                0xFF_3366CC, "✦", 0xFF_112244);
    };

    private static void renderBar(GuiGraphics g, int x, int y, int w, int h,
                                   float current, float max, int color, String icon, int bgColor) {
        var font = Minecraft.getInstance().font;

        // Background
        g.fill(x, y, x + w, y + h, bgColor);

        // Fill
        float pct = max > 0 ? Math.min(current / max, 1f) : 0f;
        int fillW = (int) ((w - 2) * pct);
        if (fillW == 0 && current > 0) fillW = 1; // always show at least 1px when alive
        if (fillW > 0) {
            g.fill(x + 1, y + 1, x + 1 + fillW, y + h - 1, color);
            if (fillW > 4) g.fill(x + 1, y + 1, x + 1 + fillW, y + 1 + h / 2, 0x33_FFFFFF);
        }

        // Border
        g.fill(x, y, x + w, y + 1, 0xFF_333333);
        g.fill(x, y + h - 1, x + w, y + h, 0xFF_333333);
        g.fill(x, y, x + 1, y + h, 0xFF_333333);
        g.fill(x + w - 1, y, x + w, y + h, 0xFF_333333);

        // Icon
        g.drawString(font, icon, x + 2, y, 0xFF_FFFFFF);

        // Text centered with shadow
        String text = (int) current + " / " + (int) max;
        int textW = font.width(text);
        int textX = x + (w - textW) / 2;
        g.drawString(font, text, textX + 1, y, 0xFF_000000);
        g.drawString(font, text, textX, y, 0xFF_FFFFFF);
    }

    @Mod.EventBusSubscriber(value = Dist.CLIENT, modid = "rpgpack", bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModBusHandler {
        @SubscribeEvent
        public static void onRegisterOverlays(RegisterGuiOverlaysEvent event) {
            event.registerAbove(VanillaGuiOverlay.HOTBAR.id(), "rpg_status_bars", RPG_BARS);
        }
    }

    @Mod.EventBusSubscriber(value = Dist.CLIENT, modid = "rpgpack")
    public static class ForgeBusHandler {
        @SubscribeEvent
        public static void onRenderOverlay(RenderGuiOverlayEvent.Pre event) {
            var id = event.getOverlay().id();
            if (id.equals(VanillaGuiOverlay.PLAYER_HEALTH.id())
                    || id.equals(VanillaGuiOverlay.ARMOR_LEVEL.id())
                    || id.equals(VanillaGuiOverlay.EXPERIENCE_BAR.id())) {
                event.setCanceled(true);
            }
        }
    }
}
