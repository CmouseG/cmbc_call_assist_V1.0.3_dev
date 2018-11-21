package com.guiji.robot.service.vo;

import lombok.Data;

/** 
* @ClassName: AiBaseInfo 
* @Description: 机器人分配基本信息
* @date 2018年11月16日 下午1:35:39 
* @version V1.0  
*/
@Data
public class AiBaseInfo {
	//机器人编号
	private String aiNo;
	//机器人ip
	private String ip;
	//机器人端口
	private String port;
	/**
	 * @param aiNo
	 * @param ip
	 * @param port
	 */
	public AiBaseInfo(String aiNo, String ip, String port) {
		super();
		this.aiNo = aiNo;
		this.ip = ip;
		this.port = port;
	}
	/**
	 * @param ip
	 * @param port
	 */
	public AiBaseInfo(String ip, String port) {
		super();
		this.ip = ip;
		this.port = port;
	}
	/**
	 * 
	 */
	public AiBaseInfo() {
		super();
	}
	
}
