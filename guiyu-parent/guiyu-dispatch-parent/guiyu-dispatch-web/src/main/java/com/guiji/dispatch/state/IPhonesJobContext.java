package com.guiji.dispatch.state;

import java.util.List;

import com.guiji.dispatch.dao.entity.DispatchPlan;

public interface IPhonesJobContext {
	public void execute(int stateType ,List<DispatchPlan> list);
}
