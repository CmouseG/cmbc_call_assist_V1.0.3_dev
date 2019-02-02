package com.guiji.calloutserver.service.impl;

import com.guiji.auth.api.IAuth;
import com.guiji.callcenter.dao.NoticeSendLabelMapper;
import com.guiji.callcenter.dao.entity.NoticeSendLabel;
import com.guiji.callcenter.dao.entity.NoticeSendLabelExample;
import com.guiji.calloutserver.service.SendNoticeService;
import com.guiji.component.result.Result;
import com.guiji.notice.api.INoticeSend;
import com.guiji.notice.entity.MessageSend;
import com.guiji.user.dao.entity.SysOrganization;
import com.guiji.utils.RedisUtil;
import com.mysql.jdbc.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

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

    @Async
    @Override
    public void sendNotice(Integer userId, String phone, String intent) {
        log.info("进入方法sendNotice，进入发送消息流程， userId[{}],phone[{}],intent[{}]",userId,phone,intent);
        String noticeLabels = getSendLables(userId);
        if(!StringUtils.isNullOrEmpty(noticeLabels)){
            if(noticeLabels.contains(intent)){
                log.info("开始发送消息 userId[{}],phone[{}],intent[{}]",userId,phone,intent);
                MessageSend messageSend = new MessageSend();
                messageSend.setUserId(Long.valueOf(userId));
                messageSend.setMailContent("客户号码："+phone+"，点击查看详细通话记录，及时进行客户跟进");
                iNoticeSend.sendMessage(messageSend);
            }
        }
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
