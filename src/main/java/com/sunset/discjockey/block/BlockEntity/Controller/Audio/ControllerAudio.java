package com.sunset.discjockey.block.BlockEntity.Controller.Audio;

import com.sunset.discjockey.client.audio.SpeakerSound;
import com.sunset.discjockey.util.MusicMisc.MusicFileManager;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.concurrent.CompletableFuture;

public class ControllerAudio
{
    public ControllerAudioManager manager;
    public String url;

    public SpeakerSound speakerSound = null;//only on level clientside

    public boolean isPlayingOnServer = false;

    public int elapsedTimeOnServer = 0;


    public ControllerAudio(ControllerAudioManager controllerAudioManager, String url) {
        this.manager = controllerAudioManager;
        this.url = url;
    }

    public CompoundTag getCompoundTag() {
        CompoundTag compoundTag = new CompoundTag();
        compoundTag.putString("url", this.url);
        return compoundTag;
    }

    public void writeCompoundTag(CompoundTag compoundTag) {
        this.url = compoundTag.getString("url");
    }

    @OnlyIn(Dist.CLIENT)
    public void setupOnClient() {
        CompletableFuture.runAsync(() ->
//                        Minecraft.getInstance().submitAsync(() ->
                {
                    try {
                        if (!MusicFileManager.checkURL(this.url)) {
                            throw new Exception("Can't Play it!");
                        }
                        this.speakerSound = new SpeakerSound(this.manager.controller.getBlockPos(), this.url);
                        this.speakerSound.elapsedTime.setValue(this.elapsedTimeOnServer);
                        Minecraft.getInstance().getSoundManager().play(this.speakerSound);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
//                        )
                , Util.backgroundExecutor());
    }

}
