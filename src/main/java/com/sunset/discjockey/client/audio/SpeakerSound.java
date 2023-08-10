package com.sunset.discjockey.client.audio;

import com.sunset.discjockey.block.BlockEntity.BlockEntityDDJ400;
import com.sunset.discjockey.util.RegistryCollection.SoundEventCollection;
import com.sunset.discjockey.util.MusicMisc.MusicFileManager;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.Sound;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.AudioStream;
import net.minecraft.client.sounds.SoundBufferLibrary;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.concurrent.CompletableFuture;

@OnlyIn(Dist.CLIENT)
public class SpeakerSound extends AbstractTickableSoundInstance
{
    public FileAudioStream fileAudioStream;
    public final String songUrl;

    public int songTimeByTick = -1;
    public final BlockPos pos;
    public int tick = 0;


    public SpeakerSound(BlockPos pos, String songUrl) {
        super(SoundEventCollection.SOUND_EVENT.get(), SoundSource.RECORDS, SoundInstance.createUnseededRandom());
        this.songUrl = songUrl;
        this.fileAudioStream = new FileAudioStream(this.songUrl);
        this.songTimeByTick = MusicFileManager.getSongTimeBySecond(songUrl) * 20;
        this.x = pos.getX() + 0.5f;
        this.y = pos.getY() + 0.5f;
        this.z = pos.getZ() + 0.5f;
        this.pos = pos;
        this.volume = 0.3f;
        this.tick = 0;
    }

    @Override
    public void tick() {
        Level world = Minecraft.getInstance().level;
        BlockEntity blockEntity = world.getBlockEntity(this.pos);
        if (blockEntity instanceof BlockEntityDDJ400 blockEntityDDJ400) {
            if (blockEntityDDJ400.getPlaying()) {
                tick++;
                if (songTimeByTick != -1 && tick > songTimeByTick + 50) {
                    this.stop();
                }
            } else {
                this.stop();
            }
        } else {
            this.stop();
        }
    }

    @Override
    public CompletableFuture<AudioStream> getStream(SoundBufferLibrary soundBuffers, Sound sound, boolean looping) {
        return CompletableFuture.supplyAsync(() -> {
            return this.fileAudioStream;
        }, Util.backgroundExecutor());
    }
}
