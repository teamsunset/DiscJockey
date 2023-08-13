package com.sunset.discjockey.block.BlockEntity.Controller.Audio;

import net.minecraft.nbt.CompoundTag;

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
}
