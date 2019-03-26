package com.guiji.botsentence.controller;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.guiji.botsentence.constant.Constant;
import com.guiji.botsentence.dao.entity.BotSentenceProcess;
import com.guiji.botsentence.manager.IAIManager;
import com.guiji.botsentence.service.ISelfTestService;
import com.guiji.botsentence.service.impl.BotSentenceApprovalServiceImpl;
import com.guiji.botsentence.service.impl.BotSentenceProcessServiceImpl;
import com.guiji.botsentence.vo.ResponseSelfTestVO;
import com.guiji.botsentence.vo.SelfTestVO;
import com.guiji.component.jurisdiction.Jurisdiction;
import com.guiji.component.result.ServerResult;

/**
 * @Auther: 魏驰
 * @Date: 2019/1/9 15:03
 * @Project：guiji-parent
 * @Description: 自测类辅助方法
 */
@Slf4j
@RestController
public class SelfTestController {
    @Autowired
    ISelfTestService selfTestService;
    
    @Autowired
    BotSentenceProcessServiceImpl botSentenceProcessService;
    
    @Autowired
    BotSentenceApprovalServiceImpl botSentenceApprovalService;
    
    @Autowired
    IAIManager iaiManager;

    @PostMapping("/selftest")
    @Jurisdiction("botsentence_maker_test")
    public ServerResult<ResponseSelfTestVO> selfTest(@RequestBody SelfTestVO request, @RequestHeader String userId){
        log.info("收到自测请求[{}]", request);
        if(StringUtils.isNotBlank(request.getTempId())) {
        	request.setTempId(request.getTempId().replace("_en", ""));
        }else {
        	return ServerResult.createByErrorMessage("话术模板为空!");
        }
        
        if(StringUtils.isBlank(request.getProcessId())) {
        	return ServerResult.createByErrorMessage("话术流程编号为空!");
        }
        ServerResult serverResult = null;
        
        //判断当前模板是否需要重新部署
        BotSentenceProcess process = botSentenceProcessService.queryBotsentenceProcessInfo(request.getProcessId());
        if(Constant.TEST_STATE_ONLINE.equals(process.getTestState())) {
        	log.info("当前话术模板已部署测试环境，可直接进行测试...");
        }else {
        	log.info("当前话术模板做过修改，需要重新部署...");
        	botSentenceApprovalService.deployTestSellbotByScp(request.getProcessId(), userId);
        }
        try{
            ResponseSelfTestVO result = selfTestService.makeTest(request, userId);
            log.info("测试返回结果为[{}]", result);
            serverResult = ServerResult.createBySuccess(result);
        }catch (Exception ex){
            log.warn("自测产生异常", ex);
            log.info("释放机器人..." + request.getUuid());
            selfTestService.endTest(request.getUuid());
            serverResult = ServerResult.createByErrorMessage(ex.getMessage());
        }

        log.info("自测返回结果[{}]", serverResult);
        return serverResult;
    }

    @PostMapping("/endtest")
    public ServerResult<String> endTest(@RequestBody SelfTestVO request){
        log.info("收到关闭自测请求[{}]", request);
        ServerResult serverResult = null;
        try{
            selfTestService.endTest(request.getUuid());
            serverResult = ServerResult.createBySuccess();
        }catch (Exception ex){
            log.warn("结束自测产生异常", ex);
            serverResult = ServerResult.createByErrorMessage(ex.getMessage());
        }

        log.info("结束自测返回结果[{}]", serverResult);
        return serverResult.createBySuccess("结束测试...");
    }
    
    
    @PostMapping("/endAll")
    public ServerResult endAll(){
    	iaiManager.endAll();
    	return ServerResult.createBySuccess();
    }
}
