package com.guiji.dispatch.line;

import java.util.List;

import com.guiji.dispatch.dao.entity.DispatchLines;
import com.guiji.dispatch.dao.entity.DispatchPlan;

public interface ILinesService {
	public List<DispatchLines> queryLinesByPlanUUID(String uuid);
	
	public List<DispatchLines> queryLinesByPlanUUIDAndLineId(String uuid,Integer lineId);
	
	public boolean insertLines(DispatchLines lines);
	
	/**
	 * 更新线路排序规则
	 */
	public void getLineRule();
	
	/**
	 * 更新接通率
	 */
	public void getLineRate();
	
	/**
	 * 根据排序规则排序
	 */
	List<DispatchPlan> sortLine(List<DispatchPlan> list);
}
