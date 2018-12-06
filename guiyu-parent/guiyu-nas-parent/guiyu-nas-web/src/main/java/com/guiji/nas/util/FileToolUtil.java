package com.guiji.nas.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class FileToolUtil {
    private static final Logger logger = LoggerFactory.getLogger(FileToolUtil.class);
    /**
     * 判断文件夹是否存在
     * @param file
     * @return
     */
    public static void judeDirExists(File file) {

        if (file.exists()) {
            if (file.isDirectory()) {
                logger.debug("dir exists");
            } else {
                logger.debug("the same name file exists, can not create dir");
            }
        } else {
            logger.debug("dir not exists, create it ...");
            file.mkdir();
        }
    }

}