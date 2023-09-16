package com.sunset.discjockey.block.BlockEntity.Controller.Audio;

import com.sunset.discjockey.block.BlockEntity.Controller.AbstractControllerEntity;
import com.sunset.discjockey.util.MusicMisc.MusicFileManager;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class ControllerAudioManager {
    public static Vector<ControllerAudioManager> MANAGERS = new Vector<>();

    public AbstractControllerEntity controller;

    //selected index should be used widget "knob"
    public Vector<String> audios = new Vector<>();

    //channel,ControllerAudio
    public Map<Integer, ControllerAudio> loadedAudios = new HashMap<>();

    public ControllerAudioManager(AbstractControllerEntity controller) {
        this.controller = controller;
    }

    public ControllerAudioManager(CompoundTag compoundTag) {
    }

    public CompoundTag getCompoundTag() {
        CompoundTag compoundTag = new CompoundTag();

        ListTag audiosTag = new ListTag();
        for (String audio : audios) {
            audiosTag.add(StringTag.valueOf(audio));
        }
        compoundTag.put("audios", audiosTag);

        CompoundTag loadAudiosTag = new CompoundTag();
        for (int key : loadedAudios.keySet()) {
            loadAudiosTag.put(String.valueOf(key), loadedAudios.get(key).getCompoundTag());
        }
        compoundTag.put("loadedAudios", loadAudiosTag);
        return compoundTag;
    }

    public void writeCompoundTag(CompoundTag compoundTag) {
        ListTag audiosTag = (ListTag) compoundTag.get("audios");
        Vector<String> audios = new Vector<>();
        if (audiosTag != null) {
            for (Tag tag : audiosTag) {
                DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> MusicFileManager.loadURLToCache(tag.getAsString()));
                audios.add(tag.getAsString());
            }
        }
        this.audios = audios;

        CompoundTag loadAudiosTag = compoundTag.getCompound("loadedAudios");
        for (int channelIndex : loadedAudios.keySet()) {
            if (!loadAudiosTag.contains(String.valueOf(channelIndex))) {
                this.unloadAudio(channelIndex);
            }
        }
        for (String key : loadAudiosTag.getAllKeys()) {
            if (loadedAudios.get(Integer.parseInt(key)) == null || !loadedAudios.get(Integer.parseInt(key)).url.equals(loadAudiosTag.getCompound(key).getString("url"))) {
                this.unloadAudio(Integer.parseInt(key));
                loadedAudios.put(Integer.parseInt(key), new ControllerAudio(this, loadAudiosTag.getCompound(key).getString("url")));
            }
            loadedAudios.get(Integer.parseInt(key)).writeCompoundTag(loadAudiosTag.getCompound(key));
        }
    }

    public boolean loadAudio(int index, int channelIndex) {
        this.unloadAudio(channelIndex);
        if (audios.size() > index) {
            loadedAudios.put(channelIndex, new ControllerAudio(this, audios.get(index)));
            return true;
        } else {
            return false;
        }
    }

    public void unloadAudio(int channelIndex) {
        if (loadedAudios.get(channelIndex) != null) {
            loadedAudios.get(channelIndex).terminate();
            loadedAudios.remove(channelIndex);
        }
    }

    public void resetAudio() {
        audios.clear();
        for (ControllerAudio controllerAudio : loadedAudios.values())
            controllerAudio.terminate();
    }

    public void onServerTick() {
        for (ControllerAudio controllerAudio : loadedAudios.values()) {
            controllerAudio.onServerTick();
        }
    }

    public void onClientTick() {
        for (ControllerAudio controllerAudio : loadedAudios.values()) {
            controllerAudio.onClientTick();
        }
    }
}
