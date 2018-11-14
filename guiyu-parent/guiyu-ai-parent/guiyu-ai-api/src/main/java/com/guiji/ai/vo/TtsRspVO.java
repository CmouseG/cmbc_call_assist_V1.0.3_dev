package com.guiji.ai.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Map;

/**
 * 语音合成返回对象
 * Created by ty on 2018/11/13.
 */
@ApiModel(value="TtsRspVO对象",description="语音合成返回对象")
public class TtsRspVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value="业务ID")
    private String busId;

    @ApiModelProperty(value="模型名称")
    private String model;

    @ApiModelProperty(value="文本和音频下载地址,key是文本value是音频")
    private Map<String,String> audios;

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

    public Map<String, String> getAudios() {
        return audios;
    }

    public void setAudios(Map<String, String> audios) {
        this.audios = audios;
    }

    @Override
    public String toString() {
        return "TtsRspVO{" +
                "busId='" + busId + '\'' +
                ", model='" + model + '\'' +
                ", audios=" + audios +
                '}';
    }
}
