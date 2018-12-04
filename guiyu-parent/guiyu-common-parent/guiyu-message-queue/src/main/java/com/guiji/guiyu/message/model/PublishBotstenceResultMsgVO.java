package com.guiji.guiyu.message.model;

import com.guiji.common.model.process.ProcessTypeEnum;

import java.io.Serializable;

/**
 * Created by ty on 2018/11/30.
 */
public class PublishBotstenceResultMsgVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private ProcessTypeEnum processTypeEnum;

    private String tmplId;

    private Integer result;//发布结果0成功，1失败

    public ProcessTypeEnum getProcessTypeEnum() {
        return processTypeEnum;
    }

    public void setProcessTypeEnum(ProcessTypeEnum processTypeEnum) {
        this.processTypeEnum = processTypeEnum;
    }

    public String getTmplId() {
        return tmplId;
    }

    public void setTmplId(String tmplId) {
        this.tmplId = tmplId;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "PublishBotstenceResultMsgVO{" +
                "processTypeEnum=" + processTypeEnum +
                ", tmplId='" + tmplId + '\'' +
                ", result='" + result + '\'' +
                '}';
    }
}
