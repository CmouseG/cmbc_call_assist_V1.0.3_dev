package com.guiji.process.agent.model;

import com.guiji.process.core.vo.CmdTypeEnum;

public class CfgProcessOperVO {

    private CmdTypeEnum cmdTypeEnum;

    private String cmd;

    public CmdTypeEnum getCmdTypeEnum() {
        return cmdTypeEnum;
    }

    public void setCmdTypeEnum(String cmdTypeEnum) {
        this.cmdTypeEnum = CmdTypeEnum.valueOf(cmdTypeEnum);
    }

    public void setCmdTypeEnum(Integer cmdTypeEnum) {
        this.cmdTypeEnum = CmdTypeEnum.valueOf(cmdTypeEnum);
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    @Override
    public String toString() {
        return "CfgProcessOperVO{" +
                "cmdTypeEnum=" + cmdTypeEnum +
                ", cmd='" + cmd + '\'' +
                '}';
    }
}
