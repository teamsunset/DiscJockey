package com.sunset.discjockey.util.MusicMisc;

import javazoom.jl.player.Player;
import org.apache.commons.compress.utils.IOUtils;

import javax.sound.sampled.*;
import java.io.*;


import java.io.File;

public class testplayer
{
    public static void main(String[] args) {
        try {
            File file = new File("F:\\test\\1.mp3");
            AudioInputStream in = AudioSystem.getAudioInputStream(file);
            AudioInputStream din = null;
            AudioFormat baseFormat = in.getFormat();
            AudioFormat decodedFormat = new AudioFormat(
                    AudioFormat.Encoding.PCM_SIGNED,
                    baseFormat.getSampleRate(), 16, baseFormat.getChannels(),
                    baseFormat.getChannels() * 2, baseFormat.getSampleRate(),
                    false);
            din = AudioSystem.getAudioInputStream(decodedFormat, in);
            var test = IOUtils.toByteArray(din);
            din = new AudioInputStream(new ByteArrayInputStream(test), din.getFormat(), test.length);
            rawplay(decodedFormat, din);
            Player player = new Player(new FileInputStream(file));
            player.play();
        } catch (Exception e) {
            // Handle exception.
        }
    }

    private static void rawplay(AudioFormat targetFormat, AudioInputStream din)
            throws IOException, LineUnavailableException {
        byte[] data = new byte[4096];
        SourceDataLine line = getLine(targetFormat);
        if (line != null) {
            // Start
            line.start();
            int nBytesRead = 0, nBytesWritten = 0;
            while (nBytesRead != -1) {
                nBytesRead = din.read(data, 0, data.length);
                if (nBytesRead != -1)
                    nBytesWritten = line.write(data, 0, nBytesRead);
            }
            // Stop
            line.drain();
            line.stop();
            line.close();
            din.close();
        }
    }

    private static SourceDataLine getLine(AudioFormat audioFormat)
            throws LineUnavailableException {
        SourceDataLine res = null;
        DataLine.Info info = new DataLine.Info(SourceDataLine.class,
                audioFormat);
        res = (SourceDataLine) AudioSystem.getLine(info);
        res.open(audioFormat);
        return res;
    }
}


