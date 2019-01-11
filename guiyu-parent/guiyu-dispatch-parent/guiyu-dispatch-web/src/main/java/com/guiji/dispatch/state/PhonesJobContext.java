package com.guiji.dispatch.state;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guiji.dispatch.dao.entity.DispatchPlan;
import com.guiji.dispatch.pushcallcenter.IPushPhonesHandler;
import com.guiji.dispatch.util.Constant;

@Service
public class PhonesJobContext implements IPhonesJobContext {

	@Autowired
	private IPhonesInit init;
	@Autowired
	private IPhonesResResult res;
	@Autowired
	private IPushPhonesHandler handler;

	public void execute(int stateType, List<DispatchPlan> list) {
		if (stateType == Constant.INIT) {
			init.Handler(list);
		} else if (stateType == Constant.CHECKRESOURCE) {
			res.Handler(list);
		}else if(stateType == Constant.PUSHHANDLER){
//			handler.pushHandler();
		}
	}
}
