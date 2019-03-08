package com.guiji.robot.service.vo;

import lombok.Data;

/** 
* @ClassName: CallBusiData 
* @Description: 一通电话的业务数据-飞龙需要的 
* @date 2019年3月5日 下午6:21:06 
* @version V1.0  
*/
@Data
public class CallBusiData {
	//手机号
	private String phoneNo;
	//性别
	private String gender; 
	//职业
	private String job;
	//上次意向标签
	private String intentLevel;
}
