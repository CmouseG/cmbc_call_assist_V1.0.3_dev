package com.guiji.common.result;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class Result {
	
	private static Properties properties;
	
	public static Result.ReturnData ok(Object obj){
		return new Result.ReturnData(obj);
	}
	
	public static Result.ReturnData ok(){
		return new Result.ReturnData();
	}
	
	public static Result.ReturnData error(String code){
		String msg=properties.getProperty(code);
		return new Result.ReturnData(code,msg);
	}
	
	@Autowired(required = true)
	public  void setProperties(@Qualifier("errorProperties") Properties properties) {
		Result.properties = properties;
	}


	public static class ReturnData{
		public String code="0";
		public String msg="请求成功3";
		public Object body;
		
		public ReturnData(){
		}
		
		public ReturnData(String code,String msg){
			this.code=code;
			this.msg=msg;
		}
		
		public ReturnData(Object body){
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

		public Object getBody() {
			return body;
		}

		public void setBody(Object body) {
			this.body = body;
		}
	}
}
