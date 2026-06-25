package com.rpgpack.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import com.rpgpack.core.PlayerCapability;
import com.rpgpack.core.PlayerData;
import com.rpgpack.core.DerivedStats;
import com.rpgpack.core.StatCalculator;
import com.rpgpack.init.ModMessages;
import com.rpgpack.network.AddStatPointC2S;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

public class CharacterScreen extends Screen {

    private static final int LEFT_PANEL = 20;
    private static final int RIGHT_PANEL = 210;
    private static final int TOP = 30;

    public CharacterScreen() {
        super(Component.literal("Character"));
    }

    @Override
    protected void init() {
        super.init();
        int y = TOP + 20;

        // Stat allocation buttons — + button next to each stat
        String[] stats = {"STR", "VIT", "END", "AGI", "DEX", "INT", "WIS", "LUK"};
        for (String stat : stats) {
            addRenderableWidget(
                    Button.builder(Component.literal("+"), btn -> allocateStat(stat))
                            .pos(LEFT_PANEL + 130, y)
                            .size(18, 18)
                            .build()
            );
            y += 22;
        }
    }

    private void allocateStat(String statName) {
        ModMessages.CHANNEL.sendToServer(new AddStatPointC2S(statName));
    }

    @Override
    public void render(@NotNull PoseStack ps, int mouseX, int mouseY, float partialTick) {
        renderBackground(ps);
        var player = Minecraft.getInstance().player;
        if (player == null) return;

        PlayerData data = player.getCapability(PlayerCapability.PLAYER_DATA).orElse(null);
        if (data == null) return;

        DerivedStats derived = StatCalculator.calculate(data);

        // Title
        drawCenteredString(ps, this.font, "CHARACTER", this.width / 2, 8, 0xFF_FFAA00);

        // === LEFT PANEL: Base Stats ===
        drawString(ps, this.font, "Level: " + data.getLevel(), LEFT_PANEL, TOP, 0xFFFFFF);
        drawString(ps, this.font, "EXP: " + data.getExperience() + " / " + data.getExperienceToNextLevel(),
                LEFT_PANEL, TOP + 12, 0xAAAAAA);
        drawString(ps, this.font, "Class: " + data.getSelectedClass(), LEFT_PANEL, TOP + 24, 0xFF_CC88);
        drawString(ps, this.font, "Vision: " + data.getCurrentVision(), LEFT_PANEL, TOP + 36, 0x88CCFF);

        int y = TOP + 60;
        drawString(ps, this.font, "Stat Points: " + data.getStatPoints(), LEFT_PANEL, y, 0xFF_FF88);
        y += 16;

        String[] labels = {"STR", "VIT", "END", "AGI", "DEX", "INT", "WIS", "LUK"};
        int[] values = {data.getStr(), data.getVit(), data.getEnd(), data.getAgi(),
                data.getDex(), data.getInt(), data.getWis(), data.getLuk()};
        String[] descs = {"Physical Damage", "Max HP + Defense", "Max Stamina", "Speed + Dodge",
                "Crit Chance", "Magic Damage", "CDR + Mana Regen", "Loot + Crit DMG"};

        for (int i = 0; i < labels.length; i++) {
            drawString(ps, this.font, labels[i] + ": " + values[i], LEFT_PANEL, y, 0xDDDDDD);
            drawString(ps, this.font, descs[i], LEFT_PANEL + 55, y, 0x777777);
            y += 22;
        }

        // === RIGHT PANEL: Derived Stats ===
        int rx = RIGHT_PANEL + 80;
        y = TOP + 20;
        drawString(ps, this.font, "DERIVED STATS", rx - 40, y, 0xFF_CC44);
        y += 16;

        String[][] derivedLines = {
                {"HP", String.format("%.0f / %.0f", data.getCurrentHp(), derived.maxHp)},
                {"Mana", String.format("%.0f / %.0f", data.getCurrentMana(), derived.maxMana)},
                {"Stamina", String.format("%.0f / %.0f", data.getCurrentStamina(), derived.maxStamina)},
                {"Phys DMG", String.format("%.1f", derived.physicalDamage)},
                {"Magic DMG", String.format("%.1f", derived.magicDamage)},
                {"Phys DEF", String.format("%.1f", derived.physicalDefense)},
                {"Magic DEF", String.format("%.1f", derived.magicDefense)},
                {"Crit Chance", String.format("%.1f%%", derived.critChance)},
                {"Crit DMG", String.format("%.1f%%", derived.critDamage)},
                {"Atk Speed", String.format("%.1f%%", derived.attackSpeed)},
                {"Move Speed", String.format("%.1f%%", derived.moveSpeed)},
                {"CDR", String.format("%.1f%%", derived.cooldownReduction)},
                {"Elem Bonus", String.format("%.1f%%", derived.elementalBonus)},
                {"Mana Regen", String.format("%.2f/s", derived.manaRegen)},
                {"Stam Regen", String.format("%.2f/s", derived.staminaRegen)},
        };

        for (String[] line : derivedLines) {
            drawString(ps, this.font, line[0] + ":", rx - 60, y, 0xAAAAAA);
            drawString(ps, this.font, line[1], rx + 20, y, 0xFFFFFF);
            y += 13;
        }

        // Skill Points
        y += 8;
        drawString(ps, this.font, "Skill Points: " + data.getSkillPoints(), rx - 40, y, 0xFF_FF88);

        super.render(ps, mouseX, mouseY, partialTick);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
