package com.guiji.da.service.vo;

import lombok.Data;

/** 
* @ClassName: RobotCallProcessStatVO 
* @Description: 前端显示VO 
* @date 2018年12月7日 上午10:39:30 
* @version V1.0  
*/
@Data
public class RobotCallProcessStatVO {
	//模板ID
    private String templateId;
    //当前域
    private String currentDomain;
    //话术
    private String aiAnswer;
    //类型1-主流程;2-一般问题;9-其他
    private String domainType;
    //总数
    private int totalStat;
    //拒绝数
    private int refusedNum;
    //挂断数
    private int hangupNum;
}
