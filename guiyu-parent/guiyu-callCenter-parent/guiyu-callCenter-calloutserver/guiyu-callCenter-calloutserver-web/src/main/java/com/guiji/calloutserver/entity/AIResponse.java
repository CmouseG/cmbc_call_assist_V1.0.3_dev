package com.guiji.calloutserver.entity;

import com.guiji.calloutserver.enm.EAIResponseType;
import lombok.Builder;
import lombok.Data;

//@Data
//@Builder
public class AIResponse {
    private boolean result;
    private String msg;

    private String callId;

    //机器人标识该次通话的id
    private String aiId;

    //待播放的录音文件时长
    private String wavFile;

    //文件时长
    private Double wavDuration;

    //sellbot应答文本
    private String responseTxt;

    //意向标签
    private String accurateIntent;

    //原因
    private String reason;

    //应答类型
    private EAIResponseType aiResponseType;

    //是否匹配到ai的关键词
    private boolean isMatched;

    private String keyWords;
    private String wordSegmentResult;


    public void setAiResponseType(Integer end) {
        EAIResponseType type = EAIResponseType.UNKNOWN;
        if(end!=null){
            switch (end){
                case 0:
                    type = EAIResponseType.NORMAL;
                    break;
                case 1:
                    type = EAIResponseType.END;
                    break;
                case 2:
                    type = EAIResponseType.TO_AGENT;
                    break;
            }
        }
        this.aiResponseType = type;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCallId() {
        return callId;
    }

    public void setCallId(String callId) {
        this.callId = callId;
    }

    public String getAiId() {
        return aiId;
    }

    public void setAiId(String aiId) {
        this.aiId = aiId;
    }

    public String getWavFile() {
        return wavFile;
    }

    public void setWavFile(String wavFile) {
        this.wavFile = wavFile;
    }

    public Double getWavDuration() {
        return wavDuration;
    }

    public void setWavDuration(Double wavDuration) {
        this.wavDuration = wavDuration;
    }

    public String getResponseTxt() {
        return responseTxt;
    }

    public void setResponseTxt(String responseTxt) {
        this.responseTxt = responseTxt;
    }

    public String getAccurateIntent() {
        return accurateIntent;
    }

    public void setAccurateIntent(String accurateIntent) {
        this.accurateIntent = accurateIntent;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public EAIResponseType getAiResponseType() {
        return aiResponseType;
    }

    public String getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(String keyWords) {
        this.keyWords = keyWords;
    }

    public String getWordSegmentResult() {
        return wordSegmentResult;
    }

    public void setWordSegmentResult(String wordSegmentResult) {
        this.wordSegmentResult = wordSegmentResult;
    }

    public boolean isMatched() {
        return isMatched;
    }

    public void setMatched(boolean matched) {
        isMatched = matched;
    }
}
