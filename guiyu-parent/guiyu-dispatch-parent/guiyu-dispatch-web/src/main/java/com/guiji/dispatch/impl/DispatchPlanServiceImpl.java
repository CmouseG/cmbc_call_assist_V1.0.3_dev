package com.guiji.dispatch.impl;

import java.io.InputStream;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
import com.guiji.dispatch.dao.DispatchHourMapper;
import com.guiji.dispatch.dao.DispatchPlanBatchMapper;
import com.guiji.dispatch.dao.DispatchPlanMapper;
import com.guiji.dispatch.dao.entity.DispatchHour;
import com.guiji.dispatch.dao.entity.DispatchHourExample;
import com.guiji.dispatch.dao.entity.DispatchPlan;
import com.guiji.dispatch.dao.entity.DispatchPlanBatch;
import com.guiji.dispatch.dao.entity.DispatchPlanBatchExample;
import com.guiji.dispatch.dao.entity.DispatchPlanExample;
import com.guiji.dispatch.dao.entity.DispatchPlanExample.Criteria;
import com.guiji.dispatch.service.IDispatchPlanService;
import com.guiji.dispatch.util.Constant;
import com.guiji.dispatch.util.DateProvider;
import com.guiji.dispatch.util.ToolDateTime;
import com.guiji.utils.IdGenUtil;

@Service
public class DispatchPlanServiceImpl implements IDispatchPlanService {

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
//	@Autowired
//	private IAuth authService;

	@Override
	public boolean addSchedule(DispatchPlan dispatchPlan,Long userId) throws Exception {
		DispatchPlanBatch dispatchPlanBatch = new DispatchPlanBatch();
		dispatchPlanBatch.setName(dispatchPlan.getBatchName());
		//通知状态;通知状态1等待2失败3成功
		dispatchPlanBatch.setStatusNotify(Constant.STATUSNOTIFY_0);
		dispatchPlanBatch.setGmtModified(dateProvider.getCurrentTime());
		dispatchPlanBatch.setGmtCreate(dateProvider.getCurrentTime());
		dispatchPlanBatch.setStatusShow(dispatchPlan.getStatusShow());
		dispatchPlanBatch.setUserId(userId.intValue());
		dispatchPlanBatchMapper.insert(dispatchPlanBatch);			
		
		
		dispatchPlan.setPlanUuid(IdGenUtil.uuid());
		dispatchPlan.setGmtModified(dateProvider.getCurrentTime());
		dispatchPlan.setGmtCreate(dateProvider.getCurrentTime());
		int result = dispatchPlanMapper.insert(dispatchPlan);

		// 调用机器人中心判断参数是否合法，如果合法则status_plan =1 否则为0 然后入库
		String[] hours = dispatchPlan.getCallHour().split(",");

		// 写入时间dispatchHour
		for (String hour : hours) {
			DispatchHour dispatchHour = new DispatchHour();
			try {
				dispatchHour.setGmtCreate(ToolDateTime.getCurrentTime());
			} catch (Exception e) {
				e.printStackTrace();
			}
			dispatchHour.setDispatchId(dispatchPlan.getPlanUuid());
			dispatchHour.setHour(Integer.valueOf(hour));
			// 未拨打
			dispatchHour.setIsCall(0);
			dispatchHourMapper.insert(dispatchHour);
		}

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
		List<DispatchPlan> selectByExample = dispatchPlanMapper.selectByExample(ex);
		int count = dispatchPlanMapper.countByExample(new DispatchPlanExample());
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
	public boolean batchImport(String fileName,Long userId,MultipartFile file,String str) throws Exception {
		if (!fileName.matches("^.+\\.(?i)(xls)$") && !fileName.matches("^.+\\.(?i)(xlsx)$")) {
			// throw new MyException("上传文件格式不正确");
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


		DispatchPlan dispatchPlan =	JSONObject.parseObject(str,DispatchPlan.class);
		DispatchPlanBatch dispatchPlanBatch =	JSONObject.parseObject(str,DispatchPlanBatch.class);
		dispatchPlanBatch.setGmtModified(dateProvider.getCurrentTime());
		dispatchPlanBatch.setGmtCreate(dateProvider.getCurrentTime());
		dispatchPlanBatch.setStatusNotify(Constant.STATUS_NOTIFY_0);
		dispatchPlanBatch.setUserId(userId.intValue());
			dispatchPlanBatchMapper.insert(dispatchPlanBatch);
		for (int r = 1; r <= sheet.getLastRowNum(); r++) {
			Row row = sheet.getRow(r);
			if (row == null) {
				continue;
			}
			String phone;
			if (isNull(row.getCell(0))) {
				row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
				phone = row.getCell(0).getStringCellValue();
			} else {
				throw new Exception("导入失败(第" + (r + 1) + "行,电话未填写)");
			}


			String params;
			if (isNull(row.getCell(1))) {
				row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
				params = row.getCell(1).getStringCellValue();
			} else {
				throw new Exception("导入失败(第" + (r + 1) + "行,params)");
			}

			String attach;
			if (isNull(row.getCell(2))) {
				row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
				attach = row.getCell(2).getStringCellValue();
			} else {
				throw new Exception("导入失败(第" + (r + 1) + "行,attach");
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
			dispatchPlanMapper.insert(dispatchPlan);
			
		
			String[] hours = dispatchPlan.getCallHour().split(",");
			// 写入时间dispatchHour
			for (String hr : hours) {
				DispatchHour hour = new DispatchHour();
				hour.setDispatchId(dispatchPlan.getPlanUuid());
				hour.setGmtCreate(dateProvider.getCurrentTime());
				hour.setIsCall(Constant.ISCALL_0);
				hour.setHour(Integer.valueOf(hr));
				dispatchHourMapper.insert(hour);
			}
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
		// 查询
		DispatchPlanExample ex = new DispatchPlanExample();
		ex.createCriteria().andPlanUuidEqualTo(planUuid);
		List<DispatchPlan> selectByExample = dispatchPlanMapper.selectByExample(ex);
		
		
		
		return false;
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
		example.createCriteria().andBatchIdEqualTo(batchId);
		List<DispatchPlan> selectByExample = dispatchPlanMapper.selectByExample(example);
		int count = dispatchPlanMapper.countByExample(new DispatchPlanExample());
		page.setRecords(selectByExample);
		page.setTotal(count);
		return page;
	}

	@Override
	public Page<DispatchPlan> queryDispatchPlanByParams(String phone, String planStatus, String startTime,
			String endTime, int pagenum, int pagesize) {
		Page<DispatchPlan> page = new Page<>();
		page.setPageNo(pagenum);
		page.setPageSize((pagesize));
		DispatchPlanExample example = new DispatchPlanExample();
		example.setLimitStart((pagenum - 1) * pagesize);
		example.setLimitEnd(pagesize);
		Criteria createCriteria = example.createCriteria();
		if (planStatus != null && planStatus != "") {
			createCriteria.andStatusPlanEqualTo(Integer.valueOf(planStatus));
		}
		if (startTime != null && startTime != "" && endTime != null && endTime != "") {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				createCriteria.andGmtCreateBetween(new Timestamp(sdf.parse(startTime).getTime()),
						new Timestamp(sdf.parse(endTime).getTime()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		List<DispatchPlan> selectByExample = dispatchPlanMapper.selectByExample(example);
		int count = dispatchPlanMapper.countByExample(new DispatchPlanExample());
		page.setRecords(selectByExample);
		page.setTotal(count);
		return page;
	}


	@Override
	public List<LineConcurrent> outLineinfos(String userId) {
		ReturnData<List<LineConcurrent>> outLineinfos = callManagerFeign.getLineInfos(userId);
		return outLineinfos.getBody();
	}

	@Override
	public List<DispatchPlan> queryAvailableSchedules(Integer userId, int requestCount, int lineId) {
		DispatchPlanExample example = new DispatchPlanExample();
		if (requestCount != 0) {
			example.setLimitStart((requestCount - 1) * 1);
			example.setLimitEnd(requestCount);
		}
		// 同步状态;0未同步1已同步
		example.createCriteria().andUserIdEqualTo(userId).andLineEqualTo(lineId)
				.andStatusSyncEqualTo(Constant.STATUS_SYNC_0).andStatusPlanEqualTo(Constant.STATUS_SYNC_1);
		example.setOrderByClause("`gmt_create` DESC");
		List<DispatchPlan> selectByExample = dispatchPlanMapper.selectByExample(example);
		// //修改同步状态
		// for(DispatchPlan dispatchPlan : selectByExample){
		// dispatchPlan.setStatusSync(Constant.status_sync_1);
		// dispatchPlanMapper.updateByPrimaryKeySelective(dispatchPlan);
		// }
		return selectByExample;
	}

	@Override
	public List<DispatchPlan> selectPhoneByDate() {
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String dateNowStr = sdf.format(d);
		Calendar now = Calendar.getInstance();
		int hour = now.get(Calendar.HOUR_OF_DAY);

		// 根据日期查询号码
		DispatchPlanExample ex = new DispatchPlanExample();
		ex.createCriteria().andCallDataEqualTo(Integer.valueOf(dateNowStr));
		List<DispatchPlan> phones = dispatchPlanMapper.selectByExample(ex);
		List<DispatchHour> hourList = new ArrayList<>();
		for (DispatchPlan plan : phones) {
			DispatchHourExample hourEx = new DispatchHourExample();
			hourEx.createCriteria().andHourEqualTo(hour).andDispatchIdEqualTo(plan.getPlanUuid());
			hourList = dispatchHourMapper.selectByExample(hourEx);
		}
		List<String> ids = new ArrayList<>();
		for (DispatchHour bean : hourList) {
			ids.add(String.valueOf(bean.getDispatchId()));
		}

		List<DispatchPlan> selectByExample = new ArrayList<>();
		if (ids.size() > 0) {
			DispatchPlanExample ex1 = new DispatchPlanExample();
			ex1.createCriteria().andPlanUuidIn(ids);
			selectByExample = dispatchPlanMapper.selectByExample(ex1);
		}
		return selectByExample;
	}

	@Override
	public boolean deleteSchedule(String planUuid) {	
		DispatchPlanExample ex = new DispatchPlanExample();
		ex.createCriteria().andPlanUuidEqualTo(planUuid);
		int result = dispatchPlanMapper.deleteByExample(ex);
		return result > 0 ? true : false;
	}

	@Override
	public boolean batchUpdatePlans(IdsDto[] dto) {
		int  result = 0;
		for(IdsDto bean : dto){
			DispatchPlan dis = new DispatchPlan();
			dis.setPlanUuid(bean.getPlanUUID());
			dis.setStatusPlan(Integer.valueOf(bean.getStatus()));
			try {
				dis.setGmtModified(dateProvider.getCurrentTime());
			} catch (Exception e) {
				e.printStackTrace();
			}
			result = dispatchPlanMapper.updateByExampleSelective(dis, new DispatchPlanExample());
		}
		return result > 0 ? true : false;
	}

	@Override
	public boolean operationAllPlanByBatchId(Integer batchId,String status) {
		DispatchPlanBatch dispatchPlanBatch = dispatchPlanBatchMapper.selectByPrimaryKey(batchId);
		
		DispatchPlan dispatchPlan = new DispatchPlan();
		dispatchPlan.setBatchId(dispatchPlanBatch.getId());
		dispatchPlan.setStatusPlan(Integer.valueOf(status));
		int result = dispatchPlanMapper.updateByExampleSelective(dispatchPlan, new DispatchPlanExample());
		return result > 0 ? true : false;
	}

	@Override
	public boolean batchDeletePlans(IdsDto[] dto) {
		int  result =0;
		for(IdsDto bean : dto){
			DispatchPlanExample ex = new DispatchPlanExample();
			ex.createCriteria().andPlanUuidEqualTo(bean.getPlanUUID());
			result = dispatchPlanMapper.deleteByExample(ex);
			
			DispatchHourExample exHour = new DispatchHourExample();
			exHour.createCriteria().andDispatchIdEqualTo(bean.getPlanUUID());
			result =dispatchHourMapper.deleteByExample(exHour);
		}
		return result > 0 ? true : false;
	}
}
