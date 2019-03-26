package com.guiji.dispatch.enums;

/**
 * 计划任务线路类型
 */
public enum FileTypeEnum {

    EXECL(1, "EXECL文件"),

    AUDIO(2, "语音文件"),

    VIDEO(3, "视频文件"),
    ;

    private Integer type;

    private String message;

    private FileTypeEnum(Integer type, String message) {
        this.type = type;
        this.message = message;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
