package com.guiji.dispatch.blacklistmq;

import com.guiji.dispatch.dao.entity.BlackList;

public interface BlackListImport {
	public void execute(BlackList blackList);
}
