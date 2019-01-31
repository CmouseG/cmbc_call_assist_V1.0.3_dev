package com.guiji.component.result;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

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
	private String code;
	@ApiModelProperty(value = "返回信息")
    private String msg;
    //返回数据
	@ApiModelProperty(value = "返回业务数据")
    private T data;

	//默认构造函数
	private ServerResult(){}
	
    private ServerResult(String code){
        this.code = code;
    }
    private ServerResult(String code,T data){
        this.code = code;
        this.data = data;
    }
    private ServerResult(String code,String msg,T data){
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    private ServerResult(String code,String msg){
        this.code = code;
        this.msg = msg;
    }

    public String getCode(){
        return code;
    }
    public T getData(){
        return data;
    }
    public String getMsg(){
        return msg;
    }

    public static <T> ServerResult<T> createBySuccess(){
        return new ServerResult<T>(ResponseCodeEnum.SUCCESS.getCode());
    }

    public static <T> ServerResult<T> createBySuccessMessage(String msg){
        return new ServerResult<T>(ResponseCodeEnum.SUCCESS.getCode(),msg);
    }

    public static <T> ServerResult<T> createBySuccess(T data){
        return new ServerResult<T>(ResponseCodeEnum.SUCCESS.getCode(),data);
    }

    public static <T> ServerResult<T> createBySuccess(String msg,T data){
        return new ServerResult<T>(ResponseCodeEnum.SUCCESS.getCode(), msg, data);
    }

    public static <T> ServerResult<T> createByError(){
        return new ServerResult<T>(ResponseCodeEnum.ERROR.getCode(),ResponseCodeEnum.ERROR.getMsg());
    }

    public static <T> ServerResult<T> createByErrorMessage(String errorMessage){
        return new ServerResult<T>(ResponseCodeEnum.ERROR.getCode(),errorMessage);
    }

    public static <T> ServerResult<T> createByErrorCodeMessage(String errorCode,String errorMessage){
        return new ServerResult<T>(errorCode,errorMessage);
    }
    
    public static <T> ServerResult<T> create(String code,String msg,T data){
        return new ServerResult<T>(code,msg,data);
    }
    
    /* (non-Javadoc) 
	 * @see java.lang.Object#toString() 
	 */
	@Override
	public String toString() {
		return "ServerResult [code=" + code + ", msg=" + msg + ", data=" + data + "]";
	}


	/** 
    * @ClassName: ResponseCodeEnum 
    * @Description: 服务请求返回码定义
    * @author: weiyunbo
    * @date 2018年6月12日 下午8:16:46 
    * @version V1.0  
    */
    public enum ResponseCodeEnum {
    	SUCCESS("000000","SUCCESS"),
        ERROR("999999","系统异常，请联系管理员");
    	//返回码
        private String code;  
        //返回信息
        private String msg;  
        private ResponseCodeEnum(String code, String msg) {
            this.code = code;  
            this.msg = msg;  
        }  
        //根据枚举的code获取msg的方法  
        public static String getMsgByCode(String code){  
            for(ResponseCodeEnum responseEnum : ResponseCodeEnum.values()) {
                if(responseEnum.getCode().equals(code)){  
                    return responseEnum.msg;  
                }  
            }  
            return null;  
        }
    	/**
    	 * @return the rspCode
    	 */
    	public String getCode() {
    		return code;
    	}
    	/**
    	 * @param rspCode the rspCode to set
    	 */
    	public void setCode(String code) {
    		this.code = code;
    	}
    	/**
    	 * @return the rspMsg
    	 */
    	public String getMsg() {
    		return msg;
    	}
    	/**
    	 * @param rspMsg the rspMsg to set
    	 */
    	public void setMsg(String msg) {
    		this.msg = msg;
    	} 
    }
}
