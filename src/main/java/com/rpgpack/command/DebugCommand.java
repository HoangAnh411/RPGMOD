package com.rpgpack.command;

import com.mojang.brigadier.CommandDispatcher;
import com.rpgpack.RPGPack;
import com.rpgpack.classes.ClassType;
import com.rpgpack.core.PlayerCapability;
import com.rpgpack.core.PlayerData;
import com.rpgpack.core.StatCalculator;
import com.rpgpack.loot.ItemRarity;
import com.rpgpack.init.ModMessages;
import com.rpgpack.network.SyncPlayerDataS2C;
import com.rpgpack.skills.SkillCooldownManager;
import com.rpgpack.skills.SkillRegistry;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;

public class DebugCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                Commands.literal("rpg")
                        .then(Commands.literal("debug").executes(ctx -> {
                            ServerPlayer player = ctx.getSource().getPlayerOrException();
                            player.getCapability(PlayerCapability.PLAYER_DATA).ifPresent(data -> {
                                var stats = StatCalculator.calculate(data);
                                String name = player.getName().getString();

                                ctx.getSource().sendSuccess(
                                        () -> Component.literal(""), false);

                                ctx.getSource().sendSuccess(() -> Component.literal(
                                        "§6===== RPG DEBUG: " + name + " ====="), false);
                                ctx.getSource().sendSuccess(() -> Component.literal(
                                        "§eClass: §f" + data.getSelectedClass()
                                                + "  §eLevel: §f" + data.getLevel()
                                                + "  §eEXP: §f" + data.getExperience() + "/" + data.getExperienceToNextLevel()),
                                        false);
                                ctx.getSource().sendSuccess(() -> Component.literal(
                                        "§eStatPoints: §f" + data.getStatPoints()),
                                        false);

                                ctx.getSource().sendSuccess(() -> Component.literal(
                                        "§cSTR:" + data.getStr() + " §aVIT:" + data.getVit()
                                                + " §bEND:" + data.getEnd() + " §dAGI:" + data.getAgi()
                                                + " §eDEX:" + data.getDex() + " §9INT:" + data.getInt()
                                                + " §5WIS:" + data.getWis() + " §6LUK:" + data.getLuk()),
                                        false);

                                ctx.getSource().sendSuccess(() -> Component.literal(
                                        "§cHP: §f" + String.format("%.0f", data.getCurrentHp()) + "/" + String.format("%.0f", stats.maxHp)
                                                + "  §9Mana: §f" + String.format("%.0f", data.getCurrentMana()) + "/" + String.format("%.0f", stats.maxMana)),
                                        false);

                                ctx.getSource().sendSuccess(() -> Component.literal(
                                        "§7PhysDMG: §f" + String.format("%.1f", stats.physicalDamage)
                                                + "  §7MagicDMG: §f" + String.format("%.1f", stats.magicDamage)
                                                + "  §7Crit: §f" + String.format("%.1f%%", stats.critChance)
                                                + "  §7CDR: §f" + String.format("%.1f%%", stats.cooldownReduction)),
                                        false);

                                ctx.getSource().sendSuccess(() -> Component.literal(
                                        "§7ManaRegen: §f" + String.format("%.2f/s", stats.manaRegen)),
                                        false);

                                // Skills & Cooldowns
                                ctx.getSource().sendSuccess(() -> Component.literal("§6--- Skills ---"), false);
                                try {
                                    ClassType ct = ClassType.valueOf(data.getSelectedClass());
                                    var skills = SkillRegistry.getSkillsForClass(ct);
                                    String[] keys = {"R", "G", "C", "V"};
                                    for (int i = 0; i < skills.size(); i++) {
                                        var s = skills.get(i);
                                        int cd = SkillCooldownManager.getCooldown(player, s.getSkillId());
                                        String cdStr = cd > 0 ? " §cCD:" + (cd / 20f) + "s" : " §aREADY";
                                        String manaCost = s.getManaCost() > 0 ? " §9" + s.getManaCost() + "MP" : "";
                                        String fullCost = manaCost;
                                        String skillName = s.getSkillName();
                                        String key = keys[i];
                                        ctx.getSource().sendSuccess(() -> Component.literal(
                                                "  [" + key + "] §f" + skillName + cdStr + fullCost), false);
                                    }
                                } catch (IllegalArgumentException ignored) {}

                                ctx.getSource().sendSuccess(() -> Component.literal(
                                        "§6===== END DEBUG ====="), false);
                            });
                            return 1;
                        }))
                        .then(Commands.literal("reload").executes(ctx -> {
                            ServerPlayer player = ctx.getSource().getPlayerOrException();
                            player.getCapability(PlayerCapability.PLAYER_DATA).ifPresent(data -> {
                                data.markStatsDirty();
                                var stats = data.getCachedStats(player);
                                data.setCurrentHp(stats.maxHp);
                                data.setCurrentMana(stats.maxMana);

                                PlayerData snap = new PlayerData();
                                snap.copyFrom(data);
                                ModMessages.CHANNEL.sendTo(
                                        new SyncPlayerDataS2C(snap),
                                        player.connection.connection,
                                        NetworkDirection.PLAY_TO_CLIENT);

                                ctx.getSource().sendSuccess(
                                        () -> Component.literal("§a[RPG] Reloaded! HP/Mana restored to max."),
                                        false);
                            });
                            return 1;
                        }))
                        .then(Commands.literal("salvage").executes(ctx -> {
                            ServerPlayer player = ctx.getSource().getPlayerOrException();
                            var held = player.getMainHandItem();
                            if (held.isEmpty()) {
                                ctx.getSource().sendFailure(Component.literal("§cHold an RPG item to salvage!"));
                                return 0;
                            }
                            var tag = held.getTag();
                            if (tag == null || !tag.contains("rpg_rarity")) {
                                ctx.getSource().sendFailure(Component.literal("§cThis item has no RPG rarity!"));
                                return 0;
                            }
                            ItemRarity rarity;
                            try { rarity = ItemRarity.valueOf(tag.getString("rpg_rarity")); }
                            catch (IllegalArgumentException e) {
                                ctx.getSource().sendFailure(Component.literal("§cUnknown rarity!"));
                                return 0;
                            }
                            int materialCount = switch (rarity) {
                                case COMMON -> 1;
                                case UNCOMMON -> 3;
                                case RARE -> 6;
                                case EPIC -> 12;
                                case LEGENDARY -> 25;
                                case MYTHIC -> 50;
                            };
                            net.minecraft.world.item.Item material = switch (rarity) {
                                case COMMON, UNCOMMON -> net.minecraft.world.item.Items.IRON_INGOT;
                                case RARE, EPIC -> net.minecraft.world.item.Items.GOLD_INGOT;
                                case LEGENDARY, MYTHIC -> net.minecraft.world.item.Items.DIAMOND;
                            };
                            player.getInventory().removeItem(held);
                            player.addItem(new net.minecraft.world.item.ItemStack(material, materialCount));
                            ctx.getSource().sendSuccess(
                                    () -> Component.literal("§a[Salvage] " + rarity.name + " → "
                                            + materialCount + "x " + material.getDescription().getString()),
                                    false);
                            return 1;
                        }))
        );
    }
}
