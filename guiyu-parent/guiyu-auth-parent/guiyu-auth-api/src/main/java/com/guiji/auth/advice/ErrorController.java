package com.guiji.auth.advice;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.guiji.common.result.Result;
import com.guiji.common.result.Result.ReturnData;

@RestController
public class ErrorController {
	
	@RequestMapping("noLogin")
	public ReturnData noLogin(){
		return Result.error("0001001");
	}
	
	@RequestMapping("notAuth")
	public ReturnData notAuth(){
		return Result.error("0001002");
	}
}
