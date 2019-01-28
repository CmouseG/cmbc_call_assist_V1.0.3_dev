package com.guiji.botsentence.vo;

import lombok.Data;

import java.util.List;

@Data
public class SaveIntentVO {
    private String branchId;
    private List<BotSentenceIntentVO> list;
}
