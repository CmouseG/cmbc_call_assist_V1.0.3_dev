package com.guiji.dispatch.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.sql.visitor.functions.Insert;
import com.guiji.common.model.Page;
import com.guiji.dispatch.controller.DispatchPlanController;
import com.guiji.dispatch.dao.BlackListMapper;
import com.guiji.dispatch.dao.entity.BlackList;
import com.guiji.dispatch.dao.entity.BlackListExample;
import com.guiji.dispatch.service.IBlackListService;
import com.guiji.utils.DateUtil;
import com.guiji.utils.RedisUtil;

import sun.util.logging.resources.logging;

@Service
public class BlackListServiceImpl implements IBlackListService {
	static Logger logger = LoggerFactory.getLogger(BlackListServiceImpl.class);
	@Autowired
	private BlackListMapper blackListMapper;
	@Autowired
	private RedisUtil redisUtil;

	@Override
	public boolean save(BlackList blackList) {
		try {
			blackList.setGmtCreate(DateUtil.getCurrent4Time());
			blackList.setGmtModified(DateUtil.getCurrent4Time());
		} catch (Exception e) {
			logger.error("error",e);
		}
		
		int result = blackListMapper.insert(blackList);
		// 更新到redis
		if (redisUtil.get("blackList") != null) {
			Map<String, BlackList> base = (Map) redisUtil.get("blackList");
			base.put(blackList.getPhone(), blackList);
			//更新
			redisUtil.set("blackList", base);
		} else {
			Map<String, BlackList> map = new HashMap<>();
			map.put(blackList.getPhone(), blackList);
			redisUtil.set("blackList", map);
		}
		return result > 0 ? true : false;
	}
	@Override
	public boolean delete(String phone) {
		BlackListExample ex = new BlackListExample();
		ex.createCriteria().andPhoneEqualTo(phone);
		int result = blackListMapper.deleteByExample(ex);
		if (redisUtil.get("blackList") != null) {
			Map<String, BlackList> base = (Map)redisUtil.get("blackList");
			base.remove(phone);
			redisUtil.set("blackList", base);
		}
		return result > 0 ? true : false;
	}

	@Override
	public boolean update(BlackList blackList) {
		int result = blackListMapper.updateByExampleSelective(blackList, new BlackListExample());
		delete(blackList.getPhone());
		save(blackList);
		return result > 0 ? true : false;
	}

	@Override
	public Page<BlackList> queryBlackListByParams(int pagenum, int pagesize) {
		Page<BlackList> page = new Page<>();
		page.setPageNo(pagenum);
		page.setPageSize((pagesize));
		BlackListExample example = new BlackListExample();
		example.setLimitStart((pagenum - 1) * pagesize);
		example.setLimitEnd(pagesize);
		example.setOrderByClause("`gmt_create` DESC");
		List<BlackList> result = blackListMapper.selectByExample(example);
		int countByExample = blackListMapper.countByExample(example);
		page.setRecords(result);
		page.setTotal(countByExample);
		return page;
	}

}
