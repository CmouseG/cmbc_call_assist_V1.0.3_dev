package com.guiji.dispatch.thirdinterface;

import com.alibaba.fastjson.JSONObject;
import com.guiji.auth.api.IAuth;
import com.guiji.ccmanager.api.ICallPlanDetail;
import com.guiji.ccmanager.entity.CallPlanUuidQuery;
import com.guiji.ccmanager.vo.CallPlanDetailRecordVO;
import com.guiji.component.result.Result.ReturnData;
import com.guiji.dispatch.dao.ThirdInterfaceRecordsMapper;
import com.guiji.dispatch.dao.entity.DispatchPlan;
import com.guiji.dispatch.dao.entity.ThirdInterfaceRecords;
import com.guiji.dispatch.service.ThirdApiNotifyService;
import com.guiji.dispatch.util.Constant;
import com.guiji.user.dao.entity.SysUser;
import com.guiji.utils.DateUtil;
import com.guiji.utils.HttpClientUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 回调成功调用第三方接口
 * 
 * @author Administrator
 *
 */
@Service
public class SuccPhonesThirdInterfaceImpl implements SuccPhonesThirdInterface {

	private static Logger logger = LoggerFactory.getLogger(SuccPhonesThirdInterfaceImpl.class);

	@Autowired
	private IAuth auth;
	@Autowired
	private ThirdInterfaceRecordsMapper thirdInterfaceRecordsMapper;
	@Autowired
	private ICallPlanDetail callPlanDetail;

	@Autowired
	ThirdApiNotifyService notifyService;

	@Override
	public void execute(DispatchPlan dis) {
		logger.info("---------------------第三方回调------------------------");
		// 回调批次拨打结束通知。

		//异步第三方回调处理
		notifyService.singleNotify(dis);

		ReturnData<SysUser> user = auth.getUserById(dis.getUserId().longValue());
		if (user.getBody() != null) {
			if (user.getBody().getCallRecordUrl() != null && user.getBody().getCallRecordUrl() != "") {
				String callRecordUrl = user.getBody().getCallRecordUrl();
				JSONObject jsonObject = new JSONObject();
				List<String> ids = new ArrayList<>();
				ids.add(dis.getPlanUuidLong() + "");
				CallPlanUuidQuery callPlanParam = new CallPlanUuidQuery();
				callPlanParam.setCallIds(ids);
				callPlanParam.setOrgId(dis.getOrgId());
				ReturnData<List<CallPlanDetailRecordVO>> callPlanDetailRecord = callPlanDetail
						.getCallPlanDetailRecord(callPlanParam);
				jsonObject.put("data", callPlanDetailRecord.getBody());
				try {
					String doPostJson = HttpClientUtil.doPostJson(callRecordUrl, jsonObject.toString());
					insertThirdInterface(callRecordUrl, jsonObject, Constant.THIRDAPISUCCESS, dis.getUserId());
				} catch (Exception e) {
					if (insertThirdInterface(callRecordUrl, jsonObject, Constant.THIRDAPIFAILURE, dis.getUserId())) {
						logger.info("回调错误记录新增成功...");
					}
				}
			}

		} else {
			logger.info("当前队列任务回调  用户不存在");
		}
	}

	/**
	 * 记录第三方接口记录详情
	 * 
	 * @param url
	 * @param jsonObject
	 * @return
	 */
	private boolean insertThirdInterface(String url, JSONObject jsonObject, Integer type, Integer userId) {
		ThirdInterfaceRecords record = new ThirdInterfaceRecords();
		try {
			record.setCreateTime(DateUtil.getCurrent4Time());
		} catch (Exception e2) {
			logger.error("error", e2);
		}
		record.setUrl(url);
		record.setParams(jsonObject.toJSONString());
		record.setUserId(userId);
		record.setType(type);
		if (type.equals(Constant.THIRDAPIFAILURE)) {
			record.setTimes(Constant.THIRD_INTERFACE_RETRYTIMES);
		}
		int res = thirdInterfaceRecordsMapper.insert(record);
		return res > 0 ? true : false;
	}

}
