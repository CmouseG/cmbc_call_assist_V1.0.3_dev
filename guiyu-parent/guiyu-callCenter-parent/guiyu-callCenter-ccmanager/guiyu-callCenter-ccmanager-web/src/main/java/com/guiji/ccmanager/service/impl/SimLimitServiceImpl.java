package com.guiji.ccmanager.service.impl;

import com.guiji.callcenter.dao.entity.LineSimlimitConfig;
import com.guiji.callcenter.daoNoSharing.LineSimlimitConfigMapper;
import com.guiji.calloutserver.api.ISimLimitChange;
import com.guiji.ccmanager.vo.SimLimitConfigReq;
import com.guiji.ccmanager.service.SimLimitService;
import com.guiji.component.result.Result;
import com.guiji.utils.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * author:liyang
 * Date:2019/5/30 10:01
 * Description:
 */
@Service
@Slf4j
public class SimLimitServiceImpl implements SimLimitService {


    @Autowired
    LineSimlimitConfigMapper lineSimlimitConfigMapper;
    @Autowired
    ISimLimitChange iSimLimitChange;

    @Override
    public LineSimlimitConfig getSimLimitConfigInfo(Integer lineId) {

        LineSimlimitConfig lineSimlimitConfig = lineSimlimitConfigMapper.selectByPrimaryKey(lineId);
        if(lineSimlimitConfig!=null){
            return lineSimlimitConfig;
        }else{

            // 0-10分钟、1-20分钟、2-30分钟、3-1小时、4-1天、5-1个月

            LineSimlimitConfig addLineSimlimitConfig = new LineSimlimitConfig();
            addLineSimlimitConfig.setLineId(lineId);
            addLineSimlimitConfig.setCreateTime(new Date());
            //拨打次数  10分钟
            addLineSimlimitConfig.setCallCountTop(0);
            addLineSimlimitConfig.setCallCountPeriod(0);
            //接通次数  1小时
            addLineSimlimitConfig.setConnectCountTop(0);
            addLineSimlimitConfig.setConnectCountPeriod(3);
            //接通分钟  1小时
            addLineSimlimitConfig.setConnectTimeTop(0);
            addLineSimlimitConfig.setConnectTimePeriod(3);

            lineSimlimitConfigMapper.insertSelective(addLineSimlimitConfig);

            return addLineSimlimitConfig;
        }
    }

    @Override
    public boolean updateSimLimitConfigInfo(SimLimitConfigReq simLimitConfigReq) {

        LineSimlimitConfig lineSimlimitConfigNew = new LineSimlimitConfig();
        BeanUtil.copyProperties(simLimitConfigReq, lineSimlimitConfigNew);
        lineSimlimitConfigNew.setUpdateTime(new Date());

        //先查询
        LineSimlimitConfig lineSimlimitConfigOld = lineSimlimitConfigMapper.selectByPrimaryKey(simLimitConfigReq.getLineId());
        log.info("lineSimlimitConfigOld[{}]",lineSimlimitConfigOld);
        if(lineSimlimitConfigOld!=null){
            boolean callCountPeriodChange = false;
            if(lineSimlimitConfigOld.getCallCountPeriod().intValue() != lineSimlimitConfigNew.getCallCountPeriod().intValue()){
                callCountPeriodChange = true;
            }
            boolean connectCountPeriodChange = false;
            if(lineSimlimitConfigOld.getConnectCountPeriod().intValue() != lineSimlimitConfigNew.getConnectCountPeriod().intValue()){
                connectCountPeriodChange = true;
            }
            boolean connectTimePeriodChange = false;
            if(lineSimlimitConfigOld.getConnectTimePeriod().intValue() != lineSimlimitConfigNew.getConnectTimePeriod().intValue()){
                connectTimePeriodChange = true;
            }
            try{
                Result.ReturnData result =  iSimLimitChange.changeSimLineConfig(simLimitConfigReq.getLineId(),callCountPeriodChange,
                        connectCountPeriodChange,connectTimePeriodChange);
                if(result!=null && result.success){
                    log.info("调用接口changeSimLineConfig成功,lineId[{}],callCountPeriodChange[{}],connectCountPeriodChange[{}],connectTimePeriodChange[{}]",
                            simLimitConfigReq.getLineId(),callCountPeriodChange,connectCountPeriodChange,connectTimePeriodChange);
                    lineSimlimitConfigMapper.updateByPrimaryKeySelective(lineSimlimitConfigNew);
                    return true;
                }else{
                    return false;
                }
            }catch (Exception e){
                log.error("调用changeSimLineConfig出现异常",e);
                return  false;
            }

        }else{
            log.info("lineSimlimit不存在,直接插入,simLimitConfigReq[{}]",simLimitConfigReq);
            lineSimlimitConfigNew.setCreateTime(new Date());
            lineSimlimitConfigMapper.insertSelective(lineSimlimitConfigNew);
        }
            return true;


    }
}
