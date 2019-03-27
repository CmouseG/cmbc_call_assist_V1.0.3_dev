package com.guiji.auth.model;

import lombok.Data;

/** 
* @ClassName: PrivlegeAuth 
* @Description: 资源对象权限级别
* @auth weiyunbo
* @date 2019年3月13日 下午8:04:45 
* @version V1.0  
*/
@Data
public class PrivlegeAuth {
	//资源对象类型
	private Integer authType;
	//资源对象id
	private String authId;
}
