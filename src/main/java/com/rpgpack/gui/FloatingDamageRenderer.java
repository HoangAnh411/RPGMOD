package com.rpgpack.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = "rpgpack")
public class FloatingDamageRenderer {

    private static final int LIFETIME = 30;
    private static final int MAX_ENTRIES = 200;

    // Mutable entry — no record allocation per tick
    private static class Entry {
        float amount;
        byte type;
        double x, y, z;
        int age;
        float offsetX;

        Entry(float amount, byte type, double x, double y, double z, float offsetX) {
            this.amount = amount;
            this.type = type;
            this.x = x;
            this.y = y;
            this.z = z;
            this.age = 0;
            this.offsetX = offsetX;
        }
    }

    private static final List<Entry> entries = new ArrayList<>();
    private static final Random RNG = new Random();

    // Temporary array for snapshot — allocated once, resized as needed
    private static Entry[] snapshot = new Entry[0];

    public static void add(int entityId, float amount, byte type, double x, double y, double z) {
        if (entries.size() >= MAX_ENTRIES) entries.remove(0);
        entries.add(new Entry(amount, type, x, y, z,
                (RNG.nextFloat() - 0.5f) * 0.6f));
    }

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        int i = 0;
        while (i < entries.size()) {
            var e = entries.get(i);
            e.age++;
            e.y += 0.04;
            if (e.age >= LIFETIME) {
                int last = entries.size() - 1;
                entries.set(i, entries.get(last));
                entries.remove(last);
            } else {
                i++;
            }
        }
    }

    @SubscribeEvent
    public static void onRenderLevel(RenderLevelStageEvent event) {
        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_TRANSLUCENT_BLOCKS) return;

        var mc = Minecraft.getInstance();
        if (mc.player == null || entries.isEmpty()) return;

        Camera cam = mc.gameRenderer.getMainCamera();
        PoseStack pose = event.getPoseStack();
        MultiBufferSource.BufferSource bufs = mc.renderBuffers().bufferSource();
        Font font = mc.font;

        // Snapshot entries into array without allocating new list
        int size = entries.size();
        if (snapshot.length < size) snapshot = new Entry[size];
        for (int i = 0; i < size; i++) snapshot[i] = entries.get(i);

        RenderSystem.depthMask(false);

        pose.pushPose();
        var camPos = cam.getPosition();
        pose.translate(-camPos.x, -camPos.y, -camPos.z);

        for (int i = 0; i < size; i++) {
            var entry = snapshot[i];
            double dx = entry.x - camPos.x;
            double dy = entry.y - camPos.y;
            double dz = entry.z - camPos.z;
            if (dx * dx + dy * dy + dz * dz > 900) continue;

            renderEntry(pose, bufs, font, cam, entry);
        }

        pose.popPose();
        bufs.endBatch();
        RenderSystem.depthMask(true);
    }

    private static void renderEntry(PoseStack pose, MultiBufferSource.BufferSource bufs,
                                     Font font, Camera cam, Entry entry) {
        pose.pushPose();
        pose.translate(entry.x + entry.offsetX, entry.y, entry.z);

        pose.mulPose(cam.rotation());

        float scale = 0.04f;
        pose.scale(-scale, -scale, scale);

        float alpha = 1f - (float) entry.age / LIFETIME;
        int color = getColor(entry.type, alpha);
        String text = formatText(entry.amount, entry.type);

        int textW = font.width(text);
        font.drawInBatch(text, -textW / 2f, 0f, color, false,
                pose.last().pose(), bufs, Font.DisplayMode.SEE_THROUGH, 0, 0xF000F0);

        pose.popPose();
    }

    private static int getColor(byte type, float alpha) {
        int a = Math.max(0, Math.min(255, (int)(alpha * 255))) << 24;
        return switch (type) {
            case 1 -> a | 0x00FFDD00;
            case 2 -> a | 0x0000FF44;
            case 3 -> a | 0x004488FF;
            case 4 -> a | 0x00888888;
            default -> a | 0x00FFFFFF;
        };
    }

    private static String formatText(float amount, byte type) {
        return switch (type) {
            case 1 -> (int)amount + "!";
            case 2 -> "+" + (int)amount;
            case 3 -> "DODGE";
            case 4 -> "MISS";
            default -> amount < 1 ? String.format("%.1f", amount) : String.valueOf((int)amount);
        };
    }
}
