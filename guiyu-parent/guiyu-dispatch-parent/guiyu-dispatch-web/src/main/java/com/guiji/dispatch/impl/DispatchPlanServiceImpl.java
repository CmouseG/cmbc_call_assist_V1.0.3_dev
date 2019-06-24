package com.guiji.dispatch.impl;

import com.alibaba.fastjson.JSONObject;
import com.guiji.auth.api.IAuth;
import com.guiji.auth.api.IOrg;
import com.guiji.ccmanager.api.ICallPlanDetail;
import com.guiji.ccmanager.entity.CallPlanUuidQuery;
import com.guiji.ccmanager.vo.CallPlanDetailRecordVO;
import com.guiji.common.exception.GuiyuException;
import com.guiji.component.result.Result.ReturnData;
import com.guiji.dispatch.batchimport.BatchImportQueueHandler;
import com.guiji.dispatch.bean.*;
import com.guiji.dispatch.constant.RedisConstant;
import com.guiji.dispatch.dao.DispatchPlanBatchMapper;
import com.guiji.dispatch.dao.DispatchPlanMapper;
import com.guiji.dispatch.dao.FileErrorRecordsMapper;
import com.guiji.dispatch.dao.ThirdInterfaceRecordsMapper;
import com.guiji.dispatch.dao.entity.DispatchBatchLine;
import com.guiji.dispatch.dao.entity.DispatchPlan;
import com.guiji.dispatch.dao.entity.*;
import com.guiji.dispatch.dao.entity.FileErrorRecords;
import com.guiji.dispatch.dao.entity.DispatchPlanExample.Criteria;
import com.guiji.dispatch.dto.QueryDownloadPlanListDto;
import com.guiji.dispatch.dto.QueryPlanListDto;
import com.guiji.dispatch.enums.*;
import com.guiji.dispatch.exception.BaseException;
import com.guiji.dispatch.line.IDispatchBatchLineService;
import com.guiji.dispatch.model.*;
import com.guiji.dispatch.pushcallcenter.SuccessPhoneMQService;
import com.guiji.dispatch.service.*;
import com.guiji.dispatch.sys.ResultPage;
import com.guiji.dispatch.util.Constant;
import com.guiji.dispatch.util.DaoHandler;
import com.guiji.dispatch.util.DateTimeUtils;
import com.guiji.dispatch.util.ResHandler;
import com.guiji.dispatch.vo.DispatchPlanVo;
import com.guiji.dispatch.vo.DownLoadPlanVo;
import com.guiji.dispatch.vo.TotalPlanCountVo;
import com.guiji.robot.api.IRobotRemote;
import com.guiji.robot.model.CheckParamsReq;
import com.guiji.robot.model.CheckResult;
import com.guiji.robot.model.CustTemplateVo;
import com.guiji.robot.model.HsParam;
import com.guiji.user.dao.entity.SysOrganization;
import com.guiji.user.dao.entity.SysUser;
import com.guiji.utils.DateUtil;
import com.guiji.utils.IdGengerator.IdUtils;
import com.guiji.utils.IdGengerator.SnowflakeIdWorker;
import com.guiji.utils.JsonUtils;
import com.guiji.utils.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class DispatchPlanServiceImpl implements IDispatchPlanService {
	static Logger logger = LoggerFactory.getLogger(DispatchPlanServiceImpl.class);

	@Autowired
	private DispatchPlanMapper dispatchPlanMapper;

	@Autowired
	private DispatchPlanBatchMapper dispatchPlanBatchMapper;

	@Autowired
	private IAuth authService;

	@Autowired
	private IOrg orgService;

	@Autowired
	private RedisUtil redisUtil;

	@Autowired
	private IRobotRemote robotRemote;

	@Autowired
	private IAuth auth;

	@Autowired
	private ICallPlanDetail callPlanDetail;

	@Autowired
	private ThirdInterfaceRecordsMapper thirdInterfaceRecordsMapper;

	@Autowired
	private IDispatchPlanPutCalldata planPutCalldata;
	@Autowired
	private SuccessPhoneMQService successPhoneMQService;

	@Autowired
	private IBlackListService blackService;
	@Autowired
	IPhonePlanQueueService phonePlanQueueService;

	@Autowired
	private IDispatchBatchLineService lineService;
	@Autowired
	private IPhoneRegionService phoneRegionService;

	@Autowired
    private GateWayLineService gateWayLineService;

	@Autowired
	private com.guiji.dispatch.dao.ext.PlanExtMapper planExtMapper;

	@Autowired
	private GetAuthUtil getAuthUtil;

	@Autowired
	private GetApiService getApiService;

	/**
	 * 单个任务导入
	 * @param dispatchPlan
	 * @param userId
	 * @param orgCode
	 * @return
	 * @throws Exception
	 */
	@Override
	public MessageDto addSchedule(DispatchPlan dispatchPlan, Long userId, String orgCode, Integer orgId) throws Exception {
		boolean checkPhoneInBlackList = blackService.checkPhoneInBlackList(dispatchPlan.getPhone(), orgCode);
		MessageDto dto = new MessageDto();
		dispatchPlan.setPlanUuid(SnowflakeIdWorker.nextId(orgId));
		// 检查参数
		ReturnData<List<CheckResult>> checkParams = checkParams(dispatchPlan);
		if (checkParams.success) {
			if (checkParams.getBody() != null) {
				List<CheckResult> body = checkParams.getBody();
				CheckResult checkResult = body.get(0);
				if (!checkResult.isPass()) {
					dto.setMsg(checkResult.getCheckMsg());
					dto.setResult(false);
					return dto;
				}
			}
		} else {
			dto.setMsg(checkParams.getMsg());
			dto.setResult(false);
			return dto;
		}

		// 查询用户名称
		ReturnData<SysUser> SysUser = authService.getUserById(userId);
		if (SysUser != null) {
			dispatchPlan.setUsername(SysUser.getBody().getUsername());
		}
		DispatchPlanBatch dispatchPlanBatch = new DispatchPlanBatch();
		dispatchPlanBatch.setName(dispatchPlan.getBatchName());
		// 通知状态;通知状态1等待2失败3成功
		dispatchPlanBatch.setStatusNotify(BatchNotifyStatusEnum.WAITING.getStatus());
		dispatchPlanBatch.setGmtModified(DateUtil.getCurrent4Time());
		dispatchPlanBatch.setGmtCreate(DateUtil.getCurrent4Time());
		dispatchPlanBatch.setUserId(userId.intValue());
		dispatchPlanBatch.setStatusShow(Constant.BATCH_STATUS_SHOW);
		dispatchPlanBatch.setOrgCode(orgCode);
		dispatchPlanBatchMapper.insert(dispatchPlanBatch);

		// dispatchPlan.setPlanUuid(IdGenUtil.uuid());
		dispatchPlan.setUserId(userId.intValue());
		if (checkPhoneInBlackList) {
			// 当前黑名单
			logger.info("当前号码添加处于黑名单状态:" + dispatchPlan.getPhone());
			dispatchPlan.setStatusPlan(Constant.STATUSPLAN_2);
			dispatchPlan.setStatusSync(Constant.STATUS_SYNC_1);
			dispatchPlan.setResult("X");
		} else {
			dispatchPlan.setStatusPlan(Constant.STATUSPLAN_1);
			dispatchPlan.setStatusSync(Constant.STATUS_SYNC_0);
		}
		dispatchPlan.setGmtModified(DateUtil.getCurrent4Time());
		dispatchPlan.setGmtCreate(DateUtil.getCurrent4Time());
		dispatchPlan.setReplayType(Constant.REPLAY_TYPE_0);
		dispatchPlan.setIsDel(Constant.IS_DEL_0);
		dispatchPlan.setBatchId(dispatchPlanBatch.getId());
		dispatchPlan.setIsTts(Constant.IS_TTS_0);
		dispatchPlan.setFlag(Constant.IS_FLAG_0);
		dispatchPlan.setOrgCode(orgCode);
		dispatchPlan.setOrgId(orgId);	//组织ID
		//路线类型
        Integer lineType = null != dispatchPlan.getLineType()?dispatchPlan.getLineType():PlanLineTypeEnum.SIP.getType();
		dispatchPlan.setLineType(lineType);
        // 加入线路
        List<DispatchBatchLine> lineList = dispatchPlan.getLines();
		for (DispatchBatchLine lines : lineList) {
			lines.setBatchId(dispatchPlanBatch.getId());
			lines.setLineType(dispatchPlan.getLineType());
			lines.setOrgId(orgId);
			lines.setUserId(userId.intValue());
			lines.setLineType(lineType);
			lineService.insert(lines);
		}

		// 查询号码归属地
		String cityName = phoneRegionService.queryPhoneRegion(dispatchPlan.getPhone());
		dispatchPlan.setCityName(cityName);
		boolean bool = DaoHandler.getMapperBoolRes(dispatchPlanMapper.insert(dispatchPlan));
		if(bool){
		    //判断是否是路由网关路线
            if(null != lineType && PlanLineTypeEnum.GATEWAY.getType() == lineType) {
                //设置加入路由网关路线redis及状态
                gateWayLineService.setGatewayLineRedis(lineList);
            }
        }
		return dto;
	}

	public ReturnData<List<CheckResult>> checkParams(DispatchPlan dispatchPlan) {
		CheckParamsReq req = new CheckParamsReq();
		HsParam hsParam = new HsParam();
		hsParam.setParams(dispatchPlan.getParams());
		hsParam.setSeqid(dispatchPlan.getPlanUuidLong() + "");
		hsParam.setTemplateId(dispatchPlan.getRobot());
		List<HsParam> list = new ArrayList<>();
		list.add(hsParam);
		req.setNeedResourceInit(false);
		req.setCheckers(list);
		ReturnData<List<CheckResult>> checkParams = robotRemote.checkParams(req);
		return checkParams;
	}


	@Override
	public boolean successSchedule(String planUuid, String label) {
		logger.info("----------------------------successSchedule-------------------------------------");
		logger.info("写入mq之前的UUID lable:" + planUuid + "--------------" + label);
		// 写入mq中
		MQSuccPhoneDto dto = new MQSuccPhoneDto();
		dto.setPlanuuid(planUuid);
		dto.setLabel(label);
		successPhoneMQService.insertSuccesPhone4BusinessMQ(dto);
		successPhoneMQService.insertCallBack4MQ(dto);
		return true;

	}


	public static String getCurrent4Time() throws ParseException {
		SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyyMMddHHmmss");
		return dateFormatter.format(new Date());
	}

	/**
	 * 记录第三方接口记录详情
	 *
	 * @param url
	 * @param jsonObject
	 * @return
	 */
	private boolean insertThirdInterface(String url, JSONObject jsonObject) {
		ThirdInterfaceRecords record = new ThirdInterfaceRecords();
		try {
			record.setCreateTime(DateUtil.getCurrent4Time());
		} catch (Exception e2) {
			logger.error("error", e2);
		}
		record.setUrl(url);
		record.setParams(jsonObject.toJSONString());
		record.setTimes(Constant.THIRD_INTERFACE_RETRYTIMES);
		logger.info("调用第三方接口异常，记录失败记录");
		int res = thirdInterfaceRecordsMapper.insert(record);
		return res > 0 ? true : false;
	}

	/**
	 * 检查是否完成号码通知了
	 *
	 * @param dispatchPlan
	 * @return
	 */
	private boolean checkLastNum(DispatchPlan dispatchPlan) {
		// DispatchPlanExample dis = new DispatchPlanExample();
		// dis.createCriteria().andBatchNameEqualTo(dispatchPlan.getBatchName())
		// .andStatusPlanEqualTo(Constant.STATUSPLAN_2);
		// int countByExample = dispatchPlanMapper.countByExample(dis);
		// if (countByExample <= 0) {
		// return true;
		// } else {
		// return false;
		// }
		return true;
	}



	@Override
	public List<DispatchPlan> queryAvailableSchedules(Integer userId, int requestCount, int lineId,
			DispatchPlan isSuccess, boolean flag) {
		logger.info("----------------------------queryAvailableSchedules-------------------------------------");
		logger.info("-----------------------------------------------------------------------------------------");

		if (redisUtil.get("robotId") != null) {
			logger.info("当前模板升级中，接口 queryAvailableSchedules 对应模板查不到数据，" + redisUtil.get("robotId"));
			String object = (String) redisUtil.get("robotId");
			return new ArrayList<>();
		}
		return planPutCalldata.get(userId, requestCount, lineId);
	}


	@Override
	public boolean batchUpdatePlans(IdsDto[] dto) {

		DispatchPlan dis = new DispatchPlan();
		List<Long> planUUids = new ArrayList<>();
		List<Integer> orgIds = new ArrayList<>();
		for (IdsDto bean : dto)
		{
			if (StringUtils.isNotEmpty(bean.getPlanuuid()))
			{
				planUUids.add(Long.valueOf(bean.getPlanuuid()));

				dis.setStatusPlan(bean.getStatus().intValue());

				if (!orgIds.contains(bean.getOrgId())) {
					orgIds.add(bean.getOrgId());
				}
			}
		}
		dis.setGmtModified(DateUtil.getCurrent4Time());

		DispatchPlanExample ex = new DispatchPlanExample();
		ex.createCriteria().andOrgIdIn(orgIds).andPlanUuidIn(planUUids);
		dispatchPlanMapper.updateByExampleSelective(dis, ex);

		if (dto.length > 0) {
			IdsDto bean = dto[0];
			if (bean.getStatus().equals(Constant.STATUSPLAN_3) || bean.getStatus().equals(Constant.STATUSPLAN_4)) {
				/*
				 * // 重新分配队列数据
				 * phonePlanQueueService.cleanQueueByUserId(String.valueOf(
				 * userId));
				 */
			}
		}
		return true;
	}

	/**
	 * 停止之后不能暂停 不能恢复
	 */
	@Override
	public MessageDto operationAllPlanByBatchId(Integer batchId, String status, Long userId, Integer orgId) {
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String dateNowStr = sdf.format(d);
		MessageDto result = new MessageDto();
		DispatchPlanExample example = new DispatchPlanExample();
		Criteria createCriteria = example.createCriteria();

		// 根据用户id来查询 恢复1 暂停 3 停止4
		if (status.equals("1"))
		{
			// 一键恢复
			createCriteria.andIsDelEqualTo(Constant.IS_DEL_0).andStatusPlanEqualTo(Constant.STATUSPLAN_3);
		} else if (status.equals("3"))
		{
			// 一键暂停
			createCriteria.andIsDelEqualTo(Constant.IS_DEL_0).andStatusPlanEqualTo(Constant.STATUSPLAN_1);
		} else if (status.equals("4"))
		{
			// 一件停止
			createCriteria.andIsDelEqualTo(Constant.IS_DEL_0).andStatusPlanEqualTo(Constant.STATUSPLAN_1);
		}

		if (batchId != 0)
		{
			DispatchPlanBatch dispatchPlanBatch = dispatchPlanBatchMapper.selectByPrimaryKey(batchId);
			createCriteria.andBatchIdEqualTo(dispatchPlanBatch.getId());
		}

		createCriteria.andUserIdEqualTo(userId.intValue());
		createCriteria.andOrgIdEqualTo(orgId);

		Integer converStatus = Integer.valueOf(status);

		DispatchPlan updateRecord = new DispatchPlan();
		updateRecord.setStatusPlan(converStatus);

		if (status.equals("1"))
		{
			updateRecord.setStatusSync(Constant.STATUS_SYNC_0);
			updateRecord.setCallData(Integer.valueOf(dateNowStr));
		}

		dispatchPlanMapper.updateByExampleSelective(updateRecord, example);

		if (converStatus.equals(Constant.STATUSPLAN_3) || converStatus.equals(Constant.STATUSPLAN_4))
		{
			// 重新分配队列数据
			phonePlanQueueService.cleanQueueByUserId(String.valueOf(userId));
		}

		return result;
	}


	@Override
	public boolean batchDeletePlans(IdsDto[] dto) {

		List<Long> planUUids = new ArrayList<>();
		List<Integer> batchIds = new ArrayList<>();
		List<Integer> orgIds = new ArrayList<>();
		Map<Integer, IdsDto> tmpMap = new HashMap<>();
		for (IdsDto bean : dto)
		{
			if (StringUtils.isNotEmpty(bean.getPlanuuid()))
			{
				planUUids.add(Long.valueOf(bean.getPlanuuid()));
			}

			if (!batchIds.contains(bean.getBatchid()))
			{
				batchIds.add(bean.getBatchid());
				tmpMap.put(bean.getBatchid(), bean);
			}
			if (!orgIds.contains(bean.getOrgId())) {
				orgIds.add(bean.getOrgId());
			}
		}
		logger.info("now do batchDeletePlans:{},{},{},{}:", (null != dto) ? JsonUtils.bean2Json(dto) : null, batchIds, orgIds, tmpMap);
		DispatchPlan dispatchPlan = new DispatchPlan();
		dispatchPlan.setIsDel(Constant.IS_DEL_1);
		DispatchPlanExample ex = new DispatchPlanExample();
		Criteria createCriteria = ex.createCriteria();

		createCriteria.andOrgIdIn(orgIds);
		createCriteria.andPlanUuidIn(planUUids);

		dispatchPlanMapper.updateByExampleSelective(dispatchPlan, ex);

		for (Integer batchId : batchIds)
		{
			// 当前批次都删除了 那么就不显示批次信息了
			DispatchPlanExample batch = new DispatchPlanExample();
			batch.createCriteria().andIsDelEqualTo(Constant.IS_DEL_0).andBatchIdEqualTo(batchId).andOrgIdEqualTo(tmpMap.get(batchId).getOrgId());
			;
			int countByExample = dispatchPlanMapper.countByExample(batch);

			if (countByExample <= 0) {
				DispatchPlanBatchExample bex = new DispatchPlanBatchExample();
				bex.createCriteria().andOrgIdEqualTo(tmpMap.get(batchId).getOrgId()).andIdEqualTo(batchId);
				DispatchPlanBatch batchDto = new DispatchPlanBatch();
				// 不显示
				batchDto.setStatusShow(Constant.BATCH_STATUS_NO);
				dispatchPlanBatchMapper.updateByExampleSelective(batchDto, bex);
			}
		}

		// 重新分配队列数据
		/* phonePlanQueueService.cleanQueueByUserId(String.valueOf(userId)); */

		return true;
	}

	@Override
	public List<DispatchPlanBatch> queryDispatchPlanBatch(String org_code, Long userId, Boolean isSuperAdmin, String orgCode, Integer orgId, Integer authLevel) {
		
		// 批量信息
		DispatchPlanBatchExample example = new DispatchPlanBatchExample();
		com.guiji.dispatch.dao.entity.DispatchPlanBatchExample.Criteria createCriteria = example.createCriteria();
		//权限过滤
		String operUserId = getAuthUtil.getUserIdByAuthLevel(authLevel, userId+"");//获取用户ID
		String operOrgCode = getAuthUtil.getOrgCodeByAuthLevel(authLevel, org_code==null?orgCode:org_code);//获取企业组织编码
		if(AuthLevelEnum.ORG.getLevel() == authLevel){
			createCriteria.andOrgCodeEqualTo(operOrgCode);
		}else if(AuthLevelEnum.ORG_EXT.getLevel() == authLevel){
			createCriteria.andOrgCodeLike(operOrgCode + "%");
		}
		if(AuthLevelEnum.USER.getLevel() == authLevel && !StringUtils.isEmpty(operUserId)){//本人
			createCriteria.andUserIdEqualTo(Integer.valueOf(operUserId));
		}

		createCriteria.andStatusShowEqualTo(Constant.BATCH_STATUS_SHOW);
		return dispatchPlanBatchMapper.selectByExample(example);
	}

	@Override
	public boolean updateReplayDate(Boolean flag) {

		List<Integer> allOrgIds = getAllOrgIds();

		if (flag) {
			Date d = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			String dateNowStr = sdf.format(d);

			DispatchPlan dis = new DispatchPlan();
			dis.setCallData(Integer.valueOf(dateNowStr));
			dis.setStatusPlan(Constant.STATUSPLAN_1);
			dis.setStatusSync(Constant.STATUS_SYNC_0);
			dis.setFlag(Constant.IS_FLAG_0);
			dis.setClean(Constant.IS_CLEAN_0);
			try {
				dis.setGmtModified(DateUtil.getCurrent4Time());
			} catch (Exception e) {
				logger.info("error", e);
			}
			DispatchPlanExample ex = new DispatchPlanExample();
			// 如果刷新日期操作 刷新拨打时间小于当前凌晨的日期的号码
			ex.createCriteria().andCleanEqualTo(Constant.IS_CLEAN_0).andCallDataLessThan(Integer.valueOf(dateNowStr))
					.andStatusPlanEqualTo(Constant.STATUSPLAN_1).andOrgIdIn(allOrgIds);
			dispatchPlanMapper.updateByExampleSelective(dis, ex);
			DispatchPlanExample ex1 = new DispatchPlanExample();
			ex1.createCriteria().andCleanEqualTo(Constant.IS_CLEAN_0).andCallDataLessThan(Integer.valueOf(dateNowStr))
					.andStatusPlanEqualTo(Constant.STATUSPLAN_3).andOrgIdIn(allOrgIds);

            dis.setStatusPlan(Constant.STATUSPLAN_3);
			int result = dispatchPlanMapper.updateByExampleSelective(dis, ex1);
			return result > 0 ? true : false;
		} else {
			Date d = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			String dateNowStr = sdf.format(d);
			DispatchPlan dis = new DispatchPlan();
			dis.setStatusPlan(Constant.STATUSPLAN_4);
			dis.setStatusSync(Constant.STATUS_SYNC_1);
			dis.setClean(Constant.IS_CLEAN_1);
			try {
				dis.setGmtModified(DateUtil.getCurrent4Time());
			} catch (Exception e) {
				logger.info("error", e);
			}
			DispatchPlanExample ex = new DispatchPlanExample();
			ex.createCriteria().andCleanEqualTo(Constant.IS_CLEAN_1).andCallDataLessThan(Integer.valueOf(dateNowStr))
					.andStatusPlanEqualTo(Constant.STATUSPLAN_1).andOrgIdIn(allOrgIds);
			int result = dispatchPlanMapper.updateByExampleSelective(dis, ex);
			DispatchPlanExample ex1 = new DispatchPlanExample();
			ex1.createCriteria().andCleanEqualTo(Constant.IS_CLEAN_1).andCallDataLessThan(Integer.valueOf(dateNowStr))
					.andStatusPlanEqualTo(Constant.STATUSPLAN_3).andOrgIdIn(allOrgIds);
			dispatchPlanMapper.updateByExampleSelective(dis, ex1);
			return result > 0 ? true : false;
		}
	}

	@Override
	public boolean checkBatchId(String name) {
		DispatchPlanBatchExample ex = new DispatchPlanBatchExample();
		ex.createCriteria().andNameEqualTo(name).andStatusShowEqualTo(Constant.BATCH_STATUS_SHOW);
		int count = dispatchPlanBatchMapper.countByExample(ex);
		return count > 0 ? true : false;
	}

	@Override
	public boolean batchUpdateFlag(List<DispatchPlan> list, String flag) {
		List<Long> ids = new ArrayList<>();

		List<Integer> orgIds = new ArrayList<>();
		for (DispatchPlan dispatchPlan : list) {
			if (!ids.contains(dispatchPlan.getPlanUuidLong()))
			{
				ids.add(dispatchPlan.getPlanUuidLong());
			}

			Integer orgId = IdUtils.doParse(Long.valueOf(dispatchPlan.getPlanUuidLong())).getOrgId();
			if(!orgIds.contains(orgId))
			{
				orgIds.add(orgId);
			}
		}
		int result = -1;
		// for (String id : ids) {
		// DispatchPlan dis = new DispatchPlan();
		// DispatchPlanExample ex = new DispatchPlanExample();
		// ex.createCriteria().andPlanUuidEqualTo(id);
		// dis.setFlag(flag);
		// result = dispatchPlanMapper.updateByExampleSelective(dis, ex);
		// }
		// Map<String, Object> = new
		// map.put("flag",flag);
		// map.put("list", ids);

		DispatchPlanExample ex = new DispatchPlanExample();
		ex.createCriteria().andOrgIdIn(orgIds).andPlanUuidIn(ids);

		DispatchPlan dis = new DispatchPlan();
		dis.setFlag(flag);

		result = dispatchPlanMapper.updateByExampleSelective(dis, ex);
		return result > 0 ? true : false;
	}


	@Override
	public int getcall4BatchName(Long userId, String batchName, Integer status) {

		DispatchPlanExample ex = new DispatchPlanExample();
		ex.createCriteria().andBatchNameEqualTo(batchName).andStatusPlanEqualTo(status)
				.andIsDelEqualTo(Constant.IS_DEL_0).andOrgIdIn(getSubOrgIdsByUserId(userId.intValue()));
		int result = dispatchPlanMapper.countByExample(ex);
		return result;
	}

	@Override
	public List<CallPlanDetailRecordVO> queryDispatchPlanByPhoens(Long userId, Integer authLevel, String orgCode, Integer orgId,
																  String phone, String batchName, int pagenum, int pagesize) {

		DispatchPlanExample example = new DispatchPlanExample();
		Criteria createCriteria = example.createCriteria();
		if (phone != null && phone != "") {
			createCriteria.andPhoneEqualTo(phone);
		}

		if (batchName != null && batchName != "") {
			createCriteria.andBatchNameEqualTo(batchName);
		}
		if (pagenum != -1 && pagesize > -1) {
			example.setLimitStart((pagenum - 1) * pagesize);
			example.setLimitEnd(pagesize);
		}
		createCriteria.andOrgIdIn(getSubOrgIdsByUserId(userId.intValue()));
		example.setLimitStart((pagenum - 1) * pagesize);
		example.setLimitEnd(pagesize);

		List<DispatchPlan> selectByExample = dispatchPlanMapper.selectByExample(example);

		List<String> ids = new ArrayList<>();
		for (DispatchPlan dis : selectByExample) {
			ids.add(dis.getPlanUuidLong() + "");
		}
		if (ids.size() > 0) {
			SysOrganization org = getApiService.getOrgByUserId(userId + "");
			CallPlanUuidQuery callPlanParam = new CallPlanUuidQuery();
			callPlanParam.setOrgId(orgId);
			callPlanParam.setAuthLevel(authLevel);
			callPlanParam.setCallIds(ids);
			ReturnData<List<CallPlanDetailRecordVO>> callPlanDetailRecord = callPlanDetail.getCallPlanDetailRecord(callPlanParam);
			return callPlanDetailRecord.getBody();
		} else {
			return new ArrayList<>();
		}

	}

	@Override
	public JSONObject getServiceStatistics(Long userId, Boolean isSuperAdmin, Integer authLevel, String orgCode, Integer orgId) {
		JSONObject jsonObject = new JSONObject();

		//权限过滤
		String operUserId = getAuthUtil.getUserIdByAuthLevel(authLevel, userId+"");//获取用户ID
		//String orgCode = getAuthUtil.getOrgCodeByAuthLevel(authLevel, userId, queryPlanDto.getOperOrgCode());//获取企业组织编码
		List<Integer> orgIds = getAuthUtil.getOrgIdsByAuthLevel(authLevel, orgId);//获取组织ID

		/*List<Integer> allOrgIds = getAllOrgIds();
		List<Integer> subOrgIds = getSubOrgIds(orgId);*/
		// 累计任务号码总数，累计拨打号码总数，最后计划日期，最后拨打日期，累计服务天数
		/*
		int countNums = 0;
		DispatchPlanExample ex = new DispatchPlanExample();
		Criteria createCriteria = ex.createCriteria();
		*//*if (!isSuperAdmin) {

			createCriteria.andOrgIdIn(subOrgIds);
			if (StringUtils.isNotEmpty(orgCode)) {
				createCriteria.andOrgCodeLike(orgCode + "%");
			}
			countNums = dispatchPlanMapper.countByExample(ex);
		} else {
			createCriteria.andOrgIdIn(allOrgIds);
			countNums = dispatchPlanMapper.countByExample(ex);
		}*//*
		if(AuthLevelEnum.USER.getLevel() == authLevel && !StringUtils.isEmpty(operUserId)){//本人
			createCriteria.andUserIdEqualTo(Integer.valueOf(operUserId));
		}
		createCriteria.andOrgIdIn(orgIds);//本组织或本组织及以下组织

		countNums = dispatchPlanMapper.countByExample(ex);
		*/

		/*******未拨打计划数，计划中数量************************************/
		DispatchPlanExample ex = new DispatchPlanExample();
		Criteria createCriteria = ex.createCriteria();
		createCriteria.andStatusPlanEqualTo(Constant.STATUSPLAN_1).andIsDelEqualTo(Constant.IS_DEL_0);//计划中、未删除
		if(AuthLevelEnum.USER.getLevel() == authLevel && !StringUtils.isEmpty(operUserId)){//本人
			createCriteria.andUserIdEqualTo(Integer.valueOf(operUserId));
		}
		createCriteria.andOrgIdIn(orgIds);//本组织或本组织及以下组织

		int noCallNums = dispatchPlanMapper.countByExample(ex);

		DispatchPlanBatch batchParam = new DispatchPlanBatch();
		batchParam.setOrgCode(orgCode);
		batchParam.setUserId(null != operUserId?Integer.valueOf(operUserId):null);
		ResultPage<DispatchPlanBatch> page = new ResultPage<DispatchPlanBatch>(1,1);
		page.setOrderBy("id");
		page.setSort("DESC");
		/**********最后计划日期******************************/
		batchParam.setStatusNotify(BatchNotifyStatusEnum.WAITING.getStatus());
		List<DispatchPlanBatch> doingList = dispatchPlanBatchMapper.queryBatchByParam(batchParam, page , authLevel);
		String lastPlanDate = null;
        if (null != doingList && doingList.size() > 0) {
        	Date batchModifyTime = doingList.get(0).getGmtModified();
				lastPlanDate = null != batchModifyTime?
						DateTimeUtils.getDateString(batchModifyTime, DateTimeUtils.DEFAULT_DATE_FORMAT_PATTERN_SHORT):lastPlanDate;
        }

		/*ex.setOrderByClause("call_data DESC");
		ex.setLimitStart(0);
		ex.setLimitEnd(1);
		List<DispatchPlan> doingList1 = dispatchPlanMapper.selectByExample(ex);
		String lastPlanDate1 = null;
		if (null != doingList1 && doingList1.size() > 0) {
			lastPlanDate1 = doingList1.get(0).getCallData() + "";
		}*/

		/*******最后拨打日期*************/
		batchParam.setStatusNotify(BatchNotifyStatusEnum.SUCCESS.getStatus());
		List<DispatchPlanBatch> finishList = dispatchPlanBatchMapper.queryBatchByParam(batchParam, page , authLevel);
		String lastCalledDate = DateTimeUtils.getCurrentDateString(DateTimeUtils.DEFAULT_DATE_FORMAT_PATTERN_SHORT);
		if (null != finishList && finishList.size() > 0) {
			Date batchModifyTime = finishList.get(0).getGmtModified();
			lastCalledDate = null != batchModifyTime?
					DateTimeUtils.getDateString(batchModifyTime, DateTimeUtils.DEFAULT_DATE_FORMAT_PATTERN_SHORT):lastCalledDate;
		}

		/****服务天数****/
		ReturnData<SysUser> user = auth.getUserById(userId);
		if (user.getBody() != null) {
			SysUser body = user.getBody();
			long dateNow = System.currentTimeMillis();
			long endTimeLong = body.getCreateTime().getTime();
			// long time = body.getVaildTime().getTime();
			// 结束时间-开始时间 = 天数
			long day = (dateNow - endTimeLong) / (24 * 60 * 60 * 1000);
			jsonObject.put("useDay", day);
		}

	//	jsonObject.put("countNums", countNums);		//暂时不用
		jsonObject.put("noCallNums", noCallNums);
		/*jsonObject.put("lastPlanDate", dis.getCallData());
		jsonObject.put("lastCalledDate", dis1.getCallData());*/
		jsonObject.put("lastPlanDate", lastPlanDate);
		jsonObject.put("lastCalledDate", lastCalledDate);
		return jsonObject;

	}

	@Override
	public JSONObject getServiceStatistics(Long userId, String startTime, String endTime, Boolean isSuperAdmin,
			String orgCode, Integer orgId, Integer authLevel) {
		logger.info("userId:{},orgId:{},authLevel:{},orgCode:{}",userId, orgId, authLevel,orgCode);
		//权限过滤
		String operUserId = getAuthUtil.getUserIdByAuthLevel(authLevel, userId+"");//获取用户ID
		//String orgCode = getAuthUtil.getOrgCodeByAuthLevel(authLevel, userId, queryPlanDto.getOperOrgCode());//获取企业组织编码
		List<Integer> orgIds = getAuthUtil.getOrgIdsByAuthLevel(authLevel, orgId);//获取组织ID
		logger.info("userId:{},orgIds:{},authLevel:{}",userId, JsonUtils.bean2Json(orgIds), authLevel);
		DispatchPlanExample ex = new DispatchPlanExample();
		Criteria createCriteria = ex.createCriteria();
		if (startTime != null && startTime != "" && endTime != null && endTime != "") {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				createCriteria.andGmtCreateBetween(new Timestamp(sdf.parse(startTime).getTime()),
						new Timestamp(sdf.parse(endTime).getTime()));
				createCriteria.andIsDelEqualTo(Constant.IS_DEL_0);
			} catch (ParseException e) {
				logger.error("error", e);
			}
		}

		if(AuthLevelEnum.USER.getLevel() == authLevel && !StringUtils.isEmpty(operUserId)){//本人
			createCriteria.andUserIdEqualTo(Integer.valueOf(operUserId));
		}
		createCriteria.andOrgIdIn(orgIds);//本组织或本组织及以下组织

		DispatchPlanExample ex1 = new DispatchPlanExample();
		Criteria createCriteria1 = ex1.createCriteria();
		ex1.createCriteria().andStatusPlanEqualTo(Constant.STATUSPLAN_2);

		if (startTime != null && startTime != "" && endTime != null && endTime != "") {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				createCriteria1.andGmtCreateBetween(new Timestamp(sdf.parse(startTime).getTime()),
						new Timestamp(sdf.parse(endTime).getTime()));
				createCriteria1.andIsDelEqualTo(Constant.IS_DEL_0).andStatusPlanEqualTo(Constant.STATUSPLAN_2);
			} catch (ParseException e) {
				logger.error("error", e);
			}
		}
		if(AuthLevelEnum.USER.getLevel() == authLevel && !StringUtils.isEmpty(operUserId)){//本人
			createCriteria1.andUserIdEqualTo(Integer.valueOf(operUserId));
		}
		createCriteria1.andOrgIdIn(orgIds);//本组织或本组织及以下组织

		int taskCount = dispatchPlanMapper.countByExample(ex);
		int calledCount = dispatchPlanMapper.countByExample(ex1);
		int batchcount = 0;
		if (!isSuperAdmin) {
			DispatchPlanBatchExample batch = new DispatchPlanBatchExample();
			batch.createCriteria().andOrgCodeLike(orgCode + "%");
			batchcount = dispatchPlanBatchMapper.countByExample(batch);
		} else {
			batchcount = dispatchPlanBatchMapper.countByExample(new DispatchPlanBatchExample());
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("taskCount", taskCount);
		jsonObject.put("calledNums", calledCount);
		jsonObject.put("batchcount", batchcount);
		return jsonObject;
	}

	/**
	 * 按日期统计计划数量
	 * @param userId
	 * @param beginDate	: yyyy-MM-dd
	 * @param endDate	: yyyy-MM-dd
	 * @return
	 */
	@Override
	public TotalPlanCountVo totalPlanCountByUserDate(String userId, String beginDate, String endDate, Integer orgId) {
		if(!StringUtils.isEmpty(userId)) {
			SysUser user = ResHandler.getResObj(auth.getUserById(Long.valueOf(userId)));
			String orgCode = null != user ? user.getOrgCode() : null;
			if (!StringUtils.isEmpty(beginDate) && StringUtils.isEmpty(endDate)) {
				endDate = DateTimeUtils.DEFAULT_END_TIME;
			} else if (StringUtils.isEmpty(beginDate) && !StringUtils.isEmpty(endDate)) {
				beginDate = DateTimeUtils.DEFAULT_BEGIN_TIME;
			} else if (!StringUtils.isEmpty(beginDate) && !StringUtils.isEmpty(endDate)) {
				beginDate += " " + DateTimeUtils.DEFAULT_DATE_START_TIME;
				endDate += " " + DateTimeUtils.DEFAULT_DATE_END_TIME;
			}

			DispatchPlan plan = new DispatchPlan();
			plan.setOrgCode(orgCode);
			plan.setOrgId(orgId);
			TotalPlanCountVo total = new TotalPlanCountVo();
			int totalCount = 0, doingCount = 0, finishCount = 0, suspendCount=0, stopCount=0;

			TotalPlanCountVo totalNum = dispatchPlanMapper.totalPlanCount(plan, beginDate, endDate);
			totalCount += totalNum.getTotalCount();
			doingCount += totalNum.getDoingCount();
			finishCount += totalNum.getFinishCount();
			suspendCount += totalNum.getSuspendCount();
			stopCount += totalNum.getStopCount();
			/*
			TotalPlanCountVo total0 = dispatchPlanMapper.totalPlanCount(0, plan, beginDate, endDate);//
			TotalPlanCountVo total1 = dispatchPlanMapper.totalPlanCount(1, plan, beginDate, endDate);//
			TotalPlanCountVo total2 = dispatchPlanMapper.totalPlanCount(2, plan, beginDate, endDate);//
			totalCount = total0.getTotalCount() + total1.getTotalCount() + total2.getTotalCount();
			doingCount = total0.getDoingCount() + total1.getDoingCount() + total2.getDoingCount();
			finishCount = total0.getFinishCount() + total1.getFinishCount() + total2.getFinishCount();
			suspendCount = total0.getSuspendCount() + total1.getSuspendCount() + total2.getSuspendCount();
			stopCount = total0.getStopCount() + total1.getStopCount() + total2.getStopCount();
			*/
			total.setTotalCount(totalCount);
			total.setDoingCount(doingCount);
			total.setFinishCount(finishCount);
			total.setSuspendCount(suspendCount);
			total.setStopCount(stopCount);
			return total;
		}else{
			throw new BaseException(SysDefaultExceptionEnum.NULL_PARAM_EXCEPTION.getErrorCode(),
					SysDefaultExceptionEnum.NULL_PARAM_EXCEPTION.getErrorMsg());
		}
	}

	@Override
	public TotalPlanCountVo totalPlanCountByBatch(Integer batchId, Integer orgId) {
		DispatchPlan plan = new DispatchPlan();
		plan.setBatchId(batchId);
		plan.setOrgId(orgId);
		TotalPlanCountVo total = new TotalPlanCountVo();
		int totalCount = 0, doingCount = 0, finishCount = 0, suspendCount=0, stopCount=0;
		TotalPlanCountVo totalNum = dispatchPlanMapper.totalPlanCount(plan, null, null);
		totalCount += totalNum.getTotalCount();
		doingCount += totalNum.getDoingCount();
		finishCount += totalNum.getFinishCount();
		suspendCount += totalNum.getSuspendCount();
		stopCount += totalNum.getStopCount();
		total.setTotalCount(totalCount);
		total.setDoingCount(doingCount);
		total.setFinishCount(finishCount);
		total.setSuspendCount(suspendCount);
		total.setStopCount(stopCount);
		return total;
	}


	@Override
	public PlanCountVO getPlanCountByUserId(String orgCode, Integer orgId) {

		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String dateNowStr = sdf.format(d);
		DispatchPlanExample ex = new DispatchPlanExample();
		ex.createCriteria().andStatusPlanEqualTo(Constant.STATUSPLAN_1).andIsDelEqualTo(Constant.IS_DEL_0)
				.andOrgCodeLike(orgCode + "%").andOrgIdEqualTo(orgId);
		// 总数
		int countByExample = dispatchPlanMapper.countByExample(ex);

		DispatchPlanExample ex1 = new DispatchPlanExample();
		ex1.createCriteria().andStatusPlanEqualTo(Constant.STATUSPLAN_1).andIsDelEqualTo(Constant.IS_DEL_0)
				.andOrgCodeEqualTo(orgCode).andOrgIdEqualTo(orgId);
		int countByExample2 = dispatchPlanMapper.countByExample(ex1);

		PlanCountVO bean = new PlanCountVO();
		bean.setMainAccountNum(countByExample2);
		bean.setSubAccountNum(countByExample - countByExample2);

		return bean;
	}

	@Override
	public boolean stopPlanByorgCode(String orgCode, Integer orgId, String type) {
		logger.info("orgCode{},type{}", orgCode, type);
		DispatchPlanExample example = new DispatchPlanExample();
		Criteria createCriteria = example.createCriteria();
		// 一件停止
		if (type.equals(Constant.TYPE_ALL)) {
			createCriteria.andIsDelEqualTo(Constant.IS_DEL_0).andStatusPlanNotEqualTo(Constant.STATUSPLAN_2);
			createCriteria.andOrgCodeLike(orgCode + "%");
		} else if (type.equals(Constant.TYPE_NOALL)) {
			createCriteria.andIsDelEqualTo(Constant.IS_DEL_0).andStatusPlanNotEqualTo(Constant.STATUSPLAN_2);
			createCriteria.andOrgCodeEqualTo(orgCode);
		}
		createCriteria.andOrgIdIn(getSubOrgIdsByUserId(orgId));

		DispatchPlan updateRecord = new DispatchPlan();
		updateRecord.setStatusPlan(Constant.STATUSPLAN_4);

		dispatchPlanMapper.updateByExampleSelective(updateRecord, example);
		return true;
	}

	@Override
	public boolean batchInsertDisplanPlan(BatchDispatchPlanList plans, Long userId, String orgCode, Integer orgId) {
		DispatchPlanBatch batch = new DispatchPlanBatch();
		batch.setName(plans.getBatchName());
		batch.setUserId(userId.intValue());
		batch.setStatusShow(Constant.BATCH_STATUS_SHOW);
		batch.setGmtCreate(DateUtil.getCurrent4Time());
		batch.setGmtModified(DateUtil.getCurrent4Time());
		batch.setOrgCode(orgCode);
		dispatchPlanBatchMapper.insert(batch);

		//路线类型
		Integer lineType = null != plans.getLineType()?plans.getLineType():PlanLineTypeEnum.SIP.getType();
		// 加入线路
		List<DispatchBatchLine> lineList = plans.getLines();
		for (DispatchBatchLine lines : lineList) {
			lines.setBatchId(batch.getId());
			lines.setLineType(lineType);
			lines.setOrgId(orgId);
			lines.setUserId(userId.intValue());
			lineService.insert(lines);
		}

		List<String> phones = new ArrayList<>();
		for (int i = 0; i < plans.getMobile().size(); i++) {
			DispatchPlan dispatchPlan = plans.getMobile().get(i);


			com.guiji.dispatch.dao.entity.DispatchPlan bean = new com.guiji.dispatch.dao.entity.DispatchPlan();
			BeanUtils.copyProperties(dispatchPlan, bean);
			bean.setPlanUuid(SnowflakeIdWorker.nextId(orgId));
			bean.setBatchId(batch.getId());
			bean.setUserId(userId.intValue());
			// bean.setLine(Integer.valueOf(plans.getLine()));
			bean.setRobot(plans.getRobot());
			bean.setClean(Integer.valueOf(plans.getClean()));
			bean.setCallHour(plans.getCallHour());
			bean.setCallData(Integer.valueOf(plans.getCallDate()));
			bean.setFlag(Constant.IS_FLAG_0);
			bean.setGmtCreate(DateUtil.getCurrent4Time());
			bean.setGmtModified(DateUtil.getCurrent4Time());
			//号码去重
			if(phones.contains(bean.getPhone())){
				logger.info("当前批量加入号码存在重复号码，已经过滤");
				continue;
			}
			// 查询手机号
			String phone = queryPhone(dispatchPlan.getPlanUuidLong(), orgId);
			if(phone ==null){
				continue;
			}
			bean.setPhone(phone);
			// 查询用户名称
			ReturnData<SysUser> SysUser = authService.getUserById(userId);
			if (SysUser != null) {
				bean.setUsername(SysUser.getBody().getUsername());
			}
			bean.setIsDel(Constant.IS_DEL_0);
			bean.setStatusPlan(Constant.STATUSPLAN_1);
			bean.setStatusSync(Constant.STATUS_SYNC_0);
			bean.setOrgCode(orgCode);
			bean.setOrgId(orgId);//组织ID
			bean.setBatchName(plans.getBatchName());
			bean.setIsTts(Constant.IS_TTS_0);
			bean.setReplayType(Constant.REPLAY_TYPE_0);
			bean.setLineName(plans.getLineName());
			bean.setRobotName(plans.getRobotName());
			bean.setLineType(lineType);			//线路类型		1-SIP， 2-SIM
			// 校验黑名单逻辑
			if (blackService.checkPhoneInBlackList(dispatchPlan.getPhone(), orgCode)) {
				blackService.setBlackPhoneStatus(bean);
				continue;
			}

			dispatchPlan.setLineType(lineType);	//线路类型		1-SIP， 2-SIM
			// 查询号码归属地
			String cityName = phoneRegionService.queryPhoneRegion(bean.getPhone());
			bean.setCityName(cityName);
			boolean bool = DaoHandler.getMapperBoolRes(dispatchPlanMapper.insert(bean));
			if(bool){
				//判断是否是路由网关路线
				if(null != lineType && PlanLineTypeEnum.GATEWAY.getType() == lineType) {
					//设置加入路由网关路线redis及状态
					gateWayLineService.setGatewayLineRedis(lineList);
				}
			}
			phones.add(bean.getPhone());
		}
		return true;
	}

	private String queryPhone(long planUuid, int orgId) {
		DispatchPlanExample ex = new DispatchPlanExample();
		ex.createCriteria().andPlanUuidEqualTo(planUuid).andOrgIdEqualTo(orgId);
		List<DispatchPlan> selectByExample = dispatchPlanMapper.selectByExample(ex);
		if (selectByExample.size() <= 0) {
			logger.info("queryPhone planuuid  error ");
			return null;
		}
		return selectByExample.get(0).getPhone();
	}

	@Override
	public DispatchPlan queryDispatchPlanById(long planUuId) {
		return dispatchPlanMapper.queryDispatchPlanById(planUuId, IdUtils.doParse(planUuId).getOrgId());
	}

	@Override
	public String queryPlanRemarkById(long planUuid) {
		return dispatchPlanMapper.queryPlanRemarkById(planUuid, IdUtils.doParse(planUuid).getOrgId());
	}

  /**
   * 查询计划列表
   *
   * @param queryPlanDto
   * @return
   */
  @Override
  public ResultPage<DispatchPlan> queryPlanList(
		  QueryPlanListDto queryPlanDto, ResultPage<DispatchPlan> page) {

	  if (queryPlanDto.getOperOrgId() <= 0) {
		  return page;
	  }
	  Integer pageNo = page.getPageNo();
	  Integer pageSize = page.getPageSize();
	  DispatchPlanExample example = new DispatchPlanExample();
	  example.setLimitStart((pageNo - 1) * pageSize);
	  example.setLimitEnd(pageSize);
	  example.setOrderByClause("`plan_uuid` DESC");
	  Criteria createCriteria = example.createCriteria();

	  if (!StringUtils.isEmpty(queryPlanDto.getPhone())) {
		  createCriteria.andPhoneEqualTo(queryPlanDto.getPhone());
	  }
	  if (!StringUtils.isEmpty(queryPlanDto.getStartCallData())
			  && !StringUtils.isEmpty(queryPlanDto.getEndCallData())) {
		  createCriteria.andCallDataBetween(
				  Integer.valueOf(queryPlanDto.getStartCallData()),
				  Integer.valueOf(queryPlanDto.getEndCallData()));
	  }

	  if (!StringUtils.isEmpty(queryPlanDto.getUserId())) {
		  createCriteria.andUserIdEqualTo(Integer.valueOf(queryPlanDto.getUserId()));
	  }

	  	//状态集合
		if (null != queryPlanDto.getPlanStatusList() && queryPlanDto.getPlanStatusList().size()>0) {
			createCriteria.andStatusPlanIn(queryPlanDto.getPlanStatusList());
		}
	  if (!StringUtils.isEmpty(queryPlanDto.getStartTime())
			  && !StringUtils.isEmpty(queryPlanDto.getEndTime())) {
		  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		  try {
			  createCriteria.andGmtCreateBetween(
					  new Timestamp(sdf.parse(queryPlanDto.getStartTime()).getTime()),
					  new Timestamp(sdf.parse(queryPlanDto.getEndTime()).getTime()));
		  } catch (ParseException e) {
			  logger.error("error", e);
		  }
	  }

	  if (queryPlanDto.getBatchId() != null && queryPlanDto.getBatchId() != 0) {//批次号
		  createCriteria.andBatchIdEqualTo(queryPlanDto.getBatchId());
	  }

	  if (null != queryPlanDto.getResultList() && queryPlanDto.getResultList().size() > 0) {//意向标签
		  createCriteria.andResultIn(queryPlanDto.getResultList());
	  }

	  if(!StringUtils.isEmpty(queryPlanDto.getCustName())){//客户名称模糊查询条件
		  createCriteria.andCustNameLike("%" + queryPlanDto.getCustName() + "%");
	  }

	  if(!StringUtils.isEmpty(queryPlanDto.getCustCompany())){//客户所属单位模糊查询条件
	  	createCriteria.andCustCompanyLike("%" + queryPlanDto.getCustCompany() + "%");
	  }

	  /*if (!queryPlanDto.isSuperAdmin()) {
		  createCriteria.andOrgCodeLike(queryPlanDto.getOperOrgCode() + "%");
	  }*/

	  //权限过滤
	  Integer authLevel = queryPlanDto.getAuthLevel();//操作用户权限等级
	  String userId = getAuthUtil.getUserIdByAuthLevel(authLevel, queryPlanDto.getOperUserId());//获取用户ID
	  //String orgCode = getAuthUtil.getOrgCodeByAuthLevel(authLevel, userId, queryPlanDto.getOperOrgCode());//获取企业组织编码
	  List<Integer> orgIds = (null != queryPlanDto.getOrgIdList() && queryPlanDto.getOrgIdList().size()>0)?
			  queryPlanDto.getOrgIdList()
			  :getAuthUtil.getOrgIdsByAuthLevel(authLevel, queryPlanDto.getOperOrgId());//获取组织ID
	  if(null != orgIds && orgIds.size()>0) {
		  createCriteria.andOrgIdIn(orgIds);
	  }
	  if(AuthLevelEnum.USER.getLevel() == authLevel && !StringUtils.isEmpty(userId)){//本人
		  createCriteria.andUserIdEqualTo(Integer.valueOf(userId));
	  }

	  createCriteria.andIsDelEqualTo(Constant.IS_DEL_0);

	  List<DispatchPlan> selectByExample = dispatchPlanMapper.selectByExample(example);

	  List<DispatchPlan> resList = new ArrayList<DispatchPlan>();
	  if (null != selectByExample && selectByExample.size() > 0) {
		  LinkedHashSet<Long> planUuidSet = new LinkedHashSet<Long>();

		  Integer isDesensitization = queryPlanDto.getIsDesensitization();
		  Map<Integer, List<DispatchBatchLine>> tmpMap = new HashMap<>();
		  Map<Integer, SysUser> tmpUserMap = new HashMap<>();
		  // 转换userName
		  for (DispatchPlan dis : selectByExample) {
			  if (planUuidSet.contains(dis.getPlanUuidLong())) {
				  continue;
			  }

			  planUuidSet.add(dis.getPlanUuidLong());

			  // 转换userName
			  if (!tmpUserMap.containsKey(dis.getUserId())) {
				  ReturnData<SysUser> user = auth.getUserById(Long.valueOf(dis.getUserId()));
				  if (user.getBody() != null) {
					  tmpUserMap.put(dis.getUserId(), user.getBody());
				  }
			  }
			  if (tmpUserMap.containsKey(dis.getUserId())) {
				  dis.setUserName(tmpUserMap.get(dis.getUserId()).getUsername());
			  }

			  /*List<DispatchBatchLine> linesVO = null;
			  if (!tmpMap.containsKey(dis.getBatchId())) {
				  linesVO = lineService.queryListByBatchId(dis.getBatchId());
				  if (linesVO != null) {
					  tmpMap.put(dis.getBatchId(), linesVO);
				  }
			  }

			  if (tmpMap.containsKey(dis.getBatchId())) {
				  dis.setLines(tmpMap.get(dis.getBatchId()));
			  }*/

			  // isDesensitization
			  if (isDesensitization.equals(0)) {
				  if (dis.getPhone().length() <= 7) {

					  continue;
				  }
				  String phoneNumber =
						  dis.getPhone().substring(0, 3)
								  + "****"
								  + dis.getPhone().substring(7, dis.getPhone().length());
				  dis.setPhone(phoneNumber);
			  }

			  resList.add(dis);
		  }
	  }

	  int count = dispatchPlanMapper.countByExample(example);
	  page.setList(resList);
	  page.setTotalItemAndPageNumber(count);
	  return page;
  }

	/**
	 * 查询下载计划数据
	 * @param queryPlanDto
	 * @return
	 */
	@Override
	public List<DownLoadPlanVo> queryDownloadPlanList(QueryDownloadPlanListDto queryPlanDto) {
		Integer startIdx = queryPlanDto.getStartIdx();
		Integer pageSize = queryPlanDto.getPageSize();
		DispatchPlanExample example = new DispatchPlanExample();
		example.setLimitStart(startIdx);
		example.setLimitEnd(pageSize);
		example.setOrderByClause("`plan_uuid` DESC");
		Criteria createCriteria = example.createCriteria();
		if (!StringUtils.isEmpty(queryPlanDto.getPhone())) {
			createCriteria.andPhoneEqualTo(queryPlanDto.getPhone());
		}
		if (!StringUtils.isEmpty(queryPlanDto.getStartCallData()) && !StringUtils.isEmpty(queryPlanDto.getEndCallData())) {
			createCriteria.andCallDataBetween(Integer.valueOf(queryPlanDto.getStartCallData()), Integer.valueOf(queryPlanDto.getEndCallData()));
		}

		/*if (!StringUtils.isEmpty(queryPlanDto.getUserId())) {
			createCriteria.andUserIdEqualTo(Integer.valueOf(queryPlanDto.getUserId()));
		}*/

		//状态集合
		if (null != queryPlanDto.getPlanStatusList() && queryPlanDto.getPlanStatusList().size()>0) {
			createCriteria.andStatusPlanIn(queryPlanDto.getPlanStatusList());
		}
		if (!StringUtils.isEmpty(queryPlanDto.getStartTime()) && !StringUtils.isEmpty(queryPlanDto.getEndTime())) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				createCriteria.andGmtCreateBetween(new Timestamp(sdf.parse(queryPlanDto.getStartTime()).getTime()), new Timestamp(sdf.parse(queryPlanDto.getEndTime()).getTime()));
			} catch (ParseException e) {
				logger.error("error", e);
			}
		}

		if (queryPlanDto.getBatchId() != null && queryPlanDto.getBatchId() != 0) {
			createCriteria.andBatchIdEqualTo(queryPlanDto.getBatchId());
		}

		/*if (!StringUtils.isEmpty(queryPlanDto.getReplayType())) {
			List<Integer> ids = new ArrayList<>();
			if (queryPlanDto.getReplayType().contains(",")) {
				String[] split = queryPlanDto.getReplayType().split(",");
				for (String sp : split) {
					ids.add(Integer.valueOf(sp));
				}
				createCriteria.andReplayTypeIn(ids);
			} else {
				createCriteria.andReplayTypeEqualTo(Integer.valueOf(queryPlanDto.getReplayType()));
			}
		}*/

		if(null != queryPlanDto.getResultList()
				&& queryPlanDto.getResultList().size()>0){
			createCriteria.andResultIn(queryPlanDto.getResultList());
		}

		List<Integer> allOrgIds = getAllOrgIds();
		List<Integer> subOrgIds = getSubOrgIds(queryPlanDto.getOperOrgId());

		if (!queryPlanDto.isSuperAdmin()) {
			createCriteria.andOrgIdIn(subOrgIds);
		}
		else
		{
			createCriteria.andOrgIdIn(allOrgIds);
		}
		createCriteria.andIsDelEqualTo(Constant.IS_DEL_0);

		//权限过滤
		Integer authLevel = queryPlanDto.getAuthLevel();//操作用户权限等级
		String userId = getAuthUtil.getUserIdByAuthLevel(authLevel, queryPlanDto.getOperUserId());//获取用户ID
		//String orgCode = getAuthUtil.getOrgCodeByAuthLevel(authLevel, userId, queryPlanDto.getOperOrgCode());//获取企业组织编码
		List<Integer> orgIds = (null != queryPlanDto.getOrgIdList() && queryPlanDto.getOrgIdList().size()>0)?
				queryPlanDto.getOrgIdList()
				:getAuthUtil.getOrgIdsByAuthLevel(authLevel, queryPlanDto.getOperOrgId());//获取组织ID
		createCriteria.andOrgIdIn(orgIds);
		if(AuthLevelEnum.USER.getLevel() == authLevel && !StringUtils.isEmpty(userId)){
			createCriteria.andUserIdEqualTo(Integer.valueOf(userId));
		}

		List<DownLoadPlanVo> list = dispatchPlanMapper.queryDownloadPlanList(example);
		return list;
	}

	@Override
	public List<DispatchBatchLine> queryLineByPlan(Integer batchId) {
		return lineService.queryListByBatchId(batchId);
	}

	@Override
	public ResultPage<DispatchPlanVo> queryPlanListByPage(QueryPlanListDto queryPlanDto, ResultPage<DispatchPlanVo> page) {
		Integer pageNo = page.getPageNo();
		Integer pageSize = page.getPageSize();
		DispatchPlanExample example = new DispatchPlanExample();
		example.setLimitStart((pageNo - 1) * pageSize);
		example.setLimitEnd(pageSize);
		example.setOrderByClause("`plan_uuid` DESC");
		Criteria createCriteria = example.createCriteria();
		if (!StringUtils.isEmpty(queryPlanDto.getPhone())) {
			createCriteria.andPhoneEqualTo(queryPlanDto.getPhone());
		}
		if (!StringUtils.isEmpty(queryPlanDto.getStartCallData()) && !StringUtils.isEmpty(queryPlanDto.getEndCallData())) {
			createCriteria.andCallDataBetween(Integer.valueOf(queryPlanDto.getStartCallData()), Integer.valueOf(queryPlanDto.getEndCallData()));
		}

		if (!StringUtils.isEmpty(queryPlanDto.getUserId())) {
			createCriteria.andUserIdEqualTo(Integer.valueOf(queryPlanDto.getUserId()));
		}

		//状态集合
		if (null != queryPlanDto.getPlanStatusList() && queryPlanDto.getPlanStatusList().size()>0) {
			createCriteria.andStatusPlanIn(queryPlanDto.getPlanStatusList());
		}
		if (!StringUtils.isEmpty(queryPlanDto.getStartTime()) && !StringUtils.isEmpty(queryPlanDto.getEndTime())) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				createCriteria.andGmtCreateBetween(new Timestamp(sdf.parse(queryPlanDto.getStartTime()).getTime()), new Timestamp(sdf.parse(queryPlanDto.getEndTime()).getTime()));
			} catch (ParseException e) {
				logger.error("error", e);
			}
		}

		if (queryPlanDto.getBatchId() != null && queryPlanDto.getBatchId() != 0) {
			createCriteria.andBatchIdEqualTo(queryPlanDto.getBatchId());
		}

		/*if (!StringUtils.isEmpty(queryPlanDto.getReplayType())) {
			List<Integer> ids = new ArrayList<>();
			if (queryPlanDto.getReplayType().contains(",")) {
				String[] split = queryPlanDto.getReplayType().split(",");
				for (String sp : split) {
					ids.add(Integer.valueOf(sp));
				}
				createCriteria.andReplayTypeIn(ids);
			} else {
				createCriteria.andReplayTypeEqualTo(Integer.valueOf(queryPlanDto.getReplayType()));
			}
		}*/

		if (null != queryPlanDto.getResultList()
				&& queryPlanDto.getResultList().size() > 0) {
			createCriteria.andResultIn(queryPlanDto.getResultList());
		}

		//权限过滤
		Integer authLevel = queryPlanDto.getAuthLevel();//操作用户权限等级
		String userId = getAuthUtil.getUserIdByAuthLevel(authLevel, queryPlanDto.getOperUserId());//获取用户ID
		//String orgCode = getAuthUtil.getOrgCodeByAuthLevel(authLevel, userId, queryPlanDto.getOperOrgCode());//获取企业组织编码
		List<Integer> orgIds = (null != queryPlanDto.getOrgIdList() && queryPlanDto.getOrgIdList().size()>0)?
				queryPlanDto.getOrgIdList()
				:getAuthUtil.getOrgIdsByAuthLevel(authLevel, queryPlanDto.getOperOrgId());//获取组织ID
		createCriteria.andOrgIdIn(orgIds);
		if(AuthLevelEnum.USER.getLevel() == authLevel && !StringUtils.isEmpty(userId)){//本人
			createCriteria.andUserIdEqualTo(Integer.valueOf(userId));
		}

		createCriteria.andIsDelEqualTo(Constant.IS_DEL_0);

		List<DispatchPlanVo> selectByExample = planExtMapper.queryPlanListByPage(example, queryPlanDto.getIsDesensitization());
		List<DispatchPlanVo> resList = new ArrayList<DispatchPlanVo>();
		if (null != selectByExample && selectByExample.size() > 0) {
			LinkedHashSet<String> planUuidSet = new LinkedHashSet<String>();
			for (DispatchPlanVo dis : selectByExample) {
				planUuidSet.add(dis.getPlanUuid());
			}

			//	Integer isDesensitization = queryPlanDto.getIsDesensitization();
			loopA:
			for (String planUuid : planUuidSet) {
				// 转换userName
				loopB:
				for (DispatchPlanVo dis : selectByExample) {
					if (planUuid.equals(dis.getPlanUuid())) {
						dis.setUserName(this.getUserName(dis.getUserId()));
						/*ReturnData<SysUser> user = auth.getUserById(Long.valueOf(dis.getUserId()));
						if (user.getBody() != null) {
							dis.setUserName(user.getBody().getUsername());
						}*/

						/*List<DispatchLines> queryLinesByPlanUUID = lineService.queryLinesByPlanUUID(planUuid);
						dis.setLines(queryLinesByPlanUUID);*/

						/*// isDesensitization
						if (isDesensitization.equals(0)) {
							if (dis.getPhone().length() <= 7) {
								continue;
							}
							String phoneNumber = dis.getPhone().substring(0, 3) + "****" + dis.getPhone().substring(7, dis.getPhone().length());
							dis.setPhone(phoneNumber);
						}*/

						resList.add(dis);
						continue loopA;
					}
				}
			}
		}


		int count = dispatchPlanMapper.countByExample(example);
		page.setList(resList);
		page.setTotalItemAndPageNumber(count);
		return page;
	}

	/**
	 * 获取用户名称
	 *
	 * @param userId
	 * @return
	 */
	private String getUserName(Integer userId) {
		String userName = "";
		try {
			Object userNameObj = redisUtil.get(RedisConstant.RedisConstantKey.QUERY_PLANLIST_USERNAME_TMP + userId);
			if (null != userNameObj) {
				userName = (String) userNameObj;
			} else {
				SysUser user = ResHandler.getResObj(auth.getUserById(Long.valueOf(userId)));
				if (null != user) {
					userName = user.getUsername();
					redisUtil.set(RedisConstant.RedisConstantKey.QUERY_PLANLIST_USERNAME_TMP + userId,
							userName,
							RedisConstant.RedisConstantKey.QUERY_PLANLIST_USERNAME_TMP_TIMELONG);
				}
			}
		} catch (Exception e) {
			logger.error("获取用户名称异常", e);
		}
		return userName;
	}

	private List<Integer> getAllOrgIds()
	{
		ReturnData<List<Integer>>  resp = orgService.getAllOrgId();
		List<Integer> result = null;
		if (resp != null && resp.getBody() != null) {
			result = resp.getBody();
		}

		if(result == null)
		{
			result = new ArrayList<>();
		}

		return  result;
	}

	private List<Integer> getSubOrgIds(Integer orgId)
	{
		ReturnData<List<Integer>>  resp = orgService.getSubOrgIdByOrgId(orgId);
		List<Integer> result = null;
		if (resp != null && resp.getBody() != null) {
			result = resp.getBody();
		}

		if(result == null)
		{
			result = new ArrayList<>();
		}

		result.add(orgId);

		return result;
	}

	private List<Integer> getSubOrgIdsByUserId(Integer userId)
	{
		SysOrganization userOrg = ResHandler.getResObj(auth.getOrgByUserId(Long.valueOf(userId)));

		return getSubOrgIds(userOrg.getId().intValue());
	}

	@Override
	public int queryPlanCountByBatch(Integer batchId) {
		return planExtMapper.queryPlanCountByBatch(batchId);
	}

	@Override
	public boolean updPlanStatusSyncById(Long planUuid, Integer statusSync) {
		List<Long> list = new ArrayList<>();
		list.add(planUuid);
		Integer orgId = IdUtils.doParse(Long.valueOf(planUuid)).getOrgId();
		boolean bool = DaoHandler.getMapperBoolRes(dispatchPlanMapper.updPlanByStatusSync(list, SyncStatusEnum.NO_SYNC.getStatus(), orgId));
		return bool;
	}


	@Async
	@Override
	public void addPlanAsync(AddPlanAsyncEntity entity){
		DispatchPlanForApiRo ro = entity.getRo();
		Integer lineType = entity.getLineType();
		DispatchPlanBatch dispatchPlanBatch = entity.getBatch();
		CustTemplateVo custTemplateVo = entity.getCustTemplateVo();
		SysUser sysUser = entity.getSysUser();
		SysOrganization orgInfo = entity.getOrgInfo();
		Integer orgId = orgInfo.getId();

		for(PhoneRo phoneRo : ro.getPhoneRoList()) {

			DispatchPlan dispatchPlan = new DispatchPlan();

			dispatchPlan.setPhone(phoneRo.getPhoneNo());
			dispatchPlan.setParams(phoneRo.getParams());
			dispatchPlan.setAttach(phoneRo.getAttach());
			dispatchPlan.setCallAgent(ro.getCallAgent());
			dispatchPlan.setCustName(phoneRo.getCustName());
			dispatchPlan.setCustCompany(phoneRo.getCustCompany());
			dispatchPlan.setLineType(lineType);
			dispatchPlan.setCallData(ro.getCallData());
			dispatchPlan.setCallHour(ro.getCallHour());
			dispatchPlan.setBatchId(dispatchPlanBatch.getId());
			dispatchPlan.setRobot(custTemplateVo.getTemplateId());
			dispatchPlan.setRobotName(custTemplateVo.getTemplateName());
			dispatchPlan.setOrgCode(sysUser.getOrgCode());
			dispatchPlan.setUsername(sysUser.getUsername());
			dispatchPlan.setOrgId(orgInfo.getId());
			dispatchPlan.setOrgCode(orgInfo.getCode());
			dispatchPlan.setClean(ro.getClean());
			dispatchPlan.setUserId(ro.getUserId());
			dispatchPlan.setCallbackUrl(ro.getSingleCallBackUrl());
			dispatchPlan.setBatchName(ro.getBatchName());
			addPlan(dispatchPlan);
		}
	}


	/**
	 * 添加计划
	 * @param dispatchPlan
	 */
	@Override
	public void addPlan(DispatchPlan dispatchPlan) {

		Integer orgId = dispatchPlan.getOrgId();

		dispatchPlan.setPlanUuid(SnowflakeIdWorker.nextId(orgId));
		dispatchPlan.setGmtModified(DateUtil.getCurrent4Time());
		dispatchPlan.setGmtCreate(DateUtil.getCurrent4Time());
		dispatchPlan.setStatusPlan(Constant.STATUSPLAN_1);
		dispatchPlan.setStatusSync(Constant.STATUS_SYNC_0);
		dispatchPlan.setIsDel(Constant.IS_DEL_0);
		dispatchPlan.setReplayType(Constant.REPLAY_TYPE_0);
		dispatchPlan.setIsTts(Constant.IS_TTS_0);
		dispatchPlan.setFlag(Constant.IS_FLAG_0);

		// 放入队列
		batchImportQueueHandler.add(dispatchPlan);
	}

	@Override
	public void saveError(DispatchPlan dispatchPlan) {
		dispatchPlan.setStatusPlan(Constant.STATUSPLAN_2);
		dispatchPlan.setStatusSync(Constant.STATUS_SYNC_1);
		dispatchPlan.setResult("X");
		dispatchPlanMapper.insert(dispatchPlan);
	}

	@Override
	public Integer getRightCount(DispatchPlan plan) {
		DispatchPlanExample example = new DispatchPlanExample();

		example.createCriteria().andBatchIdEqualTo(plan.getBatchId())
				.andOrgIdEqualTo(plan.getOrgId())
				.andStatusPlanEqualTo(Constant.STATUSPLAN_1)
				.andStatusSyncEqualTo(Constant.STATUS_SYNC_0)
				.andIsDelEqualTo(Constant.IS_DEL_0);

		return dispatchPlanMapper.countByExample(example);
	}

	@Autowired
	FileErrorRecordsMapper fileErrorRecordsMapper;

	@Override
	public List<DispatchPlan> getFailCount(DispatchPlan plan) {
		FileErrorRecordsExample example = new FileErrorRecordsExample();

		example.createCriteria().andBatchIdEqualTo(plan.getBatchId());

		List<FileErrorRecords> fileErrorRecords = fileErrorRecordsMapper.selectByExample(example);

		List<DispatchPlan> dispatchPlans = new ArrayList<>();

		fileErrorRecords.forEach(obj -> {
			DispatchPlan dispatchPlan = new DispatchPlan();

			dispatchPlan.setCustName(obj.getCustName());
			dispatchPlan.setCustCompany(obj.getCustCompany());
			dispatchPlan.setPhone(obj.getPhone());
			dispatchPlan.setAttach(obj.getAttach());
			dispatchPlan.setParams(obj.getParams());

			dispatchPlans.add(dispatchPlan);
		});

		return dispatchPlans;
	}

	@Autowired
	BatchImportQueueHandler batchImportQueueHandler;


	/**
	 *  查询通话记录
	 * @param userId
	 * @param batchId
	 * @param pagenum
	 * @param pagesize
	 * @return
	 */
	@Override
	public List<CallPlanDetailRecordVO> queryPlanByUserAndBatchId(Long userId, Integer batchId, int pagenum, int pagesize) {
		DispatchPlanExample example = new DispatchPlanExample();
		Criteria createCriteria = example.createCriteria();

		Integer orgId = getApiService.getOrgIdByUser(userId.toString());

		createCriteria.andBatchIdEqualTo(batchId);
		createCriteria.andOrgIdEqualTo(orgId);
		createCriteria.andStatusPlanEqualTo(Constant.STATUSPLAN_2);
		example.setLimitStart((pagenum - 1) * pagesize);
		example.setLimitEnd(pagesize);

		List<DispatchPlan> planUuidList = dispatchPlanMapper.getPlanUuidList(example);

		List<String> ids = new ArrayList<>();
		for (DispatchPlan dis : planUuidList) {
			ids.add(dis.getPlanUuid()+"");
		}

		CallPlanUuidQuery query = new CallPlanUuidQuery();

		query.setOrgId(orgId);
		query.setAuthLevel(AuthLevelEnum.USER.getLevel());
		query.setCallIds(ids);

		if (ids.size() > 0) {
			ReturnData<List<CallPlanDetailRecordVO>> callPlanDetailRecord = callPlanDetail.getCallPlanDetailRecord(query);
			return callPlanDetailRecord.getBody();
		} else {
			throw new GuiyuException("查询无数据");
		}

	}

	@Override
	public DispatchPlanBatchAddVo getPlanResult(DispatchPlan vo) {
		DispatchPlanBatchAddVo addVo = new DispatchPlanBatchAddVo();

		DispatchPlanBatch batch = dispatchPlanBatchMapper.selectByPrimaryKey(vo.getBatchId());

		Integer orgId = getApiService.getOrgIdByUser(batch.getUserId().toString());

		List<DispatchPlan> dispatchPlans = getFailCount(vo);

		Integer failCount = 0;

		List<PhoneVo> phoneVos = new ArrayList<>();

		if(!CollectionUtils.isEmpty(dispatchPlans)) {
			failCount = dispatchPlans.size();

			dispatchPlans.forEach(obj -> {

				PhoneVo phoneVo = new PhoneVo();

				phoneVo.setAttach(obj.getAttach());
				phoneVo.setPhoneNo(obj.getPhone());
				phoneVo.setCustCompany(obj.getCustCompany());
				phoneVo.setCustName(obj.getCustName());
				phoneVo.setResult(obj.getResult());
				phoneVo.setParams(obj.getParams());

				phoneVos.add(phoneVo);

			});

		}

		addVo.setAcceptCount(batch.getTotalNum());
		addVo.setFailCount(failCount);
		addVo.setFailList(phoneVos);
		addVo.setSuccessCount(addVo.getAcceptCount() - failCount);
		addVo.setBatchName(batch.getName());

		return addVo;
	}

}
