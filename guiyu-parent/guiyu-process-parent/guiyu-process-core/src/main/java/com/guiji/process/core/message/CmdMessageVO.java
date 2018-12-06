package com.guiji.process.core.message;

import com.guiji.process.core.vo.CmdTypeEnum;
import com.guiji.common.model.process.ProcessInstanceVO;

import java.io.Serializable;
import java.util.List;

public class CmdMessageVO  implements Serializable {

    private static final long serialVersionUID = 1L;

    private String reqKey;

    private CmdMsgTypeEnum msgTypeEnum;

    private CmdTypeEnum cmdType;

    private List<String> parameters;

    private ProcessInstanceVO processInstanceVO;

    private String commandResult;

    private String commandResultDesc;

    private Long userId;

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

    public List<String> getParameters() {
        return parameters;
    }

    public void setParameters(List<String> parameters) {
        this.parameters = parameters;
    }

    public String getReqKey() {
        return reqKey;
    }

    public void setReqKey(String reqKey) {
        this.reqKey = reqKey;
    }

    public CmdMsgTypeEnum getMsgTypeEnum() {
        return msgTypeEnum;
    }

    public void setMsgTypeEnum(CmdMsgTypeEnum msgTypeEnum) {
        this.msgTypeEnum = msgTypeEnum;
    }

    public String getCommandResult() {
        return commandResult;
    }

    public void setCommandResult(String commandResult) {
        this.commandResult = commandResult;
    }

    public String getCommandResultDesc() {
        return commandResultDesc;
    }

    public void setCommandResultDesc(String commandResultDesc) {
        this.commandResultDesc = commandResultDesc;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "CmdMessageVO{" +
                "reqKey='" + reqKey + '\'' +
                ", msgTypeEnum=" + msgTypeEnum +
                ", cmdType=" + cmdType +
                ", parameters=" + parameters +
                ", processInstanceVO=" + processInstanceVO +
                ", commandResult='" + commandResult + '\'' +
                ", commandResultDesc='" + commandResultDesc + '\'' +
                ", userId=" + userId +
                '}';
    }
}
