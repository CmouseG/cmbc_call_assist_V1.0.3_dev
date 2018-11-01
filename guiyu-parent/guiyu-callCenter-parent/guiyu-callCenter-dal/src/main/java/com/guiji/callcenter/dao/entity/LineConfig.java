package com.guiji.callcenter.dao.entity;

import java.io.Serializable;

public class LineConfig implements Serializable {
    private String lineId;

    private String fileType;

    private String fileName;

    private String fileData;

    private static final long serialVersionUID = 1L;

    public String getLineId() {
        return lineId;
    }

    public void setLineId(String lineId) {
        this.lineId = lineId == null ? null : lineId.trim();
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType == null ? null : fileType.trim();
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName == null ? null : fileName.trim();
    }

    public String getFileData() {
        return fileData;
    }

    public void setFileData(String fileData) {
        this.fileData = fileData == null ? null : fileData.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", lineId=").append(lineId);
        sb.append(", fileType=").append(fileType);
        sb.append(", fileName=").append(fileName);
        sb.append(", fileData=").append(fileData);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}