package ai.guiji.botsentence.api.entity;

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

    public static <T> ServerResult<T> createBySuccess(){
        return new ServerResult<T>(ResponseCodeEnum.SUCCESS.getRspCode());
    }

    public static <T> ServerResult<T> createBySuccessMessage(String rspMsg){
        return new ServerResult<T>(ResponseCodeEnum.SUCCESS.getRspCode(),rspMsg);
    }

    public static <T> ServerResult<T> createBySuccess(T data){
        return new ServerResult<T>(ResponseCodeEnum.SUCCESS.getRspCode(),data);
    }

    public static <T> ServerResult<T> createBySuccess(String rspMsg,T data){
        return new ServerResult<T>(ResponseCodeEnum.SUCCESS.getRspCode(),rspMsg,data);
    }

    public static <T> ServerResult<T> createByError(){
        return new ServerResult<T>(ResponseCodeEnum.ERROR.getRspCode(),ResponseCodeEnum.ERROR.getRspMsg());
    }

    public static <T> ServerResult<T> createByErrorMessage(String errorMessage){
        return new ServerResult<T>(ResponseCodeEnum.ERROR.getRspCode(),errorMessage);
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
        private String rspCode;  
        //返回信息
        private String rspMsg;  
        private ResponseCodeEnum(String rspCode, String rspMsg) {
            this.rspCode = rspCode;  
            this.rspMsg = rspMsg;  
        }  
        //根据枚举的code获取msg的方法  
        public static String getMsgByCode(String rspCode){  
            for(ResponseCodeEnum responseEnum : ResponseCodeEnum.values()) {  
                if(responseEnum.getRspCode().equals(rspCode)){  
                    return responseEnum.rspMsg;  
                }  
            }  
            return null;  
        }
    	/**
    	 * @return the rspCode
    	 */
    	public String getRspCode() {
    		return rspCode;
    	}
    	/**
    	 * @param rspCode the rspCode to set
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
    	 * @param rspMsg the rspMsg to set
    	 */
    	public void setRspMsg(String rspMsg) {
    		this.rspMsg = rspMsg;
    	} 
    }
}
