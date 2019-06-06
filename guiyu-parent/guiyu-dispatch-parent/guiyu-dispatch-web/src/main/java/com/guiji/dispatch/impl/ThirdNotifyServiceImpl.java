package com.guiji.dispatch.impl;

import com.guiji.dispatch.bean.DispatchPlanBatchAddRes;
import com.guiji.dispatch.constant.RedisConstant;
import com.guiji.dispatch.dao.DispatchPlanBatchMapper;
import com.guiji.dispatch.dao.entity.DispatchPlan;
import com.guiji.dispatch.dao.entity.DispatchPlanBatch;
import com.guiji.dispatch.model.DispatchPlanBatchAddVo;
import com.guiji.dispatch.model.MqNotifyMessage;
import com.guiji.dispatch.model.PlanCallResultVo;
import com.guiji.dispatch.service.IDispatchPlanService;
import com.guiji.dispatch.service.ThirdApiNotifyService;
import com.guiji.guiyu.message.component.QueueSender;
import com.guiji.utils.JsonUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

/**
 * @Classname ThirdNotifyServiceImpl
 * @Description TODO
 * @Date 2019/5/23 11:27
 * @Created by qinghua
 */
@Service
public class ThirdNotifyServiceImpl implements ThirdApiNotifyService {

    private static final String NOTIFY_QUEUE = "thirdApi.notify";

    /** logger */
    private final static Logger LOGGER = LoggerFactory.getLogger(ThirdNotifyServiceImpl.class);

    @Autowired
    QueueSender mqSender;

    @Autowired
    DispatchPlanBatchMapper dispatchPlanBatchMapper;

    @Autowired
    IDispatchPlanService dispatchPlanService;

    /**
     * 单个拨打结果的通知
     * @param plan
     */
    @Override
    @Async("asyncThirdApiNotify")
    public void singleNotify(DispatchPlan plan) {


        DispatchPlanBatch dispatchPlanBatch = dispatchPlanBatchMapper.selectByPrimaryKey(plan.getBatchId());
        if(StringUtils.isNotEmpty(dispatchPlanBatch.getSingleCallbackUrl())) {
            PlanCallResultVo resultVo = new PlanCallResultVo();

            resultVo.setAttach(plan.getAttach());
            resultVo.setParams(plan.getParams().replace("|", "-"));
            resultVo.setBatchName(plan.getBatchName());
            resultVo.setCustName(plan.getCustName());
            resultVo.setCustCompany(plan.getCustCompany());
            resultVo.setPhone(plan.getPhone());
            resultVo.setResult(plan.getResult());

            MqNotifyMessage mqNotifyMessage = new MqNotifyMessage();

            mqNotifyMessage.setUserId(plan.getUserId());
            mqNotifyMessage.setBody(JsonUtils.bean2Json(resultVo));
            mqNotifyMessage.setNotifyUrl(dispatchPlanBatch.getSingleCallbackUrl());

            mqSender.send(NOTIFY_QUEUE, JsonUtils.bean2Json(mqNotifyMessage));

        }
    }

    /**
     * 批量添加结果的回调
     * @param dispatchPlan
     */
    @Override
    public void batchNotify(DispatchPlan dispatchPlan) {
        DispatchPlanBatch batch = dispatchPlanBatchMapper.selectByPrimaryKey(dispatchPlan.getBatchId());

        DispatchPlanBatchAddVo addVo = dispatchPlanService.getPlanResult(dispatchPlan);

        DispatchPlanBatchAddRes result = new DispatchPlanBatchAddRes();

        result.setAcceptCount(addVo.getAcceptCount());
        result.setBatchName(addVo.getBatchName());
        result.setFailCount(addVo.getFailCount());
        result.setSuccessCount(addVo.getSuccessCount());

        StringBuilder res = new StringBuilder();

        if(!CollectionUtils.isEmpty(addVo.getFailList())) {

            addVo.getFailList().forEach(obj -> {

                res.append(obj.getPhoneNo());
                res.append("^");
                res.append(org.apache.commons.lang.StringUtils.isEmpty(obj.getAttach()) ? "" : obj.getAttach());
                res.append("^");
                res.append(org.apache.commons.lang.StringUtils.isEmpty(obj.getParams()) ? "" : obj.getParams().replace("|", "-"));
                res.append("^");
                res.append(org.apache.commons.lang.StringUtils.isEmpty(obj.getCustName()) ? "" : obj.getCustName());
                res.append("^");
                res.append(org.apache.commons.lang.StringUtils.isEmpty(obj.getCustCompany()) ? "" : obj.getCustCompany());
                res.append("^");
                res.append(org.apache.commons.lang.StringUtils.isEmpty(obj.getResult()) ? "" : obj.getResult());
                res.append("|");

            });

        }

        result.setFailList(res.length() > 0 ? res.deleteCharAt(res.length() - 1).toString() : "");

        MqNotifyMessage mqNotifyMessage = new MqNotifyMessage();

        mqNotifyMessage.setNotifyUrl(batch.getCallbackUrl());
        mqNotifyMessage.setUserId(dispatchPlan.getUserId());
        mqNotifyMessage.setBody(JsonUtils.bean2Json(result));

        mqSender.send(NOTIFY_QUEUE, JsonUtils.bean2Json(mqNotifyMessage));
    }

}
