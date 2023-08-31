package com.sunset.discjockey.block.BlockEntity.Controller;

import com.sunset.discjockey.block.BlockEntity.Controller.Audio.ControllerAudioManager;
import com.sunset.discjockey.block.BlockEntity.Controller.Widget.Base.ControllerWidgetManager;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import static net.minecraft.world.level.block.Block.UPDATE_CLIENTS;

public class AbstractController extends BlockEntity
{
    public ControllerAudioManager controllerAudioManager;
    public ControllerWidgetManager controllerWidgetManager;

    public AbstractController(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
        controllerAudioManager = new ControllerAudioManager(this);
        controllerWidgetManager = new ControllerWidgetManager(this);
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
        CompoundTag buildTag = getUpdateTag();
        for (String key : buildTag.getAllKeys()) {
            compoundTag.put(key, buildTag.get(key));
        }
    }

    //sync
    //load <-> getUpdatePacket gaming
    //getUpdateTag <->handleUpdateTag loading

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
        super.handleUpdateTag(compoundTag);
//        super.load(compoundTag);
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        // Will get tag from #getUpdateTag
        return ClientboundBlockEntityDataPacket.create(this);
    }
}
