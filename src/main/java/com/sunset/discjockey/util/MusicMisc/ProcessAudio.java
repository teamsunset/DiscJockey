package com.sunset.discjockey.util.MusicMisc;


import javax.sound.sampled.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class ProcessAudio {

    // 复制
    public static byte[] copy(byte[] data) {
//        int len = data.length;
//        byte[] newData = new byte[len];
//        System.arraycopy(data, 0, newData, 0, len);
//        return newData;
        return data.clone();
    }

    //倒放
    public static byte[] reverse(byte[] data) {
        byte[] newData = data.clone();
        for (int i = 0; i < data.length; i += 2) {
            newData[i] = data[data.length - i - 2];
            newData[i + 1] = data[data.length - i - 1];
        }
        return newData;
    }

    public static byte[] changeSpeedByResampled(byte[] data, float speed) {
        if (speed == 0) {
            return new byte[data.length];
        } else if (speed == 1) {
            return data.clone();
        }

        byte[] origin = data.clone();
        if (speed < 0) {
            origin = reverse(data);
            speed *= -1;
        }

        int newLength = (int) (origin.length / speed);
        if (newLength % 2 != 0) {
            newLength++; // Make sure the new length is even for 16-bit PCM data
        }

        byte[] resampled = new byte[newLength];

        if (speed < 1) {
            //first short
            if (origin.length > 1) {
                resampled[0] = origin[0];
                resampled[1] = origin[1];
                short pre = (short) ((resampled[0] & 0xff) | (resampled[1] << 8));
                for (int i = 2; i < origin.length; i += 2) {
                    int resampledIndex = (int) (i / speed);
                    resampled[resampledIndex] = origin[i];
                    resampled[resampledIndex + 1] = origin[i + 1];
                    short cur = (short) ((resampled[resampledIndex] & 0xff) | (resampled[resampledIndex + 1] << 8));

                    for (int j = 0; j < resampledIndex - i; j += 2) {
                        resampled[resampledIndex + j] = (byte) (pre & 0xff);
                        resampled[resampledIndex + j + 1] = (byte) (pre >> 8);
                    }
                }
            }

        } else {
            for (int i = 0; i < newLength; i += 2) {
                int sampleIndex = (int) (i * speed);

                if (sampleIndex >= origin.length - 2) {
                    // If we are at or beyond the last sample, just copy it
                    resampled[i] = origin[origin.length - 2];
                    resampled[i + 1] = origin[origin.length - 1];
                } else {
                    resampled[i] = origin[sampleIndex];
                }
            }
        }
        return resampled;
    }

    // 变速效果
    public static void changeSpeedByResampled(AudioInputStream audioInputStream, float speed) {

    }

    public static void main(String[] args) throws LineUnavailableException, IOException, InterruptedException {
        byte[] array = MusicFileManager.getMusicBytes("netease:1404596131");
        //倒放
        byte[] reverseArray = reverse(array);

        //output format
        System.out.println(MusicFileManager.getMusicAudioInputStream("netease:1404596131").getFormat());
        AudioFormat format = new AudioFormat(44100, 16, 1, true, false);
        System.out.println(format);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(reverseArray);
        AudioInputStream audioInputStream = new AudioInputStream(byteArrayInputStream, format, reverseArray.length / format.getFrameSize());

        Clip clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        clip.start();
        Thread.sleep(clip.getMicrosecondLength() / 1000);
    }
}
