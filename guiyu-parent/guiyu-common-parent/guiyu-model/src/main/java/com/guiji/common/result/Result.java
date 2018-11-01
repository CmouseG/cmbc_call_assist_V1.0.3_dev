package com.guiji.common.result;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class Result {
	
	private static Properties properties;
	
	public static <T> Result.ReturnData<T> ok(T obj){
		return new Result.ReturnData<T>(obj);
	}
	
	public static <T> Result.ReturnData<T> ok(){
		return new Result.ReturnData<T>();
	}
	
	public static <T> Result.ReturnData<T> error(String code){
		String msg=properties.getProperty(code);
		return new Result.ReturnData<T>(code,msg,false);
	}
	
	@Autowired(required = true)
	public  void setProperties(@Qualifier("errorProperties") Properties properties) {
		Result.properties = properties;
	}


	public static class ReturnData<T>{
		public String code="0";
		public String msg="请求成功";
		public boolean success = true;
		public T body;
		
		public ReturnData(){
		}
		
		public ReturnData(String code,String msg,boolean success){
			this.code=code;
			this.msg=msg;
			this.success=success;
		}
		
		public ReturnData(T body){
			this.body=body;
		}
		
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getMsg() {
			return msg;
		}
		public void setMsg(String msg) {
			this.msg = msg;
		}

		public T getBody() {
			return body;
		}

		public void setBody(T body) {
			this.body = body;
		}
	}
}
