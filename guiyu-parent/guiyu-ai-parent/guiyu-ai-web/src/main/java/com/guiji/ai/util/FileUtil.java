package com.guiji.ai.util;

import java.io.File;
import java.io.FileOutputStream;

import org.apache.http.HttpEntity;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.guiji.ai.entity.GuiyuAIExceptionEnum;
import com.guiji.common.exception.GuiyuException;

public class FileUtil
{
	private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);
	
	public static File writeToFile(HttpEntity httpEntity, String filePath){
		FileOutputStream fileOutputStream = null;
		File file = null;
		try{
			File directory = new File(filePath);
			if (!directory.exists()){
				directory.mkdirs();
			} 
			file = new File(filePath + System.currentTimeMillis() + ".wav"); //文件保存路径
			if (!file.exists()) {
				file.createNewFile();
			}
			fileOutputStream = new FileOutputStream(file);
			httpEntity.writeTo(fileOutputStream); // 写到输出流中
			return file;
		} catch (Exception e) {
			logger.error("写入文件失败!", e);
			throw new GuiyuException(GuiyuAIExceptionEnum.EXCP_Write_To_File);
		} finally {
			IOUtils.closeQuietly(fileOutputStream);
		}
	}
}
