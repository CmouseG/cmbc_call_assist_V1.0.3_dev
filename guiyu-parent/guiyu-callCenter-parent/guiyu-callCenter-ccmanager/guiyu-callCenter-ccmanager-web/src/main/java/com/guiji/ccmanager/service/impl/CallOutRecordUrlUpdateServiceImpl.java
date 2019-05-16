package com.guiji.ccmanager.service.impl;

import com.guiji.callcenter.dao.CallOutPlanMapper;
import com.guiji.callcenter.dao.UpdateRecordUrlMapper;
import com.guiji.callcenter.dao.entity.UpdateRecordUrl;
import com.guiji.callcenter.dao.entity.UpdateRecordUrlExample;
import com.guiji.callcenter.dao.entityext.CallIdRecordUrl;
import com.guiji.ccmanager.service.AuthService;
import com.guiji.ccmanager.service.CallOutRecordUrlUpdateService;
import com.guiji.guiyu.message.component.QueueSender;
import com.guiji.nas.vo.AliyunReqVO;
import com.guiji.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

/**
 * author:liyang
 * Date:2019/4/22 17:53
 * Description:
 */
@Service
@Slf4j
public class CallOutRecordUrlUpdateServiceImpl implements CallOutRecordUrlUpdateService {

    @Autowired
    CallOutPlanMapper callOutPlanMapper;
    @Autowired
    UpdateRecordUrlMapper updateRecordUrlMapper;
    @Autowired
    QueueSender queueSender;
    @Autowired
    AuthService authService;

    @Override
    public void updateCallOutRecordUrl() {

        log.info("开始迁移老的calloutrecord全程录音地址");
        BigInteger minId = null;

        //查询上次结束时的minId
        UpdateRecordUrlExample queryExampleStart = new UpdateRecordUrlExample();
        queryExampleStart.createCriteria().andTypeEqualTo(0);// 0 表示call_out_plan的记录
        List<UpdateRecordUrl> updateRecordUrlStartList = updateRecordUrlMapper.selectByExample(queryExampleStart);
        if (updateRecordUrlStartList != null && updateRecordUrlStartList.size() > 0) {
            minId = updateRecordUrlStartList.get(0).getId();
        }

        List<Integer> orgIdList = authService.getAllOrgIds();
        log.info("循环已经开始，通话记录，不断查询老的recordUrl推送到mq, minId[{}]", minId);
        //3000一个批次进行操作
        List<CallIdRecordUrl> list = callOutPlanMapper.selectCallIdRecordUrl(minId,orgIdList);
        if (list != null) {
            int size = list.size();
            if (size > 0) {

                for (CallIdRecordUrl callIdRecordUrl : list) {
                    BigInteger callId = callIdRecordUrl.getCallId();
                    String recordUrl = callIdRecordUrl.getRecordUrl();

                    //扔到mq里面
                    AliyunReqVO aliyunReqVO = new AliyunReqVO();
                    aliyunReqVO.setBusiId(callId.toString());
                    aliyunReqVO.setSourceUrl(recordUrl);
                    log.debug("callOutPlan推送到阿里云更新recordUrl队列 [{}]", aliyunReqVO);
                    queueSender.send("aliyunUploadQueue", JsonUtils.bean2Json(aliyunReqVO));

                }

                CallIdRecordUrl callIdRecordUrlMax = list.get(size - 1);
                minId = callIdRecordUrlMax.getCallId();
                log.info("callOutRecord老的url推送到mq，一波操作已结束， minId[{}]", minId);

                //minId 存储起来，防止重启丢失
                UpdateRecordUrl updateRecordUrl = new UpdateRecordUrl();
                updateRecordUrl.setType(0);// 0 表示call_out_plan的记录
                updateRecordUrl.setId(minId);
                updateRecordUrl.setUpdateTime(new Date());

                UpdateRecordUrlExample queryExample = new UpdateRecordUrlExample();
                queryExample.createCriteria().andTypeEqualTo(0);// 0 表示call_out_plan的记录
                List<UpdateRecordUrl> updateRecordUrlList = updateRecordUrlMapper.selectByExample(queryExample);

                if (updateRecordUrlList != null && updateRecordUrlList.size() > 0) {
                    UpdateRecordUrlExample example = new UpdateRecordUrlExample();
                    example.createCriteria().andTypeEqualTo(0);// 0 表示call_out_plan的记录
                    updateRecordUrlMapper.updateByExample(updateRecordUrl, example);
                } else {
                    updateRecordUrlMapper.insert(updateRecordUrl);
                }
            }
        }
    }

    @Override
    public void updateDetailCustomerUrl() {
        log.info("开始迁移老的call_out_detail录音地址");
        BigInteger minId = null;

        //查询上次结束时的minId
        UpdateRecordUrlExample queryExampleStart = new UpdateRecordUrlExample();
        queryExampleStart.createCriteria().andTypeEqualTo(1);// 1 表示call_out_detail的记录
        List<UpdateRecordUrl> updateRecordUrlStartList = updateRecordUrlMapper.selectByExample(queryExampleStart);
        if (updateRecordUrlStartList != null && updateRecordUrlStartList.size() > 0) {
            minId = updateRecordUrlStartList.get(0).getId();
        }

        List<Integer> orgIdList = authService.getAllOrgIds();
        log.info("循环已经开始，call_out_detail，不断查询老的detail录音推送到mq, minId[{}]", minId);
        //3000一个批次进行操作
        List<CallIdRecordUrl> list = callOutPlanMapper.selectDetailIdRecordUrl(minId, orgIdList);
        if (list != null) {
            int size = list.size();
            if (size > 0) {

                for (CallIdRecordUrl callIdRecordUrl : list) {
                    BigInteger callDetailId = callIdRecordUrl.getCallDetailId();
                    String customerRecordUrl = callIdRecordUrl.getCustomerRecordUrl();
                    String botRecordUrl = callIdRecordUrl.getBotRecordUrl();

                    //扔到mq里面
                    AliyunReqVO aliyunReqVO = new AliyunReqVO();
                    if (StringUtils.isNotEmpty(customerRecordUrl)) {
                        aliyunReqVO.setBusiId("customer_" + callIdRecordUrl.getCallId() + "_" + callDetailId);
                        aliyunReqVO.setSourceUrl(customerRecordUrl);
                        log.debug("客户说话明细录音推送到阿里云更新recordUrl队列 [{}]", aliyunReqVO);
                        queueSender.send("aliyunUploadQueue", JsonUtils.bean2Json(aliyunReqVO));
                    } else {
                        aliyunReqVO.setBusiId("agent_" + callIdRecordUrl.getCallId() + "_" + callDetailId);
                        aliyunReqVO.setSourceUrl(botRecordUrl);
                        log.debug("坐席说话明细录音推送到阿里云更新recordUrl队列 [{}]", aliyunReqVO);
                        queueSender.send("aliyunUploadQueue", JsonUtils.bean2Json(aliyunReqVO));
                    }


                }

                CallIdRecordUrl callIdRecordUrlMax = list.get(size - 1);
                minId = callIdRecordUrlMax.getCallDetailId();
                log.info("callOutDetailRecord老的url推送到mq，一波操作已结束， minId[{}]", minId);

                //minId 存储起来，防止重启丢失
                UpdateRecordUrl updateRecordUrl = new UpdateRecordUrl();
                updateRecordUrl.setType(1);// 1 表示call_out_detail的记录
                updateRecordUrl.setId(minId);
                updateRecordUrl.setUpdateTime(new Date());

                UpdateRecordUrlExample queryExample = new UpdateRecordUrlExample();
                queryExample.createCriteria().andTypeEqualTo(1);// 1 表示call_out_detail的记录
                List<UpdateRecordUrl> updateRecordUrlList = updateRecordUrlMapper.selectByExample(queryExample);

                if (updateRecordUrlList != null && updateRecordUrlList.size() > 0) {
                    UpdateRecordUrlExample example = new UpdateRecordUrlExample();
                    example.createCriteria().andTypeEqualTo(1);// 1 表示call_out_detail的记录
                    updateRecordUrlMapper.updateByExample(updateRecordUrl, example);
                } else {
                    updateRecordUrlMapper.insert(updateRecordUrl);
                }
            }
        }
    }

}
