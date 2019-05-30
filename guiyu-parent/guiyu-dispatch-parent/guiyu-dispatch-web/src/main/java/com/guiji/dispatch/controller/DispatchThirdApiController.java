package com.guiji.dispatch.controller;

import com.google.common.collect.Lists;
import com.guiji.common.exception.GuiyuException;
import com.guiji.component.result.Result;
import com.guiji.dispatch.api.DispatchThirdApi;
import com.guiji.dispatch.bean.AddPlanAsyncEntity;
import com.guiji.dispatch.constant.RedisConstant;
import com.guiji.dispatch.dao.entity.DispatchBatchLine;
import com.guiji.dispatch.dao.entity.DispatchPlanBatch;
import com.guiji.dispatch.enums.PlanStatusEnum;
import com.guiji.dispatch.enums.SysDefaultExceptionEnum;
import com.guiji.dispatch.exception.BaseException;
import com.guiji.dispatch.exception.DispatchCodeExceptionEnum;
import com.guiji.dispatch.line.IDispatchBatchLineService;
import com.guiji.dispatch.model.*;
import com.guiji.dispatch.service.CallAgentService;
import com.guiji.dispatch.service.GetApiService;
import com.guiji.dispatch.service.IDispatchPlanBatchService;
import com.guiji.dispatch.service.IDispatchPlanService;
import com.guiji.dispatch.vo.DispatchPlanVo;
import com.guiji.robot.api.CustAiAccountRemote;
import com.guiji.robot.model.CustTemplateVo;
import com.guiji.user.dao.entity.SysOrganization;
import com.guiji.user.dao.entity.SysUser;
import com.guiji.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Classname DispatchThirdApiController
 * @Description dispatch 三方api接口
 * @Date 2019/5/21 16:06
 * @Created by qinghua
 */
@RestController
public class DispatchThirdApiController implements DispatchThirdApi {

    @Autowired
    IDispatchBatchLineService dispatchBatchLineService;

    @Autowired
    CustAiAccountRemote aiAccountRemote;

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    CallAgentService callAgentService;

    private static final int STOP = 4;
    private static final int RECOVER = 1;
    private static final int SUSPEND = 3;

    /**
     * 添加计划任务
     * @param ro
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result.ReturnData<Boolean> addBatchPlan(@RequestBody DispatchPlanForApiRo ro) {

        //校验线路
        if(CollectionUtils.isEmpty(ro.getLineIds())
                || null == ro.getUserId()
                || StringUtils.isEmpty(ro.getBatchName())
                || null == ro.getCallData()
                || StringUtils.isEmpty(ro.getCallHour())
                || (null == ro.getClean() || (ro.getClean() != 0 && ro.getClean() != 1))
                || StringUtils.isEmpty(ro.getRobot())
                || CollectionUtils.isEmpty(ro.getPhoneRoList())
                || StringUtils.isEmpty(ro.getBatchCallBackUrl())
                || StringUtils.isEmpty(ro.getSingleCallBackUrl())
        ) {
            throw new BaseException(DispatchCodeExceptionEnum.IN_DATA_EXCEPTION.getErrorMsg(), DispatchCodeExceptionEnum.IN_DATA_EXCEPTION.getErrorCode());
        }

        //获取用户信息
        SysUser sysUser = checkUser(ro.getUserId());

        SysOrganization orgInfo = getOrgInfo(ro.getUserId());

        DispatchPlanBatch dispatchPlanBatch = initBatch(ro);

        Integer lineType = checkLineType(ro, dispatchPlanBatch.getId());

        CustTemplateVo custTemplateVo = checkTemplate(ro.getUserId(), ro.getRobot());

        checkCallAgent(custTemplateVo.getAgentFlag(), ro, sysUser.getOrgCode());

        int num = ro.getPhoneRoList().size();

        setBatchRedisNum(Long.valueOf(num), dispatchPlanBatch.getId());

        AddPlanAsyncEntity entity = new AddPlanAsyncEntity();

        entity.setBatch(dispatchPlanBatch);
        entity.setRo(ro);
        entity.setSysUser(sysUser);
        entity.setOrgInfo(orgInfo);
        entity.setCustTemplateVo(custTemplateVo);
        entity.setLineType(lineType);


        dispatchPlanService.addPlanAsync(entity);

        return Result.ok();
    }

    /**
     * 校验坐席组是否合理
     * @param agentFlag
     * @param ro
     */
    private void checkCallAgent(Boolean agentFlag, DispatchPlanForApiRo ro, String orgCode) {

        if(!agentFlag) {
            ro.setCallAgent(null);
        } else {
            if(StringUtils.isEmpty(ro.getCallAgent())) {
                throw new BaseException(DispatchCodeExceptionEnum.THIS_TEMPLATE_HAS_CALL_AGENT.getErrorMsg(), DispatchCodeExceptionEnum.THIS_TEMPLATE_HAS_CALL_AGENT.getErrorCode());
            } else {
                List<String> agent = callAgentService.getAgent(orgCode);
                if(!agent.contains(ro.getCallAgent())) {
                    throw new BaseException(DispatchCodeExceptionEnum.IN_DATA_EXCEPTION.getErrorMsg(), DispatchCodeExceptionEnum.IN_DATA_EXCEPTION.getErrorCode());
                }
            }
        }
    }

    /**
     * 校验并添加线路信息
     * @param ro
     * @param batchId
     * @return
     */
    private Integer checkLineType(DispatchPlanForApiRo ro, Integer batchId) {

        Integer lineType = 1;

        Set<Integer> lineTypeSet = new HashSet<>();

        //添加线路
        for(Integer lineId : ro.getLineIds()) {
            DispatchBatchLine dispatchBatchLine = dispatchBatchLineService.addByLineId(batchId, ro.getUserId(), lineId);

            lineType = dispatchBatchLine.getLineType();

            lineTypeSet.add(dispatchBatchLine.getLineType());
        }

        if(lineTypeSet.size() > 1) {
            throw new BaseException(DispatchCodeExceptionEnum.BATCH_SAME_LINE_TYPE.getErrorMsg(), DispatchCodeExceptionEnum.BATCH_SAME_LINE_TYPE.getErrorCode());
        }

        return lineType;

    }

    /**
     * 初始化批次信息
     * @param ro
     * @return
     */
    DispatchPlanBatch initBatch(DispatchPlanForApiRo ro) {
        DispatchPlanBatch batch = new DispatchPlanBatch();

        batch.setName(ro.getBatchName());
        batch.setUserId(ro.getUserId());
        batch.setCallbackUrl(ro.getBatchCallBackUrl());
        batch.setTotalNum(ro.getPhoneRoList().size());
        batch.setSingleCallbackUrl(ro.getSingleCallBackUrl());

        dispatchPlanBatchService.addDispatchPlanBatch(batch);

        return batch;
    }

    /**
     * 记录该批次的总计数
     * 每处理一条，计数减1，当变为0时，会触发批次信息通知
     * @param num
     * @param batchId
     */
    private void setBatchRedisNum(Long num, Integer batchId) {

        String key = RedisConstant.RedisConstantKey.DISPATCH_ADD_PLAN_COUNT_PRE +batchId;

        redisUtil.set(key, num, 3*24*3600L);

    }

    /**
     * 校验并查询话术信息
     * @param userId
     * @param robot
     * @return
     */
    private CustTemplateVo checkTemplate(Integer userId, String robot) {

        Result.ReturnData<CustTemplateVo> custTemplateVoReturnData = aiAccountRemote.queryUserTemplateInfo(userId, robot);

        if(custTemplateVoReturnData == null || !custTemplateVoReturnData.success || custTemplateVoReturnData.getBody() == null) {

            throw new BaseException(DispatchCodeExceptionEnum.USER_NO_THIS_TEMPLATE.getErrorMsg(), DispatchCodeExceptionEnum.USER_NO_THIS_TEMPLATE.getErrorCode());

        }

        return custTemplateVoReturnData.getBody();

    }

    @Autowired
    IDispatchPlanService dispatchPlanService;

    @Autowired
    IDispatchPlanBatchService dispatchPlanBatchService;

    @Autowired
    GetApiService userService;

    /**
     * 校验用户信息是否存在
     * @param userId
     * @return
     */
    private SysUser checkUser(Integer userId) {

        // 查询用户名称
        SysUser user = userService.getUserById(userId.toString());
        if (user == null) {
            throw new BaseException(DispatchCodeExceptionEnum.NO_THIS_USER.getErrorMsg(), DispatchCodeExceptionEnum.NO_THIS_USER.getErrorCode());
        }

        return user;
    }

    /**
     * 获取用户组织信息
     * @param userId
     * @return
     */
    private SysOrganization getOrgInfo(Integer userId) {

        return userService.getOrgByUserId(userId.toString());

    }

    /**
     * 三方批量暂停
     *
     * @param ro
     * @return
     */
    @Override
    public Result.ReturnData<Boolean> suspendPlanThirdBatch(@RequestBody OperDispatchRo ro) {

        operPlanStatus(ro, SUSPEND);

        return Result.ok(true);
    }

    /**
     * 批量停止
     *
     * @param ro
     * @return
     */
    @Override
    public Result.ReturnData<Boolean> stopPlanThirdBatch(@RequestBody OperDispatchRo ro) {

        operPlanStatus(ro, STOP);

        return Result.ok(true);

    }

    /**
     * 三方批量恢复
     *
     * @param ro
     * @return
     */
    @Override
    public Result.ReturnData<Boolean> recoveryPlanThirdBatch(@RequestBody OperDispatchRo ro) {
        operPlanStatus(ro, RECOVER);

        return Result.ok(true);
    }

    /**
     * 对计划任务进行操作
     * @param ro
     * @param action
     */
    private void operPlanStatus(OperDispatchRo ro, Integer action) {

        //校验参数
        if (null == ro || StringUtils.isEmpty(ro.getBatchName())) {
            throw new GuiyuException(SysDefaultExceptionEnum.NULL_PARAM_EXCEPTION.getErrorMsg());
        }

        //查询批次是否存在
        DispatchPlanBatch dispatchPlanBatch = dispatchPlanBatchService.queryPlanBatchByName(ro.getBatchName(), ro.getUserId());

        //查询用户信息
        Integer orgId = userService.getOrgIdByUser(ro.getUserId().toString());

        PlanStatusEnum planStatusEnum = null;

        switch (action) {
            case STOP:
               planStatusEnum = PlanStatusEnum.STOP;
               break;
            case SUSPEND:
                planStatusEnum = PlanStatusEnum.SUSPEND;
                break;
            case RECOVER:
                planStatusEnum = PlanStatusEnum.DOING;
                break;
        }

        dispatchPlanBatchService.updatePlanBatchStatus(dispatchPlanBatch.getId(), Lists.newArrayList(orgId), planStatusEnum);
    }

    /**
     * 获取拨打任务详情
     * @param ro
     * @return
     */
    @Override
    public Result.ReturnData<DispatchPlanBatchAddVo> getPlanThirdBatchDetail(@RequestBody OperDispatchRo ro) {

        //校验参数
        if (null == ro || StringUtils.isEmpty(ro.getBatchName()) || null == ro.getUserId()) {
            throw new GuiyuException(SysDefaultExceptionEnum.NULL_PARAM_EXCEPTION.getErrorMsg());
        }

        DispatchPlanBatch batch = dispatchPlanBatchService.queryPlanBatchByName(ro.getBatchName(), ro.getUserId());

        DispatchPlanVo dispatchPlanVo = new DispatchPlanVo();

        dispatchPlanVo.setUserId(ro.getUserId());
        dispatchPlanVo.setBatchName(ro.getBatchName());
        dispatchPlanVo.setBatchId(batch.getId());

        DispatchPlanBatchAddVo planResult = dispatchPlanService.getPlanResult(dispatchPlanVo);

        return Result.ok(planResult);

    }

    /**
     * 获取批次拨打情况
     * @param ro
     * @return
     */
    @Override
    public Result.ReturnData<IPlanThirdBatchDialVo> getPlanThirdBatchDial(@RequestBody OperDispatchRo ro) {

        //校验参数
        if (null == ro || StringUtils.isEmpty(ro.getBatchName()) || null == ro.getUserId()) {
            throw new GuiyuException(SysDefaultExceptionEnum.NULL_PARAM_EXCEPTION.getErrorMsg());
        }

        IPlanThirdBatchDialVo planThirdBatchVo = dispatchPlanBatchService.getPlanThirdBatchDial(ro.getBatchName(), ro.getUserId());
        return new Result.ReturnData<>(planThirdBatchVo);
    }

    /**
     * 获取号码列表
     * @param ro
     * @return
     */
    @Override
    public Result.ReturnData<IPlanThirdBatchPhoneVo> queryPlanThirdBatchPage(@RequestBody QueryPlanThirdRo ro) {

        //校验参数
        if (null == ro || StringUtils.isEmpty(ro.getBatchName()) || null == ro.getUserId()) {
            throw new GuiyuException(SysDefaultExceptionEnum.NULL_PARAM_EXCEPTION.getErrorMsg());
        }

        IPlanThirdBatchPhoneVo planThirdBatchVo = dispatchPlanBatchService.queryPlanThirdBatchPage(ro);
        return new Result.ReturnData<>(planThirdBatchVo);
    }
}
