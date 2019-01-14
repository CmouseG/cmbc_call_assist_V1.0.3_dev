package com.guiji.robot.service.vo;

import java.util.Map;

import lombok.Data;

/** 
* @ClassName: AiPoolInfo 
* @Description: Ai机器人概况
* @date 2019年1月14日 下午4:21:40 
* @version V1.0  
*/
@Data
public class AiPoolInfo {
	//机器人资源池总机器人数量
	private int totalNum;
	//资源池中在忙的机器人数量
	private int busiNum;
	//资源池中每个用户在忙的数量
	private Map<String,Integer> userAssignMap;
}
