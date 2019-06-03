package com.guiji.botsentence.vo;

public class TtsTaskVO{


    private Long taskId;

    /**
     * 序号
     */
    private Integer index;

    /**
     * 内容
     */
    private String content;

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
