package com.guiji.process.server.exception;

import com.guiji.common.exception.GuiyuException;

/**
 * Created by ty on 2018/12/4.
 */
public class ProcessException extends GuiyuException {
    private static final long serialVersionUID = 1L;

    public ProcessException(){

        super();
    }

    public ProcessException(String msg){
        super(msg);
    }

    public ProcessException(String errorCode,String msg){
        super(errorCode,msg);
    }

    public ProcessException(Throwable throwable){
        super(throwable);
    }

    public ProcessException(String msg,Throwable throwable){
        super(msg, throwable);
    }
}
