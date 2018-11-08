package com.guiji.calloutserver.entity;

import lombok.Data;

import java.util.List;

@Data
public class InnerAsrResponse {
    private int finish;
    private String request_id;
    private ResultBean result;
    private int status_code;
    private String version;

    @Data
    public static class ResultBean {
        private int sentence_id;
        private Long begin_time;
        private Long current_time;
        private Long end_time;
        private int status_code;
        private String text;
        private List<?> words;
    }
}
