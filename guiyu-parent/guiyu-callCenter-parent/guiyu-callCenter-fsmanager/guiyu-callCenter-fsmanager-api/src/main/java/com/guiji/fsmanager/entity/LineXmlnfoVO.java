package com.guiji.fsmanager.entity;

import java.io.Serializable;

/**
 * @Auther: 黎阳
 * @Date: 2018/10/26 0026 17:16
 * @Description:
 */
public class LineXmlnfoVO implements Serializable {

    private String configType;

    private String fileName;

    private String fileData;

    private static final long serialVersionUID = 1L;

    public String getConfigType() {
        return configType;
    }

    public void setConfigType(String configType) {
        this.configType = configType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileData() {
        return fileData;
    }

    public void setFileData(String fileData) {
        this.fileData = fileData;
    }

    @Override
    public String toString() {
        return "LineXmlnfo{" +
                "configType='" + configType + '\'' +
                ", fileName='" + fileName + '\'' +
                ", fileData='" + fileData + '\'' +
                '}';
    }


}
