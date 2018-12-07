package com.guiji.da.api;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.guiji.component.result.Result;

import io.swagger.annotations.Api;

/** 
* @ClassName: IDaRemote 
* @Description: 量化分析对外服务
* @date 2018年12月7日 下午13:29:45 
* @version V1.0  
*/
@Api(tags="量化分析")
@FeignClient("guiyu-da-web")
public interface IDaRemote {
	
	/**
	 * 接收SELLBOT通话分析回调
	 * @param sellbotJson
	 * @return
	 */
	@RequestMapping(value = "/remote/receiveSellbotCallback", method = RequestMethod.POST)
	public Result.ReturnData receiveSellbotCallback(@RequestParam(value="sellbotJson",required=true)String sellbotJson);
	
}
