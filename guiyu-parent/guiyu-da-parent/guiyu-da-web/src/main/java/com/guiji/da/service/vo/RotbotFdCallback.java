package com.guiji.da.service.vo;

import java.util.List;

import com.guiji.da.dao.entity.CustPreInfo;
import com.guiji.da.dao.entity.RobotCallDetail;
import com.guiji.da.dao.entity.RobotCallHis;

import lombok.Data;

/** 
* @ClassName: RotbotCallback 
* @Description: 飞龙通话完成后回调信息
* @date 2019年1月10日 上午10:11:01 
* @version V1.0  
*/
@Data
public class RotbotFdCallback {
	//主要为3块：客户信息、通话基本信息、通话详情
	private CustPreInfo cust;	
	private RobotCallHis base;
	private List<RobotCallDetail> details;
}
