package com.guiji.dispatch.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guiji.dispatch.dao.DispatchLogMapper;
import com.guiji.dispatch.dao.entity.DispatchLog;
import com.guiji.dispatch.service.OperatorLogService;

@Service
public class OperatorLogServiceImpl implements OperatorLogService {

	@Autowired
	private DispatchLogMapper dispatchLogMapper;
	
	@Override
	public void saveOperatorLog(DispatchLog dispatchLog) {
		dispatchLogMapper.insert(dispatchLog);
	}

}
