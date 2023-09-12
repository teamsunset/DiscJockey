package com.sunset.discjockey.block.BlockEntity.Controller;

import com.sunset.discjockey.block.BlockEntity.Controller.Audio.ControllerAudioManager;
import com.sunset.discjockey.block.BlockEntity.Controller.Widget.Base.ControllerWidgetManager;
import com.sunset.discjockey.network.NetworkHandler;
import com.sunset.discjockey.network.message.ControllerSyncMessage;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;

import static com.sunset.discjockey.DiscJockey.DEBUG_LOGGER;

public class AbstractController extends BlockEntity {
    public ControllerAudioManager controllerAudioManager;
    public ControllerWidgetManager controllerWidgetManager;

    public AbstractController(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
        controllerAudioManager = new ControllerAudioManager(this);
        controllerWidgetManager = new ControllerWidgetManager(this);
        MinecraftForge.EVENT_BUS.register(this);
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
//            level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), UPDATE_CLIENTS);
            NetworkHandler.NETWORK_CHANNEL.send(PacketDistributor.DIMENSION.with(() -> level.dimension()), new ControllerSyncMessage(this.getBlockPos(), this.getUpdateTag()));
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

    public boolean loadDone = false;

    @SubscribeEvent
    public void onLevelTick(TickEvent.LevelTickEvent event) {
        if (event.level != null && this.level != null && (event.level.isClientSide() == this.level.isClientSide()) && event.phase.equals(TickEvent.Phase.END)) {
            if (!loadDone) {
                if (this.level.getBlockEntity(this.getBlockPos()) != null)
                    loadDone = true;
            } else {
                if (this.level.getBlockEntity(this.getBlockPos()) == null) {
                    MinecraftForge.EVENT_BUS.unregister(this);
                } else if (!event.level.isClientSide()) {
                    this.controllerAudioManager.onLevelTick(event);
                    this.sync();
                }
            }
        }
    }
}
