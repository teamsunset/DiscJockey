package com.sunset.discjockey.block.BlockEntity.Controller.Widget;

import com.sunset.discjockey.block.BlockEntity.Controller.Audio.ControllerAudio;
import com.sunset.discjockey.block.BlockEntity.Controller.Audio.ControllerAudioManager;
import com.sunset.discjockey.block.BlockEntity.Controller.Widget.AbstractWidget.ControllerButton;
import com.sunset.discjockey.util.TouchMap.Vec2Type.PlaneRange;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

public class ControllerPlayButton extends ControllerButton {
    public int channelIndex;
    public ControllerAudioManager controllerAudioManager;

    public ControllerPlayButton(String id, PlaneRange planeRange, ControllerAudioManager controllerAudioManager, int channelIndex) {
        super(id, planeRange);
        this.controllerAudioManager = controllerAudioManager;
        this.channelIndex = channelIndex;
    }

    @Override
    public void executeOnServer(double value) {
        super.executeOnServer(value);
        ControllerAudio audio = controllerAudioManager.loadedAudios.get(channelIndex);
        if (audio != null) {
            audio.isPlayingOnServer = !audio.isPlayingOnServer;
        }
    }

    @Override
    public void executeOnClient() {
        Minecraft.getInstance().player.sendSystemMessage(Component.literal("play button"));
    }
}
