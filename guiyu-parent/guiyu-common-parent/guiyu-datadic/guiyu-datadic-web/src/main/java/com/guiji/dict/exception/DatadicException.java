package com.guiji.dict.exception;

import com.guiji.common.exception.GuiyuException;

/**
 * Created by ty on 2018/12/4.
 */
public class DatadicException extends GuiyuException {
    private static final long serialVersionUID = 1L;

    public DatadicException(){

        super();
    }

    public DatadicException(String msg){
        super(msg);
    }

    public DatadicException(String errorCode, String msg){
        super(errorCode,msg);
    }

    public DatadicException(Throwable throwable){
        super(throwable);
    }

    public DatadicException(String msg, Throwable throwable){
        super(msg, throwable);
    }
}
