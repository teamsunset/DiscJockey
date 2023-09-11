package com.sunset.discjockey.block.BlockEntity.Controller.Widget;

import com.sunset.discjockey.block.BlockEntity.Controller.Audio.ControllerAudio;
import com.sunset.discjockey.block.BlockEntity.Controller.Audio.ControllerAudioManager;
import com.sunset.discjockey.block.BlockEntity.Controller.Widget.AbstractWidget.ControllerButton;
import com.sunset.discjockey.util.TouchMap.Vec2Type.PlaneRange;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

public class ControllerCueButton extends ControllerButton {
    public int channelIndex;
    public ControllerAudioManager controllerAudioManager;

    public ControllerCueButton(String id, PlaneRange planeRange, ControllerAudioManager controllerAudioManager, int channelIndex) {
        super(id, planeRange);
        this.controllerAudioManager = controllerAudioManager;
        this.channelIndex = channelIndex;
    }

    @Override
    public void executeOnServer(double value) {
        controllerAudioManager.loadAudio(0, 0);
        this.markExecute();
        this.markDirty();
    }

    @Override
    public void executeOnClient() {
        Minecraft.getInstance().player.sendSystemMessage(Component.literal("cue button"));
    }
}
