package com.guiji.botsentence.api;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.guiji.botsentence.api.entity.BotSentenceTemplateTradeVO;
import com.guiji.botsentence.api.entity.BotSentenceTradeVO;
import com.guiji.botsentence.api.entity.ServerResult;
import com.guiji.component.result.Result;

/**
 * 行业相关服务类
 * @author 张朋
 *
 */
@FeignClient("guiyu-botstence-web")
public interface IBotSentenceTradeService {

	/**
	 * 查询所有行业树型结构
	 * @return
	 */
	@RequestMapping(value = "/botsentenceServer/queryTradeListByTradeIdList")
	public ServerResult<List<BotSentenceTemplateTradeVO>> queryTradeListByTradeIdList(@RequestParam("tradeIdList")List<String> tradeIdList);
	
	/**
	 * 查询所有行业树型结构
	 * @return
	 */
	@RequestMapping(value = "/botsentenceServer/queryTradeListByTradeIdList")
	public ServerResult<List<BotSentenceTemplateTradeVO>> queryTradeListByOrgCode(@RequestParam("tradeIdList")List<String> tradeIdList);
	
	
	/**
	 * 根据行业ID查询行业名称
	 * @return
	 */
	@RequestMapping(value = "/botsentenceServer/queryTradeByTradeId")
	public Result.ReturnData<BotSentenceTradeVO> queryTradeByTradeId(@RequestParam("tradeId")String tradeId);
	
	
	/**
	 * 查询所有行业树型结构
	 * @return
	 */
	@RequestMapping(value = "/botsentenceServer/queryAllTradeList")
	public ServerResult<List<BotSentenceTemplateTradeVO>> queryAllTradeList();
	
	
	/**
	 * 查询所有模板的树结构
	 * @return
	 */
	@RequestMapping(value = "/botsentenceServer/queryTradeListByTemplateIdList")
	public ServerResult<List<BotSentenceTemplateTradeVO>> queryTradeListByTemplateIdList(@RequestParam("templateIdList")List<String> templateIdList);
}
