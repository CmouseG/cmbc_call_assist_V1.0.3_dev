package com.guiji.voipgateway.service.impl;

import java.util.Collection;
import java.util.List;

import com.guiji.voipgateway.model.PortStatusEnum;
import com.guiji.voipgateway.service.ThirdGateWayService;
import com.guiji.voipgateway.synway.dao.entity.PortStatus;
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
import org.springframework.util.CollectionUtils;

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

	/**
	 * 1	可用
	 * 2	不可用
	 * 3	停用
	 * 4	断线
	 * 5	启用
	 * 6	锁定
	 * 7	正常
	 * 8	不同步
	 * 9	远端阻断
	 * 10	闭塞
	 * 11	其它
	 * 12	在线
	 * 13	离线
	 * 14	异常
	 * 15	不支持
	 * @param companyId
	 * @param devId
	 * @param portNo
	 * @return
	 */
	@Override
	public PortStatusEnum querySimPortStatus(Integer companyId, Integer devId, Integer portNo) {

		if(companyId!=null && devId!=null) {
			String tabName = companyId + "_simporttbl";	//表名：(公司ID)_porttbl
			List<PortStatus> simPorts = synwayMapper.querySimPortStatus(tabName, devId, portNo);

			if(CollectionUtils.isEmpty(simPorts)) {
				return PortStatusEnum.OTHER;
			} else {
				PortStatus portStatus = simPorts.get(0);

				switch (portStatus.getRunStatus()) {
					case 0:
						return PortStatusEnum.IDLE;
					case 1:
					case 2:
					case 3:
					case 4:
					case 5:
					case 6:
					case 7:
					case 9:
						return PortStatusEnum.BUSY;
					case 10:
					case 11:
						return PortStatusEnum.OTHER;
				}
			}
		}

		return PortStatusEnum.OTHER;
	}

}
