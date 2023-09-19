package com.sunset.discjockey.block.BlockEntity.Controller.Audio;

import com.sunset.discjockey.DiscJockey;
import com.sunset.discjockey.client.audio.SpeakerSound;
import com.sunset.discjockey.util.MusicMisc.MusicFileManager;
import com.sunset.discjockey.util.SpecialType.OneShotBoolean;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;

import java.util.concurrent.CompletableFuture;

public class ControllerAudio {
    public ControllerAudioManager manager;
    public String url;

    public SpeakerSound speakerSound = null;//only on clientside

    public boolean isPlayingOnServer = false;

    public double elapsedTimeOnServer = 0;

    public int songTime = -1;

    public OneShotBoolean notSetSongTime = new OneShotBoolean(true);

    public double speed = 1;


    public ControllerAudio(ControllerAudioManager controllerAudioManager, String url) {
        this.manager = controllerAudioManager;
        this.url = url;
        if (this.manager.controller.hasLevel() && this.manager.controller.getLevel().isClientSide()) {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> this::setupOnClient);
        }
    }

    public CompoundTag getCompoundTag() {
        CompoundTag compoundTag = new CompoundTag();
        compoundTag.putString("url", this.url);
        compoundTag.putBoolean("isPlayingOnServer", this.isPlayingOnServer);
        compoundTag.putDouble("elapsedTimeOnServer", this.elapsedTimeOnServer);
        compoundTag.putInt("songTime", this.songTime);
        compoundTag.putDouble("speed", this.speed);
        return compoundTag;
    }

    public void writeCompoundTag(CompoundTag compoundTag) {
        this.url = compoundTag.getString("url");
        this.isPlayingOnServer = compoundTag.getBoolean("isPlayingOnServer");
        this.elapsedTimeOnServer = compoundTag.getInt("elapsedTimeOnServer");
        this.songTime = compoundTag.getInt("songTime");
        this.speed = compoundTag.getDouble("speed");

        if (this.speakerSound != null) {
            this.speakerSound.isPlaying = this.isPlayingOnServer;
            if (this.speed == 1 && Math.abs(this.elapsedTimeOnServer - this.speakerSound.elapsedTime.get()) > 10) {
                DiscJockey.DEBUG_LOGGER.debug(String.valueOf(this.elapsedTimeOnServer - this.speakerSound.elapsedTime.get()));
                this.speakerSound.elapsedTime.set(this.elapsedTimeOnServer);
            }
            this.speakerSound.speed.set(this.speed);
        }
    }

    public void terminate() {
        if (this.speakerSound != null) {
            this.speakerSound.terminate();
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void setupOnClient() {
        CompletableFuture.runAsync(() ->
                {
                    try {
                        if (!MusicFileManager.checkURL(this.url)) {
                            throw new Exception("Can't Play it!");
                        }
                        this.speakerSound = new SpeakerSound(this.manager.controller.getBlockPos(), this.url);
                        this.speakerSound.elapsedTime.set(this.elapsedTimeOnServer);
                        Minecraft.getInstance().submitAsync(() -> {
                            Minecraft.getInstance().getSoundManager().play(this.speakerSound);
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                , Util.backgroundExecutor());
    }

    public void onServerTick() {
        if (this.isPlayingOnServer) {
            if (speed > 0 || (speed < 0 && this.elapsedTimeOnServer > speed))
                this.elapsedTimeOnServer += speed;
        }

        if (this.songTime != -1 && this.elapsedTimeOnServer > this.songTime) {
            this.elapsedTimeOnServer = 0;
            this.speed = 1;
            this.isPlayingOnServer = false;
        }
        this.manager.controller.markDirty();
    }

    public void onClientTick() {
        if (this.speakerSound != null && this.speakerSound.fileAudioStream != null && this.speakerSound.fileAudioStream.isStreamClosed.get()) {
            this.speakerSound.terminate();
            this.setupOnClient();
        }
    }
}
