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

    @ApiModelProperty(value="合成状态:S-成功;F-失败",required=true)
	private String status;
    
    @ApiModelProperty(value="失败原因")
    private String errorMsg;

    @ApiModelProperty(value="文本和音频下载地址,key是文本value是音频")
    private Map<String,String> audios;

	
    public String getBusId() {
		return busId;
	}

	public void setBusId(String busId) {
		this.busId = busId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public Map<String, String> getAudios() {
		return audios;
	}

	public void setAudios(Map<String, String> audios) {
		this.audios = audios;
	}

	@Override
	public String toString() {
		return "TtsRspVO{"
				+ "busId=" + busId + ", "
				+ "status=" + status + ", "
				+ "errorMsg=" + errorMsg + ", "
				+ "audios=" + audios
				+ "}";
	}
}
