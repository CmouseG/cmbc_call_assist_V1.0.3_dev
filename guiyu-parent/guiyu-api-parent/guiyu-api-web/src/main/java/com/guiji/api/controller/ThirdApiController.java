package com.guiji.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.guiji.auth.api.IApiLogin;
import com.guiji.component.result.Result.ReturnData;
import com.guiji.dispatch.api.IThirdApiOut;
import com.guiji.dispatch.model.DispatchPlanList;

@RestController
public class ThirdApiController {
	@Autowired
	private IApiLogin apiLogin;
	@Autowired
	private IThirdApiOut thirdApi;

	/**
	 * 查询通话记录
	 * 
	 * @param accessToken
	 * @param callId
	 * @return
	 */
	@GetMapping("/getCalldetail")
	public JSONObject getCalldetail(@RequestParam(required = false, name = "phone") String phone,
			@RequestParam(required = false, name = "batch_number") String batchNumber,
			@RequestParam(required = true, name = "pagenum") int pagenum,
			@RequestParam(required = true, name = "pagesize") int pagesize) {
		return thirdApi.getCalldetail(phone, batchNumber, pagenum, pagesize);
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
		return thirdApi.getcall4BatchName(batchNumber);
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
		jsonObject.put("data", thirdApi.queryDispatchPlan(batchNumber, pagenum, pagesize));
		return jsonObject;
	}

	/**
	 * 获取token
	 * 
	 * @param access_key
	 * @param secret_key
	 * @return
	 */
	@GetMapping("/getToken")
	public JSONObject getToken(@RequestParam(required = true, name = "access_key") String access_key,
			@RequestParam(required = true, name = "secret_key") String secret_key) {
		JSONObject jsonObject = new JSONObject();
		ReturnData<String> apiLogin2 = apiLogin.apiLogin(access_key, secret_key);
		jsonObject.put("token", apiLogin2.getBody());
		return jsonObject;
	}

	/**
	 * 添加任务
	 * 
	 * @param dispatchPlanList
	 * @return
	 */
	@PostMapping("/addPhones")
	public JSONObject addPhones(@RequestBody DispatchPlanList dispatchPlanList) {
		return thirdApi.insertDispatchPlanList(dispatchPlanList);
	}

}
