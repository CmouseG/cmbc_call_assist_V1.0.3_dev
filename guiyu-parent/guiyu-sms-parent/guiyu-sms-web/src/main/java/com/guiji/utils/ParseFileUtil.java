package com.guiji.utils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.fastjson.util.IOUtils;
import com.guiji.common.exception.GuiyuException;
import com.guiji.entity.SmsExceptionEnum;
import com.guiji.listener.ParseExcelListener;

public class ParseFileUtil
{
	private static final Logger logger = LoggerFactory.getLogger(ParseFileUtil.class);
	
	/**
	 * 解析excel文件
	 */
	public static List<String> parseExcelFile(MultipartFile file) throws Exception
	{
		List<String> phoneList = new ArrayList<>();
		// 校验文件类型
		String fileName = file.getOriginalFilename();
		if (!fileName.matches("^.+\\.(?i)(xls)$") && !fileName.matches("^.+\\.(?i)(xlsx)$")) {
			throw new GuiyuException(SmsExceptionEnum.Incorrect_Format);
		}
		InputStream inputStream = file.getInputStream();
		
		try
		{
			ParseExcelListener listener = new ParseExcelListener(phoneList);
			EasyExcelFactory.readBySax(inputStream, new Sheet(1, 1), listener);
			
		} catch (Exception e) {
			logger.error("解析文件失败！" + e);
			throw new GuiyuException(SmsExceptionEnum.ParseFile_Error); 
		} finally {
			IOUtils.close(inputStream);
		}
		
		return phoneList;
	}
}
