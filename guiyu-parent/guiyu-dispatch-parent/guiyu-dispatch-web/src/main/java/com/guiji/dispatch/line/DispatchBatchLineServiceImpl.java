package com.guiji.dispatch.line;

import com.guiji.auth.api.IOrg;
import com.guiji.ccmanager.api.ILineRate;
import com.guiji.ccmanager.entity.LineRateResponse;
import com.guiji.clm.api.LineSipRouteRemote;
import com.guiji.clm.model.SipRouteItemVO;
import com.guiji.clm.model.SipRouteRuleVO;
import com.guiji.component.result.Result;
import com.guiji.component.result.Result.ReturnData;
import com.guiji.dispatch.constant.RedisConstant;
import com.guiji.dispatch.dao.DispatchBatchLineMapper;
import com.guiji.dispatch.dao.DispatchPlanMapper;
import com.guiji.dispatch.dao.entity.DispatchBatchLine;
import com.guiji.dispatch.dao.entity.DispatchBatchLineExample;
import com.guiji.dispatch.dao.entity.DispatchPlan;
import com.guiji.dispatch.enums.PlanLineTypeEnum;
import com.guiji.dispatch.model.RateTimeReq;
import com.guiji.dispatch.service.GetApiService;
import com.guiji.dispatch.service.LineMarketService;
import com.guiji.dispatch.util.ResHandler;
import com.guiji.user.dao.entity.SysUser;
import com.guiji.utils.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;
@Service
public class DispatchBatchLineServiceImpl implements IDispatchBatchLineService
{
	private static final Logger logger = LoggerFactory.getLogger(IDispatchBatchLineService.class);

	@Autowired
	DispatchBatchLineMapper dispatchBatchLineMapper;

	@Autowired
	private LineSipRouteRemote remote;
	@Autowired
	private DispatchPlanMapper dispatchPlanMapper;
	@Autowired
	private RedisUtil redisUtils;
	@Autowired
	private ILineRate lineRate;

	@Autowired
	private IOrg orgService;


	
	@Override
	public List<DispatchBatchLine> queryListByBatchId(Integer batchId)
	{
		DispatchBatchLineExample example = new DispatchBatchLineExample();
		example.createCriteria().andBatchIdEqualTo(batchId);
		return dispatchBatchLineMapper.selectByExample(example);
	}

	@Override
	public List<DispatchBatchLine> queryListByLineId(Integer lineId)
	{
		DispatchBatchLineExample example = new DispatchBatchLineExample();
		example.createCriteria().andLineIdEqualTo(lineId);
		return dispatchBatchLineMapper.selectByExample(example);
	}

	@Override
	public List<DispatchBatchLine> queryListByBatchLineId(Integer batchId, Integer lineId)
	{
		DispatchBatchLineExample example = new DispatchBatchLineExample();
		example.createCriteria().andBatchIdEqualTo(batchId).andLineIdEqualTo(lineId);
		return dispatchBatchLineMapper.selectByExample(example);
	}

	@Override
	public List<DispatchBatchLine> queryListByUserId(Long userId)
	{
		DispatchBatchLineExample example = new DispatchBatchLineExample();
		example.createCriteria().andUserIdEqualTo(userId.intValue());
		return dispatchBatchLineMapper.selectByExample(example);
	}

    @Override
    public List<DispatchBatchLine> queryListByUserIdLineId(Long userId, Integer lineId) {
        DispatchBatchLineExample example = new DispatchBatchLineExample();
        example.createCriteria().andUserIdEqualTo(userId.intValue()).andLineIdEqualTo(lineId);
        return dispatchBatchLineMapper.selectByExample(example);
    }

	@Override
	public List<DispatchBatchLine> queryListByUserIdLineId(List<Integer> userIdList, Integer lineId) {
		DispatchBatchLineExample example = new DispatchBatchLineExample();
		example.createCriteria().andUserIdIn(userIdList).andLineIdEqualTo(lineId);
		return dispatchBatchLineMapper.selectByExample(example);
	}

	@Override
	public void insert(DispatchBatchLine dispatchBatchLine)
	{
		dispatchBatchLineMapper.insertSelective(dispatchBatchLine);
	}

	@Override
	public void deleteByBatchId(Integer batchId)
	{
		DispatchBatchLineExample example = new DispatchBatchLineExample();
		example.createCriteria().andBatchIdEqualTo(batchId);
		dispatchBatchLineMapper.deleteByExample(example);
	}

	@Override
	public void getLineRule() {/*
		DispatchPlan param = new DispatchPlan();
		param.setCallData(Integer.valueOf(new SimpleDateFormat("yyyyMMdd").format(new Date())));
		// 查询用户
		List<DispatchPlan> selectPlanGroupByUserId = dispatchPlanMapper.selectPlanGroupByUserId(param, getAllOrgIds());
		// 根据用户查询规则
		for (DispatchPlan dis : selectPlanGroupByUserId) {
			// 去重查询任务中心用户
			ReturnData<List<SipRouteRuleVO>> querySipRouteRule = remote
					.querySipRouteRule(String.valueOf(dis.getUserId()));
			if (querySipRouteRule.getBody() != null) {
				// 查询每个用户的分配规则
				redisUtils.set("LINE_RULE_USER_ID_" + dis.getUserId(), querySipRouteRule.getBody());
			} else {
				logger.info("查询用户sip线路路由规则为null>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
			}
		}
	*/}

	@Override
	public void getLineRate() {
		try {
			RateTimeReq rateTime = new RateTimeReq();
			rateTime.setStartTime(getStartTime());
			rateTime.setEndTime(getnowEndTime());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String startTime = sdf.format(rateTime.getStartTime());
			String endTime = sdf.format(rateTime.getEndTime());
			//查询两个月的接通率
			ReturnData<List<LineRateResponse>> lineRateAll = lineRate.getLineRateAll(startTime, endTime);
			if (lineRateAll.getBody() != null) {
				logger.info("查询线路监控数量>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + lineRateAll.getBody().size());
				redisUtils.set("LINE_RATE_DATA", lineRateAll.getBody());
			} else {
				logger.info("查询线路监控为null>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
			}
		}catch(Exception e){
			logger.error("查询两个月接通率异常", e);
		}
	}

	/**
	 * 批量排序
	 * @param list
	 * @return
	 */
	@Override
	public List<DispatchPlan> sortLine(List<DispatchPlan> list) {
		List<DispatchPlan> res = new ArrayList<>();
		for (DispatchPlan dis : list) {
			// 线路一条的话就不排序0
			if (dis.getLines().size() <= 1) {
				res.add(dis);
				continue;
			}

			// 线路一条的话就不排序0
			if (dis.getLineType() == PlanLineTypeEnum.GATEWAY.getType()) {
				res.add(dis);
				continue;
			}
			// 获取用户的排序规则
			List<SipRouteRuleVO> userRule = (List<SipRouteRuleVO>) redisUtils
					.get(RedisConstant.RedisConstantKey.lineKey.LINE_RULE_USER_ID + dis.getUserId());
			if(null == userRule){
				userRule = ResHandler.getResObj(remote.querySipRouteRule(String.valueOf(dis.getUserId())));
				if(null != userRule && userRule.size()>0
					&& !StringUtils.isEmpty(userRule.get(0).getRuleContent())){
					// 查询每个用户的分配规则
					redisUtils.set(RedisConstant.RedisConstantKey.lineKey.LINE_RULE_USER_ID + dis.getUserId(), userRule,
							RedisConstant.RedisConstantKey.lineKey.USER_LINE_RULE_TIMEOUT);
				} else {
					logger.info("查询用户ID:{},sip线路路由规则为null,不排序",dis.getUserId());
					return new ArrayList<>();
				}
			}

			// 获取不同用户排序规则
			SipRouteRuleVO ruleVO = getRule(dis, userRule);
			if (ruleVO == null) {
				logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>当前redis用户没有线路规则去查询接口" + dis.getUserId());
				/*
				// 去接口查询
				ReturnData<List<SipRouteRuleVO>> querySipRouteRule = remote
						.querySipRouteRule(String.valueOf(dis.getUserId()));
				if (querySipRouteRule.getBody() != null) {
					if (querySipRouteRule.getBody().size() > 0) {
						List<SipRouteItemVO> itemList = querySipRouteRule.getBody().get(0).getItemList();
						// 排序算法
						List<DispatchBatchLine> sort = sort(dis, itemList);
						dis.setLines(sort);
						res.add(dis);
						continue;
					} else {
						logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>当前用户接口查询没有线路规则" + dis.getUserId());
						continue;
					}
				} else {
					logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>当前用户接口查询没有线路规则" + dis.getUserId());
					continue;
				}
				*/

				List<SipRouteItemVO> routeItemList = userRule.get(0).getItemList();
				if(null != routeItemList && routeItemList.size()>0){
					// 排序算法
					List<DispatchBatchLine> sort = sort(dis, routeItemList);
					dis.setLines(sort);
					res.add(dis);
					continue;
				}
			}
			// 排序算法
			List<DispatchBatchLine> sort = sort(dis, ruleVO.getItemList());
			dis.setLines(sort);
			res.add(dis);
		}
		return res;
	}

	/**
	 * 单个排序
	 * @param dis
	 * @return
	 */
	@Override
	public DispatchPlan sortLine(DispatchPlan dis) {
		// 线路一条的话就不排序0
		if (dis.getLines().size() <= 1) {
			return dis;
		}

		// 线路一条的话就不排序0
		if (dis.getLineType() == PlanLineTypeEnum.GATEWAY.getType()) {
			return dis;
		}
		// 获取用户的排序规则
		List<SipRouteRuleVO> userRule = (List<SipRouteRuleVO>) redisUtils
				.get(RedisConstant.RedisConstantKey.lineKey.LINE_RULE_USER_ID + dis.getUserId());
		if(null == userRule){
			userRule = ResHandler.getResObj(remote.querySipRouteRule(String.valueOf(dis.getUserId())));
			if(null != userRule && userRule.size()>0
					&& !StringUtils.isEmpty(userRule.get(0).getRuleContent())){
				// 查询每个用户的分配规则
				redisUtils.set(RedisConstant.RedisConstantKey.lineKey.LINE_RULE_USER_ID + dis.getUserId(), userRule,
						RedisConstant.RedisConstantKey.lineKey.USER_LINE_RULE_TIMEOUT);
			} else {
				logger.info("查询用户ID:{},sip线路路由没有线路规则,不排序",dis.getUserId());
				return dis;
			}
		}

		// 获取不同用户排序规则
		SipRouteRuleVO ruleVO = getRule(dis, userRule);
		if (ruleVO == null) {
			logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>当前redis用户没有线路规则去查询接口" + dis.getUserId());
			/*
			// 去接口查询
			ReturnData<List<SipRouteRuleVO>> querySipRouteRule = remote
					.querySipRouteRule(String.valueOf(dis.getUserId()));
			if (querySipRouteRule.getBody() != null) {
				if (querySipRouteRule.getBody().size() > 0) {
					List<SipRouteItemVO> itemList = querySipRouteRule.getBody().get(0).getItemList();
					// 排序算法
					List<DispatchBatchLine> sort = sort(dis, itemList);
					dis.setLines(sort);
					return dis;
				} else {
					logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>当前用户接口查询没有线路规则" + dis.getUserId());
					return dis;
				}
			} else {
				logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>当前用户接口查询没有线路规则" + dis.getUserId());
				return dis;
			}
			*/

			List<SipRouteItemVO> routeItemList = userRule.get(0).getItemList();
			if(null != routeItemList && routeItemList.size()>0){
				// 排序算法
				List<DispatchBatchLine> sort = sort(dis, routeItemList);
				dis.setLines(sort);
				return dis;
			}
		}
		// 排序算法
		List<DispatchBatchLine> sort = sort(dis, ruleVO.getItemList());
		dis.setLines(sort);

		return dis;
	}

	private SipRouteRuleVO getRule(DispatchPlan dis, List<SipRouteRuleVO> userRule) {
		for (SipRouteRuleVO vo : userRule) {
			if (dis.getUserId().equals(Integer.valueOf(vo.getUserId()))) {
				return vo;
			}
		}
		return null;
	}

	private List<DispatchBatchLine> sort(DispatchPlan dis, List<SipRouteItemVO> itemList) {
		// 线路 ,接通率， 費用
		// 1:按优先级赋权值 10000 100 1
		Map<String, BigDecimal> map = new HashMap<>();
		for (int i = 0; i < itemList.size(); i++) {
			if (i == 0) {
				map.put(itemList.get(i).getItemCode(), new BigDecimal(10000));
			} else if (i == 1) {
				map.put(itemList.get(i).getItemCode(), new BigDecimal(100));
			} else if (i == 2) {
				map.put(itemList.get(i).getItemCode(), new BigDecimal(1));
			}
		}
		// 地区计算
		Map<DispatchBatchLine, BigDecimal> lineCity = new HashMap<>();
		List<DispatchBatchLine> lines = dis.getLines();
		// 地区的权重值
		BigDecimal cityResultData = new BigDecimal(0);
		for (DispatchBatchLine line : lines) {
			// 获取号码归属地
			String cityName = dis.getCityName();
			// 如果号码归属地里面有符合线路归属地
			if (cityName.equals(line.getOvertarea())) {
				// 1
				lineCity.put(line, new BigDecimal(1));
			} else {
				// 0
				lineCity.put(line, new BigDecimal(0));
			}
		}

		// 计算针对地区所有汇总值
		Iterator<Entry<DispatchBatchLine, BigDecimal>> iterator = lineCity.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<DispatchBatchLine, BigDecimal> entry = iterator.next();
			// 拿到地区的初始化的权重值
			BigDecimal baseRate = map.get("01"); // 地区
			// 汇总计算 当前地区线路的总值
			cityResultData = cityResultData.add(baseRate.multiply(entry.getValue()));
		}
		// 排序结果
		Map<DispatchBatchLine, BigDecimal> sortByValueDescending = sortByValueDescending(lineCity);

		// -------------------------------------------------------------
		// -------------------------------------------------------------
		// -------------------------------------------------------------

		// 接通率权重值
		BigDecimal RateResultData = new BigDecimal(0);
		// 接通率排序
		List<LineRateResponse> lineRate = (List<LineRateResponse>) redisUtils.get("LINE_RATE_DATA");
		if (lineRate == null || lineRate.size() <= 0) {
			// 當前接通率data有問題
			logger.info("lineRate有問題>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		}

		Map<DispatchBatchLine, BigDecimal> lineRateMap = new HashMap<DispatchBatchLine, BigDecimal>();
		for (DispatchBatchLine line : dis.getLines()) {
			// 获取线路接通率
			LineRateResponse rate = getRate(line, lineRate);
			if (rate != null) {
				lineRateMap.put(line, new BigDecimal(rate.getRate()));
			}else{
				lineRateMap.put(line, new BigDecimal(0));
			}
		}
		// 排序结果
		Map<DispatchBatchLine, BigDecimal> sortByValueDescending2 = sortByValueDescending(lineRateMap);

		// 计算权重值
		int index = sortByValueDescending2.size();
		for (DispatchBatchLine key : sortByValueDescending2.keySet()) {
			BigDecimal baseRate = map.get("02"); // 接通率
			RateResultData = RateResultData.add(baseRate.multiply(new BigDecimal(index)
					.divide(new BigDecimal(sortByValueDescending2.size()), 2, BigDecimal.ROUND_HALF_UP)));
			index = index - 1;
		}
		// 判断当前能接通率是否都一样如果一样 则权重为0
		System.out.println();
		boolean rate = checkSame(sortByValueDescending2);
		if (rate) {
			RateResultData = new BigDecimal(0);
		}

		// -------------------------------------------------------------
		// -------------------------------------------------------------
		// -------------------------------------------------------------
		// 线路费用权重值
		BigDecimal amountResultData = new BigDecimal(0);
		// 费用
		Map<DispatchBatchLine, BigDecimal> amountMap = new HashMap<DispatchBatchLine, BigDecimal>();
		for (DispatchBatchLine line : dis.getLines()) {
			amountMap.put(line, line.getLineAmount());
		}
		// 根据费用给当前线路排序
		Map<DispatchBatchLine, BigDecimal> sortByValueDescending3 = sortByValueAscending(amountMap);
		int sortByValueDescending3index = sortByValueDescending3.size();

		for (DispatchBatchLine key : sortByValueDescending3.keySet()) {
			BigDecimal baseRate = map.get("03"); // 费用
			amountResultData = amountResultData.add(baseRate.multiply(new BigDecimal(sortByValueDescending3index)
					.divide(new BigDecimal(sortByValueDescending3.size()), 2, BigDecimal.ROUND_HALF_UP)));
			sortByValueDescending3index = sortByValueDescending3index - 1;
		}

		// 判断当前线路费用是否都一样 如果一样 则权重为0
		boolean amount = checkSame(sortByValueDescending3);
		if (amount) {
			amountResultData = new BigDecimal(0);
		}
		// 根据各个维度权重值算出最佳的排序规则
		Map<String, BigDecimal> resultMap = new HashMap<>();
		resultMap.put("01", cityResultData);
		resultMap.put("02", RateResultData);
		resultMap.put("03", amountResultData);

		Map<String, BigDecimal> sortByValueDescending4 = sortByValueDescending(resultMap);

		List<DispatchBatchLine> result = new ArrayList<>();
		for (String key : sortByValueDescending4.keySet()) {
			if (key.equals("01")) {
				get(sortByValueDescending, result);
				break;
			} else if (key.equals("02")) {
				get(sortByValueDescending2, result);
				break;
			} else if (key.equals("03")) {
				get(sortByValueDescending3, result);
				break;
			}
		}
		return result;
	}

	private void get(Map<DispatchBatchLine, BigDecimal> sortByValueDescending, List<DispatchBatchLine> result) {
		for (Map.Entry<DispatchBatchLine, BigDecimal> entry : sortByValueDescending.entrySet()) {
			result.add(entry.getKey());
		}
	}

	private LineRateResponse getRate(DispatchBatchLine line, List<LineRateResponse> lineRate2) {
		for (LineRateResponse rate : lineRate2) {
			if (rate.getLineId() == line.getLineId()) {
				return rate;
			}
		}
		logger.info("getRate 没有找到线路对应的接通率:" + line.getLineId());
		return null;
	}

	// 降序排序
	public static <K, V extends Comparable<? super V>> Map<K, V> sortByValueDescending(Map<K, V> map) {
		List<Map.Entry<K, V>> list = new LinkedList<Map.Entry<K, V>>(map.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
			@Override
			public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
				int compare = (o1.getValue()).compareTo(o2.getValue());
				return -compare;
			}
		});

		Map<K, V> result = new LinkedHashMap<K, V>();
		for (Map.Entry<K, V> entry : list) {
			result.put(entry.getKey(), entry.getValue());
		}
		return result;
	}

	//升序排序
	public static <K, V extends Comparable<? super V>> Map<K, V> sortByValueAscending(Map<K, V> map)
	{
		List<Map.Entry<K, V>> list = new LinkedList<Map.Entry<K, V>>(map.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<K, V>>()
		{
			@Override
			public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2)
			{
				int compare = (o1.getValue()).compareTo(o2.getValue());
				return compare;
			}
		});

		Map<K, V> result = new LinkedHashMap<K, V>();
		for (Map.Entry<K, V> entry : list) {
			result.put(entry.getKey(), entry.getValue());
		}
		return result;
	}


	private boolean checkSame(Map<DispatchBatchLine, BigDecimal> sortByValueDescending2) {
		BigDecimal baseFlag = new BigDecimal(0);
		int i = 0;
		for (DispatchBatchLine key : sortByValueDescending2.keySet()) {
			if (i == 0) {
				baseFlag = sortByValueDescending2.get(key);
			} else {
				if (sortByValueDescending2.get(key).compareTo(baseFlag) != 0) {
					return false;
				}
			}
			i++;
		}
		return true;
	}

	private List<Integer> getAllOrgIds()
	{
		Result.ReturnData<List<Integer>> resp = orgService.getAllOrgId();
		List<Integer> result = null;
		if (resp != null && resp.getBody() != null) {
			result = resp.getBody();
		}

		if(result == null)
		{
			result = new ArrayList<>();
		}

		return  result;
	}



	private static Date getStartTime() {
		Calendar calendar1 = Calendar.getInstance();
		calendar1.add(Calendar.MONTH, -1);
		calendar1.set(Calendar.DAY_OF_MONTH, 1);
		return calendar1.getTime();
	}

	private static Date getnowEndTime() {
		Calendar ca = Calendar.getInstance();
		ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
		return ca.getTime();
	}


	@Autowired
	GetApiService getApiService;

	@Autowired
	LineMarketService lineMarketService;


	/**
	 * @param batchId 批次id
	 * @param userId 用户id
	 * @param lineId 线路id
	 * @return
	 */
	@Override
	public DispatchBatchLine addByLineId(Integer batchId, Integer userId, Integer lineId) {
		SysUser sysUser = getApiService.getUserById(userId.toString());

		Integer orgId = getApiService.getOrgIdByUser(userId.toString());

		DispatchBatchLine dispatchBatchLine = lineMarketService.getByLineId(lineId, userId);

		dispatchBatchLine.setOrgId(orgId);
		dispatchBatchLine.setBatchId(batchId);

		dispatchBatchLineMapper.insert(dispatchBatchLine);

		return dispatchBatchLine;

	}

}


