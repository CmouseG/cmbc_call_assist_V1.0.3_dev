package com.guiji.web.request;

import lombok.Data;

import java.util.List;

/**
 * @Auther: 黎阳
 * @Date: 2018/11/27 0027 09:51
 * @Description:
 */
@Data
public class ReqDialogue {
    private String cfg_name;
    private String code;
    private String key;
    private String seqid;
    private List<ReqDialogueItem> data;

    @Data
    public static class ReqDialogueItem {
        private String ai_answer;
        private String current_domain;
        private Integer domain_type;
        private Integer hangup_type;
        private Integer is_refused;
        private String keywords;
        private Integer match_type;
        private String next_domain;
        private String sentence;
    }
}
