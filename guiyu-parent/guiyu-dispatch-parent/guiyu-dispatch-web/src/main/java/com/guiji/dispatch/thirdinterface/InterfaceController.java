//package com.guiji.dispatch.thirdinterface;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.regex.Pattern;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestHeader;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.alibaba.fastjson.JSONObject;
//import com.google.gson.JsonObject;
//import com.guiji.auth.api.IApiLogin;
//import com.guiji.auth.api.IAuth;
//import com.guiji.common.model.Page;
//import com.guiji.component.result.Result.ReturnData;
//import com.guiji.dispatch.bean.DispatchPlanList;
//import com.guiji.dispatch.dao.entity.DispatchPlan;
//import com.guiji.dispatch.service.IDispatchPlanService;
//import com.guiji.dispatch.util.Constant;
//import com.guiji.user.dao.entity.SysUser;
//import com.guiji.utils.DateUtil;
//import com.guiji.utils.IdGenUtil;
//
//import springfox.documentation.spring.web.json.Json;
//
///**
// * 第三方接口
// * 
// * @author Administrator
// *
// */
//@RestController
//public class InterfaceController {
//
//	@Autowired
//	private IDispatchPlanService dispatchPlanService;
//	@Autowired
//	private IAuth auth;
//	@Autowired
//	private IApiLogin apiLogin;
//	
//	
//	static Logger logger = LoggerFactory.getLogger(InterfaceController.class);
//
//	/**
//	 * 查询通话记录
//	 * 
//	 * @param accessToken
//	 * @param callId
//	 * @return
//	 */
//	@GetMapping("/getCalldetail")
//	public JSONObject getCalldetail(@RequestParam(required = false, name = "access_token") String accessToken,
//			@RequestParam(required = false, name = "phone") String phone,
//			@RequestParam(required = false, name = "batch_number") String batchNumber,
//			@RequestParam(required = true, name = "pagenum") int pagenum,
//			@RequestParam(required = true, name = "pagesize") int pagesize) {
//
//		return dispatchPlanService.queryDispatchPlanByPhoens(phone, batchNumber, pagenum, pagesize);
//	}
//
//	/**
//	 * 通过批次号查询该批次的拨打情况
//	 * 
//	 * @param batchNumber
//	 * @param pagenum
//	 * @param pagesize
//	 * @return
//	 */
//	@GetMapping("/getCallInfoByBatchId")
//	public JSONObject getCallInfoByBatchId(@RequestParam(required = false, name = "batch_number") String batchNumber) {
//		JSONObject jsonObject = new JSONObject();
//		int countAlready = dispatchPlanService.getcall4BatchName(batchNumber, Constant.STATUSPLAN_1);
//		int countNo = dispatchPlanService.getcall4BatchName(batchNumber, Constant.STATUSPLAN_2);
//		jsonObject.put("countAlready", countAlready);
//		jsonObject.put("countNo", countNo);
//		return jsonObject;
//	}
//
//	/**
//	 * 通过批次号查询该批次任务的号码列表
//	 * 
//	 * @param pagenum
//	 * @param pagesize
//	 * @return
//	 */
//	@GetMapping("/getphonesByBatchNumber")
//	public JSONObject getphonesByBatchNumber(@RequestParam(required = true, name = "batch_number") String batchNumber,
//			@RequestParam(required = true, name = "pagenum") int pagenum,
//			@RequestParam(required = true, name = "pagesize") int pagesize) {
//		JSONObject jsonObject = new JSONObject();
//		Page<DispatchPlan> queryDispatchPlan = dispatchPlanService.queryDispatchPlan(batchNumber, pagenum, pagesize);
//		jsonObject.put("data", queryDispatchPlan);
//		return jsonObject;
//	}
//	
////	@GetMapping("/getToken")
////	public JSONObject getToken(@RequestParam(required = true, name = "access_key") String access_key,
////			@RequestParam(required = true, name = "secret_key") String secret_key){
////		JSONObject jsonObject = new JSONObject();
////		ReturnData<String> apiLogin2 = apiLogin.apiLogin(access_key, secret_key);
////		jsonObject.put("token", apiLogin2.getBody());
////		return  jsonObject;
////	}
//	
//	
//	@GetMapping("/changeAccessKey")
//	public JSONObject changeAccessKey(@RequestHeader Long userId){
//		JSONObject jsonObject = new JSONObject();
//		ReturnData<String> changeAccessKey = auth.changeAccessKey(userId);
//		jsonObject.put("AccessKey", changeAccessKey.getBody());
//		return  jsonObject;
//	}
//	
//	
//	@GetMapping("/changeSecretKey")
//	public JSONObject changeSecretKey(@RequestHeader Long userId){
//		JSONObject jsonObject = new JSONObject();
//		 ReturnData<String> changeSecretKey = auth.changeSecretKey(userId);
//		jsonObject.put("SecretKey", changeSecretKey.getBody());
//		return  jsonObject;
//	}
//	
//	
//
//	@PostMapping("/addPhones")
//	public JSONObject addPhones(@RequestBody DispatchPlanList dispatchPlanList, @RequestHeader Long userId) {
//		JSONObject jsonObject = new JSONObject();
//		boolean result = true;
//		ReturnData<SysUser> user = auth.getUserById(userId);
//		String userName = "";
//		if (user.getBody() != null) {
//			userName = user.getBody().getUsername();
//		}
//
//		List<DispatchPlan> fails = new ArrayList<>();
//		List<DispatchPlan> succ = new ArrayList<>();
//		
//		for (DispatchPlan dis : dispatchPlanList.getMobile()) {
//			if (dis.getPhone() == null || dis.getPhone() == "" || !isInteger(dis.getPhone())) {
//				result = false;
//			}
//			if (dispatchPlanList.getRobot() == null || dispatchPlanList.getRobot() == "") {
//				result = false;
//			}
//
//			if (dispatchPlanList.getLine() == null || dispatchPlanList.getLine() == "") {
//				result = false;
//			}
//
//			if (dispatchPlanList.getIsClean() == null || dispatchPlanList.getIsClean() == "") {
//				result = false;
//			}
//
//			if (dispatchPlanList.getCallDate() == null || dispatchPlanList.getCallDate() =="") {
//				result = false;
//			}
//
//			if (dispatchPlanList.getCallHour() == null || dispatchPlanList.getCallHour() == "") {
//				result = false;
//			}
//
//			if (dispatchPlanList.getBatchName() == null || dispatchPlanList.getBatchName() == "") {
//				result = false;
//			}
//
//			if (result) {
//				dis.setPlanUuid(IdGenUtil.uuid());
//				dis.setUserId(userId.intValue());
//				dis.setUsername(userName);
//				dis.setRobot(dispatchPlanList.getRobot());
//				dis.setClean(Integer.valueOf(dispatchPlanList.getIsClean()));
//				dis.setCallHour(dispatchPlanList.getCallHour());
//				dis.setCallData(Integer.valueOf(dispatchPlanList.getCallDate()));
//				dis.setIsTts(Constant.IS_TTS_0);
//				dis.setReplayType(Constant.REPLAY_TYPE_0);
//				dis.setFlag(Constant.IS_FLAG_0);
//				
//				try {
//					dis.setGmtCreate(DateUtil.getCurrent4Time());
//					dis.setGmtModified(DateUtil.getCurrent4Time());
//				} catch (Exception e) {
//					logger.error("e", e);
//				}
//				succ.add(dis);
//			} else {
//				fails.add(dis);
//			}
//		}
//		boolean insertDispatchPlanList = dispatchPlanService.insertDispatchPlanList(succ);
//		logger.info("批量写入结果 ： "+ insertDispatchPlanList);
//		jsonObject.put("succSize", succ.size());
//		jsonObject.put("failSize", fails.size());
//		return jsonObject;
//	}
//
//	public static boolean isInteger(String str) {
//		Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
//		return pattern.matcher(str).matches();
//	}
//	
//
//}
