package com.guiji.process.agent.model;

import java.util.List;

public class CfgFileVO {

    private String fileName;

    private CfgNodeVO cfgNodeVO;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public CfgNodeVO getCfgNodeVO() {
        return cfgNodeVO;
    }

    public void setCfgNodeVO(CfgNodeVO cfgNodeVO) {
        this.cfgNodeVO = cfgNodeVO;
    }

    @Override
    public String toString() {
        return "CfgFileVO{" +
                "fileName='" + fileName + '\'' +
                ", cfgNodeVO=" + cfgNodeVO +
                '}';
    }
}
