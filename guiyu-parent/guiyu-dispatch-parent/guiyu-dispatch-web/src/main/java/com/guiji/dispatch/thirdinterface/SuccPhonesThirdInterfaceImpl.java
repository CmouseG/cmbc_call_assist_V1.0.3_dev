package com.guiji.dispatch.thirdinterface;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.guiji.auth.api.IAuth;
import com.guiji.ccmanager.api.ICallPlanDetail;
import com.guiji.ccmanager.vo.CallPlanDetailRecordVO;
import com.guiji.component.result.Result.ReturnData;
import com.guiji.dispatch.dao.ThirdInterfaceRecordsMapper;
import com.guiji.dispatch.dao.entity.DispatchPlan;
import com.guiji.dispatch.dao.entity.ThirdInterfaceRecords;
import com.guiji.dispatch.util.Constant;
import com.guiji.user.dao.entity.SysUser;
import com.guiji.utils.DateUtil;
import com.guiji.utils.HttpClientUtil;

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

	@Override
	public void execute(DispatchPlan dis) {
		logger.info("---------------------第三方回调------------------------");
		logger.info("---------------------第三方回调------------------------");
		logger.info("---------------------第三方回调------------------------");
		logger.info("---------------------第三方回调------------------------");
		// 回调批次拨打结束通知。
		ReturnData<SysUser> user = auth.getUserById(dis.getUserId().longValue());
		if (user.getBody() != null) {
			String batchRecordUrl = user.getBody().getBatchRecordUrl();
			if (batchRecordUrl != null && batchRecordUrl != "") {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("batch_number", dis.getBatchName());
				jsonObject.put("operate", user.getBody().getUsername());
				String sendHttpPost = "";
				try {
					sendHttpPost = HttpClientUtil.doPostJson(batchRecordUrl, jsonObject.toString());
				} catch (Exception e) {
					if (insertThirdInterface(batchRecordUrl, jsonObject)) {
						logger.info("回调错误记录新增成功...");
					}
					logger.error("error", e);
				}
				logger.info("回调批次拨打结束通知结果 :" + sendHttpPost);
			}

			if (user.getBody().getCallRecordUrl() != null && user.getBody().getCallRecordUrl() != "") {
				logger.info("通话记录通知开始");
				JSONObject jsonObject = new JSONObject();
				List<String> ids = new ArrayList<>();
				ids.add(dis.getPlanUuid());
				ReturnData<List<CallPlanDetailRecordVO>> callPlanDetailRecord = callPlanDetail
						.getCallPlanDetailRecord(ids);
				logger.info("当前回调通话记录调用接口结果 " + callPlanDetailRecord.success);
				logger.info("当前回调通话记录调用接口结果 " + callPlanDetailRecord.msg);
				jsonObject.put("data", callPlanDetailRecord.getBody());
				boolean insertThirdInterface = insertThirdInterface(user.getBody().getCallRecordUrl(), jsonObject);
				logger.info("当前回调通话记录调用接口通话记录通知结果 :" + insertThirdInterface);
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
	private boolean insertThirdInterface(String url, JSONObject jsonObject) {
		ThirdInterfaceRecords record = new ThirdInterfaceRecords();
		try {
			record.setCreateTime(DateUtil.getCurrent4Time());
		} catch (Exception e2) {
			logger.error("error", e2);
		}
		record.setUrl(url);
		record.setParams(jsonObject.toJSONString());
		record.setTimes(Constant.THIRD_INTERFACE_RETRYTIMES);
		logger.info("调用第三方接口异常，记录失败记录");
		int res = thirdInterfaceRecordsMapper.insert(record);
		return res > 0 ? true : false;
	}

}
