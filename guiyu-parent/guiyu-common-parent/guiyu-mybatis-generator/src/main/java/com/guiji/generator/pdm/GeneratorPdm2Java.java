/** 
 *@Copyright:Copyright (c) 2008 - 2100 
 *@Company:SJS 
 */  
package com.guiji.generator.pdm;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** 
 *@Description: 
 *@Author:weiyunbo
 *@date:2018年6月13日 下午5:16:13
 *@history:
 *@Version:v1.0 
 */
public class GeneratorPdm2Java {

	public static void main(String[] args) throws Exception {
		new GeneratorPdm2Java().deal();
	}
	
	public void deal() throws Exception {
		//先读出来
		List<String> lines = this.readToString();
		//要写文件的内容
		List<String> contents = new ArrayList<String>();
		if(lines != null) {
			for(int i = 1; i<lines.size() ; i++) {
				String line = lines.get(i);
				String[] array = line.split("	");
				String commentTxt = array[0];
				String fieldNameTxt = array[1];
				String typeTxt = array[2];
				int length = array.length;
				String pFlag = array[length-1];	//主键
				String mFlag = array[length-3];	//必输
				System.out.println(commentTxt+"---"+fieldNameTxt+"---"+typeTxt);
				String swagger = "";
				if("TRUE".equals(mFlag) || "TRUE".equals(pFlag)) {
					//必输
					swagger = "@ApiModelProperty(value=\""+commentTxt+"\",required=true)";
				}else {
					swagger = "@ApiModelProperty(value=\""+commentTxt+"\")";
				}
				contents.add(swagger);
				String fielsStr = "private "+getJavaType(typeTxt)+" "+lineToHump(fieldNameTxt)+";";
				contents.add(fielsStr);
			}
			if(contents != null && !contents.isEmpty()) {
				//写入文件
				writeLineFile(contents);
			}
		}
	}
	
	//获取java类型
	private String getJavaType(String typeTxt) {
		typeTxt = typeTxt.toLowerCase();
		if(typeTxt.contains("char")) {
			return "String";
		}else if(typeTxt.contains("int")) {
			return "Integer";
		}else if(typeTxt.contains("decimal")) {
			return "BigDecimal";
		}else if(typeTxt.contains("date")) {
			return "Date";
		}else{
			return "String";
		}
	}
	
	
	 /**下划线转驼峰*/  
    public static String lineToHump(String str){  
    	Pattern linePattern = Pattern.compile("_(\\w)"); 
        str = str.toLowerCase();  
        Matcher matcher = linePattern.matcher(str);  
        StringBuffer sb = new StringBuffer();  
        while(matcher.find()){  
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());  
        }  
        matcher.appendTail(sb);  
        return sb.toString();  
    }
	
    /**
     * 读PDM内容
     * @date:2018年6月13日 下午5:46:40 
     * @return
     * @throws Exception List<String>
     */
	public List<String> readToString() throws Exception {  
		String pdmPath = this.getClass().getResource("").getPath()+"pdm.txt";
		FileReader reader = new FileReader(pdmPath);
		BufferedReader br = new BufferedReader(reader);
		String s1 = null;
		List<String> lineList = null;
		while((s1 = br.readLine()) != null) {
			if(lineList == null) lineList = new ArrayList<String>();
			lineList.add(s1);
		}
		br.close();
		reader.close();
		return lineList;
    }
	
	/**
	 * 写文件
	 * @date:2018年6月13日 下午5:46:33 
	 * @param content
	 * @throws Exception void
	 */
	public void writeLineFile(List<String> content) throws Exception{  
    	String pdmPath = System.getProperty("user.dir")+"/guiyu-common-parent/guiyu-mybatis-generator/src/main/java/com/guiji/generator/pdm/pdm.txt";
    	File outFile = new File(pdmPath);
    	if(outFile.exists()) {
    		outFile.delete();
    		outFile.createNewFile();
    	}
        FileOutputStream out = new FileOutputStream(outFile);  
        OutputStreamWriter outWriter = new OutputStreamWriter(out, "UTF-8");  
        BufferedWriter bufWrite = new BufferedWriter(outWriter);  
        for (String txt : content) {  
            bufWrite.write(txt + "\r\n");  
        }  
        bufWrite.close();  
        outWriter.close();  
        out.close();  
    } 
	
	
}
  
