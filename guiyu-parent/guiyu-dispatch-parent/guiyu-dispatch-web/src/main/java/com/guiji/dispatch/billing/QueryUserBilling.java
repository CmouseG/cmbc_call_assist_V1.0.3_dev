package com.guiji.dispatch.billing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.guiji.api.IAcctUser;
import com.guiji.utils.RedisUtil;
import com.guiji.vo.ArrearageNotifyVo;

/**
 * 系统启动的时候查询当前欠费的用户
 * @author Administrator
 *
 */
@Component
public class QueryUserBilling implements ApplicationRunner {
	static Logger logger = LoggerFactory.getLogger(QueryUserBilling.class);
	@Autowired
	private RedisUtil redisUtils;
	@Autowired
	private IAcctUser accountUser;
	@Override
	public void run(ApplicationArguments args) throws Exception {
		logger.info("系统启动查询前费用用户>>>>>>>>>>>>>>>>");
		ArrearageNotifyVo queryArrearageUserList = accountUser.queryArrearageUserList();
		redisUtils.set("USER_BILLING_DATA",queryArrearageUserList.getUserIdList());
	}


}
