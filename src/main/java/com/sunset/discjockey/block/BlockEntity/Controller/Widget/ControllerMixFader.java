package com.sunset.discjockey.block.BlockEntity.Controller.Widget;

import com.sunset.discjockey.block.BlockEntity.Controller.Audio.ControllerAudio;
import com.sunset.discjockey.block.BlockEntity.Controller.Audio.ControllerAudioManager;
import com.sunset.discjockey.block.BlockEntity.Controller.Widget.AbstractWidget.ControllerFader;
import com.sunset.discjockey.util.TouchMap.Vec2Type.PlaneRange;

public class ControllerMixFader extends ControllerFader {

    public ControllerAudioManager controllerAudioManager;

    public ControllerMixFader(String id, PlaneRange planeRange, ControllerAudioManager controllerAudioManager) {
        super(id, planeRange);
        this.controllerAudioManager = controllerAudioManager;
    }

    @Override
    public void executeOnServer(double value) {
        super.executeOnServer(value);
        ControllerAudio leftAudio = this.controllerAudioManager.loadedAudios.get(0);
        ControllerAudio rightAudio = this.controllerAudioManager.loadedAudios.get(1);
    }

    @Override
    public void executeOnClient() {

    }
}
