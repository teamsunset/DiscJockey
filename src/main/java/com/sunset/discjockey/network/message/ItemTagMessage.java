package com.sunset.discjockey.network.message;

import com.sunset.discjockey.block.BlockEntity.Controller.AbstractController;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;

import java.util.Vector;
import java.util.function.Supplier;

import static com.sunset.discjockey.DiscJockey.DEBUG_LOGGER;

public class ItemTagMessage {
    public int slotIndex;
    public CompoundTag compoundTag;

    public ItemTagMessage(int slotIndex, CompoundTag compoundTag) {
        this.slotIndex = slotIndex;
        this.compoundTag = compoundTag;
    }

    public static void encode(ItemTagMessage message, FriendlyByteBuf buf) {
        buf.writeInt(message.slotIndex);
        buf.writeNbt(message.compoundTag);
    }

    public static ItemTagMessage decode(FriendlyByteBuf buf) {
        int slotIndex = buf.readInt();
        CompoundTag compoundTag = buf.readNbt();
        return new ItemTagMessage(slotIndex, compoundTag);
    }

    public static void handle(ItemTagMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (player != null) {
                player.getInventory().getItem(message.slotIndex).setTag(message.compoundTag);
            }
        });
        context.setPacketHandled(true);
    }
}
