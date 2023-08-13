package com.sunset.discjockey.util.MusicMisc;

import com.sunset.discjockey.util.Reference;
//import javazoom.spi.mpeg.sampled.file.MpegAudioFileReader;
import javazoom.spi.mpeg.sampled.file.MpegAudioFileReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.commons.io.IOUtils;

import javax.sound.sampled.*;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Paths;
import java.util.HashMap;

@OnlyIn(Dist.CLIENT)
public class MusicFileManager
{
    public static final String fileDir = Paths.get(FMLPaths.MODSDIR.get().toString(),Reference.MOD_ID , "cache") + File.separator;

    public static final HashMap<String, File> soundFiles = new HashMap<>();

    public static final HashMap<String, AudioInputStream> soundStream = new HashMap<>();
    public static final HashMap<String, byte[]> soundBytes = new HashMap<>();


    //total function

//    public static <P, R> R getMusic(Class<R> clazz, P p) {
//        R result = null;
//
//        try {
//            File audioFile = null;
//            InputStream inputStream = null;
//
//            if (p instanceof String) {
//                audioFile = getFileFromUrl((String) p);
//            } else if (p instanceof InputStream) {
//                inputStream = (InputStream) p;
//            } else {
//                throw new Exception("Unsupported parameter type");
//            }
//
//            if (clazz == File.class) {
//                result = (R) audioFile;
//            } else if (clazz == AudioInputStream.class) {
//                result = (R) getPcmAudioInputStreamFromMp3(audioFile);
//            } else {
//                throw new Exception("Unsupported return type");
//            }
//
//            return result;
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return null;
//    }


    //get

    public static int getSongTimeBySecond(String mp3) {
        int songTimeBySecond = -1;

        try {
            AudioInputStream audioInputStream = getStreamFromUrl(mp3);

            if (audioInputStream == null)
                return 0;

            //获取音乐时长
            AudioFormat audioFormat = audioInputStream.getFormat();
            float bitrate = audioFormat.getSampleRate() * audioFormat.getSampleSizeInBits() * audioFormat.getChannels();
            byte[] array = getBytesFromUrl(mp3);
            songTimeBySecond = (int) (array.length * 8 / bitrate);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return songTimeBySecond;
    }

    public static File getFileFromUrl(String url) {
        String hashCode = SHA256.calculateSHA256(url);
        if (soundFiles.containsKey(hashCode)) {
            return soundFiles.get(hashCode);
        } else {
            File file = downLoadFile(url);
            if (file != null)
                soundFiles.put(hashCode, file);
            return file;
        }
    }

    public static AudioInputStream getStreamFromUrl(String url) {
        String hashCode = SHA256.calculateSHA256(url);
        if (soundStream.containsKey(hashCode)) {
            return soundStream.get(hashCode);
        } else {
            AudioInputStream stream = getPcmAudioInputStreamFromMp3(getFileFromUrl(url));
            if (stream != null)
                soundStream.put(hashCode, stream);
            return stream;
        }
    }

    public static byte[] getBytesFromUrl(String url) {
        byte[] bytes = new byte[0];
        try {
            String hashCode = SHA256.calculateSHA256(url);
            if (soundBytes.containsKey(hashCode)) {
                return soundBytes.get(hashCode);
            } else {
                AudioInputStream audioInputStream = getPcmAudioInputStreamFromMp3(getFileFromUrl(url));
                if (audioInputStream.available() == 0) {
                    bytes = IOUtils.toByteArray(audioInputStream);
                    if (bytes != null)
                        soundBytes.put(hashCode, bytes);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bytes;
    }

    //network

    /**
     * @param mp3 File,InputStream,String
     * @return AudioInputStream
     */
    public static <T> AudioInputStream getPcmAudioInputStreamFromMp3(T mp3) {
        AudioInputStream audioInputStream = new AudioInputStream(new ByteArrayInputStream(new byte[0]), new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 0, 0, 0, 0, 0, false), 0);

        try {
            AudioInputStream originalInputStream = null;

            if (mp3 instanceof File) {
                originalInputStream = new MpegAudioFileReader().getAudioInputStream((File) mp3);
            } else if (mp3 instanceof InputStream) {
                originalInputStream = new MpegAudioFileReader().getAudioInputStream((InputStream) mp3);
            } else if (mp3 instanceof String) {
                originalInputStream = new MpegAudioFileReader().getAudioInputStream(getFileFromUrl((String) mp3));
            } else {
                throw new Exception("Unsupported type");
            }

            AudioFormat baseFormat = originalInputStream.getFormat();

            AudioFormat originFormat = new AudioFormat(
                    AudioFormat.Encoding.PCM_SIGNED,
                    baseFormat.getSampleRate(),
                    16,
                    baseFormat.getChannels(),
                    baseFormat.getChannels() * 2,
                    baseFormat.getSampleRate(),
                    false
            );
            audioInputStream = AudioSystem.getAudioInputStream(originFormat, originalInputStream);
            AudioFormat targetFormat = new AudioFormat(
                    AudioFormat.Encoding.PCM_SIGNED,    //encoding technique
                    baseFormat.getSampleRate(),         //sample rate
                    16,                                 //sample size in bits
                    1,                                  //channels
                    2,                                  //frame size
                    baseFormat.getSampleRate(),         //frame rate
                    false                               //big endian
            );
            audioInputStream = AudioSystem.getAudioInputStream(targetFormat, audioInputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return audioInputStream;
    }

    public static URLConnection getURLConnection(String url) {
        URLConnection connection = null;

        try {
            // netease music redirect handle
            //若非url格式，匹配netease:数字id，例如netease:123456
            if (url.matches("^neteast:[0-9]*$")) {
                url = "https://music.163.com/song/media/outer/url?id=" + url.split(":")[1] + ".mp3";
            }

            if (url.matches("(.*)music.163.com(.*)")) {
                url = new URL(url).openConnection().getHeaderField("Location");
            }

            connection = new URL(url).openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(false);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return connection;
    }

    public static File downLoadFile(String url) {
        File file = null;

        try {
            URLConnection connection = getURLConnection(url);

            InputStream inputStream = connection.getInputStream();
            if (inputStream.available() == 0) {
                throw new Exception("File not found");
            }

            //name the file with hashcode
            file = new File(fileDir + SHA256.calculateSHA256(url) + ".mp3");
            FileOutputStream fileOutputStream = new FileOutputStream(file);

            byte[] buffer = new byte[1024];
            int len;
            while ((len = inputStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, len);
            }

            fileOutputStream.close();
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }


    //add existing files to the map, or create directory
    public static void readCache() {
        File path = new File(fileDir);
        if (!path.exists()) {
            new File(fileDir).mkdirs();
        } else {
            for (File file : path.listFiles()) {
                String hashcode = file.getName().replace(".mp3", "");
//                String hashcode = SHA256.calculateSHA256(file);
                if (!soundFiles.containsKey(hashcode)) {
                    soundFiles.put(hashcode, file);
                }
            }
        }
    }

    public static void clearCache() {
        File path = new File(fileDir);
        if (path.exists()) {
            for (File file : path.listFiles()) {
                file.delete();
            }
        }
    }

}
