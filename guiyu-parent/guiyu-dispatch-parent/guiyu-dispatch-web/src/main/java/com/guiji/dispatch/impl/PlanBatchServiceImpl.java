package com.guiji.dispatch.impl;

import com.guiji.common.exception.GuiyuException;
import com.guiji.component.result.Result;
import com.guiji.dispatch.batchimport.IBatchImportQueueHandlerService;
import com.guiji.dispatch.dao.DispatchPlanBatchMapper;
import com.guiji.dispatch.dao.entity.DispatchPlan;
import com.guiji.dispatch.dao.entity.DispatchPlanBatch;
import com.guiji.dispatch.dao.ext.PlanBatchOptMapper;
import com.guiji.dispatch.dto.JoinPlanDto;
import com.guiji.dispatch.dto.OptPlanDto;
import com.guiji.dispatch.enums.*;
import com.guiji.dispatch.service.GetApiService;
import com.guiji.dispatch.service.GetAuthUtil;
import com.guiji.dispatch.service.IPlanBatchService;
import com.guiji.dispatch.util.Constant;
import com.guiji.dispatch.util.DaoHandler;
import com.guiji.dispatch.util.DateTimeUtils;
import com.guiji.user.dao.entity.SysUser;
import com.guiji.utils.IdGenUtil;
import com.guiji.utils.IdGengerator.SnowflakeIdWorker;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
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

    @Autowired
    private GetApiService getApiService;

    @Autowired
    private IBatchImportQueueHandlerService batchImportQueueHandler;

    @Autowired
    private DispatchPlanBatchMapper dispatchPlanBatchMapper;

    /**
     * 删除计划任务
     * @param optPlanDto
     * @return
     */
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
            optPlanDto.setUserId(getAuthUtil.getUserIdByAuthLevel(authLevel, optPlanDto.getUserId()));//获取用户ID,如果不是本人权限，则为null
            optPlanDto.setOrgIdList((null != optPlanDto.getOrgIdList())?optPlanDto.getOrgIdList()
                    :getAuthUtil.getOrgIdsByAuthLevel(authLevel, optPlanDto.getOperOrgId()));//获取组织ID
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

    /**
     * 暂停计划任务
     * @param optPlanDto
     * @return
     */
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
            optPlanDto.setUserId(getAuthUtil.getUserIdByAuthLevel(authLevel, optPlanDto.getUserId()));//获取用户ID,如果不是本人权限，则为null
            optPlanDto.setOrgIdList((null != optPlanDto.getOrgIdList())?optPlanDto.getOrgIdList()
                    :getAuthUtil.getOrgIdsByAuthLevel(authLevel, optPlanDto.getOperOrgId()));//获取组织ID
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

    /**
     * 停止计划任务
     * @param optPlanDto
     * @return
     */
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
            optPlanDto.setUserId(getAuthUtil.getUserIdByAuthLevel(authLevel, optPlanDto.getUserId()));//获取用户ID,如果不是本人权限，则为null
            optPlanDto.setOrgIdList((null != optPlanDto.getOrgIdList())?optPlanDto.getOrgIdList()
                    :getAuthUtil.getOrgIdsByAuthLevel(authLevel, optPlanDto.getOperOrgId()));//获取组织ID
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

    /**
     * 恢复计划任务
     * @param optPlanDto
     * @return
     */
    @Override
    public boolean recoveryPlanBatch(OptPlanDto optPlanDto) {
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
            optPlanDto.setUserId(getAuthUtil.getUserIdByAuthLevel(authLevel, optPlanDto.getUserId()));//获取用户ID,如果不是本人权限，则为null
            optPlanDto.setOrgIdList((null != optPlanDto.getOrgIdList())?optPlanDto.getOrgIdList()
                    :getAuthUtil.getOrgIdsByAuthLevel(authLevel, optPlanDto.getOperOrgId()));//获取组织ID
        }

        //全选
        if(PlanOperTypeEnum.ALL.getType() == optPlanDto.getType()){
            if(null != optPlanDto){
                optPlanDto.setNocheckPlanUuid(null);
            }
            bool = DaoHandler.getMapperBoolRes(
                    planBatchMapper.recoveryPlanBatchByParam(optPlanDto, new Date()));

            //只勾选
        }else if(PlanOperTypeEnum.CHECK.getType() == optPlanDto.getType()){
            if(null != optPlanDto
                    && null != optPlanDto.getCheckPlanUuid()
                    && optPlanDto.getCheckPlanUuid().size()>0){
                bool = DaoHandler.getMapperBoolRes(
                        planBatchMapper.recoveryPlanBatchById(optPlanDto.getCheckPlanUuid(), orgIds, new Date()));
            }

            //全选去勾
        }else if(PlanOperTypeEnum.NO_CHECK.getType() == optPlanDto.getType()){
            bool = DaoHandler.getMapperBoolRes(
                    planBatchMapper.recoveryPlanBatchByParam(optPlanDto, new Date()));
        }
        return bool;
    }

    @Override
    public boolean joinPlanBatch(JoinPlanDto joinPlanDto) {
        boolean bool = false;
        if(null != joinPlanDto){
            Long operUserId = Long.valueOf(joinPlanDto.getOperUserId());    //操作用户ID
            Integer operOrgId = joinPlanDto.getOperOrgId();     //企业组织ID
            String operOrgCode = joinPlanDto.getOperOrgCode();  //企业编码
            OptPlanDto optPlanDto = joinPlanDto.getOptPlan();
            DispatchPlan submitPlan = joinPlanDto.getDispatchPlan();

            // 查询用户名称
            SysUser sysUser = getApiService.getUserById(operUserId+"");
            if (null == sysUser) {
                throw new GuiyuException("用户不存在");
            }

            //线路入库
            DispatchPlanBatch batchPlan = new DispatchPlanBatch();
            batchPlan.setUserId(operUserId.intValue());
            batchPlan.setOrgCode(operOrgCode);
            batchPlan.setName(submitPlan.getBatchName());
            batchPlan.setBatchName(submitPlan.getBatchName());
            batchPlan.setStatusShow(Constant.BATCH_STATUS_SHOW);
            batchPlan.setStatusNotify(SyncStatusEnum.NO_SYNC.getStatus());
            batchPlan.setGmtCreate(new Date());
            batchPlan.setGmtModified(new Date());
            dispatchPlanBatchMapper.insert(batchPlan);

            //批量加入MQ
            this.batchJoin(joinPlanDto, batchPlan.getId());
            bool = true;
        }
        return bool;
    }

    @Async("asyncBatchImportExecutor")
    protected void batchJoin(JoinPlanDto joinPlanDto, Integer batchId){
        Long operUserId = Long.valueOf(joinPlanDto.getOperUserId());    //操作用户ID
        Integer oper0rgId = joinPlanDto.getOperOrgId();     //企业组织ID
        String operOrgCode = joinPlanDto.getOperOrgCode();  //企业编码
        OptPlanDto optPlanDto = joinPlanDto.getOptPlan();
        DispatchPlan submitPlan = joinPlanDto.getDispatchPlan();
        //获取权限
        Integer authLevel = optPlanDto.getAuthLevel();//操作用户权限等级
        optPlanDto.setUserId(getAuthUtil.getUserIdByAuthLevel(authLevel, optPlanDto.getUserId()));//获取用户ID,如果不是本人权限，则为null
        optPlanDto.setOrgIdList((null != optPlanDto.getOrgIdList())?optPlanDto.getOrgIdList()
                :getAuthUtil.getOrgIdsByAuthLevel(authLevel, optPlanDto.getOperOrgId()));//获取组织ID
        int limit = 30000;
        //查询条件列表（注意，号码去重）
        List<String> phoneList = planBatchMapper.getDisPhone(optPlanDto, limit);
        for(String phone : phoneList){
            this.pushPlanCreateMQ(submitPlan, batchId, phone, operUserId, oper0rgId, operOrgCode);
        }
    }

    /**
     * 推送MQ
     * @param submitPlan
     * @param batchId
     * @param phone
     * @param userId
     * @param orgId
     * @param orgCode
     */
    private void pushPlanCreateMQ(DispatchPlan submitPlan, Integer batchId, String phone, Long userId, Integer orgId, String orgCode){
        try {
            DispatchPlan newPlan = new DispatchPlan();
            newPlan.setBatchId(batchId);
            newPlan.setParams(submitPlan.getParams());
            newPlan.setAttach(submitPlan.getAttach());
            newPlan.setPhone(phone);

            newPlan.setBatchId(batchId);
            newPlan.setUserId(userId.intValue());
            newPlan.setOrgCode(orgCode);
            newPlan.setOrgId(orgId);

            batchImportQueueHandler.add(newPlan);
        }catch(Exception e){
            logger.error("批量加入计划，单条加入MQ异常", e);
        }
    }

}
