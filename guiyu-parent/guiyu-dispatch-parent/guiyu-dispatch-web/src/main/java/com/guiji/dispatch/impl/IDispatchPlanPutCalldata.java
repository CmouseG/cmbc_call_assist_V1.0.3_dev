package com.guiji.dispatch.impl;

import java.util.List;

import com.guiji.dispatch.dao.entity.DispatchPlan;


public interface IDispatchPlanPutCalldata {
	
	public void put(Integer userId, Integer LineId, List<DispatchPlan> data);
	
	public List<DispatchPlan> get(Integer userId, int requestCount, int lineId);
	
	public int getQuerySize(Integer userId, Integer lineId);
}
