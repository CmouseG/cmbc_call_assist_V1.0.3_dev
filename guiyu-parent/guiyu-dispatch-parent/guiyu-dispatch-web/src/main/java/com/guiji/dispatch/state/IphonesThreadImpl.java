package com.guiji.dispatch.state;

import java.util.List;

import com.guiji.dispatch.service.GetApiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guiji.dispatch.bean.DispatchPlanPushHandler;
import com.guiji.dispatch.dao.entity.DispatchPlan;
import com.guiji.dispatch.service.IGetPhonesInterface;
import com.guiji.dispatch.util.Constant;

@Service
public class IphonesThreadImpl implements IphonesThread {

	static Logger logger = LoggerFactory.getLogger(IphonesThreadImpl.class);
	@Autowired
	private IGetPhonesInterface getPhones;

	@Autowired
	private GetApiService getApiService;

	@Override
	public void execute() {
		// 每次最大查询
		Integer maxLimit = 500;
		List<Integer> allOrgId = getApiService.getAllOrgId();
		// 获取当前可以拨打的用户id group by
		List<Integer> userIds = getPhones.getUsersByParams(Constant.STATUSPLAN_1, Constant.STATUS_SYNC_0,
				Constant.IS_FLAG_0, allOrgId);
		logger.info("当前可以拨打的用户{}" + userIds);
		if (userIds != null) {
			for (Integer userId : userIds) {
				// 根据用户查询出需要清洗的号码
				List<DispatchPlan> dispatchPlanList = getPhones.getUsersByParamsByUserId(userId, maxLimit,
						Constant.STATUSPLAN_1, Constant.STATUS_SYNC_0, Constant.IS_FLAG_0);
				logger.info("当前用户{}可以拨打的号码数量{}" + userId, dispatchPlanList.size());
				DispatchPlanPushHandler.getInstance().add(dispatchPlanList);
			}
		}else{
			// 获取当前或者未来可以拨打的用户id group by
			List<Integer> futureUserIds = getPhones.getFutureUsersByParams(Constant.STATUSPLAN_1, Constant.STATUS_SYNC_0,
					Constant.IS_FLAG_0, allOrgId);
			logger.info("当前可以拨打的用户{}" + futureUserIds);
			if (userIds != null) {
				for (Integer userId : futureUserIds) {
					// 根据用户查询出需要清洗的号码
					List<DispatchPlan> dispatchPlanList = getPhones.getFuturePlanByUserId(userId, maxLimit,
							Constant.STATUSPLAN_1, Constant.STATUS_SYNC_0, Constant.IS_FLAG_0);
					logger.info("当前用户{}可以拨打的号码数量{}" + userId, dispatchPlanList.size());
					DispatchPlanPushHandler.getInstance().add(dispatchPlanList);
				}
			}
		}

	}
}
