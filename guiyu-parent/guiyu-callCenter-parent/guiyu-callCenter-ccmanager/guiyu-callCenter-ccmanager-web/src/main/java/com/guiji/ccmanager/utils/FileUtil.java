package com.guiji.ccmanager.utils;

import lombok.extern.slf4j.Slf4j;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.io.File;
import java.io.IOException;
import java.util.Date;

@Slf4j
public class FileUtil {

    /**
     * 删除文件
     * @param fileName
     * @return
     */
    public static boolean delete(String fileName){
        File file = new File(fileName);
        return file.delete();
    }

    /**
     * 先根遍历序递归删除文件夹
     *
     * @param dirFile 要被删除的文件或者目录
     * @return 删除成功返回true, 否则返回false
     */
    public static boolean deleteFile(File dirFile) {
         // 如果dir对应的文件不存在，则退出
        if (!dirFile.exists()) {
            return false;
        }
        if (dirFile.isFile()) {
            return dirFile.delete();
        } else {
            for (File file : dirFile.listFiles()) {
                deleteFile(file);
            }
        }
        return dirFile.delete();
    }


    /**
     * 判断文件是否存在
     * @param fileName
     * @return
     */
    public static boolean isExist(String fileName){
        File file = new File(fileName);
        return file.exists();
    }

    /**
     * 删除n天前的文件
     * @param Folder
     * @param data
     */
    public static boolean deleteFilesByDay(String Folder,int data){
        Date date = new Date(System.currentTimeMillis() - data*24*60*60*1000);
        File folder = new File(Folder);
        File[] files = folder.listFiles();
        for (int i=0;i<files.length;i++){
            File file = files[i];
            if (new Date(file.lastModified()).before(date)){
                if(!file.delete()){
                    return false;
                }
            }
        }
        return true;
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
        }finally {
            try {
                if(audioInputStream!=null){
                    audioInputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 截取音频文件
     * @param sourceFileName
     * @param destinationFileName
     * @param startSecond
     * @param secondsToCopy
     */
    public static void copyAudio(String sourceFileName, String destinationFileName, int startSecond, int secondsToCopy) {
        AudioInputStream inputStream = null;
        AudioInputStream shortenedStream = null;
        try {
            File file = new File(sourceFileName);
            AudioFileFormat fileFormat = AudioSystem.getAudioFileFormat(file);
            AudioFormat format = fileFormat.getFormat();
            inputStream = AudioSystem.getAudioInputStream(file);
            int bytesPerSecond = format.getFrameSize() * (int) format.getFrameRate();
            inputStream.skip(startSecond * bytesPerSecond);
            long framesOfAudioToCopy = secondsToCopy * (int) format.getFrameRate();
            shortenedStream = new AudioInputStream(inputStream, format, framesOfAudioToCopy);
            File destinationFile = new File(destinationFileName);
            AudioSystem.write(shortenedStream, fileFormat.getType(), destinationFile);
        } catch (Exception e) {
            log.warn("截取音频文件出现异常", e);
        } finally {
            if (inputStream != null)
                try {
                inputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (shortenedStream != null)
                try {
                shortenedStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
