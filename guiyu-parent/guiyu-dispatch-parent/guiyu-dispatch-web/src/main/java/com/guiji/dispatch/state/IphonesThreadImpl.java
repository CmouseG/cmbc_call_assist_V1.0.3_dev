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

	// 创建一个线程池
	ExecutorService exec = Executors.newFixedThreadPool(10);
	@Override
	public void execute() {
		// 每次最大查询
		Integer maxLimit = 10;

		// 获取当前可以拨打的用户id
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
					if (usersByParamsByUserId.size() > 0) {
						try {
							exe(usersByParamsByUserId);
						} catch (InterruptedException e) {
							logger.info("error", e);
						} catch (ExecutionException e) {
							logger.info("error", e);
						}
					}
				} else {
					List<DispatchPlan> usersByParamsByUserId = getPhones.getUsersByParamsByUserId(userId, countByUserId,
							Constant.STATUSPLAN_1, Constant.STATUS_SYNC_0, Constant.IS_FLAG_0);
					logger.info("当前用户:{}可以拨打的号码数量{}", userId, usersByParamsByUserId.size());
					try {
						exe(usersByParamsByUserId);
					} catch (Exception e) {
						logger.info("error", e);
					}
				}

			}
		}
	}

	public void exe(List<DispatchPlan> list) throws InterruptedException, ExecutionException {
		// 开始时间
		long start = System.currentTimeMillis();
		// 每500条数据开启一条线程
		int threadSize = 5;
		// 总数据条数
		int dataSize = list.size();

		// 定义一个任务集合
		List<Callable<Integer>> tasks = new ArrayList<Callable<Integer>>();
		Callable<Integer> task = null;
		List<DispatchPlan> cutList = null;
			// System.out.println("第" + (i + 1) + "组：" + cutList.toString());
			final List<DispatchPlan> listStr = cutList;
			task = new Callable<Integer>() {
				@Override
				public Integer call() throws Exception {
					logger.info(Thread.currentThread().getName() + "线程：" + listStr.size());
					context.execute(Constant.INIT, listStr);
					context.execute(Constant.CHECKRESOURCE, listStr);
					context.execute(Constant.PUSHHANDLER, new ArrayList<>());
					return 1;
				}
			};
			// 这里提交的任务容器列表和返回的Future列表存在顺序对应的关系
			tasks.add(task);
		List<Future<Integer>> results = exec.invokeAll(tasks);
		for (Future<Integer> future : results) {
			logger.info(future.get() + "");
		}
		// 关闭线程池
		exec.shutdown();
		logger.info("线程任务执行结束");
		logger.info("执行任务消耗了 ：" + (System.currentTimeMillis() - start) + "毫秒");
	}
}
