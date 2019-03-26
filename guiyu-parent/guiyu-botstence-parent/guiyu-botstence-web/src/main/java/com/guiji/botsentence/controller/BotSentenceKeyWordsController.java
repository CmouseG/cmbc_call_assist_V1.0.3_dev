package com.guiji.botsentence.controller;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.guiji.botsentence.service.BotSentenceKeyWordsService;
import com.guiji.botsentence.service.IBotSentenceKeyWordsValidateService;
import com.guiji.botsentence.vo.*;
import com.guiji.component.client.config.JsonParam;
import com.guiji.component.jurisdiction.Jurisdiction;
import com.guiji.component.result.ServerResult;

import javax.websocket.server.PathParam;
import java.util.List;


/**
 * 关键词库
 *
 * @author ssy
 * @Description:
 * @date 2019年1月09日
 */
@RestController
@RequestMapping(value = "keywords")
public class BotSentenceKeyWordsController {
    @Autowired
    BotSentenceKeyWordsService botSentenceKeyWordsService;
    @Autowired
    IBotSentenceKeyWordsValidateService botSentenceKeyWordsValidateService;
    /**
     * 导入关键词库
     *
     * @param multipartFile
     * @param industryId
     * @return
     */
    @RequestMapping(value = "importKeyWords", method = RequestMethod.POST)
    @Jurisdiction("botsentence_keywords_import")
    public ServerResult importKeyWords(@RequestParam("file") MultipartFile multipartFile, @RequestParam("industryId") String industryId, @RequestHeader String userId) {
        if (StringUtils.isBlank(industryId)) {
            return ServerResult.createByErrorMessage("参数异常");
        }
        botSentenceKeyWordsService.importKeyWords(multipartFile, industryId, userId);
        return ServerResult.createBySuccessMessage("success");
    }

    /**
     * (管理员界面)根据industryId查询词库
     * @return
     */
    @RequestMapping(value = "getKeyWords", method = RequestMethod.POST)
    public ServerResult<List<KeywordsVO>> getKeyWords(@JsonParam String industryId){
        List<KeywordsVO> list =botSentenceKeyWordsService.getKeyWords(industryId);
        return ServerResult.createBySuccess(list);
    }

    /**
     * 根据关键字模糊查询所有词库（下拉框选择）
     * @return
     */
    @RequestMapping(value = "vagueKeywords", method = RequestMethod.POST)
    public ServerResult<List<KeywordsVO>> vagueKeyWords(@JsonParam String industryId, @JsonParam String condition ){
        if(StringUtils.isBlank(industryId)){
            return ServerResult.createByErrorMessage("参数industryId不能为空");
        }
        List<KeywordsVO> list =botSentenceKeyWordsService.vagueKeyWords(industryId,condition);
        return ServerResult.createBySuccess(list);
    }


    /**
     * (业务界面)根据industryId和检索条件查询词库
     * @return
     */
    @RequestMapping(value = "queryByIndustryId", method = RequestMethod.POST)
    public ServerResult<List<QueryKeywordsByIndustryVO>> queryByIndustryId(@JsonParam String industryId,@JsonParam String condition){
        if(StringUtils.isBlank(industryId)){
            return ServerResult.createByErrorMessage("参数industryId不能为空");
        }
        List<QueryKeywordsByIndustryVO> list =botSentenceKeyWordsService.queryByIndustryId(industryId,condition);
        return ServerResult.createBySuccess(list);
    }


    /**
     * 引用意图
     *
     * @param addIntentVO
     * @return
     */
    @RequestMapping(value = "addIntent", method = RequestMethod.POST)
    public ServerResult addIntent(@RequestBody AddIntentVO addIntentVO, @RequestHeader String userId) {
        botSentenceKeyWordsService.addIntent(addIntentVO, userId);
        return ServerResult.createBySuccessMessage("success");
    }

    /**
     * 添加自定义意图
     * @param
     * @return
     */
    @RequestMapping(value = "addCustomIntent", method = RequestMethod.POST)
    public ServerResult addCustomIntent(@JsonParam String branchId,@JsonParam String keysString, @RequestHeader String userId) {
        if(StringUtils.isBlank(branchId)||StringUtils.isBlank(keysString)){
            return ServerResult.createByErrorMessage("参数非法");
        }
        botSentenceKeyWordsService.addCustomIntent(branchId,keysString, userId);
        return ServerResult.createBySuccessMessage("success");
    }

    /**
     * 获取意图列表
     *
     * @param branchId
     * @return
     */
    @RequestMapping(value = "getIntent", method = RequestMethod.POST)
    public ServerResult<List<BotSentenceIntentVO>> getIntent(@JsonParam String branchId) {
        List<BotSentenceIntentVO> list = botSentenceKeyWordsService.getIntent(branchId);
        return ServerResult.createBySuccess(list);
    }

    /**
     * 删除意图
     *
     * @param branchId
     * @param intentId
     * @return
     */
    @RequestMapping(value = "delIntent", method = RequestMethod.POST)
    public ServerResult delIntent(@JsonParam String branchId, @JsonParam String intentId, @RequestHeader String userId) {
        botSentenceKeyWordsService.delIntent(branchId,intentId, userId);
        return ServerResult.createBySuccessMessage("success");
    }

    /**
     * 编辑意图
     * @param editIntentVO
     * @return
     */
    @RequestMapping(value = "editIntent", method = RequestMethod.POST)
    public ServerResult editIntent(@RequestBody EditIntentVO editIntentVO) {
        if(StringUtils.isBlank(editIntentVO.getIntentId())||StringUtils.isBlank(editIntentVO.getKeysString())){
            return ServerResult.createByErrorMessage("参数非法");
        }
        botSentenceKeyWordsService.editIntent(editIntentVO.getIntentId(), editIntentVO.getKeysString());
        return ServerResult.createBySuccessMessage("success");
    }


    @RequestMapping(value = "saveIntent", method = RequestMethod.POST)
    public ServerResult saveIntent(@RequestBody SaveIntentVO saveIntentVO, @RequestHeader String userId){
        botSentenceKeyWordsService.saveIntent(saveIntentVO.getBranchId(),saveIntentVO.getList(), null, userId);
        return ServerResult.createBySuccess();
    }

    @RequestMapping(value = "validateIntents", method = RequestMethod.POST)
    public ServerResult validateIntents(@JsonParam String processId, @JsonParam Long intentId) {
    	botSentenceKeyWordsValidateService.validateIntents(processId, intentId);
         return ServerResult.createBySuccess();
    }

    
    @RequestMapping(value = "initKeywords", method = RequestMethod.POST)
    public ServerResult initKeywords() {
    	botSentenceKeyWordsService.initKeywords();
         return ServerResult.createBySuccess();
    }
}
