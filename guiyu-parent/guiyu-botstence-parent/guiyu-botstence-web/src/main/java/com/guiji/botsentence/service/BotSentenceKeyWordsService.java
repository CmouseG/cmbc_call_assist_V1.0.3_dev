package com.guiji.botsentence.service;


import org.springframework.web.multipart.MultipartFile;

import com.guiji.botsentence.dao.entity.BotSentenceBranch;
import com.guiji.botsentence.vo.AddIntentVO;
import com.guiji.botsentence.vo.BotSentenceIntentVO;
import com.guiji.botsentence.vo.KeywordsVO;
import com.guiji.botsentence.vo.QueryKeywordsByIndustryVO;

import java.util.List;

public interface BotSentenceKeyWordsService {
    boolean importKeyWords(MultipartFile multipartFile, String industryId, String userId);

    List<KeywordsVO> getKeyWords(String industryId);

   void addIntent( AddIntentVO addIntentVO, String userId);

    void addCustomIntent( String branchId,String keysString, String userId);

    List<BotSentenceIntentVO> getIntent(String branchId);

    void delIntent(String branchId, String intentId, String userId);

    void editIntent( String intentId,String keysString);

    List vagueKeyWords(String industryId, String condition );

    List<QueryKeywordsByIndustryVO> queryByIndustryId(String industryId, String condition);

    /**
     * 大保存接口
     * @param branchId
     * @param list
     */
    void saveIntent(String branchId,List<BotSentenceIntentVO> list, String type, String userId);
    
    /**
     * 用于新增分支保存意图列表
     * @param list
     * @return String
     */
    String saveIntent(String domain, String processId, String templateId, List<BotSentenceIntentVO> list, String type, BotSentenceBranch branch, String userId);
    
    public void initKeywords();
    
    public void initSimTxtKeywords(String simTxt, String industryId);
    
    public void initTemplateKeywords(String simTxt, String processId);
}
