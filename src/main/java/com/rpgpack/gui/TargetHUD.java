package com.rpgpack.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = "rpgpack", bus = Mod.EventBusSubscriber.Bus.MOD)
public class TargetHUD {

    private static LivingEntity currentTarget;
    private static LivingEntity pendingTarget;
    private static int switchTimer;
    private static final int SWITCH_THRESHOLD = 8;
    private static final double TARGET_RANGE = 30;

    @Mod.EventBusSubscriber(value = Dist.CLIENT, modid = "rpgpack")
    public static class TickHandler {
        @SubscribeEvent
        public static void onClientTick(TickEvent.ClientTickEvent event) {
            if (event.phase != TickEvent.Phase.END) return;
            var mc = Minecraft.getInstance();
            if (mc.player == null || mc.level == null) {
                currentTarget = null;
                pendingTarget = null;
                return;
            }
            // Use vanilla's pre-computed hit result — no custom raycast needed
            LivingEntity looked = null;
            if (mc.hitResult instanceof EntityHitResult entityHit
                    && entityHit.getEntity() instanceof LivingEntity living
                    && living.isAlive()
                    && living.distanceToSqr(mc.player) <= TARGET_RANGE * TARGET_RANGE) {
                looked = living;
            }

            if (looked != pendingTarget) {
                pendingTarget = looked;
                switchTimer = 0;
            } else {
                switchTimer++;
                if (switchTimer >= SWITCH_THRESHOLD) currentTarget = pendingTarget;
            }
            if (currentTarget != null && (!currentTarget.isAlive()
                    || currentTarget.distanceToSqr(mc.player) > TARGET_RANGE * TARGET_RANGE)) {
                currentTarget = null;
                pendingTarget = null;
            }
        }
    }

    public static final IGuiOverlay TARGET_HUD = (gui, g, partialTick, screenWidth, screenHeight) -> {
        var mc = Minecraft.getInstance();
        if (mc.options.hideGui) return;

        LivingEntity target = currentTarget;
        if (target == null || !target.isAlive()) return;

        float maxHp = target.getMaxHealth();
        float hp = target.getHealth();
        int level = Math.max(1, Math.round((maxHp - 18f) / 5f));
        Font font = mc.font;

        int tierVal = target.getPersistentData().getInt("rpg_tier");
        com.rpgpack.combat.MobScaler.BossTier tier = com.rpgpack.combat.MobScaler.BossTier.NONE;
        for (var bt : com.rpgpack.combat.MobScaler.BossTier.values()) {
            if (bt.tier == tierVal) { tier = bt; break; }
        }
        boolean hasTier = tier.tier >= 1;

        int pw = hasTier ? (tier.tier >= 4 ? 150 : 130) : 100;
        int ph = hasTier ? (tier.tier >= 3 ? 40 : 32) : 24;
        int px = (screenWidth - pw) / 2;
        int py = 20;

        int bg = hasTier ? (0xDD_000000 | ((tier.color & 0xFEFEFE) >> 1)) : 0xCC_111122;
        g.fill(px, py, px + pw, py + ph, bg);

        int bc = hasTier ? tier.color : 0xFF_445566;
        g.fill(px, py, px + pw, py + 1, bc);
        g.fill(px, py + ph - 1, px + pw, py + ph, bc);
        g.fill(px, py, px + 1, py + ph, bc);
        g.fill(px + pw - 1, py, px + pw, py + ph, bc);

        int cx = px + pw / 2;

        String name = target.getName().getString();
        String line1;
        if (hasTier && !tier.name.isEmpty()) {
            line1 = "[" + tier.name + "] Lv." + level + " " + name;
        } else {
            line1 = "Lv." + level + " " + name;
        }
        g.drawCenteredString(font, line1, cx, py + 3, hasTier && !tier.name.isEmpty() ? tier.color : 0xFF_FFFFFF);

        int barY = py + ph - 11;
        int barW = pw - 10;
        int barH = hasTier && tier.tier >= 3 ? 7 : 5;
        int barX = px + 5;

        g.fill(barX, barY, barX + barW, barY + barH, 0xCC_000000);
        float pct = maxHp > 0 ? Math.min(hp / maxHp, 1f) : 0f;
        int fillW = (int)((barW - 2) * pct);
        if (fillW > 0) {
            g.fill(barX + 1, barY + 1, barX + 1 + fillW, barY + barH - 1, 0xFF_DD4444);
            if (fillW > 4) g.fill(barX + 1, barY + 1, barX + 1 + fillW, barY + 1 + barH/2, 0x33_FFFFFF);
        }

        String hpText = (int)hp + " / " + (int)maxHp;
        g.drawCenteredString(font, hpText, cx, barY + 1, 0xFF_FFFFFF);
    };

    @SubscribeEvent
    public static void onRegisterOverlays(RegisterGuiOverlaysEvent event) {
        event.registerAbove(VanillaGuiOverlay.HOTBAR.id(), "rpg_target_info", TARGET_HUD);
    }
}
