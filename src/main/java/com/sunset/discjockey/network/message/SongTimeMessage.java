package com.sunset.discjockey.network.message;

import com.sunset.discjockey.block.BlockEntity.Controller.AbstractControllerEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SongTimeMessage {

    public BlockPos pos;
    public int channelIndex;


    public int songTime;

    public SongTimeMessage(BlockPos pos, int channelIndex, int songTime) {
        this.pos = pos;
        this.channelIndex = channelIndex;
        this.songTime = songTime;
    }

    public static void encode(SongTimeMessage message, FriendlyByteBuf buf) {
        buf.writeBlockPos(message.pos);
        buf.writeInt(message.channelIndex);
        buf.writeInt(message.songTime);

    }

    public static SongTimeMessage decode(FriendlyByteBuf buf) {
        BlockPos pos = buf.readBlockPos();
        int channelIndex = buf.readInt();
        int songTime = buf.readInt();
        return new SongTimeMessage(pos, channelIndex, songTime);
    }

    public static void handle(SongTimeMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (player != null && player.level().isLoaded(message.pos) && (player.level().getBlockEntity(message.pos) instanceof AbstractControllerEntity abstractControllerEntity)) {
                abstractControllerEntity.controllerAudioManager.loadedAudios.get(message.channelIndex).songTime = message.songTime;
            }
        });
        context.setPacketHandled(true);
    }

}
