package com.guiji.dispatch.job;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.guiji.ccmanager.api.ICallManagerOut;
import com.guiji.component.lock.DistributedLockHandler;
import com.guiji.component.lock.Lock;
import com.guiji.component.result.Result.ReturnData;
import com.guiji.dispatch.dao.DispatchPlanMapper;
import com.guiji.dispatch.dao.ThirdInterfaceRecordsMapper;
import com.guiji.dispatch.dao.entity.DispatchPlan;
import com.guiji.dispatch.dao.entity.ThirdInterfaceRecords;
import com.guiji.dispatch.dao.entity.ThirdInterfaceRecordsExample;
import com.guiji.dispatch.impl.DispatchPlanPutCalldata;
import com.guiji.dispatch.model.ModularLogs;
import com.guiji.dispatch.service.IDispatchPlanService;
import com.guiji.dispatch.service.IModularLogsService;
import com.guiji.dispatch.util.Constant;
import com.guiji.robot.api.IRobotRemote;
import com.guiji.robot.model.CheckParamsReq;
import com.guiji.robot.model.HsParam;
import com.guiji.robot.model.TtsComposeCheckRsp;
import com.guiji.robot.model.TtsVoiceReq;
import com.guiji.utils.DateUtil;
import com.guiji.utils.HttpClientUtil;
import com.guiji.utils.RedisUtil;

@Component
// @RestController
public class TimeTask {

	private static final Logger logger = LoggerFactory.getLogger(TimeTask.class);

	@Autowired
	private IDispatchPlanService dispatchPlanService;
	@Autowired
	private ICallManagerOut callManagerOutApi;
	@Autowired
	private IRobotRemote robotRemote;
	@Autowired
	private RedisUtil redisUtil;
	@Autowired
	private ThirdInterfaceRecordsMapper thirdInterfaceRecordsMapper;

	@Autowired
	DistributedLockHandler distributedLockHandler;
	@Autowired
	private IModularLogsService modularLogs;

	@Autowired
	private DispatchPlanPutCalldata patchPlanPutCalldata;

	@Autowired
	private DispatchPlanMapper dispatchMapper;

	/**
	 * 捞取号码初始化资源
	 */
	@Scheduled(cron = "0 0/1 * * * ?")
	// @PostMapping("selectPhoneInit")
	public void selectPhoneInit() {
		logger.info("-----------------------------------------------------------------");
		logger.info("-----------------------------------------------------------------");
		logger.info("-----------------------------------------------------------------");
		logger.info("-----------------------------------------------------------------");
		logger.info("-----------------------------------------------------------------");
		logger.info("-----------------------------------------------------------------");
		logger.info("-----------------------------------------------------------------");
		logger.info("-----------------------------------------------------------------");
		logger.info("-----------------------------------------------------------------");
		logger.info("-----------------------------------------------------------------");
		Lock lock = new Lock("selectPhoneInitJob.TimeTask", "selectPhoneInitJob.TimeTask");
		try {
			if (distributedLockHandler.tryLock(lock)) { // 默认锁设置
				logger.info("捞取号码初始化资源..");
				List<DispatchPlan> list = dispatchPlanService.selectPhoneByDate();
				logger.info("数量size" + list.size());
				List<HsParam> sendData = new ArrayList<>();
				for (DispatchPlan dispatchPlan : list) {
					HsParam hsParam = new HsParam();
					hsParam.setParams(dispatchPlan.getParams());
					hsParam.setSeqid(dispatchPlan.getPlanUuid());
					hsParam.setTemplateId(dispatchPlan.getRobot());
					sendData.add(hsParam);
				}

				if (sendData.size() > 0) {
					CheckParamsReq req = new CheckParamsReq();
					req.setCheckers(sendData);
					req.setNeedResourceInit(true);
					robotRemote.checkParams(req);
				}

				// 批量修改状态flag
				if (list.size() > 0) {
					logger.info("selectPhoneInit修改开始......");
					dispatchPlanService.batchUpdateFlag(list, Constant.IS_FLAG_1);
					logger.info("selectPhoneInit修改结束......");
				}
				logger.info("有一批数据调用初始化资源结果数量....." + list.size());
				distributedLockHandler.releaseLock(lock); // 释放锁
			}
		} catch (Exception e) {
			logger.info("error", e);
		} finally {
			distributedLockHandler.releaseLock(lock);
		}
	}

	/**
	 * 获取当前资源情况
	 */
	@Scheduled(cron = "0 0/1 * * * ?")
	// @PostMapping("getResourceResult")
	public void getResourceResult() {
		logger.info("-----------------------------------------------------------------");
		logger.info("-----------------------------------------------------------------");
		logger.info("-----------------------------------------------------------------");
		logger.info("-----------------------------------------------------------------");
		logger.info("-----------------------------------------------------------------");
		logger.info("-----------------------------------------------------------------");
		logger.info("-----------------------------------------------------------------");
		logger.info("-----------------------------------------------------------------");
		logger.info("-----------------------------------------------------------------");
		logger.info("-----------------------------------------------------------------");
		Lock lock = new Lock("getResourceResultJob.TimeTask", "getResourceResultJob.TimeTask");
		try {
			if (distributedLockHandler.tryLock(lock)) { // 默认锁设置
				logger.info("获取当前初始化号码的请求资源结果");
				List<DispatchPlan> selectPhoneByDateAndFlag = dispatchPlanService
						.selectPhoneByDateAndFlag(Constant.IS_FLAG_1);

				List<TtsVoiceReq> ttsVoiceReqList = new ArrayList<>();
				List<ModularLogs> beforeLogs = new ArrayList<>();
				for (DispatchPlan dis : selectPhoneByDateAndFlag) {
					TtsVoiceReq req = new TtsVoiceReq();
					req.setSeqid(dis.getPlanUuid());
					req.setTemplateId(dis.getRobot());
					ttsVoiceReqList.add(req);

					ModularLogs log = new ModularLogs();
					log.setCreateTime(DateUtil.getCurrent4Time());
					log.setModularName(Constant.MODULAR_NAME_DISPATCH);
					log.setStatus(Constant.MODULAR_STATUS_START);
					log.setPlanUuid(dis.getPlanUuid());
					log.setPhone(dis.getPhone());
					log.setBatchName(dis.getBatchName());
					beforeLogs.add(log);
				}
				modularLogs.notifyLogsList(beforeLogs);

				ReturnData<List<TtsComposeCheckRsp>> ttsComposeCheck = robotRemote.ttsComposeCheck(ttsVoiceReqList);
				List<DispatchPlan> successList = new ArrayList<>();

				List<ModularLogs> afterlogs = new ArrayList<>();
				if (ttsComposeCheck.success) {
					if (ttsComposeCheck.getBody() != null) {
						List<TtsComposeCheckRsp> body = ttsComposeCheck.getBody();
						for (TtsComposeCheckRsp tts : body) {
							// 记录logs
							ModularLogs log = new ModularLogs();
							log.setCreateTime(DateUtil.getCurrent4Time());
							log.setModularName(Constant.MODULAR_NAME_DISPATCH);
							log.setStatus(Constant.MODULAR_STATUS_END);
							log.setPlanUuid(tts.getSeqId());
							if (tts.getStatus().equals("S")) {
								DispatchPlan dis = new DispatchPlan();
								dis.setFlag(Constant.IS_FLAG_2);
								dis.setPlanUuid(tts.getSeqId());
								successList.add(dis);
							} else if (tts.getStatus().equals("F")) {
								log.setStatus(Constant.MODULAR_STATUS_ERROR);
								log.setMsg("校验资源状态返回结果为F");
							}
							afterlogs.add(log);
						}
						modularLogs.notifyLogsList(afterlogs);
					}
				} else {
					logger.info("获取当前初始化号码的请求资源结果失败了");
				}
				logger.info("当前准备好的资源号码有:" + successList.size());
				boolean batchUpdateFlag = false;
				if (successList.size() > 0) {
					// List<List<DispatchPlan>> averageAssign =
					// averageAssign(successList, 30);
					// int count = 1;
					// for (List<DispatchPlan> list : averageAssign) {
					// logger.info("批量修改了{}次结束了" + count);
					logger.info("getResourceResult修改开始......");
					batchUpdateFlag = dispatchPlanService.batchUpdateFlag(successList, Constant.IS_FLAG_2);
					logger.info("getResourceResult修改结束......");
					// count++;
					// }
					// batchUpdateFlag =
					// dispatchPlanService.batchUpdateFlag(successList,
					// Constant.IS_FLAG_2);
				}
				logger.info("获取当前初始化号码的请求资源结果修改当结果" + batchUpdateFlag);
				distributedLockHandler.releaseLock(lock); // 释放锁
			}
		} catch (Exception e) {
			logger.error("error", e);
		} finally {
			distributedLockHandler.releaseLock(lock); // 释放锁
		}
	}

	/**
	 * 将一个list均分成n个list,主要通过偏移量来实现的
	 * 
	 * @param source
	 * @return
	 */
	public static <T> List<List<T>> averageAssign(List<T> source, int n) {
		List<List<T>> result = new ArrayList<List<T>>();
		int remaider = source.size() % n; // (先计算出余数)
		int number = source.size() / n; // 然后是商
		int offset = 0;// 偏移量
		for (int i = 0; i < n; i++) {
			List<T> value = null;
			if (remaider > 0) {
				value = source.subList(i * number + offset, (i + 1) * number + offset + 1);
				remaider--;
				offset++;
			} else {
				value = source.subList(i * number + offset, (i + 1) * number + offset);
			}
			result.add(value);
		}
		return result;
	}

	/**
	 * 获取可以拨打的号码
	 */
	@Scheduled(cron = "0 0/1 * * * ?")
	// @PostMapping("getSuccessPhones")
	public void getSuccessPhones() {
		logger.info("-----------------------------------------------------------------");
		logger.info("-----------------------------------------------------------------");
		logger.info("-----------------------------------------------------------------");
		logger.info("-----------------------------------------------------------------");
		logger.info("-----------------------------------------------------------------");
		logger.info("-----------------------------------------------------------------");
		logger.info("-----------------------------------------------------------------");
		logger.info("-----------------------------------------------------------------");
		logger.info("-----------------------------------------------------------------");
		logger.info("-----------------------------------------------------------------");
		Lock lock = new Lock("getSuccessPhonesJob.TimeTask", "getSuccessPhonesJob.TimeTask");
		try {
			if (distributedLockHandler.tryLock(lock)) { // 默认锁设置
				logger.info(" 获取可以拨打的号码..");
				List<DispatchPlan> list = dispatchPlanService.selectPhoneByDateAndFlag(Constant.IS_FLAG_2);
				logger.info(" 获取可以拨打的号码count .." + list.size());
				// 分组
				Map<String, List<DispatchPlan>> collect = list.stream()
						.collect(Collectors.groupingBy(d -> fetchGroupKey(d)));
				for (Entry<String, List<DispatchPlan>> entry : collect.entrySet()) {
					if (redisUtil.get(entry.getKey().split("-")[1]) != null) {
						logger.info("当前模板id正在升级中...." + entry.getKey().split("-")[1]);
						continue;
					}
					// }
					// put redis
					checkRedisAndDate(entry.getKey(), entry.getValue());
				}
				distributedLockHandler.releaseLock(lock); // 释放锁
			}
		} catch (Exception e) {
			logger.info("error", e);
		} finally {
			distributedLockHandler.releaseLock(lock); // 释放锁
		}
	}

	@Scheduled(cron = "0 0/1 * * * ?")
	// @PostMapping("putRedisByphones")
	public void putRedisByphones() {
		logger.info("-----------------------------putRedisByphones------------------------------------");
		logger.info("-----------------------------------------------------------------");
		logger.info("-----------------------------------------------------------------");
		logger.info("-----------------------------------------------------------------");
		logger.info("-----------------------------------------------------------------");
		logger.info("-----------------------------------------------------------------");
		logger.info("-----------------------------------------------------------------");
		logger.info("-----------------------------------------------------------------");
		logger.info("-----------------------------------------------------------------");
		logger.info("-----------------------------------------------------------------");
		Lock lock = new Lock("putRedisByphones.TimeTask", "putRedisByphones.TimeTask");
		try {
			if (distributedLockHandler.tryLock(lock)) { // 默认锁设置
				logger.info(" 获取可以拨打的号码..");
				// 获取userIdList
				// List<DispatchPlan> selectPhoneByDate4UserId =
				// dispatchPlanService
				// .selectPhoneByDate4UserId(Constant.IS_FLAG_2, 1000);
				// System.out.println(selectPhoneByDate4UserId);
				Map<String, String> map = (HashMap) redisUtil.hmget("dispath-userIds");
				if (map == null) {
					return;
				}
				for (Entry<String, String> ent : map.entrySet()) {
					String[] split = ent.getKey().split("-");
					Integer limit = patchPlanPutCalldata.getQuerySize(Integer.valueOf(split[0]),
							Integer.valueOf(split[2]));
					if (limit > 0) {
						List<DispatchPlan> list = dispatchPlanService.selectPhoneByDate4Redis(Constant.IS_FLAG_2,
								limit);
						if (list.size() > 0) {
							patchPlanPutCalldata.put(Integer.valueOf(split[0]), Integer.valueOf(split[2]), list);
						}
						List<String> ids = new ArrayList<>();
						for (DispatchPlan dis : list) {
							ids.add(dis.getPlanUuid());
						}
						if(ids.size() >0){
							dispatchMapper.updateDispatchPlanListByStatusSYNC(ids, Constant.STATUS_SYNC_1);
						}
					}
				}
				distributedLockHandler.releaseLock(lock); // 释放锁
			}
		} catch (Exception e) {
			logger.info("error", e);
		} finally {
			distributedLockHandler.releaseLock(lock); // 释放锁
		}
	}

	// 每天凌晨1点执行一次
	@Scheduled(cron = "0 0 1 * * ?")
	// @PostMapping("replayPhone")
	public void replayPhone() {
		logger.info("-----------------------------------------------------------------");
		logger.info("-----------------------------------------------------------------");
		logger.info("-----------------------------------------------------------------");
		logger.info("-----------------------------------------------------------------");
		logger.info("-----------------------------------------------------------------");
		logger.info("-----------------------------------------------------------------");
		logger.info("-----------------------------------------------------------------");
		logger.info("-----------------------------------------------------------------");
		logger.info("-----------------------------------------------------------------");
		logger.info("-----------------------------------------------------------------");
		Lock lock = new Lock("replayPhoneJob.TimeTask", "replayPhoneJob.TimeTask");
		try {
			if (distributedLockHandler.tryLock(lock)) { // 默认锁设置
				boolean result = dispatchPlanService.updateReplayDate(true);
				logger.info("当前凌晨一点执行日期刷新操作了！不清除号码操作" + result);
				distributedLockHandler.releaseLock(lock); // 释放锁
			}
		} catch (Exception e) {
			logger.info("error", e);
		} finally {
			distributedLockHandler.releaseLock(lock); // 释放锁
		}
	}

	// 每天凌晨1点执行一次
	@Scheduled(cron = "0 0 1 * * ?")
	// @PostMapping("replayPhoneClean")
	public void replayPhoneClean() {
		logger.info("-----------------------------------------------------------------");
		logger.info("-----------------------------------------------------------------");
		logger.info("-----------------------------------------------------------------");
		logger.info("-----------------------------------------------------------------");
		logger.info("-----------------------------------------------------------------");
		logger.info("-----------------------------------------------------------------");
		logger.info("-----------------------------------------------------------------");
		logger.info("-----------------------------------------------------------------");
		logger.info("-----------------------------------------------------------------");
		logger.info("-----------------------------------------------------------------");
		Lock lock = new Lock("replayPhoneClean", "replayPhoneClean.TimeTask");
		try {
			if (distributedLockHandler.tryLock(lock)) { // 默认锁设置
				boolean result = dispatchPlanService.updateReplayDate(false);
				logger.info("当前凌晨一点执行日期刷新操作了！不清除号码操作" + result);
				distributedLockHandler.releaseLock(lock); // 释放锁
			}
		} catch (Exception e) {
			logger.info("error", e);
		} finally {
			distributedLockHandler.releaseLock(lock); // 释放锁
		}
	}

	@Scheduled(cron = "0 0/1 * * * ?")
	// @PostMapping("reTryThirdInterface")
	public void reTryThirdInterface() {
		logger.info("-----------------------------------------------------------------");
		logger.info("-----------------------------------------------------------------");
		logger.info("-----------------------------------------------------------------");
		logger.info("-----------------------------------------------------------------");
		logger.info("-----------------------------------------------------------------");
		logger.info("-----------------------------------------------------------------");
		logger.info("-----------------------------------------------------------------");
		logger.info("-----------------------------------------------------------------");
		logger.info("-----------------------------------------------------------------");
		logger.info("-----------------------------------------------------------------");
		Lock lock = new Lock("reTryThirdInterfaceJob.TimeTask", "reTryThirdInterfaceJob.TimeTask");
		try {
			if (distributedLockHandler.tryLock(lock)) { // 默认锁设置
				ThirdInterfaceRecordsExample ex = new ThirdInterfaceRecordsExample();
				// 大于0
				ex.createCriteria().andTimesGreaterThan(0);
				List<ThirdInterfaceRecords> selectByExample = thirdInterfaceRecordsMapper.selectByExample(ex);
				for (ThirdInterfaceRecords record : selectByExample) {
					try {
						String result = HttpClientUtil.doPostJson(record.getUrl(), record.getParams());
						// 删除数据
						thirdInterfaceRecordsMapper.deleteByPrimaryKey(record.getId());
					} catch (Exception e) {
						// 如果有问题那么把times次数减1
						record.setTimes(record.getTimes() - 1);
						thirdInterfaceRecordsMapper.updateByPrimaryKeySelective(record);
						// if (insertThirdInterface(batchRecordUrl, jsonObject))
						// {
						// logger.info("回调错误记录新增成功...");
						// }
						logger.error("error", e);
					}
				}
				distributedLockHandler.releaseLock(lock); // 释放锁
			}
		} catch (Exception e) {
			logger.info("error", e);
		} finally {
			distributedLockHandler.releaseLock(lock); // 释放锁
		}
	}

	/*
	 * 判断redis是否在当前key
	 * 
	 * @param key
	 */
	private void checkRedisAndDate(String key, List<DispatchPlan> list) {
		Object object = redisUtil.get(key);
		// logger.info("checkRedisAndDate key result:" + object);
		if (object != null && object != "") {
			logger.info("当前推送已经推送过：在失效时间内，不重复推送:" + key);
		} else {
			String[] split = key.split("-");

			HashMap map = (HashMap) redisUtil.hmget("dispath-userIds");
			if (map == null) {
				map = new HashMap<>();
			}
			map.put(key, key);
			redisUtil.hmset("dispath-userIds", map);
			ReturnData<Boolean> startcallplan = callManagerOutApi.startCallPlan(split[0], split[1], split[2]);
			logger.info("启动客户呼叫计划结果" + startcallplan.success);
			logger.info("启动客户呼叫计划结果详情 " + startcallplan.msg);
			if (startcallplan.success) {
				// 5分钟失效时间
				redisUtil.set(key, new Date(), 300);
			}
		}
	}

	/**
	 * 分组排序字段
	 * 
	 * @param detail
	 * @return
	 */
	private static String fetchGroupKey(DispatchPlan detail) {
		return String.valueOf(detail.getUserId() + "-" + detail.getRobot() + "-" + detail.getLine());
	}
}
