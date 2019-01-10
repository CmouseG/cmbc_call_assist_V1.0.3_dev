package com.guiji.dispatch.state;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guiji.dispatch.dao.entity.DispatchPlan;
import com.guiji.dispatch.service.IGetPhonesInterface;
import com.guiji.dispatch.util.Constant;

@Service
public class IphonesThreadImpl implements IphonesThread {

	static Logger logger = LoggerFactory.getLogger(IphonesThreadImpl.class);

	@Autowired
	private IGetPhonesInterface getPhones;

	@Autowired
	private PhonesJobContext context;

	@Override
	public void execute() {
		// 每次最大查询
		Integer maxLimit = 10;

		// 获取当前可以拨打的用户id group by
		List<Integer> userIds = getPhones.getUsersByParams(Constant.STATUSPLAN_1, Constant.STATUS_SYNC_0,
				Constant.IS_FLAG_0);
		logger.info("当前可以拨打的用户{}" + userIds);

		for (Integer userId : userIds) {
			// 查询出每个用户对应号码的总数
			while (true) {
				// 当前用户号码总数
				Integer countByUserId = getPhones.getCountByUserId(userId, Constant.STATUSPLAN_1,
						Constant.STATUS_SYNC_0, Constant.IS_FLAG_0);

				if (countByUserId <= 0) {
					break;
				}
				if (countByUserId >= maxLimit) {
					// 根据用户查询出可以拨打的号码
					List<DispatchPlan> usersByParamsByUserId = getPhones.getUsersByParamsByUserId(userId, maxLimit,
							Constant.STATUSPLAN_1, Constant.STATUS_SYNC_0, Constant.IS_FLAG_0);
					logger.info("当前用户:{}可以拨打的号码数量{}", userId, usersByParamsByUserId.size());
				} else {
					List<DispatchPlan> usersByParamsByUserId = getPhones.getUsersByParamsByUserId(userId, countByUserId,
							Constant.STATUSPLAN_1, Constant.STATUS_SYNC_0, Constant.IS_FLAG_0);
					logger.info("当前用户:{}可以拨打的号码数量{}", userId, usersByParamsByUserId.size());
					try {
						// exe(usersByParamsByUserId);
					} catch (Exception e) {
						logger.info("error", e);
					}
				}

			}
		}
	}

}
