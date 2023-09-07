package com.sunset.discjockey.block.BlockEntity.Controller.Audio;

import com.sunset.discjockey.block.BlockEntity.Controller.AbstractController;
import com.sunset.discjockey.network.message.TagMessage;
import net.minecraft.nbt.CompoundTag;
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
        MinecraftForge.EVENT_BUS.register(ControllerAudioManager.class);
    }

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

    public void loadAudio(int index, int channelIndex) {
        loadedAudios.put(channelIndex, new ControllerAudio(this, audios.get(index)));
    }

    public void unloadAudio(int channelIndex) {
        loadedAudios.remove(channelIndex);
    }

//    @SubscribeEvent
//    public static void onLevelTick(TickEvent.LevelTickEvent event) {
//        //sync data from client to server
//        if (event.level.isClientSide() && event.phase == TickEvent.Phase.END) {
//            CompoundTag compoundTag = new CompoundTag();
//            for (ControllerAudioManager manager : MANAGERS) {
//
//                CompoundTag managerCompoundTag = new CompoundTag();
//                CompoundTag dataCompoundTag = new CompoundTag();
//
//                for (Integer channel : manager.loadedAudios.keySet()) {
//                    ControllerAudio audio = manager.loadedAudios.get(channel);
//                    CompoundTag audioCompoundTag = new CompoundTag();
//                    if (audio != null) {
//                        audioCompoundTag.putInt("elapsed_time", audio.speakerSound.elapsedTime.getValue());
//                    }
//                    dataCompoundTag.put(String.valueOf(channel), audioCompoundTag);
//                }
//                BlockPos pos = manager.controller.getBlockPos();
//                managerCompoundTag.put("Pos", new IntArrayTag(new int[]{pos.getX(), pos.getY(), pos.getZ()}));
//                managerCompoundTag.put("data", dataCompoundTag);
//                compoundTag.put(String.valueOf(MANAGERS.indexOf(manager)), managerCompoundTag);
//            }
//            NetworkHandler.NETWORK_CHANNEL.sendToServer(new TagMessage(compoundTag));
//        }
//    }

    @SubscribeEvent
    public void onLevelTick(TickEvent.LevelTickEvent event) {
        //sync data from client to server
    }


    public static void handleTagMessage(TagMessage message, NetworkEvent.Context context) {

    }
}
