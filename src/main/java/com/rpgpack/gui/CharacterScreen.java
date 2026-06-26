package com.rpgpack.gui;

import com.rpgpack.core.PlayerCapability;
import com.rpgpack.core.PlayerData;
import com.rpgpack.core.DerivedStats;
import com.rpgpack.init.ModMessages;
import com.rpgpack.network.AddStatPointC2S;
import com.rpgpack.network.RankUpC2S;
import com.rpgpack.skills.BaseSkill;
import com.rpgpack.skills.SkillRegistry;
import com.rpgpack.classes.ClassType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CharacterScreen extends Screen {

    private static final int LEFT_PANEL = 20;
    private static final int TOP = 28;
    private static final int STAT_ROW = 16;
    private static final int DERIVED_ROW = 11;
    private static final int TAB_W = 70;
    private static final int TAB_H = 18;

    private static final String[] STAT_NAMES = {"STR", "VIT", "END", "AGI", "DEX", "INT", "WIS", "LUK"};

    private int scrollOffset;
    private int selectedTab; // 0=Character, 1=Skills
    private final java.util.List<int[]> clickZones = new java.util.ArrayList<>();
    // clickZones: {x, y, w, h, actionType, ...data}
    // actionType: 0=stat+, 1=skillUpgrade, 2=tabCharacter, 3=tabSkills

    public CharacterScreen() {
        super(Component.literal("Character"));
    }

    private void allocateStat(String statName) {
        ModMessages.CHANNEL.sendToServer(new AddStatPointC2S(statName));
    }

    private void rankUpSkill(String skillId) {
        ModMessages.CHANNEL.sendToServer(new RankUpC2S(skillId));
    }

    @Override
    protected void init() {
        super.init();
    }

    @Override
    public void render(@NotNull GuiGraphics g, int mouseX, int mouseY, float partialTick) {
        renderBackground(g);
        var player = Minecraft.getInstance().player;
        if (player == null) return;

        PlayerData data = player.getCapability(PlayerCapability.PLAYER_DATA).orElse(null);
        if (data == null) return;

        DerivedStats derived = data.getCachedStats(player);
        clickZones.clear();

        // === TITLE + TABS ===
        g.drawCenteredString(this.font, "CHARACTER", this.width / 2, 6, 0xFF_FFAA00);
        int tabY = 20;
        int tabLeft = this.width / 2 - TAB_W;
        drawTab(g, tabLeft, tabY, TAB_W, TAB_H, "Character", 0, selectedTab == 0, mouseX, mouseY);
        drawTab(g, tabLeft + TAB_W + 2, tabY, TAB_W, TAB_H, "Skills", 1, selectedTab == 1, mouseX, mouseY);

        // === XP BAR (always visible) ===
        int xpY = tabY + TAB_H + 4;
        int xpX = LEFT_PANEL;
        int xpW = this.width - LEFT_PANEL * 2;
        int xpH = 10;
        drawXpBar(g, xpX, xpY, xpW, xpH, data);

        int contentStartY = xpY + xpH + 6;
        int panelBottom = this.height - 4;

        if (selectedTab == 0) {
            renderCharacterTab(g, player, data, derived, mouseX, mouseY, contentStartY, panelBottom);
        } else {
            renderSkillsTab(g, data, derived, mouseX, mouseY, contentStartY, panelBottom);
        }

        super.render(g, mouseX, mouseY, partialTick);
    }

    private void drawTab(GuiGraphics g, int x, int y, int w, int h, String label, int tabId,
                          boolean active, int mouseX, int mouseY) {
        int bg = active ? 0xCC_334466 : 0x88_222233;
        g.fill(x, y, x + w, y + h, bg);
        g.fill(x, y, x + w, y + 1, 0xFF_5577AA);
        g.fill(x, y + h - 1, x + w, y + h, active ? 0xFF_5577AA : 0xFF_333355);
        g.drawCenteredString(this.font, label, x + w / 2, y + (h - 8) / 2, active ? 0xFF_FFCC00 : 0xFF_888888);

        boolean hovered = mouseX >= x && mouseX < x + w && mouseY >= y && mouseY < y + h;
        if (hovered && !active) g.fill(x, y, x + w, y + h, 0x22_FFFFFF);

        clickZones.add(new int[]{x, y, w, h, 2 + tabId}); // action 2=tabChar, 3=tabSkills
    }

    private void drawXpBar(GuiGraphics g, int x, int y, int w, int h, PlayerData data) {
        int lv = data.getLevel();
        int xp = data.getExperience();
        int need = data.getExperienceToNextLevel();
        float pct = need > 0 ? Math.min((float) xp / need, 1f) : 1f;

        // Background
        g.fill(x, y, x + w, y + h, 0xCC_000000);

        // Fill
        int fillW = (int) ((w - 2) * pct);
        if (fillW > 0) {
            g.fill(x + 1, y + 1, x + 1 + fillW, y + h - 1, 0xFF_44AA44);
            g.fill(x + 1, y + 1, x + 1 + fillW, y + 1 + h / 2, 0x44_FFFFFF);
        }

        // Border
        g.fill(x, y, x + w, y + 1, 0xFF_555555);
        g.fill(x, y + h - 1, x + w, y + h, 0xFF_555555);
        g.fill(x, y, x + 1, y + h, 0xFF_555555);
        g.fill(x + w - 1, y, x + w, y + h, 0xFF_555555);

        // Text
        String text = "LV " + lv + "  " + xp + " / " + need + " XP";
        g.drawCenteredString(this.font, text, x + w / 2, y + 1, 0xFF_FFFFFF);
    }

    // === CHARACTER TAB ===
    private void renderCharacterTab(GuiGraphics g, net.minecraft.world.entity.player.Player player,
                                     PlayerData data, DerivedStats derived,
                                     int mouseX, int mouseY, int contentTop, int panelBottom) {
        int y = contentTop;
        g.drawString(this.font, "Class: " + data.getSelectedClass(), LEFT_PANEL, y, 0xFF_FFCC88);
        g.drawString(this.font, "Vision: " + data.getCurrentVision(), LEFT_PANEL + 100, y, 0xFF_88CCFF);
        y += DERIVED_ROW;
        g.drawString(this.font, "Stat Points: " + data.getStatPoints(), LEFT_PANEL, y, 0xFF_FFDD88);
        y += DERIVED_ROW + 2;

        int maxLines = Math.max(STAT_NAMES.length, 15);
        int contentH = maxLines * STAT_ROW + 16;
        int visibleH = panelBottom - y;
        int maxScroll = Math.max(0, contentH - visibleH);
        if (scrollOffset > maxScroll) scrollOffset = maxScroll;
        if (scrollOffset < 0) scrollOffset = 0;

        int drawBaseY = y - scrollOffset;
        g.drawString(this.font, "BASE STATS", LEFT_PANEL, drawBaseY, 0xFF_CC44);
        g.drawString(this.font, "DERIVED STATS", this.width - 130, drawBaseY, 0xFF_CC44);
        int statStartY = drawBaseY + DERIVED_ROW + 2;

        // Left: Base Stats (with item bonus shown)
        String[] descs = {"Phys DMG", "Max HP+DEF", "HP+Mana+DEF", "Speed+AtkSpd",
                "Crit%+CritDMG", "Magic DMG", "CDR+ManaReg", "Loot+Craft (utility)"};
        int[] values = {data.getStr(), data.getVit(), data.getEnd(), data.getAgi(),
                data.getDex(), data.getInt(), data.getWis(), data.getLuk()};
        float[] bonuses = {data.getBonusStr(), data.getBonusVit(), data.getBonusEnd(), data.getBonusAgi(),
                data.getBonusDex(), data.getBonusInt(), data.getBonusWis(), data.getBonusLuk()};

        for (int i = 0; i < STAT_NAMES.length; i++) {
            int rowY = statStartY + i * STAT_ROW;
            if (rowY + STAT_ROW < contentTop || rowY > panelBottom) continue;
            String line = STAT_NAMES[i] + ": " + values[i];
            if (bonuses[i] > 0) line += " §a(+" + (int)bonuses[i] + ")";
            g.drawString(this.font, line, LEFT_PANEL, rowY, 0xFF_DDDDDD);
            g.drawString(this.font, descs[i], LEFT_PANEL + 80, rowY, 0xFF_666666);

            if (data.getStatPoints() > 0) {
                int bx = LEFT_PANEL + 155, by = rowY - 2, bw = 14, bh = 14;
                boolean hovered = mouseX >= bx && mouseX < bx + bw && mouseY >= by && mouseY < by + bh;
                g.fill(bx, by, bx + bw, by + bh, hovered ? 0xFF_88AA44 : 0xFF_446622);
                borderBox(g, bx, by, bw, bh);
                g.drawCenteredString(this.font, "+", bx + bw / 2, by + 1, 0xFF_FFFFFF);
                clickZones.add(new int[]{bx, by, bw, bh, 0, i});
            }
        }

        // Right: Derived Stats
        String[][] derivedLines = {
                {"HP", String.format("%.0f / %.0f", player.getHealth(), player.getMaxHealth())},
                {"Mana", String.format("%.0f / %.0f", data.getCurrentMana(), derived.maxMana)},
                {"Phys DMG", String.format("%.1f", derived.physicalDamage)},
                {"Magic DMG", String.format("%.1f", derived.magicDamage)},
                {"Phys DEF", String.format("%.1f", derived.physicalDefense)},
                {"Magic DEF", String.format("%.1f", derived.magicDefense)},
                {"Crit Chc", String.format("%.1f%%", derived.critChance)},
                {"Crit DMG", String.format("%.1f%%", derived.critDamage)},
                {"Atk Spd", String.format("%.1f%%", derived.attackSpeed)},
                {"Mov Spd", String.format("%.1f%%", derived.moveSpeed)},
                {"CDR", String.format("%.1f%%", derived.cooldownReduction)},
                {"Elem+", String.format("%.1f%%", derived.elementalBonus)},
                {"Mana/s", String.format("%.2f", derived.manaRegen)},
        };

        int rx = this.width - 130;
        for (int i = 0; i < derivedLines.length; i++) {
            int rowY = statStartY + i * STAT_ROW;
            if (rowY + STAT_ROW < contentTop || rowY > panelBottom) continue;
            String[] line = derivedLines[i];
            g.drawString(this.font, line[0], rx - 60, rowY, 0xFF_AAAAAA);
            g.drawString(this.font, line[1], rx, rowY, 0xFF_FFFFFF);
        }

        if (maxScroll > 0) {
            String hint = scrollOffset >= maxScroll ? "▲" : (scrollOffset <= 0 ? "▼" : "▼▲");
            g.drawString(this.font, hint, this.width / 2 - 8, panelBottom - 8, 0xFF_888888);
        }
    }

    // === SKILLS TAB ===
    private void renderSkillsTab(GuiGraphics g, PlayerData data, DerivedStats derived,
                                  int mouseX, int mouseY, int contentTop, int panelBottom) {
        String className = data.getSelectedClass();
        if ("NONE".equals(className)) {
            g.drawCenteredString(this.font, "Select a class first!", this.width / 2, contentTop + 20, 0xFF_FF4444);
            return;
        }

        ClassType classType;
        try { classType = ClassType.valueOf(className); }
        catch (IllegalArgumentException e) { return; }

        List<BaseSkill> skills = SkillRegistry.getSkillsForClass(classType);
        if (skills.isEmpty()) return;

        String[] keyLabels = {"R", "G", "C", "V"};
        int cardH = 52;
        int cardGap = 4;
        int totalH = skills.size() * (cardH + cardGap);
        int maxScroll = Math.max(0, totalH - (panelBottom - contentTop));
        if (scrollOffset > maxScroll) scrollOffset = maxScroll;
        if (scrollOffset < 0) scrollOffset = 0;

        int drawY = contentTop - scrollOffset;

        for (int i = 0; i < skills.size() && i < keyLabels.length; i++) {
            BaseSkill skill = skills.get(i);
            int cardY = drawY + i * (cardH + cardGap);
            if (cardY + cardH < contentTop || cardY > panelBottom) continue;
            drawSkillCard(g, LEFT_PANEL, cardY, this.width - LEFT_PANEL * 2, cardH,
                    skill, keyLabels[i], data, mouseX, mouseY, i);
        }

        if (maxScroll > 0) {
            String hint = scrollOffset >= maxScroll ? "▲" : (scrollOffset <= 0 ? "▼" : "▼▲");
            g.drawString(this.font, hint, this.width / 2 - 8, panelBottom - 8, 0xFF_888888);
        }
    }

    private void drawSkillCard(GuiGraphics g, int x, int y, int w, int h,
                                BaseSkill skill, String key, PlayerData data,
                                int mouseX, int mouseY, int skillIndex) {
        int rank = data.getSkillRank(skill.getSkillId());
        int mastery = data.getSkillMastery(skill.getSkillId());
        int nextRankMastery = data.getMasteryForNextRank(skill.getSkillId());
        boolean canRankUp = data.canRankUp(skill.getSkillId(), data.getLevel());

        // Card background
        g.fill(x, y, x + w, y + h, 0xAA_1A1A2A);
        borderBox(g, x, y, w, h);

        // Key label + name + rank badge
        g.drawString(this.font, "[" + key + "]", x + 4, y + 3, 0xFF_FFCC00);
        g.drawString(this.font, skill.getSkillName(), x + 30, y + 3, 0xFF_FFFFFF);

        String rankName = PlayerData.RANK_NAMES[rank];
        int rankColor = switch (rank) {
            case 6 -> 0xFF_FF4444; // SS
            case 5 -> 0xFF_FF8800; // S
            case 4 -> 0xFF_FFCC00; // A
            case 3 -> 0xFF_88FF00; // B
            case 2 -> 0xFF_44CCFF; // C
            case 1 -> 0xFF_8888FF; // D
            default -> 0xFF_888888; // E
        };
        String badge = "Rank " + rankName;
        g.drawString(this.font, badge, x + w - font.width(badge) - 6, y + 3, rankColor);

        // Mastery bar
        int row2Y = y + 16;
        int barX = x + 4;
        int barW = w - 8;
        int barH = 6;
        g.fill(barX, row2Y, barX + barW, row2Y + barH, 0xFF_111111);
        if (nextRankMastery > 0) {
            float pct = Math.min((float) mastery / nextRankMastery, 1f);
            int fillW = (int) ((barW - 2) * pct);
            if (fillW > 0) {
                g.fill(barX + 1, row2Y + 1, barX + 1 + fillW, row2Y + barH - 1, 0xFF_CCAA44);
            }
        } else {
            g.fill(barX + 1, row2Y + 1, barX + barW - 1, row2Y + barH - 1, 0xFF_FFAA00); // MAX
        }
        String masteryText = nextRankMastery > 0
                ? "Mastery: " + mastery + " / " + nextRankMastery
                : "Mastery: " + mastery + " (MAX)";
        g.drawString(this.font, masteryText, barX + 2, row2Y + barH + 1, 0xFF_AAAAAA);

        // Stats row
        int row3Y = row2Y + barH + 11;
        int cdSec = skill.getScaledCooldown(rank) / 20;
        float dmgMul = skill.getDamageMultiplier(rank);
        String statsStr = "CD: " + cdSec + "s  |  DMG: " + (int)(dmgMul * 100) + "%";
        if (skill.getManaCost() > 0) statsStr += "  |  MP: " + skill.getManaCost();
        g.drawString(this.font, statsStr, x + 4, row3Y, 0xFF_999999);

        // Rank up button or status
        if (canRankUp) {
            String btnText = "Rank Up → " + PlayerData.RANK_NAMES[rank + 1];
            int bw = font.width(btnText) + 12;
            int bh = 14;
            int bx = x + w - bw - 6;
            int by = y + h - bh - 4;
            boolean hovered = mouseX >= bx && mouseX < bx + bw && mouseY >= by && mouseY < by + bh;
            g.fill(bx, by, bx + bw, by + bh, hovered ? 0xFF_886622 : 0xFF_443311);
            borderBox(g, bx, by, bw, bh);
            g.drawCenteredString(this.font, btnText, bx + bw / 2, by + 3, 0xFF_FFCC00);
            clickZones.add(new int[]{bx, by, bw, bh, 1, skillIndex});
        } else if (rank >= 6) {
            g.drawString(this.font, "MAX RANK", x + w - font.width("MAX RANK") - 8, y + h - 16, 0xFF_FF4444);
        } else {
            int needLv = (rank + 1) * 10;
            String reason = data.getLevel() < needLv ? "Need Lv" + needLv : "Need Mastery";
            g.drawString(this.font, reason, x + w - font.width(reason) - 8, y + h - 16, 0xFF_666666);
        }

        // Type tag + unlock info
        g.drawString(this.font, skill.getDamageType(), x + 4, y + h - 16, 0xFF_8888CC);
    }

    private void borderBox(GuiGraphics g, int x, int y, int w, int h) {
        g.fill(x, y, x + w, y + 1, 0xFF_556677);
        g.fill(x + w - 1, y, x + w, y + h, 0xFF_445566);
        g.fill(x, y + h - 1, x + w, y + h, 0xFF_445566);
        g.fill(x, y, x + 1, y + h, 0xFF_556677);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0) {
            for (int[] z : clickZones) {
                if (mouseX >= z[0] && mouseX < z[0] + z[2]
                        && mouseY >= z[1] && mouseY < z[1] + z[3]) {
                    int action = z[4];
                    if (action == 0) {
                        allocateStat(STAT_NAMES[z[5]]);
                        return true;
                    } else if (action == 1) {
                        // Rank up skill
                        var player = Minecraft.getInstance().player;
                        if (player != null) {
                            var data = player.getCapability(PlayerCapability.PLAYER_DATA).orElse(null);
                            if (data != null) {
                                try {
                                    ClassType ct = ClassType.valueOf(data.getSelectedClass());
                                    var skills = SkillRegistry.getSkillsForClass(ct);
                                    if (z[5] < skills.size()) {
                                        rankUpSkill(skills.get(z[5]).getSkillId());
                                        return true;
                                    }
                                } catch (Exception ignored) {}
                            }
                        }
                    } else if (action == 2) {
                        selectedTab = 0;
                        scrollOffset = 0;
                        return true;
                    } else if (action == 3) {
                        selectedTab = 1;
                        scrollOffset = 0;
                        return true;
                    }
                }
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
        scrollOffset -= (int) (delta * 2);
        return true;
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
