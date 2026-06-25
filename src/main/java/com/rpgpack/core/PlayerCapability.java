package com.rpgpack.core;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlayerCapability implements ICapabilitySerializable<CompoundTag> {

    public static final Capability<PlayerData> PLAYER_DATA = CapabilityManager.get(new CapabilityToken<>() {});

    private final PlayerData data = new PlayerData();
    private final LazyOptional<PlayerData> holder = LazyOptional.of(() -> data);

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == PLAYER_DATA) {
            return holder.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        return data.toNBT();
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        data.fromNBT(nbt);
    }
}
