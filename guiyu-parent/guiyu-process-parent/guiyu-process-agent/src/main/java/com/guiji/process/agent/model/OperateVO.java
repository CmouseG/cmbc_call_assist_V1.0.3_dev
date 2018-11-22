package com.guiji.process.agent.model;

import com.guiji.process.core.vo.CmdTypeEnum;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by ty on 2018/11/22.
 */
public class OperateVO implements Serializable{
    private static final long serialVersionUID = 1L;
    private int port;
    private CmdTypeEnum cmdTypeEnum;
    private Date time;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public CmdTypeEnum getCmdTypeEnum() {
        return cmdTypeEnum;
    }

    public void setCmdTypeEnum(CmdTypeEnum cmdTypeEnum) {
        this.cmdTypeEnum = cmdTypeEnum;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "OperateVO{" +
                "port='" + port + '\'' +
                ", cmdTypeEnum=" + cmdTypeEnum +
                ", time=" + time +
                '}';
    }
}
