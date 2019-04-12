package com.guiji.botsentence.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.guiji.botsentence.dao.*;
import com.guiji.botsentence.dao.entity.*;
import com.guiji.botsentence.service.BotSentenceKeyWordsService;
import com.guiji.botsentence.util.BotSentenceUtil;
import com.guiji.botsentence.util.KeywordsUtil;
import com.guiji.botsentence.vo.AddIntentVO;
import com.guiji.botsentence.vo.BotSentenceIntentVO;
import com.guiji.botsentence.vo.KeywordsVO;
import com.guiji.botsentence.vo.QueryKeywordsByIndustryVO;
import com.guiji.common.exception.CommonException;
import com.guiji.component.client.util.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;

@Slf4j
@Service
public class BotSentenceKeyWordsServiceImpl implements BotSentenceKeyWordsService {
    @Autowired
    BotSentenceKeywordTemplateMapper botSentenceKeywordTemplateMapper;
    @Autowired
    BotSentenceTradeMapper botSentenceTradeMapper;
    @Autowired
    BotSentenceBranchMapper botSentenceBranchMapper;
    @Autowired
    BotSentenceIntentMapper botSentenceIntentMapper;
    @Autowired
    BotSentenceAdditionMapper botSentenceAdditionMapper;

    @Override
    public boolean importKeyWords(MultipartFile multipartFile, String industryId, String userId) {
    	if(multipartFile.getSize() == 0) {
    		throw new CommonException("文件大小为0!");
    	}
        String fileName = multipartFile.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        if (!suffix.equals("txt") && !suffix.equals("docx") && !suffix.equals("doc")) {
            throw new CommonException("请上传txt文件或者word文件!");
        }
        StringBuilder result = new StringBuilder();
        try {
            if (suffix.equals("doc")) {
                InputStream fis = multipartFile.getInputStream();
                WordExtractor wordExtractor = new WordExtractor(fis);//使用HWPF组件中WordExtractor类从Word文档中提取文本或段落
                for (String words : wordExtractor.getParagraphText()) {//获取段落内容
                    result.append(words);
                }
                fis.close();
            } else if (suffix.equals("docx")) {
                String uFileName = String.valueOf(System.currentTimeMillis()) + ".docx";
                File uFile = new File(uFileName);//创建一个临时文件
                if (!uFile.exists()) {
                    uFile.createNewFile();
                }
                FileCopyUtils.copy(multipartFile.getBytes(), uFile);//复制文件内容
                OPCPackage opcPackage = POIXMLDocument.openPackage(uFileName);
                XWPFDocument document = new XWPFDocument(opcPackage);//使用XWPF组件XWPFDocument类获取文档内容
                List<XWPFParagraph> paras = document.getParagraphs();
                for (XWPFParagraph paragraph : paras) {
                    result.append(paragraph.getText());
                }
                document.close();
                uFile.delete();
            } else {//处理txt文件
                InputStream in = multipartFile.getInputStream();
                InputStreamReader reader = new InputStreamReader(in, "gbk");
                BufferedReader br = new BufferedReader(reader);
                String s = null;
                while ((s = br.readLine()) != null) {//使用readLine方法，一次读一行
                    result.append(System.lineSeparator() + s);
                }
                br.close();
            }
        } catch (Exception e) {
        	log.error("导入词库异常", e);
            throw new CommonException(e.getMessage());
        }

        if(StringUtils.isBlank(result.toString())) {
        	throw new CommonException("关键词内容为空!");
        }
        
        List<String> allkkeywordList = BotSentenceUtil.StringToList(result.toString()); 
        List<String> keywordList = new ArrayList<>();
        
        for(String keyword : allkkeywordList) {
        	if(keywordList.contains(keyword) && StringUtils.isNotBlank(keyword)) {
        		continue;
        	}else {
        		keywordList.add(keyword);
        	}
        }
        
        if(keywordList.size() == 0) {
        	throw new CommonException("导入关键词为空!");
        }
        
        String result2 = BotSentenceUtil.listToString(keywordList);
        
        validasteKeywords(industryId, result2);
        
        String keywordsJson = BotSentenceUtil.generateKeywords(result2);
        
        //处理得到意图名称
        String intentName = "";
        if (fileName.indexOf("_") != -1) {
            intentName = fileName.substring(fileName.lastIndexOf("_") + 1, fileName.lastIndexOf("."));
        } else {
            intentName = fileName.substring(0, fileName.lastIndexOf("."));
        }

        
        
        // TODO: 2019/1/11  已存在行业意图判断
        if (industryId.equals("currency")) {//通用
            BotSentenceKeywordTemplateExample example = new BotSentenceKeywordTemplateExample();
            example.createCriteria().andTypeEqualTo("00").andIntentNameEqualTo(intentName);
            List<BotSentenceKeywordTemplate> templateList= botSentenceKeywordTemplateMapper.selectByExampleWithBLOBs(example);
            if(templateList!=null && templateList.size() > 0){
                BotSentenceKeywordTemplate botSentenceKeywordTemplate = templateList.get(0);
                //增量处理关键词
                String existKeywords = botSentenceKeywordTemplate.getKeywords();
                if(StringUtils.isNotBlank(existKeywords)) {
                	existKeywords = existKeywords.substring(1, existKeywords.length() - 1);
                	existKeywords = BotSentenceUtil.generateShowKeywords(existKeywords);
                	List<String> existKeywordList = BotSentenceUtil.StringToList(existKeywords);
                	List<String> addKeywordList = BotSentenceUtil.StringToList(result2);
                	addKeywordList.addAll(existKeywordList);
                	keywordsJson = BotSentenceUtil.generateKeywords(BotSentenceUtil.listToString(addKeywordList));
                }
                
                //全量处理关键词
                botSentenceKeywordTemplate.setKeywords(keywordsJson);
                botSentenceKeywordTemplateMapper.updateByPrimaryKeyWithBLOBs(botSentenceKeywordTemplate);
            }else{
                BotSentenceKeywordTemplate botSentenceKeywordTemplate = new BotSentenceKeywordTemplate();
                botSentenceKeywordTemplate.setType("00");
                botSentenceKeywordTemplate.setIndustryId(industryId);
                botSentenceKeywordTemplate.setIndustryName("通用库");
                botSentenceKeywordTemplate.setIntentName(intentName);
                botSentenceKeywordTemplate.setKeywords(keywordsJson);
                botSentenceKeywordTemplate.setCrtTime(new Date(System.currentTimeMillis()));
                botSentenceKeywordTemplate.setCrtUser(userId);
                botSentenceKeywordTemplateMapper.insert(botSentenceKeywordTemplate);
            }
        }else{//行业
            BotSentenceKeywordTemplateExample example = new BotSentenceKeywordTemplateExample();
            example.createCriteria().andIndustryIdEqualTo(industryId).andIntentNameEqualTo(intentName);
            List<BotSentenceKeywordTemplate> templateList= botSentenceKeywordTemplateMapper.selectByExampleWithBLOBs(example);
            if(templateList!=null && templateList.size() > 0){
                BotSentenceKeywordTemplate botSentenceKeywordTemplate = templateList.get(0);
                //增量处理关键词
                String existKeywords = botSentenceKeywordTemplate.getKeywords();
                if(StringUtils.isNotBlank(existKeywords)) {
                	existKeywords = existKeywords.substring(1, existKeywords.length() - 1);
                	existKeywords = BotSentenceUtil.generateShowKeywords(existKeywords);
                	List<String> existKeywordList = BotSentenceUtil.StringToList(existKeywords);
                	List<String> addKeywordList = BotSentenceUtil.StringToList(result2);
                	addKeywordList.addAll(existKeywordList);
                	keywordsJson = BotSentenceUtil.generateKeywords(BotSentenceUtil.listToString(addKeywordList));
                }
                botSentenceKeywordTemplate.setKeywords(keywordsJson);
                botSentenceKeywordTemplateMapper.updateByPrimaryKeyWithBLOBs(botSentenceKeywordTemplate);
            }else{
                BotSentenceKeywordTemplate botSentenceKeywordTemplate = new BotSentenceKeywordTemplate();
                botSentenceKeywordTemplate.setIndustryId(industryId);
                BotSentenceTradeExample exampleTrade = new BotSentenceTradeExample();
                exampleTrade.createCriteria().andIndustryIdEqualTo(industryId);
                List<BotSentenceTrade> botSentenceTradeList = botSentenceTradeMapper.selectByExample(exampleTrade);
                BotSentenceTrade botSentenceTrade = botSentenceTradeList.get(0);
                botSentenceKeywordTemplate.setIndustryName(botSentenceTrade.getIndustryName());
                botSentenceKeywordTemplate.setType("01");
                botSentenceKeywordTemplate.setIntentName(intentName);
                botSentenceKeywordTemplate.setKeywords(keywordsJson);
                botSentenceKeywordTemplate.setCrtTime(new Date(System.currentTimeMillis()));
                botSentenceKeywordTemplate.setCrtUser(userId);
                botSentenceKeywordTemplateMapper.insert(botSentenceKeywordTemplate);
            }
        }

//        BotSentenceKeywordTemplate botSentenceKeywordTemplate = new BotSentenceKeywordTemplate();
//        if (industryId.equals("currency")) {
//            botSentenceKeywordTemplate.setType("00");
//        } else {
//            botSentenceKeywordTemplate.setIndustryId(industryId);
//            BotSentenceTradeExample example = new BotSentenceTradeExample();
//            example.createCriteria().andIndustryIdEqualTo(industryId);
//            List<BotSentenceTrade> botSentenceTradeList = botSentenceTradeMapper.selectByExample(example);
//            BotSentenceTrade botSentenceTrade = botSentenceTradeList.get(0);
//            botSentenceKeywordTemplate.setIndustryName(botSentenceTrade.getIndustryName());
//            botSentenceKeywordTemplate.setType("01");
//        }
//        botSentenceKeywordTemplate.setIntentName(intentName);
//        botSentenceKeywordTemplate.setKeywords(BotSentenceUtil.generateKeywords(result.toString()));
//        botSentenceKeywordTemplateMapper.insert(botSentenceKeywordTemplate);
        return true;
    }

    @Override
    public void addIntent(AddIntentVO addIntentVO, String userId) {

        //从词库中查到意图
        BotSentenceKeywordTemplate botSentenceKeywordTemplate = botSentenceKeywordTemplateMapper.selectByPrimaryKey(addIntentVO.getKeywordsId());
        //查询分支
        BotSentenceBranch branch = botSentenceBranchMapper.selectByPrimaryKey(addIntentVO.getBranchId());
        if (null != branch) {
            //新增意图
            BotSentenceIntent intent = new BotSentenceIntent();
            intent.setCrtTime(new Date(System.currentTimeMillis()));
            intent.setCrtUser(userId);
            intent.setProcessId(branch.getProcessId());
            intent.setDomainName(branch.getDomain());
            intent.setKeywords(botSentenceKeywordTemplate.getKeywords());
            intent.setTemplateId(branch.getTemplateId());
            intent.setForSelect(0);
            intent.setName(botSentenceKeywordTemplate.getIntentName());
            botSentenceIntentMapper.insert(intent);
            //修改branch表中的意图id的关联
            String intentIds = branch.getIntents();
            intentIds = intentIds + "," + intent.getId().toString();
            branch.setIntents(intentIds);
            branch.setLstUpdateTime(new Date(System.currentTimeMillis()));
            branch.setLstUpdateUser(userId);
            botSentenceBranchMapper.updateByPrimaryKey(branch);
        }
    }

    @Override
    public void addCustomIntent(String branchId, String keysString, String userId) {
        BotSentenceBranch branch = botSentenceBranchMapper.selectByPrimaryKey(branchId);
        if (null != branch) {
            String keywordsJson = BotSentenceUtil.generateKeywords(keysString);
            keywordsJson = keywordsJson.substring(1, keywordsJson.length() - 1);
            BotSentenceIntent intent = new BotSentenceIntent();
            intent.setCrtTime(new Date(System.currentTimeMillis()));
            intent.setCrtUser(userId);
            intent.setName("自定义意图");
            intent.setProcessId(branch.getProcessId());
            intent.setDomainName(branch.getDomain());
            intent.setKeywords("[" + keywordsJson + "]");
            intent.setTemplateId(branch.getTemplateId());
            intent.setForSelect(0);
            botSentenceIntentMapper.insert(intent);

            //修改branch表中的意图id的关联
            String intentIds = branch.getIntents();
            intentIds = intentIds + "," + intent.getId().toString();
            branch.setIntents(intentIds);
            branch.setLstUpdateTime(new Date(System.currentTimeMillis()));
            branch.setLstUpdateUser(userId);
            botSentenceBranchMapper.updateByPrimaryKey(branch);
        }

    }

    @Override
    public List<BotSentenceIntentVO> getIntent(String branchId) {
        List<BotSentenceIntentVO> list = new ArrayList<>();
        BotSentenceBranch branch = botSentenceBranchMapper.selectByPrimaryKey(branchId);
        if (null != branch) {
            String intents = branch.getIntents();
            if(StringUtils.isNotBlank(intents)) {
            	 String intent[] = intents.split(",");
                 for (int i = 0; i < intent.length; i++) {
                     BotSentenceIntent botSentenceIntent = botSentenceIntentMapper.selectByPrimaryKey(Long.parseLong(intent[i]));
                     BotSentenceIntentVO botSentenceIntentVO = new BotSentenceIntentVO();
                     BeanUtils.copyProperties(botSentenceIntent, botSentenceIntentVO);

                     KeywordsUtil.KeywordsGroup keywordsGroup = KeywordsUtil.getKeywordsGroup(botSentenceIntent.getKeywords());
                     botSentenceIntentVO.setSingleKeywords(keywordsGroup.getSingleKeywords());
                     botSentenceIntentVO.setCombinationKeywords(keywordsGroup.getCombinationKeywords());
                     botSentenceIntentVO.setExactMatchKeywords(keywordsGroup.getExactMatchKeywords());
                     botSentenceIntentVO.setOtherKeywords(keywordsGroup.getOtherKeywords());

                     String keyword = botSentenceIntentVO.getKeywords();
                     List<String> keywordList = BotSentenceUtil.getKeywords(keyword);

                    if(null != keywordList && keywordList.size() > 0) {
                        keyword = keywordList.get(0);
                    }else {
                        keyword = "";
                    }

                     //keyword = keyword.replace("[", "");
                     //keyword = keyword.replace("]", "");
                     keyword = keyword.replaceAll("\"", "");
                     botSentenceIntentVO.setKeywords(keyword);

                     list.add(botSentenceIntentVO);
                 }
            }
           
        }
        return list;
    }

    @Override
    public void delIntent(String branchId, String intentId, String userId) {
        //删除意图表中的记录
        botSentenceIntentMapper.deleteByPrimaryKey(Long.parseLong(intentId));
        //删除分支表中该意图id的关联
        BotSentenceBranch branch = botSentenceBranchMapper.selectByPrimaryKey(branchId);
        if (null != branch) {
            String intents = branch.getIntents();
            String intent[] = intents.split(",");
            List<String> list = new ArrayList<>();
            for (int i = 0; i < intent.length; i++) {
                if (!intent[i].equals(intentId)) {
                    list.add(intent[i]);
                }
            }
            branch.setIntents(StringUtils.join(list.toArray(), ","));
            branch.setLstUpdateTime(new Date(System.currentTimeMillis()));
            branch.setLstUpdateUser(userId);
            botSentenceBranchMapper.updateByPrimaryKey(branch);
        }
    }

    @Override
    public void editIntent(String intentId, String keysString) {
        BotSentenceIntent intent = botSentenceIntentMapper.selectByPrimaryKey(Long.parseLong(intentId));
        String keywordsJson = BotSentenceUtil.generateKeywords(keysString);
        keywordsJson = keywordsJson.substring(1, keywordsJson.length() - 1);
        intent.setKeywords("[" + keywordsJson + "]");
        botSentenceIntentMapper.updateByPrimaryKeyWithBLOBs(intent);
    }

    @Override
    public List<QueryKeywordsByIndustryVO> queryByIndustryId(String industryId, String condition) {
        List<QueryKeywordsByIndustryVO> list = new ArrayList<>();
        if(StringUtils.isBlank(condition)){//初始，没有查询条件
            BotSentenceKeywordTemplateExample exampleCurrency = new BotSentenceKeywordTemplateExample();
            exampleCurrency.createCriteria().andTypeEqualTo("00");
            List<BotSentenceKeywordTemplate> templateCurrencyList= botSentenceKeywordTemplateMapper.selectByExampleWithBLOBs(exampleCurrency);
            QueryKeywordsByIndustryVO queryKeywordsByIndustryVO = new QueryKeywordsByIndustryVO();
            queryKeywordsByIndustryVO.setType("通用");
            if(templateCurrencyList!=null){
                queryKeywordsByIndustryVO.setKeywordsList(buildKeywordsVOList(templateCurrencyList));
            }
            list.add(queryKeywordsByIndustryVO);

            BotSentenceKeywordTemplateExample exampleIndustry = new BotSentenceKeywordTemplateExample();
            exampleIndustry.createCriteria().andIndustryIdEqualTo(industryId);
            List<BotSentenceKeywordTemplate> templateIndustryList= botSentenceKeywordTemplateMapper.selectByExampleWithBLOBs(exampleIndustry);
            QueryKeywordsByIndustryVO queryKeywordsByIndustryVO2 = new QueryKeywordsByIndustryVO();
            queryKeywordsByIndustryVO2.setType("行业");
            if(templateIndustryList!=null){
                queryKeywordsByIndustryVO2.setKeywordsList(buildKeywordsVOList(templateIndustryList));
            }
            list.add(queryKeywordsByIndustryVO2);
        }else{
            BotSentenceKeywordTemplateExample exampleCurrency = new BotSentenceKeywordTemplateExample();
            exampleCurrency.createCriteria().andTypeEqualTo("00").andIntentNameLike("%"+condition+"%");
            List<BotSentenceKeywordTemplate> templateCurrencyList= botSentenceKeywordTemplateMapper.selectByExampleWithBLOBs(exampleCurrency);
            QueryKeywordsByIndustryVO queryKeywordsByIndustryVO = new QueryKeywordsByIndustryVO();
            queryKeywordsByIndustryVO.setType("通用");
            if(templateCurrencyList!=null){
                queryKeywordsByIndustryVO.setKeywordsList(buildKeywordsVOList(templateCurrencyList));
            }
            list.add(queryKeywordsByIndustryVO);

            BotSentenceKeywordTemplateExample exampleIndustry = new BotSentenceKeywordTemplateExample();
            exampleIndustry.createCriteria().andIndustryIdEqualTo(industryId).andIntentNameLike("%"+condition+"%");
            List<BotSentenceKeywordTemplate> templateIndustryList= botSentenceKeywordTemplateMapper.selectByExampleWithBLOBs(exampleIndustry);
            QueryKeywordsByIndustryVO queryKeywordsByIndustryVO2 = new QueryKeywordsByIndustryVO();
            queryKeywordsByIndustryVO2.setType("行业");
            if(templateIndustryList!=null){
                queryKeywordsByIndustryVO2.setKeywordsList(buildKeywordsVOList(templateIndustryList));
            }
            list.add(queryKeywordsByIndustryVO2);
        }
        return list;
    }

    @Override
    public void saveIntent(String branchId, List<BotSentenceIntentVO> list, String type, String userId) {
        List<String> intentIdsList = new ArrayList<>();//用于收集intentId
        BotSentenceBranch branch = botSentenceBranchMapper.selectByPrimaryKey(branchId);
        if (null != branch) {
            for (BotSentenceIntentVO botSentenceIntentVO : list) {
                if (botSentenceIntentVO.getId() == null) {//新增
                    String keywordsJson = BotSentenceUtil.generateKeywords(botSentenceIntentVO.getKeywords());
                    if(StringUtils.isNotBlank(keywordsJson	)) {
                    	keywordsJson = keywordsJson.substring(1, keywordsJson.length() - 1);
                    }else {
                    	if("00".equals(type)) {
                    		keywordsJson = "";
                    	}else {
                    		throw new CommonException("意图【"+botSentenceIntentVO.getName()+"】的关键词不能为空");	
                    	}
                    }
                    
                    
                    keywordsJson = keywordsJson.substring(1, keywordsJson.length() - 1);
                    BotSentenceIntent intent = new BotSentenceIntent();
                    intent.setCrtTime(new Date(System.currentTimeMillis()));
                    intent.setCrtUser(userId);
                    intent.setName(botSentenceIntentVO.getName());
                    intent.setProcessId(branch.getProcessId());
                    intent.setDomainName(branch.getDomain());
                    intent.setKeywords("[" + keywordsJson + "]");
                    intent.setTemplateId(branch.getTemplateId());
                    intent.setForSelect(0);
                    intent.setRefrenceId(botSentenceIntentVO.getReferenceId());
                    botSentenceIntentMapper.insert(intent);
                    intentIdsList.add(intent.getId().toString());
                } else {//修改
                    intentIdsList.add(botSentenceIntentVO.getId().toString());
                    BotSentenceIntent intent = botSentenceIntentMapper.selectByPrimaryKey(botSentenceIntentVO.getId());
                    String keywordsJson = BotSentenceUtil.generateKeywords(botSentenceIntentVO.getKeywords());
                    if(StringUtils.isNotBlank(keywordsJson	)) {
                    	keywordsJson = keywordsJson.substring(1, keywordsJson.length() - 1);
                    }else {
                    	if("00".equals(type)) {
                    		keywordsJson = "";
                    	}else {
                    		throw new CommonException("意图【"+botSentenceIntentVO.getName()+"】的关键词不能为空");	
                    	}
                    }
                    
                    //得到保存之前的多关键词
                    List<String> keywordList = BotSentenceUtil.getKeywords(intent.getKeywords());
                    if(null != keywordList && keywordList.size() > 0 && org.apache.commons.lang.StringUtils.isNotBlank(keywordList.get(1))) {
            			intent.setKeywords("[" + keywordsJson + "," + keywordList.get(1).replace("\n", "") + "]");
            		}else {
            			intent.setKeywords("[" + keywordsJson + "]");
            		}
                    
                   // keywordsJson = keywordsJson.substring(1, keywordsJson.length() - 1);
                    //intent.setKeywords("[" + keywordsJson + "]");
                    botSentenceIntentMapper.updateByPrimaryKeyWithBLOBs(intent);
                }
            }
            //修改branch表中的意图id的关联
            String intentIds = StringUtils.join(intentIdsList.toArray(), ",");
            branch.setIntents(intentIds);
            branch.setLstUpdateTime(new Date(System.currentTimeMillis()));
            branch.setLstUpdateUser(userId);
            botSentenceBranchMapper.updateByPrimaryKey(branch);
        }
    }

    //通用方法，查询遍历组装List<KeywordsVO>
    public List<KeywordsVO> buildKeywordsVOList(List<BotSentenceKeywordTemplate>  botSentenceKeywordTemplateList){
        List<KeywordsVO> keywordsVOList = new ArrayList<>();
        for (BotSentenceKeywordTemplate botSentenceKeywordTemplate:botSentenceKeywordTemplateList) {
            KeywordsVO keywordsVO = new KeywordsVO();
            keywordsVO.setIntentId(botSentenceKeywordTemplate.getId());
            keywordsVO.setIntentName(botSentenceKeywordTemplate.getIntentName());
            String keyword = botSentenceKeywordTemplate.getKeywords();
            keyword = keyword.replace("[", "");
            keyword = keyword.replace("]", "");
            keyword = keyword.replaceAll("\"", "");
            keywordsVO.setKeyWords(keyword);
            keywordsVOList.add(keywordsVO);
        }
        return keywordsVOList;
    }


    @Override
    public List vagueKeyWords(String industryId, String condition) {
        //先查询本行业的
        BotSentenceKeywordTemplateExample exampleIndustry = new BotSentenceKeywordTemplateExample();
        exampleIndustry.createCriteria().andIndustryIdEqualTo(industryId).andIntentNameLike("%"+condition+"%");
        List<BotSentenceKeywordTemplate> botSentenceKeywordTemplateList = botSentenceKeywordTemplateMapper.selectByExampleWithBLOBs(exampleIndustry);
        //再查询通用的
        BotSentenceKeywordTemplateExample exampleCurrency = new BotSentenceKeywordTemplateExample();
        exampleCurrency.createCriteria().andTypeEqualTo("00").andIntentNameLike("%"+condition+"%");
        botSentenceKeywordTemplateList.addAll(botSentenceKeywordTemplateMapper.selectByExampleWithBLOBs(exampleCurrency));
        List<KeywordsVO> keywordsVOList = new ArrayList<>();
        if (botSentenceKeywordTemplateList != null) {
            keywordsVOList = buildKeywordsVOList(botSentenceKeywordTemplateList);
        }
        return keywordsVOList;
    }

    @Override
    public List<KeywordsVO> getKeyWords(String industryId) {
        BotSentenceKeywordTemplateExample example = new BotSentenceKeywordTemplateExample();
        if (industryId.equals("currency")) {//通用
            example.createCriteria().andTypeEqualTo("00");
        } else {
            example.createCriteria().andIndustryIdEqualTo(industryId);
        }
        List<BotSentenceKeywordTemplate> botSentenceKeywordTemplateList = botSentenceKeywordTemplateMapper.selectByExampleWithBLOBs(example);
        List<KeywordsVO> keywordsVOList = new ArrayList<>();
        if(botSentenceKeywordTemplateList!=null){
            keywordsVOList = buildKeywordsVOList(botSentenceKeywordTemplateList);
        }
        return keywordsVOList;
    }

    /**
     * 初始化导入关键词库
     */
    @Override
    @Transactional
    public void initKeywords() {
        File keywordsFile = new File("D:\\日常工作\\话术市场自用版本\\关键词库20190114\\行业库\\银行词库\\银行_信用卡_用卡保障险");//初始化关键词文件夹
        BotSentenceTradeExample childExample = new BotSentenceTradeExample();
        childExample.createCriteria().andLevelEqualTo(3);
        List<BotSentenceTrade> childIndustryList = botSentenceTradeMapper.selectByExample(childExample);
        Map<String, String> map = new HashMap<>();
        for (BotSentenceTrade botSentenceTrade : childIndustryList) {
            BotSentenceTradeExample parentExample = new BotSentenceTradeExample();
            parentExample.createCriteria().andIndustryIdEqualTo(botSentenceTrade.getParentId());
            List<BotSentenceTrade> parentIndustryList = botSentenceTradeMapper.selectByExample(parentExample);
            BotSentenceTrade parentBotSentenceTrade = parentIndustryList.get(0);
            map.put(parentBotSentenceTrade.getParentName() + "_" + botSentenceTrade.getParentName() + "_" + botSentenceTrade.getIndustryName(), botSentenceTrade.getIndustryId());
        }
       // File[] fs = keywordsFile.listFiles();
        
        List<File> listFile = new ArrayList<File>();
		FileUtil.getAllFilePaths(keywordsFile, listFile);
        
        //遍历所有的excel文件，逐个处理
        for (File file : listFile) {
        	if(file.isDirectory()) {
        		continue;
        	}
            String fileName = file.getName();
            String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
            StringBuilder result = new StringBuilder();
            try {
                if (suffix.equals("doc")) {
                    InputStream fis = new FileInputStream(file);
                    WordExtractor wordExtractor = new WordExtractor(fis);//使用HWPF组件中WordExtractor类从Word文档中提取文本或段落
                    for (String words : wordExtractor.getParagraphText()) {//获取段落内容
                        result.append(words);
                    }
                    fis.close();
                } else if (suffix.equals("docx")) {
                    OPCPackage opcPackage = POIXMLDocument.openPackage(file.getPath());
                    XWPFDocument document = new XWPFDocument(opcPackage);//使用XWPF组件XWPFDocument类获取文档内容
                    List<XWPFParagraph> paras = document.getParagraphs();
                    for (XWPFParagraph paragraph : paras) {
                        result.append(paragraph.getText());
                    }
                } else {//处理txt文件
                    InputStream fis = new FileInputStream(file);
                    InputStreamReader reader = new InputStreamReader(fis, "gbk");
                    BufferedReader br = new BufferedReader(reader);
                    String s = null;
                    while ((s = br.readLine()) != null) {//使用readLine方法，一次读一行
                        result.append(System.lineSeparator() + s);
                    }
                    br.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            String value = fileName.substring(0, fileName.indexOf("."));
            String fileNameSeparate[] = value.split("_");
            if (fileNameSeparate.length == 2) {//通用
                BotSentenceKeywordTemplate botSentenceKeywordTemplate = new BotSentenceKeywordTemplate();
                botSentenceKeywordTemplate.setType("00");
                botSentenceKeywordTemplate.setIntentName(fileNameSeparate[1]);
                botSentenceKeywordTemplate.setKeywords(BotSentenceUtil.generateKeywords(result.toString()));
                botSentenceKeywordTemplateMapper.insert(botSentenceKeywordTemplate);
            } else if (fileNameSeparate.length == 4) {//行业
                //第一步：根据名字查询industryId
                String a = fileName.substring(0, fileName.lastIndexOf("_"));
                String industryId = map.get(a);
                if(StringUtils.isBlank(industryId)) {
                	throw new CommonException("行业不存在: " + a);
                }
                
                BotSentenceKeywordTemplate botSentenceKeywordTemplate = new BotSentenceKeywordTemplate();
                botSentenceKeywordTemplate.setIndustryId(industryId);
                BotSentenceTradeExample exampleTrade = new BotSentenceTradeExample();
                exampleTrade.createCriteria().andIndustryIdEqualTo(industryId);
                List<BotSentenceTrade> botSentenceTradeList = botSentenceTradeMapper.selectByExample(exampleTrade);
                BotSentenceTrade botSentenceTrade = botSentenceTradeList.get(0);
                botSentenceKeywordTemplate.setIndustryName(botSentenceTrade.getIndustryName());
                botSentenceKeywordTemplate.setType("01");
                botSentenceKeywordTemplate.setIntentName(fileNameSeparate[3].trim());
                botSentenceKeywordTemplate.setKeywords(BotSentenceUtil.generateKeywords(result.toString()));
                botSentenceKeywordTemplateMapper.insert(botSentenceKeywordTemplate);
            }else {
            	throw new CommonException("文件命名不规范: " + fileName);
            }
        }
    }

    /**
     * type=00 表示通用对话保存
     */
	@Override
	@Transactional
	public String saveIntent(String domain, String processId, String templateId, List<BotSentenceIntentVO> list, String type, BotSentenceBranch branch, String userId) {
        List<String> intentIdsList = new ArrayList<>();//用于收集intentId
        List<String> existIntentIds = new ArrayList<>();//已存在的
        if(null != branch) {
        	String existIntents = branch.getIntents();
        	if(StringUtils.isNotBlank(existIntents)) {
            	existIntentIds = BotSentenceUtil.StringToList(existIntents);
            }
        }

        for (BotSentenceIntentVO botSentenceIntentVO : list) {
        	Long intentId = botSentenceIntentVO.getId();

            if (null == intentId) {//新增
                BotSentenceIntent intent = new BotSentenceIntent();
                List<String> keywordList = KeywordsUtil.getAllKeywordsFromIntentVO(botSentenceIntentVO);
                if(CollectionUtils.isEmpty(keywordList)){
                    if("00".equals(type)){
                        intent.setKeywords("[]");
                    }else {
                        throw new CommonException("关键词不能为空!");
                    }
                }else {
                    intent.setKeywords(JSON.toJSONString(keywordList));
                }
                
                intent.setCrtTime(new Date(System.currentTimeMillis()));
                intent.setCrtUser(userId);
                intent.setName(botSentenceIntentVO.getName());
                intent.setProcessId(processId);
                if(StringUtils.isNotBlank(botSentenceIntentVO.getIntentDomain())) {
                	intent.setDomainName(botSentenceIntentVO.getIntentDomain());
                }else{
                	intent.setDomainName(domain);
                }
                intent.setTemplateId(templateId);
                intent.setForSelect(0);
                intent.setRefrenceId(botSentenceIntentVO.getReferenceId());
                botSentenceIntentMapper.insert(intent);
                intentId = intent.getId();
            } else {//修改
                BotSentenceIntent intent = botSentenceIntentMapper.selectByPrimaryKey(new Long(intentId));

                List<String> keywordList = KeywordsUtil.getAllKeywordsFromIntentVO(botSentenceIntentVO);
                if(CollectionUtils.isEmpty(keywordList)){
                    if("00".equals(type)){
                        intent.setKeywords("[]");
                    }else {
                        if("自定义".equals(intent.getName())) {
                            intent.setKeywords("[]");
                            botSentenceIntentMapper.updateByPrimaryKeyWithBLOBs(intent);
                        }
                        continue;
                    }
                }else {
                    intent.setKeywords(JSON.toJSONString(keywordList));
                }
                botSentenceIntentMapper.updateByPrimaryKeyWithBLOBs(intent);
            }

            intentIdsList.add(intentId.toString());
        }

        //删除未选择的意图
        if(!CollectionUtils.isEmpty(existIntentIds)) {
            List<Long> needDeleteIntentIds = Lists.newArrayList();
            existIntentIds.forEach(existIntentId -> {
                if(!intentIdsList.contains(existIntentId)){
                    needDeleteIntentIds.add(Long.parseLong(existIntentId));
                }
            });

            if(!CollectionUtils.isEmpty(needDeleteIntentIds)){
                BotSentenceIntentExample intentExample = new BotSentenceIntentExample();
                intentExample.createCriteria().andIdIn(needDeleteIntentIds);
                botSentenceIntentMapper.deleteByExample(intentExample);
            }
        }
        
        //修改branch表中的意图id的关联
        return StringUtils.join(intentIdsList.toArray(), ",");
	}
	
	private void validasteKeywords(String industryId, String keywords) {
		//查询当前行业下的所有意图关键词
		List<String> currentKeywords = BotSentenceUtil.StringToList(keywords);
		String message = "";
		
		BotSentenceKeywordTemplateExample example = new BotSentenceKeywordTemplateExample();
        example.createCriteria().andIndustryIdEqualTo(industryId);
        List<BotSentenceKeywordTemplate> templateList= botSentenceKeywordTemplateMapper.selectByExampleWithBLOBs(example);
        if(templateList!=null && templateList.size() > 0){
        	for(BotSentenceKeywordTemplate temp : templateList) {
        		String tempKeywords = temp.getKeywords();
        		if(StringUtils.isNotBlank(tempKeywords) && tempKeywords.length() > 2) {
        			tempKeywords = tempKeywords.substring(1, tempKeywords.length() - 1);
            		tempKeywords = BotSentenceUtil.generateShowKeywords(tempKeywords);
            		List<String> industryKeywords = BotSentenceUtil.StringToList(tempKeywords);
            		
            		for(String keyword : currentKeywords) {
                		if(industryKeywords.contains(keyword)) {
                			message = message + keyword + "与意图[" + temp.getIntentName() + "]重复<br/>";
                		}
                	}
        		}
        	}
        	
        	if(StringUtils.isNotBlank(message)) {
        		throw new CommonException(message);
        	}
        }
	}
	
	@Override
	public void initSimTxtKeywords(String simTxt, String industryId) {
		//获取相似词库关键词
		Map<String, List<String>> simKeywordMap = new HashMap<>();
		List<List<String>> simList = BotSentenceUtil.getSimtxtKeywordsList(simTxt);
		if(null != simList && simList.size() > 0) {
			for(List<String> simLine : simList) {
				for(String simKeyword : simLine) {
					if(StringUtils.isNotBlank(simKeyword)) {
						simKeywordMap.put(simKeyword, simLine);
					}
				}
			}
		}
		
		//获取当前行业的词库
		List<QueryKeywordsByIndustryVO> list = this.queryByIndustryId(industryId, null);
		if(null != list && list.size() > 0) {
			for(QueryKeywordsByIndustryVO vo : list) {
				List<KeywordsVO> keywordList = vo.getKeywordsList();
				for(KeywordsVO keywordVO : keywordList) {
					boolean needUpdate = false;
					String keywords = keywordVO.getKeyWords();
					List<String> industryKeywordList = BotSentenceUtil.StringToList(keywords);
					List<String> newKeywordList = BotSentenceUtil.StringToList(keywords);
					
					for(String keyword: industryKeywordList) {
						if(simKeywordMap.containsKey(keyword)) {
							needUpdate = true;
							List<String> simKeywordList = simKeywordMap.get(keyword);
							if(null != simKeywordList && simKeywordList.size() > 0) {
								for(String simKeyword : simKeywordList) {
									if(!newKeywordList.contains(simKeyword)) {
										newKeywordList.add(simKeyword);
									}
								}
							}
						}
					}
					
					if(needUpdate) {
						BotSentenceKeywordTemplate template = botSentenceKeywordTemplateMapper.selectByPrimaryKey(keywordVO.getIntentId());
						String keywordsJson = BotSentenceUtil.generateKeywords(BotSentenceUtil.listToString(newKeywordList));
						template.setKeywords(keywordsJson);
						botSentenceKeywordTemplateMapper.updateByPrimaryKeyWithBLOBs(template);
					}
				}
			}
		}
	}
	
	
	@Override
	public void initTemplateKeywords(String simTxt, String processId) {

		Map<String, List<String>> simKeywordMap = new HashMap<>();
		
		List<List<String>> simList = BotSentenceUtil.getSimtxtKeywordsList(simTxt);
		if(null != simList && simList.size() > 0) {
			for(List<String> simLine : simList) {
				for(String simKeyword : simLine) {
					if(StringUtils.isNotBlank(simKeyword)) {
						simKeywordMap.put(simKeyword, simLine);
					}
				}
			}
			
			//获取当前模板的词库
			BotSentenceIntentExample intentExample = new BotSentenceIntentExample();
			intentExample.createCriteria().andProcessIdEqualTo(processId);
			List<BotSentenceIntent> intentList = botSentenceIntentMapper.selectByExampleWithBLOBs(intentExample);
			for(BotSentenceIntent intent : intentList) {
				boolean needUpdate = false;
				//得到保存之前的关键词
				if(StringUtils.isNotBlank(intent.getKeywords())) {
					List<String> keywordList = BotSentenceUtil.getKeywords(intent.getKeywords());
	                String onekeywords = keywordList.get(0);
	                String twokeywords = keywordList.get(1);
	                
	                List<String> oneKeywordList = BotSentenceUtil.StringToList(onekeywords);
	                
	                List<String> newKeywordList = new ArrayList<>();
	                for(String keyword : oneKeywordList) {
	                	if(!newKeywordList.contains(keyword)) {
	                		newKeywordList.add(keyword);
	                	}
	                }
	                
	                for(String keyword : oneKeywordList) {
	                	keyword = keyword.replace("\"", "");
	                	if(simKeywordMap.containsKey(keyword)) {
							needUpdate = true;
							List<String> simKeywordList = simKeywordMap.get(keyword);
							if(null != simKeywordList && simKeywordList.size() > 0) {
								for(String simKeyword : simKeywordList) {
									if(!newKeywordList.contains("\"" + simKeyword + "\"")) {
										newKeywordList.add("\"" + simKeyword + "\"");
									}
								}
							}
						}
	                }
	                
	                Collections.reverse(newKeywordList);
	                
	                String keywordsJson = BotSentenceUtil.generateKeywords(BotSentenceUtil.listToString(newKeywordList));
	                if(StringUtils.isNotBlank(keywordsJson)) {
	                	keywordsJson = keywordsJson.substring(1, keywordsJson.length() - 1);
	                }else {
	                	keywordsJson = "";
	                }
	                
	                if(needUpdate) {
	                    if(null != keywordList && keywordList.size() > 0 && org.apache.commons.lang.StringUtils.isNotBlank(twokeywords)) {
	            			intent.setKeywords("[" + keywordsJson + "," + keywordList.get(1).replace("\n", "") + "]");
	            		}else {
	            			intent.setKeywords("[" + keywordsJson + "]");
	            		}
	                    
	                    botSentenceIntentMapper.updateByPrimaryKeyWithBLOBs(intent);
					}
				}
			}
		}
	}
}
