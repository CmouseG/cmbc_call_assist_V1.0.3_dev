package com.guiji.fsagent.util;

import sun.misc.BASE64Encoder;

import java.io.*;
import java.util.Base64;

public class Base64Util {

    public static void base64ToFile(String base64, String fileName) {
        File file = null;
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        try {
            byte[] bytes = Base64.getDecoder().decode(base64);
            file = new File(fileName);
            fos = new java.io.FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
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

