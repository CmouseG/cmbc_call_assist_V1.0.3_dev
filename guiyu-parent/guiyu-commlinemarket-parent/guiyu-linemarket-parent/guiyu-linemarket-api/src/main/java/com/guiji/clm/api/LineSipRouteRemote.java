package com.guiji.clm.api;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.guiji.clm.model.SipRouteRuleVO;
import com.guiji.component.result.Result;

import io.swagger.annotations.Api;

/** 
* @ClassName: LineMarketRemote
* @Description: 线路市场服务-线路路由
* @date 2019年1月29日 上午10:29:45 
* @version V1.0  
*/
@Api(tags="线路市场服务-线路路由")
@FeignClient("guiyu-linemarket-web")
public interface LineSipRouteRemote {
	
	/**
	 * 查询用户sip线路路由规则
	 * @param userId
	 * @return
	 */
    @PostMapping(value = "/remote/querySipRouteRule")
	Result.ReturnData<List<SipRouteRuleVO>> querySipRouteRule(@RequestParam(value="userId",required=true) String userId);
    
    
}
