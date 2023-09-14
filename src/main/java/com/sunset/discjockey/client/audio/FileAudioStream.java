package com.sunset.discjockey.client.audio;

import com.sunset.discjockey.DiscJockey;
import com.sunset.discjockey.util.MusicMisc.MusicFileManager;
import com.sunset.discjockey.util.SpecialType.Property;
import net.minecraft.client.sounds.AudioStream;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.BufferUtils;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

//should be async

@OnlyIn(Dist.CLIENT)
public class FileAudioStream implements AudioStream {
    public final AudioInputStream stream;
    public final byte[] array;
    public int offset;

    public final int tickSize;

    public boolean isPlaying = false;

    public boolean isStreamClosed = false;

    public FileAudioStream(String url) {
        this.stream = MusicFileManager.getMusicAudioInputStream(url);
        this.array = MusicFileManager.getMusicBytes(url);
        this.tickSize = this.array.length / MusicFileManager.getSongTime(url);
        this.offset = 0;
    }

    @NotNull
    @Override
    public AudioFormat getFormat() {
        return stream.getFormat();
    }

    @NotNull
    @Override
    public ByteBuffer read(int size) {
        size = this.tickSize;
        ByteBuffer byteBuffer = BufferUtils.createByteBuffer(size);
        if (this.isPlaying && array.length >= offset + size) {
            byteBuffer.put(array, offset, size);
            offset += size;
        } else {
            byteBuffer.put(new byte[size]);
        }
        byteBuffer.flip();
        return byteBuffer;
    }

    @Override
    public void close() throws IOException {
        this.isStreamClosed = true;
        stream.close();
    }
}
