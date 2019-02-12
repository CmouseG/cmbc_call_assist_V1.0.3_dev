package com.guiji.clm.service.voip;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.guiji.clm.constant.ClmConstants;
import com.guiji.clm.dao.VoipGwPortMapper;
import com.guiji.clm.dao.entity.VoipGwInfo;
import com.guiji.clm.dao.entity.VoipGwPort;
import com.guiji.clm.dao.entity.VoipGwPortExample;
import com.guiji.clm.enm.VoipGwRegStatusEnum;
import com.guiji.clm.enm.VoipGwRegTypeStatusEnum;
import com.guiji.clm.exception.ClmErrorEnum;
import com.guiji.clm.exception.ClmException;
import com.guiji.clm.service.fee.FeeService;
import com.guiji.clm.service.fee.FeeService.FeeOptEnum;
import com.guiji.clm.util.CheckUtil;
import com.guiji.clm.util.DataLocalCacheUtil;
import com.guiji.clm.vo.VoipGwInfoVO;
import com.guiji.clm.vo.VoipGwPortQueryCondition;
import com.guiji.clm.vo.VoipGwPortVO;
import com.guiji.clm.vo.VoipGwQueryCondition;
import com.guiji.common.model.Page;
import com.guiji.component.result.Result;
import com.guiji.component.result.Result.ReturnData;
import com.guiji.user.dao.entity.SysOrganization;
import com.guiji.utils.BeanUtil;
import com.guiji.utils.StrUtils;
import com.guiji.voipgateway.api.VoipGatewayRemote;
import com.guiji.voipgateway.model.GwDevtbl;
import com.guiji.voipgateway.model.SimPort;

import lombok.extern.slf4j.Slf4j;

/** 
* @Description: 语音网关管理功能
* @Author: weiyunbo
* @date 2019年1月23日 下午5:02:27 
* @version V1.0  
*/
@Slf4j
@Service
public class VoipGwManager {
	@Autowired
	VoipGwPortMapper voipGwPortMapper;
	@Autowired
	VoipGwService voipGwService;
	@Autowired
	VoipGwPortService voipGwPortService;
	@Autowired
	DataLocalCacheUtil dataLocalCacheUtil;
	@Autowired
	VoipGatewayRemote voipGatewayRemote;
	@Autowired
	FeeService feeService;
	
	/**
	 * 客户发起网关初始化申请
	 * 1、校验网关名称不能重复
	 * 2、初始化网关sip起始账号、密码、步长等信息
	 * 3、初始化网关所有端口信息
	 * @param voipGwInfo
	 * @return
	 */
	@Transactional
	public VoipGwInfo startVoipGwInit(VoipGwInfo voipGwInfo) {
		if(voipGwInfo==null || !CheckUtil.fieldIsNullCheck(voipGwInfo, 
				new String[]{"gwName","portNum","userId"})) {
			//非空校验
			throw new ClmException(ClmErrorEnum.C00060001.getErrorCode(),ClmErrorEnum.C00060001.getErrorMsg());
		}
		/**1、名称校验**/
		VoipGwInfo voipGwInfoExist = voipGwService.queryByGwName(voipGwInfo.getGwName());
		if(voipGwInfoExist!=null) {
			log.error("新申请的网关名称:{}已经存在,不能发起申请",voipGwInfo.getGwName());
			throw new ClmException(ClmErrorEnum.CLM1809304.getErrorCode(),ClmErrorEnum.CLM1809304.getErrorMsg());
		}
		if(StrUtils.isEmpty(voipGwInfo.getOrgCode())) {
			//设置企业
			SysOrganization sysOrganization = dataLocalCacheUtil.queryUserRealOrg(voipGwInfo.getUserId());
			if(sysOrganization!=null) {
				voipGwInfo.setOrgCode(sysOrganization.getCode());
			}
		}
		/**2、调用呼叫中心服务，注册账号密码**/
		//TODO 调用注册中心注册
		//TODO 更新IP/端口
//		voipGwInfo.setSipIp(sipIp);
//		voipGwInfo.setSipPort(sipPort);
		/**3、初始化网关sip起始账号、密码、步长等信息**/
		this.initStartSipAccount(voipGwInfo);
		/**4、保存本地信息**/
		voipGwService.save(voipGwInfo);
		/**5、初始化网关所有端口**/
		voipGwService.initVoipGwPort(voipGwInfo);
		return voipGwInfo;
	}
	
	/**
	 * 默认初始化下网关的注册ip、起始账号、起始密码、步长数据
	 * 语音网关默认起始账号7位,步长1，密码8位,步长10
	 * 例如32口：
	 * 1001000（1001000-10010000,
	 * 			1001001-10010010,
	 *          1001002-10010020,
	 *          ...
	 *          1000031-10000310）
	 * 下个设备：1001000 + 1000*1 （倒数第4位加1）
	 * 1002000（1002000-1002000,
	 * 			1002001-10020010,
	 *          1002001-10020010,
	 *          ...
	 *          1002031-10020310）
	 * @param voipGwInfo
	 */
	private void initStartSipAccount(VoipGwInfo voipGwInfo) {
		//查询目前最大语音网关配置的sip账号
		Integer maxVoipSipAccount = null; 
		if(maxVoipSipAccount == null) {
			//首次使用默认账号开始
			maxVoipSipAccount = ClmConstants.VOIP_ACCOUNT;
		}
		Integer nextVoipSipAccount = maxVoipSipAccount + 1000;  //从第4位开始递增
		Integer nextVoipSipPsd = nextVoipSipAccount*10; //密码=账号+1位
		voipGwInfo.setStartSipAccount(nextVoipSipAccount);
		voipGwInfo.setStartSipPwd(nextVoipSipPsd);
		voipGwInfo.setSipAccountStep(ClmConstants.VOIP_ACCOUNT_STEP);
		voipGwInfo.setSipPwdStep(ClmConstants.VOIP_PSD_STEP);
		voipGwInfo.setGwRegStatus(VoipGwRegStatusEnum.INIT.getCode()); //初始状态
		voipGwInfo.setRegType(VoipGwRegTypeStatusEnum.reverse.getCode()); //反向注册：网关注册到fs
	}
	
	
	/**
	 * 插卡/分配
	 * 1、批量分配,分配到人
	 * 2、如果有单价,调用计费中心开始计费
	 * @param gwPortList 要分配的端口
	 * @param userId 分配人
	 */
	@Transactional
	public void assignGwPort(List<VoipGwPort> gwPortList,String userId) {
		if(gwPortList==null) {
			//非空校验
			throw new ClmException(ClmErrorEnum.C00060001.getErrorCode(),ClmErrorEnum.C00060001.getErrorMsg());
		}
		List<VoipGwPort> portList = new ArrayList<VoipGwPort>();
		for(VoipGwPort gwPort : gwPortList) {
			VoipGwPort port = voipGwPortService.queryById(gwPort.getId());
			if(StrUtils.isNotEmpty(gwPort.getPhoneNo())) {
				port.setPhoneNo(gwPort.getPhoneNo());
			}
			port.setUserId(gwPort.getUserId());
			//设置企业
			if(StrUtils.isEmpty(gwPort.getOrgCode()) && StrUtils.isNotEmpty(gwPort.getUserId())) {
				SysOrganization sysOrganization = dataLocalCacheUtil.queryUserRealOrg(gwPort.getUserId());
				if(sysOrganization!=null) {
					port.setOrgCode(sysOrganization.getCode());
				}
			}else {
				port.setOrgCode(gwPort.getOrgCode());
			}
			port.setCrtUser(userId);
			voipGwPortService.save(port);
			portList.add(port);
			//计费
			feeService.voipFee(FeeOptEnum.UP, port);
		}
	}
	
	
	/**
	 * 拔卡
	 * @param gwPortIdList
	 * @param userId
	 */
	@Transactional
	public void unAssignGwPort(List<Integer> gwPortIdList,String userId) {
		if(gwPortIdList==null) {
			//非空校验
			throw new ClmException(ClmErrorEnum.C00060001.getErrorCode(),ClmErrorEnum.C00060001.getErrorMsg());
		}
		//设置企业
		List<VoipGwPort> portList = new ArrayList<VoipGwPort>();
		for(Integer gwPortId : gwPortIdList) {
			VoipGwPort port = voipGwPortService.queryById(gwPortId);
			port.setUserId(null);
			port.setOrgCode(null);
			port.setPhoneNo(null);
			port.setCrtUser(userId);
			voipGwPortService.save(port);
			portList.add(port);
		}
		//TODO 计费
		
	}
	
	
	/**
	 * 根据条件查询网关列表（带监控信息）
	 * @param condition
	 * @return
	 */
	public List<VoipGwInfoVO> queryVoipGwListWrap(VoipGwQueryCondition condition) {
		List<VoipGwInfo> list = voipGwService.queryVoipGwInfoList(condition);
		//转带监控数据的VO
		return this.changeGw2VO(list);
	}
	
	/**
	 * 根据条件分页查询网关列表(带监控信息)
	 * @param condition
	 * @return
	 */
	public Page<VoipGwInfoVO> queryVoipGwForPageWrap(int pageNo, int pageSize,VoipGwQueryCondition condition) {
		//分页查询本地数据
		Page<VoipGwInfo> page = voipGwService.queryVoipGwInfoForPageByCondition(pageNo, pageSize, condition);
		//转带监控的VO数据
		return new Page<VoipGwInfoVO>(pageNo,page.getTotalRecord(),this.changeGw2VO(page.getRecords()));
	}
	
	/**
	 * 根据条件查询网关端口信息(带监控信息)
	 * @param condition
	 * @return
	 */
	public List<VoipGwPortVO> queryVoipGwPortListWrap(VoipGwPortQueryCondition condition) {
		List<VoipGwPort> list = voipGwPortService.queryVoipGwPortList(condition);
		return this.changePort2VO(list);
	}
	
	/**
	 * 将本地网关数据转为带监控的VO数据
	 * @param list
	 * @return
	 */
	private List<VoipGwInfoVO> changeGw2VO(List<VoipGwInfo> list){
		if(list!=null && !list.isEmpty()) {
			List<VoipGwInfoVO> rtnList = new ArrayList<VoipGwInfoVO>();
			for(VoipGwInfo voipGwInfo : list) {
				VoipGwInfoVO vo = new VoipGwInfoVO();
				BeanUtil.copyProperties(voipGwInfo, vo);
				if(StrUtils.isEmpty(voipGwInfo.getCompanyId())) {
					//如果设备信息为空，检查下
					this.fillVoipGwDevInfo(voipGwInfo);
				}
				//获取设备实时数据
				if(voipGwInfo.getCompanyId()!=null && voipGwInfo.getDevId()!=null) {
					ReturnData<GwDevtbl> gwDevtblData = voipGatewayRemote.queryGwDevByDevId(voipGwInfo.getCompanyId(), voipGwInfo.getDevId());
					if(gwDevtblData!=null && gwDevtblData.getBody()!=null) {
						GwDevtbl gwDevtbl = gwDevtblData.getBody();
						vo.setGwStatus(gwDevtbl.getWorkStatusId());	//设置目前设备的工作情况
						vo.setBeEnable(gwDevtbl.getBeEnable()); //设备是否启用
						vo.setChUseNum(gwDevtbl.getChUseNum());	//忙的通道数
						vo.setChFreeNum(gwDevtbl.getIdleChNum());	//现在闲的通道数
						vo.setChPutNum(gwDevtbl.getChPutNum()); //闲+忙的通道数，可以理解为插卡的通道数
					}
				}
				rtnList.add(vo);
			}
			return rtnList;
		}
		return null;
	}
	
	/**
	 * 将本地网关端口数据转为带监控的VO数据
	 * @param list
	 * @return
	 */
	private List<VoipGwPortVO> changePort2VO(List<VoipGwPort> list){
		if(list!=null && !list.isEmpty()) {
			List<VoipGwPortVO> rtnList = new ArrayList<VoipGwPortVO>();
			//获取设备实时数据
			ReturnData<List<SimPort>> gwPortData = null;
			if(list.get(0).getCompanyId()!=null && list.get(0).getDevId()!=null) {
				//实时查询集中管理平台的端口信息
				gwPortData = voipGatewayRemote.querySimPortListByDevId(list.get(0).getCompanyId(), list.get(0).getDevId());
			}
			for(VoipGwPort voipGwPort : list) {
				VoipGwPortVO vo = new VoipGwPortVO();
				BeanUtil.copyProperties(voipGwPort, vo);
				if(gwPortData!=null && gwPortData.getBody()!=null) {
					List<SimPort> simPortList = gwPortData.getBody();
					if(simPortList!=null && !simPortList.isEmpty()) {
						for(SimPort simPort : simPortList) {
							if(voipGwPort.getPort()==simPort.getPortNumber()) {
								vo.setPortRegStatus(simPort.getRegStatusId()); //端口注册状态
								vo.setPortWorkStatus(simPort.getWorkStatusId()); //端口工作状态
								vo.setPortConnFlag(simPort.getConnectionStatus()==1?true:false); //基站连接状态
								vo.setLoadType(simPort.getLoadType()); //负载状态
								if(StrUtils.isNotEmpty(simPort.getPhoneNumber())) {
									vo.setGwPhoneNo(simPort.getPhoneNumber());
									if(StrUtils.isEmpty(vo.getPhoneNo())) {
										vo.setPhoneNo(simPort.getPhoneNumber());
									}
								}
								continue;
							}
						}
					}
				}
				rtnList.add(vo);
			}
			return rtnList;
		}
		return null;
	}
	
	
	/**
	 * 填充网关部分集中管理设备信息
	 * 如：公司ID、设备ID等信息
	 * @param voipGwInfo
	 */
	@Transactional
	private void fillVoipGwDevInfo(VoipGwInfo voipGwInfo) {
		if(voipGwInfo!=null && StrUtils.isEmpty(voipGwInfo.getCompanyId())) {
			//设备信息（公司信息）为空，那么调用网关服务重新查询下
			Result.ReturnData<GwDevtbl> gwDevtblData = voipGatewayRemote.queryCompanyByDevName(voipGwInfo.getGwName());
			if(gwDevtblData != null && gwDevtblData.getBody()!=null) {
				//更新网关集中管理后信息
				voipGwInfo.setCompanyId(gwDevtblData.getBody().getCompanyId()); //公司ID
				voipGwInfo.setDevId(gwDevtblData.getBody().getDevId()); //设备编号
				voipGwInfo.setGwRegStatus(VoipGwRegStatusEnum.CONFIRM.getCode()); //集中管理了
				voipGwService.save(voipGwInfo);
				//更新网关端口集中管理后信息
				VoipGwPort record = new VoipGwPort();
				record.setCompanyId(gwDevtblData.getBody().getCompanyId());
				record.setDevId(gwDevtblData.getBody().getDevId());
				VoipGwPortExample example = new VoipGwPortExample();
				example.createCriteria().andGwIdEqualTo(voipGwInfo.getId());
				voipGwPortMapper.updateByExampleSelective(record, example);
			}
		}
	}
	
}
