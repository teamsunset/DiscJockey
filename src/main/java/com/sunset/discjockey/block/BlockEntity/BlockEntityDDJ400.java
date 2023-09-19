package com.sunset.discjockey.block.BlockEntity;

import com.sunset.discjockey.block.BlockEntity.Controller.AbstractControllerEntity;
import com.sunset.discjockey.block.BlockEntity.Controller.Widget.AbstractWidget.ControllerDisc;
import com.sunset.discjockey.block.BlockEntity.Controller.Widget.AbstractWidget.ControllerFader;
import com.sunset.discjockey.block.BlockEntity.Controller.Widget.Base.ControllerWidgetManager;
import com.sunset.discjockey.block.BlockEntity.Controller.Widget.*;
import com.sunset.discjockey.network.message.MusicURLSyncMessage;
import com.sunset.discjockey.util.MusicMisc.MusicFileManager;
import com.sunset.discjockey.util.RegistryCollection.BlockEntityTypeCollection;
import com.sunset.discjockey.util.RegistryCollection.ItemCollection;
import com.sunset.discjockey.util.TouchMap.TouchMapDDJ400;
import com.sunset.discjockey.util.TouchMap.Vec2Type.Vec2Plane;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import java.util.Vector;
import java.util.concurrent.CompletableFuture;

import static com.sunset.discjockey.network.NetworkHandler.NETWORK_CHANNEL;

public class BlockEntityDDJ400 extends AbstractControllerEntity {

    public BlockEntityDDJ400(BlockPos pPos, BlockState pBlockState) {
        super(BlockEntityTypeCollection.BLOCK_ENTITY_DDJ400.get(), pPos, pBlockState);
        ControllerSideMixFader leftMixFader = new ControllerSideMixFader("left_mix_fader", TouchMapDDJ400.LEFT_MIX_FADER, controllerAudioManager, 0);
        ControllerSideMixFader rightMixFader = new ControllerSideMixFader("right_mix_fader", TouchMapDDJ400.RIGHT_MIX_FADER, controllerAudioManager, 1);
        controllerWidgetManager.add(leftMixFader);
        controllerWidgetManager.add(rightMixFader);
        controllerWidgetManager.add(new ControllerCrossFader("cross_fader", TouchMapDDJ400.CROSS_FADER, controllerAudioManager, leftMixFader, rightMixFader));
        controllerWidgetManager.add(new ControllerPlayButton("left_play_button", TouchMapDDJ400.LEFT_PLAY_BUTTON, controllerAudioManager, 0));
        controllerWidgetManager.add(new ControllerPlayButton("right_play_button", TouchMapDDJ400.RIGHT_PLAY_BUTTON, controllerAudioManager, 1));
        controllerWidgetManager.add(new ControllerCueButton("left_cue_button", TouchMapDDJ400.LEFT_CUE_BUTTON, controllerAudioManager, 0));
        controllerWidgetManager.add(new ControllerCueButton("right_cue_button", TouchMapDDJ400.RIGHT_CUE_BUTTON, controllerAudioManager, 1));
        controllerWidgetManager.add(new ControllerFader("left_bpm_fader", TouchMapDDJ400.LEFT_BPM_FADER));
        controllerWidgetManager.add(new ControllerFader("right_bpm_fader", TouchMapDDJ400.RIGHT_BPM_FADER));
        controllerWidgetManager.add(new ControllerLoadButton("left_load_button", TouchMapDDJ400.LEFT_LOAD_BUTTON, controllerAudioManager, 0, 0));
        controllerWidgetManager.add(new ControllerLoadButton("right_load_button", TouchMapDDJ400.RIGHT_LOAD_BUTTON, controllerAudioManager, 1, 1));
        controllerWidgetManager.add(new ControllerDisc("left_disc", TouchMapDDJ400.LEFT_DISC, controllerAudioManager, 0));
        controllerWidgetManager.add(new ControllerDisc("right_disc", TouchMapDDJ400.RIGHT_DISC, controllerAudioManager, 1));
    }

    //action
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!level.isClientSide() && !player.getItemInHand(hand).getItem().equals(ItemCollection.ITEM_USB_FLASH_DISK.get())) {
            Vec2Plane relativeHitPoint = this.getRelativeHitPoint(hit);
            controllerWidgetManager.interact(player, ControllerWidgetManager.InteractType.DRAG, 1, relativeHitPoint);
            controllerWidgetManager.interact(player, ControllerWidgetManager.InteractType.PRESS, 1, relativeHitPoint);
        } else if (level.isClientSide() && player.getItemInHand(hand).getItem().equals(ItemCollection.ITEM_USB_FLASH_DISK.get())) {
            CompletableFuture.runAsync(() -> {
                ListTag listTag = player.getItemInHand(hand).getOrCreateTag().getList("urls", Tag.TAG_STRING);
                boolean isValid = true;
                for (Tag tag : listTag) {
                    if (!MusicFileManager.checkURL(tag.getAsString())) {
                        isValid = false;
                        player.sendSystemMessage(Component.literal("§4URLs not found!, url:" + tag.getAsString()));
                        break;
                    }
                }
                if (isValid) {
                    Vector<String> urls = new Vector<>();
                    for (Tag tag : listTag) {
                        urls.add(tag.getAsString());
                    }
                    NETWORK_CHANNEL.sendToServer(new MusicURLSyncMessage(this.getBlockPos(), urls));
                }
            }, Util.backgroundExecutor());
        }
        return InteractionResult.SUCCESS;
    }

}
