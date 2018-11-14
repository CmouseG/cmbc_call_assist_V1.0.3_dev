package com.guiji.ai.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * 语音合成请求对象
 * Created by ty on 2018/11/13.
 */
@ApiModel(value="TtsReqVO对象",description="语音合成请求对象")
public class TtsReqVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value="业务ID")
    private String busId;

    @ApiModelProperty(value="模型名称")
    private String model;

    @ApiModelProperty(value="文本集合")
    private List<String> contents;

    public String getBusId() {
        return busId;
    }

    public void setBusId(String busId) {
        this.busId = busId;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public List<String> getContents() {
        return contents;
    }

    public void setContents(List<String> contents) {
        this.contents = contents;
    }

    @Override
    public String toString() {
        return "TtsReqVO{" +
                "busId='" + busId + '\'' +
                ", model='" + model + '\'' +
                ", contents=" + contents +
                '}';
    }
}
