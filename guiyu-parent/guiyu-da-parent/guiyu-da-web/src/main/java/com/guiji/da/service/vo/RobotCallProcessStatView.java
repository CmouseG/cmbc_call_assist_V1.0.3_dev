package com.guiji.da.service.vo;

import java.util.List;

import lombok.Data;

/** 
* @ClassName: RobotCallProcessStatView 
* @Description: 数据统计VIEW
* @date 2018年12月7日 下午1:17:51 
* @version V1.0  
*/
@Data
public class RobotCallProcessStatView {
	//主流程AI话术统计列表
	private List<RobotCallProcessStatVO> majorStatList;
	
	//一般AI话术统计列表
	private List<RobotCallProcessStatVO> commonStatList;
}
