package com.sunset.discjockey.network.message;

import com.google.common.hash.HashCode;
import com.sunset.discjockey.block.BlockEntity.Controller.AbstractController;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;

import java.util.Random;
import java.util.function.Supplier;

import static com.sunset.discjockey.DiscJockey.DEBUG_LOGGER;

public class ControllerSyncMessage {
    public BlockPos pos;
    public CompoundTag compoundTag;

    public ControllerSyncMessage(BlockPos pos, CompoundTag compoundTag) {
        this.pos = pos;
        this.compoundTag = compoundTag;
    }

    public static void encode(ControllerSyncMessage message, FriendlyByteBuf buf) {
        buf.writeInt(message.pos.getX());
        buf.writeInt(message.pos.getY());
        buf.writeInt(message.pos.getZ());
        buf.writeNbt(message.compoundTag);
    }

    public static ControllerSyncMessage decode(FriendlyByteBuf buf) {
        BlockPos pos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
        CompoundTag compoundTag = buf.readNbt();
        return new ControllerSyncMessage(pos, compoundTag);
    }


    public static void handle(ControllerSyncMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
//            context.getSender().level().getBlockEntity(message.pos).getCapability(MusicURLSyncMessageProvider.MUSIC_URL_SYNC_MESSAGE_CAPABILITY).ifPresent(cap -> {
//                cap.setURLs(message.urls);
//            });
            BlockEntity blockEntity = Minecraft.getInstance().level.getBlockEntity(message.pos);
//            if(blockEntity instanceof MusicURLSyncMessageProvider) {
//                ((MusicURLSyncMessageProvider) blockEntity).setURLs(message.urls);
//            }
            if (blockEntity instanceof AbstractController controller) {
                controller.load(message.compoundTag);
            } else {
                DEBUG_LOGGER.debug("what the hell?" + ControllerSyncMessage.class.getName());
            }
            context.setPacketHandled(true);
        });
    }
}
