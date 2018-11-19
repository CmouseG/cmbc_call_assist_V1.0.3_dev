package com.guiji.process.vo;

import java.io.Serializable;

public class DeviceMsgVO extends DeviceVO implements Serializable {


    private CmdTypeEnum cmdType;

    public CmdTypeEnum getCmdType() {
        return cmdType;
    }

    public void setCmdType(CmdTypeEnum cmdType) {
        this.cmdType = cmdType;
    }

    @Override
    public String toString() {
        return "DeviceMsgVO{" +
                "cmdType=" + cmdType +
                super.toString()+
                '}';
    }
}
