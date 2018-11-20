package com.guiji.dispatch.job;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.guiji.ccmanager.api.ICallManagerOut;
import com.guiji.component.result.Result.ReturnData;
import com.guiji.dispatch.dao.entity.DispatchPlan;
import com.guiji.dispatch.service.IDispatchPlanService;


@Component
public class TimeTask {
	
	 private static final Logger logger = LoggerFactory.getLogger(TimeTask.class);
	 
	@Autowired
	private IDispatchPlanService dispatchPlanService;
	@Autowired
	private ICallManagerOut callManagerOutApi;
//	@Autowired
//	private IRobotRemote robotRemote;
	
//	@Scheduled(cron = "0 0/1 * * * ?")
	public void selectPhonesByDate(){
		List<DispatchPlan> list = dispatchPlanService.selectPhoneByDate();
		for(DispatchPlan bean : list){
			//判断机器人是否准备就绪
//			robotRemote.checkAiResourceReady(checkAiReady)
			//启动客户呼叫计划
			logger.info("startcallplan..");
			 ReturnData<Boolean> startcallplan = callManagerOutApi.startCallPlan(String.valueOf(bean.getUserId()), bean.getRobot(), String.valueOf(bean.getLine()));
			logger.info("启动客户呼叫计划结果"+startcallplan.getBody());
		}
	}
}
