package com.guiji.dispatch.billing;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.guiji.api.IAcctUser;
import com.guiji.component.result.Result.ReturnData;
import com.guiji.utils.RedisUtil;
import com.guiji.vo.ArrearageNotifyVo;

/**
 * 系统启动的时候查询当前欠费的用户
 * 
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
		new Thread(() -> {
			try {
				boolean initFlag = false;
				while (true) {
					try {
						if (!initFlag) {
							ReturnData<ArrearageNotifyVo> queryArrearageUserList = accountUser.queryArrearageUserList();
							if(queryArrearageUserList.getBody()!=null){
								List<String> userIdList = queryArrearageUserList.getBody().getUserIdList();
								if(userIdList == null){
									userIdList = new ArrayList<>();
								}
								redisUtils.set("USER_BILLING_DATA", userIdList);
								initFlag = true;
							}
			
						} else {
							break;
						}
					} catch (Exception e) {
						initFlag = false;
					}
					Thread.sleep(30000);
				}
			} catch (Exception e) {
				logger.error("QueryUserBilling:" + e.getMessage());
			}
		}).start();
	}

}
