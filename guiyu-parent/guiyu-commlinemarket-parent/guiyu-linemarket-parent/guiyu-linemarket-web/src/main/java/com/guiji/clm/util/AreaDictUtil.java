package com.guiji.clm.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.guiji.utils.StrUtils;

import lombok.extern.slf4j.Slf4j;

/** 
* @Description: 地区字典工具
* @Author: weiyunbo
* @date 2019年1月31日 下午7:41:49 
* @version V1.0  
*/
@Slf4j
public class AreaDictUtil {
	private static String areaJson;
	private static JSONObject areaJsonObj;
    
	public static void main(String[] args) {
		System.out.println(AreaDictUtil.getAreaName("410000,410300"));
		System.out.println(AreaDictUtil.getLowAreaNames("410300,410100"));
	}
	
	static {
		ResourceLoader resourceLoader = new DefaultResourceLoader();
		try {
			Resource resource = resourceLoader.getResource("classpath:area.json");
	        InputStream inputStream = resource.getInputStream();
	        areaJson = getString(inputStream);
			areaJsonObj = JSON.parseObject(areaJson);
		} catch (IOException e) {
			log.error("解析area.json文件失败,请求参数",e);
		}
	}
	
	/**
	 * 根据地区码查询地区名称
	 * 参数：上层地区,下级地区    410000,410300  (河南省,洛阳市) 返回洛阳市
	 * 返回下级地区名称
	 * @param areaCode
	 * @return
	 */
	public static String getAreaName(String areaArrayCode) {
		if(StrUtils.isNotEmpty(areaArrayCode)) {
			String parentCode = "86";
			String[] areaArray = areaArrayCode.split(",");
			for(int i=0;i<areaArray.length;i++) {
				String areaCode = areaArray[i];
				if(i==(areaArray.length-1)) {
					return areaJsonObj.getJSONObject(parentCode).getString(areaCode);
				}else {
					parentCode = areaCode;
				}
			}
		}
		return null;
	}
	
	
	/**
	 * 查询多个底层地区，如：洛阳市,郑州市  （编号）
	 * 使用json不太好处理，树形结构不确定多少层，因为编号长度固定，所以直接使用字符串匹配
	 * 返回名称
	 * @param areaArrayCode
	 * @return
	 */
	public static String getLowAreaNames(String areaArrayCode) {
		if(StrUtils.isNotEmpty(areaArrayCode)) {
			StringBuffer sb = new StringBuffer();
			String[] areaArray = areaArrayCode.split(",");
			for(String area:areaArray) {
				int indexBegin = areaJson.indexOf(area);
				if(indexBegin>=0) {
					int indexEnd = areaJson.indexOf("\"", indexBegin+9);
					String areaName = areaJson.substring(indexBegin+9, indexEnd);
					sb.append(","+areaName);
				}
			}
			if(sb!=null && sb.length()>1) {
				return sb.toString().substring(1);
			}
		}
		return null;
	}
	
	
	/**
	 * 读取文件
	 * @param inputStream
	 * @return
	 */
	private static String getString(InputStream inputStream) {
        InputStreamReader inputStreamReader = null;
        try {
            inputStreamReader = new InputStreamReader(inputStream, "utf-8");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        BufferedReader reader = new BufferedReader(inputStreamReader);
        StringBuffer sb = new StringBuffer("");
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
