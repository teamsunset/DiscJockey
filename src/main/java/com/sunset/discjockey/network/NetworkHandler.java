package com.sunset.discjockey.network;

import com.sunset.discjockey.network.message.*;
import com.sunset.discjockey.util.ModReference;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.Optional;

public class NetworkHandler {
    private static final String VERSION = "1.0.0";

    public static final SimpleChannel NETWORK_CHANNEL = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(ModReference.MOD_ID, "network"),
            () -> VERSION,
            VERSION::equals,
            VERSION::equals
    );

    public static void init() {
        int id = 0;
        NETWORK_CHANNEL.registerMessage(
                id++,
                TagMessage.class,
                TagMessage::encode,
                TagMessage::decode,
                TagMessage::handle
        );
        NETWORK_CHANNEL.registerMessage(
                id++,
                MusicURLSyncMessage.class,
                MusicURLSyncMessage::encode,
                MusicURLSyncMessage::decode,
                MusicURLSyncMessage::handle,
                Optional.of(NetworkDirection.PLAY_TO_SERVER)
        );
        NETWORK_CHANNEL.registerMessage(
                id++,
                ControllerSyncMessage.class,
                ControllerSyncMessage::encode,
                ControllerSyncMessage::decode,
                ControllerSyncMessage::handle,
                Optional.of(NetworkDirection.PLAY_TO_CLIENT)
        );
        NETWORK_CHANNEL.registerMessage(
                id++,
                ItemTagMessage.class,
                ItemTagMessage::encode,
                ItemTagMessage::decode,
                ItemTagMessage::handle,
                Optional.of(NetworkDirection.PLAY_TO_SERVER)
        );
        NETWORK_CHANNEL.registerMessage(
                id++,
                ScratchMessage.class,
                ScratchMessage::encode,
                ScratchMessage::decode,
                ScratchMessage::handle,
                Optional.of(NetworkDirection.PLAY_TO_SERVER)
        );
    }

//    public static void sendToNearby(Level world, BlockPos pos, Object toSend) {
//        if (world instanceof ServerLevel serverLevel) {
//
//            serverLevel.getChunkSource().chunkMap.getPlayers(new ChunkPos(pos), false).stream()
//                    .filter(p -> p.distanceToSqr(pos.getX(), pos.getY(), pos.getZ()) < 96 * 96)
//                    .forEach(p -> NETWORK_CHANNEL.send(PacketDistributor.PLAYER.with(() -> p), toSend));
//        }
//    }
}
