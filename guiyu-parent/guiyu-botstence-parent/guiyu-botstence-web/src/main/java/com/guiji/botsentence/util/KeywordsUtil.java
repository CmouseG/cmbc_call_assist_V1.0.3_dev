package com.guiji.botsentence.util;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.guiji.botsentence.util.enums.KeywordsType;
import com.guiji.botsentence.vo.BotSentenceIntentVO;
import org.springframework.util.CollectionUtils;

import java.util.List;

public class KeywordsUtil {

    private static final String COMBINATION_SYMBOL = "*!";

    private static final String COMBINATION_REGEX = "\\*!";

    private static final String EXACT_MATCH_SYMBOL = "*1";

    private static final String EXACT_MATCH_REGEX = "\\*1";

    private static final String BRACKET_SYMBOL = "[";

    private static final String EMPTY_SYMBOL = "";

    private static final String SEPARATOR_SYMBOL ="、";


    public static KeywordsGroup getKeywordsGroup(String keywords){

        List<String>  keywordList = JSON.parseArray(keywords, String.class);
        if(CollectionUtils.isEmpty(keywordList)){
            return KeywordsGroup.build();
        }

        KeywordsGroup keywordsGroup = KeywordsGroup.build();

        keywordList.forEach(originKeyword -> {

            Keyword keyword = buildKeywordFromOrigin(originKeyword);
            switch (keyword.getType()){
                case SINGLE:
                    keywordsGroup.addSingleKeyword(keyword.displayKeyword);
                    break;
                case COMBINATION:
                    keywordsGroup.addCombinationKeyword(keyword.displayKeyword);
                    break;
                case EXACT_MATCH:
                    keywordsGroup.addExactMatchKeyword(keyword.displayKeyword);
                    break;
                case OTHER:
                    keywordsGroup.addOtherKeyword(keyword.displayKeyword);
                    break;
            }
        });

        return keywordsGroup;
    }

    public static List<String> getAllKeywordsFromIntentVO(BotSentenceIntentVO botSentenceIntentVO){
        List<String> allKeywords = Lists.newArrayList();
        if(null == botSentenceIntentVO){
            return Lists.newArrayList(allKeywords);
        }
        if(!CollectionUtils.isEmpty(botSentenceIntentVO.getSingleKeywords())){
            allKeywords.addAll(botSentenceIntentVO.getSingleKeywords());
        }

        if(!CollectionUtils.isEmpty(botSentenceIntentVO.getOtherKeywords())){
            allKeywords.addAll(botSentenceIntentVO.getOtherKeywords());
        }

        if(!CollectionUtils.isEmpty(botSentenceIntentVO.getExactMatchKeywords())){
            botSentenceIntentVO.getExactMatchKeywords()
                    .forEach(exactMatchKeyword -> allKeywords.add(exactMatchKeyword + EXACT_MATCH_SYMBOL));
        }

        if(!CollectionUtils.isEmpty(botSentenceIntentVO.getCombinationKeywords())){
            botSentenceIntentVO.getCombinationKeywords().forEach(combinationKeyword -> {
                List<String> combinationKeywordList = Lists.newArrayList();
                String[] combinationKeywordArray = combinationKeyword.split(SEPARATOR_SYMBOL);
                for (String s: combinationKeywordArray) {
                    combinationKeywordList.add(s + COMBINATION_SYMBOL);
                }

                allKeywords.add(JSON.toJSONString(combinationKeywordList));
            });
        }

        return allKeywords;
    }

    public static Keyword buildKeywordFromOrigin(String originKeyword){
        if (originKeyword.contains(BRACKET_SYMBOL)){
            if(originKeyword.contains(COMBINATION_SYMBOL)) {
                if(originKeyword.indexOf(COMBINATION_SYMBOL) != originKeyword.lastIndexOf(COMBINATION_SYMBOL)){
                    StringBuilder sb = new StringBuilder();
                    JSON.parseArray(originKeyword, String.class).forEach(combinationKeyword -> {
                        sb.append(combinationKeyword.replaceAll(COMBINATION_REGEX, EMPTY_SYMBOL));
                        sb.append(SEPARATOR_SYMBOL);
                    });
                    return new Keyword(KeywordsType.COMBINATION, sb.substring(0, sb.length() -1));
                }else {
                    return new Keyword(KeywordsType.OTHER, originKeyword);
                }
            }else {
                return new Keyword(KeywordsType.OTHER, originKeyword);
            }
        }else {
            if(originKeyword.contains(EXACT_MATCH_SYMBOL)){
                return new Keyword(KeywordsType.EXACT_MATCH, originKeyword.replaceAll(EXACT_MATCH_REGEX, EMPTY_SYMBOL));
            }else {
                return new Keyword(KeywordsType.SINGLE, originKeyword);
            }
        }
    }


    public static class Keyword {
        private KeywordsType type;

        private String displayKeyword;

        public Keyword() {
        }

        public Keyword(KeywordsType type, String displayKeyword) {
            this.type = type;
            this.displayKeyword = displayKeyword;
        }

        public KeywordsType getType() {
            return type;
        }

        public void setType(KeywordsType type) {
            this.type = type;
        }

        public String getDisplayKeyword() {
            return displayKeyword;
        }

        public void setDisplayKeyword(String displayKeyword) {
            this.displayKeyword = displayKeyword;
        }
    }

    public static class KeywordsGroup {

        private List<String> singleKeywords;
        private List<String>  combinationKeywords;
        private List<String>  exactMatchKeywords;
        private List<String>  otherKeywords;

        public KeywordsGroup() {
            this.singleKeywords = Lists.newArrayList();
            this.combinationKeywords = Lists.newArrayList();
            this.exactMatchKeywords = Lists.newArrayList();
            this.otherKeywords = Lists.newArrayList();
        }

        public static KeywordsGroup build(){
            return new KeywordsGroup();
        }

        public List<String> getSingleKeywords() {
            return singleKeywords;
        }

        public void setSingleKeywords(List<String> singleKeywords) {
            this.singleKeywords = singleKeywords;
        }

        public void addSingleKeyword(String keyword){
            this.singleKeywords.add(keyword);
        }

        public List<String> getCombinationKeywords() {
            return combinationKeywords;
        }

        public void setCombinationKeywords(List<String> combinationKeywords) {
            this.combinationKeywords = combinationKeywords;
        }

        public void addCombinationKeyword(String keyword){
            this.combinationKeywords.add(keyword);
        }

        public List<String> getExactMatchKeywords() {
            return exactMatchKeywords;
        }

        public void setExactMatchKeywords(List<String> exactMatchKeywords) {
            this.exactMatchKeywords = exactMatchKeywords;
        }

        public void addExactMatchKeyword(String keyword){
            this.exactMatchKeywords.add(keyword);
        }

        public List<String> getOtherKeywords() {
            return otherKeywords;
        }

        public void setOtherKeywords(List<String> otherKeywords) {
            this.otherKeywords = otherKeywords;
        }

        public void addOtherKeyword(String keyword){
            this.otherKeywords.add(keyword);
        }
    }
}
