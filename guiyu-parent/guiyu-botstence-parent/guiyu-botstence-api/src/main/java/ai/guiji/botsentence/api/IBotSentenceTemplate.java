package ai.guiji.botsentence.api;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ai.guiji.botsentence.api.entity.BotSentenceIndustryVO;
import ai.guiji.botsentence.api.entity.ServerResult;

@FeignClient("guiyu-botstence-web")
public interface IBotSentenceTemplate {
	
	/**
	 * 查询行业模板接口
	 * @param userId
	 * @return
	 */
	@RequestMapping(value="/botSentenceTemplate/queryIndustryTemplate")
	public ServerResult<List<BotSentenceIndustryVO>> queryIndustryTemplate(@RequestParam("userId")Long userId);
}
