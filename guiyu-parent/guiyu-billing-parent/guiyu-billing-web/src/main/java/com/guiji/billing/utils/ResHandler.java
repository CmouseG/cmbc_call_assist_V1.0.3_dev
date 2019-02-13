package com.guiji.billing.utils;

import com.guiji.component.result.Result;
import com.guiji.user.dao.entity.SysUser;

public class ResHandler {

    public static <T> T getResObj(Result.ReturnData<T> res){
        if(null != res && res.success){
            return res.getBody();
        }else{
            return null;
        }
    }
}
