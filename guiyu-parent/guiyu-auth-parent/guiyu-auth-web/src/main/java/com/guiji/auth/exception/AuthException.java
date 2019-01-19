package com.guiji.auth.exception;

import com.guiji.common.exception.GuiyuException;

/**
 * Created by ty on 2018/12/4.
 */
public class AuthException extends GuiyuException {
    private static final long serialVersionUID = 1L;

    public AuthException(){

        super();
    }

    public AuthException(String msg){
        super(msg);
    }

    public AuthException(String errorCode, String msg){
        super(errorCode,msg);
    }

    public AuthException(Throwable throwable){
        super(throwable);
    }

    public AuthException(String msg, Throwable throwable){
        super(msg, throwable);
    }
}
