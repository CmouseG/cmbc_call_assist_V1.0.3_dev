package com.guiji.dispatch.line;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;

/**
 * 每五分钟获取所有线路接通率
 * @author Administrator
 *
 */
@JobHandler(value="RefreshLineDataHandler")
@Component
public class RefreshLineDataHandler extends IJobHandler{

	@Autowired
	private IDispatchBatchLineService lineService;

	@Override
	public ReturnT<String> execute(String arg0) throws Exception {
//		//根据用户更新用户规则(已经放到线路优先级处理中获取，或者系统启动时获取)
	//	lineService.getLineRule();
		//更新接通率
		lineService.getLineRate();
		return SUCCESS;
	}

}
