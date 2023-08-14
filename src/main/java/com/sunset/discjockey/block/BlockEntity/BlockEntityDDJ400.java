package com.sunset.discjockey.block.BlockEntity;

import com.sunset.discjockey.block.BlockEntity.Controller.Audio.ControllerAudioManager;
import com.sunset.discjockey.block.BlockEntity.Controller.Widget.Base.ControllerWidgetManager;
import com.sunset.discjockey.block.BlockEntity.Controller.Widget.ControllerMiddleBladeFader;
import com.sunset.discjockey.util.RegistryCollection.BlockEntityTypeCollection;
import com.sunset.discjockey.util.TouchMap.TouchMapDDJ400;
import com.sunset.discjockey.util.TouchMap.Vec2Type.Vec2Plane;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

import static net.minecraft.world.level.block.Block.UPDATE_CLIENTS;

public class BlockEntityDDJ400 extends BlockEntity
{

    public ControllerAudioManager controllerAudioManager = new ControllerAudioManager();
    public ControllerWidgetManager controllerWidgetManager = new ControllerWidgetManager();


    public BlockEntityDDJ400(BlockPos pPos, BlockState pBlockState) {
        super(BlockEntityTypeCollection.BLOCK_ENTITY_DDJ400.get(), pPos, pBlockState);
        controllerWidgetManager.add(new ControllerMiddleBladeFader("middle_blade_fader", TouchMapDDJ400.MIDDLE_BLADE_FADER, controllerAudioManager));
    }


    //action
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
//        if (player.getItemInHand(hand).getItem().equals(ItemCollection.ITEM_USB_FLASH_DISK.get())) {
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

        if (!level.isClientSide()) {
            Vec3 hitLocation = hit.getLocation();
            Vec3 relativeHitLocation = hitLocation.subtract(this.getBlockPos().getCenter());
            Direction blockFacing = state.getValue(HorizontalDirectionalBlock.FACING);
            Vec2Plane relativeHitPoint = new Vec2Plane(new Vec2Plane(relativeHitLocation.x, relativeHitLocation.z)).rotate(-1 * Vec2Plane.DIRECTION_DEGREE_MAP.get(blockFacing), Vec2Plane.ORIGIN);
//        player.getEyePosition();
//        player.pick(player.distance)
//            if (TouchMapDDJ400.MIDDLE_BLADE_FADER.isInRange(relativeHitPoint)) {
//                this.setMiddleBladeFader(((relativeHitPoint.x - TouchMapDDJ400.MIDDLE_BLADE_FADER.p1.x) / (TouchMapDDJ400.MIDDLE_BLADE_FADER.p2.x - TouchMapDDJ400.MIDDLE_BLADE_FADER.p1.x) * 2 - 1));
//                SG_LOGGER.debug(String.valueOf(hitLocation));
//            }
        }
        return InteractionResult.SUCCESS;
    }

    //data change actions
    public void markDirty() {
        if (level != null) {
            this.setChanged();
            this.sync();
        }
    }

    public void sync() {
        if (level != null && !level.isClientSide)
            level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), UPDATE_CLIENTS);
    }

    //all getPacket function use getUpdateTag() to get data
    //save actions
    @Override
    public void saveAdditional(CompoundTag compoundTag) {
        super.saveAdditional(compoundTag);
//        ListTag listTag = new ListTag();
        CompoundTag buildTag = getUpdateTag();
        for (String key : buildTag.getAllKeys()) {
            compoundTag.put(key, buildTag.get(key));
        }
    }

    @Override
    public void load(CompoundTag compoundTag) throws RuntimeException {
        super.load(compoundTag);
        controllerAudioManager.writeCompoundTag(compoundTag.getCompound("controller_audio_manager"));
        controllerWidgetManager.writeCompoundTag(compoundTag.getCompound("controller_widget_manager"));
    }

    //network
    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag compoundTag = super.getUpdateTag();
        compoundTag.put("controller_audio_manager", controllerAudioManager.getCompoundTag());
        compoundTag.put("controller_widget_manager", controllerWidgetManager.getCompoundTag());
        return compoundTag;
    }

    //super use load()
    @Override
    public void handleUpdateTag(CompoundTag compoundTag) {
//        super.handleUpdateTag(compoundTag);

//        super.load(compoundTag);
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        // Will get tag from #getUpdateTag
        return ClientboundBlockEntityDataPacket.create(this);
    }
}
