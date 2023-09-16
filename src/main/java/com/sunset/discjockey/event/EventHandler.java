package com.sunset.discjockey.event;

import com.sunset.discjockey.block.BlockDDJ400;
import com.sunset.discjockey.block.BlockEntity.Controller.AbstractControllerEntity;
import com.sunset.discjockey.client.model.ModelDDJ400;
import com.sunset.discjockey.client.renderer.BlockEntity.BlockEntityRendererDDJ400;
import com.sunset.discjockey.network.NetworkHandler;
import com.sunset.discjockey.util.ModReference;
import com.sunset.discjockey.util.MusicMisc.MusicFileManager;
import com.sunset.discjockey.util.RegistryCollection.BlockEntityTypeCollection;
import com.sunset.discjockey.util.SpecialType.SimpleInterpolationValue;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

import java.io.IOException;

public class EventHandler {
    @Mod.EventBusSubscriber(modid = ModReference.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public class ForgeEventBoth {
        @SubscribeEvent
        public static void onLevelTick(TickEvent.LevelTickEvent event) {

        }

        @SubscribeEvent
        public static void onServerTick(TickEvent.ServerTickEvent event) {
            SimpleInterpolationValue.onServerTick(event);
        }

        @SubscribeEvent
        public static void onClientTick(TickEvent.ClientTickEvent event) {
            SimpleInterpolationValue.onClientTick(event);
        }
    }

    @Mod.EventBusSubscriber(modid = ModReference.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public class ModEventBothSide {
        @SubscribeEvent
        public static void onSetupEvent(FMLCommonSetupEvent event) {
            event.enqueueWork(NetworkHandler::init);
        }
    }

    @Mod.EventBusSubscriber(modid = ModReference.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public class ModEventClientSide {
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

    @Mod.EventBusSubscriber(modid = ModReference.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.DEDICATED_SERVER)
    public class ModEventDedicatedServerSide {

    }
}
