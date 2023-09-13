package com.sunset.discjockey.block.BlockEntity.Controller.Widget;

import com.sunset.discjockey.block.BlockEntity.Controller.Audio.ControllerAudio;
import com.sunset.discjockey.block.BlockEntity.Controller.Audio.ControllerAudioManager;
import com.sunset.discjockey.block.BlockEntity.Controller.Widget.AbstractWidget.ControllerFader;
import com.sunset.discjockey.util.TouchMap.Vec2Type.PlaneRange;

public class ControllerVolumeFader extends ControllerFader {
    public ControllerAudioManager controllerAudioManager;
    public int channelIndex;

    public ControllerVolumeFader(String id, PlaneRange planeRange, ControllerAudioManager controllerAudioManager, int channelIndex) {
        super(id, planeRange);
        this.controllerAudioManager = controllerAudioManager;
        this.channelIndex = channelIndex;
        this.value.set(1);
        this.value.onClientInterpolate = () -> {
            ControllerAudio audio = this.controllerAudioManager.loadedAudios.get(this.channelIndex);
            if (audio != null && audio.speakerSound != null) {
                audio.speakerSound.setVolume((float) this.value.get());
            }
        };
    }

    @Override
    public void executeOnServer(double value) {
        super.executeOnServer(value);
        this.value.setTarget((value + 1) * 0.5);
    }

    @Override
    public void executeOnClient() {
        super.executeOnClient();
    }
}
