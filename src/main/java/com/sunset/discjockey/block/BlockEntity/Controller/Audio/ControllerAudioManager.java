package com.sunset.discjockey.block.BlockEntity.Controller.Audio;

import com.sunset.discjockey.block.BlockEntity.Controller.AbstractController;
import com.sunset.discjockey.network.message.ControllerSyncMessage;
import com.sunset.discjockey.network.message.TagMessage;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.network.NetworkEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import static com.sunset.discjockey.network.NetworkHandler.NETWORK_CHANNEL;

public class ControllerAudioManager {
    public static Vector<ControllerAudioManager> MANAGERS = new Vector<>();

    public AbstractController controller;

    //selected index should be used widget "knob"
    public Vector<String> audios = new Vector<>();

    //channel,ControllerAudio
    public Map<Integer, ControllerAudio> loadedAudios = new HashMap<>();

    public ControllerAudioManager(AbstractController controller) {
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
                audios.add(tag.getAsString());
            }
        }
        this.audios = audios;

        CompoundTag loadAudiosTag = compoundTag.getCompound("loadedAudios");
        for (String key : loadAudiosTag.getAllKeys()) {
            if (loadedAudios.get(Integer.parseInt(key)) == null) {
                loadedAudios.put(Integer.parseInt(key), new ControllerAudio(this, loadAudiosTag.getCompound(key).getString("url")));
            }
            loadedAudios.get(Integer.parseInt(key)).writeCompoundTag(loadAudiosTag.getCompound(key));
        }
    }

    public void loadAudio(int index, int channelIndex) {
        loadedAudios.put(channelIndex, new ControllerAudio(this, audios.get(index)));
    }

    public void unloadAudio(int channelIndex) {
        loadedAudios.remove(channelIndex);
    }

    public void onServerTick(TickEvent.ServerTickEvent event) {
        for (ControllerAudio controllerAudio : loadedAudios.values()) {
            controllerAudio.onServerTick(event);
        }
    }
}
