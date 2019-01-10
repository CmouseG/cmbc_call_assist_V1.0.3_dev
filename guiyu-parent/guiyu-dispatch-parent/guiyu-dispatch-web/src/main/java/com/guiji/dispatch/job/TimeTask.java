//package com.guiji.dispatch.job;
//
//import java.util.List;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import com.guiji.component.lock.DistributedLockHandler;
//import com.guiji.component.lock.Lock;
//import com.guiji.dispatch.dao.ThirdInterfaceRecordsMapper;
//import com.guiji.dispatch.dao.entity.ThirdInterfaceRecords;
//import com.guiji.dispatch.dao.entity.ThirdInterfaceRecordsExample;
//import com.guiji.dispatch.service.IDispatchPlanService;
//import com.guiji.utils.HttpClientUtil;
//
//@Component
//public class TimeTask {
//
//	private static final Logger logger = LoggerFactory.getLogger(TimeTask.class);
////
////	@Autowired
////	private IDispatchPlanService dispatchPlanService;
////	
////	@Autowired
////	private ThirdInterfaceRecordsMapper thirdInterfaceRecordsMapper;
////
////	@Autowired
////	DistributedLockHandler distributedLockHandler;
////
////
////	// 每天凌晨1点执行一次
////	@Scheduled(cron = "0 0 1 * * ?")
////	// @PostMapping("replayPhone")
////	public void replayPhone() {
////		logger.info("-----------------------------------------------------------------");
////		logger.info("-----------------------------------------------------------------");
////		logger.info("-----------------------------------------------------------------");
////		logger.info("-----------------------------------------------------------------");
////		logger.info("-----------------------------------------------------------------");
////		logger.info("-----------------------------------------------------------------");
////		logger.info("-----------------------------------------------------------------");
////		logger.info("-----------------------------------------------------------------");
////		logger.info("-----------------------------------------------------------------");
////		logger.info("-----------------------------------------------------------------");
////		Lock lock = new Lock("replayPhoneJob.TimeTask", "replayPhoneJob.TimeTask");
////		try {
////			if (distributedLockHandler.tryLock(lock)) { // 默认锁设置
////				boolean result = dispatchPlanService.updateReplayDate(true);
////				logger.info("当前凌晨一点执行日期刷新操作了！不清除号码操作" + result);
////				distributedLockHandler.releaseLock(lock); // 释放锁
////			}
////		} catch (Exception e) {
////			logger.info("error", e);
////		} finally {
////			distributedLockHandler.releaseLock(lock); // 释放锁
////		}
////	}
////
////	// 每天凌晨1点执行一次
////	@Scheduled(cron = "0 0 1 * * ?")
////	// @PostMapping("replayPhoneClean")
////	public void replayPhoneClean() {
////		logger.info("-----------------------------------------------------------------");
////		logger.info("-----------------------------------------------------------------");
////		logger.info("-----------------------------------------------------------------");
////		logger.info("-----------------------------------------------------------------");
////		logger.info("-----------------------------------------------------------------");
////		logger.info("-----------------------------------------------------------------");
////		logger.info("-----------------------------------------------------------------");
////		logger.info("-----------------------------------------------------------------");
////		logger.info("-----------------------------------------------------------------");
////		logger.info("-----------------------------------------------------------------");
////		Lock lock = new Lock("replayPhoneClean", "replayPhoneClean.TimeTask");
////		try {
////			if (distributedLockHandler.tryLock(lock)) { // 默认锁设置
////				boolean result = dispatchPlanService.updateReplayDate(false);
////				logger.info("当前凌晨一点执行日期刷新操作了！不清除号码操作" + result);
////				distributedLockHandler.releaseLock(lock); // 释放锁
////			}
////		} catch (Exception e) {
////			logger.info("error", e);
////		} finally {
////			distributedLockHandler.releaseLock(lock); // 释放锁
////		}
////	}
//
//	@Scheduled(cron = "0 0/1 * * * ?")
//	// @PostMapping("reTryThirdInterface")
//	public void reTryThirdInterface() {
//		logger.info("-----------------------------------------------------------------");
//		logger.info("-----------------------------------------------------------------");
//		logger.info("-----------------------------------------------------------------");
//		logger.info("-----------------------------------------------------------------");
//		logger.info("-----------------------------------------------------------------");
//		logger.info("-----------------------------------------------------------------");
//		logger.info("-----------------------------------------------------------------");
//		logger.info("-----------------------------------------------------------------");
//		logger.info("-----------------------------------------------------------------");
//		logger.info("-----------------------------------------------------------------");
//		Lock lock = new Lock("reTryThirdInterfaceJob.TimeTask", "reTryThirdInterfaceJob.TimeTask");
//		try {
//			if (distributedLockHandler.tryLock(lock)) { // 默认锁设置
//				ThirdInterfaceRecordsExample ex = new ThirdInterfaceRecordsExample();
//				// 大于0
//				ex.createCriteria().andTimesGreaterThan(0);
//				List<ThirdInterfaceRecords> selectByExample = thirdInterfaceRecordsMapper.selectByExample(ex);
//				for (ThirdInterfaceRecords record : selectByExample) {
//					try {
//						String result = HttpClientUtil.doPostJson(record.getUrl(), record.getParams());
//						// 删除数据
//						thirdInterfaceRecordsMapper.deleteByPrimaryKey(record.getId());
//					} catch (Exception e) {
//						// 如果有问题那么把times次数减1
//						record.setTimes(record.getTimes() - 1);
//						thirdInterfaceRecordsMapper.updateByPrimaryKeySelective(record);
//						// if (insertThirdInterface(batchRecordUrl, jsonObject))
//						// {
//						// logger.info("回调错误记录新增成功...");
//						// }
//						logger.error("error", e);
//					}
//				}
//				distributedLockHandler.releaseLock(lock); // 释放锁
//			}
//		} catch (Exception e) {
//			logger.info("error", e);
//		} finally {
//			distributedLockHandler.releaseLock(lock); // 释放锁
//		}
//	}
//}
