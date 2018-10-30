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
	
	public static Result.ReturnData error(String code){
		String msg=properties.getProperty(code);
		return new Result.ReturnData(code,msg);
	}
	
	@Autowired(required = true)
	public  void setProperties(@Qualifier("errorProperties") Properties properties) {
		Result.properties = properties;
	}


	public static class ReturnData<T>{
		public String code="0";
		public String msg="请求成功3";
		public T body;
		
		public ReturnData(){
		}
		
		public ReturnData(String code,String msg){
			this.code=code;
			this.msg=msg;
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
