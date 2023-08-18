package com.sunset.discjockey.client.audio;

import com.sunset.discjockey.util.MusicMisc.MusicFileManager;
import net.minecraft.client.sounds.AudioStream;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.BufferUtils;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

//should be async
public class FileAudioStream implements AudioStream
{
    public final AudioInputStream stream;
    public final byte[] array;
    public int offset;

    public FileAudioStream(String url) {
        this.stream = MusicFileManager.getMusicAudioInputStream(url);
        this.array = MusicFileManager.getMusicBytes(url);
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
        size = 4000;
        ByteBuffer byteBuffer = BufferUtils.createByteBuffer(size);
        if (array.length >= offset + size) {
            byteBuffer.put(array, offset, size);
        }
        else {
            byteBuffer.put(new byte[size]);
        }
        offset += size;
        byteBuffer.flip();
        return byteBuffer;
    }

    @Override
    public void close() throws IOException {
        stream.close();
    }
}
