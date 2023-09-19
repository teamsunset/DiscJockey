package com.sunset.discjockey.util.MusicMisc;


import com.sunset.discjockey.util.SpecialType.MathMisc;

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
        return shortToPcm16(reverse(pcm16ToShort(data)));
    }

    public static short[] reverse(short[] data) {
        short[] newData = data.clone();
        for (int i = 0; i < data.length; i++) {
            newData[i] = data[data.length - i - 1];
        }
        return newData;
    }

    public static short[] pcm16ToShort(byte[] data) {
        short[] newData = new short[data.length / 2];
        for (int i = 0; i < data.length - 1; i += 2) {
            newData[i / 2] = (short) ((data[i] & 0xff) | (data[i + 1] << 8));
        }
        return newData;
    }

    public static byte[] shortToPcm16(short[] data) {
        byte[] newData = new byte[data.length * 2];
        for (int i = 0; i < data.length; i++) {
            newData[i * 2] = (byte) (data[i] & 0xff);
            newData[i * 2 + 1] = (byte) ((data[i] >> 8) & 0xff);
        }
        return newData;
    }

    public static short[] changeSpeedByResampled(short[] data, double speed) {

        //special condition
        if (speed == 0) {
            return new short[data.length];
        } else if (speed == 1) {
            return data.clone();
        }

        //speed<0
        short[] origin = data.clone();
        if (speed < 0) {
            origin = reverse(data);
            speed *= -1;
        }

        //get new length
        int newLength = (int) (origin.length / speed);

        short[] resampled = new short[newLength];

        if (speed < 1) {
            for (int i = 0; i < origin.length; i++) {
                int preResampleIndex = (int) ((i - 1) / speed);
                int resampledIndex = (int) (i / speed);

                if (resampledIndex < resampled.length - 1) {
                    resampled[resampledIndex] = origin[i];

                    if (i > 0) {
                        short preSample = resampled[preResampleIndex];
                        short curSample = resampled[resampledIndex];

                        for (int j = preResampleIndex + 1; j < resampledIndex; j++) {
                            short newSample = (short) MathMisc.linearInterpolate(preSample, curSample, 1.0D * (j - preResampleIndex) / (curSample - preSample));
                            resampled[j] = newSample;
                        }
                    }
                }
            }

            // 首先，收集需要插值的数据点
//            List<Double> x = new ArrayList<>();
//            List<Double> y = new ArrayList<>();
//
//            for (int i = 0; i < data.length; i++) {
//                int resampledIndex = (int) (i / speed);
//                if (resampledIndex < resampled.length) {
//                    x.add((double) i);
//                    y.add((double) data[i]);
//                }
//            }
//
//            // 使用三次样条插值
//            MathMisc.CubicSpline spline = new MathMisc.CubicSpline(x.stream().mapToDouble(Double::doubleValue).toArray(), y.stream().mapToDouble(Double::doubleValue).toArray());
//
//            // 用插值的结果填充resampled数组
//            for (int i = 0; i < data.length; i++) {
//                int resampledIndex = (int) (i / speed);
//                if (resampledIndex < resampled.length) {
//                    resampled[resampledIndex] = (short) spline.interpolate(i);
//                }
//            }
        } else {
            for (int i = 0; i < newLength; i++) {
                int sampleIndex = (int) (i * speed);

                if (sampleIndex < origin.length - 1) {
                    resampled[i] = origin[sampleIndex];
                } else {
                    resampled[i] = origin[origin.length - 1];
                }
            }
        }

        return resampled;
    }

    public static byte[] changeSpeedByResampled(byte[] data, double speed) {
        return shortToPcm16(changeSpeedByResampled(pcm16ToShort(data), speed));
    }

    // 变速效果
    public static void changeSpeedByResampled(AudioInputStream audioInputStream, double speed) {

    }

    public static void main(String[] args) throws LineUnavailableException, IOException, InterruptedException {
        byte[] array = MusicFileManager.getMusicBytes("netease:1404596131");
        //倒放
//        array = changeSpeedByResampled(array, 0.85f);

        //output format
        System.out.println(MusicFileManager.getMusicAudioInputStream("netease:1404596131").getFormat());
        AudioFormat format = new AudioFormat(44100, 16, 1, true, false);
        System.out.println(format);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(array);
        AudioInputStream audioInputStream = new AudioInputStream(byteArrayInputStream, format, array.length / format.getFrameSize());
        // 3. 获取音频设备
        DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
        SourceDataLine audioLine = (SourceDataLine) AudioSystem.getLine(info);

        // 4. 打开音频设备
        audioLine.open(format);
        audioLine.start();

        float speed = 0.1f;
        int offset = 0;
        int size = 44100;

        while (array.length >= offset / speed + size * speed) {

            byte[] testArray = new byte[(int) (size * speed)];
            System.arraycopy(array, offset, testArray, 0, (int) (size * speed));

            testArray = ProcessAudio.changeSpeedByResampled(testArray, speed);

            offset += (int) (size * speed);

            audioLine.write(testArray, 0, Math.min(size, testArray.length));
        }

        // 6. 关闭音频设备
        audioLine.drain();
        audioLine.close();
        audioInputStream.close();
    }
}
