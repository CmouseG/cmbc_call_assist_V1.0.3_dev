package com.guiji.botsentence.vo;

import lombok.Data;

/**
 * @Auther: 魏驰
 * @Date: 2019/1/9 15:23
 * @Project：guiji-parent
 * @Description:
 */
@Data
public class ResponseSelfTestVO {
    private String wavFileUrl;
    private Integer wavDuration;
    private String answerTxt;
    private String uuid;

    private String word_segment_result;
    private int match_type;
    private String keywords;
    private int match_method;
    private String intention_confidence;
    
    private String httpFileUrl;
    private int end;
    private int port;
    private String accurate_intent;
}
