package com.guiji.fsagent.entity;

import java.io.Serializable;

public class TtsWav implements Serializable {

    private static final long serialVersionUID = 1L;

    private String fileName;
    private Double fileDuration;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Double getFileDuration() {
        return fileDuration;
    }

    public void setFileDuration(Double fileDuration) {
        this.fileDuration = fileDuration;
    }

    @Override
    public String toString() {
        return "TtsWav{" +
                "fileName='" + fileName + '\'' +
                ", fileDuration=" + fileDuration +
                '}';
    }
}
