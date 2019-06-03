package com.guiji.botsentence.vo;

public class TtsVoiceVO {

    /**
     * 录音ID
     */
    private Long voliceId;

    /**
     * 内容
     */
    private String content;

    /**
     * 是否有备用话术
     */
    private Boolean hasBackup = false;


    public Long getVoliceId() {
        return voliceId;
    }

    public void setVoliceId(Long voliceId) {
        this.voliceId = voliceId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getHasBackup() {
        return hasBackup;
    }

    public void setHasBackup(Boolean hasBackup) {
        this.hasBackup = hasBackup;
    }
}
