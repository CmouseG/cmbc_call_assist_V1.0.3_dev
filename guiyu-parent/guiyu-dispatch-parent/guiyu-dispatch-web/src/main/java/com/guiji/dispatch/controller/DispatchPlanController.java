package com.guiji.dispatch.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.guiji.ccmanager.entity.LineConcurrent;
import com.guiji.common.model.Page;
import com.guiji.component.result.Result;
import com.guiji.dispatch.bean.IdsDto;
import com.guiji.dispatch.dao.entity.DispatchPlan;
import com.guiji.dispatch.dao.entity.DispatchPlanBatch;
import com.guiji.dispatch.service.IDispatchPlanService;
import com.guiji.dispatch.util.Log;

@RestController
public class DispatchPlanController {
	static Logger logger = LoggerFactory.getLogger(DispatchPlanController.class);
	@Autowired
	private IDispatchPlanService dispatchPlanService;

	/**
	 * 单个导入任务
	 * 
	 * @param schedule
	 *            任务
	 * @return 响应报文 异常
	 */
	@PostMapping("addSchedule")
	@Log(info = "单个导入任务")
	public boolean addSchedule(@RequestBody DispatchPlan dispatchPlan, @RequestHeader Long userId) {
		// Long userId = 1l;
		boolean result = false;
		try {
			result = dispatchPlanService.addSchedule(dispatchPlan, userId);
		} catch (Exception e) {
		}
		return result;
	}

	
	/**
	 * 查询任务
	 * @return
	 */
	@PostMapping("queryDispatchPlanBatch")
	public List<DispatchPlanBatch> queryDispatchPlanBatch() {
		return dispatchPlanService.queryDispatchPlanBatch();

	}

	// /**
	// * 查询任务列表
	// *
	// * @param userId
	// * 用户id
	// * @return 响应报文
	// *
	// * 异常
	// */
	// @GetMapping("querySchedules")
	// Page<DispatchPlan> querySchedules(@RequestParam(required = true, name =
	// "userId") Integer userId,
	// @RequestParam(required = true, name = "pageSize") Integer pageSize,
	// @RequestParam(required = true, name = "pagenum") int pagenum) {
	// return dispatchPlanService.querySchedules(userId, pagenum, pageSize);
	// }

	// /**
	// * 暂停任务
	// *
	// * @param planUuid
	// * 任务id
	// * @return 响应报文
	// *
	// * 异常
	// */
	//
	// @PostMapping("pauseSchedule")
	// @Log(info = "暂停任务")
	// public boolean pauseSchedule(@RequestParam(required = true, name =
	// "planuuid") String planuuid) {
	// return dispatchPlanService.pauseSchedule(planuuid);
	//
	// }
	//
	// /**
	// * 取消
	// *
	// * @param planUuid
	// * 任务id
	// * @return 响应报文
	// *
	// * 异常
	// */
	// @Log(info = "取消任务")
	// @PostMapping("cancelSchedule")
	// public boolean cancelSchedule(@RequestParam(required = true, name =
	// "planuuid") String planuuid) {
	// return dispatchPlanService.cancelSchedule(planuuid);
	//
	// }

	/**
	 * 文件上传
	 * 
	 * @param fileName
	 * @param file
	 * @return
	 */
	@Log(info = "文件上传")
	@PostMapping("/batchImport")
	public Result.ReturnData batchImport(@RequestParam("file") MultipartFile file, @RequestHeader Long userId,
			@RequestParam(required = true, name = "dispatchPlan") String dispatchPlan) {
		String fileName = file.getOriginalFilename();

		boolean result = false;
		try {
			result = dispatchPlanService.batchImport(fileName, userId, file, dispatchPlan);
		} catch (Exception e) {
			// e.printStackTrace();
			return Result.error("0203001");
		}

		return Result.ok();

	}

	// /**
	// * 恢复任务
	// *
	// * @param planUuid
	// * 任务id
	// * @return 响应报文
	// *
	// */
	// @PostMapping("resumeSchedule")
	// @Log(info = "恢复任务")
	// public boolean resumeSchedule(@RequestParam(required = true, name =
	// "planuuid") String planuuid) {
	// return dispatchPlanService.resumeSchedule(planuuid);
	// }

	// @PostMapping("deleteSchedule")
	// @Log(info = "删除任务")
	// public boolean deleteSchedule(@RequestParam(required = true, name =
	// "planuuid") String planuuid) {
	// return dispatchPlanService.deleteSchedule(planuuid);
	// }

	// /**
	// * 根据批次查询号码
	// *
	// * @param batchId
	// * @return
	// */
	// @PostMapping("queryDispatchPlanByBatchId")
	// @Log(info = "根据批次查询号码")
	// public Page<DispatchPlan> queryDispatchPlanByBatchId(
	// @RequestParam(required = true, name = "batchId") Integer batchId,
	// @RequestParam(required = true, name = "pagenum") int pagenum,
	// @RequestParam(required = true, name = "pagesize") int pagesize) {
	// return dispatchPlanService.queryDispatchPlanByBatchId(batchId, pagenum,
	// pagesize);
	// }

	// /**
	// * 根据用户状态操作号码
	// *
	// * @param userId
	// * @param status
	// * @return
	// */
	// @PostMapping("OperationAllDispatchByUserId")
	// @Log(info = "根据用户状态操作号码")
	// public boolean OperationAllDispatchByUserId(@RequestParam(required =
	// true, name = "userId") Integer userId,
	// @RequestParam(required = true, name = "status") Integer status) {
	// return dispatchPlanService.OperationAllDispatchByUserId(userId, status);
	// }

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
	public Page<DispatchPlan> queryDispatchPlanByParams(@RequestParam(required = false, name = "phone") String phone,
			@RequestParam(required = false, name = "planStatus") String planStatus,
			@RequestParam(required = false, name = "startTime") String startTime,
			@RequestParam(required = false, name = "endTime") String endTime,
			@RequestParam(required = false, name = "batchId") Integer batchId,
			@RequestParam(required = false, name = "replayType") Integer replayType,
			@RequestParam(required = true, name = "pagenum") int pagenum,
			@RequestParam(required = true, name = "pagesize") int pagesize) {
		return dispatchPlanService.queryDispatchPlanByParams(phone, planStatus, startTime, endTime, batchId, replayType,
				pagenum, pagesize);
	}

	/**
	 * 批量修改任务状态
	 * 
	 * @param dto
	 * @return
	 */
	@PostMapping("batchUpdatePlans")
	public boolean batchUpdatePlans(@RequestBody IdsDto[] dto) {
		return dispatchPlanService.batchUpdatePlans(dto);
	}

	/**
	 * 一键修改状态
	 * 
	 * @param batchId
	 * @return
	 */
	@PostMapping("operationAllPlanByBatchId")
	public boolean operationAllPlanByBatchId(@RequestParam(required = true, name = "batchId") Integer batchId,
			@RequestParam(required = true, name = "status") String status) {
		return dispatchPlanService.operationAllPlanByBatchId(batchId, status);
	}

	/**
	 * 一键删除状态
	 * 
	 * @param batchId
	 * @return
	 */
	@PostMapping("deleteAllPlanByBatchId")
	public boolean deleteAllPlanByBatchId(@RequestBody IdsDto[] dto) {
		return dispatchPlanService.batchDeletePlans(dto);
	}

	/**
	 * 获取客户线路列表
	 * 
	 * @param userId
	 * @return
	 */
	@PostMapping("outLineinfos")
	@Log(info = "获取客户线路列表")
	public List<LineConcurrent> outLineinfos(@RequestHeader Long userId) {
		return dispatchPlanService.outLineinfos(String.valueOf(userId));
	}
}
