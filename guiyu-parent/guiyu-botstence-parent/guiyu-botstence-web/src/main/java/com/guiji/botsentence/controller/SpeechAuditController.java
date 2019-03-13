package com.guiji.botsentence.controller;

import com.alibaba.fastjson.JSON;
import com.guiji.botsentence.api.entity.*;
import com.guiji.botsentence.service.IKeywordsAuditService;
import com.guiji.common.model.Page;
import com.guiji.component.result.ServerResult;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("keywordAudit")
public class SpeechAuditController {

    private final Logger logger = LoggerFactory.getLogger(SpeechAuditController.class);

    @Resource
    private IKeywordsAuditService iKeywordsAuditService;

    @RequestMapping(value="list")
    public ServerResult<Page<KeywordsAuditRpsVO>> getKeywordAuditList(@RequestBody KeywordsAuditListReqVO keywordsAuditListReqVO) {
        logger.info("get keyword audit list req:{}", JSON.toJSONString(keywordsAuditListReqVO));
        try{
            return iKeywordsAuditService.getKeywordAuditList(keywordsAuditListReqVO);
        }catch (Exception e){
            logger.error("failed get keyword audit list", e);
            return ServerResult.createByError();
        }
    }

    @RequestMapping("items")
    public ServerResult<List<KeywordAuditItemRpsVO>> getItemListByStatus(@RequestBody KeywordAuditItemReqVO keywordAuditItemReqVO){
        logger.info("get keyword audit item list req:{}", JSON.toJSONString(keywordAuditItemReqVO));

        try{
            return iKeywordsAuditService.getItemListByStatus(keywordAuditItemReqVO);
        }catch (Exception e){
            logger.error("failed get keyword audit item list", e);
            return ServerResult.createByError();
        }
    }

    @RequestMapping("batch")
    public ServerResult<String> batchAuditKeywordItem(@RequestBody KeywordBatchAuditItemReqVO keywordBatchAuditItemReqVO, @RequestHeader Integer userId){
        keywordBatchAuditItemReqVO.setAuditUserId(userId);

        logger.info("batch audit keyword item req:{}", JSON.toJSONString(keywordBatchAuditItemReqVO));

        if(CollectionUtils.isEmpty(keywordBatchAuditItemReqVO.getKeywordAuditItemIds())){
            return ServerResult.createByErrorMessage("keyword item can not be empty");
        }

        try{
            iKeywordsAuditService.batchAuditKeywordItem(keywordBatchAuditItemReqVO);
            return ServerResult.createBySuccess("success batch audit keyword item");
        }catch (Exception e){
            logger.error("failed batch audit keyword item", e);
            return ServerResult.createByError();
        }
    }
}
