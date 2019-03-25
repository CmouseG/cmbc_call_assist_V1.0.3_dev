//package com.guiji.dispatch.state;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Map.Entry;
//import java.util.stream.Collectors;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.guiji.dispatch.dao.DispatchPlanMapper;
//import com.guiji.dispatch.dao.entity.DispatchPlan;
//import com.guiji.dispatch.impl.DispatchPlanPutCalldata;
//import com.guiji.dispatch.service.IDispatchPlanService;
//import com.guiji.dispatch.util.Constant;
//import com.guiji.utils.RedisUtil;
//
//@Service
//public class PhonesSuccHandler implements IPhoneSucc {
//
//	static Logger logger = LoggerFactory.getLogger(PhonesSuccHandler.class);
//
//	@Autowired
//	private IDispatchPlanService dispatchPlanService;
//
//	@Autowired
//	private RedisUtil redisUtil;
//
//	@Autowired
//	private DispatchPlanPutCalldata patchPlanPutCalldata;
//
//	@Autowired
//	private DispatchPlanMapper dispatchMapper;
//
//
//	@Override
//	public void Handler(List<DispatchPlan> list) {
//		logger.info("*******************************PhonesSuccHandler***********************************");
//		logger.info("*******************************PhonesSuccHandler***********************************");
//		logger.info("*******************************PhonesSuccHandler***********************************");
//		
////		List<DispatchPlan> list = dispatchPlanService.selectPhoneByDateAndFlag(Constant.IS_FLAG_2,
////				Constant.STATUSPLAN_1, Constant.STATUS_SYNC_0);
//		logger.info(" 获取可以拨打的号码count ..." + list.size());
//		// 分组
//		Map<String, List<DispatchPlan>> collect = list.stream().collect(Collectors.groupingBy(d -> fetchGroupKey(d)));
//		for (Entry<String, List<DispatchPlan>> entry : collect.entrySet()) {
//			if (redisUtil.get(entry.getKey().split("-")[1]) != null) {
//				logger.info("当前模板id正在升级中...." + entry.getKey().split("-")[1]);
//				continue;
//			}
//			// 通知呼叫计划
//			checkDateandCallStartPlan(entry.getKey());
//		}
//	}
//
//	/**
//	 * 分组排序字段
//	 * 
//	 * @param detail
//	 * @return
//	 */
//	private static String fetchGroupKey(DispatchPlan detail) {
//		return String.valueOf(detail.getUserId() + "-" + detail.getRobot() + "-" + detail.getLine());
//	}
//
//	/**
//	 * 启动呼叫计划
//	 * 
//	 * @param key
//	 */
//	private void checkDateandCallStartPlan(String key) {
//		Object object = redisUtil.get(key);
//		// logger.info("checkRedisAndDate key result:" + object);
//		if (object != null && object != "") {
//			logger.info("当前推送已经推送过：在失效时间内，不重复推送:" + key);
//		} else {
//			String[] split = key.split("-");
//			HashMap map = (HashMap) redisUtil.hmget("dispath-userIds");
//			if (map == null) {
//				map = new HashMap<>();
//			}
//			map.put(key, key);
//			redisUtil.hmset("dispath-userIds", map);
////			ReturnData<Boolean> startcallplan = callManagerOutApi.startCallPlan(split[0], split[1], split[2]);
////			logger.info("-------------启动客户呼叫计划结果------------------" + startcallplan.success);
////			logger.info("-------------启动客户呼叫计划结果详情---------------" + startcallplan.msg);
////			if (startcallplan.success) {
////				// 5分钟失效时间
////				redisUtil.set(key, new Date(), 300);
////				// 通知成功put 数据
////				putRedisByphones(key);
////			}
//		}
//	}
//
//	/**
//	 * 将当前号码放入redis
//	 */
//	private void putRedisByphones(String key) {
//		Map<String, String> map = (HashMap) redisUtil.hmget("dispath-userIds");
//		logger.info("-------------------当前redis-----dispath-userIds数据:---------------" + map);
//		if (map == null) {
//			return;
//		}
//		for (Entry<String, String> ent : map.entrySet()) {
//			String[] split = ent.getKey().split("-");
//			Integer limit = patchPlanPutCalldata.getQuerySize(Integer.valueOf(split[0]), Integer.valueOf(split[2]));
//			if (limit > 0) {
//				List<DispatchPlan> list = dispatchPlanService.selectPhoneByDate4Redis(Integer.valueOf(split[0]),
//						Constant.IS_FLAG_2, limit, Integer.valueOf(split[2]));
//				if (list.size() > 0) {
//					patchPlanPutCalldata.put(Integer.valueOf(split[0]), Integer.valueOf(split[2]), list);
//				}
//				List<String> ids = new ArrayList<>();
//				for (DispatchPlan dis : list) {
//					ids.add(dis.getPlanUuidLong());
//				}
//				if (ids.size() > 0) {
//					dispatchMapper.updateDispatchPlanListByStatusSYNC(ids, Constant.STATUS_SYNC_1);
//				}
//			}
//		}
//		logger.info("-----------------------当前redis推送完毕-----------------");
//		String[] split = key.split("-");
////		ReturnData<Boolean> startcallplan = callManagerOutApi.startCallPlan(split[0], split[1], split[2]);
////		logger.info("-------------当前redis推送完毕启动客户呼叫计划结果------------------" + startcallplan.success);
////		logger.info("-------------当前redis推送完毕启动客户呼叫计划结果详情---------------" + startcallplan.msg);
//	}
//
//}
