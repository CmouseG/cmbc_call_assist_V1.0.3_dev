package com.guiji.calloutserver.entity;

import lombok.Data;

@Data
public class SellbotIsMatchResponse {
    private int matched;
    private String answer;

    public boolean isMatched(){
        return matched == 1;
    }
}
