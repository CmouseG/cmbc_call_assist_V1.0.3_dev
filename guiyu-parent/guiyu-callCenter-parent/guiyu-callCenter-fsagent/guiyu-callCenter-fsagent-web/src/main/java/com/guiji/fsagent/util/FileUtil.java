package com.guiji.fsagent.util;

import lombok.extern.slf4j.Slf4j;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.io.*;
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
                log.warn("关闭流出现异常", e);
            }
        }
        return null;
    }

}
