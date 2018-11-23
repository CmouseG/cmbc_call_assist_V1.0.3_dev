package com.guiji.robot.exception;

import com.guiji.common.exception.GuiyuException;

/** 
* @ClassName: RobotException 
* @Description: Robot模块异常信息
* @version V1.0  
*/
public class RobotException extends GuiyuException{
	private static final long serialVersionUID = 1L;
	
	public RobotException(){
		
		super();
	}
	
	public RobotException(String msg){
		super(msg);
	}
	
	public RobotException(String errorCode,String msg){
		super(errorCode,msg);
	}
	
	public RobotException(Throwable throwable){
		super(throwable);
	}
	
	public RobotException(String msg,Throwable throwable){
		super(msg, throwable);
	}
	
}
