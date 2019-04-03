package com.guiji.calloutserver.util;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.io.File;


@Slf4j
public class FileUtil {
    private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);

    public static boolean isExist(String fileName){
        File file = new File(fileName);
        return file.exists();
    }

    public static boolean delete(String fileName){
        File file = new File(fileName);
        return file.delete();
    }

    /**
     * 判断文件是否正常，条件是：文件存在，并且体积大于0
     * @param fileName
     * @return
     */
    public static boolean isValid(String fileName){
        File file = new File(fileName);
        return file.exists() && file.length()>0;
    }

    /**
     * 获取文件大小
     * @param fileName
     * @return
     */
    public static Long getFileSize(String fileName){
        File file = new File(fileName);
        return file.length();
    }

    /**
     * 获取wav文件的播放时长, 单位为秒
     * @param fileName
     * @return
     */
    public static Double getWavDuration(String fileName){
        File file = new File(fileName);
        AudioInputStream audioInputStream = null;
        try {
            audioInputStream = AudioSystem.getAudioInputStream(file);
            AudioFormat format = audioInputStream.getFormat();
            long frames = audioInputStream.getFrameLength();
            Double durationInSeconds = (frames+0.0) / format.getFrameRate();
            return durationInSeconds;
        } catch (Exception e) {
            log.warn("获取wav时长出现异常", e);
        }
        return null;
    }
}
