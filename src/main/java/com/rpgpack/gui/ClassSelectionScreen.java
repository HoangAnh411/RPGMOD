package com.rpgpack.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.rpgpack.classes.ClassType;
import com.rpgpack.init.ModMessages;
import com.rpgpack.network.ChooseClassC2S;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ClassSelectionScreen extends Screen {

    private static final int CARD_WIDTH = 160;
    private static final int CARD_HEIGHT = 220;
    private static final int CARD_GAP = 12;
    private static final int PANEL_TOP = 45;

    private final List<ClassCard> cards = new ArrayList<>();
    private ClassCard selected;

    public ClassSelectionScreen() {
        super(Component.literal("Select Your Class"));
    }

    @Override
    protected void init() {
        super.init();
        this.cards.clear();
        this.selected = null;

        ClassType[] types = {ClassType.WARRIOR, ClassType.BERSERKER, ClassType.ASSASSIN, ClassType.MAGE};
        int totalWidth = types.length * CARD_WIDTH + (types.length - 1) * CARD_GAP;
        int startX = (this.width - totalWidth) / 2;

        for (int i = 0; i < types.length; i++) {
            int x = startX + i * (CARD_WIDTH + CARD_GAP);
            cards.add(new ClassCard(types[i], x, PANEL_TOP));
        }

        int btnY = PANEL_TOP + CARD_HEIGHT + 20;
        addRenderableWidget(
                Button.builder(Component.literal("Confirm"), this::onConfirm)
                        .pos(this.width / 2 - 100, btnY)
                        .size(200, 20)
                        .build()
        );
    }

    private void onConfirm(Button btn) {
        if (selected == null) return;

        ModMessages.CHANNEL.sendToServer(new ChooseClassC2S(selected.type.name()));
        this.onClose();
    }

    @Override
    public void render(@NotNull PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
        renderBackground(poseStack);

        drawCenteredString(poseStack, this.font, this.title, this.width / 2, 12, 0xFFFFFF);
        drawCenteredString(poseStack, this.font, "Choose one — cannot be changed for free later",
                this.width / 2, 26, 0xAAAAAA);

        for (ClassCard card : cards) {
            card.render(poseStack, mouseX, mouseY, this.font);
        }

        super.render(poseStack, mouseX, mouseY, partialTick);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        for (ClassCard card : cards) {
            if (card.isHovered((int) mouseX, (int) mouseY)) {
                this.selected = card;
                return true;
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    private class ClassCard {
        final ClassType type;
        final int x, y;

        ClassCard(ClassType type, int x, int y) {
            this.type = type;
            this.x = x;
            this.y = y;
        }

        boolean isHovered(int mx, int my) {
            return mx >= x && mx <= x + CARD_WIDTH && my >= y && my <= y + CARD_HEIGHT;
        }

        void render(PoseStack poseStack, int mx, int my, net.minecraft.client.gui.Font font) {
            boolean hovered = isHovered(mx, my);
            boolean isSel = this == selected;

            int borderColor = isSel ? 0xFF_FFD700 : (hovered ? 0xFF_AAAAAA : 0xFF_555555);
            int bgColor = isSel ? 0x44_3A3A1A : (hovered ? 0x44_333333 : 0x44_111111);

            // Card background
            fill(poseStack, x, y, x + CARD_WIDTH, y + CARD_HEIGHT, bgColor);
            // Border
            fill(poseStack, x, y, x + CARD_WIDTH, y + 1, borderColor);
            fill(poseStack, x, y + CARD_HEIGHT - 1, x + CARD_WIDTH, y + CARD_HEIGHT, borderColor);
            fill(poseStack, x, y, x + 1, y + CARD_HEIGHT, borderColor);
            fill(poseStack, x + CARD_WIDTH - 1, y, x + CARD_WIDTH, y + CARD_HEIGHT, borderColor);

            int cy = y + 10;

            // Class name
            drawCenteredString(poseStack, font, type.getDisplayName(), x + CARD_WIDTH / 2, cy, 0xFF_FFAA00);
            cy += 14;

            // Role
            String role = getRole(type);
            drawCenteredString(poseStack, font, role, x + CARD_WIDTH / 2, cy, 0xCCCCCC);
            cy += 16;

            // Separator
            fill(poseStack, x + 20, cy, x + CARD_WIDTH - 20, cy + 1, 0xFF_444444);
            cy += 8;

            // Base stats
            int[][] stats = getStats(type);
            String[] labels = {"STR", "VIT", "END", "AGI", "DEX", "INT", "WIS", "LUK"};
            for (int i = 0; i < stats.length; i++) {
                String line = labels[i] + ": " + stats[i][0];
                drawString(poseStack, font, line, x + 14, cy, 0xDDDDDD);
                cy += 11;
            }

            cy += 4;
            fill(poseStack, x + 20, cy, x + CARD_WIDTH - 20, cy + 1, 0xFF_444444);
            cy += 8;

            // Playstyle hint
            String hint = getPlaystyleHint(type);
            drawCenteredString(poseStack, font, hint, x + CARD_WIDTH / 2, cy, 0xFF_88AAFF);
        }

        private String getRole(ClassType t) {
            return switch (t) {
                case WARRIOR -> "Tank / Frontliner";
                case BERSERKER -> "Heavy Melee DPS";
                case ASSASSIN -> "Burst DPS / Crit";
                case MAGE -> "Caster / Elemental";
                default -> "";
            };
        }

        private String getPlaystyleHint(ClassType t) {
            return switch (t) {
                case WARRIOR -> "Soak damage, protect allies";
                case BERSERKER -> "Big hits, AOE destruction";
                case ASSASSIN -> "Fast strikes, backstab crits";
                case MAGE -> "Ranged magic, reaction combos";
                default -> "";
            };
        }

        private int[][] getStats(ClassType t) {
            return new int[][]{
                    {t.getBaseStr()}, {t.getBaseVit()}, {t.getBaseEnd()}, {t.getBaseAgi()},
                    {t.getBaseDex()}, {t.getBaseInt()}, {t.getBaseWis()}, {t.getBaseLuk()}
            };
        }
    }
}
