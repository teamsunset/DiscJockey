package com.sunset.discjockey.event;

import com.sunset.discjockey.block.BlockDDJ400;
import com.sunset.discjockey.client.model.ModelDDJ400;
import com.sunset.discjockey.client.renderer.BlockEntity.BlockEntityRendererDDJ400;
import com.sunset.discjockey.util.MusicMisc.MusicFileManager;
import com.sunset.discjockey.util.Reference;
import com.sunset.discjockey.util.RegistryCollection.BlockEntityTypeCollection;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.io.IOException;

public class EventHandler
{
    @Mod.EventBusSubscriber(modid = Reference.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public class ModEventBothSide
    {

    }

    @Mod.EventBusSubscriber(modid = Reference.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public class ModEventClientSide
    {
        @SubscribeEvent
        public static void clientSetup(FMLClientSetupEvent event) throws IOException {
            BlockDDJ400.clientSetup(event);
            MusicFileManager.readCache();
//            MusicFileManager.getFileFromUrl("https://music.163.com/song/media/outer/url?id=30953211.mp3");
            Runtime.getRuntime().addShutdownHook(new Thread(MusicFileManager::clearCache));
        }

        @SubscribeEvent
        public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
            event.registerBlockEntityRenderer(BlockEntityTypeCollection.BLOCK_ENTITY_DDJ400.get(), BlockEntityRendererDDJ400::new);
        }

        @SubscribeEvent
        public static void onRegisterLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
            event.registerLayerDefinition(ModelDDJ400.LAYER_LOCATION, ModelDDJ400::createBodyLayer);
        }
    }

    @Mod.EventBusSubscriber(modid = Reference.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.DEDICATED_SERVER)
    public class ModEventServerSide
    {

    }
}
