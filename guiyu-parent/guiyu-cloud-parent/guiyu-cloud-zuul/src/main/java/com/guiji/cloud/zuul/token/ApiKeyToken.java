package com.guiji.cloud.zuul.token;

import org.apache.shiro.authc.UsernamePasswordToken;

public class ApiKeyToken extends UsernamePasswordToken{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ApiKeyToken(String accessKey,String secretKey){
		super(accessKey,secretKey);
	}
}
