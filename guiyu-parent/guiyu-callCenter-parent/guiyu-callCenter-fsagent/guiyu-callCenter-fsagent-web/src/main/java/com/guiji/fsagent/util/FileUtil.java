package com.guiji.fsagent.util;

import java.io.File;
import java.util.Date;

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
    public static void deleteFilesByDay(String Folder,int data){
        Date date = new Date(System.currentTimeMillis() - data*24*60*60*1000);
        File folder = new File(Folder);
        File[] files = folder.listFiles();
        for (int i=0;i<files.length;i++){
            File file = files[i];
            if (new Date(file.lastModified()).before(date)){
                file.delete();
            }
        }
    }

}
