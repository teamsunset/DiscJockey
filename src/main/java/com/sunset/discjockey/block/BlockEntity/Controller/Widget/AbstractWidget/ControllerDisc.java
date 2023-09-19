package com.sunset.discjockey.block.BlockEntity.Controller.Widget.AbstractWidget;

import com.sunset.discjockey.block.BlockEntity.Controller.Audio.ControllerAudio;
import com.sunset.discjockey.block.BlockEntity.Controller.Audio.ControllerAudioManager;
import com.sunset.discjockey.block.BlockEntity.Controller.Widget.Base.ControllerWidget;
import com.sunset.discjockey.block.BlockEntity.Controller.Widget.Base.ControllerWidgetManager;
import com.sunset.discjockey.util.TouchMap.Vec2Type.PlaneRange;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;

public class ControllerDisc extends ControllerWidget {

    public double speed = 0;

    public int windowSize = 0;

    public ControllerAudioManager controllerAudioManager;

    public int channelIndex;

    public ControllerDisc(String id, PlaneRange planeRange, ControllerAudioManager controllerAudioManager, int channelIndex) {
        super(id, ControllerWidgetManager.InteractType.SCROLL, planeRange);
        this.controllerAudioManager = controllerAudioManager;
        this.channelIndex = channelIndex;
//        this.speed.onServerInterpolate = this::markDirty;
    }

    @Override
    public void executeOnServer(Player player, double value) {
//        this.speed.setTarget(this.speed.get() + value);
        this.windowSize -= (int) value;
//        this.speed.setTarget(0.5 * (-1 * value) + 1);
        this.markExecute();
        this.markDirty();
    }

    @Override
    public void executeOnClient() {

    }

    @Override
    public CompoundTag getCompoundTag() {
        CompoundTag compoundTag = new CompoundTag();
        compoundTag.putDouble("speed", this.speed);
        return compoundTag;
    }


    @Override
    public void writeCompoundTag(CompoundTag compoundTag) {
        this.speed = compoundTag.getDouble("speed");
    }

    @Override
    public void onServerTick() {
        super.onServerTick();
//        this.speed.onServerTick();
        ControllerAudio audio = this.controllerAudioManager.loadedAudios.get(channelIndex);
        if (audio != null) {
            if (!audio.isPlayingOnServer) {
                this.speed = 0;
                this.windowSize = 0;
                return;
            }

            if (this.windowSize >= 0) {
                this.speed = this.windowSize * 0.01 + 1;
            } else {
                this.speed = this.windowSize * 0.01 - 1;
            }
            audio.speed = this.speed;
        } else {
            this.speed = 0;
            this.windowSize = 0;
            return;
        }
        
        if (windowSize != 0) {
            if (windowSize > 0) windowSize--;
            else windowSize++;
        }
    }

    @Override
    public void onClientTick() {
        super.onClientTick();
//        this.speed.onClientTick();
    }
}
