package com.guiji.voipgateway.synway.dao;

import java.util.List;

import com.guiji.voipgateway.synway.dao.entity.PortStatus;
import org.apache.ibatis.annotations.Param;

import com.guiji.voipgateway.synway.dao.entity.ShareTabQuery;
import com.guiji.voipgateway.model.Company;
import com.guiji.voipgateway.model.GwDevtbl;
import com.guiji.voipgateway.model.SimPort;

/** 
* @Description: 三汇语音网关集中管理查询
* @Author: weiyunbo
* @date 2019年1月29日 下午6:22:32 
* @version V1.0  
*/
public interface SynwayMapper {
	
	/**
	 * 查询库中某个schema中某个名称开头的表列表
	 * @param shareTabQuery
	 * @return
	 */
	List<String> queryShareTabNameList(ShareTabQuery shareTabQuery);
	
	/**
	 * 根据公司ID查询公司信息
	 * @param companyId
	 * @return
	 */
	Company queryCompanyById(@Param("companyId")Integer companyId);
	
	/**
	 * 查询某张表中某个设备名称的设备信息
	 * @param tabName
	 * @param devName
	 * @return
	 */
	GwDevtbl queryCompanyByDevName(@Param("tabName")String tabName,@Param("devName")String devName);
	
	
	/**
	 * 查询某个公司所有设备信息列表
	 * @param tabName
	 * @param companyId
	 * @return
	 */
	List<GwDevtbl> queryGwDevtblListByCompId(@Param("tabName")String tabName,@Param("companyId")Integer companyId);
	
	/**
	 * 根据设备编号查询设备信息
	 * @param tabName
	 * @param devId
	 * @return
	 */
	GwDevtbl queryGwDevByDevId(@Param("tabName")String tabName,@Param("devId")Integer devId);
	
	/**
	 * 查询网关设备sim卡端口信息列表
	 * @param tabName
	 * @param devId
	 * @return
	 */
	List<SimPort> querySimPortListByDevId(@Param("tabName")String tabName,@Param("devId")Integer devId);

	/**
	 * 查询网关设备sim卡端口信息列表
	 * @param tabName
	 * @param devId
	 * @return
	 */
	List<PortStatus> querySimPortStatus(@Param("tabName")String tabName, @Param("devId")Integer devId, @Param("portId") Integer portId);
}
