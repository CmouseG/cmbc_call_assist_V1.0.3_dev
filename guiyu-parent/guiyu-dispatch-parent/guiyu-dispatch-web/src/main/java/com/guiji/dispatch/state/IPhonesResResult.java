package com.guiji.dispatch.state;

import java.util.List;

import com.guiji.dispatch.dao.entity.DispatchPlan;

public interface IPhonesResResult {
	void Handler(List<DispatchPlan> list);
}
