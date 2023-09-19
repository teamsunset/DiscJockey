package com.sunset.discjockey.block.BlockEntity.Controller.Widget;

import com.sunset.discjockey.block.BlockEntity.Controller.Audio.ControllerAudioManager;
import com.sunset.discjockey.block.BlockEntity.Controller.Widget.AbstractWidget.ControllerButton;
import com.sunset.discjockey.util.TouchMap.Vec2Type.PlaneRange;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public class ControllerCueButton extends ControllerButton {
    public int channelIndex;
    public ControllerAudioManager controllerAudioManager;

    public ControllerCueButton(String id, PlaneRange planeRange, ControllerAudioManager controllerAudioManager, int channelIndex) {
        super(id, planeRange);
        this.controllerAudioManager = controllerAudioManager;
        this.channelIndex = channelIndex;
    }

    @Override
    public void executeOnServer(Player player, double value, boolean condition) {
        super.executeOnServer(player, value, condition);
        player.displayClientMessage(Component.literal("cue button"), true);
    }

    @Override
    public void executeOnClient() {
    }
}
