package com.guiji.notice.service.impl;

import com.guiji.auth.api.IAuth;
import com.guiji.component.result.Result;
import com.guiji.notice.dao.NoticeInfoMapper;
import com.guiji.notice.dao.NoticeMailInfoMapper;
import com.guiji.notice.dao.NoticeSettingMapper;
import com.guiji.notice.dao.entity.NoticeInfo;
import com.guiji.notice.dao.entity.NoticeMailInfo;
import com.guiji.notice.dao.entity.NoticeSetting;
import com.guiji.notice.dao.entity.NoticeSettingExample;
import com.guiji.notice.entity.MessageSend;
import com.guiji.notice.service.NoticeSendService;
import com.guiji.notice.service.SendEmailService;
import com.guiji.user.dao.entity.SysOrganization;
import com.guiji.user.dao.entity.SysUserExt;
import com.guiji.utils.BeanUtil;
import com.guiji.wechat.api.WeChatApi;
import com.guiji.wechat.vo.SendMsgReqVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class NoticeSendServiceImpl implements NoticeSendService {

    private final Logger logger = LoggerFactory.getLogger(NoticeSendServiceImpl.class);

    @Autowired
    NoticeInfoMapper noticeInfoMapper;
    @Autowired
    NoticeSettingMapper noticeSettingMapper;
    @Autowired
    NoticeMailInfoMapper noticeMailInfoMapper;
    @Autowired
    IAuth auth;
    @Autowired
    SendEmailService sendEmailService;
    @Autowired
    WeChatApi weChatApi;

    @Override
    public void sendMessage(MessageSend messageSend) {

        Result.ReturnData<SysOrganization> returnOrg = auth.getOrgByUserId(messageSend.getUserId());

        String orgCode = returnOrg.getBody().getCode();
        int noticeType = messageSend.getNoticeType().getValue();

        //notice_info新增记录
        NoticeInfo noticeInfo = new NoticeInfo();
        BeanUtil.copyProperties(messageSend,noticeInfo);
        noticeInfo.setNoticeType(noticeType);
        noticeInfo.setOrgCode(orgCode);
        noticeInfo.setCreateTime(new Date());
        noticeInfo.setWeixinData(messageSend.getWeixinData().toString());
        noticeInfoMapper.insertSelective(noticeInfo);
        int infoId = noticeInfo.getId();

        //查询setting表，查看需要发送信息的方式
        NoticeSettingExample example = new NoticeSettingExample();
        example.createCriteria()
                .andOrgCodeEqualTo(orgCode)
                .andNoticeTypeEqualTo(noticeType);
        List<NoticeSetting> settingsList =  noticeSettingMapper.selectByExample(example);
        if(settingsList!=null && settingsList.size()>0){
            NoticeSetting noticeSetting = settingsList.get(0);

            String receivers = noticeSetting.getReceivers();
            if(receivers!=null){
                String[] receiverArr =  receivers.split(",");

                //是否发送站内信
                boolean isSendMail = noticeSetting.getIsSendMail();
                if(isSendMail){
                    for(String userIdString:receiverArr){
                        NoticeMailInfo noticeMailInfo = new NoticeMailInfo();
                        noticeMailInfo.setInfoId(infoId);
                        noticeMailInfo.setReceiverId(Integer.valueOf(userIdString));
                        Date date = new Date();
                        noticeMailInfo.setCreateTime(date);
                        noticeMailInfo.setReceiveTime(date);
                        noticeMailInfo.setIsRead(false);
                        noticeMailInfo.setIsdel(false);
                        noticeMailInfoMapper.insert(noticeMailInfo);
                        logger.info("--------发送站内信 noticeMailInfo[{}]",noticeMailInfo);
                    }
                }

                //是否发送短信
                boolean isSendSms = noticeSetting.getIsSendSms();
                if(isSendSms){
                    for(String userIdString:receiverArr){
                        //发送短信
                        long userId = Long.valueOf(userIdString);
                        Result.ReturnData<SysUserExt> returnData = auth.getUserExtByUserId(userId);
                        String phone = returnData.getBody().getMobile();
                        //发送短信，接口还没有好
                    }
                }
                //是否发送邮件
                boolean isSendEmail = noticeSetting.getIsSendEmail();
                if(isSendEmail){
                    for(String userIdString:receiverArr){
                        long userId = Long.valueOf(userIdString);
                        Result.ReturnData<SysUserExt> returnData = auth.getUserExtByUserId(userId);
                        String email = returnData.getBody().getEmail();
                        if(email!=null){
                            try {
                                logger.info("--------发送邮件 userId[{}]",userIdString);
                                sendEmailService.sendEmail(email,messageSend.getEmailSubject(),messageSend.getEmailContent());
                            } catch (Exception e) {
                                logger.error("-----sendEmail,has eror messageSend[{}]",messageSend,e);
                            }
                        }
                    }
                }
                //是否发送微信
                boolean isSendWeixin = noticeSetting.getIsSendWeixin();
                if(isSendWeixin){
                    for(String userIdString:receiverArr){
                        long userId = Long.valueOf(userIdString);
                        Result.ReturnData<SysUserExt> returnData = auth.getUserExtByUserId(userId);
                        String openId = returnData.getBody().getWechatOpenid();
                        SendMsgReqVO sendMsgReqVO = new SendMsgReqVO();

                        sendMsgReqVO.setPagePath(messageSend.getWeixinPagePath());
                        sendMsgReqVO.setTemplateId(messageSend.getWeixinTemplateId());
                        sendMsgReqVO.setAppId(messageSend.getWeixinAppId());
                        sendMsgReqVO.setData(messageSend.getWeixinData());
                        sendMsgReqVO.setUrl(messageSend.getWeixinUrl());
                        sendMsgReqVO.setOpenID(openId);
                        sendMsgReqVO.setUserId(String.valueOf(messageSend.getUserId()));
                        weChatApi.send(sendMsgReqVO);
                    }
                }
            }

        }

    }

}
