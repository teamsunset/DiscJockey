package com.sunset.discjockey.block.BlockEntity;

import com.sunset.discjockey.block.BlockEntity.Controller.AbstractController;
import com.sunset.discjockey.block.BlockEntity.Controller.Widget.Base.ControllerWidgetManager;
import com.sunset.discjockey.block.BlockEntity.Controller.Widget.ControllerCueButton;
import com.sunset.discjockey.block.BlockEntity.Controller.Widget.ControllerMixFader;
import com.sunset.discjockey.block.BlockEntity.Controller.Widget.ControllerPlayButton;
import com.sunset.discjockey.network.message.MusicURLSyncMessage;
import com.sunset.discjockey.util.MusicMisc.MusicFileManager;
import com.sunset.discjockey.util.RegistryCollection.BlockEntityTypeCollection;
import com.sunset.discjockey.util.RegistryCollection.ItemCollection;
import com.sunset.discjockey.util.TouchMap.TouchMapDDJ400;
import com.sunset.discjockey.util.TouchMap.Vec2Type.Vec2Plane;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

import java.util.Vector;
import java.util.concurrent.CompletableFuture;

import static com.sunset.discjockey.network.NetworkHandler.NETWORK_CHANNEL;

public class BlockEntityDDJ400 extends AbstractController {

    public BlockEntityDDJ400(BlockPos pPos, BlockState pBlockState) {
        super(BlockEntityTypeCollection.BLOCK_ENTITY_DDJ400.get(), pPos, pBlockState);
        controllerWidgetManager.add(new ControllerMixFader("mix_fader", TouchMapDDJ400.MIX_FADER, controllerAudioManager));
        controllerWidgetManager.add(new ControllerPlayButton("left_play_button", TouchMapDDJ400.LEFT_PLAY_BUTTON, controllerAudioManager, 0));
        controllerWidgetManager.add(new ControllerCueButton("left_cue_button", TouchMapDDJ400.LEFT_CUE_BUTTON, controllerAudioManager, 0));
    }

    //action
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
//            String futureSongURL = player.getItemInHand(hand).getDisplayName().getString().replace("[", "").replace("]", "");
//            if (!level.isClientSide()) {
//                this.setSongURL(futureSongURL);
//                player.sendSystemMessage(Component.literal("Set song url to " + this.getSongURL()));
//            } else {
//                CompletableFuture.runAsync(() -> {
//                            if (!MusicFileManager.checkURL(futureSongURL)) {
//                                player.sendSystemMessage(Component.literal("§4Song not found!"));
//                            }
//                        }
//                        , Util.backgroundExecutor());
//            }
//        } else if (!level.isClientSide()) {
//            this.setPlaying(!this.getPlaying());
//            player.displayClientMessage(Component.literal("Set playing to " + this.getPlaying()), true);
//        }

        if (!level.isClientSide() && !player.getItemInHand(hand).getItem().equals(ItemCollection.ITEM_USB_FLASH_DISK.get())) {
            Vec3 hitLocation = hit.getLocation();
            Vec3 relativeHitLocation = hitLocation.subtract(this.getBlockPos().getCenter());
            Direction blockFacing = state.getValue(HorizontalDirectionalBlock.FACING);
            Vec2Plane relativeHitPoint = new Vec2Plane(new Vec2Plane(relativeHitLocation.x, relativeHitLocation.z)).rotate(-1 * Vec2Plane.DIRECTION_DEGREE_MAP.get(blockFacing), Vec2Plane.ORIGIN);
            controllerWidgetManager.interact(ControllerWidgetManager.InteractType.DRAG, 1, relativeHitPoint);
            controllerWidgetManager.interact(ControllerWidgetManager.InteractType.PRESS, 1, relativeHitPoint);

//        player.getEyePosition();
//        player.pick(player.distance)
//            if (TouchMapDDJ400.MIDDLE_BLADE_FADER.isInRange(relativeHitPoint)) {
//                this.setMiddleBladeFader(((relativeHitPoint.x - TouchMapDDJ400.MIDDLE_BLADE_FADER.p1.x) / (TouchMapDDJ400.MIDDLE_BLADE_FADER.p2.x - TouchMapDDJ400.MIDDLE_BLADE_FADER.p1.x) * 2 - 1));
//                SG_LOGGER.debug(String.valueOf(hitLocation));
//            }
        } else if (level.isClientSide() && player.getItemInHand(hand).getItem().equals(ItemCollection.ITEM_USB_FLASH_DISK.get())) {
            CompletableFuture.runAsync(() -> {
                ListTag listTag = player.getItemInHand(hand).getTag().getList("urls", Tag.TAG_STRING);
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
