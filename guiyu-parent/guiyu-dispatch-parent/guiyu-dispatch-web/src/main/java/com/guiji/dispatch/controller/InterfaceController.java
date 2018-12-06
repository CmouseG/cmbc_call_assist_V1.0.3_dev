package com.guiji.dispatch.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.guiji.common.model.Page;
import com.guiji.dispatch.dao.entity.DispatchPlan;
import com.guiji.dispatch.service.IDispatchPlanService;
import com.guiji.dispatch.util.Constant;

/**
 * 第三方接口
 * 
 * @author Administrator
 *
 */
@RestController
public class InterfaceController {

	@Autowired
	private IDispatchPlanService dispatchPlanService;

	static Logger logger = LoggerFactory.getLogger(InterfaceController.class);

	/**
	 * 查询通话记录
	 * 
	 * @param accessToken
	 * @param callId
	 * @return
	 */
	@GetMapping("/getCalldetail")
	public JSONObject getCalldetail(@RequestParam(required = false, name = "access_token") String accessToken,
			@RequestParam(required = false, name = "phone") String phone,
			@RequestParam(required = false, name = "batch_number") String batchNumber,
			@RequestParam(required = true, name = "pagenum") int pagenum,
			@RequestParam(required = true, name = "pagesize") int pagesize) {
		// 校验tocken

		return dispatchPlanService.queryDispatchPlanByPhoens(phone, batchNumber, pagenum, pagesize);
	}

	/**
	 * 通过批次号查询该批次的拨打情况
	 * 
	 * @param batchNumber
	 * @param pagenum
	 * @param pagesize
	 * @return
	 */
	@GetMapping("/getCallInfoByBatchId")
	public JSONObject getCallInfoByBatchId(@RequestParam(required = false, name = "batch_number") String batchNumber) {
		JSONObject jsonObject = new JSONObject();
		int countAlready = dispatchPlanService.getcall4BatchName(batchNumber, Constant.STATUSPLAN_1);
		int countNo = dispatchPlanService.getcall4BatchName(batchNumber, Constant.STATUSPLAN_2);
		jsonObject.put("countAlready", countAlready);
		jsonObject.put("countNo", countNo);
		return jsonObject;
	}

	/**
	 * 通过批次号查询该批次任务的号码列表
	 * 
	 * @param pagenum
	 * @param pagesize
	 * @return
	 */
	@GetMapping("/getphonesByBatchNumber")
	public JSONObject getphonesByBatchNumber(@RequestParam(required = true, name = "batch_number") String batchNumber,
			@RequestParam(required = true, name = "pagenum") int pagenum,
			@RequestParam(required = true, name = "pagesize") int pagesize) {
		JSONObject jsonObject = new JSONObject();
		Page<DispatchPlan> queryDispatchPlan = dispatchPlanService.queryDispatchPlan(batchNumber, pagenum, pagesize);
		jsonObject.put("data", queryDispatchPlan);
		return jsonObject;
	}
	
	
	
	

}
