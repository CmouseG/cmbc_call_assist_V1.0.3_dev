package com.guiji.botsentence.vo;

import lombok.Data;

import java.util.List;
@Data
public class QueryKeywordsByIndustryVO {
    private String type;
    private List<KeywordsVO> keywordsList;
}
