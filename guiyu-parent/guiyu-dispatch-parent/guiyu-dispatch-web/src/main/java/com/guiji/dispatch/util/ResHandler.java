package com.guiji.dispatch.util;

import com.guiji.botsentence.api.entity.ServerResult;
import com.guiji.component.result.Result;

public class ResHandler {

    public static <T> T getResObj(Result.ReturnData<T> res){
        if(null != res && res.success){
            return res.getBody();
        }else{
            return null;
        }
    }

    public static <T> T getBotsentenceResObj(ServerResult<T> res){
        if(null != res && null != res.getData()){
            return res.getData();
        }else{
            return null;
        }
    }
}
