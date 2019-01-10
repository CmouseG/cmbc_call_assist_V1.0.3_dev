package com.guiji.dispatch.pushcallcenter;
//package com.guiji.dispatch.test;
//
//import java.util.List;
//
//import com.guiji.dispatch.dao.entity.DispatchPlan;
//
//public interface IGetPhonesInterface {
//	/**
//	 * 根据用户名和线路查询任务
//	 * 
//	 * @param userId
//	 * @param lineId
//	 * @param limit
//	 * @return List<DispatchPlan>
//	 */
//	public List<DispatchPlan> getPhonesByParams(Integer userId, Integer lineId, String callHour, Integer limit);
//
//	/**
//	 * @param 根据uuid修改同步状态
//	 * @return boolean
//	 */
//	public boolean resetPhoneSyncStatus(List<String> planuuidIds);
//
//	/**
//	 * 找出当前可以拨打的号码用户
//	 * 
//	 * @param callHour
//	 * @return userids
//	 */
//	public List<Integer> getUserIdsByCallHour(String callHour);
//
//	/**
//	 * 
//	 * @param callhour
//	 * @param userId
//	 * @return 返回线路
//	 */
//	public List<Integer> getPhonesByCallHourAndUserId(String callhour, Integer userId);
//
//	public List<Integer> getUsersByParams(Integer statusPlan, Integer statusSync, String flag);
//
//	List<DispatchPlan> getUsersByParamsByUserId(Integer userId, Integer limit, Integer statusPlan, Integer statusSync,
//			String flag);
//
//	Integer  getCountByUserId(Integer userId,Integer statusPlan,Integer statusSync,String flag);
//}
