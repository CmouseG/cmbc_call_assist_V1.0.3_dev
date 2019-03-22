package com.guiji.dispatch.line;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * 系统启动获取线路的接通率和线路费用 和 线路规则
 *
 * @author Administrator
 */
@Component
public class ThreadGetLineRuleAndRate implements ApplicationRunner {

	@Autowired
	private IDispatchBatchLineService lineService;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		//		//根据用户更新用户规则
		lineService.getLineRule();
		//		//更新接通率
		lineService.getLineRate();
	}
}
