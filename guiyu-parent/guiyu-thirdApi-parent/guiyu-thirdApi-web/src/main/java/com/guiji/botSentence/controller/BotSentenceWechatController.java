package com.guiji.botSentence.controller;


import com.guiji.botsentence.api.IBotSentenceTradeService;
import com.guiji.botsentence.api.IBotSentenceWechatService;
import com.guiji.botsentence.api.entity.BotSentenceProcessVO;
import com.guiji.botsentence.api.entity.BotSentenceTradeVO;
import com.guiji.botsentence.api.entity.ResponseSelfTestVO;
import com.guiji.botsentence.api.entity.SelfTestVO;
import com.guiji.botsentence.api.entity.VoliceInfoExt;
import com.guiji.component.result.Result;
import com.guiji.component.result.ServerResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class BotSentenceWechatController {

    @Autowired
    IBotSentenceWechatService botSentenceWechatService;
    @Autowired
    IBotSentenceTradeService botSentenceTradeService;
    

    @RequestMapping(value = "queryBotSentenceProcessListByAccountNo")
    public ServerResult<List<BotSentenceProcessVO>> queryBotSentenceProcessListByAccountNo(@RequestHeader String userId) {
        return botSentenceWechatService.queryBotSentenceProcessListByAccountNo(userId);
    }


    @RequestMapping(value = "uploadOneVolice")
    public ServerResult<VoliceInfoExt> uploadOneVolice(MultipartFile multipartFile, @RequestParam("processId") String processId,
                                                       @RequestParam("voliceId") String voliceId, @RequestParam("type") String type,
                                                       @RequestHeader String userId) {
        return botSentenceWechatService.uploadOneVolice(multipartFile, processId,
                voliceId, type, userId);
    }

    @RequestMapping("selftest")
    public ServerResult<ResponseSelfTestVO> selfTest(@RequestBody SelfTestVO request, @RequestHeader String userId) {
        return botSentenceWechatService.selfTest(request, userId);
    }

    @RequestMapping("endtest")
    public ServerResult<String> endTest(@RequestBody SelfTestVO request) {
        return botSentenceWechatService.endTest(request);
    }

    @RequestMapping(value="queryVoliceListSimple")
	public ServerResult<List<VoliceInfoExt>> queryVoliceListSimple(@RequestParam("processId") String processId) {
		return botSentenceWechatService.queryVoliceListSimple(processId);
	}

    @RequestMapping(value="deleteAllVolice")
   	public ServerResult<String> deleteAllVolice(@RequestParam("processId") String processId) {
   		return botSentenceWechatService.deleteAllVolice(processId);
   	}
    
    @RequestMapping(value="queryBotSentenceProcessByProcessId")
	public ServerResult<BotSentenceProcessVO> queryBotSentenceProcessByProcessId(@RequestParam("processId") String processId){
    	return botSentenceWechatService.queryBotSentenceProcessByProcessId(processId);
    }
}
