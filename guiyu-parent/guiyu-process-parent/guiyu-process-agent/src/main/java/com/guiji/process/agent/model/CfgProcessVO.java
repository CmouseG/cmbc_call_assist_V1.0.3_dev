package com.guiji.process.agent.model;

import com.guiji.common.model.process.ProcessTypeEnum;
import com.guiji.process.core.vo.CmdTypeEnum;

import java.util.List;

public class CfgProcessVO {

    private String name;

    private int port;

    private ProcessTypeEnum processTypeEnum;

    private List<CfgProcessOperVO> CfgNodeOpers;

    private String processKey;


    public ProcessTypeEnum getProcessTypeEnum() {
        return processTypeEnum;
    }

    public void setProcessTypeEnum(Integer processTypeEnum) {
        this.processTypeEnum = ProcessTypeEnum.valueOf(processTypeEnum);
    }

    public void setProcessTypeEnum(String processTypeEnum) {
        this.processTypeEnum = ProcessTypeEnum.valueOf(processTypeEnum);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public List<CfgProcessOperVO> getCfgNodeOpers() {
        return CfgNodeOpers;
    }

    public void setCfgNodeOpers(List<CfgProcessOperVO> cfgNodeOpers) {
        CfgNodeOpers = cfgNodeOpers;
    }

    public String getProcessKey() {
        return processKey;
    }

    public void setProcessKey(String processKey) {
        this.processKey = processKey;
    }


    public CfgProcessOperVO getCfgNodeOper(CmdTypeEnum cmdTypeEnum)
    {
        if(this.CfgNodeOpers== null)
        {
            return null;
        }

        for (CfgProcessOperVO cfgProcessOperVO : CfgNodeOpers) {
            if(cfgProcessOperVO.getCmdTypeEnum()== cmdTypeEnum)
            {
                return cfgProcessOperVO;
            }
        }

        return null;
    }

    @Override
    public String toString() {
        return "CfgProcessVO{" +
                "name='" + name + '\'' +
                ", port=" + port +
                ", processTypeEnum=" + processTypeEnum +
                ", CfgNodeOpers=" + CfgNodeOpers +
                ", processKey='" + processKey + '\'' +
                '}';
    }
}
