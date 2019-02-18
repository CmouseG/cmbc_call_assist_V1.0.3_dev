package com.guiji.dispatch.billing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.guiji.api.IAcctUser;
import com.guiji.utils.RedisUtil;
import com.guiji.vo.ArrearageNotifyVo;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;


/**
 * 每5分钟主动询问billing接口更新欠费账号
 *
 */
@JobHandler(value="BillingRefHandler")
@Component
public class BillingRefHandler extends IJobHandler{
	static Logger logger = LoggerFactory.getLogger(BillingRefHandler.class);
	@Autowired
	private IAcctUser accountUser;
	@Autowired
	private RedisUtil redisUtils;
	
	@Override
	public ReturnT<String> execute(String arg0) throws Exception {
		logger.info("每5分钟主动询问billing接口更新欠费账号");
		ArrearageNotifyVo queryArrearageUserList = accountUser.queryArrearageUserList();
		redisUtils.set("USER_BILLING_DATA", queryArrearageUserList.getUserIdList());
		return SUCCESS;
	}

}
