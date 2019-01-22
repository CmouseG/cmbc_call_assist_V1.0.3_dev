package com.guiji.api.controller;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.guiji.cloud.api.ILogin;
import com.guiji.common.model.Page;
import com.guiji.component.result.Result.ReturnData;
import com.guiji.dispatch.api.IThirdApiOut;
import com.guiji.dispatch.model.CallPlanDetailRecordVO;
import com.guiji.dispatch.model.DispatchPlanApi;
import com.guiji.dispatch.model.DispatchPlanList;
import com.guiji.dispatch.model.PlanCallInfoCount;
import com.guiji.dispatch.model.PlanResultInfo;

@RestController
public class ThirdApiController {

	@Autowired
	private IThirdApiOut thirdApi;
	@Autowired
	private ILogin login;

	/**
	 * 查询通话记录
	 * 
	 * @param accessToken
	 * @param callId
	 * @return
	 */
	@GetMapping("/getCalldetail")
	public JSONObject getCalldetail(@RequestParam(required = true, name = "phone") String phone,
			@RequestParam(required = true, name = "batch_number") String batchNumber,
			@RequestParam(required = true, name = "pagenum") int pagenum,
			@RequestParam(required = true, name = "pagesize") int pagesize) {
		JSONObject jsonObject = new JSONObject();
		if (!isInteger(phone)) {
			jsonObject.put("data", "当前手机号码不正确");
			return jsonObject;
		}
		if (pagesize > 100) {
			jsonObject.put("data", "当前pagesize分页过大");
			return jsonObject;
		}
		if (pagenum <= 0) {
			jsonObject.put("data", "当前pagenum必须大于0");
			return jsonObject;
		}
		ReturnData<Page<CallPlanDetailRecordVO>> calldetail = thirdApi.getCalldetail(phone, batchNumber, pagenum,
				pagesize);
		jsonObject.put("data", calldetail.getBody());
		return jsonObject;
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
	public JSONObject getCallInfoByBatchId(@RequestParam(required = true, name = "batch_number") String batchNumber,
			@RequestParam(required = true, name = "pagenum") int pagenum,
			@RequestParam(required = true, name = "pagesize") int pagesize) {
		JSONObject jsonObject = new JSONObject();
		if (pagesize > 100) {
			jsonObject.put("data", "当前pagesize分页过大");
			return jsonObject;
		}
		if (pagenum <= 0) {
			jsonObject.put("data", "当前pagenum必须大于0");
			return jsonObject;
		}
		ReturnData<PlanCallInfoCount> getcall4BatchName = thirdApi.getcall4BatchName(batchNumber, pagenum, pagesize);
		jsonObject.put("data", getcall4BatchName.getBody());
		return jsonObject;
	}

	/**
	 * 通过批次号查询该批次任务的号码列表
	 * 
	 * @param pagenum
	 * @param pagesize
	 * @return
	 */
	@GetMapping("/getPhonesByBatchNumber")
	public JSONObject getphonesByBatchNumber(@RequestParam(required = true, name = "batch_number") String batchNumber,
			@RequestParam(required = true, name = "pagenum") int pagenum,
			@RequestParam(required = true, name = "pagesize") int pagesize) {
		JSONObject jsonObject = new JSONObject();
		if (pagesize > 100) {
			jsonObject.put("data", "当前pagesize分页过大");
			return jsonObject;
		}
		if (pagenum <= 0) {
			jsonObject.put("data", "当前pagenum必须大于0");
			return jsonObject;
		}
		ReturnData<Page<DispatchPlanApi>> queryDispatchPlan = thirdApi.queryDispatchPlan(batchNumber, pagenum,
				pagesize);
		jsonObject.put("data", queryDispatchPlan.getBody());
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
		ReturnData<String> apiLogin2 = login.apiLogin(access_key, secret_key);
		jsonObject.put("token", apiLogin2.getBody());
		return jsonObject;
	}

	/**
	 * 第三方回调重试机制
	 * 
	 * @param access_key
	 * @param secret_key
	 * @return
	 */
	@GetMapping("/reTryThirdApi")
	public JSONObject reTryThirdApi(@RequestParam(required = true, name = "user_id") Integer userId) {
		JSONObject jsonObject = new JSONObject();
		ReturnData<Boolean> reTryThirdApi = thirdApi.reTryThirdApi(Long.valueOf(userId));
		jsonObject.put("data", reTryThirdApi.getBody());
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
		ReturnData<PlanResultInfo> insertDispatchPlanList = thirdApi.insertDispatchPlanList(dispatchPlanList);
		JSONObject json = new JSONObject();
		json.put("data", insertDispatchPlanList.getBody());
		return json;
	}

	public static boolean isInteger(String str) {
		Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
		return pattern.matcher(str).matches();
	}

}
