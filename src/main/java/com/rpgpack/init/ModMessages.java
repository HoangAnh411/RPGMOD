package com.rpgpack.init;

import com.rpgpack.RPGPack;
import com.rpgpack.network.AddStatPointC2S;
import com.rpgpack.network.ChooseClassC2S;
import com.rpgpack.network.CooldownSyncS2C;
import com.rpgpack.network.FloatingDamageS2C;
import com.rpgpack.network.OpenClassSelectionS2C;
import com.rpgpack.network.RankUpC2S;
import com.rpgpack.network.SyncPlayerDataS2C;
import com.rpgpack.network.UseSkillC2S;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class ModMessages {

    private static final String PROTOCOL = "1";
    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(RPGPack.MODID, "main"),
            () -> PROTOCOL,
            PROTOCOL::equals,
            PROTOCOL::equals
    );

    private static int id;

    public static void register() {
        CHANNEL.registerMessage(id++, SyncPlayerDataS2C.class,
                SyncPlayerDataS2C::encode, SyncPlayerDataS2C::decode, SyncPlayerDataS2C::handle);
        CHANNEL.registerMessage(id++, ChooseClassC2S.class,
                ChooseClassC2S::encode, ChooseClassC2S::decode, ChooseClassC2S::handle);
        CHANNEL.registerMessage(id++, AddStatPointC2S.class,
                AddStatPointC2S::encode, AddStatPointC2S::decode, AddStatPointC2S::handle);
        CHANNEL.registerMessage(id++, OpenClassSelectionS2C.class,
                OpenClassSelectionS2C::encode, OpenClassSelectionS2C::decode, OpenClassSelectionS2C::handle);
        CHANNEL.registerMessage(id++, UseSkillC2S.class,
                UseSkillC2S::encode, UseSkillC2S::decode, UseSkillC2S::handle);
        CHANNEL.registerMessage(id++, CooldownSyncS2C.class,
                CooldownSyncS2C::encode, CooldownSyncS2C::decode, CooldownSyncS2C::handle);
        CHANNEL.registerMessage(id++, RankUpC2S.class,
                RankUpC2S::encode, RankUpC2S::decode, RankUpC2S::handle);
        CHANNEL.registerMessage(id++, FloatingDamageS2C.class,
                FloatingDamageS2C::encode, FloatingDamageS2C::decode, FloatingDamageS2C::handle);
    }
}
