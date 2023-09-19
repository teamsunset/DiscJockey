package com.sunset.discjockey.network.message;

import com.sunset.discjockey.block.BlockEntity.Controller.AbstractControllerEntity;
import com.sunset.discjockey.block.BlockEntity.Controller.Widget.Base.ControllerWidgetManager;
import com.sunset.discjockey.util.TouchMap.Vec2Type.Vec2Plane;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ScratchMessage {
    public BlockPos pos;
    public double scrollDelta;

    public boolean isTouching;


    public Vec2Plane relativeHitPoint;

    public ScratchMessage(BlockPos pos, double scrollDelta, boolean isTouching, Vec2Plane relativeHitPoint) {
        this.pos = pos;
        this.scrollDelta = scrollDelta;
        this.isTouching = isTouching;
        this.relativeHitPoint = relativeHitPoint;
    }

    public static void encode(ScratchMessage message, FriendlyByteBuf buf) {
        buf.writeBlockPos(message.pos);
        buf.writeDouble(message.scrollDelta);
        buf.writeBoolean(message.isTouching);
        buf.writeDouble(message.relativeHitPoint.x);
        buf.writeDouble(message.relativeHitPoint.z);

    }

    public static ScratchMessage decode(FriendlyByteBuf buf) {
        BlockPos pos = buf.readBlockPos();
        double scrollDelta = buf.readDouble();
        boolean isTouching = buf.readBoolean();
        Vec2Plane relativeHitPoint = new Vec2Plane(buf.readDouble(), buf.readDouble());
        return new ScratchMessage(pos, scrollDelta, isTouching, relativeHitPoint);
    }

    public static void handle(ScratchMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (player != null && player.level().isLoaded(message.pos) && (player.level().getBlockEntity(message.pos) instanceof AbstractControllerEntity abstractControllerEntity)) {
                abstractControllerEntity.controllerWidgetManager.interact(player, ControllerWidgetManager.InteractType.SCROLL, message.scrollDelta, message.isTouching, message.relativeHitPoint);
            }
        });
        context.setPacketHandled(true);
    }
}
