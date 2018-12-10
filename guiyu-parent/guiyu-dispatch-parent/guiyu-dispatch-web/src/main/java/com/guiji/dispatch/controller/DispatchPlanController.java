package com.guiji.dispatch.controller;

import java.io.IOException;
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

import com.alibaba.fastjson.JSONObject;
import com.guiji.ccmanager.entity.LineConcurrent;
import com.guiji.common.model.Page;
import com.guiji.dispatch.bean.IdsDto;
import com.guiji.dispatch.bean.MessageDto;
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
	public MessageDto addSchedule(@RequestBody DispatchPlan dispatchPlan, @RequestHeader Long userId) {
		MessageDto dto = new MessageDto();
		try {
			dto = dispatchPlanService.addSchedule(dispatchPlan, userId);
		} catch (Exception e) {
			logger.error("error", e);
		}
		return dto;
	}

	/**
	 * 查询任务
	 * 
	 * @return
	 */
	@PostMapping("queryDispatchPlanBatch")
	@Log(info = "查询批量信息")
	public List<DispatchPlanBatch> queryDispatchPlanBatch(@RequestHeader Long userId,
			@RequestHeader Boolean isSuperAdmin) {
		return dispatchPlanService.queryDispatchPlanBatch(userId, isSuperAdmin);

	}

	/**
	 * 文件上传
	 * 
	 * @param fileName
	 * @param file
	 * @return
	 */
	@Log(info = "文件上传")
	@PostMapping("batchImport")
	public MessageDto batchImport(@RequestParam("file") MultipartFile file, @RequestHeader Long userId,
			@RequestParam(required = true, name = "dispatchPlan") String dispatchPlan) {
		logger.info("batchImport start");
		String fileName = file.getOriginalFilename();
		MessageDto batchImport = new MessageDto();

		try {
			dispatchPlanService.batchImport(fileName, userId, file, dispatchPlan);
		} catch (Exception e) {
			batchImport.setResult(false);
			batchImport.setMsg(e.getMessage());
			logger.error("error", e);
		}

		return batchImport;

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
	@Log(info = "查询任务计划")
	public Page<DispatchPlan> queryDispatchPlanByParams(@RequestParam(required = false, name = "phone") String phone,
			@RequestParam(required = false, name = "planStatus") String planStatus,
			@RequestParam(required = false, name = "startTime") String startTime,
			@RequestParam(required = false, name = "endTime") String endTime,
			@RequestParam(required = false, name = "batchId") Integer batchId,
			@RequestParam(required = false, name = "replayType") String replayType,
			@RequestParam(required = true, name = "pagenum") int pagenum,
			@RequestParam(required = true, name = "pagesize") int pagesize, @RequestHeader Long userId,
			@RequestHeader Boolean isSuperAdmin) {
		return dispatchPlanService.queryDispatchPlanByParams(phone, planStatus, startTime, endTime, batchId, replayType,
				pagenum, pagesize, userId, isSuperAdmin);
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
	@Log(info = "一键修改状态")
	public MessageDto operationAllPlanByBatchId(@RequestParam(required = true, name = "batchId") Integer batchId,
			@RequestParam(required = true, name = "status") String status, @RequestHeader Long userId) {
		return dispatchPlanService.operationAllPlanByBatchId(batchId, status, userId);
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
	 * 批量加入
	 * 
	 * @param batchId
	 * @return
	 */
	@PostMapping("batchInsertDisplanPlan")
	public boolean batchInsertDisplanPlan(@RequestBody DispatchPlan[] dispatchPlans, @RequestHeader Long userId) {
		boolean result = false;
		for (DispatchPlan dis : dispatchPlans) {
			try {
				dispatchPlanService.addSchedule(dis, userId);
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}
		return result;
	}

	@PostMapping("checkBatchName")
	public boolean checkBatchName(@RequestParam(required = true, name = "batchName") String batchName) {
		return dispatchPlanService.checkBatchId(batchName);
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

	// 累计任务号码总数，累计拨打号码总数，最后计划日期，最后拨打日期，累计服务天数
	@PostMapping("getServiceStatistics")
	public JSONObject getServiceStatistics(@RequestHeader Long userId, @RequestHeader Boolean isSuperAdmin) {
		if (!isSuperAdmin) {
			userId = 0l;
		}
		return dispatchPlanService.getServiceStatistics(userId);
	}

	/**
	 * 任务概览：批次数，任务数，拨打数
	 * 
	 * @param userId
	 * @param isSuperAdmin
	 * @return
	 */
	@PostMapping("getData")
	public JSONObject getData(@RequestParam(required = false, name = "startTime") String startTime,
			@RequestParam(required = false, name = "endTime") String endTime, @RequestHeader Long userId,
			@RequestHeader Boolean isSuperAdmin) {

		return dispatchPlanService.getServiceStatistics(userId,startTime,endTime,isSuperAdmin);
	}

}
