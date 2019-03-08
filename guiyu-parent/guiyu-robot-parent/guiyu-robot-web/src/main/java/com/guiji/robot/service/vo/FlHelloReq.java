package com.guiji.robot.service.vo;

import lombok.Data;

/** 
* @ClassName: FlHelloReq 
* @Description: 飞龙hello请求
* @date 2019年3月4日 下午4:30:35 
* @version V1.0  
*/
@Data
public class FlHelloReq {
	//会话id
	private String seqid;
	//话术模板
	private String cfg_name;
	//会话状态 0-初始化（类似restore); 1-会话中 ; 2-挂断
	private int cur_dialog_status;
	//客户说的话
	private String cur_dialog_content;
	//现在播的录音文件（非必输）
	private String cur_dialog_filename;
	//{"姓名":张三,"年龄":12} # 表示客户信息的业务数据
	private String business_data;
	/** key和val是为了兼容老的sellbot,合起来表示客户信息的业务数据, 他们和 business_data一起使用的话，以business_data为主,要想用key|val 就不要传business_data这个字段 **/
	//保留老sellbot字段，基本没用
	private String key;
	//张三|12 
	private String val;
}
