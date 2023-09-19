package com.sunset.discjockey.client.audio;

import com.sunset.discjockey.util.MusicMisc.MusicFileManager;
import com.sunset.discjockey.util.MusicMisc.ProcessAudio;
import com.sunset.discjockey.util.SpecialType.OneShotBoolean;
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
    public byte[] array;

    public short[] arrayRaw;

    public byte[] reversedArray;

    public short[] reversedArrayRaw;

    public int offset;

    public int tickSize;

    public boolean isPlaying = false;

    public boolean isReversed = false;

    public OneShotBoolean isStreamClosed = new OneShotBoolean();

    public double speed = 1;

    public FileAudioStream(String url) {
        this.stream = MusicFileManager.getMusicAudioInputStream(url);
        this.array = MusicFileManager.getMusicBytes(url);
        this.arrayRaw = ProcessAudio.pcm16ToShort(this.array);
        this.reversedArray = ProcessAudio.reverse(this.array);
        this.reversedArrayRaw = ProcessAudio.pcm16ToShort(this.reversedArray);
        this.tickSize = this.array.length / MusicFileManager.getSongTime(url);
        this.offset = 0;
    }

    @NotNull
    @Override
    public AudioFormat getFormat() {
        return this.stream.getFormat();
    }

    @NotNull
    @Override
    public ByteBuffer read(int size) {
        size = this.tickSize;
        ByteBuffer byteBuffer = BufferUtils.createByteBuffer(size);


        if (this.offset >= 0 && this.isPlaying && this.isReversed && offset >= size * speed) {
            short[] bufferArrayRaw = new short[(int) (size * speed / 2)];
            System.arraycopy(reversedArrayRaw, (array.length - offset) / 2, bufferArrayRaw, 0, (int) (size * speed / 2));

            bufferArrayRaw = ProcessAudio.changeSpeedByResampled(bufferArrayRaw, speed);
            byteBuffer.put(ProcessAudio.shortToPcm16(bufferArrayRaw), 0, Math.min(size, bufferArrayRaw.length * 2));
            offset -= (int) (size * speed);
        } else if (this.offset >= 0 && this.isPlaying && !this.isReversed && array.length >= offset + size * speed) {
            short[] bufferArrayRaw = new short[(int) (size * speed / 2)];
            System.arraycopy(arrayRaw, offset / 2, bufferArrayRaw, 0, (int) (size * speed / 2));

            bufferArrayRaw = ProcessAudio.changeSpeedByResampled(bufferArrayRaw, speed);
            byteBuffer.put(ProcessAudio.shortToPcm16(bufferArrayRaw), 0, Math.min(size, bufferArrayRaw.length * 2));
            offset += (int) (size * speed);
        } else {
            byteBuffer.put(new byte[size]);
        }
        byteBuffer.flip();
        return byteBuffer;
    }

    @Override
    public void close() throws IOException {
        this.isStreamClosed.set(true);
        stream.close();
    }
}
