package com.guiji.fsagent.util;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * 将base64串转换为文件
 */
public class Base64Util {
    private static final Logger logger = LoggerFactory.getLogger(Base64Util.class);
    /**
     * 将base64串转换为文件
     * @param encodedText
     * @param fileName
     * @throws IOException
     */
    public static void base64ToFile(String encodedText, String fileName) {
        Base64 base64 = new Base64();
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        try {
            byte[] bytes = base64.decode(encodedText);
            File  file = new File(fileName);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(bytes);
        } catch (Exception e) {
            logger.info("base64串转换为文件失败", e);
        } finally {
            try {
                if (bos != null) {
                    bos.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                logger.info("关闭流失败", e);
            }
        }
    }
}

