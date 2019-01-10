package com.guiji.dispatch.state;

import java.util.List;

import com.guiji.dispatch.dao.entity.DispatchPlan;

public interface IPhoneSucc {
	void Handler(List<DispatchPlan> list);
}
