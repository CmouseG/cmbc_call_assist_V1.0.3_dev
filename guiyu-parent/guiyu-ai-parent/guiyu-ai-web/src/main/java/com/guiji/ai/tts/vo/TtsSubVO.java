package com.guiji.ai.tts.vo;

import java.io.Serializable;

/**
 * Created by ty on 2018/11/13.
 */
public class TtsSubVO implements Serializable{
    private static final long serialVersionUID = 1L;
    private String busId;

    private String model;

    private String content;

    public String getBusId() {
        return busId;
    }

    public void setBusId(String busId) {
        this.busId = busId;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "TtsSubVO{" +
                "busId='" + busId + '\'' +
                ", model='" + model + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
