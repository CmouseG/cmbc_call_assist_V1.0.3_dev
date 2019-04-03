package com.guiji.notice.service.impl;

import com.guiji.auth.api.IAuth;
import com.guiji.auth.api.IOrg;
import com.guiji.auth.model.SysUserRoleVo;
import com.guiji.ccmanager.api.INoticeLabel;
import com.guiji.common.exception.GuiyuException;
import com.guiji.component.result.Result;
import com.guiji.notice.constant.NoticeExceptionEnum;
import com.guiji.notice.dao.NoticeSettingExtMapper;
import com.guiji.notice.dao.NoticeSettingMapper;
import com.guiji.notice.dao.entity.NoticeSetting;
import com.guiji.notice.dao.entity.NoticeSettingExample;
import com.guiji.notice.enm.NoticeType;
import com.guiji.notice.entity.NoticeSettingUpdateReq;
import com.guiji.notice.entity.SettingIntent;
import com.guiji.notice.entity.User;
import com.guiji.notice.service.AuthService;
import com.guiji.notice.service.NoticeSettingService;
import com.guiji.user.dao.entity.SysOrganization;
import com.guiji.user.dao.entity.SysUser;
import com.guiji.utils.BeanUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class NoticeSettingServiceImpl implements NoticeSettingService {

    private final Logger logger = LoggerFactory.getLogger(NoticeSettingServiceImpl.class);
    @Autowired
    NoticeSettingMapper noticeSettingMapper;
    @Autowired
    IAuth auth;
    @Autowired
    IOrg iOrg;
    @Autowired
    NoticeSettingExtMapper noticeSettingExtMapper;
    @Autowired
    AuthService authService;
    @Autowired
    INoticeLabel iNoticeLabel;

    @Override
    public List<NoticeSetting> getNoticeSettingList(String orgCode) {

        NoticeSettingExample example = new NoticeSettingExample();
        example.createCriteria().andOrgCodeEqualTo(orgCode);
        return noticeSettingMapper.selectByExample(example);
    }

   /* private String getReceivers(String orgCode) {
        //查询改企业的所有管理员
        Result.ReturnData<List<SysUserRoleVo>> result = auth.getAllUserRoleByOrgCode(orgCode);
        List<SysUserRoleVo> userList = result.getBody();
        List<Long> receiverList = new ArrayList<>();
        if (userList != null && userList.size() > 0) {
            for (SysUserRoleVo sysUserRoleVo : userList) {
                List<SysRole> roleList = sysUserRoleVo.getSysRoleList();
                for (SysRole sysRole : roleList) {
                    if (sysRole.getId().intValue() == 3) {//企业管理员
                        receiverList.add(sysUserRoleVo.getSysUser().getId());
                    }
                }
            }
        }
        if (receiverList != null && receiverList.size() > 0) {
            String receiver = "";
            for (Long userId : receiverList) {
                receiver += "," + String.valueOf(userId);
            }
            return receiver.substring(1);
        }
        return null;
    }*/

    @Override
    @Transactional
    public void addNoticeSetting(String orgCode) {
        Date date = new Date();
        List list = new ArrayList<>();
        for (NoticeType e : NoticeType.values()) {

            NoticeSetting noticeSetting = new NoticeSetting();
            noticeSetting.setIsSendEmail(false);
            noticeSetting.setIsSendSms(false);
            noticeSetting.setIsSendWeixin(false);
            if(e.getValue()==1){ //意向客户不选中站内信
                noticeSetting.setIsSendMail(false);
            }else{
                noticeSetting.setIsSendMail(true);
            }
            noticeSetting.setNoticeOverType(e.getParent());
            noticeSetting.setNoticeType(e.getValue());
            noticeSetting.setOrgCode(orgCode);

            //查询改企业的所有管理员
//            String receiver = getReceivers(orgCode);
//            if (receiver != null) {
//                noticeSetting.setReceivers(receiver);
//            }
            noticeSetting.setCreateTime(date);
            list.add(noticeSetting);
        }

        //先删除，再插入
        NoticeSettingExample example = new NoticeSettingExample();
        example.createCriteria().andOrgCodeEqualTo(orgCode);
        noticeSettingMapper.deleteByExample(example);
        noticeSettingExtMapper.insertNoticeSettingBatch(list);
    }

    @Override
    @Transactional
    public void restoreDefaultSettings(String orgCode) {

        NoticeSetting noticeSetting = new NoticeSetting();
        noticeSetting.setIsSendEmail(false);
        noticeSetting.setIsSendSms(false);
        noticeSetting.setIsSendWeixin(false);
        noticeSetting.setIsSendMail(true);

        //查询改企业的所有管理员
//        String receiver = getReceivers(orgCode);
//        if (receiver != null) {
//            noticeSetting.setReceivers(receiver);
//        }
        Date date = new Date();
        noticeSetting.setUpdateTime(date);

        NoticeSettingExample example = new NoticeSettingExample();
        example.createCriteria().andOrgCodeEqualTo(orgCode)
                .andNoticeTypeNotEqualTo(NoticeType.intentional_customer.getValue());
        noticeSettingMapper.updateByExampleSelective(noticeSetting, example);


        NoticeSettingExample noticeSettingExample = new NoticeSettingExample();
        noticeSettingExample.createCriteria().andOrgCodeEqualTo(orgCode)
                .andNoticeTypeEqualTo(NoticeType.intentional_customer.getValue());
        noticeSetting.setIsSendMail(false);
        noticeSettingMapper.updateByExampleSelective(noticeSetting, noticeSettingExample);
        //重置意向标签
        Result.ReturnData returnData = null;
        try {
            returnData = iNoticeLabel.updateNoticeIntent(orgCode,"");
        }catch (Exception e){
            logger.error("调用ccmanger重置意向标签出现异常",e);
        }
        if(returnData==null || !returnData.success){
            logger.error("调用ccmanger重置意向标签出现异常,code[{}]",returnData.getCode());
            throw new GuiyuException(NoticeExceptionEnum.EXCP_NOTICE_PARAM);
        }
    }

    @Override
    public void setReceivers(String receiverIds, Integer id) {

        NoticeSetting noticeSetting = new NoticeSetting();
        noticeSetting.setReceivers(receiverIds);
        noticeSetting.setId(id);
        noticeSettingMapper.updateByPrimaryKeySelective(noticeSetting);
    }

    @Override
    public List<User> getUserList4Receive(Long userId) {

        Result.ReturnData<List<SysUser>> result = auth.getAllUserByUserId(userId);

        List<SysUser> list = result.getBody();

        List<User> returnList = new ArrayList<>();

        if(list!=null && list.size()>0){
            for (SysUser sysUser : list) {
                User user = new User();
                Long sysUserId = sysUser.getId();
                user.setId(sysUserId);
                user.setUsername(sysUser.getUsername());
                returnList.add(user);
            }
        }

        return returnList;

    }

    @Override
    public void updateNoticeSetting(List<NoticeSettingUpdateReq> NoticeSettingUpdateReqs) {

        for (NoticeSettingUpdateReq noticeSettingUpdateReq : NoticeSettingUpdateReqs) {
            NoticeSetting record = new NoticeSetting();
            BeanUtil.copyProperties(noticeSettingUpdateReq, record);
            record.setUpdateTime(new Date());
            noticeSettingMapper.updateByPrimaryKeySelective(record);
        }

    }

    @Override
    public void addNoticeSettingReceiver(String orgCode, Long userId) {
        noticeSettingExtMapper.addNoticeSettingReceiver(orgCode, String.valueOf(userId));
    }

    @Override
    public void addWeixinNoticeSettingReceiver(String orgCode, Long userId) {

        String userIdStr = String.valueOf(userId);
        //如果是企业管理员
//        if(authService.isCompanyAdmin(userId)){

        //如果userId在receiver里,则要把是否发送微信勾上
        NoticeSettingExample example = new NoticeSettingExample();
        example.createCriteria().andOrgCodeEqualTo(orgCode);
        List<NoticeSetting> listNoticeSetting = noticeSettingMapper.selectByExample(example);
        if (listNoticeSetting != null && listNoticeSetting.size() > 0) {
            for (NoticeSetting noticeSetting : listNoticeSetting) {
                String receivers = noticeSetting.getReceivers();
                if (receivers != null) {
                    String[] receiverIds = receivers.split(",");
                    List<String> list = Arrays.asList(receiverIds);
                    if (list.contains(userIdStr)) {
                        if (!noticeSetting.getIsSendWeixin()) {
                            noticeSetting.setIsSendWeixin(true);
                            noticeSettingMapper.updateByPrimaryKeySelective(noticeSetting);
                        }
                    }
                }
            }
        }

//        }

    }

    @Override
    public List<SettingIntent> queryNoticeIntent(Long userId) {

        Result.ReturnData<SysOrganization> orgReturn = auth.getOrgByUserId(userId);
        String orgCode = orgReturn.getBody().getCode();

        Result.ReturnData<String> resultDate = iNoticeLabel.queryNoticeIntent(orgCode);
        String noticeIntent = resultDate.getBody();

        List<String> settingIntentList = new ArrayList<>();
        if (StringUtils.isNotBlank(noticeIntent)) {
            String[] settingIntentArr = noticeIntent.split(",");
            settingIntentList = Arrays.asList(settingIntentArr);
        }

        List<SettingIntent> resultList = new ArrayList<>();
//        Result.ReturnData<SysUser> result = auth.getUserById(userId);
//        String intentLabel = result.getBody().getIntenLabel();
//        if (StringUtils.isNotBlank(intentLabel)) {
            String[] allIntentArr = {"A","B","C","D","E","G","H","I","J","K","L","N","O","P","Q","R","S","T","Y","Z",};
            for (String intent : allIntentArr) {
                SettingIntent settingIntent = new SettingIntent();
                settingIntent.setIntent(intent);
                if (settingIntentList.contains(intent)) {
                    settingIntent.setSelected(true);
                } else {
                    settingIntent.setSelected(false);
                }
                resultList.add(settingIntent);
            }

//        }
        return resultList;

    }

    @Override
    public void updateNoticeIntent(String labels, Long userId) {

        Result.ReturnData<SysOrganization> orgReturn = auth.getOrgByUserId(userId);
        String orgCode = orgReturn.getBody().getCode();

        iNoticeLabel.updateNoticeIntent(orgCode,labels);

    }
}
