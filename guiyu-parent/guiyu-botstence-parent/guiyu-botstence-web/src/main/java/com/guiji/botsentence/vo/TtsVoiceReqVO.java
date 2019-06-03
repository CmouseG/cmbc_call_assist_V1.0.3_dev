package com.guiji.botsentence.vo;

import java.util.List;

public class TtsVoiceReqVO {

    /**
     * 录音ID
     */
    private Long voliceId;

    /**
     * tts合成类型：1-变量;2-变量加内容
     */
    private Integer ttsCompositeType;

    /**
     * 备用话术Id
     */
    private String backupId;

    /**
     * 备用话术内容
     */
    private String backupContent;

    /**
     * 话术文案分段list
     */
    private List<TtsTaskVO> taskVOList;


    public Long getVoliceId() {
        return voliceId;
    }

    public void setVoliceId(Long voliceId) {
        this.voliceId = voliceId;
    }

    public Integer getTtsCompositeType() {
        return ttsCompositeType;
    }

    public void setTtsCompositeType(Integer ttsCompositeType) {
        this.ttsCompositeType = ttsCompositeType;
    }

    public String getBackupId() {
        return backupId;
    }

    public void setBackupId(String backupId) {
        this.backupId = backupId;
    }

    public String getBackupContent() {
        return backupContent;
    }

    public void setBackupContent(String backupContent) {
        this.backupContent = backupContent;
    }

    public List<TtsTaskVO> getTaskVOList() {
        return taskVOList;
    }

    public void setTaskVOList(List<TtsTaskVO> taskVOList) {
        this.taskVOList = taskVOList;
    }
}
