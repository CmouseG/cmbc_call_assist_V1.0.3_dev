package com.guiji.robot.model;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/** 
* @ClassName: UserAiCfgVO 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @date 2018年12月4日 上午10:41:35 
* @version V1.0  
*/
@Data
@ApiModel(value="UserAiCfgVO对象",description="用户机器人配置信息")
public class UserAiCfgVO {
	@ApiModelProperty(value="用户编号",required=true)
	private String userId;
	@ApiModelProperty(value="机器人总数量",required=true)
	private int aiTotalNum;
	@ApiModelProperty(value="用户机器人分配详情",required=true)
	private List<UserAiCfgDetailVO> userAiCfgDetailList;
}
