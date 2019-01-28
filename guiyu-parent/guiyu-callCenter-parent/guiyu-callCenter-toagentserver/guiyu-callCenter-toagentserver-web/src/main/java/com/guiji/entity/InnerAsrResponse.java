package com.guiji.entity;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;

@Slf4j
@Data
public class InnerAsrResponse {
    private Header header;
    private Payload payload;

    public String getBeginTime(){
        if(payload==null){
            return null;
        }

        LocalDateTime time = LocalDateTime.now().minus(getDuration(), ChronoField.MILLI_OF_DAY.getBaseUnit());
//        log.info("asr获取开始时间，当前时间[{}], asr开始时间[{}]", LocalDateTime.now(), time);
        return time.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public String getEndTime(){
        if(payload==null){
            return null;
        }

        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public Long getDuration(){
        if(payload == null){
            return null;
        }

        return (long)(payload.getTime() - payload.getBegin_time());
    }

    public String getAsrText(){
        if(payload == null){
            return null;
        }

        return payload.getResult();
    }

    @Data
    public static class Payload {
        private float index;
        private float time;
        private float begin_time;
        private String result;
        private float confidence;
    }

    @Data
    public static class Header {
        private String namespace;
        private String name;
        private float status;
        private String message_id;
        private String task_id;
        private String status_text;
    }
}
