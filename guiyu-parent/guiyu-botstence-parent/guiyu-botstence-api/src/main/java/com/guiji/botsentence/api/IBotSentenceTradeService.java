package com.guiji.botsentence.api;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import com.guiji.botsentence.api.entity.BotSentenceTemplateTradeVO;
import com.guiji.botsentence.api.entity.ServerResult;

/**
 * 行业相关服务类
 * @author 张朋
 *
 */
public interface IBotSentenceTradeService {

	/**
	 * 查询所有行业树型结构
	 * @return
	 */
	@RequestMapping(value = "/botsentenceServer/queryAllTradeList")
	public ServerResult<List<BotSentenceTemplateTradeVO>> queryAllTradeList();
	
	/**
	 * 根据行业ID查询行业名称
	 * @return
	 */
	@RequestMapping(value = "/botsentenceServer/queryTradeByTradeId")
	public ServerResult<List<BotSentenceTemplateTradeVO>> queryTradeByTradeId(String tradeId);
}
