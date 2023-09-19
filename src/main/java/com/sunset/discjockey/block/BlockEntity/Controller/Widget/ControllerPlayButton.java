package com.sunset.discjockey.block.BlockEntity.Controller.Widget;

import com.sunset.discjockey.block.BlockEntity.Controller.Audio.ControllerAudio;
import com.sunset.discjockey.block.BlockEntity.Controller.Audio.ControllerAudioManager;
import com.sunset.discjockey.block.BlockEntity.Controller.Widget.AbstractWidget.ControllerButton;
import com.sunset.discjockey.util.TouchMap.Vec2Type.PlaneRange;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public class ControllerPlayButton extends ControllerButton {
    public int channelIndex;
    public ControllerAudioManager controllerAudioManager;

    public ControllerPlayButton(String id, PlaneRange planeRange, ControllerAudioManager controllerAudioManager, int channelIndex) {
        super(id, planeRange);
        this.controllerAudioManager = controllerAudioManager;
        this.channelIndex = channelIndex;
    }

    @Override
    public void executeOnServer(Player player, double value, boolean condition) {
        super.executeOnServer(player, value, condition);
        ControllerAudio audio = controllerAudioManager.loadedAudios.get(channelIndex);
        if (audio != null) {
            audio.isPlayingOnServer = !audio.isPlayingOnServer;
            player.displayClientMessage(Component.literal("The " + channelIndex + " st channel is " + (audio.isPlayingOnServer ? "playing" : "pause")), true);
        } else {
            player.displayClientMessage(Component.literal("The " + channelIndex + " st channel is empty"), true);
        }
    }

    @Override
    public void executeOnClient() {
    }
}
