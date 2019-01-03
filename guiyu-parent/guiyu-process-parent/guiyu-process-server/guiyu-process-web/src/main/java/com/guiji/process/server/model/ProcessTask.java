package com.guiji.process.server.model;

import com.guiji.common.model.process.ProcessInstanceVO;
import com.guiji.process.core.vo.CmdTypeEnum;

import java.util.Date;
import java.util.List;

/**
 * Created by ty on 2018/12/10.
 */
public class ProcessTask {
    private ProcessInstanceVO processInstanceVO;

    private CmdTypeEnum cmdType;

    private List<String> parameters;

    private Long userId;

    private String reqKey;

    private int retryCount;

    private Integer result;

    private Date exeTime;

    public ProcessInstanceVO getProcessInstanceVO() {
        return processInstanceVO;
    }

    public void setProcessInstanceVO(ProcessInstanceVO processInstanceVO) {
        this.processInstanceVO = processInstanceVO;
    }

    public CmdTypeEnum getCmdType() {
        return cmdType;
    }

    public void setCmdType(CmdTypeEnum cmdType) {
        this.cmdType = cmdType;
    }

    public List<String> getParameters() {
        return parameters;
    }

    public void setParameters(List<String> parameters) {
        this.parameters = parameters;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getReqKey() {
        return reqKey;
    }

    public void setReqKey(String reqKey) {
        this.reqKey = reqKey;
    }

    public int getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }

    public Date getExeTime() {
        return exeTime;
    }

    public void setExeTime(Date exeTime) {
        this.exeTime = exeTime;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "ProcessTask{" +
                "processInstanceVO=" + processInstanceVO +
                ", cmdType=" + cmdType +
                ", parameters=" + parameters +
                ", userId=" + userId +
                ", reqKey='" + reqKey + '\'' +
                ", retryCount=" + retryCount +
                ", result='" + result + '\'' +
                ", exeTime=" + exeTime +
                '}';
    }
}
