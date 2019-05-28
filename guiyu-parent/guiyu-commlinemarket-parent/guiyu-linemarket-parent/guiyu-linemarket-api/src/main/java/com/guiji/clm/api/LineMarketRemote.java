package com.guiji.clm.api;

import java.util.List;

import com.guiji.clm.model.PortLimitVo;
import com.guiji.clm.model.SimLineStatus;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.guiji.clm.model.SipLineVO;
import com.guiji.component.result.Result;

import io.swagger.annotations.Api;

/** 
* @ClassName: LineMarketRemote
* @Description: 线路市场服务
* @date 2019年1月29日 上午10:29:45 
* @version V1.0  
*/
@Api(tags="线路市场服务")
@FeignClient("guiyu-linemarket-web")
public interface LineMarketRemote {
	
	/**
	 * 查询用户SIP线路列表
	 * @param userId
	 * @return
	 */
    @PostMapping(value = "/remote/queryUserSipLineList")
	Result.ReturnData<List<SipLineVO>> queryUserSipLineList(@RequestParam(value="userId",required=true) String userId);
    
    
    /**
	 * 查询用户一条SIP线路
	 * @param id
	 * @return
	 */
    @PostMapping(value = "/remote/querySipLineById")
	Result.ReturnData<SipLineVO> queryUserSipLineList(@RequestParam(value="id",required=true) Integer id);
    
    /**
	 * 查询用户SIP线路列表
	 * @param userId
	 * @param lineId
	 * @return
	 */
    @PostMapping(value = "/remote/queryUserSipLineByLineId")
	public Result.ReturnData<SipLineVO> queryUserSipLineByLineId(
			@RequestParam(value="userId",required=true) String userId,
			@RequestParam(value="lineId",required=true) Integer lineId);

	/**
	 * 查询用户SIM卡线路对应端口状态
	 * @param userId
	 * @param lineId
	 * @return
	 */
	@PostMapping(value = "/remote/querySimLineStatus")
	public Result.ReturnData<SimLineStatus> querySimLineStatus(@RequestParam(value="lineId",required=true) Integer lineId);


	@PostMapping(value = "/remote/querySimLineLimitInfo")
	Result.ReturnData<List<PortLimitVo>> querySimLineLimitInfo(@RequestParam(value="lineId",required=true) Integer lineId);

}
