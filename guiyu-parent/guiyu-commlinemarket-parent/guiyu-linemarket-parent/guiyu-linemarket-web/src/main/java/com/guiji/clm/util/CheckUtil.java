package com.guiji.clm.util;

import java.lang.reflect.Field;
import java.util.Arrays;
import com.guiji.common.exception.GuiyuException;

import lombok.extern.slf4j.Slf4j;

/** 
* @Description: 校验工具类
* @Author: weiyunbo
* @date 2019年1月23日 下午3:26:29 
* @version V1.0  
*/
@Slf4j
public class CheckUtil {
	
	/**
	 * 校验对象中需要校验非空的属性
	 * @param o
	 * @param notNullFields
	 * @return
	 */
	public static boolean fieldIsNullCheck(Object o,String[] notNullFields){
		try {
			if(notNullFields!=null && notNullFields.length>0) {
				for (Field field : o.getClass().getDeclaredFields()) {
					//判断是否需要校验该字段
					if(!Arrays.asList(notNullFields).contains(field.getName())) {
						continue;
					}
					field.setAccessible(true);
					Object object = field.get(o);
					if (object instanceof CharSequence) {
						if (org.springframework.util.ObjectUtils.isEmpty(object)) {
							log.error("对象属性{}不能为空！",field.getName());
							return false;
						}
					} else {
						if (null == object) {
							log.error("对象属性{}不能为空！",field.getName());
							return false;
						}
					}
				}
			}
		} catch (Exception e) {
			throw new GuiyuException("对象校验系统异常",e);
		}
		return true;
	}
}
