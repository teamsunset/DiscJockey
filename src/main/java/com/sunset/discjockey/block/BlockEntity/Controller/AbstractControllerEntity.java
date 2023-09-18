package com.sunset.discjockey.block.BlockEntity.Controller;

import com.sunset.discjockey.block.BlockEntity.Controller.Audio.ControllerAudio;
import com.sunset.discjockey.block.BlockEntity.Controller.Audio.ControllerAudioManager;
import com.sunset.discjockey.block.BlockEntity.Controller.Widget.Base.ControllerWidgetManager;
import com.sunset.discjockey.util.TouchMap.Vec2Type.Vec2Plane;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import static net.minecraft.world.level.block.Block.UPDATE_CLIENTS;

public class AbstractControllerEntity extends BlockEntity implements BlockEntityTicker<AbstractControllerEntity> {
    public ControllerAudioManager controllerAudioManager;
    public ControllerWidgetManager controllerWidgetManager;

    public AbstractControllerEntity(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
        controllerAudioManager = new ControllerAudioManager(this);
        controllerWidgetManager = new ControllerWidgetManager(this);
    }

    public Vec2Plane getRelativeHitPoint(BlockHitResult hit) {
        Vec3 hitLocation = hit.getLocation();
        Vec3 relativeHitLocation = hitLocation.subtract(this.getBlockPos().getCenter());
        Direction blockFacing = this.getBlockState().getValue(HorizontalDirectionalBlock.FACING);
        Vec2Plane relativeHitPoint = new Vec2Plane(new Vec2Plane(relativeHitLocation.x, relativeHitLocation.z)).rotate(-1 * Vec2Plane.DIRECTION_DEGREE_MAP.get(blockFacing), Vec2Plane.ORIGIN);
        return relativeHitPoint;
    }


    //data change actions
    public void markDirty() {
        if (level != null) {
            this.setChanged();
//            this.sync();
        }
    }

    public void sync() {
        if (level != null && !level.isClientSide) {
            level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), UPDATE_CLIENTS);
//            NetworkHandler.NETWORK_CHANNEL.send(PacketDistributor.DIMENSION.with(() -> level.dimension()), new ControllerSyncMessage(this.getBlockPos(), this.getUpdateTag()));
        }
    }


    //all getPacket function use getUpdateTag() to get data
    //save actions
    @Override
    public void saveAdditional(CompoundTag compoundTag) {
        super.saveAdditional(compoundTag);
        CompoundTag buildTag = getUpdateTag();
        for (String key : buildTag.getAllKeys()) {
            compoundTag.put(key, buildTag.get(key));
        }
    }

    //sync
    //load <-> getUpdatePacket gaming
    //getUpdateTag <->handleUpdateTag loading

    @Override
    public void load(@NotNull CompoundTag compoundTag) throws RuntimeException {
        super.load(compoundTag);
        this.controllerAudioManager.writeCompoundTag(compoundTag.getCompound("controller_audio_manager"));
        this.controllerWidgetManager.writeCompoundTag(compoundTag.getCompound("controller_widget_manager"));
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        // Will get tag from #getUpdateTag
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public @NotNull CompoundTag getUpdateTag() {
        CompoundTag compoundTag = super.getUpdateTag();
        compoundTag.put("controller_audio_manager", this.controllerAudioManager.getCompoundTag());
        compoundTag.put("controller_widget_manager", this.controllerWidgetManager.getCompoundTag());
        return compoundTag;
    }

    //super use load()
    @Override
    public void handleUpdateTag(CompoundTag compoundTag) {
        super.handleUpdateTag(compoundTag);
    }

    public void tick(Level pLevel, BlockPos pPos, BlockState pState, AbstractControllerEntity pBlockEntity) {
        if (pLevel.isClientSide()) {
            this.onClientTick();
        } else {
            this.onServerTick();
        }
    }

    @Override
    public void onChunkUnloaded() {
        super.onChunkUnloaded();
        for (ControllerAudio audio : controllerAudioManager.loadedAudios.values()) {
            audio.terminate();
        }
    }

    public void onServerTick() {
        this.controllerAudioManager.onServerTick();
        this.controllerWidgetManager.onServerTick();
        this.sync();
    }

    public void onClientTick() {
        this.controllerAudioManager.onClientTick();
        this.controllerWidgetManager.onClientTick();
    }

}
