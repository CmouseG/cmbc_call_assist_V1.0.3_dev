package com.guiji.dispatch.job;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.guiji.dispatch.constant.RedisConstant;
import com.guiji.dispatch.exception.ExternalCodeExceptionEnum;
import com.guiji.dispatch.service.GetApiService;
import com.guiji.utils.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.guiji.calloutserver.api.ICallPlan;
import com.guiji.calloutserver.entity.CallEndIntent;
import com.guiji.component.result.Result.ReturnData;
import com.guiji.dispatch.bean.MQSuccPhoneDto;
import com.guiji.dispatch.dao.PushRecordsMapper;
import com.guiji.dispatch.dao.entity.PushRecords;
import com.guiji.dispatch.dao.entity.PushRecordsExample;
import com.guiji.dispatch.pushcallcenter.SuccessPhoneMQService;
import com.guiji.dispatch.util.Constant;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;

@JobHandler(value = "getCallCenterPhoneHandler")
@Component
public class GetCallCenterPhoneHandler extends IJobHandler {
	static Logger logger = LoggerFactory.getLogger(GetCallCenterPhoneHandler.class);

	@Autowired
	private PushRecordsMapper pushMapper;
	@Autowired
	private ICallPlan callplan;
	@Autowired
	private SuccessPhoneMQService successPhoneMQService;

	@Autowired
	private RedisUtil redisUtil;

	@Autowired
	private GetApiService getApiService;

	@Override
	public ReturnT<String> execute(String arg0) throws Exception {
		XxlJobLogger.log("XXL-JOB, GetCallCenterPhoneHandler start.");
		PushRecordsExample pushEx = new PushRecordsExample();
		// 查询出前五分钟没有回调的号码
		pushEx.createCriteria().andCreateTimeLessThanOrEqualTo(getLast5MinutesTime())
				.andCallbackStatusEqualTo(Constant.NOCALLBACK);
		List<PushRecords> result = pushMapper.selectByExample(pushEx);
		logger.info("当前呼叫中心五分钟没有回调的号码数量:" + result.size());

		// 调用呼叫中心去询问如果正在通话就不修改当前
		for (PushRecords records : result) {
			String planuuid = records.getPlanuuid();
			Integer orgId = getApiService.getOrgIdByUser(records.getUserId()+"");
			// 如果一通电话已挂断,那么放到回调成功的队列中，把当前状态设置成已经回调
			ReturnData<CallEndIntent> callEnd = callplan.isCallEnd(planuuid, orgId);
			if (callEnd.success) {
				if (callEnd.getBody().isEnd()) {
					// 如果已经挂断
					MQSuccPhoneDto dto = new MQSuccPhoneDto();
					dto.setPlanuuid(records.getPlanuuid());
					dto.setLabel(callEnd.getBody().getIntent());
					//设置用户的id
					dto.setUserId(records.getUserId());
					dto.setLineId(records.getLine());
					dto.setTempId(records.getRobot());
					successPhoneMQService.insertCallBack4MQ(dto);
					successPhoneMQService.insertSuccesPhone4BusinessMQ(dto);
				}
			} else {
				printCallBackLog(planuuid, callEnd);//打印调用呼叫中心日志
			}
		}
		XxlJobLogger.log("XXL-JOB, GetCallCenterPhoneHandler end.");
		return SUCCESS;
	}

	private void printCallBackLog(String planuuid, ReturnData<CallEndIntent> callEnd){
		Object obj = (Object)redisUtil.get(RedisConstant.RedisConstantKey.TEMPLATE_NO_READY + planuuid);
		if(null != obj) {
			logger.info("plan_uuid:" + planuuid + "五分钟没有回调，主动调用呼叫中心isCallEnd接口失败");
		}else{
			if(null != callEnd.getCode()
					&& ExternalCodeExceptionEnum.CALL_CENTER_0305012.getErrorCode().equals(callEnd.getCode())) {
				redisUtil.set(RedisConstant.RedisConstantKey.TEMPLATE_NO_READY + planuuid, 1,
						RedisConstant.RedisConstantKey.TEMPLATE_NO_READY_TIMELONG);
			}
		}
	}

	public Date getLast5MinutesTime() {
		Calendar beforeTime = Calendar.getInstance();
		beforeTime.add(Calendar.MINUTE, -5);// 5分钟之前的时间
		Date beforeD = beforeTime.getTime();
		return beforeD;
	}

}
