package com.sunset.discjockey.block.BlockEntity.Controller.Audio;

import net.minecraft.nbt.CompoundTag;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class ControllerAudioManager
{

    //selected index should be used widget "knob"
    public Vector<String> audios = new Vector<>();

    //channel,ControllerAudio
    public Map<Integer, ControllerAudio> loadedAudios = new HashMap<>();

    public ControllerAudioManager(CompoundTag compoundTag) {
    }

    public CompoundTag getCompoundTag() {
        CompoundTag compoundTag = new CompoundTag();
//        ListTag audiosCompoundTag = new ListTag();
//        audios.forEach(i -> audiosCompoundTag.add(audios.indexOf(i), i.getCompoundTag()));
//
//        CompoundTag loadedAudioIndexesCompoundTag = new CompoundTag();
//        for (Integer i : loadedAudioIndexes.keySet()) {
//            loadedAudioIndexesCompoundTag.putInt(String.valueOf(i), loadedAudioIndexes.get(i));
//        }
//        compoundTag.put("audios", audiosCompoundTag);
//        compoundTag.put("loadedAudioIndexes", loadedAudioIndexesCompoundTag);
        return compoundTag;
    }

    public void writeCompoundTag(CompoundTag compoundTag) {
//        ListTag audiosCompoundTag = compoundTag.getList("audios", Tag.TAG_COMPOUND);
//        audios.ensureCapacity(audiosCompoundTag.size());
//        audios.setSize(audiosCompoundTag.size());
//        audios.forEach(i -> {
//            CompoundTag audioCompoundTag = audiosCompoundTag.getCompound(audios.indexOf(i));
//            if (i == null)
//                i = new ControllerAudio(audioCompoundTag);
//            else
//                i.writeCompoundTag(audioCompoundTag);
//        });
//
//        //without delete
//        CompoundTag loadedAudioIndexesCompoundTag = compoundTag.getCompound("loadedAudioIndexes");
//        for (String index : loadedAudioIndexesCompoundTag.getAllKeys()) {
//            int i = Integer.parseInt(index);
//            loadedAudioIndexes.put(i, audiosCompoundTag.getInt(i));
//        }
    }

    public void load(int index, int channelIndex) {
        loadedAudios.put(channelIndex, new ControllerAudio(audios.get(index)));
    }
}
