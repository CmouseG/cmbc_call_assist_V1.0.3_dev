package com.guiji.cloud.zuul.exception;

import com.guiji.common.exception.GuiyuException;

/**
 * Created by ty on 2018/12/3.
 */
public class ZuulException extends GuiyuException {
    private static final long serialVersionUID = 1L;

    public ZuulException(){

        super();
    }

    public ZuulException(String msg){
        super(msg);
    }

    public ZuulException(String errorCode,String msg){
        super(errorCode,msg);
    }

    public ZuulException(Throwable throwable){
        super(throwable);
    }

    public ZuulException(String msg,Throwable throwable){
        super(msg, throwable);
    }
}
