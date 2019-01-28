package com.guiji.web.request;

import lombok.Data;

@Data
public class DetailsVO implements Comparable<DetailsVO>{
    private String who;
    private String say;
    private String date;

    @Override
    public int compareTo(DetailsVO o) {
        return this.date.compareTo(o.getDate());
    }
}
