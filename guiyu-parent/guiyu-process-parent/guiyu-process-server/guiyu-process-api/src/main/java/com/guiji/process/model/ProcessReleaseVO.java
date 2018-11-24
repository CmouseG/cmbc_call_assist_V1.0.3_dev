package com.guiji.process.model;

import com.guiji.common.model.process.ProcessInstanceVO;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ty on 2018/11/23.
 */
public class ProcessReleaseVO implements Serializable {
    private List<ProcessInstanceVO> processInstanceVOS;

    public List<ProcessInstanceVO> getProcessInstanceVOS() {
        return processInstanceVOS;
    }

    public void setProcessInstanceVOS(List<ProcessInstanceVO> processInstanceVOS) {
        this.processInstanceVOS = processInstanceVOS;
    }

    @Override
    public String toString() {
        return "ProcessReleaseVO{" +
                "processInstanceVOS=" + processInstanceVOS +
                '}';
    }
}
