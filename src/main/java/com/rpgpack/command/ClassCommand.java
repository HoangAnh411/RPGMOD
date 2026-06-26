package com.rpgpack.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.rpgpack.RPGPack;
import com.rpgpack.classes.ClassManager;
import com.rpgpack.classes.ClassType;
import com.rpgpack.core.PlayerCapability;
import com.rpgpack.core.PlayerData;
import com.rpgpack.init.ModMessages;
import com.rpgpack.network.SyncPlayerDataS2C;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.network.NetworkDirection;

public class ClassCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                Commands.literal("class")
                        .then(Commands.argument("type", StringArgumentType.word())
                                .suggests((ctx, builder) -> {
                                    for (ClassType t : ClassType.values()) {
                                        if (t != ClassType.NONE) {
                                            builder.suggest(t.name().toLowerCase());
                                        }
                                    }
                                    return builder.buildFuture();
                                })
                                .executes(ctx -> {
                                    String typeStr = StringArgumentType.getString(ctx, "type").toUpperCase();
                                    ServerPlayer player = ctx.getSource().getPlayerOrException();

                                    ClassType classType;
                                    try {
                                        classType = ClassType.valueOf(typeStr);
                                    } catch (IllegalArgumentException e) {
                                        ctx.getSource().sendFailure(
                                                Component.literal("Invalid class. Use: warrior, berserker, assassin, mage"));
                                        return 0;
                                    }

                                    if (classType == ClassType.NONE) {
                                        ctx.getSource().sendFailure(Component.literal("Cannot select NONE class."));
                                        return 0;
                                    }

                                    player.getCapability(PlayerCapability.PLAYER_DATA).ifPresent(data -> {
                                        ClassManager.applyClass(data, classType);

                                        // Sync vanilla max health to RPG maxHp
                                        var stats = data.getCachedStats(player);
                                        player.getAttribute(Attributes.MAX_HEALTH).setBaseValue(stats.maxHp);
                                        player.setHealth(stats.maxHp);

                                        RPGPack.LOGGER.info("[CLASS] Command: {} switched to {}",
                                                player.getName().getString(), classType.getDisplayName());

                                        PlayerData snap = new PlayerData();
                                        snap.copyFrom(data);
                                        ModMessages.CHANNEL.sendTo(
                                                new SyncPlayerDataS2C(snap),
                                                player.connection.connection,
                                                NetworkDirection.PLAY_TO_CLIENT
                                        );
                                    });

                                    ctx.getSource().sendSuccess(
                                            () -> Component.literal("Class changed to " + classType.getDisplayName()),
                                            true);
                                    return 1;
                                }))
        );
    }
}
