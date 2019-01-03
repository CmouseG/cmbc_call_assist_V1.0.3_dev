package com.guiji.robot.model;

import java.util.Map;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/** 
* @ClassName: TtsCallback 
* @Description: TTS合成后的回call数据
* @date 2018年11月30日 下午1:09:36 
* @version V1.0  
*/
@Data
public class TtsCallback {
	@ApiModelProperty(value="业务ID",required=true)
	private String busiId;
	@ApiModelProperty(value="合成状态:1-成功;0-失败",required=true)
	private int status;
	@ApiModelProperty(value="失败原因")
    private String errorMsg;
    @ApiModelProperty(value="文本和音频下载地址,key是文本value是音频",required=true)
    private Map<String,String> audios;
}
