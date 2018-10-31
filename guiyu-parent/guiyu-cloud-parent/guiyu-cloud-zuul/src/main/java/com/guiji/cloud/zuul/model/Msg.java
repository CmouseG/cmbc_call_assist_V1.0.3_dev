package com.guiji.cloud.zuul.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

/**
 * Created by Administratorn 2017/12/26.
 * Time:13:41
 * ProjectName:guiyu-cloud-zuul
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Msg<T> implements Serializable{
    private static final long serialVersionUID = -1177183613782210351L;
    private String rspCode;
    private String rspMsg;
    private T data;

    /** 
	 * @return the rspCode 
	 */
	public String getRspCode() {
	
		return rspCode;
	}



	/** 
	 @param rspCode the rspCode to set 
	 */
	public void setRspCode(String rspCode) {
		this.rspCode = rspCode;
	}



	/** 
	 * @return the rspMsg 
	 */
	public String getRspMsg() {
	
		return rspMsg;
	}



	/** 
	 @param rspMsg the rspMsg to set 
	 */
	public void setRspMsg(String rspMsg) {
		this.rspMsg = rspMsg;
	}



	@Override
    public String toString() {
        return "Msg{" +
                "rspCode=" + rspCode +
                ", rspMsg='" + rspMsg + '\'' +
                ", data=" + data +
                '}';
    }
}
