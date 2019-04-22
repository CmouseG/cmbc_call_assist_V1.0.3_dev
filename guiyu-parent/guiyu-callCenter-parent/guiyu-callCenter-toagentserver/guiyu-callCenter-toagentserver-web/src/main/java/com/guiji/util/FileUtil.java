package com.guiji.util;

import com.guiji.common.model.SysFileReqVO;
import com.guiji.common.model.SysFileRspVO;
import com.guiji.utils.NasUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static com.guiji.util.CommonUtil.doShCommand;

@Slf4j
public class FileUtil {
    private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);

    /**
     * 获取文件行数
     * @param fileName
     * @return
     */
    public static int count(String fileName){
        int rows= 0;
        String cmd = String.format("wc -l < %s", fileName);
        try {
            String result = doShCommand(cmd);
            rows = Integer.parseInt(result.trim());
        } catch (IOException e) {
            logger.warn("获取文件[{}]行号出现异常", fileName);
        }

        return rows;
    }

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


    //上传配置文件，并保存到nas中
    public static String uploadConfig(Long userId, String configPath,String appName) {
        NasUtil nasUtil = new NasUtil();
        SysFileReqVO reqVO = new SysFileReqVO();
        reqVO.setThumbImageFlag("0");
        reqVO.setUserId(userId);
        reqVO.setBusiType("freeswitch_config");
        reqVO.setSysCode(appName);
        reqVO.setBusiId("callcenter.conf.xml");
        SysFileRspVO fileRspVO = nasUtil.uploadNas(reqVO, new File(configPath));
        return fileRspVO.getSkUrl();
    }
}


