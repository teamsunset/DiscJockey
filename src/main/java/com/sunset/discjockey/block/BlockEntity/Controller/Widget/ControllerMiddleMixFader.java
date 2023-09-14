package com.sunset.discjockey.block.BlockEntity.Controller.Widget;

import com.sunset.discjockey.block.BlockEntity.Controller.Audio.ControllerAudio;
import com.sunset.discjockey.block.BlockEntity.Controller.Audio.ControllerAudioManager;
import com.sunset.discjockey.block.BlockEntity.Controller.Widget.AbstractWidget.ControllerFader;
import com.sunset.discjockey.util.TouchMap.Vec2Type.PlaneRange;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;

public class ControllerMiddleMixFader extends ControllerFader {

    public ControllerAudioManager controllerAudioManager;

    public ControllerSideMixFader leftMixFader;
    public ControllerSideMixFader rightMixFader;

    public ControllerMiddleMixFader(String id, PlaneRange planeRange, ControllerAudioManager controllerAudioManager, ControllerSideMixFader leftMixFader, ControllerSideMixFader rightMixFader) {
        super(id, planeRange);
        this.controllerAudioManager = controllerAudioManager;
        this.leftMixFader = leftMixFader;
        this.rightMixFader = rightMixFader;
    }

    @Override
    public void executeOnServer(Player player, double value) {
        super.executeOnServer(player, value);
        player.displayClientMessage(Component.literal("middle mix rate set: " + (int) (this.value.getTarget() * 100) + "%"), true);
    }

    @Override
    public void executeOnClient() {

    }

    @Override
    public void onServerTick(TickEvent.ServerTickEvent event) {
        super.onServerTick(event);
    }

    @Override
    public void onClientTick(TickEvent.ClientTickEvent event) {
        super.onClientTick(event);
        ControllerAudio leftAudio = this.controllerAudioManager.loadedAudios.get(0);
        ControllerAudio rightAudio = this.controllerAudioManager.loadedAudios.get(1);
        if (leftAudio != null && leftAudio.speakerSound != null) {
            leftAudio.speakerSound.setVolume((float) (this.value.get() * this.leftMixFader.value.get()));
        }
        if (rightAudio != null && rightAudio.speakerSound != null) {
            rightAudio.speakerSound.setVolume((float) (this.value.get() * this.rightMixFader.value.get()));
        }
    }
}
