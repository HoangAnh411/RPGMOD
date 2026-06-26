package com.rpgpack.gui;

import com.rpgpack.RPGPack;
import com.rpgpack.classes.ClassType;
import com.rpgpack.init.ModMessages;
import com.rpgpack.network.ChooseClassC2S;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ClassSelectionScreen extends Screen {

    private static final int MAX_CARD_WIDTH = 160;
    private static final int CARD_GAP = 10;
    private static final int PANEL_TOP = 50;

    private final List<ClassCard> cards = new ArrayList<>();
    private ClassCard selected;
    private Button confirmBtn;

    public ClassSelectionScreen() {
        super(Component.literal("Select Your Class"));
    }

    @Override
    protected void init() {
        super.init();
        this.cards.clear();
        this.selected = null;

        ClassType[] types = {ClassType.WARRIOR, ClassType.BERSERKER, ClassType.ASSASSIN, ClassType.MAGE, ClassType.CLERIC};

        // Responsive card sizing: fit 4 cards in available width
        int availableWidth = this.width - 40; // 20px padding each side
        int cardWidth = Math.min(MAX_CARD_WIDTH, (availableWidth - (types.length - 1) * CARD_GAP) / types.length);
        int totalWidth = types.length * cardWidth + (types.length - 1) * CARD_GAP;
        int startX = (this.width - totalWidth) / 2;

        // Card height scales with width (maintain roughly 1:1.4 ratio)
        int cardHeight = (int) (cardWidth * 1.35);

        for (int i = 0; i < types.length; i++) {
            int x = startX + i * (cardWidth + CARD_GAP);
            cards.add(new ClassCard(types[i], x, PANEL_TOP, cardWidth, cardHeight));
        }

        // Confirm button — only enabled after selection
        int btnY = PANEL_TOP + cardHeight + 16;
        confirmBtn = Button.builder(Component.literal("CONFIRM"), this::onConfirm)
                .pos(this.width / 2 - 100, btnY)
                .size(200, 22)
                .build();
        confirmBtn.active = false;
        addRenderableWidget(confirmBtn);

        RPGPack.LOGGER.info("[CLASS] Open GUI");
    }

    private void onConfirm(Button btn) {
        if (selected == null) return;

        RPGPack.LOGGER.info("[CLASS] Selected " + selected.type.getDisplayName());
        RPGPack.LOGGER.info("[CLASS] Packet sent");
        ModMessages.CHANNEL.sendToServer(new ChooseClassC2S(selected.type.name()));
        this.onClose();
    }

    @Override
    public void render(@NotNull GuiGraphics g, int mouseX, int mouseY, float partialTick) {
        renderBackground(g);

        // Title
        g.drawCenteredString(this.font, this.title, this.width / 2, 10, 0xFF_FFAA00);
        g.drawCenteredString(this.font, "Choose one — cannot be changed for free later",
                this.width / 2, 24, 0xFF_AAAAAA);

        for (ClassCard card : cards) {
            card.render(g, mouseX, mouseY, this.font);
        }

        // Hint text
        if (selected == null) {
            g.drawCenteredString(this.font, "Click a card to select your class",
                    this.width / 2, this.height - 20, 0xFF_888888);
        }

        super.render(g, mouseX, mouseY, partialTick);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        for (ClassCard card : cards) {
            if (card.isHovered((int) mouseX, (int) mouseY)) {
                this.selected = card;
                this.confirmBtn.active = true;
                RPGPack.LOGGER.info("[CLASS] Highlighted " + card.type.getDisplayName());
                return true;
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    private class ClassCard {
        final ClassType type;
        final int x, y, width, height;

        ClassCard(ClassType type, int x, int y, int width, int height) {
            this.type = type;
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }

        boolean isHovered(int mx, int my) {
            return mx >= x && mx <= x + width && my >= y && my <= y + height;
        }

        void render(GuiGraphics g, int mx, int my, net.minecraft.client.gui.Font font) {
            boolean hovered = isHovered(mx, my);
            boolean isSel = this == selected;

            // Color scheme based on selection state
            int borderColor;
            int bgColor;
            int titleColor;
            if (isSel) {
                borderColor = 0xFF_FFD700;      // gold
                bgColor = 0x66_2A2A00;           // dark gold bg
                titleColor = 0xFF_FFCC00;        // bright gold
            } else if (hovered) {
                borderColor = 0xFF_CCCCCC;       // light grey
                bgColor = 0x66_1A1A1A;           // lighter bg
                titleColor = 0xFF_DDDDDD;        // bright white
            } else {
                borderColor = 0xFF_555555;       // dim
                bgColor = 0x88_0A0A0A;           // dark bg
                titleColor = 0xFF_AAAAAA;        // grey
            }

            // Card background with rounded-rect look via fill
            g.fill(x, y, x + width, y + height, bgColor);
            // Border — top, bottom, left, right
            g.fill(x, y, x + width, y + 2, borderColor);
            g.fill(x, y + height - 2, x + width, y + height, borderColor);
            g.fill(x, y, x + 2, y + height, borderColor);
            g.fill(x + width - 2, y, x + width, y + height, borderColor);

            int padding = (int) (width * 0.08);
            int cy = y + 10;

            // Class name — larger
            g.drawCenteredString(font, type.getDisplayName(), x + width / 2, cy, titleColor);
            cy += 14;

            // Role
            g.drawCenteredString(font, getRole(type), x + width / 2, cy, 0xFF_CCCCCC);
            cy += 16;

            // Separator
            g.fill(x + padding, cy, x + width - padding, cy + 1, 0xFF_444444);
            cy += 7;

            // Base stats — use scaled font size
            int[][] stats = getStats(type);
            String[] labels = {"STR", "VIT", "END", "AGI", "DEX", "INT", "WIS", "LUK"};
            int lineH = Math.max(9, Math.min(11, height / 22));
            for (int i = 0; i < stats.length; i++) {
                String line = labels[i] + ": " + stats[i][0];
                g.drawString(font, line, x + padding, cy, 0xFF_DDDDDD);
                cy += lineH;
            }

            cy += 4;
            g.fill(x + padding, cy, x + width - padding, cy + 1, 0xFF_444444);
            cy += 8;

            // Playstyle hint
            g.drawCenteredString(font, getPlaystyleHint(type), x + width / 2, cy, 0xFF_88AAFF);

            // Selected checkmark indicator
            if (isSel) {
                g.drawCenteredString(font, "✓ SELECTED", x + width / 2, y + height - 14, 0xFF_FFD700);
            }
        }

        private String getRole(ClassType t) {
            return switch (t) {
                case WARRIOR -> "Tank / Frontliner";
                case BERSERKER -> "Heavy Melee DPS";
                case ASSASSIN -> "Burst DPS / Crit";
                case MAGE -> "Caster / Elemental";
                case CLERIC -> "Support / Healer";
                default -> "";
            };
        }

        private String getPlaystyleHint(ClassType t) {
            return switch (t) {
                case WARRIOR -> "Soak damage, protect allies";
                case BERSERKER -> "Big hits, AOE destruction";
                case ASSASSIN -> "Fast strikes, backstab crits";
                case MAGE -> "Ranged magic, reaction combos";
                case CLERIC -> "Heal allies, buff party";
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
