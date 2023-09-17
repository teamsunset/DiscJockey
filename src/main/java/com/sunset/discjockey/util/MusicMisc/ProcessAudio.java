package com.sunset.discjockey.util.MusicMisc;


import javax.sound.sampled.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class ProcessAudio {

    // 复制
    public static byte[] copy(byte[] data) {
        int len = data.length;
        byte[] newData = new byte[len];
        System.arraycopy(data, 0, newData, 0, len);
        return newData;
    }

    //倒放
    public static byte[] reverse(byte[] data) {
        byte[] newData = copy(data);
        for (int i = 0; i < data.length; i += 2) {
            newData[i] = data[data.length - i - 2];
            newData[i + 1] = data[data.length - i - 1];
        }
        return newData;
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
