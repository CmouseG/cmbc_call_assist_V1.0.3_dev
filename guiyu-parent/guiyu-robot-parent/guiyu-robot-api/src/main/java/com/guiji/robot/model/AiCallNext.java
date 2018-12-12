package com.guiji.robot.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/** 
* @ClassName: AiCallNext 
* @Description: AI电话机器人电话拨打响应信息，返回呼叫中心下个语音 
* @date 2018年11月15日 下午1:52:47 
* @version V1.0  
*/
@Data
@ApiModel(value="AiCallNext对象",description="AI机器人拨打下个语音")
public class AiCallNext {
	@ApiModelProperty(value="机器人编号，开始拨打电话时分配的机器人",required=true)
	private String aiNo;
	@ApiModelProperty(value="相应过程中helloStatus必输(play-播放;wait-无操作)")
	private String helloStatus;
	@ApiModelProperty(value="sellbot返回的原始JSON报文",required=true)
	private String sellbotJson;
}