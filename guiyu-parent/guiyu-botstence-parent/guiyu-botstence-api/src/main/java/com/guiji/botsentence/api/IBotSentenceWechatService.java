package com.guiji.botsentence.api;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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

	@RequestMapping(value="/wechat/queryBotSentenceProcessListByAccountNo")
	public ServerResult<List<BotSentenceProcessVO>> queryBotSentenceProcessListByAccountNo(@RequestParam("userId") String userId);
	
	@RequestMapping(value="/wechat/uploadOneVolice")
	@Transactional
	public ServerResult<VoliceInfoExt> uploadOneVolice(MultipartFile multipartFile,@RequestParam("processId") String processId, @RequestParam("voliceId") String voliceId,@RequestParam("type") String type, @RequestParam("userId")String userId);
	
	@PostMapping("/wechat/selftest")
    public ServerResult<ResponseSelfTestVO> selfTest(@RequestBody SelfTestVO request, @RequestParam("userId") String userId);
	
	@PostMapping("/wechat/endtest")
    public ServerResult<String> endTest(@RequestBody SelfTestVO request);
	
	@RequestMapping("/wechat/queryVoliceListSimple")
	public ServerResult<List<VoliceInfoExt>> queryVoliceListSimple(@RequestParam("processId") String processId);
	
	@RequestMapping(value="/wechat/deleteAllVolice")
	public ServerResult<String> deleteAllVolice(@RequestParam("processId") String processId);
	
	@RequestMapping(value="/wechat/queryBotSentenceProcessByProcessId")
	public ServerResult<BotSentenceProcessVO> queryBotSentenceProcessByProcessId(@RequestParam("processId") String processId);
	
}
