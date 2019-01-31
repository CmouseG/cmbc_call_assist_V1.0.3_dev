package com.guiji.botsentence.api;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.guiji.botsentence.api.entity.BotSentenceProcessVO;
import com.guiji.botsentence.api.entity.ResponseSelfTestVO;
import com.guiji.botsentence.api.entity.SelfTestVO;
import com.guiji.botsentence.api.entity.VoliceInfoExt;
import com.guiji.component.result.ServerResult;

/**
 * 硅基快配音小程序对外服务类
 * @author 张朋
 *
 */
@FeignClient("guiyu-botstence-web")
public interface IBotSentenceWechatService {

	@RequestMapping(value="updateAccount")
	public ServerResult<String> updateAccount(@RequestParam("accountNo") String accountNo, @RequestParam("password") String password);
	
	@RequestMapping(value="queryBotSentenceProcessListByAccountNo")
	public ServerResult<List<BotSentenceProcessVO>> queryBotSentenceProcessListByAccountNo(@RequestParam("accountNo") String accountNo);
	
	
	@RequestMapping(value="uploadOneVolice")
	@Transactional
	public ServerResult<VoliceInfoExt> uploadOneVolice(MultipartFile multipartFile,@RequestParam("processId") String processId, @RequestParam("voliceId") String voliceId,@RequestParam("type") String type, @RequestHeader String userId);
	
	@PostMapping("/selftest")
    public ServerResult<ResponseSelfTestVO> selfTest(@RequestParam("request") SelfTestVO request, @RequestHeader String userId);
	
	@PostMapping("/endtest")
    public ServerResult<String> endTest(@RequestParam("request") SelfTestVO request);
	
	
	
}
