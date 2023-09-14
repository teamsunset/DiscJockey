package com.sunset.discjockey.block.BlockEntity.Controller.Widget;

import com.sunset.discjockey.block.BlockEntity.Controller.Audio.ControllerAudio;
import com.sunset.discjockey.block.BlockEntity.Controller.Audio.ControllerAudioManager;
import com.sunset.discjockey.block.BlockEntity.Controller.Widget.AbstractWidget.ControllerFader;
import com.sunset.discjockey.util.TouchMap.Vec2Type.PlaneRange;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public class ControllerSideMixFader extends ControllerFader {
    public ControllerAudioManager controllerAudioManager;
    public int channelIndex;

    public ControllerSideMixFader(String id, PlaneRange planeRange, ControllerAudioManager controllerAudioManager, int channelIndex) {
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
    public void executeOnServer(Player player, double value) {
        super.executeOnServer(player, value);
        this.value.setTarget((value + 1) * 0.5);
        player.displayClientMessage(Component.literal((this.channelIndex == 0 ? "left" : "right") + " mix: " + (int) (this.value.getTarget() * 100) + "%"), true);
    }

    @Override
    public void executeOnClient() {
        super.executeOnClient();
    }
}
