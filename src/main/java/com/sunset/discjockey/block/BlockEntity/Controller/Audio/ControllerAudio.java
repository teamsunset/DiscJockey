package com.sunset.discjockey.block.BlockEntity.Controller.Audio;

import com.sunset.discjockey.client.audio.SpeakerSound;
import com.sunset.discjockey.util.MusicMisc.MusicFileManager;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.concurrent.CompletableFuture;

public class ControllerAudio
{
    public String url;

    public int elapsedTime = 0;

    public ControllerAudio(String url) {
        this.url = url;
    }

    public ControllerAudio(CompoundTag compoundTag) {
        this.writeCompoundTag(compoundTag);
    }

    public CompoundTag getCompoundTag() {
        CompoundTag compoundTag = new CompoundTag();
        compoundTag.putString("url", this.url);
        compoundTag.putInt("elapsed_time", this.elapsedTime);
        return compoundTag;
    }

    public void writeCompoundTag(CompoundTag compoundTag) {
        this.url = compoundTag.getString("url");
        this.elapsedTime = compoundTag.getInt("elapsed_time");
    }

    //level clientside

    public SpeakerSound speakerSound = null;

    @OnlyIn(Dist.CLIENT)
    public void play() {
        CompletableFuture.runAsync(() ->
//                        Minecraft.getInstance().submitAsync(() ->
                {
                    try {
                        if (!MusicFileManager.checkURL(this.url)) {
                            throw new Exception("Can't Play it!");
                        }
//                        this.speakerSound = new SpeakerSound(this.getBlockPos(), this.url);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return;
                    }
                    Minecraft.getInstance().getSoundManager().play(this.speakerSound);
                }
//                        )
                , Util.backgroundExecutor());
    }

    @OnlyIn(Dist.CLIENT)
    public void pause() {

    }
}
