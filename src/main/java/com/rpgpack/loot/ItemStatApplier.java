package com.rpgpack.loot;

import com.rpgpack.RPGPack;
import com.rpgpack.core.PlayerCapability;
import com.rpgpack.core.StatCalculator;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = RPGPack.MODID)
public class ItemStatApplier {

    /** Periodic safety net — ensures bonuses stay in sync (every 2s). */
    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        if (event.player.level().isClientSide) return;
        if (event.player.tickCount % 40 != 0) return;

        apply(event.player);
    }

    /** Triggered by equipment change handler for instant update. */
    public static void apply(Player player) {
        player.getCapability(PlayerCapability.PLAYER_DATA).ifPresent(data -> {
            StatCalculator.applyItemBonuses(data, player);
            data.markStatsDirty();
        });
    }
}
