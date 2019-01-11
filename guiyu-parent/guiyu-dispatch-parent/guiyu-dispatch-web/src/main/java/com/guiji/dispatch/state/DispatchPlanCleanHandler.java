package com.guiji.dispatch.state;

import com.guiji.dispatch.dao.entity.DispatchPlan;
import com.guiji.dispatch.util.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ty on 2019/1/10.
 */
@Service
public class DispatchPlanCleanHandler {
	static Logger logger = LoggerFactory.getLogger(DispatchPlanCleanHandler.class);
	@Autowired
	private PhonesJobContext context;

	public void excute(List<DispatchPlan> dispatchPlans) throws Exception {
		// 开始时间
		long start = System.currentTimeMillis();
		context.execute(Constant.INIT, dispatchPlans);
		context.execute(Constant.CHECKRESOURCE, dispatchPlans);
		//context.execute(Constant.PUSHHANDLER, new ArrayList<>());
		logger.info("线程任务执行结束");
		logger.info("执行任务消耗了 ：" + (System.currentTimeMillis() - start) + "毫秒");
	}
}
