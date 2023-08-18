package com.sunset.discjockey.network.message;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class TagMessage
{

    public static Map<String, BiConsumer<TagMessage, NetworkEvent.Context>> HANDLERS = new HashMap<>();
    public final CompoundTag compoundTag;

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
        try {
            if (!message.compoundTag.getString("handler").equals("")) {
                BiConsumer<TagMessage, NetworkEvent.Context> handler = HANDLERS.get(message.compoundTag.getString("handler"));
                if (handler != null)
                    context.enqueueWork(() -> handler.accept(message, context));
                else
                    throw new Exception("Not Found Handler in HANDLERS!");
            }
            else {
                throw new Exception("Not Found Handler in tag!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        context.setPacketHandled(true);
    }
}
