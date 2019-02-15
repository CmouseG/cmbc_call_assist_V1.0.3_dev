package com.guiji.clm.service.sip;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.guiji.botsentence.api.IBotSentenceTradeService;
import com.guiji.botsentence.api.entity.BotSentenceTradeVO;
import com.guiji.ccmanager.api.ILineOperation;
import com.guiji.ccmanager.entity.OutLineInfoAddReq;
import com.guiji.ccmanager.entity.OutLineInfoUpdateReq;
import com.guiji.clm.dao.entity.SipLineApply;
import com.guiji.clm.dao.entity.SipLineBaseInfo;
import com.guiji.clm.dao.entity.SipLineExclusive;
import com.guiji.clm.dao.entity.SipLineShare;
import com.guiji.clm.enm.SipLineApplyStatusEnum;
import com.guiji.clm.enm.SipLineApplyTypeEnum;
import com.guiji.clm.enm.SipLineFeeTypeEnum;
import com.guiji.clm.enm.SipLineStatusEnum;
import com.guiji.clm.enm.SipLineTypeEnum;
import com.guiji.clm.exception.ClmErrorEnum;
import com.guiji.clm.exception.ClmException;
import com.guiji.clm.service.fee.FeeService;
import com.guiji.clm.service.fee.FeeService.FeeOptEnum;
import com.guiji.clm.util.AreaDictUtil;
import com.guiji.clm.util.CheckUtil;
import com.guiji.clm.util.DataLocalCacheUtil;
import com.guiji.clm.vo.SipLineExclusiveQueryCondition;
import com.guiji.component.result.Result;
import com.guiji.user.dao.entity.SysOrganization;
import com.guiji.dispatch.api.IDispatchPlanOut;
import com.guiji.utils.DateUtil;
import com.guiji.utils.StrUtils;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import lombok.extern.slf4j.Slf4j;

/** 
* @Description: SIP线路管理功能
* @Author: weiyunbo
* @date 2019年1月23日 下午4:45:01 
* @version V1.0  
*/
@Slf4j
@Service
public class SipLineManager {
	@Autowired
	SipLineInfoService sipLineInfoService;
	@Autowired
	SipLineShareService sipLineShareService;
	@Autowired
	SipLineExclusiveService sipLineExclusiveService;
	@Autowired
	SipLineApplyService sipLineApplyService;
	@Autowired
	DataLocalCacheUtil dataLocalCacheUtil;
	@Autowired
	ILineOperation iLineOperation;
	@Autowired
	FeeService feeService;
	@Autowired
	IBotSentenceTradeService iBotSentenceTradeService;
	@Autowired
	IDispatchPlanOut IDispatchPlanOut;
	
	
	/**
	 * 第三方SIP线路配置
	 * 1、初始化数据
	 * 2、调用callcenter生成线路fs gateway
	 * @param sipLineBaseInfo
	 * @return
	 */
	@Transactional
	public SipLineBaseInfo thirdSipLineCfg(SipLineBaseInfo sipLineBaseInfo,Boolean isSuperAdmin) {
		if(sipLineBaseInfo==null || !CheckUtil.fieldIsNullCheck(sipLineBaseInfo, 
				new String[]{"lineName","sipIp","sipPort","codec","maxConcurrentCalls","univalent","industrys","overtArea","callDirec"})) {
			//非空校验
			throw new ClmException(ClmErrorEnum.C00060001.getErrorCode(),ClmErrorEnum.C00060001.getErrorMsg());
		}
		SipLineBaseInfo currentSipLine = new SipLineBaseInfo();
		BeanUtil.copyProperties(sipLineBaseInfo, currentSipLine); //留备份使用
		if(StrUtils.isNotEmpty(sipLineBaseInfo.getBelongUser())) {
			sipLineBaseInfo.setBelongUser(sipLineBaseInfo.getCrtUser());
		}
		SipLineBaseInfo existSipLine = null;
		if(sipLineBaseInfo.getId()!=null) {
			//更新
			//先查出原线路
			existSipLine = sipLineInfoService.queryById(sipLineBaseInfo.getId());
			BeanUtil.copyProperties(sipLineBaseInfo, existSipLine, CopyOptions.create().setIgnoreNullValue(true).setIgnoreError(true)
					.setIgnoreProperties("id","crtUser","crtTime","belongUser","orgCode","sipShareId","lineId"));
			sipLineBaseInfo = existSipLine;
		}
		if(StrUtils.isEmpty(sipLineBaseInfo.getOrgCode())) {
			//设置企业
			SysOrganization sysOrganization = dataLocalCacheUtil.queryUserRealOrg(sipLineBaseInfo.getCrtUser());
			if(sysOrganization!=null) {
				sipLineBaseInfo.setOrgCode(sysOrganization.getCode());
			}
		}
		//调用呼叫中心线路，生成线路ID
		Integer lineId = this.callcenterLine(currentSipLine, existSipLine);
		if(lineId!=null) {
			sipLineBaseInfo.setLineId(lineId);
		}
		//校验是否生成虚拟共享线路
		Integer shareLineId = initShareSipLine(sipLineBaseInfo, isSuperAdmin);
		if(shareLineId!=null) {
			//生成放到共享池的线路
			sipLineBaseInfo.setSipShareId(shareLineId);
		}
		sipLineInfoService.save(sipLineBaseInfo);
		if(sipLineBaseInfo.getSipShareId()==null) {
			//如果客户线路没有共享出去，那么全部分给自己
			SipLineExclusive sipLineExclusive = new SipLineExclusive();
			BeanUtil.copyProperties(sipLineBaseInfo, sipLineExclusive);
			if(StrUtils.isEmpty(sipLineBaseInfo.getBelongOrgCode())) {
				//如果分配企业为空，那么默认分配给自己
				log.error("线路{}没有共享，但是也没有分配给企业，默认分配给配置人所属企业",sipLineBaseInfo.getId());
				sipLineExclusive.setBelongOrgCode(sipLineBaseInfo.getOrgCode());
			}
			sipLineExclusive.setUnivalent(sipLineBaseInfo.getContractUnivalent()); //分配给自己使用时，价格用合同价
			sipLineExclusive.setSipLineId(sipLineBaseInfo.getId());
			sipLineExclusive.setId(null);
			this.splitExclusiveSipLine(sipLineExclusive);
		}
		return sipLineBaseInfo;
	}
	
	
	/**
	 * 删除第三方线路
	 * 1、检查显示是否有在使用
	 * 2、检查线路是否被申请
	 * 3、调用呼叫中心删除第三方线路
	 * 4、删除
	 * @param id
	 */
	public void delThirdSipLineCfg(Integer id) {
		//查询线路信息
		SipLineBaseInfo sipLineBaseInfo = sipLineInfoService.queryById(id);
		/**1、检查是否有在使用**/
		if(sipLineBaseInfo.getLineId()!=null) {
			//调用调度中心检查线路是否在使用
			Result.ReturnData<Boolean> inUsedFlag = IDispatchPlanOut.lineIsUsed(sipLineBaseInfo.getLineId());
			if(inUsedFlag.getBody().booleanValue()) {
				//在使用抛出异常，不能直接删除
				log.error("线路编号:{}仍在调度中心使用中，不能删除",sipLineBaseInfo.getLineId());
				throw new ClmException(ClmErrorEnum.CLM1809310.getErrorCode(),ClmErrorEnum.CLM1809310.getErrorMsg());
			}
		}
		/**3、调用呼叫中心删除线路**/
		iLineOperation.deleteLineInfo(sipLineBaseInfo.getLineId());
		/**4、删除本地**/
		sipLineInfoService.delete(id);
		
	}
	
	
	/**
	 * 从第三方SIP线路中拆出一条共享线路
	 * 1、校验是否超过了SIP线路最大并发数
	 * 2、设置下默认值
	 * 3、保存
	 * @param sipLineShare
	 */
	@Transactional
	public void splitShareSipLine(Integer sipLineId,SipLineShare sipLineShare) {
		if(sipLineId == null
				|| sipLineShare==null || !CheckUtil.fieldIsNullCheck(sipLineShare, 
				new String[]{"sipLineId","maxConcurrentCalls","univalent"})) {
			//非空校验
			throw new ClmException(ClmErrorEnum.C00060001.getErrorCode(),ClmErrorEnum.C00060001.getErrorMsg());
		}
		//根据线路编号查询原始信息
		SipLineBaseInfo sipBaseInfo = sipLineInfoService.queryById(sipLineId);
		//将分享出来的项目基本信息初始化下
		if(StrUtils.isEmpty(sipLineShare.getLineName())) {
			sipLineShare.setLineName(sipBaseInfo.getLineName());
		}
		if(StrUtils.isEmpty(sipLineShare.getSupplier())) {
			sipLineShare.setSupplier(sipBaseInfo.getSupplier());
		}
		if(StrUtils.isEmpty(sipLineShare.getBeginDate())) {
			sipLineShare.setBeginDate(sipBaseInfo.getBeginDate());
		}
		if(StrUtils.isEmpty(sipLineShare.getEndDate())) {
			sipLineShare.setEndDate(sipBaseInfo.getEndDate());
		}
		if(StrUtils.isEmpty(sipLineShare.getBelongUser())) {
			sipLineShare.setBelongUser(sipBaseInfo.getBelongUser());
		}
		if(StrUtils.isEmpty(sipLineShare.getOrgCode())) {
			sipLineShare.setOrgCode(sipBaseInfo.getOrgCode());
		}
		if(StrUtils.isEmpty(sipLineShare.getRemark())) {
			sipLineShare.setRemark(sipBaseInfo.getRemark());
		}
		if(StrUtils.isEmpty(sipLineShare.getCallDirec())) {
			sipLineShare.setCallDirec(sipBaseInfo.getCallDirec());
		}
		if(StrUtils.isEmpty(sipLineShare.getOvertArea())) {
			sipLineShare.setOvertArea(sipBaseInfo.getOvertArea());
		}
		sipLineShare.setLineStatus(SipLineStatusEnum.OK.getCode()); //默认状态:正常
		sipLineShareService.save(sipLineShare);
	}
	
	/**
	 * 从第三方SIP线路中拆出一条独享(自己的)线路--到企业
	 * 1、校验是否有超总并发
	 * 2、设置下默认值
	 * 3、保存
	 * 4、已经分配到用户了，此时通知费用中心进行计费准备
	 * 
	 * @param sipLineShare
	 */
	@Transactional
	public void splitExclusiveSipLine(SipLineExclusive sipLineExclusive) {
		if(sipLineExclusive==null || !CheckUtil.fieldIsNullCheck(sipLineExclusive, 
				new String[]{"sipLineId","maxConcurrentCalls","univalent","belongOrgCode"})) {
			//非空校验
			throw new ClmException(ClmErrorEnum.C00060001.getErrorCode(),ClmErrorEnum.C00060001.getErrorMsg());
		}
		Integer sipLineId = sipLineExclusive.getSipLineId();	//sip线路编号
//		if(this.checkOverSipMaxCallNum(sipLineId, null, sipLineExclusive.getMaxConcurrentCalls())) {
//			//本次分配超过了SIP线路最大并发量
//			throw new ClmException(ClmErrorEnum.CLM1809303.getErrorCode(),ClmErrorEnum.CLM1809303.getErrorMsg());
//		}
		//根据线路编号查询原始信息
		SipLineBaseInfo sipBaseInfo = sipLineInfoService.queryById(sipLineId);
		//将分享出来的项目基本信息初始化下
		if(StrUtils.isEmpty(sipLineExclusive.getLineName())) {
			sipLineExclusive.setLineName(sipBaseInfo.getLineName());
		}
		if(StrUtils.isEmpty(sipLineExclusive.getSupplier())) {
			sipLineExclusive.setSupplier(sipBaseInfo.getSupplier());
		}
		if(StrUtils.isEmpty(sipLineExclusive.getBeginDate())) {
			sipLineExclusive.setBeginDate(sipBaseInfo.getBeginDate());
		}
		if(StrUtils.isEmpty(sipLineExclusive.getEndDate())) {
			sipLineExclusive.setEndDate(sipBaseInfo.getEndDate());
		}
		if(StrUtils.isEmpty(sipLineExclusive.getRemark())) {
			sipLineExclusive.setRemark(sipBaseInfo.getRemark());
		}
		if(StrUtils.isEmpty(sipLineExclusive.getCallDirec())) {
			sipLineExclusive.setCallDirec(sipBaseInfo.getCallDirec());
		}
		if(StrUtils.isEmpty(sipLineExclusive.getOvertArea())) {
			sipLineExclusive.setOvertArea(sipBaseInfo.getOvertArea());
		}
		if(StrUtils.isEmpty(sipLineExclusive.getOvertArea())) {
			sipLineExclusive.setOvertArea(sipBaseInfo.getOvertArea());
		}
		if(sipLineExclusive.getLineFeeType()==null) {
			sipLineExclusive.setLineFeeType(sipBaseInfo.getLineFeeType());
		}
		sipLineExclusive.setLineStatus(SipLineStatusEnum.OK.getCode()); //默认状态:正常
		sipLineExclusive.setLineType(SipLineTypeEnum.SELF.getCode()); //线路类型：自营
		sipLineExclusiveService.save(sipLineExclusive);
		//计费准备
		feeService.sipFee(FeeOptEnum.UP, sipLineExclusive);
	}
	
	/**
	 * 发起一条线路申请
	 * 1、基本数据校验
	 * 2、根据申请类型，特殊字段校验
	 * 3、设置默认值
	 * 4、扣减原线路剩余并发数
	 * 5、保存申请信息
	 * @param sipLineApply
	 */
	@Transactional
	public void applySipLine(SipLineApply sipLineApply){
		if(sipLineApply==null || sipLineApply.getApplyType()==null || sipLineApply.getMaxConcurrentCalls()==null || StrUtils.isEmpty(sipLineApply.getApplyUser())) {
			//非空校验
			throw new ClmException(ClmErrorEnum.C00060001.getErrorCode(),ClmErrorEnum.C00060001.getErrorMsg());
		}
		Integer applyType = sipLineApply.getApplyType();	//申请类型
		int maxConcurrentCalls = sipLineApply.getMaxConcurrentCalls();	//并发数
		String applyUser = sipLineApply.getApplyUser();	//申请人
		//开始检查是否有超过企业机器人数量配置
		SysOrganization sysOrganization = null;
		if(StrUtils.isEmpty(sipLineApply.getApplyOrgCode())) {
			//设置企业
			sysOrganization = dataLocalCacheUtil.queryUserRealOrg(sipLineApply.getApplyUser());
			if(sysOrganization!=null) {
				sipLineApply.setApplyOrgCode(sysOrganization.getCode());
			}
		}else {
			sysOrganization = dataLocalCacheUtil.queryOrgByCode(sipLineApply.getApplyOrgCode());
		}
		//获取企业配置的机器人数量
		Integer orgRobotNum = sysOrganization.getRobot();
		if(orgRobotNum==null) {
			log.error("企业{}没有配置机器人,不能申请",sysOrganization.getCode());
			throw new ClmException(ClmErrorEnum.CLM1809307.getErrorCode(),ClmErrorEnum.CLM1809307.getErrorMsg());
		}else if(maxConcurrentCalls>orgRobotNum) {
			log.error("用户{}申请的线路并发量{}不能大于企业机器人数量{}",applyUser,maxConcurrentCalls,orgRobotNum);
			throw new ClmException(ClmErrorEnum.CLM1809306.getErrorCode(),ClmErrorEnum.CLM1809306.getErrorMsg());
		}
		if(SipLineApplyTypeEnum.NEW_LINE.getCode()==applyType) {
			//新线路申请
			if(sipLineApply==null || !CheckUtil.fieldIsNullCheck(sipLineApply, 
					new String[]{"agentLineId","maxConcurrentCalls","templates","applyType","applyUser"})) {
				//非空校验
				throw new ClmException(ClmErrorEnum.C00060001.getErrorCode(),ClmErrorEnum.C00060001.getErrorMsg());
			}
			//根据线路编号查询要代理的线路信息
			SipLineShare sipShareInfo = sipLineShareService.queryById(sipLineApply.getAgentLineId());
			//将分享出来的项目基本信息初始化下
			if(StrUtils.isEmpty(sipLineApply.getLineName())) {
				sipLineApply.setLineName(sipShareInfo.getLineName());
			}
			if(StrUtils.isEmpty(sipLineApply.getSupplier())) {
				sipLineApply.setSupplier(sipShareInfo.getSupplier());
			}
			if(StrUtils.isEmpty(sipLineApply.getBeginDate())) {
				sipLineApply.setBeginDate(sipShareInfo.getBeginDate());
			}
			if(StrUtils.isEmpty(sipLineApply.getEndDate())) {
				sipLineApply.setEndDate(sipShareInfo.getEndDate());
			}
			if(StrUtils.isEmpty(sipLineApply.getRemark())) {
				sipLineApply.setRemark(sipShareInfo.getRemark());
			}
			if(sipLineApply.getUnivalent()==null) {
				sipLineApply.setUnivalent(sipShareInfo.getUnivalent());
			}
			if(StrUtils.isEmpty(sipLineApply.getCallDirec())) {
				sipLineApply.setCallDirec(sipShareInfo.getCallDirec());
			}
			if(StrUtils.isEmpty(sipLineApply.getOvertArea())) {
				sipLineApply.setOvertArea(sipShareInfo.getOvertArea());
			}
			sipLineApply.setBelongUser(sipShareInfo.getBelongUser());	//设置虚拟线路归属人（审批权限控制使用）
			sipLineApply.setOrgCode(sipShareInfo.getOrgCode());	//设置虚拟线路所属企业（审批权限控制使用）
			sipShareInfo.setApplyNum((sipShareInfo.getApplyNum()==null?0:sipShareInfo.getApplyNum())+1); //申请次数+1
			sipLineShareService.save(sipShareInfo);
		}else if(SipLineApplyTypeEnum.LINE_MERGE.getCode()==applyType) {
			//线路变更,如并发数、话术模板编号、开始、结束日期、单价 等信息会影响业务操作的一些属性变更
			if(sipLineApply==null || !CheckUtil.fieldIsNullCheck(sipLineApply, 
					new String[]{"sipLineId","agentLineId","upSipLineId"})) {
				//非空校验
				throw new ClmException(ClmErrorEnum.C00060001.getErrorCode(),ClmErrorEnum.C00060001.getErrorMsg());
			}
		}else if(SipLineApplyTypeEnum.BUSI_MERGE.getCode()==applyType) {
			//业务数据变更  TODO
		}
		sipLineApply.setApplyDate(DateUtil.getCurrentymd());	//申请日期
		sipLineApply.setApplyTime(DateUtil.getCurrent4Time());  //申请时间
		sipLineApply.setApplyStatus(SipLineApplyStatusEnum.ING.getCode()); //申请中
		sipLineApplyService.save(sipLineApply);
	}
	
	/**
	 * 审批-线路申请信息
	 * 1、保存申请信息的审批数据
	 * 2.1、审批通过,变更用户业务数据
	 * 2.2、审批通过,占用共享出去的线路并发数
	 * 3、通知费用中心
	 * 4、调用消息中心通知审批人审批结果
	 * @param sipLineApply
	 */
	@Transactional
	public void approveSipLine(SipLineApply sipLineApply){
		//新线路申请
		if(sipLineApply==null || !CheckUtil.fieldIsNullCheck(sipLineApply, 
				new String[]{"id","applyStatus","approveUser"})) {
			//非空校验
			throw new ClmException(ClmErrorEnum.C00060001.getErrorCode(),ClmErrorEnum.C00060001.getErrorMsg());
		}
		//查询申请信息
		SipLineApply sipLineApplyExist = sipLineApplyService.queryById(sipLineApply.getId());
		sipLineApplyExist.setSipLineId(sipLineApply.getSipLineId()); //线路编号
		sipLineApplyExist.setApplyStatus(sipLineApply.getApplyStatus());	//审批状态
		sipLineApplyExist.setApproveUser(sipLineApply.getApproveUser());	//审批人
		sipLineApplyExist.setApproveDate(DateUtil.getCurrentymd());	//审批日期
		sipLineApplyExist.setApproveTime(DateUtil.getCurrent4Time());	//审批时间
		sipLineApplyExist.setApproveRemark(sipLineApply.getApproveRemark()); //审批备注
		sipLineApplyExist.setUpdateUser(sipLineApply.getApproveUser());	//审批人
		sipLineApplyService.save(sipLineApplyExist);
		boolean feeNoticeChangeFlag = false;	//费用相关信息是否变更
		if(SipLineApplyStatusEnum.APPROVE.getCode()==sipLineApplyExist.getApplyStatus()) {
			//审批通过
			if(sipLineApplyExist.getSipLineId()==null) {
				//如果审批通过，那么必须选择审批给客户的线路
				throw new ClmException(ClmErrorEnum.C00060001.getErrorCode(),ClmErrorEnum.C00060001.getErrorMsg());
			}
			/**1、变更业务数据 **/
			SipLineExclusive sipLineExclusive = new SipLineExclusive();
			SipLineBaseInfo sipLineBaseInfo = sipLineInfoService.queryById(sipLineApplyExist.getSipLineId());
			Integer applyType = sipLineApplyExist.getApplyType();	//申请类型
			if(SipLineApplyTypeEnum.NEW_LINE.getCode()==applyType) {
				//增加原线路已用并发数
				sipLineBaseInfo.setUseConcurrentCalls(sipLineApplyExist.getMaxConcurrentCalls().intValue()+(sipLineBaseInfo.getUseConcurrentCalls()==null?0:sipLineBaseInfo.getUseConcurrentCalls()));
				if(sipLineBaseInfo.getUseConcurrentCalls()>sipLineBaseInfo.getMaxConcurrentCalls()) {
					//超过了最大并发数
					throw new ClmException(ClmErrorEnum.CLM1809303.getErrorCode(),ClmErrorEnum.CLM1809303.getErrorMsg());
				}
				sipLineInfoService.save(sipLineBaseInfo);
				feeNoticeChangeFlag = true;
				BeanUtil.copyProperties(sipLineApplyExist, sipLineExclusive);
				sipLineExclusive.setId(null); //重新生成
				sipLineExclusive.setLineStatus(SipLineStatusEnum.OK.getCode()); //默认状态:正常
				sipLineExclusive.setLineType(SipLineTypeEnum.AGENT.getCode()); //线路类型：代理
//				sipLineExclusive.setUserId(sipLineApplyExist.getApplyUser()); //线路归属人（审批后线路分配给企业，不需要到人，因为申请人是企业管理员）
				sipLineExclusive.setBelongOrgCode(sipLineApplyExist.getApplyOrgCode()); //线路归属人企业
				sipLineExclusive.setLineFeeType(sipLineBaseInfo.getLineFeeType()); //线路计费类型
				sipLineExclusive.setCrtUser(sipLineApplyExist.getApplyUser()); //创建人
				sipLineExclusive.setUpdateUser(sipLineApplyExist.getApplyUser());	//最后更新人
			}else if(SipLineApplyTypeEnum.LINE_MERGE.getCode()==applyType) {
				//线路变更,如并发数、话术模板编号、开始、结束日期、单价 等信息会影响业务操作的一些属性变更
				Integer upSipLineId = sipLineApplyExist.getUpSipLineId();	//变更的独享线路
				sipLineExclusive = sipLineExclusiveService.queryById(upSipLineId);
				if(sipLineApplyExist.getMaxConcurrentCalls()!=null && !sipLineApplyExist.getMaxConcurrentCalls().equals(sipLineExclusive.getMaxConcurrentCalls())) {
					//如果变更了最大并发数
					//本次变更并发数
					int changeNum = sipLineApplyExist.getMaxConcurrentCalls()-sipLineExclusive.getMaxConcurrentCalls();
					if(changeNum!=0) {
						sipLineBaseInfo.setUseConcurrentCalls(changeNum+(sipLineBaseInfo.getUseConcurrentCalls()==null?0:sipLineBaseInfo.getUseConcurrentCalls()));
						if(sipLineBaseInfo.getUseConcurrentCalls()>sipLineBaseInfo.getMaxConcurrentCalls()) {
							//超过了最大并发数
							throw new ClmException(ClmErrorEnum.CLM1809303.getErrorCode(),ClmErrorEnum.CLM1809303.getErrorMsg());
						}
						sipLineInfoService.save(sipLineBaseInfo);
					}
				}
				if(sipLineApplyExist.getUnivalent()!=null && sipLineApplyExist.getUnivalent().compareTo(sipLineExclusive.getUnivalent())!=0) {
					//单价变化了，也要通知费用中心，重新计算计费
					feeNoticeChangeFlag = true;
				}
				//重置下变更的数据值
				//属性拷贝（1、忽略源对象空值 2、忽略属性id/crtUser/crtTime）
				BeanUtil.copyProperties(sipLineApplyExist,sipLineExclusive,CopyOptions.create().setIgnoreCase(true).setIgnoreError(true).setIgnoreProperties("id","crtUser","crtTime"));
			}else if(SipLineApplyTypeEnum.BUSI_MERGE.getCode()==applyType) {
				//业务数据变更  TODO
			}
			//同步到独享线路池
			sipLineExclusive.setApplyId(sipLineApplyExist.getId()); //申请id
			sipLineExclusive.setLineId(sipLineBaseInfo.getLineId()); //线路id
			sipLineExclusive.setIndustrys(sipLineBaseInfo.getIndustrys()); //行业
			sipLineExclusive.setAreas(sipLineBaseInfo.getAreas()); //地区
			sipLineExclusive.setExceptAreas(sipLineBaseInfo.getExceptAreas()); //盲区
			sipLineExclusiveService.save(sipLineExclusive);
			/**2、校验是否需要变更费用，通知计费中心**/
			if(feeNoticeChangeFlag) {
				feeService.sipFee(FeeOptEnum.UP, sipLineExclusive);
			}
		}else if(SipLineApplyStatusEnum.REJECT.getCode()==sipLineApplyExist.getApplyStatus()) {
			//拒绝
			log.info("申请{},审批结果为拒绝",sipLineApplyExist.getId());
		}else {
			throw new ClmException("申请类型枚举类型错误!");
		}
		//TODO 
		log.info("调用消息中心,通知申请人申请结果...");
	}
	
	
	/**
	 * 校验在拆线路或者更新时是否有超过线路的最大并发数
	 * @param sipBaseId	第三方线路ID
	 * @param sipSplitId 本次拆出来的线路ID
	 * @param newNum 本次拆分或者更新后的并发数
	 * @return
	 */
	private boolean checkOverSipMaxCallNum(Integer sipBaseId,Integer sipSplitId,Integer newNum) {
		log.info("本次并发数是否超第三方SIP线路校验开始：sipBaseId={},sipSplitId={},newNum={}",sipBaseId,sipSplitId,newNum);
		if(sipBaseId!=null && newNum!=null) {
			SipLineBaseInfo sipLineBaseInfo = sipLineInfoService.queryById(sipBaseId);
			int maxNum = sipLineBaseInfo.getMaxConcurrentCalls();	//第三方线路的最大并发数
			//查询目前正常的独享自营线路
			SipLineExclusiveQueryCondition exclusiveCondition = new SipLineExclusiveQueryCondition();
			exclusiveCondition.setSipLineId(sipBaseId);
			exclusiveCondition.setLineStatus(SipLineStatusEnum.OK.getCode());
			exclusiveCondition.setLineType(SipLineTypeEnum.SELF.getCode());
			List<SipLineExclusive> exclusiveList = sipLineExclusiveService.querySipLineExclusiveList(exclusiveCondition);
			//独享线路并发总数
			int exclusiveNum = 0;
			if(!exclusiveList.isEmpty()) {
				for(SipLineExclusive exclusive : exclusiveList) {
					if(sipSplitId!=null && sipSplitId.equals(exclusive.getId())) {
						//如果本次传了splitid，那么表示是数据更新，newNum变更为增量
						newNum = newNum - (exclusive.getMaxConcurrentCalls()==null?0:exclusive.getMaxConcurrentCalls());
					}
					exclusiveNum = exclusiveNum + (exclusive.getMaxConcurrentCalls()==null?0:exclusive.getMaxConcurrentCalls());
				}
			}
			log.info("该SIP线路总并发={},正常分配独享线路并发={},本次申请或者增量并发={}",maxNum,exclusiveNum,newNum);
			if(exclusiveNum+newNum>maxNum) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 变更SIP线路时是否检查是否生成共享线路
	 * @param sipLineBaseInfo
	 */
	@Transactional
	private Integer initShareSipLine(SipLineBaseInfo sipLineBaseInfo,Boolean isSuperAdmin) {
		Integer callDirec = sipLineBaseInfo.getCallDirec();	//呼叫方向
		String industrys = sipLineBaseInfo.getIndustrys();	//行业
		String overtArea = sipLineBaseInfo.getOvertArea();	//外显归属地
		BigDecimal univalent = sipLineBaseInfo.getUnivalent();	//单价
		String orgCode = sipLineBaseInfo.getOrgCode();	//企业
		if(StrUtils.isEmpty(sipLineBaseInfo.getBelongOrgCode())) {
			//如果没有选择线路分配企业，那么才允许生成共享线路
			//按4个条件查询是否已经有了共享线路
			SipLineShare sipLineShare = sipLineShareService.querySipLineShareByUnit(orgCode,industrys, overtArea, callDirec, univalent);
			if(sipLineShare!=null) {
				//返回满足条件的共享线路ID
				return sipLineShare.getId();
			}else {
				//不存在，新建一条共享线路
				sipLineShare = new SipLineShare();
				BeanUtil.copyProperties(sipLineBaseInfo, sipLineShare, CopyOptions.create().setIgnoreNullValue(true).setIgnoreError(true).setIgnoreProperties("id"));
				sipLineShare.setLineStatus(SipLineStatusEnum.OK.getCode()); //初始默认正常状态
				//重新定义虚拟线路名称
				sipLineShare.setLineName(this.genShareSipLineName(sipLineBaseInfo));
				sipLineShareService.save(sipLineShare);
				return sipLineShare.getId();
			}
		}
		return null;
	}
	
	
	/**
	 * 定义发不出去的共享线路名称：自营贷款线路-上海
	 * @param sipLineBaseInfo
	 * @return
	 */
	private String genShareSipLineName(SipLineBaseInfo sipLineBaseInfo) {
		String industry = sipLineBaseInfo.getIndustrys();	//话术行业
		String orgCode = sipLineBaseInfo.getOrgCode();	//SIP线路归属企业
		String[] industryArray = industry.split(","); //行业类似：01,0101
		String trade = industryArray[industryArray.length-1]; //需要的行业：0101
		log.info("调用话术市场查询行业:{}中文名",trade);
		Result.ReturnData<BotSentenceTradeVO> tempTradeData = iBotSentenceTradeService.queryTradeByTradeId(trade);
		if(tempTradeData==null || tempTradeData.getBody()==null) {
			log.error("调用话术市场查询行业错误,返回信息：{}",tempTradeData);
			throw new ClmException(ClmErrorEnum.CLM1809309.getErrorCode(),ClmErrorEnum.CLM1809309.getErrorMsg());
		}
		//行业名称
		String tradeName = tempTradeData.getBody().getIndustryName();
		//线路归属
		String lineOwner = this.getLineOwner(sipLineBaseInfo);
		//外显归属地
		String overtArea = AreaDictUtil.getAreaName(sipLineBaseInfo.getOvertArea());
		if(overtArea.indexOf("省")>0) {
			overtArea = overtArea.substring(0, overtArea.indexOf("省"));
		}else if(overtArea.indexOf("市")>0) {
			overtArea = overtArea.substring(0, overtArea.indexOf("市"));
		}
		return lineOwner+tradeName+"线路-"+overtArea;
	}
	
	/**
	 * 根据共享线路企业信息，获取线路拥有者类型
	 * @param org
	 * @return
	 */
	public String getLineOwner(SipLineBaseInfo sipLineBaseInfo) {
		SysOrganization org = dataLocalCacheUtil.queryOrgByCode(sipLineBaseInfo.getOrgCode());
		if(org!=null) {
			if(sipLineBaseInfo.getLineFeeType()!=null && SipLineFeeTypeEnum.UNDO_FEE.getCode()==sipLineBaseInfo.getLineFeeType()) {
				//如果管理员选择线路时自备线路了，那么直接返回自备线路
				return "自备";
			}
			if(org.getType()!=null ) {
				if(1==org.getType()) {
					if("1".equals(org.getCode())) {
						//总部
						return "自营";
					}else {
						return "代理商";
					}
				}else if(2==org.getType()) {
					return "自备";
				}
			}
		}
		return "自营";
	}
	
	/**
	 * 根据共享线路企业信息，获取线路拥有者类型
	 * @param org
	 * @return
	 */
	public String getLineOwner(SysOrganization org) {
		if(org!=null) {
			if(org.getType()!=null ) {
				if(1==org.getType()) {
					if("1".equals(org.getCode())) {
						//总部
						return "自营";
					}else {
						return "代理商";
					}
				}else if(2==org.getType()) {
					return "自备";
				}
			}
		}
		return "自营";
	}
	
	
	/**
	 * 线路变更后调用呼叫中心的线路
	 * @param sipLineBaseInfo
	 * @param existSipLineBaseInfo
	 */
	private Integer callcenterLine(SipLineBaseInfo sipLineBaseInfo,SipLineBaseInfo existSipLineBaseInfo) {
		if(existSipLineBaseInfo==null) {
			//线路新增
			OutLineInfoAddReq lineInfo = new OutLineInfoAddReq();
			lineInfo.setLineName(sipLineBaseInfo.getLineName());
			lineInfo.setSipIp(sipLineBaseInfo.getSipIp());
			lineInfo.setSipPort(sipLineBaseInfo.getSipPort().toString());
			lineInfo.setCodec(sipLineBaseInfo.getCodec());
			lineInfo.setCallerNum(sipLineBaseInfo.getCallerNum());
			lineInfo.setCalleePrefix(sipLineBaseInfo.getDestinationPrefix());
			lineInfo.setMaxConcurrentCalls(sipLineBaseInfo.getMaxConcurrentCalls());
			lineInfo.setOrgCode(sipLineBaseInfo.getOrgCode());
			Result.ReturnData<Integer> lineIdData = iLineOperation.addLineInfo(lineInfo);
			if(lineIdData==null || lineIdData.getBody()==null) {
				log.error("调用呼叫中心新增线路异常,返回结果:{}",lineIdData);
				throw new ClmException(ClmErrorEnum.CLM1809308.getErrorCode(),ClmErrorEnum.CLM1809308.getErrorMsg());
			}
			return lineIdData.getBody();
		}else if(existSipLineBaseInfo!=null && existSipLineBaseInfo.getLineId()!=null) {
			//线路变更
			//检查下本次变更后和原来的是否有变化
			if(!sipLineBaseInfo.getSipIp().equals(existSipLineBaseInfo.getSipIp())
					||!sipLineBaseInfo.getSipPort().equals(existSipLineBaseInfo.getSipPort())
					||!sipLineBaseInfo.getCodec().equals(existSipLineBaseInfo.getCodec())
					||!sipLineBaseInfo.getCallerNum().equals(existSipLineBaseInfo.getCallerNum())
					||!sipLineBaseInfo.getDestinationPrefix().equals(existSipLineBaseInfo.getDestinationPrefix())
					||!sipLineBaseInfo.getMaxConcurrentCalls().equals(existSipLineBaseInfo.getMaxConcurrentCalls())
					) {
				OutLineInfoUpdateReq lineInfo = new OutLineInfoUpdateReq();
				lineInfo.setLineId(existSipLineBaseInfo.getLineId());
				lineInfo.setSipIp(sipLineBaseInfo.getSipIp());
				lineInfo.setSipPort(sipLineBaseInfo.getSipPort().toString());
				lineInfo.setCodec(sipLineBaseInfo.getCodec());
				lineInfo.setCallerNum(sipLineBaseInfo.getCallerNum());
				lineInfo.setCalleePrefix(sipLineBaseInfo.getDestinationPrefix());
				lineInfo.setMaxConcurrentCalls(sipLineBaseInfo.getMaxConcurrentCalls());
				lineInfo.setOrgCode(sipLineBaseInfo.getOrgCode());
				Result.ReturnData upData = iLineOperation.updateLineInfo(lineInfo);
				if(upData==null || !upData.success) {
					log.error("调用呼叫中心变更线路异常,返回结果:{}",upData);
					throw new ClmException(ClmErrorEnum.CLM1809308.getErrorCode(),ClmErrorEnum.CLM1809308.getErrorMsg());
				}
			}
		}
		return null;
	}
	
}
