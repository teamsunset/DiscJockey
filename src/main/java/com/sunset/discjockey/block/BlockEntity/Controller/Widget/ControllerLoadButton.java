package com.sunset.discjockey.block.BlockEntity.Controller.Widget;

import com.sunset.discjockey.block.BlockEntity.Controller.Audio.ControllerAudioManager;
import com.sunset.discjockey.block.BlockEntity.Controller.Widget.AbstractWidget.ControllerButton;
import com.sunset.discjockey.util.TouchMap.Vec2Type.PlaneRange;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public class ControllerLoadButton extends ControllerButton {

    public int loadIndex;
    public int channelIndex;
    public ControllerAudioManager controllerAudioManager;

    public ControllerLoadButton(String id, PlaneRange planeRange, ControllerAudioManager controllerAudioManager, int loadIndex, int channelIndex) {
        super(id, planeRange);
        this.controllerAudioManager = controllerAudioManager;
        this.loadIndex = loadIndex;
        this.channelIndex = channelIndex;
    }

    @Override
    public void executeOnServer(Player player, double value) {
        super.executeOnServer(player, value);
        controllerAudioManager.loadAudio(this.loadIndex, this.channelIndex);
        player.displayClientMessage(Component.literal("The " + this.loadIndex + " st audio has been loaded to " + this.channelIndex + " channel"), true);
    }

    @Override
    public void executeOnClient() {

    }
}
