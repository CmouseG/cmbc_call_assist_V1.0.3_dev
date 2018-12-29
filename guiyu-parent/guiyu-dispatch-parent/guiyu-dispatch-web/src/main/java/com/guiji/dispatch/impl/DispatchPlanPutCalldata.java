package com.guiji.dispatch.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guiji.dispatch.dao.entity.DispatchPlan;
import com.guiji.utils.RedisUtil;

@Service
public class DispatchPlanPutCalldata implements IDispatchPlanPutCalldata {

	@Autowired
	private RedisUtil redisUtil;

	@Override
	public void put(Integer userId, Integer LineId, List<DispatchPlan> data) {

		// if (data.size() > 0) {
		// for(DispatchPlan dis : data){
		redisUtil.leftPushAll("dispatch-" + userId + "-" + LineId, data);
		// }
		// }

	}

	@Override
	public int getQuerySize(Integer userId, Integer lineId) {
		int result = (int) redisUtil.lGetListSize("dispatch-" + userId + "-" + lineId);
		return 1000 - result;
	}

	@Override
	public List<DispatchPlan> get(Integer userId, int requestCount, int lineId) {
		List<DispatchPlan> result = new ArrayList<>();
		for (int i = 0; i < requestCount; i++) {
			DispatchPlan object = (DispatchPlan) redisUtil.lrightPop("dispatch-" + userId + "-" + lineId);
			if (object != null) {
				result.add(object);
			}
		}
		return result;
	}
}
