package com.sunset.discjockey.util.MusicMisc;

import com.sunset.discjockey.util.Reference;
import javazoom.spi.mpeg.sampled.file.MpegAudioFileReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.commons.io.IOUtils;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;

@OnlyIn(Dist.CLIENT)
public class MusicFileManager
{
    public static final String fileDir = FMLPaths.MODSDIR.get().toString() + "\\" + Reference.MOD_ID + "\\cache\\";

    //para1 Return type, para2 Parameter type
//    public static final Map<Class<?>, List<Class<?>>> supportedConversionType = new HashMap<>();

    public static final HashMap<String, File> soundFiles = new HashMap<>();

    public static final HashMap<String, AudioInputStream> soundStream = new HashMap<>();
    public static final HashMap<String, byte[]> soundBytes = new HashMap<>();

//    static {
//        supportedConversionType.put(File.class, Arrays.asList(
//                String.class
//        ));
//        supportedConversionType.put(AudioInputStream.class, Arrays.asList(
//                String.class,
//                File.class,
//                byte[].class
//        ));
//        supportedConversionType.put(byte[].class, Arrays.asList(
//                String.class,
//                File.class,
//                AudioInputStream.class
//        ));
//    }

    //total function

//    public static <P, R> R getMusic(Class<R> clazz, P p) {
//        R result = null;
//
//        if (p.getClass() == clazz) return (R) p;
//
//        try {
//            //check R from P
//            for (Map.Entry<Class<?>, List<Class<?>>> entry : supportedConversionType.entrySet()) {
//                if (entry.getKey() == clazz && entry.getValue().contains(p.getClass())) {
//                    break;
//                } else {
//                    throw new Exception("Unsupported conversion type " + p.getClass() + " to " + clazz + "!");
//                }
//            }
//
//            //declare
//            String url = null;
//            String hashCode = null;
//            File audioFile = null;
//            AudioInputStream audioInputStream = null;
//            byte[] bytes = null;
//
//            //1. parameter process
//            if (p instanceof File) {
//                audioFile = (File) p;
//            } else if (p instanceof String) {
//                url = (String) p;
//                audioFile = downLoadFile(url);
//            } else if (p instanceof AudioInputStream) {
//                audioInputStream = (AudioInputStream) p;
//            }
//
//            //2. trans stream
//            if (audioInputStream == null) {
//                if (audioFile != null) {
//                    audioInputStream = getPcmAudioInputStreamFromMp3(audioFile);
//                }
//            }
//
//            //3. trans bytes
//            if (clazz == byte[].class) {
//                bytes = IOUtils.toByteArray(audioInputStream);
//            }
//
//            //4. memory cache
//            if (url != null) {
//                hashCode = String.valueOf(url.hashCode());
//                if (audioFile != null) {
//                    soundFiles.put(hashCode, audioFile);
//                }
////                if (audioInputStream != null) {
////                    soundStream.put(url, audioInputStream);
////                }
////                if (bytes != null) {
////                    soundBytes.put(url, bytes);
////                }
//            }
//
//            //5. return
//            if (clazz == File.class) {
//                result = (R) audioFile;
//            } else if (clazz == AudioInputStream.class) {
//                result = (R) audioInputStream;
//            } else if (clazz == byte[].class) {
//                result = (R) bytes;
//            }
//
//            //6. check null
//            if (result == null) {
//                throw new Exception("Fail to get music! Null result!");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return result;
//    }


    //special get
    public static boolean checkURL(String url) {
        return getMusicFile(url) != null;
    }

    //millisecond
    public static int getSongTimeByMillisecond(String url) {
        AudioInputStream audioInputStream = getMusicAudioInputStream(url);
        if (audioInputStream == null) return 0;
        //获取音乐时长
        AudioFormat audioFormat = audioInputStream.getFormat();
        byte[] array = getMusicBytes(url);
        float bitrate = audioFormat.getSampleRate() * audioFormat.getSampleSizeInBits() * audioFormat.getChannels();
        return (int) (array.length * 8 / bitrate * 1000);
    }

    //tick amount
    public static int getSongTime(String url) {
        return getSongTimeByMillisecond(url) / 50;
    }

    public static int getSongTimeBySecond(String url) {
        return getSongTimeByMillisecond(url) / 1000;
    }

    //get
    public static File getMusicFile(String url) {
        File file = null;

        String hashCode = SHA256.calculateSHA256(url);
        if (soundFiles.containsKey(hashCode)) {
            file = soundFiles.get(hashCode);
        }
        else {
            file = downLoadFile(url);
            if (file != null)
                soundFiles.put(hashCode, file);
        }

        return file;
    }

    public static AudioInputStream getMusicAudioInputStream(String url) {
        AudioInputStream audioInputStream = null;

        String hashCode = SHA256.calculateSHA256(url);
        if (soundStream.containsKey(hashCode)) {
            return soundStream.get(hashCode);
        }
        else {
            audioInputStream = getPcmAudioInputStreamFromMp3(getMusicFile(url));
            if (audioInputStream != null)
                soundStream.put(hashCode, audioInputStream);
        }

        return audioInputStream;
    }

    public static byte[] getMusicBytes(String url) {
        byte[] bytes = null;

        try {
            String hashCode = SHA256.calculateSHA256(url);
            if (soundBytes.containsKey(hashCode)) {
                return soundBytes.get(hashCode);
            }
            else {
                AudioInputStream audioInputStream = getMusicAudioInputStream(url);
                if (audioInputStream != null) {
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

    //base get

    /**
     * @param mp3 File,InputStream,String
     * @return AudioInputStream
     */
    public static <T> AudioInputStream getPcmAudioInputStreamFromMp3(File mp3) {
//        AudioInputStream audioInputStream = new AudioInputStream(new ByteArrayInputStream(new byte[0]), new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 0, 0, 0, 0, 0, false), 0);
        AudioInputStream audioInputStream = null;

        try {
            AudioInputStream originalInputStream = null;

            originalInputStream = new MpegAudioFileReader().getAudioInputStream(mp3);

            AudioFormat originFormat = originalInputStream.getFormat();

            AudioFormat baseFormat = new AudioFormat(
                    AudioFormat.Encoding.PCM_SIGNED,
                    originFormat.getSampleRate(),
                    16,
                    originFormat.getChannels(),
                    originFormat.getChannels() * 2,
                    originFormat.getSampleRate(),
                    false
            );
            audioInputStream = AudioSystem.getAudioInputStream(baseFormat, originalInputStream);
            AudioFormat targetFormat = new AudioFormat(
                    AudioFormat.Encoding.PCM_SIGNED,    //encoding technique
                    originFormat.getSampleRate(),         //sample rate
                    16,                                 //sample size in bits
                    1,                                  //channels
                    2,                                  //frame size
                    originFormat.getSampleRate(),         //frame rate
                    false                               //big endian
            );
            audioInputStream = AudioSystem.getAudioInputStream(targetFormat, audioInputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return audioInputStream;
    }

    //network
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
        }
        else {
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
