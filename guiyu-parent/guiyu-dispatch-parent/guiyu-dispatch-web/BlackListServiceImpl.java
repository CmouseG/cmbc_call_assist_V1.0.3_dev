package com.guiji.dispatch.impl;

import java.util.List;

import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guiji.common.model.Page;
import com.guiji.dispatch.dao.BlackListMapper;
import com.guiji.dispatch.dao.entity.BlackList;
import com.guiji.dispatch.dao.entity.BlackListExample;
import com.guiji.dispatch.service.IBlackListService;

@Service
public class BlackListServiceImpl implements IBlackListService{

	@Autowired
	private BlackListMapper blackListMapper;
	
	@Override
	public boolean save(BlackList blackList) {
		int result = blackListMapper.insert(blackList);
		return result > 0 ? true : false;
	}

	@Override
	public boolean delete(Long id) {
		int result = blackListMapper.deleteByPrimaryKey(id);
		return result > 0 ? true : false;
	}

	@Override
	public boolean update(BlackList blackList) {
		int result = blackListMapper.updateByExampleSelective(blackList, new BlackListExample());
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
