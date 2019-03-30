package com.guiji.dispatch.impl;

import com.guiji.dispatch.dao.ext.PlanBatchOptMapper;
import com.guiji.dispatch.dto.OptPlanDto;
import com.guiji.dispatch.enums.PlanOperTypeEnum;
import com.guiji.dispatch.service.IPlanBatchService;
import com.guiji.dispatch.util.DaoHandler;
import com.guiji.dispatch.util.DateTimeUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PlanBatchServiceImpl implements IPlanBatchService {

    private Logger logger = LoggerFactory.getLogger(PlanBatchServiceImpl.class);

    @Autowired
    private PlanBatchOptMapper planBatchMapper;

    @Override
    public boolean delPlanBatch(OptPlanDto optPlanDto) {
        boolean bool = false;
        if(null != optPlanDto){
            if(!StringUtils.isEmpty(optPlanDto.getStartTime()) && !StringUtils.isEmpty(optPlanDto.getEndTime())) {
                optPlanDto.setStartTime(optPlanDto.getStartTime() + " " + DateTimeUtils.DEFAULT_DATE_START_TIME);
                optPlanDto.setEndTime(optPlanDto.getEndTime() + " " + DateTimeUtils.DEFAULT_DATE_END_TIME);
            }
        }

        //全选
        if(PlanOperTypeEnum.ALL.getType() == optPlanDto.getType()){
            if(null != optPlanDto){
                optPlanDto.setNocheckPlanUuid(null);
            }
            bool = DaoHandler.getMapperBoolRes(
                    planBatchMapper.delPlanBatchByParam(optPlanDto, new Date()));

        //只勾选
        }else if(PlanOperTypeEnum.CHECK.getType() == optPlanDto.getType()){
            if(null != optPlanDto
                    && null != optPlanDto.getCheckPlanUuid()
                    && optPlanDto.getCheckPlanUuid().size()>0){
                bool = DaoHandler.getMapperBoolRes(
                        planBatchMapper.delPlanBatchById(optPlanDto.getCheckPlanUuid(), new Date()));
            }

        //全选去勾
        }else if(PlanOperTypeEnum.NO_CHECK.getType() == optPlanDto.getType()){
            bool = DaoHandler.getMapperBoolRes(
                    planBatchMapper.delPlanBatchByParam(optPlanDto, new Date()));
        }
        return bool;
    }
}
