package com.guiji.dispatch.bean;

import com.guiji.dispatch.dao.entity.DispatchPlan;
import com.guiji.dispatch.state.DispatchPlanCleanHandler;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by zhujy on 2018/11/17.
 */
@Service
public class DispatchPlanReadHandler {

	public void run(DispatchPlanCleanHandler handler) {
		ExecutorService executorService = Executors.newFixedThreadPool(10);
		List<DispatchPlan> dispatchPlans = null;

		try {
			while (true) {
				try {

					dispatchPlans = DispatchPlanQueue.getInstance().get();
					if (dispatchPlans == null) {
						continue;
					}

					executorService.execute(new DispatchPlanCleanThread(dispatchPlans, handler));

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} finally {
			executorService.shutdown();
		}

	}

	class DispatchPlanCleanThread implements Runnable {
		private List<DispatchPlan> dispatchPlans;
		private DispatchPlanCleanHandler handler;

		protected DispatchPlanCleanThread(List<DispatchPlan> dispatchPlans, DispatchPlanCleanHandler handler) {
			this.dispatchPlans = dispatchPlans;
			this.handler = handler;
		}

		@Override
		public void run() {
			try {
				this.handler.excute(dispatchPlans);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
