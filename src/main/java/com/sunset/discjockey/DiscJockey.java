package com.sunset.discjockey;

import com.mojang.logging.LogUtils;

import com.sunset.discjockey.util.Reference;
import com.sunset.discjockey.util.RegistryCollection.*;
import javazoom.spi.mpeg.sampled.convert.MpegFormatConversionProvider;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

import java.util.ServiceLoader;

@Mod(Reference.MOD_ID)
public class DiscJockey
{
    public static final Logger SG_LOGGER = LogUtils.getLogger();

    public DiscJockey() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        BlockCollection.BLOCK_DEFERRED_REGISTER.register(eventBus);
        CreativeModeTabCollection.CREATIVE_MODE_TAB_DEFERRED_REGISTER.register(eventBus);
        ItemCollection.ITEM_DEFERRED_REGISTER.register(eventBus);
        BlockEntityTypeCollection.BLOCK_ENTITY_TYPE_DEFERRED_REGISTER.register(eventBus);
        SoundEventCollection.SOUND_EVENT_DEFERRED_REGISTER.register(eventBus);
    }
}
