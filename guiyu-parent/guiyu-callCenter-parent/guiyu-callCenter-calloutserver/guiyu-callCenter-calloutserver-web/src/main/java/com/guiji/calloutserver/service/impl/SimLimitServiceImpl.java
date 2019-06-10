package com.guiji.calloutserver.service.impl;

import com.guiji.callcenter.dao.entity.LineSimlimitConfig;
import com.guiji.callcenter.daoNoSharing.LineSimlimitConfigMapper;
import com.guiji.calloutserver.enm.ESimLimitPeriod;
import com.guiji.calloutserver.manager.SimCallManager;
import com.guiji.calloutserver.service.SimLimitService;
import com.guiji.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * author:liyang
 * Date:2019/5/21 16:39
 * Description:
 */
@Service
@Slf4j
public class SimLimitServiceImpl implements SimLimitService {

    @Autowired
    RedisUtil redisUtil;
    @Autowired
    SimCallManager simCallManager;
    @Autowired
    LineSimlimitConfigMapper lineSimlimitConfigMapper;

    //拨打次数上限
    private static final String simLimitCallCount = "simLimitCallCount_";
    //接通次数上限
    private static final String simLimitConnectCount = "simLimitConnectCount_";
    //接通时长上限
    private static final String simLimitConnectTime = "simLimitConnectTime_";

    @Override
    @Async
    public void addSimCall(String callId, Integer lineId, Integer billSec) {
        try{
            if (simCallManager.isSimCall(callId)) {

                LineSimlimitConfig lineSimlimitConfig = lineSimlimitConfigMapper.selectByPrimaryKey(lineId);
                if (lineSimlimitConfig != null) {
                    //拨打次数
                    setSimCount(simLimitCallCount + lineId, lineSimlimitConfig.getCallCountPeriod(),1);

                    if (billSec != null && billSec > 0) {
                        //接通次数
                        setSimCount(simLimitConnectCount + lineId, lineSimlimitConfig.getConnectCountPeriod(),1);

                        //接通时长
                        setSimCount(simLimitConnectTime + lineId, lineSimlimitConfig.getConnectTimePeriod(),billSec);
                    }
                }
            }
        }catch (Exception e){
            log.error("addSimCall出现异常",e);
        }

    }

    private void setSimCount(String key, Integer period, Integer incr) {
        //查询缓存分钟数
        Integer simLimitCallPeriod = ESimLimitPeriod.getMinutesByValue(period);
        if (simLimitCallPeriod != null && simLimitCallPeriod != 0) {
            Object simLimitCallCountValue = redisUtil.get(key);
            if (simLimitCallCountValue != null) {
                long newValue = redisUtil.incr(key, incr);
                if (newValue == incr.intValue()) {//表示在进来的一瞬间，key过期了
                    redisUtil.expire(key, simLimitCallPeriod * 60);
                }
            } else {
                redisUtil.set(key, incr, simLimitCallPeriod * 60);
            }
        }
    }

    @Override
    public Boolean isAllowSimCall(Integer lineId) {
        try {
            LineSimlimitConfig lineSimlimitConfig = lineSimlimitConfigMapper.selectByPrimaryKey(lineId);
            if (lineSimlimitConfig != null) {

                //拨打次数
                Integer callCountTop = lineSimlimitConfig.getCallCountTop();
                if (callCountTop != null && callCountTop != 0) {
                    Object simLimitCallCountValue = redisUtil.get(simLimitCallCount + lineId);
                    if (simLimitCallCountValue != null && callCountTop <= (Integer) simLimitCallCountValue) { // 拨打次数超过限制
                        log.info("拨打次数超过限制,lineId[{}],top[{}],当前值[{}]", lineId, callCountTop, simLimitCallCountValue);
                        return false;
                    }
                }
                //接通次数
                Integer connectCountTop = lineSimlimitConfig.getConnectCountTop();
                if (connectCountTop != null && connectCountTop != 0) {
                    Object simLimitConnectCountValue = redisUtil.get(simLimitConnectCount + lineId);
                    if (simLimitConnectCountValue != null && connectCountTop <= (Integer) simLimitConnectCountValue) { // 接通次数超过限制
                        log.info("接通次数超过限制,lineId[{}],top[{}],当前值[{}]", lineId, connectCountTop, simLimitConnectCountValue);
                        return false;
                    }
                }
                //接通时长
                Integer connectTimeTop = lineSimlimitConfig.getConnectTimeTop()*60; //数据库里存的是分钟
                if (connectTimeTop != null && connectTimeTop != 0) {
                    Object simLimitConnectTimeValue = redisUtil.get(simLimitConnectTime + lineId);
                    if (simLimitConnectTimeValue != null && connectTimeTop <= (Integer) simLimitConnectTimeValue) { // 接通时长超过限制
                        log.info("接通时长超过限制,lineId[{}],top[{}],当前值[{}]", lineId, connectTimeTop, simLimitConnectTimeValue);
                        return false;
                    }
                }
            }

            return true;
        } catch (Exception e) {
            log.error("isAllowSimCall方法出现异常，lineId " + lineId, e);
            return true;
        }

    }

    @Override
    public void changeSimLineConfig(Integer lineId, Boolean callCountPeriodChange, Boolean connectCountPeriodChange, Boolean connectTimePeriodChange) {

        if(callCountPeriodChange){
            redisUtil.del(simLimitCallCount + lineId);
        }
        if(connectCountPeriodChange){
            redisUtil.del(simLimitConnectCount + lineId);
        }
        if(connectTimePeriodChange){
            redisUtil.del(simLimitConnectTime + lineId);
        }

    }
}
