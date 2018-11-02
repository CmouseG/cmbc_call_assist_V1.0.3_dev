package com.guiji.fsagent.util;

import java.io.File;

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




}
