package com.guiji.ccmanager.service;

import com.guiji.common.result.Result;

/**
 * @Auther: 黎阳
 * @Date: 2018/10/30 0030 13:45
 * @Description:
 */
public interface CallManagerOutService {

    public Result.ReturnData<Object> startcallplan(String customerId, String tempId, String lineId);
}
