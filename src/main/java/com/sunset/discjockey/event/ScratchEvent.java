package com.sunset.discjockey.event;

import com.sunset.discjockey.block.AbstractControllerBlock;
import com.sunset.discjockey.block.BlockEntity.Controller.AbstractControllerEntity;
import com.sunset.discjockey.network.NetworkHandler;
import com.sunset.discjockey.network.message.ScratchMessage;
import com.sunset.discjockey.util.TouchMap.Vec2Type.Vec2Plane;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.common.ForgeMod;
import org.lwjgl.glfw.GLFW;

import java.util.concurrent.ConcurrentLinkedQueue;

public class ScratchEvent {

    public static ConcurrentLinkedQueue<Double> scrollDeltaQueue = new ConcurrentLinkedQueue<>();

    public static boolean isALTDown = false;
    public static BlockPos pos = BlockPos.ZERO;

    public static Vec2Plane relativeHitPoint = Vec2Plane.ORIGIN;

    public static boolean isLookingAtController = false;


    public static void onMouseScroll(InputEvent.MouseScrollingEvent event) {
        scrollDeltaQueue.add(event.getScrollDelta());
    }

    public static void onKeyInput(InputEvent.Key event) {
        if (event.getKey() == GLFW.GLFW_KEY_LEFT_ALT && event.getAction() == GLFW.GLFW_PRESS) {
            ScratchEvent.isALTDown = true;
        } else if (event.getKey() == GLFW.GLFW_KEY_LEFT_ALT && event.getAction() == GLFW.GLFW_RELEASE) {
            ScratchEvent.isALTDown = false;
        } else {
            ScratchEvent.isALTDown = false;
        }
    }

    public static void onRenderTick(RenderLevelStageEvent event) {
        ScratchEvent.pos = BlockPos.ZERO;
        ScratchEvent.relativeHitPoint = Vec2Plane.ORIGIN;
        ScratchEvent.isLookingAtController = false;

        if (event.getStage().equals(RenderLevelStageEvent.Stage.AFTER_LEVEL)) {
            Player player = Minecraft.getInstance().player;

            if (player != null) {

                AttributeInstance hitDistanceAttribute = player.getAttribute(ForgeMod.BLOCK_REACH.get());
                double hitDistance = 0;
                if (hitDistanceAttribute != null) {
                    hitDistance = hitDistanceAttribute.getValue();
                }

                HitResult rayTraceResult = player.pick(hitDistance, event.getPartialTick(), false);

                if (rayTraceResult.getType() == HitResult.Type.BLOCK) {
                    BlockPos pos = ((BlockHitResult) rayTraceResult).getBlockPos();
                    Level level = Minecraft.getInstance().level;
                    if (level != null && level.isLoaded(pos)) {
                        BlockState blockState = Minecraft.getInstance().level.getBlockState(pos);
                        if (blockState.getBlock() instanceof AbstractControllerBlock) {
                            if (level.getBlockEntity(pos) instanceof AbstractControllerEntity abstractControllerEntity) {
                                ScratchEvent.relativeHitPoint = abstractControllerEntity.getRelativeHitPoint((BlockHitResult) rayTraceResult);
                                ScratchEvent.pos = pos;
                                ScratchEvent.isLookingAtController = true;
                                if (!scrollDeltaQueue.isEmpty()) {
                                    NetworkHandler.NETWORK_CHANNEL.sendToServer(new ScratchMessage(pos, scrollDeltaQueue.poll(), relativeHitPoint));
                                }
                                return;
                            }
                        }
                    }
                }

            }

            scrollDeltaQueue.clear();
        }
    }
}
