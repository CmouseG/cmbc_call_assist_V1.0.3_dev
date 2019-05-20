package com.guiji.clm.service.voip;

import com.guiji.clm.cfg.BrandConfig;
import com.guiji.clm.constant.ClmConstants;
import com.guiji.clm.dao.VoipGwInfoMapper;
import com.guiji.clm.dao.VoipGwPortMapper;
import com.guiji.clm.dao.entity.VoipGwInfo;
import com.guiji.clm.dao.entity.VoipGwPort;
import com.guiji.clm.dao.entity.VoipGwPortExample;
import com.guiji.clm.dao.ext.VoipGwInfoMapperExt;
import com.guiji.clm.enm.VoipGwRegStatusEnum;
import com.guiji.clm.enm.VoipGwRegTypeStatusEnum;
import com.guiji.clm.enm.VoipGwStatusEnum;
import com.guiji.clm.exception.ClmErrorEnum;
import com.guiji.clm.exception.ClmException;
import com.guiji.clm.model.SimLineStatus;
import com.guiji.clm.service.fee.FeeService;
import com.guiji.clm.service.fee.FeeService.FeeOptEnum;
import com.guiji.clm.util.CheckUtil;
import com.guiji.clm.util.DataLocalCacheUtil;
import com.guiji.clm.vo.VoipGwInfoVO;
import com.guiji.clm.vo.VoipGwPortQueryCondition;
import com.guiji.clm.vo.VoipGwPortVO;
import com.guiji.clm.vo.VoipGwQueryCondition;
import com.guiji.common.model.Page;
import com.guiji.component.lock.DistributedLockHandler;
import com.guiji.component.lock.Lock;
import com.guiji.component.result.Result;
import com.guiji.component.result.Result.ReturnData;
import com.guiji.dict.api.ISysDict;
import com.guiji.dispatch.api.IDispatchPlanOut;
import com.guiji.fsmanager.api.ILineOperation;
import com.guiji.fsmanager.api.ISimCard;
import com.guiji.fsmanager.entity.FsSipVO;
import com.guiji.fsmanager.entity.LineInfoVO;
import com.guiji.fsmanager.entity.OutLineInfoAddReq;
import com.guiji.fsmanager.entity.SimCardVO;
import com.guiji.user.dao.entity.SysOrganization;
import com.guiji.user.dao.entity.SysUser;
import com.guiji.utils.BeanUtil;
import com.guiji.utils.StrUtils;
import com.guiji.voipgateway.api.VoipGatewayRemote;
import com.guiji.voipgateway.model.GwDevtbl;
import com.guiji.voipgateway.model.PortRo;
import com.guiji.voipgateway.model.PortStatusEnum;
import com.guiji.voipgateway.model.SimPort;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

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
	ISysDict sysDict;
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
	@Autowired
	VoipGwInfoMapperExt voipGwInfoMapperExt;
	@Autowired
	DistributedLockHandler distributedLockHandler;
	@Autowired
	ISimCard iSimCard;
	@Autowired
	ILineOperation iLineOperation;
	@Autowired
	IDispatchPlanOut iDispatchPlanOut;
	
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
		Lock lock = new Lock(ClmConstants.LOCK_LINEMARKET_INIT_VOIP, ClmConstants.LOCK_LINEMARKET_INIT_VOIP);
		if (distributedLockHandler.tryLock(lock)) { // 持锁
			try {
				/**1、名称校验**/
				VoipGwInfo voipGwInfoExist = voipGwService.queryByGwName(voipGwInfo.getGwName());
				if(voipGwInfoExist!=null) {
					log.error("新申请的网关名称:{}已经存在,不能发起申请",voipGwInfo.getGwName());
					throw new ClmException(ClmErrorEnum.CLM1809304.getErrorCode(),ClmErrorEnum.CLM1809304.getErrorMsg());
				}
				if(StrUtils.isEmpty(voipGwInfo.getOrgCode())) {
					//设置企业
					SysOrganization sysOrganization = dataLocalCacheUtil.queryOrgByUser(voipGwInfo.getUserId());
					if(sysOrganization!=null) {
						voipGwInfo.setOrgCode(sysOrganization.getCode());
					}
				} 
				/**2、初始化网关sip起始账号、密码、步长等信息**/
				this.initStartSipAccount(voipGwInfo);
				/**3、保存本地信息**/
				voipGwService.save(voipGwInfo);
				/**4、调用呼叫中心服务，注册账号密码**/
				SimCardVO fsGateway = new SimCardVO();
				fsGateway.setGatewayId(voipGwInfo.getId().toString());	//新增的网关id
				fsGateway.setStartCount(voipGwInfo.getStartSipAccount()); //起始账号
				fsGateway.setStartPwd(voipGwInfo.getStartSipPwd()); //起始密码
				fsGateway.setCountsStep(voipGwInfo.getSipAccountStep()); //账号步长
				fsGateway.setPwdStep(voipGwInfo.getSipPwdStep()); //密码步长
				fsGateway.setCountNum(voipGwInfo.getPortNum()); //端口数量
				FsSipVO fsSipVO = null;
				log.info("开始调用fsmanager服务注册网关：{}",fsGateway);
				ReturnData<FsSipVO> fsSipData = iSimCard.createGateway(fsGateway);
				log.info("结束调用fsmanager服务注册网关，返回：{}",fsSipData);
				if(fsSipData!=null && fsSipData.success && fsSipData.getBody()!=null) {
					fsSipVO = fsSipData.getBody();
					voipGwInfo.setSipIp(fsSipVO.getSipIp()); //用户网关注册到我们的fs的ip（外网ip ,和 lineip对应的，一个内网-内部注册，一个外网-给客户看）
					voipGwInfo.setSipPort(Integer.valueOf(fsSipVO.getSipPort())); //用户网关注册到我们的fs的端口
					voipGwInfo.setLinePort(Integer.valueOf(fsSipVO.getLinePort())); //提供给呼叫中心的线路端口，ip公用
				}else {
					throw new ClmException(ClmErrorEnum.CLM1809314.getErrorCode(),ClmErrorEnum.CLM1809314.getErrorMsg());
				}
				try {
					//重新更新ip/port
					voipGwService.save(voipGwInfo);
					/**5、初始化网关所有端口**/
					List<VoipGwPort> portList = voipGwService.initVoipGwPort(voipGwInfo);
					/**6、遍历端口，调用呼叫中心新增线路，并更新端口信息**/
					List<OutLineInfoAddReq> outLineInfoAddReqList = new ArrayList<OutLineInfoAddReq>();
					for(VoipGwPort port : portList) {
						//线路新增
						OutLineInfoAddReq lineInfo = new OutLineInfoAddReq();
						lineInfo.setLineName(voipGwInfo.getGwName()+"-"+port.getPort());
						lineInfo.setSipIp(fsSipVO.getLineIp());	//线路sip地址(内网IP)
						lineInfo.setSipPort(fsSipVO.getLinePort()); //新增线路时的使用端口
						lineInfo.setCodec("PCMA"); //网关编号都支持，所以此处默认个最常用编码：PCMA
						lineInfo.setCallerNum(port.getSipAccount().toString()); //主叫号码送网关端口账号
						lineInfo.setMaxConcurrentCalls(1);	//端口并发数默认1
						lineInfo.setOrgCode(voipGwInfo.getOrgCode()); //企业编号
						lineInfo.setLineType(2);
						lineInfo.setRemark("语音网关");
						outLineInfoAddReqList.add(lineInfo);
					}
					if(outLineInfoAddReqList!=null && !outLineInfoAddReqList.isEmpty()) {
						log.info("开始调用呼叫中心批量新增线路：{}",outLineInfoAddReqList);
						Result.ReturnData<List<LineInfoVO>> lineListData = iLineOperation.batchAddLineInfo(outLineInfoAddReqList);
						log.error("完成调用呼叫中心批量新增线路,返回结果:{}",lineListData);
						if(lineListData==null || lineListData.getBody()==null) {
							throw new ClmException(ClmErrorEnum.CLM1809308.getErrorCode(),ClmErrorEnum.CLM1809308.getErrorMsg());
						}
						List<LineInfoVO> lineList = lineListData.getBody();
						for(VoipGwPort port : portList) {
							for(LineInfoVO line : lineList) {
								if(port.getSipAccount().toString().equals(line.getCallerNum())) {
									//sip账号和 主叫号码一致
									port.setLineId(Integer.valueOf(line.getLineId()));
									voipGwPortService.save(port);
									break;
								}
							}
						}
					}
				}catch (Exception e) {
					//如果在调用fsmanager服务注册网关后，发生了异常，那么需要将新增的网关删除掉
					log.error("调用fsmanager删除网关:"+voipGwInfo.getId(),e);
					iSimCard.deleteGateway(voipGwInfo.getId().toString());
					throw new ClmException(ClmErrorEnum.CLM1809314.getErrorCode(),ClmErrorEnum.CLM1809314.getErrorMsg());
				}
				return voipGwInfo;
			} catch (ClmException e) {
				throw e; 
			} catch (Exception e1) {
				log.error("初始化VOIP网关异常！",e1);
				throw new ClmException(ClmErrorEnum.CLM1809314.getErrorCode(),ClmErrorEnum.CLM1809314.getErrorMsg());
			}finally {
				//释放锁
				distributedLockHandler.releaseLock(lock);
			}
		}else {
			log.error("用户资源删除未能获取到锁!");
			throw new ClmException(ClmErrorEnum.CLM1809315.getErrorCode(),ClmErrorEnum.CLM1809315.getErrorMsg());
		}
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
		Integer maxVoipSipAccount = this.queryMaxSipAccount(); 
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
	 * 查询下一个sip账号
	 * @return
	 */
	private Integer queryMaxSipAccount() {
		Integer maxSipAccount = voipGwInfoMapperExt.queryMaxSipAccount();
		if(maxSipAccount==null) {
			return ClmConstants.VOIP_ACCOUNT;
		}else {
			return maxSipAccount;
		}
	}
	
	
	/**
	 * 插卡/分配
	 * 1、批量分配,分配到人
	 * 2、如果有单价,调用计费中心开始计费
	 * @param gwPortList 要分配的端口
	 * @param operUserId 分配人
	 */
	@Transactional
	public void assignGwPort(List<VoipGwPort> gwPortList,String operUserId) {
		if(gwPortList==null) {
			//非空校验
			throw new ClmException(ClmErrorEnum.C00060001.getErrorCode(),ClmErrorEnum.C00060001.getErrorMsg());
		}
		List<VoipGwPort> feePortList = new ArrayList<VoipGwPort>();	//要计费端口
		List<VoipGwPort> unFeePortList = new ArrayList<VoipGwPort>();	//要解除计费端口
		for(VoipGwPort gwPort : gwPortList) {
			VoipGwPort port = voipGwPortService.queryById(gwPort.getId());
			if(StrUtils.isNotEmpty(port.getUserId()) && !port.getUserId().equals(gwPort.getUserId())) {
				//如果是换卡操作，那么检查被换卡的用户是否可以被换
				//调用调度中心检查线路是否在使用
				Result.ReturnData<Boolean> inUsedFlag = iDispatchPlanOut.lineIsUsedByUserId(port.getLineId(),Integer.valueOf(port.getUserId()));
				if(inUsedFlag.getBody().booleanValue()) {
					//在使用抛出异常，不能直接删除
					log.error("网关线路编号:{}仍在调度中心使用中，不能删除",port.getLineId());
					throw new ClmException(ClmErrorEnum.CLM1809310.getErrorCode(),ClmErrorEnum.CLM1809310.getErrorMsg());
				}
				// 复制一份记录取消计费数据
				VoipGwPort feePort = new VoipGwPort();
				BeanUtil.copyProperties(port, feePort);
				unFeePortList.add(feePort);
			}
			if(StrUtils.isNotEmpty(gwPort.getPhoneNo())) {
				port.setPhoneNo(gwPort.getPhoneNo());
			}
			port.setUserId(gwPort.getUserId());
			//设置企业
			if(StrUtils.isEmpty(gwPort.getOrgCode()) && StrUtils.isNotEmpty(gwPort.getUserId())) {
				SysOrganization sysOrganization = dataLocalCacheUtil.queryOrgByUser(gwPort.getUserId());
				if(sysOrganization!=null) {
					port.setOrgCode(sysOrganization.getCode());
				}
			}else {
				port.setOrgCode(gwPort.getOrgCode());
			}
			port.setCrtUser(operUserId);
			voipGwPortService.save(port);	//保存
			//将要计费数据复制一份
			VoipGwPort feePort = new VoipGwPort();
			BeanUtil.copyProperties(port, feePort);
			feePortList.add(feePort);
		}
		if(unFeePortList!=null && !unFeePortList.isEmpty()) {
			for(VoipGwPort port:unFeePortList) {
				//取消计费
				feeService.voipFee(FeeOptEnum.DEL, port);
			}
		}
		if(feePortList!=null && !feePortList.isEmpty()) {
			for(VoipGwPort port:feePortList) {
				//计费
				feeService.voipFee(FeeOptEnum.UP, port);
			}
		}
		
	}
	
	
	/**
	 * 拔卡
	 * @param gwPortIdList
	 * @param operUserId 操作人
	 */
	@Transactional
	public void unAssignGwPort(List<Integer> gwPortIdList,String operUserId) {
		if(gwPortIdList==null) {
			//非空校验
			throw new ClmException(ClmErrorEnum.C00060001.getErrorCode(),ClmErrorEnum.C00060001.getErrorMsg());
		}
		//计费项
		List<VoipGwPort> unFeePortList = new ArrayList<VoipGwPort>();
		for(Integer gwPortId : gwPortIdList) {
			VoipGwPort port = voipGwPortService.queryById(gwPortId);
			//调用调度中心检查线路是否在使用
			Result.ReturnData<Boolean> inUsedFlag = iDispatchPlanOut.lineIsUsedByUserId(port.getLineId(),Integer.valueOf(port.getUserId()));
			if(inUsedFlag.getBody().booleanValue()) {
				//在使用抛出异常，不能直接删除
				log.error("网关线路编号:{}仍在调度中心使用中，不能删除",port.getLineId());
				throw new ClmException(ClmErrorEnum.CLM1809310.getErrorCode(),ClmErrorEnum.CLM1809310.getErrorMsg());
			}
			// 复制一份记录取消计费数据
			VoipGwPort feePort = new VoipGwPort();
			BeanUtil.copyProperties(port, feePort);
			unFeePortList.add(feePort);
			port.setUserId(null);
			port.setOrgCode(null);
			port.setPhoneNo(null);
			port.setCrtUser(operUserId);
			voipGwPortService.save(port);
		}
		//删除计费项
		if(unFeePortList!=null && !unFeePortList.isEmpty()) {
			for(VoipGwPort port:unFeePortList) {
				//取消计费
				feeService.voipFee(FeeOptEnum.DEL, port);
			}
		}
	}
	
	
	/**
	 * 删除语音网关
	 * @param gwId
	 */
	public void delVoipGateway(Integer gwId){
		if(gwId!=null) {
			VoipGwInfo voipGwInfo = voipGwService.queryById(gwId);
			if(voipGwInfo==null) {
				log.info("语音网关{}不存在...",gwId);
				throw new ClmException(ClmErrorEnum.CLM1809316.getErrorCode(),ClmErrorEnum.CLM1809316.getErrorMsg());
			}
			//计费项
			List<VoipGwPort> unFeePortList = new ArrayList<VoipGwPort>();
			//根据网关编号查询所有端口
			List<VoipGwPort> portList = voipGwPortService.queryVoipGwPortsByGwId(gwId);
			/**1、线路使用中校验**/
			if(portList!=null && !portList.isEmpty()) {
				for(VoipGwPort port : portList) {
					if(port.getLineId()!=null) {
						//调用调度中心检查线路是否在使用
						if(StrUtils.isNotEmpty(port.getUserId())) {
							Result.ReturnData<Boolean> inUsedFlag = iDispatchPlanOut.lineIsUsedByUserId(port.getLineId(),Integer.valueOf(port.getUserId()));
							log.error("线路编号:{},调用调度中心检查是否使用中，返回结果：{}",port.getLineId(),inUsedFlag);
							if(inUsedFlag.getBody().booleanValue()) {
								//在使用抛出异常，不能直接删除
								throw new ClmException(ClmErrorEnum.CLM1809310.getErrorCode(),ClmErrorEnum.CLM1809310.getErrorMsg());
							}
						}
						// 复制一份记录取消计费数据
						VoipGwPort feePort = new VoipGwPort();
						BeanUtil.copyProperties(port, feePort);
						unFeePortList.add(feePort);
					}
				}
				//线路都没有使用的情况下，才可以删除网关
				/**2、调用呼叫中心删除线路**/
				for(VoipGwPort port : portList) {
					if(port.getLineId()!=null) {
						log.info("调用呼叫中心删除线路：{}",port.getLineId());
						iLineOperation.deleteLineInfo(port.getLineId());
					}
				}
				/**3、调用fsag**/
				log.error("调用fsmanager删除网关:{}",gwId);
				iSimCard.deleteGateway(gwId.toString());
				/**4、网关、端口设置删除标记**/
				voipGwInfo.setGwStatus(VoipGwStatusEnum.INVALID.getCode()); //删除标记
				voipGwService.save(voipGwInfo);
				voipGwPortService.delGwPortByGwId(gwId);
				/**5、删除计费项**/
				//删除计费项
				if(unFeePortList!=null && !unFeePortList.isEmpty()) {
					for(VoipGwPort port:unFeePortList) {
						//取消计费
						feeService.voipFee(FeeOptEnum.DEL, port);
					}
				}
			}
		}
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
	 * 根据条件查询网关端口信息(带监控信息)--分页查询
	 * @param condition
	 * @return
	 */
	public Page<VoipGwPortVO> queryVoipGwPortListWrapForPage(VoipGwPortQueryCondition condition) {
		Page<VoipGwPort> page = voipGwPortService.queryVoipGwPortForPageByCondition(condition);
		return new Page<VoipGwPortVO>(condition.getPageNo(),page.getTotalRecord(),this.changePort2VO(page.getRecords()));
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
				if(voipGwInfo.getCompanyId()!=null && voipGwInfo.getDevId()!=null && (!"三汇".equals(voipGwInfo.getGwBrand()) || !"鼎信".equals(voipGwInfo.getGwBrand()))) {
					ReturnData<GwDevtbl> gwDevtblData = voipGatewayRemote.queryGwDevByDevId(voipGwInfo.getGwBrand(), voipGwInfo.getCompanyId(), voipGwInfo.getDevId());
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

	@Autowired
	VoipGwInfoMapper voipGwInfoMapper;

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

			VoipGwInfo voipGwInfo = null;

			if(list.get(0).getCompanyId()!=null && list.get(0).getDevId()!=null) {
				//实时查询集中管理平台的端口信息

				voipGwInfo = voipGwInfoMapper.selectByPrimaryKey(list.get(0).getGwId());

				gwPortData = voipGatewayRemote.querySimPortListByDevId(voipGwInfo.getGwBrand(), list.get(0).getCompanyId(), list.get(0).getDevId());
			}
			for(VoipGwPort voipGwPort : list) {
				VoipGwPortVO vo = new VoipGwPortVO();
				BeanUtil.copyProperties(voipGwPort, vo);
				if(gwPortData!=null && gwPortData.getBody()!=null) {
					List<SimPort> simPortList = gwPortData.getBody();
					if(simPortList!=null && !simPortList.isEmpty()) {
						for(SimPort simPort : simPortList) {
							//如果是鼎信，匹配端口，如果是三汇，匹配用户名
							if(!(BrandConfig.DINGXIN.equals(voipGwInfo.getGwBrand()) && voipGwPort.getPort().equals(simPort.getPortNumber()+1)) &&
									!(BrandConfig.SYNWAY.equals(voipGwInfo.getGwBrand()) && voipGwPort.getSipAccount().equals(simPort.getPortNumber()))) continue;
							vo.setSipMatched(true); //账号匹配了
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
						}
					}
				}
				//设置用户姓名
				if(StrUtils.isNotEmpty(voipGwPort.getUserId())) {
					SysUser sysUser = dataLocalCacheUtil.queryUser(voipGwPort.getUserId());
					if(sysUser==null) {
						log.error("用户编号：{}查询不到用户信息",voipGwPort.getUserId());
						continue;
					}
					vo.setUserName(sysUser.getUsername());
				}
				rtnList.add(vo);
			}

			voipGwInfo = voipGwInfoMapper.selectByPrimaryKey(list.get(0).getGwId());

			//如果是鼎信，需端口减1，保持展示与设备一致
			if(BrandConfig.DINGXIN.equals(voipGwInfo.getGwBrand())) {
				for (VoipGwPortVO vo : rtnList) {
					vo.setPort(vo.getPort() - 1);
				}
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
	void fillVoipGwDevInfo(VoipGwInfo voipGwInfo) {
		if(voipGwInfo!=null && StrUtils.isEmpty(voipGwInfo.getCompanyId())) {
			//设备信息（公司信息）为空，那么调用网关服务重新查询下
			Result.ReturnData<GwDevtbl> gwDevtblData = voipGatewayRemote.queryCompanyByDevName(voipGwInfo.getGwBrand(), voipGwInfo.getGwName());
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

	/**
	 * 查询sim线路状态
	 * @param lineId
	 * @return
	 */
    public SimLineStatus querySimLineStatus(Integer lineId) {

		SimLineStatus status = new SimLineStatus();

		status.setLineId(lineId);

		VoipGwPortExample example = new VoipGwPortExample();

		example.createCriteria().andLineIdEqualTo(lineId);

		List<VoipGwPort> voipGwPorts = voipGwPortMapper.selectByExample(example);

		if(CollectionUtils.isEmpty(voipGwPorts)) {
			throw new ClmException(ClmErrorEnum.CLM1809319.getErrorCode(), ClmErrorEnum.CLM1809319.getErrorMsg());
		}

		VoipGwPort voipGwPort = voipGwPorts.get(0);

		VoipGwInfo voipGwInfo = voipGwInfoMapper.selectByPrimaryKey(voipGwPort.getGwId());

		String brand = voipGwInfo.getGwBrand();

		Integer companyId = voipGwPort.getCompanyId();

		Integer port = voipGwPort.getPort();

		Integer devId = voipGwPort.getDevId();

		if (null == devId) {
			status.setStatus(PortStatusEnum.NOT_REGIST.getStatus());
			status.setStatusMsg(PortStatusEnum.NOT_REGIST.getStatusMsg());
			return status;
		}

		PortRo ro = new PortRo();

		ro.setCompanyId(companyId);
		ro.setDevId(devId);
		ro.setGwBrand(brand);
		ro.setIp(voipGwInfo.getGwIp());
		ro.setPortNo(port-1);

		ReturnData<PortStatusEnum> data = voipGatewayRemote.querySipPortStatus(ro);

		if(data != null && data.success) {

			status.setStatusMsg(data.getBody().getStatusMsg());
			status.setStatus(data.getBody().getStatus());

			return status;
		} else {
			throw new ClmException(ClmErrorEnum.CLM1809319.getErrorCode(), ClmErrorEnum.CLM1809319.getErrorMsg());
		}
	}

	public List<VoipGwPort> queryByOrgCode(String code, String userId) {

    	VoipGwPortExample example = new VoipGwPortExample();

		VoipGwPortExample.Criteria criteria = example.createCriteria();
		criteria.andOrgCodeEqualTo(code);

    	if(StringUtils.isNotEmpty(userId)) {
    		criteria.andUserIdEqualTo(userId);
		}

		return voipGwPortMapper.selectByExample(example);

	}

}
