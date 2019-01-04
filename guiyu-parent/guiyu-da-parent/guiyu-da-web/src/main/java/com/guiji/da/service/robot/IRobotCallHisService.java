package com.guiji.da.service.robot;

import com.guiji.da.dao.entity.RobotCallHis;

/** 
* @ClassName: RobotCallbackHisService 
* @Description: 机器人通话纪录
* @date 2018年12月6日 下午2:15:57 
* @version V1.0  
*/
public interface IRobotCallHisService {
	
	/**
	 * 根据主键ID查询某个通话记录
	 * @param id
	 * @return
	 */
	RobotCallHis queryRobotCallById(int id);
	
	
	/**
	 * 根据会话id查询通话记录
	 * @param seqId
	 * @return
	 */
	RobotCallHis queryRobotCallBySeqId(String seqId);
	
	
	/**
	 * 根据会话id更新通话状态
	 * @param seqId
	 * @param callStatus
	 */
	void updateCallStatus(String seqId,int callStatus);
	
}
