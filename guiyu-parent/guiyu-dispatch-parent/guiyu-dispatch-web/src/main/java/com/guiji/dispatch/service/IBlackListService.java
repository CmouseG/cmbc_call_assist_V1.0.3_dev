package com.guiji.dispatch.service;

import com.guiji.common.model.Page;
import com.guiji.dispatch.dao.entity.BlackList;

public interface IBlackListService {
	
	boolean save(BlackList blackList);
	
	boolean delete(Long id);
	
	boolean update(BlackList blackList);
	
	public Page<BlackList> queryBlackListByParams(int pagenum,int pagesize);
	
}
