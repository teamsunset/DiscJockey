package com.sunset.discjockey.block.BlockEntity.Controller.Audio;

import com.sunset.discjockey.block.BlockEntity.Controller.AbstractController;
import com.sunset.discjockey.network.message.TagMessage;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.network.NetworkEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class ControllerAudioManager {
    public static Vector<ControllerAudioManager> MANAGERS = new Vector<>();

    public AbstractController controller;

    //selected index should be used widget "knob"
    public Vector<String> audios = new Vector<>();

    //channel,ControllerAudio
    public Map<Integer, ControllerAudio> loadedAudios = new HashMap<>();

    public ControllerAudioManager(AbstractController controller) {
        this.controller = controller;
        MinecraftForge.EVENT_BUS.register(this);
    }

    public ControllerAudioManager(CompoundTag compoundTag) {
    }

    public CompoundTag getCompoundTag() {
        CompoundTag compoundTag = new CompoundTag();

        ListTag loadAudiosTag = new ListTag();
        for (ControllerAudio controllerAudio : loadedAudios.values()) {
            loadAudiosTag.add(controllerAudio.getCompoundTag());
        }
        compoundTag.put("loadedAudios", loadAudiosTag);
        return compoundTag;
    }

    public void writeCompoundTag(CompoundTag compoundTag) {

    }

    public void loadAudio(int index, int channelIndex) {
        loadedAudios.put(channelIndex, new ControllerAudio(this, audios.get(index)));
    }

    public void unloadAudio(int channelIndex) {
        loadedAudios.remove(channelIndex);
    }

    @SubscribeEvent
    public void onLevelTick(TickEvent.LevelTickEvent event) {
        //sync data from client to server
    }


    public static void handleTagMessage(TagMessage message, NetworkEvent.Context context) {

    }
}
