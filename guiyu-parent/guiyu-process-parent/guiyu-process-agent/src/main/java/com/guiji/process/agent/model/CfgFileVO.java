package com.guiji.process.agent.model;

public class CfgFileVO {

    private String fileName;

    private CfgProcessVO cfgProcessVO;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public CfgProcessVO getCfgProcessVO() {
        return cfgProcessVO;
    }

    public void setCfgProcessVO(CfgProcessVO cfgProcessVO) {
        this.cfgProcessVO = cfgProcessVO;
    }

    @Override
    public String toString() {
        return "CfgFileVO{" +
                "fileName='" + fileName + '\'' +
                ", cfgProcessVO=" + cfgProcessVO +
                '}';
    }
}
