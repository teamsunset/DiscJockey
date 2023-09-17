package com.sunset.discjockey.util.MusicMisc;


public class ProcessAudio {
    // 复制
    public static byte[] copy(byte[] data) {
        int len = data.length;
        byte[] newData = new byte[len];
        System.arraycopy(data, 0, newData, 0, len);
        return newData;
    }

    // 混响效果


    // EQ均衡器效果


    // 变速效果
    public static byte[] changeSpeed(byte[] data, float speed) {
        int sampleSizeInBytes = 2; // 16 bits = 2 bytes
        int len = data.length;
        int newLen = (int) (len / speed);

        // Ensure the new length is a multiple of the sample size
        newLen = (newLen / sampleSizeInBytes) * sampleSizeInBytes;

        byte[] newData = new byte[newLen];
        for (int i = 0; i < newLen; i += sampleSizeInBytes) {
            int index = (int) (i * speed);

            // Ensure you don't go out of bounds or cut off a sample
            if (index < len - sampleSizeInBytes + 1) {
                newData[i] = data[index];
                newData[i + 1] = data[index + 1];
            } else {
                newData[i] = 0;
                newData[i + 1] = 0;
            }
        }
        return newData;
    }

}
