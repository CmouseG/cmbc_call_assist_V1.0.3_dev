package com.guiji.da.service.robot;

import com.guiji.da.dao.entity.RobotCallDetail;

/** 
* @Description: 通话详情
* @Author: weiyunbo
* @date 2019年1月2日 下午8:15:46 
* @version V1.0  
*/
public interface IRobotCallCallDetailService {
	
	/**
	 * 新增/更新通话详情
	 * @param RobotCallDetail
	 * @return
	 */
	RobotCallDetail save(RobotCallDetail RobotCallDetail);
	
	/**
	 * 根据主键ID查询某个通话详细
	 * @param id
	 * @return
	 */
	RobotCallDetail queryRobotCallDetailById(int id);
	
	
	/**
	 * 根据会话id查询通话详细
	 * @param seqId
	 * @return
	 */
	RobotCallDetail queryRobotCallDetailBySeqId(String seqId);
	
	
}
