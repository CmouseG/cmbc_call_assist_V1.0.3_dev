package com.guiji.robot.util;

import com.guiji.robot.exception.RobotException;
import com.guiji.utils.StrUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * @version V1.0
 * @ClassName: ReadTxtUtil
 * @Description: 文本读取工具类
 * @date 2018年11月20日 下午4:04:24
 */
public class ReadTxtUtil {
    private static final Logger logger = LoggerFactory.getLogger(ReadTxtUtil.class);


    /**
     * 读取文本
     *
     * @param filePath
     * @return
     */
    public static String readTxtFile(String filePath) {
        if (StrUtils.isEmpty(filePath)) {
            return null;
        }
        InputStream is = null;
        InputStreamReader read = null;
        BufferedReader bufferedReader = null;
        try {
            String encoding = "UTF-8";
            File file = new File(filePath);
            if (file.isFile() && file.exists()) { // 判断文件是否存在
                is = new FileInputStream(file);
                read = new InputStreamReader(is, encoding);// 考虑到编码格式
                bufferedReader = new BufferedReader(read);
                StringBuilder sb = new StringBuilder();
                String lineTxt = null;
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    sb.append(lineTxt);
                }
                read.close();
                return sb.toString();
            } else {
                throw new RobotException("文件" + filePath + "不存在！");
            }
        } catch (Exception e) {
            logger.error("读取文件" + filePath + "内容出错", e);
        } finally {

            IOUtils.closeQuietly(bufferedReader);
            IOUtils.closeQuietly(read);
            IOUtils.closeQuietly(is);

        }
        return null;
    }
}
