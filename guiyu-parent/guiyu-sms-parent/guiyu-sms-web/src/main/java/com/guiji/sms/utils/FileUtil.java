package com.guiji.sms.utils;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.fastjson.util.IOUtils;
import com.guiji.sms.common.ExceptionEnum;
import com.guiji.sms.common.SmsException;
import com.guiji.sms.handler.ParseExcelHandler;

public class FileUtil
{
	private static final Logger log = LoggerFactory.getLogger(FileUtil.class);
	/**
	 * 解析excel文件
	 */
	public static List<String> parseExcelFile(MultipartFile file)
	{
		List<String> phoneList = new ArrayList<>();
		// 校验文件类型
		String fileName = file.getOriginalFilename();
		if (!fileName.matches("^.+\\.(?i)(xls)$") && !fileName.matches("^.+\\.(?i)(xlsx)$")){
			throw new SmsException(ExceptionEnum.ERROR_FILE_TYPE);
		}
		InputStream inputStream = null;
		try
		{
			inputStream = new BufferedInputStream(file.getInputStream());
			ParseExcelHandler handler = new ParseExcelHandler(phoneList);
			ExcelReader excelReader = new ExcelReader(inputStream, null, handler);
			excelReader.read(new Sheet(1, 1));
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new SmsException(ExceptionEnum.ERROR_PARSE_EXCEL);
		} finally {
			IOUtils.close(inputStream);
		}
		return phoneList;
	}
}
