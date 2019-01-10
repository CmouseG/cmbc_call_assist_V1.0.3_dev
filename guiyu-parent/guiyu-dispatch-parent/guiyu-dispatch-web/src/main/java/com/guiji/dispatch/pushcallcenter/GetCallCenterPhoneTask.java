package com.guiji.dispatch.pushcallcenter;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.guiji.calloutserver.api.ICallPlan;
import com.guiji.calloutserver.entity.CallEndIntent;
import com.guiji.component.result.Result.ReturnData;
import com.guiji.dispatch.bean.MQSuccPhoneDto;
import com.guiji.dispatch.controller.DispatchOutApiController;
import com.guiji.dispatch.dao.PushRecordsMapper;
import com.guiji.dispatch.dao.entity.PushRecords;
import com.guiji.dispatch.dao.entity.PushRecordsExample;
import com.guiji.dispatch.util.Constant;

/**
 * 每隔五分钟询问5分钟之前推送给呼叫中心的号码
 * 
 * @author Administrator
 *
 */
@Component
public class GetCallCenterPhoneTask {

	static Logger logger = LoggerFactory.getLogger(DispatchOutApiController.class);
	@Autowired
	private PushRecordsMapper pushMapper;
	@Autowired
	private ICallPlan callplan;
	@Autowired
	private SuccessPhoneMQService successPhoneMQService;


	
	@Scheduled(cron = "0 0/5 * * * ?")
	public void callCenterPhoneStatus() {
		PushRecordsExample pushEx = new PushRecordsExample();
		// 查询出前五分钟没有回调的号码
		pushEx.createCriteria().andCreateTimeLessThanOrEqualTo(getLast5MinutesTime())
				.andCallbackStatusEqualTo(Constant.NOCALLBACK);
		List<PushRecords> result = pushMapper.selectByExample(pushEx);
		logger.info("当前呼叫中心五分钟没有回调的号码数量:" + result.size());

		// 调用呼叫中心去询问如果正在通话就不修改当前
		for (PushRecords records : result) {
			// 如果一通电话已挂断,那么放到回调成功的队列中，把当前状态设置成已经回调
			ReturnData<CallEndIntent> callEnd = callplan.isCallEnd(records.getPlanuuid());
			if (callEnd.success) {
				if(callEnd.body.isEnd()){
					//如果已经挂断
					MQSuccPhoneDto dto = new MQSuccPhoneDto();
					dto.setPlanuuid(records.getPlanuuid());
					dto.setLabel(callEnd.body.getIntent());
					successPhoneMQService.insertCallBack4MQ(dto);
					successPhoneMQService.insertSuccesPhone4BusinessMQ(dto);
				}
			} else {
				logger.info("调用呼叫中心isCallEnd接口失败");
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
