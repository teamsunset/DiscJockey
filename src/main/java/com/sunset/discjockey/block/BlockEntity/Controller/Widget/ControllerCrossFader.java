package com.sunset.discjockey.block.BlockEntity.Controller.Widget;

import com.sunset.discjockey.block.BlockEntity.Controller.Audio.ControllerAudio;
import com.sunset.discjockey.block.BlockEntity.Controller.Audio.ControllerAudioManager;
import com.sunset.discjockey.block.BlockEntity.Controller.Widget.AbstractWidget.ControllerFader;
import com.sunset.discjockey.util.TouchMap.Vec2Type.PlaneRange;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;

public class ControllerCrossFader extends ControllerFader {

    public ControllerAudioManager controllerAudioManager;

    public ControllerSideMixFader leftMixFader;
    public ControllerSideMixFader rightMixFader;

    public ControllerCrossFader(String id, PlaneRange planeRange, ControllerAudioManager controllerAudioManager, ControllerSideMixFader leftMixFader, ControllerSideMixFader rightMixFader) {
        super(id, planeRange);
        this.controllerAudioManager = controllerAudioManager;
        this.leftMixFader = leftMixFader;
        this.rightMixFader = rightMixFader;
    }

    @Override
    public void executeOnServer(Player player, double value) {
        super.executeOnServer(player, value);
        int rateNum = (int) ((this.value.getTarget() + 1.0D) / 2 * 100);
        player.displayClientMessage(Component.literal("cross mix: " + rateNum + "% / " + (100 - rateNum) + "%"), true);
    }

    @Override
    public void executeOnClient() {

    }

    @Override
    public void onServerTick() {
        super.onServerTick();
    }

    @Override
    public void onClientTick() {
        super.onClientTick();
        double rate = ((this.value.get() + 1.0D) / 2);
        ControllerAudio leftAudio = this.controllerAudioManager.loadedAudios.get(0);
        ControllerAudio rightAudio = this.controllerAudioManager.loadedAudios.get(1);
        if (leftAudio != null && leftAudio.speakerSound != null) {
            leftAudio.speakerSound.setVolume((float) (rate * this.leftMixFader.value.get() * 2));
        }
        if (rightAudio != null && rightAudio.speakerSound != null) {
            rightAudio.speakerSound.setVolume((float) ((1 - rate) * this.rightMixFader.value.get() * 2));
        }
    }
}
