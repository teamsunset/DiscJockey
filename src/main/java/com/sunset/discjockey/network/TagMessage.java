package com.sunset.discjockey.network;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class TagMessage
{
    private final CompoundTag compoundTag;

    public TagMessage(CompoundTag compoundTag) {
        this.compoundTag = compoundTag;
    }

    public static TagMessage decode(FriendlyByteBuf buf) {
        return new TagMessage(buf.readAnySizeNbt());
    }

    public static void encode(TagMessage message, FriendlyByteBuf buf) {
        buf.writeNbt(message.compoundTag);
    }

    public static void handle(TagMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.setPacketHandled(true);
    }
}
