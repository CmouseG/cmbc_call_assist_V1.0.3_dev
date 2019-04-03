package com.guiji.calloutserver.entity;

import lombok.Data;

@Data
public class SellbotIsMatchResponse {
    private int matched;
    private String sentence;

    public boolean isMatched(){
        return matched == 1;
    }
}
