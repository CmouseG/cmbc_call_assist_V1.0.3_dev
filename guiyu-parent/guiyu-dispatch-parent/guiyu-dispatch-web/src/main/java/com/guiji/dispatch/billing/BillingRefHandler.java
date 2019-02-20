package com.guiji.dispatch.billing;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.guiji.api.IAcctUser;
import com.guiji.component.result.Result.ReturnData;
import com.guiji.utils.JsonUtils;
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
		ReturnData<ArrearageNotifyVo> queryBillingResult = accountUser.queryArrearageUserList();
		if(queryBillingResult.getBody()!=null){
			ArrearageNotifyVo queryArrearageUserList = queryBillingResult.getBody();
			List<String> userIdList = queryArrearageUserList.getUserIdList();
			if(userIdList == null){
				userIdList = new ArrayList<>();
			}
			redisUtils.set("USER_BILLING_DATA", userIdList);
		}else{
			logger.info("当前查询billing接口返回null");
		}
		
		
		return SUCCESS;
	}

}
