package com.guiji.voipgateway.service.impl;

import java.util.List;

import com.guiji.voipgateway.service.ThirdGateWayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guiji.utils.StrUtils;
import com.guiji.voipgateway.constants.VoipGatewayConstants;
import com.guiji.voipgateway.synway.dao.SynwayMapper;
import com.guiji.voipgateway.synway.dao.entity.ShareTabQuery;
import com.guiji.voipgateway.model.Company;
import com.guiji.voipgateway.model.GwDevtbl;
import com.guiji.voipgateway.model.SimPort;

import lombok.extern.slf4j.Slf4j;

/** 
* @Description: 三汇语音网关服务
* @Author: weiyunbo
* @date 2019年1月25日 下午6:27:46 
* @version V1.0  
*/
@Slf4j
@Service("synwayService")
public class SynwayServiceImpl implements ThirdGateWayService {
	@Autowired
	SynwayMapper synwayMapper;
	
	/**
	 * 根据设备名称查找设备基本信息
	 * @param devName
	 * @return
	 */
	@Override
	public GwDevtbl queryCompanyByDevName(String devName) {
		if(StrUtils.isNotEmpty(devName)) {
			//查询所有设备表
			ShareTabQuery shareTabQuery = new ShareTabQuery();
			shareTabQuery.setTableSchema(VoipGatewayConstants.SYN_WAY_SCHEMA);	//schema
			shareTabQuery.setTableNamePostfix("\\_devtbl");	//要查询表的后缀 _特殊符号转义
			List<String> devtblTabNameList = synwayMapper.queryShareTabNameList(shareTabQuery);
			if(devtblTabNameList!=null && !devtblTabNameList.isEmpty()) {
				//遍历设备表,倒着查2个表,还查不到结束
				int i=1;
				for(String tabName : devtblTabNameList) {
					if(i>2) break;
					log.info("设备名称{}到表{}中查找",devName,tabName);
					GwDevtbl gwDevtbl = synwayMapper.queryCompanyByDevName(tabName, devName);
					if(gwDevtbl != null) {
						return gwDevtbl;
					}
					i++;
				}
				log.error("设备名称{}没有查到对应的设备信息..",devName);
			}
		}
		return null;
	}
	
	/**
	 * 根据公司编号查找公司信息
	 * @param companyId
	 * @return
	 */
	public Company queryCompanyById(Integer companyId) {
		if(companyId!=null) {
			return synwayMapper.queryCompanyById(companyId);
		}
		return null;
	}
	
	/**
	 * 根据客户编号查询客户下所有设备
	 * @param companyId
	 * @return
	 */
	public List<GwDevtbl> queryGwDevtblListByCompId(Integer companyId){
		if(companyId!=null) {
			String tabName = companyId/10 + "_devtbl";	//表名：(公司ID/10)_devtbl
			return synwayMapper.queryGwDevtblListByCompId(tabName, companyId);
		}
		return null;
	}
	
	/**
	 * 根据设备编号查询设备基本信息
	 * @param companyId
	 * @param devId
	 * @return
	 */
	public GwDevtbl queryGwDevByDevId(Integer companyId,Integer devId) {
		if(companyId!=null && devId!=null) {
			String tabName = companyId/10 + "_devtbl";	//表名：(公司ID/10)_devtbl
			return synwayMapper.queryGwDevByDevId(tabName, devId);
		}
		return null;
	}
	
	
	/**
	 * 根据设备编号查询该设备下所有端口情况
	 * @param companyId
	 * @param devId
	 * @return
	 */
	public List<SimPort> querySimPortListByDevId(Integer companyId,Integer devId){
		if(companyId!=null && devId!=null) {
			String tabName = companyId + "_porttbl";	//表名：(公司ID)_porttbl
			return synwayMapper.querySimPortListByDevId(tabName, devId);
		}
		return null;
	}
	
}
