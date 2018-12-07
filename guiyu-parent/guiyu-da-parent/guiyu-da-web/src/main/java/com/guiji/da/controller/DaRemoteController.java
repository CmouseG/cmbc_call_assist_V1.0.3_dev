package com.guiji.da.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.guiji.component.result.Result;
import com.guiji.da.api.IDaRemote;
import com.guiji.da.service.robot.ISellbotCallbackService;

/** 
* @ClassName: DaRemoteImpl 
* @Description: 量化分析对外服务
* @date 2018年12月7日 下午1:43:32 
* @version V1.0  
*/
@RestController
public class DaRemoteController implements IDaRemote{
	private final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	ISellbotCallbackService iSellbotCallbackService;
	
	/**
	 * 接收SELLBOT通话分析回调
	 * @param sellbotJson
	 * @return
	 */
	public Result.ReturnData receiveSellbotCallback(@RequestParam(value="sellbotJson",required=true)String sellbotJson){
		iSellbotCallbackService.receiveSellbotCallback(sellbotJson);
		return Result.ok();
	}
}
