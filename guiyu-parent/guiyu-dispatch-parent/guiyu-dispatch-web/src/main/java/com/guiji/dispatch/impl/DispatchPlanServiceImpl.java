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
import com.guiji.ccmanager.entity.LineConcurrent;
import com.guiji.common.model.Page;
import com.guiji.component.result.Result.ReturnData;
import com.guiji.dispatch.bean.IdsDto;
import com.guiji.dispatch.bean.MessageDto;
import com.guiji.dispatch.dao.DispatchHourMapper;
import com.guiji.dispatch.dao.DispatchPlanBatchMapper;
import com.guiji.dispatch.dao.DispatchPlanMapper;
import com.guiji.dispatch.dao.entity.DispatchHourExample;
import com.guiji.dispatch.dao.entity.DispatchPlan;
import com.guiji.dispatch.dao.entity.DispatchPlanBatch;
import com.guiji.dispatch.dao.entity.DispatchPlanBatchExample;
import com.guiji.dispatch.dao.entity.DispatchPlanExample;
import com.guiji.dispatch.dao.entity.DispatchPlanExample.Criteria;
import com.guiji.dispatch.service.IDispatchPlanService;
import com.guiji.dispatch.util.Constant;
import com.guiji.dispatch.util.DateProvider;
import com.guiji.user.dao.entity.SysUser;
import com.guiji.utils.IdGenUtil;
import com.guiji.utils.RedisUtil;

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
	private DateProvider dateProvider;
	//
	@Autowired
	private IAuth authService;

	@Autowired
	private RedisUtil redisUtil;

	@Override
	public boolean addSchedule(DispatchPlan dispatchPlan, Long userId) throws Exception {
		logger.info("userId:" + userId);
		// 查询用户名称
		ReturnData<SysUser> SysUser = authService.getUserById(userId);
		logger.info("SysUser:" + SysUser.getBody().getUsername());
		if (SysUser != null) {
			dispatchPlan.setUsername(SysUser.getBody().getUsername());
		}
		DispatchPlanBatch dispatchPlanBatch = new DispatchPlanBatch();
		dispatchPlanBatch.setName(dispatchPlan.getBatchName());
		// 通知状态;通知状态1等待2失败3成功
		dispatchPlanBatch.setStatusNotify(Constant.STATUSNOTIFY_0);
		dispatchPlanBatch.setGmtModified(dateProvider.getCurrentTime());
		dispatchPlanBatch.setGmtCreate(dateProvider.getCurrentTime());
		dispatchPlanBatch.setStatusShow(dispatchPlan.getStatusShow());
		dispatchPlanBatch.setUserId(userId.intValue());
		dispatchPlanBatchMapper.insert(dispatchPlanBatch);

		dispatchPlan.setPlanUuid(IdGenUtil.uuid());
		dispatchPlan.setUserId(userId.intValue());
		dispatchPlan.setStatusPlan(Constant.STATUSPLAN_1);
		dispatchPlan.setStatusSync(Constant.STATUS_SYNC_0);
		dispatchPlan.setGmtModified(dateProvider.getCurrentTime());
		dispatchPlan.setGmtCreate(dateProvider.getCurrentTime());
		dispatchPlan.setReplayType(Constant.REPLAY_TYPE_0);
		dispatchPlan.setIsDel(Constant.IS_DEL_0);
		dispatchPlan.setBatchId(dispatchPlanBatch.getId());
		dispatchPlan.setIsTts(Constant.IS_TTS_0);
		int result = dispatchPlanMapper.insert(dispatchPlan);

		// 调用机器人中心判断参数是否合法，如果合法则status_plan =1 否则为0 然后入库

		// // 写入时间dispatchHour
		// for (String hour : hours) {
		// DispatchHour dispatchHour = new DispatchHour();
		// try {
		// dispatchHour.setGmtCreate(ToolDateTime.getCurrentTime());
		// } catch (Exception e) {
		// logger.error("error", e);
		// }
		// dispatchHour.setDispatchId(dispatchPlan.getPlanUuid());
		// dispatchHour.setHour(Integer.valueOf(hour));
		// // 未拨打
		// dispatchHour.setIsCall(0);
		// dispatchHourMapper.insert(dispatchHour);
		// }

		return result > 0 ? true : false;
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
		dispatchPlanBatch.setGmtModified(dateProvider.getCurrentTime());
		dispatchPlanBatch.setGmtCreate(dateProvider.getCurrentTime());
		dispatchPlanBatch.setStatusNotify(Constant.STATUS_NOTIFY_0);
		dispatchPlanBatch.setUserId(userId.intValue());
		dispatchPlanBatchMapper.insert(dispatchPlanBatch);

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
			dispatchPlan.setGmtModified(dateProvider.getCurrentTime());
			dispatchPlan.setGmtCreate(dateProvider.getCurrentTime());
			dispatchPlan.setBatchId(dispatchPlanBatch.getId());
			dispatchPlan.setStatusPlan(Constant.STATUSPLAN_1);
			dispatchPlan.setStatusSync(Constant.STATUS_SYNC_0);
			dispatchPlan.setIsDel(Constant.IS_DEL_0);
			dispatchPlan.setReplayType(Constant.REPLAY_TYPE_0);
			dispatchPlan.setIsTts(Constant.IS_TTS_0);
			dispatchPlanMapper.insert(dispatchPlan);

			// String[] hours = dispatchPlan.getCallHour().split(",");
			// // 写入时间dispatchHour
			// for (String hr : hours) {
			// DispatchHour hour = new DispatchHour();
			// hour.setDispatchId(dispatchPlan.getPlanUuid());
			// hour.setGmtCreate(dateProvider.getCurrentTime());
			// hour.setIsCall(Constant.ISCALL_0);
			// hour.setHour(Integer.valueOf(hr));
			// dispatchHourMapper.insert(hour);
			// }
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
		logger.info("回调完成通知planUuid:" + planUuid);
		DispatchPlanExample ex = new DispatchPlanExample();
		ex.createCriteria().andPlanUuidEqualTo(planUuid);
		List<DispatchPlan> list = dispatchPlanMapper.selectByExample(ex);
		logger.info("回调完成通知查询结果:" + list.size());
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
					dispatchPlan.getLine(), new DispatchPlan());
			if (queryAvailableSchedules.size() <= 0) {
				String key = dispatchPlan.getUserId() + "-" + dispatchPlan.getRobot() + "-" + dispatchPlan.getLine();
				redisUtil.del(key);
				logger.info("当前计划没有下一批任务:" + key + "删除了redis缓存");
			}

		}

		return false;
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
		logger.info("queryDispatchPlanByParams isSuperAdmin:" + isSuperAdmin);
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
		getBatchNames(selectByExample);
		int count = dispatchPlanMapper.countByExample(example);
		page.setRecords(selectByExample);
		page.setTotal(count);
		return page;
	}

	private void getBatchNames(List<DispatchPlan> selectByExample) {
		DispatchPlanBatchExample ex = new DispatchPlanBatchExample();
		List<Integer> ids = new ArrayList<>();
		for (DispatchPlan dispatchPlan : selectByExample) {
			ids.add(dispatchPlan.getBatchId());
		}
		ex.createCriteria().andIdIn(ids);

		if (ids.size() > 0) {
			List<DispatchPlanBatch> Batch = dispatchPlanBatchMapper.selectByExample(ex);

			for (DispatchPlanBatch batchBean : Batch) {
				for (DispatchPlan plan : selectByExample) {
					if (batchBean.getId().equals(plan.getBatchId())) {
						plan.setBatchName(batchBean.getName());
					}
				}
			}
		}
	}

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
			DispatchPlan isSuccess) {
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

		List<String> list = new ArrayList<>();
		Object object2 = redisUtil.get("robotId");
		System.out.println(object2);
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

		// // 同步状态;0未同步1已同步
		for (DispatchPlan dispatchPlan : phones) {
			dispatchPlan.setStatusSync(Constant.STATUS_SYNC_1);
			dispatchPlanMapper.updateByPrimaryKeySelective(dispatchPlan);
		}
		// 判断当前任务是否查询完毕
		int count = dispatchPlanMapper.countByExample(ex);
		if (count <= requestCount) {
			isSuccess.setSuccess(false);
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

		List<DispatchPlan> phones = dispatchPlanMapper.selectByCallHour(dis);
		// // 根据日期查询号码
		// DispatchPlanExample ex = new DispatchPlanExample();
		// ex.createCriteria().andCallDataEqualTo(Integer.valueOf(dateNowStr)).andIsDelEqualTo(Constant.IS_DEL_0)
		// .andStatusPlanEqualTo(Constant.STATUSPLAN_1).andStatusSyncEqualTo(Constant.STATUS_SYNC_0);
		// List<DispatchPlan> phones = dispatchPlanMapper.selectByExample(ex);
		// List<DispatchHour> hourList = new ArrayList<>();
		// // 遍历当前符合日期号码
		// for (DispatchPlan plan : phones) {
		// DispatchHourExample hourEx = new DispatchHourExample();
		// // 查询拨打时间
		// hourEx.createCriteria().andHourEqualTo(hour).andDispatchIdEqualTo(plan.getPlanUuid());
		// hourList = dispatchHourMapper.selectByExample(hourEx);
		// }
		// List<String> ids = new ArrayList<>();
		// for (DispatchHour bean : hourList) {
		// ids.add(String.valueOf(bean.getDispatchId()));
		// }
		//
		// List<DispatchPlan> selectByExample = new ArrayList<>();
		// if (ids.size() > 0) {
		// DispatchPlanExample ex1 = new DispatchPlanExample();
		// ex1.createCriteria().andPlanUuidIn(ids);
		// selectByExample = dispatchPlanMapper.selectByExample(ex1);
		// }
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
				dis.setGmtModified(dateProvider.getCurrentTime());
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
	public boolean operationAllPlanByBatchId(Integer batchId, String status, Long userId) {
		logger.info("operationAllPlanByBatchId userId:" + userId);
		int result = -1;
		if (batchId != 0) {
			// 不选择传入0
			DispatchPlanBatch dispatchPlanBatch = dispatchPlanBatchMapper.selectByPrimaryKey(batchId);
			DispatchPlan dispatchPlan = new DispatchPlan();
			dispatchPlan.setBatchId(dispatchPlanBatch.getId());
			dispatchPlan.setStatusPlan(Integer.valueOf(status));
			result = dispatchPlanMapper.updateByExampleSelective(dispatchPlan, new DispatchPlanExample());
		} else {
			DispatchPlan dis = new DispatchPlan();
			dis.setStatusPlan(Integer.valueOf(status));
			DispatchPlanExample example = new DispatchPlanExample();
			example.createCriteria().andUserIdEqualTo(userId.intValue());
			dispatchPlanMapper.updateByExampleSelective(dis, example);
		}
		return result > 0 ? true : false;
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
}
