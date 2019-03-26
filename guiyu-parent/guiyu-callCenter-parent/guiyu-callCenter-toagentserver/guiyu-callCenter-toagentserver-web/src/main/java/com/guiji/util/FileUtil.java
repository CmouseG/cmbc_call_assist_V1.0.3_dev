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

    /**
     * 将多个文件压缩为zip包
     * @param srcFiles
     * @param zipFile
     */
    public static void zipFiles(List<File> srcFiles, File zipFile) {
        // 判断压缩后的文件存在不，不存在则创建
        if (!zipFile.exists()) {
            try {
                zipFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // 创建 FileOutputStream 对象
        FileOutputStream fileOutputStream = null;
        // 创建 ZipOutputStream
        ZipOutputStream zipOutputStream = null;
        // 创建 FileInputStream 对象
        FileInputStream fileInputStream = null;
        try {
            // 实例化 FileOutputStream 对象
            fileOutputStream = new FileOutputStream(zipFile);
            // 实例化 ZipOutputStream 对象
            zipOutputStream = new ZipOutputStream(fileOutputStream);
            // 创建 ZipEntry 对象
            ZipEntry zipEntry = null;
            // 遍历源文件数组
            for (int i = 0; i < srcFiles.size(); i++) {
                // 将源文件数组中的当前文件读入 FileInputStream 流中
                fileInputStream = new FileInputStream(srcFiles.get(i));
                // 实例化 ZipEntry 对象，源文件数组中的当前文件
                zipEntry = new ZipEntry(srcFiles.get(i).getName());
                zipOutputStream.putNextEntry(zipEntry);
                // 该变量记录每次真正读的字节个数
                int len;
                // 定义每次读取的字节数组
                byte[] buffer = new byte[1024];
                while ((len = fileInputStream.read(buffer)) > 0) {
                    zipOutputStream.write(buffer, 0, len);
                }
                if(fileInputStream!=null){
                    fileInputStream.close();
                }
            }
            zipOutputStream.closeEntry();
            zipOutputStream.close();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
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


