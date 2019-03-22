package com.guiji.dispatch.line;

import com.guiji.dispatch.dao.entity.DispatchBatchLine;
import com.guiji.dispatch.dao.entity.DispatchPlan;

import java.util.List;

public interface IDispatchBatchLineService {
	List<DispatchBatchLine> queryListByBatchId(Integer batchId);

	List<DispatchBatchLine> queryListByLineId(Integer lineId);

	List<DispatchBatchLine> queryListByBatchLineId(Integer batchId, Integer lineId);

	List<DispatchBatchLine> queryListByUserId(Long userId);

	List<DispatchBatchLine> queryListByUserIdLineId(Long userId, Integer lineId);

	void insert(DispatchBatchLine dispatchBatchLine);

	void deleteByBatchId(Integer batchId);

	/**
	 * 更新线路排序规则
	 */
	void getLineRule();

	/**
	 * 更新接通率
	 */
	void getLineRate();

	/**
	 * 根据排序规则排序
	 */
	List<DispatchPlan> sortLine(List<DispatchPlan> list);
}