package com.rpgpack;

import com.rpgpack.init.ModCapabilities;
import com.rpgpack.init.ModMessages;
import com.rpgpack.init.ModEvents;
import com.rpgpack.skills.SkillRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(RPGPack.MODID)
public class RPGPack {

    public static final String MODID = "rpgpack";
    public static final Logger LOGGER = LogManager.getLogger();

    public RPGPack() {
        var bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(this::commonSetup);
        ModCapabilities.register();
        ModMessages.register();
        ModEvents.register();
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        SkillRegistry.init();
        LOGGER.info("[RPGPACK] Echoes of Aincrad RPG System initialized!");
    }
}
