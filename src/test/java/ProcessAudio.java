import java.io.*;
import javax.sound.sampled.*;

import javazoom.jl.converter.Converter;
import javazoom.jl.decoder.JavaLayerException;

public class ProcessAudio
{

    public static void convertMP3ToPCM(String mp3File, String wavFile, String pcmFile) {
        try {
            // 使用JLayer将MP3转换为WAV
            Converter converter = new Converter();
            converter.convert(mp3File, wavFile);

            // 读取WAV文件
            AudioInputStream ais = AudioSystem.getAudioInputStream(new File(wavFile));

            // 获取音频格式
            AudioFormat baseFormat = ais.getFormat();

            // 创建一个新的音频格式为PCM
            AudioFormat pcmFormat = new AudioFormat(
                    AudioFormat.Encoding.PCM_SIGNED,
                    baseFormat.getSampleRate(),
                    16,
                    baseFormat.getChannels(),
                    baseFormat.getChannels() * 2,
                    baseFormat.getSampleRate(),
                    false
            );

            // 获取PCM数据
            AudioInputStream pcmAis = AudioSystem.getAudioInputStream(pcmFormat, ais);

            // 将PCM数据写入文件
            AudioSystem.write(pcmAis, AudioFileFormat.Type.WAVE, new File(pcmFile));

            // 关闭流
            ais.close();
            pcmAis.close();
        } catch (UnsupportedAudioFileException | IOException | JavaLayerException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        convertMP3ToPCM("F:\\test\\1.mp3", "F:\\test\\1.wav", "F:\\test\\1.pcm");
    }

    // 混响效果


    // EQ均衡器效果


    // 变速效果

}
