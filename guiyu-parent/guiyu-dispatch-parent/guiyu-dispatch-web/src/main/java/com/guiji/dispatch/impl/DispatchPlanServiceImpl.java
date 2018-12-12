package com.guiji.dispatch.impl;

import java.io.InputStream;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.guiji.auth.api.IAuth;
import com.guiji.ccmanager.api.ICallManagerOut;
import com.guiji.ccmanager.api.ICallPlanDetail;
import com.guiji.ccmanager.entity.LineConcurrent;
import com.guiji.ccmanager.vo.CallPlanDetailRecordVO;
import com.guiji.common.model.Page;
import com.guiji.component.result.Result.ReturnData;
import com.guiji.dispatch.bean.IdsDto;
import com.guiji.dispatch.bean.MessageDto;
import com.guiji.dispatch.dao.DispatchHourMapper;
import com.guiji.dispatch.dao.DispatchPlanBatchMapper;
import com.guiji.dispatch.dao.DispatchPlanMapper;
import com.guiji.dispatch.dao.ThirdInterfaceRecordsMapper;
import com.guiji.dispatch.dao.entity.BlackList;
import com.guiji.dispatch.dao.entity.DispatchHourExample;
import com.guiji.dispatch.dao.entity.DispatchPlan;
import com.guiji.dispatch.dao.entity.DispatchPlanBatch;
import com.guiji.dispatch.dao.entity.DispatchPlanBatchExample;
import com.guiji.dispatch.dao.entity.DispatchPlanExample;
import com.guiji.dispatch.dao.entity.DispatchPlanExample.Criteria;
import com.guiji.dispatch.dao.entity.ThirdInterfaceRecords;
import com.guiji.dispatch.service.IDispatchPlanService;
import com.guiji.dispatch.util.Constant;
import com.guiji.robot.api.IRobotRemote;
import com.guiji.robot.model.CheckParamsReq;
import com.guiji.robot.model.CheckResult;
import com.guiji.robot.model.HsParam;
import com.guiji.user.dao.entity.SysUser;
import com.guiji.utils.DateUtil;
import com.guiji.utils.HttpClientUtil;
import com.guiji.utils.IdGenUtil;
import com.guiji.utils.RedisUtil;
import com.sun.glass.ui.Size;

import sun.util.logging.resources.logging;

@Service
public class DispatchPlanServiceImpl implements IDispatchPlanService {
	static Logger logger = LoggerFactory.getLogger(DispatchPlanServiceImpl.class);

	@Autowired
	private DispatchPlanMapper dispatchPlanMapper;

	@Autowired
	private DispatchPlanBatchMapper dispatchPlanBatchMapper;

	@Autowired
	private ICallManagerOut callManagerFeign;

	@Autowired
	private DispatchHourMapper dispatchHourMapper;

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

	@Override
	public MessageDto addSchedule(DispatchPlan dispatchPlan, Long userId) throws Exception {
		boolean result = checkBalckList(dispatchPlan);
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
					logger.info("addSchedule校验参数失败");
					return dto;
				}
			}
		} else {
			dto.setMsg(checkParams.getMsg());
			dto.setResult(false);
			logger.info("addSchedule校验参数失败");
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
		dispatchPlanBatchMapper.insert(dispatchPlanBatch);

		// dispatchPlan.setPlanUuid(IdGenUtil.uuid());
		dispatchPlan.setUserId(userId.intValue());
		if (result) {
			// 当前黑名单
			logger.info("当前号码添加处于黑名单状态:" + dispatchPlan.getPhone());
			dispatchPlan.setStatusPlan(Constant.STATUSPLAN_2);
			dispatchPlan.setStatusSync(Constant.STATUS_SYNC_1);
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

		dispatchPlanMapper.insert(dispatchPlan);
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
		DispatchPlanExample ex = new DispatchPlanExample();
		ex.createCriteria().andUserIdEqualTo(userId);
		List<DispatchPlan> list = dispatchPlanMapper.selectByExample(ex);
		int result = 0;
		for (DispatchPlan batch : list) {
			DispatchPlan dispatchPlan = new DispatchPlan();
			dispatchPlan.setUserId(batch.getUserId());
			dispatchPlan.setStatusPlan(status);
			result = dispatchPlanMapper.updateByExampleSelective(dispatchPlan, new DispatchPlanExample());
		}
		return result > 0 ? true : false;
	}

	@Override
	public List<DispatchPlan> queryExecuteResult(String planUuid) {
		return null;
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public boolean batchImport(String fileName, Long userId, MultipartFile file, String str) throws Exception {
		boolean result = false;
		if (!fileName.matches("^.+\\.(?i)(xls)$") && !fileName.matches("^.+\\.(?i)(xlsx)$")) {
			throw new Exception("上传文件格式不正确");
		}
		boolean isExcel2003 = true;
		if (fileName.matches("^.+\\.(?i)(xlsx)$")) {
			isExcel2003 = false;
		}
		InputStream is = file.getInputStream();
		Workbook wb = null;
		if (isExcel2003) {
			wb = new HSSFWorkbook(is);
		} else {
			wb = new XSSFWorkbook(is);
		}
		Sheet sheet = wb.getSheetAt(0);

		DispatchPlan dispatchPlan = JSONObject.parseObject(str, DispatchPlan.class);
		DispatchPlanBatch dispatchPlanBatch = JSONObject.parseObject(str, DispatchPlanBatch.class);
		dispatchPlanBatch.setGmtModified(DateUtil.getCurrent4Time());
		dispatchPlanBatch.setGmtCreate(DateUtil.getCurrent4Time());
		dispatchPlanBatch.setStatusNotify(Constant.STATUS_NOTIFY_0);
		dispatchPlanBatch.setUserId(userId.intValue());
		// 查询用户名称
		ReturnData<SysUser> SysUser = authService.getUserById(userId);
		if (SysUser != null) {
			dispatchPlan.setUsername(SysUser.getBody().getUsername());
		}
		// 重复校验
		List phones = new ArrayList<>();

		for (int r = 1; r <= sheet.getLastRowNum(); r++) {
			Row row = sheet.getRow(r);
			if (row == null) {
				continue;
			}
			String phone;
			if (isNull(row.getCell(0))) {
				row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
				phone = row.getCell(0).getStringCellValue();
				if (!isNumeric(phone)) {
					throw new Exception("号码格式有问题，请检查文件");
				}
				phones.add(phone);
			} else {
				throw new Exception("导入失败(第" + (r + 1) + "行,电话未填写)");
			}

			String params = "";
			if (isNull(row.getCell(1))) {
				row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
				params = row.getCell(1).getStringCellValue();
			}

			String attach = "";
			if (isNull(row.getCell(2))) {
				row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
				attach = row.getCell(2).getStringCellValue();
			}

			HashSet<Integer> hashSet = new HashSet<>(phones);
			if (phones.size() != hashSet.size()) {
				throw new Exception("当前号码存在重复的数据,请检查文件");
			}

			dispatchPlan.setPhone(phone);
			dispatchPlan.setAttach(attach);
			dispatchPlan.setUserId(userId.intValue());
			dispatchPlan.setParams(params);
			dispatchPlan.setPlanUuid(IdGenUtil.uuid());
			dispatchPlan.setGmtModified(DateUtil.getCurrent4Time());
			dispatchPlan.setGmtCreate(DateUtil.getCurrent4Time());
			dispatchPlan.setBatchId(dispatchPlanBatch.getId());
			dispatchPlan.setStatusPlan(Constant.STATUSPLAN_1);
			dispatchPlan.setStatusSync(Constant.STATUS_SYNC_0);
			dispatchPlan.setIsDel(Constant.IS_DEL_0);
			dispatchPlan.setReplayType(Constant.REPLAY_TYPE_0);
			dispatchPlan.setIsTts(Constant.IS_TTS_0);
			dispatchPlan.setFlag(Constant.IS_FLAG_0);
			// 检查校验参数
			ReturnData<List<CheckResult>> checkParams = checkParams(dispatchPlan);
			if (checkParams.success) {
				if (checkParams.getBody() != null) {
					List<CheckResult> body = checkParams.getBody();
					CheckResult checkResult = body.get(0);
					if (!checkResult.isPass()) {
						throw new Exception("机器人合成" + checkResult.getCheckMsg());
					}
				}
			} else {
				throw new Exception("请求校验参数失败,请检查机器人的参数");
			}

			dispatchPlanMapper.insert(dispatchPlan);
			dispatchPlanBatch.setId(null);
			dispatchPlanBatchMapper.insert(dispatchPlanBatch);
		}
		return result;
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
	public boolean successSchedule(String planUuid) {
		logger.info("----------------------------successSchedule-------------------------------------");
		logger.info("----------------------------successSchedule-------------------------------------");
		logger.info("----------------------------successSchedule-------------------------------------");
		logger.info("----------------------------successSchedule-------------------------------------");
		logger.info("----------------------------successSchedule-------------------------------------");
		logger.info("----------------------------successSchedule-------------------------------------");
		logger.info("----------------------------successSchedule-------------------------------------");
		logger.info("----------------------------successSchedule-------------------------------------");
		logger.info("回调完成通知planUuid:" + planUuid);
		DispatchPlanExample ex = new DispatchPlanExample();
		ex.createCriteria().andPlanUuidEqualTo(planUuid);
		List<DispatchPlan> list = dispatchPlanMapper.selectByExample(ex);
		logger.info("回调完成通知查询结果:" + list.size());
		if (list.size() <= 0) {
			logger.info("回调完成通知查询结果 uuid错误！");
			return false;
		}

		boolean checkRes = checkLastNum(list.get(0));
		if (checkRes) {
			// 回调批次拨打结束通知。
			logger.info("回调批次拨打结束通知开始 ");
			ReturnData<SysUser> user = auth.getUserById(list.get(0).getUserId().longValue());
			if (user.getBody() != null) {
				String batchRecordUrl = user.getBody().getBatchRecordUrl();
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("batch_number", list.get(0).getBatchName());
				jsonObject.put("operate", user.getBody().getUsername());
				String sendHttpPost = "";
				try {
					sendHttpPost = HttpClientUtil.doPostJson(batchRecordUrl, jsonObject.toString());
				} catch (Exception e) {
					if (insertThirdInterface(batchRecordUrl, jsonObject)) {
						logger.info("回调错误记录新增成功...");
					}
					logger.error("error", e);
				}
				logger.info("回调批次拨打结束通知结果 :" + sendHttpPost);
			} else {
				logger.info("successSchedule 用户不存在");
			}
		}

		List<String> ids = new ArrayList<>();
		ids.add(list.get(0).getPlanUuid());
		ReturnData<List<CallPlanDetailRecordVO>> callPlanDetailRecord = callPlanDetail.getCallPlanDetailRecord(ids);
		ReturnData<SysUser> user = auth.getUserById(list.get(0).getUserId().longValue());
		if (user.getBody() != null) {
			logger.info("通话记录通知开始");
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("data", callPlanDetailRecord.getBody());
			boolean insertThirdInterface = insertThirdInterface(user.getBody().getCallRecordUrl(), jsonObject);
			logger.info("通话记录通知结果 :" + insertThirdInterface);
		}

		if (list.size() > 0) {
			DispatchPlan dispatchPlan = list.get(0);
			dispatchPlan.setStatusPlan(Constant.STATUSPLAN_2);// 2计划完成
			// 0不重播非0表示重播次数
			// if (dispatchPlan.getRecall() > 0) {
			// // 获取重播条件
			// String recallParams = dispatchPlan.getRecallParams();
			// ReplayDto replayDto = JSONObject.parseObject(recallParams,
			// ReplayDto.class);
			// // 查询语音记录
			// ReturnData<CallOutPlan> callRecordById =
			// callManagerFeign.getCallRecordById(planUuid);
			//
			// if (callRecordById.getBody() != null) {
			// // 意图
			// if
			// (replayDto.getLabel().contains(callRecordById.getBody().getAccurateIntent()))
			// {
			// if (callRecordById.getBody().getAccurateIntent().equals("F")) {
			// // F类判断挂断类型
			// if
			// (replayDto.getLabelType().contains(callRecordById.getBody().getReason()))
			// {
			// // 创建
			// }
			// } else {
			// // 创建
			// }
			// }
			// }
			// }
			int result = dispatchPlanMapper.updateByExampleSelective(dispatchPlan, ex);
			logger.info("回调完成通知修改结果" + result);
			// 判断下一批计算是否还有相同的推送任务，如果没有则设置redis失效时间为0
			List<DispatchPlan> queryAvailableSchedules = queryAvailableSchedules(dispatchPlan.getUserId(), 1,
					dispatchPlan.getLine(), new DispatchPlan(), false);
			if (queryAvailableSchedules.size() <= 0) {
				String key = dispatchPlan.getUserId() + "-" + dispatchPlan.getRobot() + "-" + dispatchPlan.getLine();
				redisUtil.del(key);
				logger.info("当前计划没有下一批任务:" + key + "删除了redis缓存");
			}

		}

		return true;
	}

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
		DispatchPlanExample dis = new DispatchPlanExample();
		dis.createCriteria().andBatchNameEqualTo(dispatchPlan.getBatchName())
				.andStatusPlanEqualTo(Constant.STATUSPLAN_2);
		int countByExample = dispatchPlanMapper.countByExample(dis);
		if (countByExample <= 0) {
			return true;
		} else {
			return false;
		}
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
	public Page<DispatchPlan> queryDispatchPlanByParams(String phone, String planStatus, String startTime,
			String endTime, Integer batchId, String replayType, int pagenum, int pagesize, Long userId,
			boolean isSuperAdmin) {
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
				createCriteria.andGmtCreateBetween(new Timestamp(sdf.parse(startTime).getTime()),
						new Timestamp(sdf.parse(endTime).getTime()));
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
			createCriteria.andUserIdEqualTo(userId.intValue());
		}
		createCriteria.andIsDelEqualTo(Constant.IS_DEL_0);
		List<DispatchPlan> selectByExample = dispatchPlanMapper.selectByExample(example);

		int count = dispatchPlanMapper.countByExample(example);
		page.setRecords(selectByExample);
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
		logger.info("----------------------------queryAvailableSchedules-------------------------------------");
		logger.info("----------------------------queryAvailableSchedules-------------------------------------");
		logger.info("----------------------------queryAvailableSchedules-------------------------------------");
		logger.info("----------------------------queryAvailableSchedules-------------------------------------");
		logger.info("----------------------------queryAvailableSchedules-------------------------------------");
		logger.info("----------------------------queryAvailableSchedules-------------------------------------");
		logger.info("----------------------------queryAvailableSchedules-------------------------------------");
		DispatchPlanExample ex = new DispatchPlanExample();
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String dateNowStr = sdf.format(d);
		Calendar now = Calendar.getInstance();
		int hour = now.get(Calendar.HOUR_OF_DAY);

		// 根据日期查询号码
		ex.createCriteria().andCallDataEqualTo(Integer.valueOf(dateNowStr)).andIsDelEqualTo(Constant.IS_DEL_0)
				.andStatusPlanEqualTo(Constant.STATUSPLAN_1).andUserIdEqualTo(userId).andLineEqualTo(lineId)
				.andStatusSyncEqualTo(Constant.STATUS_SYNC_0);
		DispatchPlan dis = new DispatchPlan();
		dis.setCallData(Integer.valueOf(dateNowStr));
		dis.setIsDel(Constant.IS_DEL_0);
		dis.setStatusPlan(Constant.STATUSPLAN_1);
		dis.setUserId(userId);
		dis.setLine(lineId);
		dis.setCallHour(String.valueOf(hour));
		dis.setStatusSync(Constant.STATUS_SYNC_0);
		dis.setFlag(Constant.IS_FLAG_2);
		List<String> list = new ArrayList<>();
		Object object2 = redisUtil.get("robotId");
		if (redisUtil.get("robotId") != null) {
			logger.info("当前模板升级中，接口 queryAvailableSchedules 对应模板查不到数据，" + redisUtil.get("robotId"));
			String object = (String) redisUtil.get("robotId");
			String[] split = object.split(",");
			for (String str : split) {
				list.add(str);
			}
		}
		dis.setRobotIds(list);
		if (requestCount != 0) {
			dis.setLimitStart(0);
			dis.setLimitEnd(requestCount);
		}
		List<DispatchPlan> phones = dispatchPlanMapper.selectByCallHour(dis);
		logger.info("queryAvailableSchedules phones size" + phones.size());
		// 同步状态;0未同步1已同步
		List<DispatchPlan> updateStatus = new ArrayList<>();

		for (DispatchPlan dispatchPlan : phones) {
			dispatchPlan.setStatusSync(Constant.STATUS_SYNC_1);
			updateStatus.add(dispatchPlan);
			// dispatchPlanMapper.updateByPrimaryKeySelective(dispatchPlan);
		}
		if (phones.size() > 0) {
			logger.info("当前queryAvailableSchedules号码:" + phones.get(0).getPlanUuid() + "--------"
					+ phones.get(0).getPhone());
			logger.info("当前queryAvailableSchedules号码:" + phones.get(0));
		}

		if (flag) {
			// 批量修改状态
			if (updateStatus.size() > 0) {
				int res = dispatchPlanMapper.updateDispatchPlanList(updateStatus);
				// 判断当前任务是否查询完毕
				int count = dispatchPlanMapper.countByExample(ex);
				if (count <= requestCount) {
					isSuccess.setSuccess(false);
				}
			}
		}
		return phones;
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
		int result = 0;
		for (IdsDto bean : dto) {
			DispatchPlan dis = new DispatchPlan();
			dis.setStatusPlan(bean.getStatus().intValue());
			try {
				dis.setGmtModified(DateUtil.getCurrent4Time());
			} catch (Exception e) {
				logger.error("error", e);
			}
			DispatchPlanExample ex = new DispatchPlanExample();
			ex.createCriteria().andPlanUuidEqualTo(bean.getPlanuuid());
			result = dispatchPlanMapper.updateByExampleSelective(dis, ex);
		}
		return result > 0 ? true : false;
	}

	@Override
	public MessageDto operationAllPlanByBatchId(Integer batchId, String status, Long userId) {
		logger.info("operationAllPlanByBatchId userId:" + userId);
		MessageDto result = new MessageDto();
		if (batchId != 0) {
			// 不选择传入0
			DispatchPlanBatch dispatchPlanBatch = dispatchPlanBatchMapper.selectByPrimaryKey(batchId);
			DispatchPlan dispatchPlan = new DispatchPlan();
			dispatchPlan.setBatchId(dispatchPlanBatch.getId());

			// 根据批次号查询一条数据，判断到底是什么
			DispatchPlanExample dis = new DispatchPlanExample();
			dis.createCriteria().andBatchIdEqualTo(dispatchPlanBatch.getId());
			DispatchPlan resultPlan = dispatchPlanMapper.selectByExample(dis).get(0);
			// 0未计划1计划中2计划完成3暂停计划4停止计划
			// 停止之后不能暂停 不能恢复
			boolean res = checkStatus(status, resultPlan);
			if (!res) {
				result.setResult(false);
				result.setMsg("当前批次号码状态不能进行当前状态");
				return result;
			}
			dispatchPlan.setStatusPlan(Integer.valueOf(status));
			dispatchPlanMapper.updateByExampleSelective(dispatchPlan, new DispatchPlanExample());
		} else {
			DispatchPlan dis = new DispatchPlan();
			DispatchPlanExample example = new DispatchPlanExample();
			example.createCriteria().andUserIdEqualTo(userId.intValue());
			DispatchPlan dispatchPlan = dispatchPlanMapper.selectByExample(example).get(0);
			boolean res = checkStatus(status, dispatchPlan);
			if (!res) {
				result.setResult(false);
				result.setMsg("当前批次号码状态不能进行当前状态");
				return result;
			}
			dis.setStatusPlan(Integer.valueOf(status));
			dispatchPlanMapper.updateByExampleSelective(dis, example);
		}
		return result;
	}

	private boolean checkStatus(String status, DispatchPlan dispatchPlan) {
		if (Integer.valueOf(status) == Constant.STATUSPLAN_3
				&& dispatchPlan.getStatusPlan().equals(Constant.STATUSPLAN_4)) {
			return false;
		}
		// 停止之后不能暂停 不能恢复
		if (Integer.valueOf(status) == Constant.STATUSPLAN_1
				&& dispatchPlan.getStatusPlan().equals(Constant.STATUSPLAN_4)) {
			return false;
		}
		// 一键恢复针对暂停的不能操作
		if (Integer.valueOf(status) == Constant.STATUSPLAN_1
				&& dispatchPlan.getStatusPlan().equals(Constant.STATUSPLAN_3)) {
			return false;
		}
		return true;
	}

	@Override
	public boolean batchDeletePlans(IdsDto[] dto) {
		int result = 0;
		for (IdsDto bean : dto) {
			DispatchPlan dispatchPlan = new DispatchPlan();
			dispatchPlan.setIsDel(Constant.IS_DEL_1);
			DispatchPlanExample ex = new DispatchPlanExample();
			ex.createCriteria().andPlanUuidEqualTo(bean.getPlanuuid());
			result = dispatchPlanMapper.updateByExampleSelective(dispatchPlan, ex);
			DispatchHourExample exHour = new DispatchHourExample();
			exHour.createCriteria().andDispatchIdEqualTo(bean.getPlanuuid());
			result = dispatchHourMapper.deleteByExample(exHour);
		}
		return result > 0 ? true : false;
	}

	@Override
	public List<DispatchPlanBatch> queryDispatchPlanBatch(Long userId, Boolean isSuperAdmin) {
		DispatchPlanBatchExample example = new DispatchPlanBatchExample();
		com.guiji.dispatch.dao.entity.DispatchPlanBatchExample.Criteria createCriteria = example.createCriteria();
		if (!isSuperAdmin) {
			createCriteria.andUserIdEqualTo(userId.intValue());
		}
		return dispatchPlanBatchMapper.selectByExample(example);
	}

	@Override
	public boolean updateReplayDate() {
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String dateNowStr = sdf.format(d);

		DispatchPlan dis = new DispatchPlan();
		dis.setCallData(Integer.valueOf(dateNowStr));

		DispatchPlanExample ex = new DispatchPlanExample();
		ex.createCriteria().andCleanEqualTo(Constant.IS_CLEAN_1);

		int result = dispatchPlanMapper.updateByExampleSelective(dis, ex);
		return result > 0 ? true : false;
	}

	@Override
	public boolean checkBatchId(String name) {
		DispatchPlanBatchExample ex = new DispatchPlanBatchExample();
		ex.createCriteria().andNameEqualTo(name);
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
		for (String id : ids) {
			DispatchPlan dis = new DispatchPlan();
			DispatchPlanExample ex = new DispatchPlanExample();
			ex.createCriteria().andPlanUuidEqualTo(id);
			dis.setFlag(flag);
			result = dispatchPlanMapper.updateByExampleSelective(dis, ex);
		}

		// List<DispatchPlan> modList = new ArrayList<>();
		// for(DispatchPlan dis : modList){
		// dis.setFlag(flag);
		// }
		// result = dispatchPlanMapper.updateDispatchPlanList(modList);
		//
		return result > 0 ? true : false;
	}

	@Override
	public List<DispatchPlan> selectPhoneByDateAndFlag(String flag) {
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
	public JSONObject queryDispatchPlanByPhoens(String phone, String batchName, int pagenum, int pagesize) {
		JSONObject jsonObject = new JSONObject();
		Page<DispatchPlan> page = new Page<>();
		page.setPageNo(pagenum);
		page.setPageSize((pagesize));
		DispatchPlanExample example = new DispatchPlanExample();
		Criteria createCriteria = example.createCriteria();
		if (phone != null && phone != "") {
			createCriteria.andPhoneEqualTo(phone);
		}

		if (batchName != null && batchName != "") {
			createCriteria.andBatchNameEqualTo(batchName);
		}
		example.setLimitStart((pagenum - 1) * pagesize);
		example.setLimitEnd(pagesize);

		List<DispatchPlan> selectByExample = dispatchPlanMapper.selectByExample(example);
		int countByExample = dispatchPlanMapper.countByExample(example);
		page.setRecords(selectByExample);
		page.setTotal(countByExample);

		List<String> ids = new ArrayList<>();
		for (DispatchPlan dis : selectByExample) {
			ids.add(dis.getPlanUuid());
		}
		ReturnData<List<CallPlanDetailRecordVO>> callPlanDetailRecord = callPlanDetail.getCallPlanDetailRecord(ids);
		jsonObject.put("data", callPlanDetailRecord.getBody());
		return jsonObject;
	}

	@Override
	public JSONObject getServiceStatistics(Long userId) {
		JSONObject jsonObject = new JSONObject();
		// 累计任务号码总数，累计拨打号码总数，最后计划日期，最后拨打日期，累计服务天数

		int countNums = dispatchPlanMapper.countByExample(new DispatchPlanExample());
		DispatchPlanExample ex = new DispatchPlanExample();
		Criteria andStatusPlanEqualTo = ex.createCriteria().andIsDelEqualTo(Constant.IS_DEL_0)
				.andStatusPlanEqualTo(Constant.STATUSPLAN_2);
		if (userId != 0) {
			andStatusPlanEqualTo.andUserIdEqualTo(userId.intValue());
		}
		int calledNums = dispatchPlanMapper.countByExample(ex);

		DispatchPlanExample ex1 = new DispatchPlanExample();
		Criteria andStatusPlanEqualTo2 = ex1.createCriteria().andIsDelEqualTo(Constant.IS_DEL_0)
				.andStatusPlanEqualTo(Constant.STATUSPLAN_1);
		if (userId != 0) {
			andStatusPlanEqualTo2.andUserIdEqualTo(userId.intValue());
		}
		ex1.setOrderByClause("`gmt_create` DESC");
		DispatchPlan dispatchPlan = dispatchPlanMapper.selectByExample(ex1).get(0);

		DispatchPlanExample ex2 = new DispatchPlanExample();
		Criteria andStatusPlanEqualTo3 = ex2.createCriteria().andIsDelEqualTo(Constant.IS_DEL_0)
				.andStatusPlanEqualTo(Constant.STATUSPLAN_2);
		if (userId != 0) {
			andStatusPlanEqualTo3.andUserIdEqualTo(userId.intValue());
		}

		ex2.setOrderByClause("`gmt_create` DESC");
		DispatchPlan dispatchPlan1 = dispatchPlanMapper.selectByExample(ex2).get(0);

		ReturnData<SysUser> user = auth.getUserById(userId);
		if (user.getBody() != null) {
			SysUser body = user.getBody();
			long stateTimeLong = new Date().getTime();
			long endTimeLong = body.getCreateTime().getTime();
			// 结束时间-开始时间 = 天数
			long day = (stateTimeLong - endTimeLong) / (24 * 60 * 60 * 1000);
			jsonObject.put("day", day);
		}

		jsonObject.put("countNums", countNums);
		jsonObject.put("calledNums", calledNums);
		jsonObject.put("lastPlanDate", dispatchPlan.getCallData());
		jsonObject.put("lastCalledDate", dispatchPlan1.getCallData());
		return jsonObject;
	}

	@Override
	public JSONObject getServiceStatistics(Long userId, String startTime, String endTime, Boolean isSuperAdmin) {
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
			createCriteria.andUserIdEqualTo(userId.intValue());
		}

		DispatchPlanExample ex1 = new DispatchPlanExample();
		Criteria createCriteria1 = ex1.createCriteria();
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
			createCriteria1.andUserIdEqualTo(userId.intValue());
		}

		int taskCount = dispatchPlanMapper.countByExample(ex);
		int calledCount = dispatchPlanMapper.countByExample(ex1);
		int Batchcount = dispatchPlanBatchMapper.countByExample(new DispatchPlanBatchExample());
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("taskCount", taskCount);
		jsonObject.put("calledCount", calledCount);
		jsonObject.put("Batchcount", Batchcount);
		return jsonObject;
	}

	@Override
	public boolean insertDispatchPlanList(List<DispatchPlan> list) {
		int result = dispatchPlanMapper.insertDispatchPlanList(list);
		return result > 0 ? true : false;
	}

	public boolean checkBalckList(DispatchPlan dispatchPlan) {
		if (redisUtil.get("blackList") != null) {
			Map<String, BlackList> base = (Map) redisUtil.get("blackList");
			if (base.containsKey(dispatchPlan.getPhone())) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

}
