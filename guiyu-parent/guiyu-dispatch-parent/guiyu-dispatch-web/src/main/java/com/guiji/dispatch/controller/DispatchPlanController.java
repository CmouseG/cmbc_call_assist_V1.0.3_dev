package com.guiji.dispatch.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.guiji.ccmanager.entity.LineConcurrent;
import com.guiji.common.model.Page;
import com.guiji.dispatch.dao.entity.DispatchPlan;
import com.guiji.dispatch.service.IDispatchPlanService;
import com.guiji.dispatch.util.Log;

@RestController
public class DispatchPlanController {

	@Autowired
	private IDispatchPlanService dispatchPlanService;

	/**
	 * 单个导入
	 * @param schedule
	 *            任务
	 * @return 响应报文
	 * @throws Exception
	 *             异常
	 */
	@PostMapping("addSchedule")
	@Log(info = "单个导入")
	public boolean addSchedule(@RequestBody DispatchPlan dispatchPlan) {
		return dispatchPlanService.addSchedule(dispatchPlan);
	}

	/**
	 * 查询任务列表
	 * 
	 * @param userId
	 *            用户id
	 * @return 响应报文
	 * @throws Exception
	 *             异常
	 */
	@GetMapping("querySchedules")
	@Log(info = "查询任务列表")
	Page<DispatchPlan> querySchedules(@RequestParam(required = true, name = "userId") Integer userId,
			@RequestParam(required = true, name = "pageSize") Integer pageSize,
			@RequestParam(required = true, name = "pagenum") int pagenum) {
		return dispatchPlanService.querySchedules(userId, pagenum, pageSize);
	}

	/**
	 * 暂停任务
	 * 
	 * @param planUuid
	 *            任务id
	 * @return 响应报文
	 * @throws Exception
	 *             异常
	 */

	@PostMapping("pauseSchedule")
	@Log(info = "暂停任务")
	public boolean pauseSchedule(@RequestParam(required = true, name = "planuuid") String planuuid) {
		return dispatchPlanService.pauseSchedule(planuuid);

	}

	/**
	 * 取消
	 * 
	 * @param planUuid
	 *            任务id
	 * @return 响应报文
	 * @throws Exception
	 *             异常
	 */
	@Log(info = "取消任务")
	@PostMapping("cancelSchedule")
	public boolean cancelSchedule(@RequestParam(required = true, name = "planuuid") String planuuid) {
		return dispatchPlanService.cancelSchedule(planuuid);

	}

	/**
	 * 文件上传
	 * 
	 * @param fileName
	 * @param file
	 * @return
	 */
	@Log(info = "文件上传")
	public boolean batchImport(String fileName, MultipartFile file) {
		return false;
	}

	/**
	 * 恢复任务
	 *
	 * @param planUuid
	 *            任务id
	 * @return 响应报文
	 * @throws Exception
	 *             异常
	 */
	@PostMapping("resumeSchedule")
	@Log(info = "恢复任务")
	public boolean resumeSchedule(@RequestParam(required = true, name = "planuuid") String planuuid) {
		return dispatchPlanService.resumeSchedule(planuuid);
	}

	/**
	 * 根据批次查询号码
	 * 
	 * @param batchId
	 * @return
	 */
	@PostMapping("queryDispatchPlanByBatchId")
	@Log(info = "根据批次查询号码")
	public Page<DispatchPlan> queryDispatchPlanByBatchId(
			@RequestParam(required = true, name = "batchId") Integer batchId,
			@RequestParam(required = true, name = "pagenum") int pagenum,
			@RequestParam(required = true, name = "pagesize") int pagesize) {
		return dispatchPlanService.queryDispatchPlanByBatchId(batchId, pagenum, pagesize);
	}

	/**
	 * 根据用户状态操作号码
	 * 
	 * @param userId
	 * @param status
	 * @return
	 */
	@PostMapping("OperationAllDispatchByUserId")
	@Log(info = "根据用户状态操作号码")
	public boolean OperationAllDispatchByUserId(@RequestParam(required = true, name = "userId") Integer userId,
			@RequestParam(required = true, name = "status") Integer status) {
		return dispatchPlanService.OperationAllDispatchByUserId(userId, status);
	}

	/**
	 * 根据参数查询任务计划
	 * 
	 * @param phone
	 * @param planStatus
	 * @param startTime
	 * @param endTime
	 * @param pagenum
	 * @param pagesize
	 * @return
	 */
	@PostMapping("queryDispatchPlanByParams")
	@Log(info = "根据参数查询任务计划")
	public Page<DispatchPlan> queryDispatchPlanByParams(@RequestParam(required = false, name = "phone") String phone,
			@RequestParam(required = false, name = "planStatus") String planStatus,
			@RequestParam(required = false, name = "startTime") String startTime,
			@RequestParam(required = false, name = "endTime") String endTime,
			@RequestParam(required = true, name = " pagenum") int pagenum,
			@RequestParam(required = true, name = " pagesize") int pagesize) {
		return  dispatchPlanService.queryDispatchPlanByParams(phone, planStatus, startTime, endTime, pagenum, pagesize);
	}
	
	@PostMapping("outLineinfos")
	public List<LineConcurrent> outLineinfos(@RequestParam(required = true, name = "userId") String userId) {
		return dispatchPlanService.outLineinfos(userId);
	}
}
