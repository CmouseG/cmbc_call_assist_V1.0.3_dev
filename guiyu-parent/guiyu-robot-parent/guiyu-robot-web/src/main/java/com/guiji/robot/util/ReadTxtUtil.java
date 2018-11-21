package com.guiji.robot.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.guiji.robot.exception.RobotException;
import com.guiji.robot.service.vo.HsReplace;
import com.guiji.utils.JsonUtils;
import com.guiji.utils.StrUtils;

/**
 * @ClassName: ReadTxtUtil
 * @Description: 文本读取工具类
 * @date 2018年11月20日 下午4:04:24
 * @version V1.0
 */
public class ReadTxtUtil {
	private static final Logger logger = LoggerFactory.getLogger(ReadTxtUtil.class);

	public static void main(String[] args) {
		String json = ReadTxtUtil.readTxtFile("C:\\Users\\weiyunbo\\Desktop\\robot\\360m12c\\replace.json");
		System.out.println(json);
		HsReplace hsReplace = JsonUtils.json2Bean(json, HsReplace.class);
		System.out.println(hsReplace);
	}

	
	/**
	 * 读取文本
	 * @param filePath
	 * @return
	 */
	public static String readTxtFile(String filePath) {
		if (StrUtils.isEmpty(filePath)) {
			return null;
		}
		try {
			String encoding = "UTF-8";
			File file = new File(filePath);
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);// 考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				StringBuilder sb = new StringBuilder();
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					sb.append(lineTxt);
				}
				read.close();
				return sb.toString();
			} else {
				throw new RobotException("文件"+filePath+"不存在！");
			}
		} catch (Exception e) {
			logger.error("读取文件"+filePath+"内容出错",e);
		}
		return null;
	}
}
