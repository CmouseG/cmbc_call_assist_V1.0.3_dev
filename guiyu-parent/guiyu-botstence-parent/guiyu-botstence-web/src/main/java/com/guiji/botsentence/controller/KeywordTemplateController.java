package com.guiji.botsentence.controller;

import com.alibaba.fastjson.JSON;
import com.guiji.botsentence.api.entity.KeywordTemplateReqVO;
import com.guiji.botsentence.service.IKeywordTemplateService;
import com.guiji.component.result.ServerResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("keywordTemplate")
public class KeywordTemplateController {

    private Logger logger = LoggerFactory.getLogger(KeywordTemplateController.class);

    @Resource
    private IKeywordTemplateService iKeywordTemplateService;

    @RequestMapping("add")
    public ServerResult<String> addKeywordTemplate(@RequestBody KeywordTemplateReqVO keywordTemplateReqVO, @RequestHeader String userId) {
        keywordTemplateReqVO.setUserId(userId);
        logger.info("add keyword template req:{}", JSON.toJSONString(keywordTemplateReqVO));

        try{
            iKeywordTemplateService.addKeywordTemplate(keywordTemplateReqVO);
            return ServerResult.createBySuccess("success add keyword template");
        }catch (Exception e){
            logger.error("failed add keyword template", e);
            return ServerResult.createByErrorMessage(e.getMessage());
        }
    }
}
