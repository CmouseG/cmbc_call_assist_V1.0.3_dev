package com.guiji.common.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/** 
* @ClassName: ServerResult 
* @Description: 所有后台服务公共返回通用对象
* @author: weiyunbo
* @date 2018年6月12日 下午8:25:20 
* @version V1.0  
*/
@ApiModel(description= "返回响应数据")
public class ServerResult<T> implements Serializable{
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value = "返回码")
	private String rspCode;
	@ApiModelProperty(value = "返回信息")
    private String rspMsg;
    //返回数据
	@ApiModelProperty(value = "返回业务数据")
    private T data;

	//默认构造函数
	private ServerResult(){}
	
    private ServerResult(String rspCode){
        this.rspCode = rspCode;
    }
    private ServerResult(String rspCode,T data){
        this.rspCode = rspCode;
        this.data = data;
    }
    private ServerResult(String rspCode,String rspMsg,T data){
        this.rspCode = rspCode;
        this.rspMsg = rspMsg;
        this.data = data;
    }

    private ServerResult(String rspCode,String rspMsg){
        this.rspCode = rspCode;
        this.rspMsg = rspMsg;
    }

    public String getRspCode(){
        return rspCode;
    }
    public T getData(){
        return data;
    }
    public String getRspMsg(){
        return rspMsg;
    }

    public static <T> ServerResult<T> createByErrorCodeMessage(String errorCode,String errorMessage){
        return new ServerResult<T>(errorCode,errorMessage);
    }
    
    public static <T> ServerResult<T> create(String rspCode,String rspMsg,T data){
        return new ServerResult<T>(rspCode,rspMsg,data);
    }
    
    /* (non-Javadoc) 
	 * @see java.lang.Object#toString() 
	 */
	@Override
	public String toString() {
		return "ServerResult [rspCode=" + rspCode + ", rspMsg=" + rspMsg + ", data=" + data + "]";
	}

}
