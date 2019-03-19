package com.guiji.dispatch.impl;

import com.alibaba.fastjson.JSONObject;
import com.guiji.auth.api.IAuth;
import com.guiji.ccmanager.api.ICallPlanDetail;
import com.guiji.ccmanager.entity.LineConcurrent;
import com.guiji.ccmanager.vo.CallPlanDetailRecordVO;
import com.guiji.common.model.Page;
import com.guiji.component.result.Result.ReturnData;
import com.guiji.dispatch.bean.BatchDispatchPlanList;
import com.guiji.dispatch.bean.IdsDto;
import com.guiji.dispatch.bean.MQSuccPhoneDto;
import com.guiji.dispatch.bean.MessageDto;
import com.guiji.dispatch.dao.*;
import com.guiji.dispatch.dao.entity.*;
import com.guiji.dispatch.dao.entity.DispatchPlanExample.Criteria;
import com.guiji.dispatch.dto.QueryDownloadPlanListDto;
import com.guiji.dispatch.dto.QueryPlanListDto;
import com.guiji.dispatch.enums.PlanLineTypeEnum;
import com.guiji.dispatch.enums.PlanTableNumEnum;
import com.guiji.dispatch.enums.SysDefaultExceptionEnum;
import com.guiji.dispatch.exception.BaseException;
import com.guiji.dispatch.line.ILinesService;
import com.guiji.dispatch.model.PlanCountVO;
import com.guiji.dispatch.pushcallcenter.SuccessPhoneMQService;
import com.guiji.dispatch.service.*;
import com.guiji.dispatch.sms.IMessageService;
import com.guiji.dispatch.sys.ResultPage;
import com.guiji.dispatch.util.Constant;
import com.guiji.dispatch.util.DaoHandler;
import com.guiji.dispatch.util.DateTimeUtils;
import com.guiji.dispatch.util.ResHandler;
import com.guiji.dispatch.vo.DownLoadPlanVo;
import com.guiji.dispatch.vo.TotalPlanCountVo;
import com.guiji.robot.api.IRobotRemote;
import com.guiji.robot.model.CheckParamsReq;
import com.guiji.robot.model.CheckResult;
import com.guiji.robot.model.HsParam;
import com.guiji.user.dao.entity.SysUser;
import com.guiji.utils.DateUtil;
import com.guiji.utils.IdGenUtil;
import com.guiji.utils.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	private UserSmsConfigMapper userSmsConfigMapper;

	@Autowired
	private SmsTunnelMapper smsTunnerMapper;
	@Autowired
	private IMessageService msgService;

	@Autowired
	private IDispatchPlanPutCalldata planPutCalldata;
	@Autowired
	private SuccessPhoneMQService successPhoneMQService;

	@Autowired
	private IBlackListService blackService;
	@Autowired
	IPhonePlanQueueService phonePlanQueueService;

	@Autowired
	private ILinesService lineService;
	@Autowired
	private IPhoneRegionService phoneRegionService;

	@Autowired
    private GateWayLineService gateWayLineService;

	/**
	 * 单个任务导入
	 * @param dispatchPlan
	 * @param userId
	 * @param orgCode
	 * @return
	 * @throws Exception
	 */
	@Override
	public MessageDto addSchedule(DispatchPlan dispatchPlan, Long userId, String orgCode) throws Exception {
		boolean checkPhoneInBlackList = blackService.checkPhoneInBlackList(dispatchPlan.getPhone(), orgCode);
		MessageDto dto = new MessageDto();
		dispatchPlan.setPlanUuid(IdGenUtil.uuid());
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
		dispatchPlanBatch.setStatusNotify(Constant.STATUSNOTIFY_0);
		dispatchPlanBatch.setGmtModified(DateUtil.getCurrent4Time());
		dispatchPlanBatch.setGmtCreate(DateUtil.getCurrent4Time());
		dispatchPlanBatch.setStatusShow(dispatchPlan.getStatusShow());
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
		//路线类型
        Integer lineType = null != dispatchPlan.getLineType()?dispatchPlan.getLineType():PlanLineTypeEnum.SIP.getType();
		dispatchPlan.setLineType(lineType);
        // 加入线路
        List<DispatchLines> lineList = dispatchPlan.getLines();
		for (DispatchLines lines : lineList) {
			lines.setCreateTime(DateUtil.getCurrent4Time());
			lines.setPlanuuid(dispatchPlan.getPlanUuid());
			lines.setLineType(dispatchPlan.getLineType());
			lineService.insertLines(lines);
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
		hsParam.setSeqid(dispatchPlan.getPlanUuid());
		hsParam.setTemplateId(dispatchPlan.getRobot());
		List<HsParam> list = new ArrayList<>();
		list.add(hsParam);
		req.setNeedResourceInit(false);
		req.setCheckers(list);
		ReturnData<List<CheckResult>> checkParams = robotRemote.checkParams(req);
		return checkParams;
	}

	@Override
	public Page<DispatchPlan> querySchedules(Integer userId, int pagenum, int pagesize) {
		Page<DispatchPlan> page = new Page<>();
		page.setPageNo(pagenum);
		page.setPageSize((pagesize));
		DispatchPlanExample ex = new DispatchPlanExample();
		ex.setLimitStart((pagenum - 1) * pagesize);
		ex.setLimitEnd(pagesize);
		if (userId != 0) {
			ex.createCriteria().andUserIdEqualTo(userId);
		}
		ex.createCriteria().andIsDelEqualTo(Constant.IS_DEL_0);
		List<DispatchPlan> selectByExample = dispatchPlanMapper.selectByExample(ex);
		int count = dispatchPlanMapper.countByExample(ex);
		page.setRecords(selectByExample);
		page.setTotal(count);
		return page;
	}

	@Override
	public boolean pauseSchedule(String planUuid) {
		DispatchPlan dispatchPlan = new DispatchPlan();
		dispatchPlan.setPlanUuid(planUuid);
		dispatchPlan.setStatusPlan(Constant.STATUSPLAN_3);
		int result = dispatchPlanMapper.updateByExampleSelective(dispatchPlan, new DispatchPlanExample());
		return result > 0 ? true : false;
	}

	@Override
	public boolean resumeSchedule(String planUuid) {
		DispatchPlan dispatchPlan = new DispatchPlan();
		dispatchPlan.setPlanUuid(planUuid);
		dispatchPlan.setStatusPlan(Constant.STATUSPLAN_1);
		int result = dispatchPlanMapper.updateByExampleSelective(dispatchPlan, new DispatchPlanExample());
		return result > 0 ? true : false;
	}

	@Override
	public boolean cancelSchedule(String planUuid) {
		DispatchPlan dispatchPlan = new DispatchPlan();
		dispatchPlan.setPlanUuid(planUuid);
		dispatchPlan.setStatusPlan(Constant.STATUSPLAN_4);
		int result = dispatchPlanMapper.updateByExampleSelective(dispatchPlan, new DispatchPlanExample());
		return result > 0 ? true : false;
	}

	@Override
	public boolean OperationAllDispatchByUserId(Integer userId, Integer status) {
		DispatchPlan dispatchPlan = new DispatchPlan();
		dispatchPlan.setUserId(userId);
		dispatchPlan.setStatusPlan(status);
		logger.info("OperationAllDispatchByUserId updateByExample");
		int result = dispatchPlanMapper.updateByExample(dispatchPlan, new DispatchPlanExample());
		logger.info("OperationAllDispatchByUserId end");
		return result > 0 ? true : false;
	}


	public boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

	public boolean isNull(Cell cell) {
		if (cell == null) {
			return false;
		} else {
			return true;
		}
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

		//
		// DispatchPlanExample ex = new DispatchPlanExample();
		// ex.createCriteria().andPlanUuidEqualTo(planUuid);
		// List<DispatchPlan> list = dispatchPlanMapper.selectByExample(ex);
		// DispatchPlan sendSMsDispatchPlan = null;
		// logger.info("回调完成通知查询结果:" + list.size());
		// if (list.size() <= 0) {
		// logger.info("回调完成通知查询结果 uuid错误！");
		// return false;
		// } else {
		// sendSMsDispatchPlan = list.get(0);
		// }
		//
		// boolean checkRes = checkLastNum(list.get(0));
		// if (checkRes) {
		// // 回调批次拨打结束通知。
		// logger.info("回调批次拨打结束通知开始 ");
		// ReturnData<SysUser> user =
		// auth.getUserById(list.get(0).getUserId().longValue());
		// if (user.getBody() != null) {
		// String batchRecordUrl = user.getBody().getBatchRecordUrl();
		// if (batchRecordUrl != null && batchRecordUrl != "") {
		// JSONObject jsonObject = new JSONObject();
		// jsonObject.put("batch_number", list.get(0).getBatchName());
		// jsonObject.put("operate", user.getBody().getUsername());
		// String sendHttpPost = "";
		// try {
		// sendHttpPost = HttpClientUtil.doPostJson(batchRecordUrl,
		// jsonObject.toString());
		// } catch (Exception e) {
		// if (insertThirdInterface(batchRecordUrl, jsonObject)) {
		// logger.info("回调错误记录新增成功...");
		// }
		// logger.error("error", e);
		// }
		// logger.info("回调批次拨打结束通知结果 :" + sendHttpPost);
		// }
		// } else {
		// logger.info("successSchedule 用户不存在");
		// }
		// }
		//
		// List<String> ids = new ArrayList<>();
		// ids.add(list.get(0).getPlanUuid());
		// ReturnData<List<CallPlanDetailRecordVO>> callPlanDetailRecord =
		// callPlanDetail.getCallPlanDetailRecord(ids);
		// ReturnData<SysUser> user =
		// auth.getUserById(list.get(0).getUserId().longValue());
		// if (user.getBody() != null) {
		// if (user.getBody().getCallRecordUrl() != null &&
		// user.getBody().getCallRecordUrl() != "") {
		// logger.info("通话记录通知开始");
		// JSONObject jsonObject = new JSONObject();
		// jsonObject.put("data", callPlanDetailRecord.getBody());
		// boolean insertThirdInterface =
		// insertThirdInterface(user.getBody().getCallRecordUrl(), jsonObject);
		// logger.info("通话记录通知结果 :" + insertThirdInterface);
		// }
		// }
		//
		// if (list.size() > 0) {
		// DispatchPlan dispatchPlan = list.get(0);
		// dispatchPlan.setStatusPlan(Constant.STATUSPLAN_2);// 2计划完成
		// // 0不重播非0表示重播次数
		// // if (dispatchPlan.getRecall() > 0) {
		// // // 获取重播条件
		// // String recallParams = dispatchPlan.getRecallParams();
		// // ReplayDto replayDto = JSONObject.parseObject(recallParams,
		// // ReplayDto.class);
		// // // 查询语音记录
		// // ReturnData<CallOutPlan> callRecordById =
		// // callManagerFeign.getCallRecordById(planUuid);
		// //
		// // if (callRecordById.getBody() != null) {
		// // // 意图
		// // if
		// //
		// (replayDto.getLabel().contains(callRecordById.getBody().getAccurateIntent()))
		// // {
		// // if (callRecordById.getBody().getAccurateIntent().equals("F")) {
		// // // F类判断挂断类型
		// // if
		// //
		// (replayDto.getLabelType().contains(callRecordById.getBody().getReason()))
		// // {
		// // // 创建
		// // }
		// // } else {
		// // // 创建
		// // }
		// // }
		// // }
		// // }
		// int result =
		// dispatchPlanMapper.updateByExampleSelective(dispatchPlan, ex);
		// logger.info("回调完成通知修改结果" + result);
		//
		// logger.info("---------------------发送短信------------------------");
		// logger.info("---------------------发送短信------------------------");
		// logger.info("---------------------发送短信------------------------");
		// logger.info("---------------------发送短信------------------------");
		// boolean resultmsg = SendSms(sendSMsDispatchPlan, label);
		// logger.info("---------------------发送短信------------------------");
		// logger.info("---------------------发送短信------------------------");
		// logger.info("---------------------发送短信------------------------");
		// logger.info("---------------------发送短信------------------------");
		//
		// // 判断下一批计算是否还有相同的推送任务，如果没有则设置redis失效时间为0
		// // List<DispatchPlan> queryAvailableSchedules =
		// // queryAvailableSchedules(dispatchPlan.getUserId(), 1,
		// // dispatchPlan.getLine(), new DispatchPlan(), false);
		// // if (queryAvailableSchedules.size() <= 0) {
		// // String key = dispatchPlan.getUserId() + "-" +
		// // dispatchPlan.getRobot() + "-" + dispatchPlan.getLine();
		// // redisUtil.del(key);
		// // logger.info("当前计划没有下一批任务:" + key + "删除了redis缓存");
		// // }
		//
		// }
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

	public static String getTimeByMinute(int minute) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MINUTE, minute);
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
	}

	@Override
	public boolean dispatchPlanBatch(DispatchPlanBatch dispatchPlanBatch) {
		int insert = dispatchPlanBatchMapper.insert(dispatchPlanBatch);
		return insert > 0 ? true : false;
	}

	@Override
	public Page<DispatchPlan> queryDispatchPlanByBatchId(Integer batchId, int pagenum, int pagesize) {
		Page<DispatchPlan> page = new Page<>();
		page.setPageNo(pagenum);
		page.setPageSize((pagesize));
		DispatchPlanExample example = new DispatchPlanExample();
		example.setLimitStart((pagenum - 1) * pagesize);
		example.setLimitEnd(pagesize);
		example.createCriteria().andBatchIdEqualTo(batchId).andIsDelEqualTo(Constant.IS_DEL_0);
		List<DispatchPlan> selectByExample = dispatchPlanMapper.selectByExample(example);
		int count = dispatchPlanMapper.countByExample(example);
		page.setRecords(selectByExample);
		page.setTotal(count);
		return page;
	}

	@Override
	public Page<DispatchPlan> queryDispatchPlanByParams(String phone, String planStatus, String startTime, String endTime, Integer batchId, String replayType, int pagenum, int pagesize, Long userId, boolean isSuperAdmin, Integer selectUserId, String startCallData, String endCallData, String orgCode, Integer isDesensitization)
	{
		Page<DispatchPlan> page = new Page<>();
		page.setPageNo(pagenum);
		page.setPageSize((pagesize));
		DispatchPlanExample example = new DispatchPlanExample();
		example.setLimitStart((pagenum - 1) * pagesize);
		example.setLimitEnd(pagesize);
		example.setOrderByClause("`gmt_create` DESC");
		Criteria createCriteria = example.createCriteria();
		if (phone != null && phone != "") {
			createCriteria.andPhoneEqualTo(phone);
		}
		if (startCallData != null && startCallData != "" && endCallData != null && endCallData != "") {
			createCriteria.andCallDataBetween(Integer.valueOf(startCallData), Integer.valueOf(endCallData));
		}

		if (selectUserId != null && selectUserId != 0) {
			createCriteria.andUserIdEqualTo(selectUserId);
		}

		if (planStatus != null && planStatus != "") {
			List<Integer> ids = new ArrayList<>();
			if (planStatus.contains(",")) {
				String[] split = planStatus.split(",");
				for (String sp : split) {
					ids.add(Integer.valueOf(sp));
				}
				createCriteria.andStatusPlanIn(ids);
			} else {
				createCriteria.andStatusPlanEqualTo(Integer.valueOf(planStatus));
			}
		}
		if (startTime != null && startTime != "" && endTime != null && endTime != "") {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				createCriteria.andGmtCreateBetween(new Timestamp(sdf.parse(startTime).getTime()), new Timestamp(sdf.parse(endTime).getTime()));
			} catch (ParseException e) {
				logger.error("error", e);
			}
		}

		if (batchId != null && batchId != 0) {
			createCriteria.andBatchIdEqualTo(batchId);
		}

		if (replayType != null && replayType != "") {
			List<Integer> ids = new ArrayList<>();
			if (replayType.contains(",")) {
				String[] split = replayType.split(",");
				for (String sp : split) {
					ids.add(Integer.valueOf(sp));
				}
				createCriteria.andReplayTypeIn(ids);
			} else {
				createCriteria.andReplayTypeEqualTo(Integer.valueOf(replayType));
			}
		}

		if (!isSuperAdmin) {
			createCriteria.andOrgCodeLike(orgCode + "%");
		} else {
			// // 超级用户
			// if (selectUserId != null) {
			// createCriteria.andUserIdEqualTo(selectUserId.intValue());
			// }
		}
		createCriteria.andIsDelEqualTo(Constant.IS_DEL_0);

		List<DispatchPlan> selectByExample = dispatchPlanMapper.selectByExample(example);
		List<DispatchPlan> resList = new ArrayList<DispatchPlan>();
		if(null != selectByExample && selectByExample.size()>0){
			LinkedHashSet<String> planUuidSet = new LinkedHashSet<String>();
			for(DispatchPlan dis : selectByExample){
				planUuidSet.add(dis.getPlanUuid());
			}

			loopA:for(String planUuid : planUuidSet) {
				// 转换userName
				loopB:for (DispatchPlan dis : selectByExample) {
					if(planUuid.equals(dis.getPlanUuid())) {
						ReturnData<SysUser> user = auth.getUserById(Long.valueOf(dis.getUserId()));
						if (user.getBody() != null) {
							dis.setUserName(user.getBody().getUsername());
						}

						List<DispatchLines> queryLinesByPlanUUID = lineService.queryLinesByPlanUUID(planUuid);
						dis.setLines(queryLinesByPlanUUID);

						// isDesensitization
						if (isDesensitization.equals(0)) {
							if (dis.getPhone().length() <= 7) {
								continue;
							}
							String phoneNumber = dis.getPhone().substring(0, 3) + "****" + dis.getPhone().substring(7, dis.getPhone().length());
							dis.setPhone(phoneNumber);
						}

						resList.add(dis);
						continue loopA;
					}
				}
			}
		}



		int count = dispatchPlanMapper.countByExample(example);
		page.setRecords(resList);
		page.setTotal(count);
		return page;
	}

	// private void getBatchNames(List<DispatchPlan> selectByExample) {
	// DispatchPlanBatchExample ex = new DispatchPlanBatchExample();
	// List<Integer> ids = new ArrayList<>();
	// for (DispatchPlan dispatchPlan : selectByExample) {
	// ids.add(dispatchPlan.getBatchId());
	// }
	// ex.createCriteria().andIdIn(ids);
	//
	// if (ids.size() > 0) {
	// List<DispatchPlanBatch> Batch =
	// dispatchPlanBatchMapper.selectByExample(ex);
	//
	// for (DispatchPlanBatch batchBean : Batch) {
	// for (DispatchPlan plan : selectByExample) {
	// if (batchBean.getId().equals(plan.getBatchId())) {
	// plan.setBatchName(batchBean.getName());
	// }
	// }
	// }
	// }
	// }

	@Override
	public List<LineConcurrent> outLineinfos(String userId) {
		DispatchPlanExample ex = new DispatchPlanExample();
		DispatchPlan dis = new DispatchPlan();
		dis.setCallHour("12");
		// dis.setStatusPlan(statusPlan);
		List<DispatchPlan> selectByCallHour = dispatchPlanMapper.selectByCallHour(dis);
		// ReturnData<List<LineConcurrent>> outLineinfos =
		// callManagerFeign.getLineInfos(userId);
		// return outLineinfos.getBody();
		return null;
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
	public List<DispatchPlan> selectPhoneByDate() {
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String dateNowStr = sdf.format(d);
		Calendar now = Calendar.getInstance();
		int hour = now.get(Calendar.HOUR_OF_DAY);

		DispatchPlan dis = new DispatchPlan();
		dis.setCallData(Integer.valueOf(dateNowStr));
		dis.setCallHour(String.valueOf(hour));
		dis.setIsDel(Constant.IS_DEL_0);
		dis.setStatusPlan(Constant.STATUSPLAN_1);
		dis.setStatusSync(Constant.STATUS_SYNC_0);
		// flag
		dis.setFlag(Constant.IS_FLAG_0);
		// 设置分页
		dis.setLimitStart(0);
		dis.setLimitEnd(1000);
		List<DispatchPlan> phones = dispatchPlanMapper.selectByCallHour(dis);
		return phones;
	}

	@Override
	public boolean deleteSchedule(String planUuid) {
		DispatchPlan dis = new DispatchPlan();
		dis.setIsDel(Constant.IS_DEL_1);
		DispatchPlanExample ex = new DispatchPlanExample();
		ex.createCriteria().andPlanUuidEqualTo(planUuid);
		int result = dispatchPlanMapper.updateByExampleSelective(dis, ex);
		return result > 0 ? true : false;
	}

	@Override
	public boolean batchUpdatePlans(IdsDto[] dto) {

		DispatchPlan dis = new DispatchPlan();
		List<String> planUUids = new ArrayList<>();
		for (IdsDto bean : dto)
		{
			if (StringUtils.isNotEmpty(bean.getPlanuuid()))
			{
				planUUids.add(bean.getPlanuuid());

				dis.setStatusPlan(bean.getStatus().intValue());
			}
		}

		dis.setGmtModified(DateUtil.getCurrent4Time());

		DispatchPlanExample ex = new DispatchPlanExample();
		ex.createCriteria().andPlanUuidIn(planUUids);
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
	public MessageDto operationAllPlanByBatchId(Integer batchId, String status, Long userId) {
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

		List<String> planUUids = new ArrayList<>();
		List<Integer> batchIds = new ArrayList<>();
		for (IdsDto bean : dto)
		{
			if (StringUtils.isNotEmpty(bean.getPlanuuid()))
			{
				planUUids.add(bean.getPlanuuid());
			}

			if (!batchIds.contains(bean.getBatchid()))
			{
				batchIds.add(bean.getBatchid());
			}
		}

		DispatchPlan dispatchPlan = new DispatchPlan();
		dispatchPlan.setIsDel(Constant.IS_DEL_1);
		DispatchPlanExample ex = new DispatchPlanExample();
		Criteria createCriteria = ex.createCriteria();

		createCriteria.andPlanUuidIn(planUUids);

		dispatchPlanMapper.updateByExampleSelective(dispatchPlan, ex);

		for (Integer batchId : batchIds)
		{
			// 当前批次都删除了 那么就不显示批次信息了
			DispatchPlanExample batch = new DispatchPlanExample();
			batch.createCriteria().andIsDelEqualTo(Constant.IS_DEL_0).andBatchIdEqualTo(batchId);
			int countByExample = dispatchPlanMapper.countByExample(batch);

			if (countByExample <= 0) {
				DispatchPlanBatchExample bex = new DispatchPlanBatchExample();
				bex.createCriteria().andIdEqualTo(batchId);
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
	public List<DispatchPlanBatch> queryDispatchPlanBatch(Long userId, Boolean isSuperAdmin, String orgCode) {
		// 批量信息
		DispatchPlanBatchExample example = new DispatchPlanBatchExample();
		com.guiji.dispatch.dao.entity.DispatchPlanBatchExample.Criteria createCriteria = example.createCriteria();
		if (!isSuperAdmin) {
			// createCriteria.andUserIdEqualTo(userId.intValue());
			createCriteria.andOrgCodeLike(orgCode + "%");
		}
		createCriteria.andStatusShowEqualTo(Constant.BATCH_STATUS_SHOW);
		return dispatchPlanBatchMapper.selectByExample(example);
	}

	@Override
	public boolean updateReplayDate(Boolean flag) {
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
					.andStatusPlanEqualTo(Constant.STATUSPLAN_1);
			dispatchPlanMapper.updateByExampleSelective(dis, ex);
			DispatchPlanExample ex1 = new DispatchPlanExample();
			ex1.createCriteria().andCleanEqualTo(Constant.IS_CLEAN_0).andCallDataLessThan(Integer.valueOf(dateNowStr))
					.andStatusPlanEqualTo(Constant.STATUSPLAN_3);

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
					.andStatusPlanEqualTo(Constant.STATUSPLAN_1);
			int result = dispatchPlanMapper.updateByExampleSelective(dis, ex);
			DispatchPlanExample ex1 = new DispatchPlanExample();
			ex1.createCriteria().andCleanEqualTo(Constant.IS_CLEAN_1).andCallDataLessThan(Integer.valueOf(dateNowStr))
					.andStatusPlanEqualTo(Constant.STATUSPLAN_3);
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
		List<String> ids = new ArrayList<>();

		for (DispatchPlan dispatchPlan : list) {
			ids.add(dispatchPlan.getPlanUuid());
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
		result = dispatchPlanMapper.updateDispatchPlanList(ids, flag);
		return result > 0 ? true : false;
	}

	@Override
	public List<DispatchPlan> selectPhoneByDateAndFlag(String flag, Integer stausPlan, Integer statusSYNC) {
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String dateNowStr = sdf.format(d);
		Calendar now = Calendar.getInstance();
		int hour = now.get(Calendar.HOUR_OF_DAY);

		DispatchPlan dis = new DispatchPlan();
		dis.setCallData(Integer.valueOf(dateNowStr));
		dis.setCallHour(String.valueOf(hour));
		dis.setIsDel(Constant.IS_DEL_0);
		dis.setStatusPlan(stausPlan);
		dis.setStatusSync(statusSYNC);
		dis.setFlag(flag);
		dis.setLimitStart(0);
		dis.setLimitEnd(1000);
		List<DispatchPlan> phones = dispatchPlanMapper.selectByCallHour(dis);
		return phones;
	}

	@Override
	public int getcall4BatchName(String batchName, Integer status) {
		DispatchPlanExample ex = new DispatchPlanExample();
		ex.createCriteria().andBatchNameEqualTo(batchName).andStatusPlanEqualTo(status)
				.andIsDelEqualTo(Constant.IS_DEL_0);
		int result = dispatchPlanMapper.countByExample(ex);
		return result;
	}

	@Override
	public Page<DispatchPlan> queryDispatchPlan(String batchName, int pagenum, int pagesize) {
		Page<DispatchPlan> page = new Page<>();
		page.setPageNo(pagenum);
		page.setPageSize((pagesize));
		DispatchPlanExample example = new DispatchPlanExample();
		example.setLimitStart((pagenum - 1) * pagesize);
		example.setLimitEnd(pagesize);
		// example.setOrderByClause("`gmt_create` DESC");
		example.createCriteria().andBatchNameEqualTo(batchName);
		List<DispatchPlan> selectByExample = dispatchPlanMapper.selectByExample(example);
		int countByExample = dispatchPlanMapper.countByExample(example);

		page.setRecords(selectByExample);
		page.setTotal(countByExample);
		return page;
	}

	@Override
	public List<CallPlanDetailRecordVO> queryDispatchPlanByPhoens(String phone, String batchName, int pagenum,
			int pagesize) {
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

		example.setLimitStart((pagenum - 1) * pagesize);
		example.setLimitEnd(pagesize);

		List<DispatchPlan> selectByExample = dispatchPlanMapper.selectByExample(example);

		List<String> ids = new ArrayList<>();
		for (DispatchPlan dis : selectByExample) {
			ids.add(dis.getPlanUuid());
		}
		if (ids.size() > 0) {
			ReturnData<List<CallPlanDetailRecordVO>> callPlanDetailRecord = callPlanDetail.getCallPlanDetailRecord(ids);
			return callPlanDetailRecord.getBody();
		} else {
			return new ArrayList<>();
		}

	}

	@Override
	public JSONObject getServiceStatistics(Long userId, Boolean isSuperAdmin, String orgCode) {
		JSONObject jsonObject = new JSONObject();
		// 累计任务号码总数，累计拨打号码总数，最后计划日期，最后拨打日期，累计服务天数
		int countNums = 0;
		if (!isSuperAdmin) {
			DispatchPlanExample ex = new DispatchPlanExample();
			ex.createCriteria().andOrgCodeLike(orgCode + "%");
			countNums = dispatchPlanMapper.countByExample(ex);
		} else {
			countNums = dispatchPlanMapper.countByExample(new DispatchPlanExample());
		}

		DispatchPlanExample ex = new DispatchPlanExample();
		if (!isSuperAdmin) {
			// 不是超级管理员就是通过orgCode查询
			ex.createCriteria().andStatusPlanEqualTo(Constant.STATUSPLAN_1).andIsDelEqualTo(Constant.IS_DEL_0)
					.andOrgCodeLike(orgCode + "%");
		} else {
			// 超级管理员查询所有
			ex.createCriteria().andIsDelEqualTo(Constant.IS_DEL_0).andStatusPlanEqualTo(Constant.STATUSPLAN_1);
		}
		int noCallNums = dispatchPlanMapper.countByExample(ex);

		DispatchPlanExample ex1 = new DispatchPlanExample();
		Criteria andStatusPlanEqualTo2 = ex1.createCriteria().andIsDelEqualTo(Constant.IS_DEL_0)
				.andStatusPlanEqualTo(Constant.STATUSPLAN_1);
		if (!isSuperAdmin) {
			andStatusPlanEqualTo2.andOrgCodeLike(orgCode + "%");
		}
		ex1.setOrderByClause("`gmt_create` ASC");
		ex1.setLimitStart(0);
		ex1.setLimitEnd(1);
		List<DispatchPlan> selectByExample = dispatchPlanMapper.selectByExample(ex1);

		DispatchPlan dis = new DispatchPlan();
		if (selectByExample.size() > 0) {
			dis = selectByExample.get(selectByExample.size() - 1);
		}

		DispatchPlanExample ex2 = new DispatchPlanExample();
		Criteria andStatusPlanEqualTo3 = ex2.createCriteria().andIsDelEqualTo(Constant.IS_DEL_0)
				.andStatusPlanEqualTo(Constant.STATUSPLAN_2);
		if (!isSuperAdmin) {
			andStatusPlanEqualTo3.andOrgCodeLike(orgCode + "%");
		}
		ex2.setOrderByClause("`gmt_create` DESC");
		ex2.setLimitStart(0);
		ex2.setLimitEnd(1);
		List<DispatchPlan> selectByExample2 = dispatchPlanMapper.selectByExample(ex2);

		DispatchPlan dis1 = new DispatchPlan();
		if (selectByExample2.size() > 0) {
			dis1 = selectByExample2.get(0);
		}

		ReturnData<SysUser> user = auth.getUserById(userId);
		if (user.getBody() != null) {
			SysUser body = user.getBody();
			long dateNow = new Date().getTime();
			long endTimeLong = body.getCreateTime().getTime();
			// long time = body.getVaildTime().getTime();
			// 结束时间-开始时间 = 天数
			long day = (dateNow - endTimeLong) / (24 * 60 * 60 * 1000);
			jsonObject.put("useDay", day);
		}

		jsonObject.put("countNums", countNums);
		jsonObject.put("noCallNums", noCallNums);
		jsonObject.put("lastPlanDate", dis.getCallData());
		jsonObject.put("lastCalledDate", dis1.getCallData());
		return jsonObject;

	}

	@Override
	public JSONObject getServiceStatistics(Long userId, String startTime, String endTime, Boolean isSuperAdmin,
			String orgCode) {
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
		if (!isSuperAdmin) {
			createCriteria.andOrgCodeLike(orgCode + "%");
		}

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
		if (!isSuperAdmin) {
			createCriteria1.andOrgCodeLike(orgCode + "%");
		}

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
	public TotalPlanCountVo totalPlanCountByUserDate(String userId, String beginDate, String endDate) {
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
			TotalPlanCountVo total = new TotalPlanCountVo();
			int totalCount = 0, doingCount = 0, finishCount = 0, suspendCount=0, stopCount=0;
			PlanTableNumEnum[] tables = PlanTableNumEnum.values();
			for(PlanTableNumEnum tableNum : tables){
				TotalPlanCountVo totalNum = dispatchPlanMapper.totalPlanCount(tableNum.getNum(), plan, beginDate, endDate);
				totalCount += totalNum.getTotalCount();
				doingCount += totalNum.getDoingCount();
				finishCount += totalNum.getFinishCount();
				suspendCount += totalNum.getSuspendCount();
				stopCount += totalNum.getStopCount();
			}
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
	public TotalPlanCountVo totalPlanCountByBatch(Integer batchId) {
		DispatchPlan plan = new DispatchPlan();
		plan.setBatchId(batchId);
		TotalPlanCountVo total = new TotalPlanCountVo();
		int totalCount = 0, doingCount = 0, finishCount = 0, suspendCount=0, stopCount=0;
		PlanTableNumEnum[] tables = PlanTableNumEnum.values();
		for(PlanTableNumEnum tableNum : tables){
			TotalPlanCountVo totalNum = dispatchPlanMapper.totalPlanCount(tableNum.getNum(), plan, null, null);
			totalCount += totalNum.getTotalCount();
			doingCount += totalNum.getDoingCount();
			finishCount += totalNum.getFinishCount();
			suspendCount += totalNum.getSuspendCount();
			stopCount += totalNum.getStopCount();
		}
		total.setTotalCount(totalCount);
		total.setDoingCount(doingCount);
		total.setFinishCount(finishCount);
		total.setSuspendCount(suspendCount);
		total.setStopCount(stopCount);
		return total;
	}

	@Override
	public boolean insertDispatchPlanList(List<DispatchPlan> list) {
		int result = dispatchPlanMapper.insertDispatchPlanList(list);
		return result > 0 ? true : false;
	}

	// public boolean checkBalckList(DispatchPlan dispatchPlan) {
	// if (redisUtil.get("blackList") != null) {
	// Map<String, BlackList> base = (Map) redisUtil.get("blackList");
	// if (base.containsKey(dispatchPlan.getPhone())) {
	// return true;
	// } else {
	// return false;
	// }
	// }
	// return false;
	// }

	@Override
	public List<DispatchPlan> selectPhoneByDate4Redis(Integer userId, String flag, Integer limit, Integer lineId) {
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String dateNowStr = sdf.format(d);
		Calendar now = Calendar.getInstance();
		int hour = now.get(Calendar.HOUR_OF_DAY);

		DispatchPlan dis = new DispatchPlan();
		dis.setCallData(Integer.valueOf(dateNowStr));
		dis.setCallHour(String.valueOf(hour));
		dis.setIsDel(Constant.IS_DEL_0);
		dis.setStatusPlan(Constant.STATUSPLAN_1);
		dis.setStatusSync(Constant.STATUS_SYNC_0);
		dis.setFlag(flag);
		dis.setLimitStart(0);
		dis.setLimitEnd(limit);
		dis.setUserId(userId);
		dis.setLine(lineId);
		List<DispatchPlan> phones = dispatchPlanMapper.selectByCallHour(dis);
		return phones;
	}

	@Override
	public List<DispatchPlan> selectPhoneByDate4UserId(String flag, Integer limit) {
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String dateNowStr = sdf.format(d);
		Calendar now = Calendar.getInstance();
		int hour = now.get(Calendar.HOUR_OF_DAY);
		DispatchPlan dis = new DispatchPlan();
		dis.setCallData(Integer.valueOf(dateNowStr));
		dis.setCallHour(String.valueOf(hour));
		dis.setIsDel(Constant.IS_DEL_0);
		dis.setStatusPlan(Constant.STATUSPLAN_1);
		dis.setStatusSync(Constant.STATUS_SYNC_0);
		dis.setFlag(flag);
		dis.setLimitStart(0);
		dis.setLimitEnd(limit);
		List<DispatchPlan> phones = dispatchPlanMapper.selectByCallHour4UserId(dis);
		return phones;
	}

	@Override
	public PlanCountVO getPlanCountByUserId(String orgCode) {
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String dateNowStr = sdf.format(d);
		DispatchPlanExample ex = new DispatchPlanExample();
		ex.createCriteria().andStatusPlanEqualTo(Constant.STATUSPLAN_1).andIsDelEqualTo(Constant.IS_DEL_0)
				.andOrgCodeLike(orgCode + "%");
		// 总数
		int countByExample = dispatchPlanMapper.countByExample(ex);

		DispatchPlanExample ex1 = new DispatchPlanExample();
		ex1.createCriteria().andStatusPlanEqualTo(Constant.STATUSPLAN_1).andIsDelEqualTo(Constant.IS_DEL_0)
				.andOrgCodeEqualTo(orgCode);
		int countByExample2 = dispatchPlanMapper.countByExample(ex1);

		PlanCountVO bean = new PlanCountVO();
		bean.setMainAccountNum(countByExample2);
		bean.setSubAccountNum(countByExample - countByExample2);

		return bean;
	}

	@Override
	public boolean stopPlanByorgCode(String orgCode, String type) {
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

		DispatchPlan updateRecord = new DispatchPlan();
		updateRecord.setStatusPlan(Constant.STATUSPLAN_4);

		dispatchPlanMapper.updateByExampleSelective(updateRecord, example);
		return true;
	}

	@Override
	public boolean batchInsertDisplanPlan(BatchDispatchPlanList plans, Long userId, String orgCode) {
		DispatchPlanBatch batch = new DispatchPlanBatch();
		batch.setName(plans.getBatchName());
		batch.setUserId(userId.intValue());
		batch.setStatusShow(Constant.BATCH_STATUS_SHOW);
		batch.setGmtCreate(DateUtil.getCurrent4Time());
		batch.setGmtModified(DateUtil.getCurrent4Time());
		batch.setOrgCode(orgCode);
		dispatchPlanBatchMapper.insert(batch);

		List<String> phones = new ArrayList<>();
		for (int i = 0; i < plans.getMobile().size(); i++) {
			DispatchPlan dispatchPlan = plans.getMobile().get(i);
			//路线类型
			Integer lineType = null != dispatchPlan.getLineType()?dispatchPlan.getLineType():PlanLineTypeEnum.SIP.getType();

			com.guiji.dispatch.dao.entity.DispatchPlan bean = new com.guiji.dispatch.dao.entity.DispatchPlan();
			BeanUtils.copyProperties(dispatchPlan, bean);
			bean.setPlanUuid(IdGenUtil.uuid());
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
			String phone = queryPhone(dispatchPlan.getPlanUuid());
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
			// 加入线路
			List<DispatchLines> lineList = plans.getLines();
			for (DispatchLines lines : lineList) {
				lines.setCreateTime(DateUtil.getCurrent4Time());
				lines.setPlanuuid(bean.getPlanUuid());
				lines.setLineType(lineType);
				lineService.insertLines(lines);
			}
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

				phones.add(bean.getPhone());
			}
		}
		return true;
	}

	private String queryPhone(String planUuid) {
		DispatchPlanExample ex = new DispatchPlanExample();
		ex.createCriteria().andPlanUuidEqualTo(planUuid);
		List<DispatchPlan> selectByExample = dispatchPlanMapper.selectByExample(ex);
		if (selectByExample.size() <= 0) {
			logger.info("queryPhone planuuid  error ");
			return null;
		}
		return selectByExample.get(0).getPhone();
	}

	@Override
	public DispatchPlan queryDispatchPlanById(String planUuId) {
		return dispatchPlanMapper.queryDispatchPlanById(planUuId);
	}

	@Override
	public String queryPlanRemarkById(String planUuid) {
		return dispatchPlanMapper.queryPlanRemarkById(planUuid);
	}

	/**
	 * 查询计划列表
	 * @param queryPlanDto
	 * @return
	 */
	@Override
	public ResultPage<DispatchPlan> queryPlanList(QueryPlanListDto queryPlanDto, ResultPage<DispatchPlan> page) {
		Integer pageNo = page.getPageNo();
		Integer pageSize = page.getPageSize();
		DispatchPlanExample example = new DispatchPlanExample();
		example.setLimitStart((pageNo - 1) * pageSize);
		example.setLimitEnd(pageSize);
		example.setOrderByClause("`gmt_create` DESC");
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

		if (!StringUtils.isEmpty(queryPlanDto.getPlanStatus())) {
			List<Integer> ids = new ArrayList<>();
			if (queryPlanDto.getPlanStatus().contains(",")) {
				String[] split = queryPlanDto.getPlanStatus().split(",");
				for (String sp : split) {
					ids.add(Integer.valueOf(sp));
				}
				createCriteria.andStatusPlanIn(ids);
			} else {
				createCriteria.andStatusPlanEqualTo(Integer.valueOf(queryPlanDto.getPlanStatus()));
			}
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

		if (!StringUtils.isEmpty(queryPlanDto.getReplayType())) {
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
		}

		if(null != queryPlanDto.getResultList()
				&& queryPlanDto.getResultList().size()>0){
			createCriteria.andResultIn(queryPlanDto.getResultList());
		}

		if (!queryPlanDto.isSuperAdmin()) {
			createCriteria.andOrgCodeLike(queryPlanDto.getOperOrgCode() + "%");
		} else {
			// // 超级用户
			// if (selectUserId != null) {
			// createCriteria.andUserIdEqualTo(selectUserId.intValue());
			// }
		}
		createCriteria.andIsDelEqualTo(Constant.IS_DEL_0);

		List<DispatchPlan> selectByExample = dispatchPlanMapper.selectByExample(example);
		List<DispatchPlan> resList = new ArrayList<DispatchPlan>();
		if(null != selectByExample && selectByExample.size()>0){
			LinkedHashSet<String> planUuidSet = new LinkedHashSet<String>();
			for(DispatchPlan dis : selectByExample){
				planUuidSet.add(dis.getPlanUuid());
			}

			Integer isDesensitization = queryPlanDto.getIsDesensitization();
			loopA:for(String planUuid : planUuidSet) {
				// 转换userName
				loopB:for (DispatchPlan dis : selectByExample) {
					if(planUuid.equals(dis.getPlanUuid())) {
						ReturnData<SysUser> user = auth.getUserById(Long.valueOf(dis.getUserId()));
						if (user.getBody() != null) {
							dis.setUserName(user.getBody().getUsername());
						}

						List<DispatchLines> queryLinesByPlanUUID = lineService.queryLinesByPlanUUID(planUuid);
						dis.setLines(queryLinesByPlanUUID);

						// isDesensitization
						if (isDesensitization.equals(0)) {
							if (dis.getPhone().length() <= 7) {
								continue;
							}
							String phoneNumber = dis.getPhone().substring(0, 3) + "****" + dis.getPhone().substring(7, dis.getPhone().length());
							dis.setPhone(phoneNumber);
						}

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
		example.setOrderByClause("`gmt_create` DESC");
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

		if (!StringUtils.isEmpty(queryPlanDto.getPlanStatus())) {
			List<Integer> ids = new ArrayList<>();
			if (queryPlanDto.getPlanStatus().contains(",")) {
				String[] split = queryPlanDto.getPlanStatus().split(",");
				for (String sp : split) {
					ids.add(Integer.valueOf(sp));
				}
				createCriteria.andStatusPlanIn(ids);
			} else {
				createCriteria.andStatusPlanEqualTo(Integer.valueOf(queryPlanDto.getPlanStatus()));
			}
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

		if (!StringUtils.isEmpty(queryPlanDto.getReplayType())) {
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
		}

		if(null != queryPlanDto.getResultList()
				&& queryPlanDto.getResultList().size()>0){
			createCriteria.andResultIn(queryPlanDto.getResultList());
		}

		if (!queryPlanDto.isSuperAdmin() && !StringUtils.isEmpty(queryPlanDto.getOperOrgCode())) {
			createCriteria.andOrgCodeLike(queryPlanDto.getOperOrgCode() + "%");
		} else {
			// // 超级用户
			// if (selectUserId != null) {
			// createCriteria.andUserIdEqualTo(selectUserId.intValue());
			// }
		}
		createCriteria.andIsDelEqualTo(Constant.IS_DEL_0);

		List<DownLoadPlanVo> list = dispatchPlanMapper.queryDownloadPlanList(example);
		return list;
	}

	@Override
	public List<DispatchLines> queryLineByPlan(String planUuid) {
		if(!StringUtils.isEmpty(planUuid)){
			return null;
		}else{
			return null;
		}

	}
}
