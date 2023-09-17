package com.sunset.discjockey.block.BlockEntity.Controller.Widget.AbstractWidget;

import com.sunset.discjockey.block.BlockEntity.Controller.Audio.ControllerAudio;
import com.sunset.discjockey.block.BlockEntity.Controller.Audio.ControllerAudioManager;
import com.sunset.discjockey.block.BlockEntity.Controller.Widget.Base.ControllerWidget;
import com.sunset.discjockey.block.BlockEntity.Controller.Widget.Base.ControllerWidgetManager;
import com.sunset.discjockey.util.SpecialType.SimpleInterpolationValue;
import com.sunset.discjockey.util.TouchMap.Vec2Type.PlaneRange;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;

public class ControllerDisc extends ControllerWidget {

    public SimpleInterpolationValue speed = new SimpleInterpolationValue(4, 4);

    public ControllerAudioManager controllerAudioManager;

    public int channelIndex;

    public ControllerDisc(String id, PlaneRange planeRange, ControllerAudioManager controllerAudioManager, int channelIndex) {
        super(id, ControllerWidgetManager.InteractType.SCROLL, planeRange);
        this.controllerAudioManager = controllerAudioManager;
        this.channelIndex = channelIndex;
    }

    @Override
    public void executeOnServer(Player player, double value) {
        this.markExecute();
        this.markDirty();
    }

    @Override
    public void executeOnClient() {
        ControllerAudio audio = this.controllerAudioManager.loadedAudios.get(channelIndex);
        if (audio != null && audio.speakerSound != null) {
            audio.speakerSound.speed.set((float) this.speed.get());
        }
    }

    @Override
    public CompoundTag getCompoundTag() {
        CompoundTag compoundTag = new CompoundTag();
        compoundTag.putDouble("speed", this.speed.get());
        return compoundTag;
    }


    @Override
    public void writeCompoundTag(CompoundTag compoundTag) {
        this.speed.set(compoundTag.getDouble("speed"));
    }

    ;

    @Override
    public void onServerTick() {
        super.onServerTick();
        this.speed.onServerTick();
    }

    @Override
    public void onClientTick() {
        super.onClientTick();
    }
}
