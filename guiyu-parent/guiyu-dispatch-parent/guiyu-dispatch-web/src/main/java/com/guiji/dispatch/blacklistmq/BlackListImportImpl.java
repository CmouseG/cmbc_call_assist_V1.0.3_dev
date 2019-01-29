package com.guiji.dispatch.blacklistmq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.guiji.dispatch.dao.BlackListMapper;
import com.guiji.dispatch.dao.entity.BlackList;


@Service
public class BlackListImportImpl implements BlackListImport {

	@Autowired
	private BlackListMapper balckMapper;
	
	@Async("asyncProcessBlackList")
	@Override
	public void execute(BlackList blackList) {
		balckMapper.insert(blackList);
	}

}
