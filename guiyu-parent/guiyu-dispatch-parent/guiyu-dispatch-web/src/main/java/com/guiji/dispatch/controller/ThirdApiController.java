package com.guiji.dispatch.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.guiji.auth.api.IApiLogin;
import com.guiji.auth.api.IAuth;
import com.guiji.ccmanager.vo.CallPlanDetailRecordVO;
import com.guiji.common.model.Page;
import com.guiji.component.result.Result.ReturnData;
import com.guiji.dispatch.api.IThirdApiOut;
import com.guiji.dispatch.dao.DispatchPlanMapper;
import com.guiji.dispatch.dao.entity.DispatchPlanExample;
import com.guiji.dispatch.model.DispatchPlan;
import com.guiji.dispatch.model.DispatchPlanApi;
import com.guiji.dispatch.model.DispatchPlanList;
import com.guiji.dispatch.model.PlanCallInfoCount;
import com.guiji.dispatch.model.PlanResultInfo;
import com.guiji.dispatch.service.IDispatchPlanService;
import com.guiji.dispatch.util.Constant;
import com.guiji.user.dao.entity.SysUser;
import com.guiji.utils.DateUtil;
import com.guiji.utils.IdGenUtil;

/**
 * 第三方接口
 * 
 * @author Administrator
 *
 */
@RestController
public class ThirdApiController implements IThirdApiOut {

	static Logger logger = LoggerFactory.getLogger(ThirdApiController.class);


	@Autowired
	private IDispatchPlanService dispatchPlanService;
	@Autowired
	private DispatchPlanMapper dispatchPlanMapper;
	@Autowired
	private IAuth auth;

	@Override
	@GetMapping(value = "out/getCalldetail")
	public ReturnData<List<com.guiji.dispatch.model.CallPlanDetailRecordVO>> getCalldetail(String phone,
			String batchNumber, int pagenum, int pagesize) {
		List<CallPlanDetailRecordVO> queryDispatchPlanByPhoens = dispatchPlanService.queryDispatchPlanByPhoens(phone,
				batchNumber, pagenum, pagesize);
		List<com.guiji.dispatch.model.CallPlanDetailRecordVO> list = new ArrayList<>();
		for (CallPlanDetailRecordVO vo : queryDispatchPlanByPhoens) {
			com.guiji.dispatch.model.CallPlanDetailRecordVO record = new com.guiji.dispatch.model.CallPlanDetailRecordVO();
			BeanUtils.copyProperties(vo, record);
			list.add(record);
		}
		ReturnData<List<com.guiji.dispatch.model.CallPlanDetailRecordVO>> returndata = new ReturnData<>();
		returndata.setBody(list);
		return returndata;
	}

	@Override
	@GetMapping(value = "out/getcall4BatchName")
	public ReturnData<PlanCallInfoCount> getcall4BatchName(String batchName) {
		int countAlready = dispatchPlanService.getcall4BatchName(batchName, Constant.STATUSPLAN_1);
		int countNo = dispatchPlanService.getcall4BatchName(batchName, Constant.STATUSPLAN_2);
		PlanCallInfoCount info = new PlanCallInfoCount();
		info.setSuccCount(countAlready);
		info.setPlanCount(countNo);
		ReturnData<PlanCallInfoCount> returndata = new ReturnData<>();
		returndata.setBody(info);
		return returndata;
	}

	@Override
	@GetMapping(value = "out/queryDispatchPlan")
	public ReturnData<List<DispatchPlanApi>> queryDispatchPlan(String batchName, int pagenum, int pagesize) {
		Page<DispatchPlan> page = new Page<>();
		page.setPageNo(pagenum);
		page.setPageSize((pagesize));
		DispatchPlanExample example = new DispatchPlanExample();
		example.setLimitStart((pagenum - 1) * pagesize);
		example.setLimitEnd(pagesize);
		example.createCriteria().andBatchNameEqualTo(batchName).andIsDelEqualTo(Constant.IS_DEL_0);
		List<com.guiji.dispatch.dao.entity.DispatchPlan> list = dispatchPlanMapper.selectByExample(example);
		List<DispatchPlanApi> copyBean = new ArrayList<>();
		for (com.guiji.dispatch.dao.entity.DispatchPlan plan : list) {
			DispatchPlanApi bean = new DispatchPlanApi();
			BeanUtils.copyProperties(plan, bean);
			copyBean.add(bean);
		}
		ReturnData<List<DispatchPlanApi>> returnData = new ReturnData<>();
		returnData.setBody(copyBean);
		return returnData;
	}

	/**
	 * @param dispatchPlanList
	 * @return
	 */
	@Override
	@PostMapping(value = "out/insertDispatchPlanList")
	public ReturnData<PlanResultInfo> insertDispatchPlanList(@RequestBody DispatchPlanList dispatchPlanList) {
		boolean result = true;
		ReturnData<SysUser> user = auth.getUserById(Long.valueOf(dispatchPlanList.getUserId()));
		String userName = "";
		if (user.getBody() != null) {
			userName = user.getBody().getUsername();
		}

		List<com.guiji.dispatch.dao.entity.DispatchPlan> fails = new ArrayList<>();
		List<com.guiji.dispatch.dao.entity.DispatchPlan> succ = new ArrayList<>();

		for (DispatchPlan dis : dispatchPlanList.getMobile()) {
			if (dis.getPhone() == null || dis.getPhone() == "" || !isInteger(dis.getPhone())) {
				result = false;
			}
			if (dispatchPlanList.getRobot() == null || dispatchPlanList.getRobot() == "") {
				result = false;
			}

			if (dispatchPlanList.getLine() == null || dispatchPlanList.getLine() == "") {
				result = false;
			}

			if (dispatchPlanList.getIsClean() == null || dispatchPlanList.getIsClean() == "") {
				result = false;
			}

			if (dispatchPlanList.getCallDate() == null || dispatchPlanList.getCallDate() == "") {
				result = false;
			}

			if (dispatchPlanList.getCallHour() == null || dispatchPlanList.getCallHour() == "") {
				result = false;
			}

			if (dispatchPlanList.getBatchName() == null || dispatchPlanList.getBatchName() == "") {
				result = false;
			}
			com.guiji.dispatch.dao.entity.DispatchPlan bean = new com.guiji.dispatch.dao.entity.DispatchPlan();
			BeanUtils.copyProperties(dis, bean);
			if (result) {
				bean.setPlanUuid(IdGenUtil.uuid());
				bean.setUserId(Integer.valueOf(dispatchPlanList.getUserId()));
				bean.setLine(Integer.valueOf(dispatchPlanList.getLine()));
				bean.setRobot(dispatchPlanList.getRobot());
				bean.setClean(Integer.valueOf(dispatchPlanList.getIsClean()));
				bean.setCallHour(dispatchPlanList.getCallHour());
				bean.setCallData(Integer.valueOf(dispatchPlanList.getCallDate()));
				bean.setFlag(Constant.IS_FLAG_0);
				bean.setGmtCreate(DateUtil.getCurrent4Time());
				bean.setGmtModified(DateUtil.getCurrent4Time());
				bean.setUserName(userName);
				succ.add(bean);
			} else {
				fails.add(bean);
			}
		}
		boolean insertDispatchPlanList = dispatchPlanService.insertDispatchPlanList(succ);
		logger.info("批量写入结果 ： " + insertDispatchPlanList);
		PlanResultInfo info = new PlanResultInfo();
		info.setSuccCount(succ.size());
		info.setErrorCount(fails.size());
		ReturnData<PlanResultInfo> returnData = new ReturnData<>();
		returnData.setBody(info);
		return returnData;
	}

	public static boolean isInteger(String str) {
		Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
		return pattern.matcher(str).matches();
	}

}
