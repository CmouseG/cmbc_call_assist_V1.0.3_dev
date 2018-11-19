package com.guiji.process.core.message;

import com.guiji.process.core.vo.CmdTypeEnum;
import com.guiji.process.core.vo.ProcessInstanceVO;

import java.io.Serializable;

public class CmdMessageVO  implements Serializable {

    private static final long serialVersionUID = 1L;

    private CmdTypeEnum cmdType;

    private ProcessInstanceVO processInstanceVO;

    public CmdTypeEnum getCmdType() {
        return cmdType;
    }

    public void setCmdType(CmdTypeEnum cmdType) {
        this.cmdType = cmdType;
    }

    public ProcessInstanceVO getProcessInstanceVO() {
        return processInstanceVO;
    }

    public void setProcessInstanceVO(ProcessInstanceVO processInstanceVO) {
        this.processInstanceVO = processInstanceVO;
    }

    @Override
    public String toString() {
        return "CmdMessageVO{" +
                "cmdType=" + cmdType +
                ", processInstanceVO=" + processInstanceVO +
                '}';
    }
}
