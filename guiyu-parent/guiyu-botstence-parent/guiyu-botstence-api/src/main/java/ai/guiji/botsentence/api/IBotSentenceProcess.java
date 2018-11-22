package ai.guiji.botsentence.api;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ai.guiji.botsentence.api.entity.BotSentenceProcessVO;
import ai.guiji.botsentence.api.entity.ServerResult;

@FeignClient("guiyu-botstence-web")
public interface IBotSentenceProcess {
	
	@RequestMapping(value="/botSentenceProcess/createBotSentenceProcess")
	public ServerResult<String> createBotSentenceProcess(@RequestParam("paramVO")BotSentenceProcessVO paramVO,@RequestParam("userId") Long userId);
}
