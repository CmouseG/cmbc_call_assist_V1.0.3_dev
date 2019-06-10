package com.guiji.ccmanager.service.impl;

import com.guiji.ccmanager.service.LineNameService;
import com.guiji.clm.api.LineMarketRemote;
import com.guiji.clm.model.SipLineVO;
import com.guiji.component.result.Result;
import com.guiji.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * author:liyang
 * Date:2019/3/14 16:15
 * Description:
 */
@Slf4j
@Service
public class LineNameServiceImpl implements LineNameService {


//    @Autowired
//    RedisUtil redisUtil;
    @Autowired
    LineMarketRemote lineMarketRemote;

    @Override
    public String getLineName(Integer lineId, Integer customerId) {

//        Object o = redisUtil.get("callCenter_ccmanger_lineName_"+lineId+"_"+customerId);
//        if(o!=null){
//            return (String) o;
//        }else{
            //请求线路市场获取线路名称
            try{
                Result.ReturnData<SipLineVO> result = lineMarketRemote.queryLineNameByLineId(String.valueOf(customerId),lineId);
//                log.info("queryUserSipLineByLineId返回:",result.getCode());
                if(result.success){
                    String lineName = result.getBody().getLineName();
//                    redisUtil.set("callCenter_ccmanger_lineName_"+lineId+"_"+customerId,lineName,86400);
                    return lineName;
                }
            }catch (Exception e){
                log.error("获取线路名称出现异常,",e);
                return "";
            }

//        }
        return "";
    }

}
