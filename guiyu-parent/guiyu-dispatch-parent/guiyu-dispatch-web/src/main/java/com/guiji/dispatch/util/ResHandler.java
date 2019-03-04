package com.guiji.dispatch.util;

import com.guiji.component.result.Result;

public class ResHandler {

    public static <T> T getResObj(Result.ReturnData<T> res){
        if(null != res && res.success){
            return res.getBody();
        }else{
            return null;
        }
    }
}
