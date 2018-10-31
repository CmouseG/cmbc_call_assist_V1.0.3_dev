package com.guiji.callcenter.fsmanager.util;

import sun.misc.BASE64Encoder;

import java.io.File;
import java.io.FileInputStream;

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
     * 将文件转换为base64
     * @param fileName
     * @return
     * @throws Exception
     */
    public  static  String fileToBase64(String fileName) throws Exception{
         FileInputStream inputFile  = new FileInputStream(fileName);
         byte[] buffer = new byte[(int)fileName.length()];
         inputFile.read(buffer);
         inputFile.close();
         return  new BASE64Encoder().encode(buffer);
    }


}
