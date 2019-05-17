package com.guiji.calloutserver.service.impl;

import com.guiji.auth.api.IAuth;
import com.guiji.callcenter.dao.NoticeSendLabelMapper;
import com.guiji.callcenter.dao.entity.CallOutPlan;
import com.guiji.callcenter.dao.entity.NoticeSendLabel;
import com.guiji.callcenter.dao.entity.NoticeSendLabelExample;
import com.guiji.calloutserver.service.CallOutPlanService;
import com.guiji.calloutserver.service.SendNoticeService;
import com.guiji.calloutserver.util.DateUtil;
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
        Integer orgId = callOutPlan.getOrgId();
        int linId  = callOutPlan.getLineId();
        log.info("进入方法sendNotice，进入发送消息流程， userId[{}],phone[{}],intent[{}],orgId[{}]",userId,phone,intent,orgId);

        //	意向客户
        String noticeLabels = getSendLables(userId);
        if(!StringUtils.isNullOrEmpty(noticeLabels)){
            if(noticeLabels.contains(intent)){
                log.info("产生意向客户,userId[{}],intent[{}]",userId,intent);
                sendIntentionalCustomer(Long.valueOf(userId), phone, intent, callOutPlan.getCallId(),orgId);
            }
        }
        dealIntentFNotice(intent,userId);
        dealIntentWNotice(intent,userId,linId);
    }

    public void dealIntentFNotice(String intent,int userId){
        //	连续未接通警报.单个账号批量加入计划，连续100个F类推送提醒；下次的连续未接通警报，应该除去本次已报警的F类拨打记录
        String countFKey = "callCenter_F_count_userId_"+userId;
        //  2个小时内最多推送一次，一天最多三次
        String countFSendNumberKey = "callCenter_F_sendNumber_userId_"+userId;  //发送次数
        String sendFLockKey = "callCenter_F_sendLock_userId_"+userId;  //锁住时间，2个小时

        if(intent.equals("F") && redisUtil.get(sendFLockKey)==null){
            Object countFValue = redisUtil.get(countFKey);

            if(countFValue!=null ){
                if((int) countFValue>=99){
                    //此处有并发问题
                    synchronized(SendNoticeServiceImpl.class){

                        int newValue = (int) redisUtil.get(countFKey);
                        if(newValue>=99){
                            redisUtil.set(countFKey,0);

                            Object countFSendNumberValue = redisUtil.get(countFSendNumberKey);
                            if( countFSendNumberValue ==null){
                                redisUtil.set(countFSendNumberKey,1, DateUtil.getSecondsBeforeDawn());
                                redisUtil.set(sendFLockKey,true,7200);//锁住2个小时
                                sendFNotice(userId);
                                log.info("产生连续未接通警报,userId[{}],count[{}]",userId,countFValue);
                            }else if((int)countFSendNumberValue ==1 || (int)countFSendNumberValue == 2){
                                redisUtil.incr(countFSendNumberKey,1);
                                redisUtil.set(sendFLockKey,true,7200);//锁住2个小时
                                sendFNotice(userId);
                                log.info("产生连续未接通警报,userId[{}],count[{}]",userId,countFValue);
                            }else{
                                log.info("连续未接通警报,当天已经发送了3次，不会再发送");
                            }

                        }else{
                            redisUtil.incr(countFKey,1);
                        }
                    }
                }else{
                    redisUtil.incr(countFKey,1);
                }
            }else{
                redisUtil.set(countFKey,1);
            }
        }else{
            redisUtil.set(countFKey,0);
        }
    }

    @Override
    public void dealIntentWNotice(String intent,int userId,int linId){
        //	线路报错.单个线路，出现连续100通电话的W线路报错问题推送提醒
        String countWKey = "callCenter_W_count_lineId_"+linId+"_userId_"+userId;
        //  2个小时内最多推送一次，一天最多三次
        String countWSendNumberKey = "callCenter_W_sendNumber_userId_"+userId;  //发送次数
        String sendWLockKey = "callCenter_W_sendLock_userId_"+userId;  //锁住时间，2个小时

        if(intent.equals("W")  && redisUtil.get(sendWLockKey)==null){
            Object countWValue = redisUtil.get(countWKey);
            if(countWValue!=null ){
                if((int) countWValue>=99){
                    //此处有并发问题
                    synchronized(LineCountWServiceImpl.class){

                        int newValue = (int) redisUtil.get(countWKey);
                        if(newValue>=99){
                            redisUtil.set(countWKey,0);

                            Object countWSendNumberValue = redisUtil.get(countWSendNumberKey);
                            if( countWSendNumberValue ==null){
                                redisUtil.set(countWSendNumberKey,1, DateUtil.getSecondsBeforeDawn());
                                redisUtil.set(sendWLockKey,true,7200);//锁住2个小时
                                sendWNotice(userId);
                                log.info("产生线路报错,userId[{}],count[{}]",userId,countWValue);
                            }else if((int)countWSendNumberValue ==1 || (int)countWSendNumberValue == 2){
                                redisUtil.incr(countWSendNumberKey,1);
                                redisUtil.set(sendWLockKey,true,7200);//锁住2个小时
                                sendWNotice(userId);
                                log.info("产生线路报错,userId[{}],count[{}]",userId,countWValue);
                            }else{
                                log.info("产生线路报错,当天已经发送了3次，不会再发送");
                            }
                        }else{
                            redisUtil.incr(countWKey,1);
                        }

                    }
                }else{
                    redisUtil.incr(countWKey,1);
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
    public void sendIntentionalCustomer(long userId, String phone, String intent, BigInteger callId,Integer orgId){
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
        messageSend.setWeixinPagePath(weixinCallReordUrl+callId.toString()+"&orgId="+orgId);
        messageSend.setWeixinAppId(weixinAppid);
        HashMap<String, SendMsgReqVO.Item> map = new HashMap<>();
        map.put("keyword2",new SendMsgReqVO.Item(intent+"类意向客户",null));
        map.put("keyword3",new SendMsgReqVO.Item("客户号码："+phone+"，点击查看详细通话记录，及时进行客户跟进",null));
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
        map.put("keyword2",new SendMsgReqVO.Item("连续未接通警报",null));
        map.put("keyword3",new SendMsgReqVO.Item("您的外呼任务出现连续100通电话未接通，请点击查看",null));
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
        map.put("keyword2",new SendMsgReqVO.Item("线路报错",null));
        map.put("keyword3",new SendMsgReqVO.Item("您的外呼任务出现连续100通电话线路报错的问题，请点击查看具体报错信息，并联系您的线路提供商",null));
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

        Result.ReturnData<SysOrganization> returnOrg = auth.getOrgByUserId(Long.valueOf(userId));
        String orgCode = returnOrg.getBody().getCode();//企业orgcode

        Object orgValue = redisUtil.get("callCenter_notice_label_orgCode_" + orgCode);
        if (orgValue != null) {
            String orgLables = (String) orgValue;
            return orgLables;
        } else {
            NoticeSendLabelExample example = new NoticeSendLabelExample();
            example.createCriteria().andOrgCodeEqualTo(orgCode);
            List<NoticeSendLabel> listNotices = noticeSendLabelMapper.selectByExample(example);
            if (listNotices != null && listNotices.size() > 0) {
                String label = listNotices.get(0).getLabel();
                redisUtil.set("callCenter_notice_label_orgCode_" + orgCode, label);
                return label;
            }
        }
        return null;
    }

}
