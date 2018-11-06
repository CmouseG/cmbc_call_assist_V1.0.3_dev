package com.guiji.dispatch.impl;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.guiji.ccmanager.api.ICallManagerOutApi;
import com.guiji.ccmanager.entity.LineConcurrent;
import com.guiji.common.model.Page;
import com.guiji.component.result.Result.ReturnData;
import com.guiji.dispatch.dao.DispatchHourMapper;
import com.guiji.dispatch.dao.DispatchPlanBatchMapper;
import com.guiji.dispatch.dao.DispatchPlanMapper;
import com.guiji.dispatch.dao.entity.DispatchHour;
import com.guiji.dispatch.dao.entity.DispatchHourExample;
import com.guiji.dispatch.dao.entity.DispatchPlan;
import com.guiji.dispatch.dao.entity.DispatchPlanBatch;
import com.guiji.dispatch.dao.entity.DispatchPlanExample;
import com.guiji.dispatch.dao.entity.DispatchPlanExample.Criteria;
import com.guiji.dispatch.service.IDispatchPlanService;
import com.guiji.dispatch.util.Constant;
import com.guiji.dispatch.util.ToolDateTime;

@Service
public class DispatchPlanServiceImpl implements IDispatchPlanService {

	@Autowired
	private DispatchPlanMapper dispatchPlanMapper;

	@Autowired
	private DispatchPlanBatchMapper dispatchPlanBatchMapper;

	@Autowired
	private ICallManagerOutApi callManagerFeign;

	@Autowired
	private DispatchHourMapper dispatchHourMapper;

	@Override
	public boolean addSchedule(DispatchPlan dispatchPlan) {
		int result = dispatchPlanMapper.insert(dispatchPlan);
		
		//调用机器人中心判断参数是否合法，如果合法则status_plan =1 否则为0  然后入库
		String[] hours = dispatchPlan.getCallHour().split(",");

		// 写入时间dispatchHour
		for (String hour : hours) {
			DispatchHour dispatchHour = new DispatchHour();
			try {
				dispatchHour.setGmtCreate(ToolDateTime.getCurrentTime());
			} catch (Exception e) {
				e.printStackTrace();
			}
			dispatchHour.setDispatchId(dispatchPlan.getId());
			dispatchHour.setHour(Integer.valueOf(hour));
			// 未拨打
			dispatchHour.setIsCall(0);
			dispatchHourMapper.insert(dispatchHour);
		}

		// 针对时间做处理 20180910
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
		ex.createCriteria().andUserIdEqualTo(userId);
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
			// 暂时所有的加入的手机号的任务
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
	public boolean batchImport(String fileName, MultipartFile file) {
		return false;
	}

	@Override
	public boolean successSchedule(String planUuid) {
		//查询
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
	public List<DispatchPlan> queryDispatchOutParams(Integer userId, int requestCount, int lineId) {
		DispatchPlanExample ex = new DispatchPlanExample();
		ex.createCriteria().andUserIdEqualTo(userId).andLineEqualTo(lineId);
		return null;
	}

	@Override
	public List<LineConcurrent> outLineinfos(String userId) {
		ReturnData<List<LineConcurrent>> outLineinfos = callManagerFeign.outLineinfos(userId);
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
				.andStatusSyncEqualTo(Constant.status_sync_0).andStatusPlanEqualTo(Constant.status_sync_1);
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
	public 	List<DispatchPlan> selectPhoneByDate() {
		Date d = new Date();  
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");  
        String dateNowStr = sdf.format(d);  
   	 	Calendar now = Calendar.getInstance();  
   	 	int hour = now.get(Calendar.HOUR_OF_DAY);
   	 	
   	 	//根据日期查询号码
   	 	DispatchPlanExample ex = new  DispatchPlanExample();
   	 	ex.createCriteria().andCallDataEqualTo(Integer.valueOf(dateNowStr));
   	 	List<DispatchPlan> phones = dispatchPlanMapper.selectByExample(ex);
   		List<DispatchHour> hourList = new ArrayList<>();
   	 	for(DispatchPlan plan : phones){
   	 		DispatchHourExample hourEx = new DispatchHourExample();
	 		hourEx.createCriteria().andHourEqualTo(hour).andDispatchIdEqualTo(plan.getId());
	 		hourList = dispatchHourMapper.selectByExample(hourEx);
 		}
 		List<Integer> ids = new ArrayList<>();
 		for(DispatchHour bean : hourList){
 			ids.add(bean.getDispatchId());
 		}
 		
 		List<DispatchPlan> selectByExample = new ArrayList<>();
 		if(ids.size()>0){
 			DispatchPlanExample ex1 = 	new DispatchPlanExample();
 	 		ex1.createCriteria().andIdIn(ids);
 	 		selectByExample = dispatchPlanMapper.selectByExample(ex1);
 		}
		return selectByExample;
	}

}
