package com.guiji.dispatch.state;

import com.guiji.dispatch.dao.entity.DispatchPlan;
import com.guiji.dispatch.service.IDispatchPlanService;
import com.guiji.dispatch.util.Constant;
import com.guiji.robot.api.IRobotRemote;
import com.guiji.robot.model.CheckParamsReq;
import com.guiji.robot.model.HsParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**

 * @author xujin
 *
 */
@Service
public class PhonesInitHandler implements IPhonesInit {
	static Logger logger = LoggerFactory.getLogger(PhonesInitHandler.class);

	@Autowired
	private IDispatchPlanService dispatchPlanService;

	@Autowired
	private IRobotRemote robotRemote;

	@Override
	public void Handler(List<DispatchPlan> list) {
		logger.info("*******************************PhonesInitHandler***********************************");
		logger.info("*******************************PhonesInitHandler***********************************");
		logger.info("*******************************PhonesInitHandler***********************************");

//		List<DispatchPlan> list = dispatchPlanService.selectPhoneByDate();

		logger.info("当前准备初始化号码数量:" + list.size());
		List<HsParam> sendData = new ArrayList<>();
		for (DispatchPlan dispatchPlan : list) {
			HsParam hsParam = new HsParam();
			hsParam.setParams(dispatchPlan.getParams());
			hsParam.setSeqid(dispatchPlan.getPlanUuidLong() + "");
			hsParam.setTemplateId(dispatchPlan.getRobot());
			sendData.add(hsParam);
		}

		if (sendData.size() > 0) {
			CheckParamsReq req = new CheckParamsReq();
			req.setCheckers(sendData);
			req.setNeedResourceInit(true);
			robotRemote.checkParams(req);
		}

		// 批量修改状态flag
		if (list.size() > 0) {
			boolean batchUpdateFlag = dispatchPlanService.batchUpdateFlag(list, Constant.IS_FLAG_1);
			logger.info("获取当前初始化号码的请求资源结果修改当结果" + batchUpdateFlag);
		}

	}

}
