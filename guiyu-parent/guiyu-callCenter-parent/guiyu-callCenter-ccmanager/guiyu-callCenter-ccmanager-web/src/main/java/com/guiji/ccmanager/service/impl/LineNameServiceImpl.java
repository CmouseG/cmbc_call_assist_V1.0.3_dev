package com.guiji.ccmanager.service.impl;

import com.guiji.ccmanager.service.LineNameService;
import com.guiji.clm.api.LineMarketRemote;
import com.guiji.clm.model.SipLineVO;
import com.guiji.component.result.Result;
import com.guiji.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * author:liyang
 * Date:2019/3/14 16:15
 * Description:
 */
@Service
public class LineNameServiceImpl implements LineNameService {


    @Autowired
    RedisUtil redisUtil;
    @Autowired
    LineMarketRemote lineMarketRemote;

    @Override
    public String getLineName(Integer lineId, Integer customerId) {

        Object o = redisUtil.get("ccmanger_lineName_"+lineId+"_"+customerId);
        if(o!=null){
            return (String) o;
        }else{
            //请求线路市场获取线路名称
            Result.ReturnData<SipLineVO> result = lineMarketRemote.queryUserSipLineByLineId(String.valueOf(customerId),lineId);
            String lineName = result.getBody().getLineName();
            redisUtil.set("ccmanger_lineName_"+lineId+"_"+customerId,lineName);
            return lineName;
        }

    }

}
