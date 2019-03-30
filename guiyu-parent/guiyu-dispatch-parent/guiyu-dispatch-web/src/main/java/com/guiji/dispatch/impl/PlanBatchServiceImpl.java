package com.guiji.dispatch.impl;

import com.guiji.dispatch.dao.ext.PlanBatchOptMapper;
import com.guiji.dispatch.dto.OptPlanDto;
import com.guiji.dispatch.enums.AuthLevelEnum;
import com.guiji.dispatch.enums.PlanOperTypeEnum;
import com.guiji.dispatch.service.GetAuthUtil;
import com.guiji.dispatch.service.IPlanBatchService;
import com.guiji.dispatch.util.DaoHandler;
import com.guiji.dispatch.util.DateTimeUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PlanBatchServiceImpl implements IPlanBatchService {

    private Logger logger = LoggerFactory.getLogger(PlanBatchServiceImpl.class);

    @Autowired
    private PlanBatchOptMapper planBatchMapper;

    @Autowired
    private GetAuthUtil getAuthUtil;

    @Override
    public boolean delPlanBatch(OptPlanDto optPlanDto) {
        boolean bool = false;
        List<Integer> orgIds = null;
        if(null != optPlanDto){
            //操作开始、结束时间
            if(!StringUtils.isEmpty(optPlanDto.getStartTime()) && !StringUtils.isEmpty(optPlanDto.getEndTime())) {
                optPlanDto.setStartTime(optPlanDto.getStartTime() + " " + DateTimeUtils.DEFAULT_DATE_START_TIME);
                optPlanDto.setEndTime(optPlanDto.getEndTime() + " " + DateTimeUtils.DEFAULT_DATE_END_TIME);
            }

            //如果不查batchId时，前端会传batchId=0
            if (optPlanDto.getBatchId() != null && optPlanDto.getBatchId() != 0) {
                optPlanDto.setBatchId(optPlanDto.getBatchId());
            }

            //获取权限
            Integer authLevel = optPlanDto.getAuthLevel();//操作用户权限等级
            String userId = getAuthUtil.getUserIdByAuthLevel(authLevel, optPlanDto.getOperUserId());//获取用户ID
            orgIds = (null != optPlanDto.getOrgIdList())?optPlanDto.getOrgIdList()
                    :getAuthUtil.getOrgIdsByAuthLevel(authLevel, optPlanDto.getOperOrgId());//获取组织ID
            optPlanDto.setOrgIdList(orgIds);
            if(AuthLevelEnum.USER.getLevel() == authLevel && !StringUtils.isEmpty(userId)){//本人
                optPlanDto.setUserId(userId);
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
                        planBatchMapper.delPlanBatchById(optPlanDto.getCheckPlanUuid(), orgIds, new Date()));
            }

        //全选去勾
        }else if(PlanOperTypeEnum.NO_CHECK.getType() == optPlanDto.getType()){
            bool = DaoHandler.getMapperBoolRes(
                    planBatchMapper.delPlanBatchByParam(optPlanDto, new Date()));
        }
        return bool;
    }


    @Override
    public boolean suspendPlanBatch(OptPlanDto optPlanDto) {
        boolean bool = false;
        List<Integer> orgIds = null;
        if(null != optPlanDto){
            //操作开始、结束时间
            if(!StringUtils.isEmpty(optPlanDto.getStartTime()) && !StringUtils.isEmpty(optPlanDto.getEndTime())) {
                optPlanDto.setStartTime(optPlanDto.getStartTime() + " " + DateTimeUtils.DEFAULT_DATE_START_TIME);
                optPlanDto.setEndTime(optPlanDto.getEndTime() + " " + DateTimeUtils.DEFAULT_DATE_END_TIME);
            }

            //如果不查batchId时，前端会传batchId=0
            if (optPlanDto.getBatchId() != null && optPlanDto.getBatchId() != 0) {
                optPlanDto.setBatchId(optPlanDto.getBatchId());
            }

            //获取权限
            Integer authLevel = optPlanDto.getAuthLevel();//操作用户权限等级
            String userId = getAuthUtil.getUserIdByAuthLevel(authLevel, optPlanDto.getOperUserId());//获取用户ID
            orgIds = (null != optPlanDto.getOrgIdList())?optPlanDto.getOrgIdList()
                    :getAuthUtil.getOrgIdsByAuthLevel(authLevel, optPlanDto.getOperOrgId());//获取组织ID
            optPlanDto.setOrgIdList(orgIds);
            if(AuthLevelEnum.USER.getLevel() == authLevel && !StringUtils.isEmpty(userId)){//本人
                optPlanDto.setUserId(userId);
            }
        }

        //全选
        if(PlanOperTypeEnum.ALL.getType() == optPlanDto.getType()){
            if(null != optPlanDto){
                optPlanDto.setNocheckPlanUuid(null);
            }
            bool = DaoHandler.getMapperBoolRes(
                    planBatchMapper.suspendPlanBatchByParam(optPlanDto, new Date()));

            //只勾选
        }else if(PlanOperTypeEnum.CHECK.getType() == optPlanDto.getType()){
            if(null != optPlanDto
                    && null != optPlanDto.getCheckPlanUuid()
                    && optPlanDto.getCheckPlanUuid().size()>0){
                bool = DaoHandler.getMapperBoolRes(
                        planBatchMapper.suspendPlanBatchById(optPlanDto.getCheckPlanUuid(), orgIds, new Date()));
            }

            //全选去勾
        }else if(PlanOperTypeEnum.NO_CHECK.getType() == optPlanDto.getType()){
            bool = DaoHandler.getMapperBoolRes(
                    planBatchMapper.suspendPlanBatchByParam(optPlanDto, new Date()));
        }
        return bool;
    }


    @Override
    public boolean stopPlanBatch(OptPlanDto optPlanDto) {
        boolean bool = false;
        List<Integer> orgIds = null;
        if(null != optPlanDto){
            //操作开始、结束时间
            if(!StringUtils.isEmpty(optPlanDto.getStartTime()) && !StringUtils.isEmpty(optPlanDto.getEndTime())) {
                optPlanDto.setStartTime(optPlanDto.getStartTime() + " " + DateTimeUtils.DEFAULT_DATE_START_TIME);
                optPlanDto.setEndTime(optPlanDto.getEndTime() + " " + DateTimeUtils.DEFAULT_DATE_END_TIME);
            }

            //如果不查batchId时，前端会传batchId=0
            if (optPlanDto.getBatchId() != null && optPlanDto.getBatchId() != 0) {
                optPlanDto.setBatchId(optPlanDto.getBatchId());
            }

            //获取权限
            Integer authLevel = optPlanDto.getAuthLevel();//操作用户权限等级
            String userId = getAuthUtil.getUserIdByAuthLevel(authLevel, optPlanDto.getOperUserId());//获取用户ID
            orgIds = (null != optPlanDto.getOrgIdList())?optPlanDto.getOrgIdList()
                    :getAuthUtil.getOrgIdsByAuthLevel(authLevel, optPlanDto.getOperOrgId());//获取组织ID
            optPlanDto.setOrgIdList(orgIds);
            if(AuthLevelEnum.USER.getLevel() == authLevel && !StringUtils.isEmpty(userId)){//本人
                optPlanDto.setUserId(userId);
            }
        }

        //全选
        if(PlanOperTypeEnum.ALL.getType() == optPlanDto.getType()){
            if(null != optPlanDto){
                optPlanDto.setNocheckPlanUuid(null);
            }
            bool = DaoHandler.getMapperBoolRes(
                    planBatchMapper.stopPlanBatchByParam(optPlanDto, new Date()));

        //只勾选
        }else if(PlanOperTypeEnum.CHECK.getType() == optPlanDto.getType()){
            if(null != optPlanDto
                    && null != optPlanDto.getCheckPlanUuid()
                    && optPlanDto.getCheckPlanUuid().size()>0){
                bool = DaoHandler.getMapperBoolRes(
                        planBatchMapper.stopPlanBatchById(optPlanDto.getCheckPlanUuid(), orgIds, new Date()));
            }

        //全选去勾
        }else if(PlanOperTypeEnum.NO_CHECK.getType() == optPlanDto.getType()){
            bool = DaoHandler.getMapperBoolRes(
                    planBatchMapper.stopPlanBatchByParam(optPlanDto, new Date()));
        }
        return bool;
    }
}
