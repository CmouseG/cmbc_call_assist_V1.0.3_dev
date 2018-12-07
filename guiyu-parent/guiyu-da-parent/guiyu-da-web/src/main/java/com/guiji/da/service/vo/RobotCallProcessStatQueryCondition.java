package com.guiji.da.service.vo;

import lombok.Data;

/** 
* @ClassName: RobotCallProcessStatQueryCondition 
* @Description: robot通话量化分析查询条件
* @date 2018年12月6日 下午3:08:30 
* @version V1.0  
*/
@Data
public class RobotCallProcessStatQueryCondition {
	//用户id
	private String userId;
	//统计开始日期
    private String statBeginDate;
    //统计结束日期
    private String statEndDate;
    //统计模板编号
    private String templateId;
}
