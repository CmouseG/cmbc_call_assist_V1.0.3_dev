package com.guiji.robot.service.vo;

import lombok.Data;

/** 
* @ClassName: FlMatchReq 
* @Description: 飞龙match接口请求信息
* @date 2019年3月4日 下午3:59:58 
* @version V1.0  
*/
@Data
public class FlMatchReq {
	//会话id
	private String seqid;
	//话术模板
	private String cfg_name;
	//客户说的话
	private String cur_dialog_content;
	//当前会话状态
	private int cur_dialog_status;
}
