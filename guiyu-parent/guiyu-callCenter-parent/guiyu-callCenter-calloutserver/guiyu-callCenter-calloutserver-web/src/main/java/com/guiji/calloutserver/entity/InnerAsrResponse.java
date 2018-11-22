package com.guiji.calloutserver.entity;

import com.guiji.calloutserver.util.DateUtil;
import lombok.Data;

import java.util.List;

@Data
public class InnerAsrResponse {
    private int finish;
    private String request_id;
    private ResultBean result;
    private int status_code;
    private String version;

    public String getBeginTime(){
        if(result==null || result.getBegin_time()==null){
            return null;
        }

        return DateUtil.timeStampToDate(result.getBegin_time());
    }

    public String getEndTime(){
        if(result==null || result.getEnd_time()==null){
            return null;
        }

        return DateUtil.timeStampToDate(result.getEnd_time());
    }

    public Long getDuration(){
        if(result == null || result.getBegin_time()==null || result.getEnd_time()==null){
            return null;
        }

        return result.getEnd_time() - result.getBegin_time();
    }

    public String getAsrText(){
        if(result == null){
            return null;
        }

        return result.getText();
    }

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
