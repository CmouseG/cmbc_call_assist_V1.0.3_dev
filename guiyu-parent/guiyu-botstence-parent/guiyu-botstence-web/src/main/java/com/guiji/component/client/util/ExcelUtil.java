package com.guiji.component.client.util;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ExcelUtil {

	public static <T> void export(String[] header,String[] columns, Collection<T> dataset,OutputStream output) {
		//创建HSSFWorkbook对象(excel的文档对象)
		@SuppressWarnings("resource")
		HSSFWorkbook wb = new HSSFWorkbook();
		//建立新的sheet对象（excel的表单）
		HSSFSheet sheet=wb.createSheet("worksheet");
		//在sheet里创建第一行，参数为行索引(excel的行)，可以是0～65535之间的任何一个
		setHeader(sheet,header);
		//输出Excel文件
		try {
			setDate(sheet,columns,dataset);
			wb.write(output);
			output.flush();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if(output!=null){
				try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static <T> void export(String[] header,String[] columns, Collection<T> dataset,HttpServletResponse resp){
		setHtteHeader(resp);
		OutputStream out=null;
		try {
			out = resp.getOutputStream();
			export(header,columns,dataset,out);
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if(out!=null){
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	/**
	 * 设置表头
	 * @param sheet
	 * @param header
	 */
	private static void setHeader(HSSFSheet sheet,String[] header){
		HSSFRow row=sheet.createRow(0);
		HSSFCell cell=null;
		for(int i=0;i<header.length;i++){
			cell=row.createCell(i);
			cell.setCellValue(header[i]);
			
		}
	}
	
	/**
	 * 添加数据
	 * @param args
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * @throws IOException
	 */
	private static <T> void setDate(HSSFSheet sheet,String[] columns, Collection<T> dataset) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		Iterator<T> iterator =dataset.iterator();
		Object[] objs=new Object[] {};
		HSSFCell cell=null;
		int index=1;
		while(iterator.hasNext()){
			HSSFRow row=sheet.createRow(index++);
			T item=iterator.next();
			Class<T> clazz=(Class<T>) item.getClass();
			for(int i=0;i<columns.length;i++){
				String fieldName = columns[i];
				String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
				Method getMethod = clazz.getMethod(getMethodName, new Class[]{});
				Object value = getMethod.invoke(item, objs);
				cell=row.createCell(i);
				cell.setCellValue(String.valueOf(value));
			}
		}
	}
	
	private static void setHtteHeader(HttpServletResponse resp){
		String fileName="temp_batch_audioshiyz_"+System.currentTimeMillis()+".xls";
		try {
			String iso=new String(fileName.getBytes(),"iso-8859-1");
			resp.setContentType("application/vnd.ms-excel;charset=utf-8");
			resp.setHeader("Content-Disposition", "attachment;filename="+ fileName);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

//	public static void main(String[] args) throws IOException {
//		Test t1=new Test("zhang",2);
//		Test t2=new Test("wei",1);
//		List<Test> list=new ArrayList<Test>();
//		list.add(t1);
//		list.add(t2);
//		OutputStream out=new FileOutputStream(new File("C:/Users/Administrator/Desktop","111.xls"));
//		ExcelUtil.export(new String[]{"姓名","年龄"},new String[]{"name","age"},list,out);
//	}

}
