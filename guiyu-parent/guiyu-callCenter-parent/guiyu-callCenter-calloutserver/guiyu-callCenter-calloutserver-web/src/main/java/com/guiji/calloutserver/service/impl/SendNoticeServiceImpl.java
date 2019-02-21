package com.guiji.calloutserver.service.impl;

import com.guiji.auth.api.IAuth;
import com.guiji.callcenter.dao.NoticeSendLabelMapper;
import com.guiji.callcenter.dao.entity.CallOutPlan;
import com.guiji.callcenter.dao.entity.NoticeSendLabel;
import com.guiji.callcenter.dao.entity.NoticeSendLabelExample;
import com.guiji.calloutserver.service.CallOutPlanService;
import com.guiji.calloutserver.service.SendNoticeService;
import com.guiji.component.result.Result;
import com.guiji.notice.api.INoticeSend;
import com.guiji.notice.enm.NoticeType;
import com.guiji.notice.entity.MessageSend;
import com.guiji.user.dao.entity.SysOrganization;
import com.guiji.utils.RedisUtil;
import com.guiji.wechat.vo.SendMsgReqVO;
import com.mysql.jdbc.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class SendNoticeServiceImpl implements SendNoticeService {

    @Autowired
    RedisUtil redisUtil;
    @Autowired
    IAuth auth;
    @Autowired
    NoticeSendLabelMapper noticeSendLabelMapper;
    @Autowired
    INoticeSend iNoticeSend;
    @Autowired
    CallOutPlanService callOutPlanService;
    @Value("${weixin.templateId}")
    String weixinTemplateId;
    @Value("${weixin.appid}")
    String weixinAppid;
    @Value("${weixin.pagePath.callReordUrl}")
    String weixinCallReordUrl;
    @Value("${weixin.pagePath.reordListUrl}")
    String weixinReordListUrl;

    @Async
    @Override
    public void sendNotice(CallOutPlan callOutPlan) {
        int userId = Integer.valueOf(callOutPlan.getCustomerId());
        String phone = callOutPlan.getPhoneNum();
        String intent = callOutPlan.getAccurateIntent();
        String orgCode = callOutPlan.getOrgCode();
        int linId  = callOutPlan.getLineId();
        log.info("进入方法sendNotice，进入发送消息流程， userId[{}],phone[{}],intent[{}]",userId,phone,intent);

        //	意向客户
        String noticeLabels = getSendLables(userId);
        if(!StringUtils.isNullOrEmpty(noticeLabels)){
            if(noticeLabels.contains(intent)){
                log.info("产生意向客户,userId[{}],intent[{}]",userId,intent);
                sendIntentionalCustomer(Long.valueOf(userId), phone, intent, callOutPlan.getCallId());
            }
        }

        //	连续未接通警报.单个账号批量加入计划，连续100个F类推送提醒；下次的连续未接通警报，应该除去本次已报警的F类拨打记录
        String countFKey = "callCenter_F_count_userId_"+userId;
        if(intent.equals("F")){
            Object countFValue = redisUtil.get(countFKey);
            if(countFValue!=null ){
                redisUtil.incr(countFKey,1);
                if((int) countFValue>=99){
                    log.info("产生连续未接通警报,userId[{}],count[{}]",userId,countFValue);
                    sendFNotice(Long.valueOf(userId));
                    redisUtil.set(countFKey,0);
                }
            }else{
                redisUtil.set(countFKey,1);
            }
        }else{
            redisUtil.set(countFKey,0);
        }

        //	线路报错.单个线路，出现连续100通电话的W线路报错问题推送提醒
        String countWKey = "callCenter_W_count_lineId_"+linId+"_orgCode_"+orgCode;
        if(intent.equals("W")){
            Object countWValue = redisUtil.get(countWKey);
            if(countWValue!=null ){
                redisUtil.incr(countWKey,1);
                if((int) countWValue>=99){
                    log.info("产生线路报错,linId[{}],count[{}],orgCode[{]]",linId,countWValue,orgCode);
                    sendWNotice(Long.valueOf(userId));
                    redisUtil.set(countWKey,0);
                }
            }else{
                redisUtil.set(countWKey,1);
            }
        }else{
            redisUtil.set(countWKey,0);
        }

    }

    /**
     * 意向客户，发送消息
     */
    private void sendIntentionalCustomer(long userId, String phone, String intent, BigInteger callId){
        MessageSend messageSend = new MessageSend();
        messageSend.setNoticeType(NoticeType.intentional_customer);
        messageSend.setUserId(userId);
        //站内信
        messageSend.setMailContent("客户号码："+phone+"，点击查看详细通话记录，及时进行客户跟进");
        //邮箱
        messageSend.setEmailSubject(intent+"类意向客户");
        messageSend.setEmailContent("客户号码："+phone+"，点击查看详细通话记录，及时进行客户跟进");
        //短信
        messageSend.setSmsContent("客户号码："+phone+"，点击查看详细通话记录，及时进行客户跟进");
        //微信
        messageSend.setWeixinTemplateId(weixinTemplateId);
        messageSend.setWeixinPagePath(weixinCallReordUrl+callId.toString());
        messageSend.setWeixinAppId(weixinAppid);
        HashMap<String, SendMsgReqVO.Item> map = new HashMap<>();
        map.put("userName",new SendMsgReqVO.Item("客户号码："+phone+"，点击查看详细通话记录，及时进行客户跟进",null));
        messageSend.setWeixinData(map);
        iNoticeSend.sendMessage(messageSend);
    }
    /**
     * 产生连续未接通警报，发送消息
     */
    @Override
    public void sendFNotice(long userId){
        MessageSend messageSend = new MessageSend();
        messageSend.setNoticeType(NoticeType.unconnected_alert);
        messageSend.setUserId(userId);
        //站内信
        messageSend.setMailContent("您的外呼任务出现连续100通电话未接通，请点击查看");
        //邮箱
        messageSend.setEmailSubject("连续未接通警报");
        messageSend.setEmailContent("您的外呼任务出现连续100通电话未接通，请点击查看");
        //短信
        messageSend.setSmsContent("您的外呼任务出现连续100通电话未接通，请点击查看");
        //微信
        messageSend.setWeixinTemplateId(weixinTemplateId);
        messageSend.setWeixinPagePath(weixinReordListUrl);
        messageSend.setWeixinAppId(weixinAppid);
        HashMap<String, SendMsgReqVO.Item> map = new HashMap<>();
        map.put("userName",new SendMsgReqVO.Item("您的外呼任务出现连续100通电话未接通，请点击查看",null));
        messageSend.setWeixinData(map);
        iNoticeSend.sendMessage(messageSend);
    }

    /**
     * 线路报错，发送消息
     */
    @Override
    public void sendWNotice(long userId){
        MessageSend messageSend = new MessageSend();
        messageSend.setNoticeType(NoticeType.line_error);
        messageSend.setUserId(userId);
        //站内信
        messageSend.setMailContent("您的外呼任务出现连续100通电话线路报错的问题，请点击查看具体报错信息，并联系您的线路提供商");
        //邮箱
        messageSend.setEmailSubject("线路报错");
        messageSend.setEmailContent("您的外呼任务出现连续100通电话线路报错的问题，请点击查看具体报错信息，并联系您的线路提供商");
        //短信
        messageSend.setSmsContent("您的外呼任务出现连续100通电话线路报错的问题，请点击查看具体报错信息，并联系您的线路提供商");
        //微信
        messageSend.setWeixinTemplateId(weixinTemplateId);
        messageSend.setWeixinPagePath(weixinReordListUrl);
        messageSend.setWeixinAppId(weixinAppid);
        HashMap<String, SendMsgReqVO.Item> map = new HashMap<>();
        map.put("userName",new SendMsgReqVO.Item("您的外呼任务出现连续100通电话线路报错的问题，请点击查看具体报错信息，并联系您的线路提供商",null));
        messageSend.setWeixinData(map);
        iNoticeSend.sendMessage(messageSend);
    }

    /**
     * 查询用户需要发送消息的意向标签
     *
     * @param userId
     * @return
     */
    public String getSendLables(Integer userId) {

        Object value = redisUtil.get("callCenter_notice_label_userId_" + userId);
        if (value != null) {
            return (String) value;
        } else {
            Result.ReturnData<SysOrganization> returnOrg = auth.getOrgByUserId(Long.valueOf(userId));
            String orgCode = returnOrg.getBody().getCode();//企业orgcode

            Object orgValue = redisUtil.get("callCenter_notice_label_orgCode_" + orgCode);
            if (orgValue != null) {
                String orgLables = (String) orgValue;
                redisUtil.set("callCenter_notice_label_userId_" + userId, orgLables);
                return orgLables;
            } else {
                NoticeSendLabelExample example = new NoticeSendLabelExample();
                example.createCriteria().andOrgCodeEqualTo(orgCode);
                List<NoticeSendLabel> listNotices = noticeSendLabelMapper.selectByExample(example);
                if (listNotices != null && listNotices.size() > 0) {
                    String label = listNotices.get(0).getLabel();
                    redisUtil.set("callCenter_notice_label_orgCode_" + orgCode, label);
                    redisUtil.set("callCenter_notice_label_userId_" + userId, label);
                    return label;
                }
            }
        }
        return null;
    }

}
