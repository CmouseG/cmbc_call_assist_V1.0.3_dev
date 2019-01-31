package com.guiji.botSentence.controller;


import com.guiji.botsentence.api.IBotSentenceWechatService;
import com.guiji.botsentence.api.entity.BotSentenceProcessVO;
import com.guiji.botsentence.api.entity.ResponseSelfTestVO;
import com.guiji.botsentence.api.entity.SelfTestVO;
import com.guiji.botsentence.api.entity.VoliceInfoExt;
import com.guiji.component.result.ServerResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class BotSentenceWechatController {

    @Autowired
    IBotSentenceWechatService botSentenceWechatService;


    @RequestMapping(value = "updateAccount")
    public ServerResult<String> updateAccount(@RequestParam("accountNo") String accountNo,
                                              @RequestParam("password") String password) {
        return botSentenceWechatService.updateAccount(accountNo, password);
    }


    @RequestMapping(value = "queryBotSentenceProcessListByAccountNo")
    public ServerResult<List<BotSentenceProcessVO>> queryBotSentenceProcessListByAccountNo(@RequestParam("accountNo") String accountNo) {
        return botSentenceWechatService.queryBotSentenceProcessListByAccountNo(accountNo);
    }


    @RequestMapping(value = "uploadOneVolice")
    @Transactional
    public ServerResult<VoliceInfoExt> uploadOneVolice(MultipartFile multipartFile, @RequestParam("processId") String processId,
                                                       @RequestParam("voliceId") String voliceId, @RequestParam("type") String type,
                                                       @RequestHeader String userId) {
        return botSentenceWechatService.uploadOneVolice(multipartFile, processId,
                voliceId, type, userId);
    }

    @PostMapping("/selftest")
    public ServerResult<ResponseSelfTestVO> selfTest(@RequestParam("request") SelfTestVO request, @RequestHeader String userId) {
        return botSentenceWechatService.selfTest(request, userId);
    }

    @PostMapping("/endtest")
    public ServerResult<String> endTest(@RequestParam("request") SelfTestVO request) {
        return botSentenceWechatService.endTest(request);
    }


}
