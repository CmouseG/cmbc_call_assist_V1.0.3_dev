package com.guiji.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import com.guiji.common.exception.GuiyuException;
import com.guiji.entity.SmsExceptionEnum;

public class ParseFileUtil
{
	/**
	 * 解析excel文件
	 */
	public static List<String> parseExcelFile(MultipartFile file) throws Exception
	{
		List<String> phoneList = new ArrayList<>();
		
		String fileName = file.getOriginalFilename();
		if (!fileName.matches("^.+\\.(?i)(xls)$") && !fileName.matches("^.+\\.(?i)(xlsx)$")) {
            throw new GuiyuException(SmsExceptionEnum.Incorrect_Format);
        }
        Workbook workbook = null;
        if (fileName.matches("^.+\\.(?i)(xlsx)$")) { 
        	workbook = new XSSFWorkbook(file.getInputStream());
        } else {
        	workbook = new HSSFWorkbook(file.getInputStream());
        }
        Sheet sheet = workbook.getSheetAt(0);
		for (int r = 1; r <= sheet.getLastRowNum(); r++)
		{
			Row row = sheet.getRow(r);
			if (row == null){
                continue;
            }
			row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
			String phone = row.getCell(0).getStringCellValue(); //获取手机号
			if(phone==null || phone.isEmpty()){
                throw new GuiyuException("导入失败(第"+(r+1)+"行,号码未填写)");
            }
			if(!Pattern.compile("^\\d{11}$").matcher(phone).matches()){ //对号码进行校验
				throw new GuiyuException(SmsExceptionEnum.PhoneNum_Error);
			}
			phoneList.add(phone);
		}
		
		return phoneList;
	}
}
