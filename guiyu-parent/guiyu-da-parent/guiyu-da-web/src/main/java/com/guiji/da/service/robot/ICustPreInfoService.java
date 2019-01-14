package com.guiji.da.service.robot;

import com.guiji.da.dao.entity.CustPreInfo;

/** 
* @Description: 客户信息
* @Author: weiyunbo
* @date 2019年1月2日 下午8:50:59 
* @version V1.0  
*/
public interface ICustPreInfoService {
	
	/**
	 * 新增/更新通话详情
	 * @param RobotCallDetail
	 * @return
	 */
	CustPreInfo save(CustPreInfo custPreInfo);
	
	/**
	 * 根据主键ID查询某个通话详细
	 * @param id
	 * @return
	 */
	CustPreInfo queryCustPreInfoById(int id);
	
	/**
	 * 根据手机号查询用户
	 * @param phoneNo
	 * @return
	 */
	CustPreInfo queryCustPreInfoByPhone(String phoneNo); 
	
}
