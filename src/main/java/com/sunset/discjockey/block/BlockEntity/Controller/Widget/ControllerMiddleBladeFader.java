package com.sunset.discjockey.block.BlockEntity.Controller.Widget;

import com.sunset.discjockey.block.BlockEntity.Controller.Audio.ControllerAudio;
import com.sunset.discjockey.block.BlockEntity.Controller.Audio.ControllerAudioManager;
import com.sunset.discjockey.block.BlockEntity.Controller.Widget.AbstractWidget.ControllerFader;
import com.sunset.discjockey.util.TouchMap.Vec2Type.PlaneRange;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

public class ControllerMiddleBladeFader extends ControllerFader
{

    public ControllerAudioManager controllerAudioManager;

    public ControllerMiddleBladeFader(String id, PlaneRange planeRange, ControllerAudioManager controllerAudioManager) {
        super(id, planeRange);
        this.controllerAudioManager = controllerAudioManager;
    }

    @Override
    public void executeOnServer(double value) {
        ControllerAudio leftAudio = this.controllerAudioManager.loadedAudios.get(0);
        ControllerAudio rightAudio = this.controllerAudioManager.loadedAudios.get(1);
        this.value.setTarget(value);
        this.markDirty();
    }

    @Override
    public void executeOnClient() {

    }
}
