package com.guiji.notice.service.impl;

import com.alibaba.fastjson.JSON;
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
import com.guiji.notice.service.AuthService;
import com.guiji.notice.service.NoticeSendService;
import com.guiji.notice.service.SendEmailService;
import com.guiji.user.dao.entity.SysOrganization;
import com.guiji.user.dao.entity.SysRole;
import com.guiji.user.dao.entity.SysUser;
import com.guiji.user.dao.entity.SysUserExt;
import com.guiji.utils.BeanUtil;
import com.guiji.wechat.api.WeChatApi;
import com.guiji.wechat.vo.SendMsgReqVO;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class NoticeSendServiceImpl implements NoticeSendService {

    private final Logger logger = LoggerFactory.getLogger(NoticeSendServiceImpl.class);
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

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
    @Autowired
    AuthService authService;

    @Override
    @Async
    public void sendMessage(MessageSend messageSend) {
        try {
            String orgCode = messageSend.getOrgCode();
            if (orgCode == null) {
                Result.ReturnData<SysOrganization> returnOrg = auth.getOrgByUserId(messageSend.getUserId());
                orgCode = returnOrg.getBody().getCode();
            }

            int noticeType = messageSend.getNoticeType().getValue();

            //notice_info新增记录
            NoticeInfo noticeInfo = new NoticeInfo();
            BeanUtil.copyProperties(messageSend, noticeInfo);
            noticeInfo.setNoticeType(noticeType);
            noticeInfo.setOrgCode(orgCode);
            noticeInfo.setCreateTime(new Date());
            if (messageSend.getWeixinData() != null) {
                noticeInfo.setWeixinData(messageSend.getWeixinData().toString());
            }
            noticeInfoMapper.insertSelective(noticeInfo);
            int infoId = noticeInfo.getId();

            //查询setting表，查看需要发送信息的方式
            NoticeSettingExample example = new NoticeSettingExample();
            example.createCriteria()
                    .andOrgCodeEqualTo(orgCode)
                    .andNoticeTypeEqualTo(noticeType);
            List<NoticeSetting> settingsList = noticeSettingMapper.selectByExample(example);
            if (settingsList != null && settingsList.size() > 0) {
                NoticeSetting noticeSetting = settingsList.get(0);

                String receivers = noticeSetting.getReceivers();
                if (receivers != null) {
                    String[] receiverArray = receivers.split(",");

                    List<String> receiverList = new ArrayList<>();
                    if (messageSend.getUserId() != null) {  //传递了userId
                        long userId = messageSend.getUserId();
                        for (String userIdString : receiverArray) {
                            long receiveId = Long.valueOf(userIdString);
                            int authLevel = authService.getAuthLevel(receiveId);
                            logger.info("receiveId[{}]的authLevel[{}]", receiveId, authLevel);
                            if (authLevel == 1) {//本人权限
                                if (userId == receiveId) {
                                    receiverList.add(userIdString);
                                }
                            } else {
                                String receiveCode = authService.getOrgCode(receiveId);
                                if (authLevel == 2) { //当前orgCode
                                    if (receiveCode.equals(orgCode)) {
                                        receiverList.add(userIdString);
                                    }
                                } else if (authLevel == 3) {
                                    if (orgCode.startsWith(receiveCode)) {
                                        receiverList.add(userIdString);
                                    }
                                }
                            }
                        }
                    } else {  //没有传递userId ，所有都发送消息
                        receiverList = Arrays.asList(receiverArray);
                    }
                    logger.info("消息接收者receiverList[{}]", receiverList);
                    //是否发送站内信
                    boolean isSendMail = noticeSetting.getIsSendMail();
                    if (isSendMail) {
                        if (receiverList.size() > 0) {
                            for (String userIdString : receiverList) {
                                NoticeMailInfo noticeMailInfo = new NoticeMailInfo();
                                noticeMailInfo.setInfoId(infoId);
                                noticeMailInfo.setReceiverId(Integer.valueOf(userIdString));
                                Date date = new Date();
                                noticeMailInfo.setCreateTime(date);
                                noticeMailInfo.setReceiveTime(date);
                                noticeMailInfo.setIsRead(false);
                                noticeMailInfo.setIsdel(false);
                                noticeMailInfoMapper.insert(noticeMailInfo);
                                logger.info("send mail---> noticeMailInfo[{}],messageSend[{}]", noticeMailInfo, messageSend);
                            }
                        }
                    }

                    //是否发送短信
                    boolean isSendSms = noticeSetting.getIsSendSms();
                    if (isSendSms) {
                        if (receiverList.size() > 0) {
                            for (String userIdString : receiverList) {
                                //发送短信
                                long userId = Long.valueOf(userIdString);
                                Result.ReturnData<SysUserExt> returnData = null;
                                try {
                                    returnData = auth.getUserExtByUserId(userId);
                                } catch (Exception e) {
                                    logger.error("auth.getUserExtByUserId", e);
                                    continue;
                                }
                                String phone = returnData.getBody().getMobile();
                                //发送短信，接口还没有好
                            }
                        }
                    }
                    //是否发送邮件
                    boolean isSendEmail = noticeSetting.getIsSendEmail();
                    if (isSendEmail) {
                        if (receiverList.size() > 0) {
                            for (String userIdString : receiverList) {
                                long userId = Long.valueOf(userIdString);
                                Result.ReturnData<SysUserExt> returnData = null;
                                try {
                                    returnData = auth.getUserExtByUserId(userId);
                                } catch (Exception e) {
                                    logger.error("auth.getUserExtByUserId", e);
                                    continue;
                                }
                                if (returnData != null && returnData.getBody() != null) {
                                    String email = returnData.getBody().getEmail();
                                    if (email != null) {
                                        try {
                                            sendEmailService.sendEmail(email, messageSend.getEmailSubject(), messageSend.getEmailContent());
                                            logger.info("send email---> email[{}],messageSend[{}]", email, messageSend);
                                        } catch (Exception e) {
                                            logger.error("-----sendEmail,has eror messageSend[{}]", messageSend, e);
                                        }
                                    }
                                }
                            }
                        }
                    }
                    //是否发送微信
                    boolean isSendWeixin = noticeSetting.getIsSendWeixin();
                    if (isSendWeixin) {
                        if (receiverList.size() > 0) {
                            for (String userIdString : receiverList) {
                                long userId = Long.valueOf(userIdString);
                                Result.ReturnData<SysUserExt> returnData = null;
                                Result.ReturnData<SysUser> returnUser = null;
                                try {
                                    returnData = auth.getUserExtByUserId(userId);
                                    returnUser = auth.getUserById(userId);
                                } catch (Exception e) {
                                    logger.error("auth.getUserExtByUserId,getUserById", e);
                                    continue;
                                }

                                if (returnUser != null && returnUser.getBody() != null && returnData != null && returnData.getBody() != null) {
                                    String openId = returnData.getBody().getWechatOpenid();
                                    if (returnData.getBody().getWechatStatus() == 1 && StringUtils.isNotBlank(openId)) {  //判断是否已绑定
                                        SendMsgReqVO sendMsgReqVO = new SendMsgReqVO();

                                        sendMsgReqVO.setPagePath(messageSend.getWeixinPagePath());
                                        sendMsgReqVO.setTemplateId(messageSend.getWeixinTemplateId());
                                        sendMsgReqVO.setAppId(messageSend.getWeixinAppId());
                                        sendMsgReqVO.setUrl(messageSend.getWeixinUrl());
                                        sendMsgReqVO.setOpenID(openId);
                                        sendMsgReqVO.setUserId(String.valueOf(userId));

                                        HashMap<String, SendMsgReqVO.Item> data = messageSend.getWeixinData();
                                        if (data != null) {
                                            sendMsgReqVO.setData(data);
                                            if (data.get("keyword1") == null) {
                                                sendMsgReqVO.addData("keyword1", returnUser.getBody().getUsername());
                                            }
                                            if (data.get("keyword4") == null) {
                                                sendMsgReqVO.addData("keyword4", sdf.format(new Date()));
                                            }
                                        }

                                        weChatApi.send(sendMsgReqVO);
                                        logger.info("send weixin---> openId[{}],messageSend[{}]", openId, JSON.toJSONString(sendMsgReqVO));
                                    }

                                }
                            }
                        }
                    }
                }

            }
        }catch (Exception e){
            logger.info("出现异常啦-------------",e);
        }
    }

}
