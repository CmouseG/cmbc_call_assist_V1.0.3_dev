package com.guiji.notice.controller;

import com.guiji.auth.api.IAuth;
import com.guiji.component.jurisdiction.Jurisdiction;
import com.guiji.component.result.Result;
import com.guiji.notice.api.INoticeSetting;
import com.guiji.notice.constant.Constant;
import com.guiji.notice.dao.entity.NoticeSetting;
import com.guiji.notice.entity.NoticeSettingUpdateReq;
import com.guiji.notice.entity.NoticeSettingVO;
import com.guiji.notice.entity.SettingIntent;
import com.guiji.notice.entity.User;
import com.guiji.notice.service.AuthService;
import com.guiji.notice.service.NoticeSettingService;
import com.guiji.user.dao.entity.SysOrganization;
import com.guiji.user.dao.entity.SysUser;
import com.guiji.utils.BeanUtil;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;

@Validated
@RestController
public class NoticeSettingController implements INoticeSetting {

    private final Logger logger = LoggerFactory.getLogger(NoticeSettingController.class);

    @Autowired
    NoticeSettingService noticeSettingService;
    @Autowired
    IAuth auth;
    @Autowired
    AuthService authService;

    @ApiOperation(value = "提醒设置，查询提醒设置列表")
    @GetMapping(value = "getNoticeSettingList")
    public Result.ReturnData<List<NoticeSettingVO>> getNoticeSettingList(@RequestHeader Long userId) {

        Result.ReturnData<SysOrganization> returnData = auth.getOrgByUserId(userId);
        String orgCode = returnData.getBody().getCode();
        List<NoticeSetting> list = noticeSettingService.getNoticeSettingList(orgCode);

        List<NoticeSettingVO> resultList = new ArrayList<>();
        if(list!=null && list.size()>0){

            for(NoticeSetting noticeSetting:list){
                NoticeSettingVO noticeSettingVO = new NoticeSettingVO();
                BeanUtil.copyProperties(noticeSetting,noticeSettingVO);
                String receivers = noticeSetting.getReceivers();

                List<User> userList = new ArrayList<>();
                if(StringUtils.isNotBlank(receivers)){
                    String[] receiverArr = receivers.split(",");
                    for(String userIdStr:receiverArr){
                        Long receiveUserId = Long.valueOf(userIdStr);
                        Result.ReturnData<SysUser> result = auth.getUserById(receiveUserId);
                        String userName = result.getBody().getUsername();
                        User user = new User(receiveUserId,userName);
                        userList.add(user);
                    }

                }
                noticeSettingVO.setUserList(userList);
                resultList.add(noticeSettingVO);
            }
        }
        return Result.ok(resultList);
    }

    @Override
    public Result.ReturnData addNoticeSetting(String orgCode) {
        logger.info("--------get request addNoticeSetting orgCode[{}]",orgCode);
        if(!StringUtils.isBlank(orgCode)){
            noticeSettingService.addNoticeSetting(orgCode);
            return Result.ok();
        }else{
            return Result.error(Constant.ERROR_PARAM);
        }

    }

    @Override
    public Result.ReturnData addNoticeSettingReceiver(Long userId) {

       /* Result.ReturnData<SysOrganization> returnData = auth.getOrgByUserId(userId);
        String orgCode = returnData.getBody().getCode();
        if (authService.isCompanyAdmin(userId)) {
            logger.info("创建消息接受者,管理员,orgCode[{}],userId[{}],",orgCode,userId);
            noticeSettingService.addNoticeSettingReceiver(orgCode, userId);
        }*/
        return Result.ok();
    }

    @Override
    public Result.ReturnData addWeixinNoticeSettingReceiver(Long userId) {
        Result.ReturnData<SysOrganization> returnData = auth.getOrgByUserId(userId);
        String orgCode = returnData.getBody().getCode();
        noticeSettingService.addWeixinNoticeSettingReceiver(orgCode, userId);
        return Result.ok();
    }

    @ApiOperation(value = "跳转到编辑消息接收者时，调用该方法获取用户列表")
    @GetMapping(value = "getUserList4Receive")
    public Result.ReturnData getUserList4Receive(@RequestHeader Long userId) {

        List<User> list = noticeSettingService.getUserList4Receive(userId);
        return Result.ok(list);
    }

    @ApiOperation(value = "提醒设置，编辑消息接收者,确定提交调用该方法。用户Id以逗号连接")
    @Jurisdiction("newsCenter_receiveSetting_editReceiver")
    @GetMapping(value = "setReceivers")
    public Result.ReturnData setReceivers(@NotNull(message="消息接受者id不能为空") @Pattern(regexp = "^(\\d+(,\\d+)*)*$", message = "消息接受者id格式错误")  String receiverIds,
                                          @NotEmpty(message="id不能为空") String id) {
        noticeSettingService.setReceivers(receiverIds,Integer.valueOf(id));

        return Result.ok();
    }

    @ApiOperation(value = "提醒设置，恢复默认设置")
    @Jurisdiction("newsCenter_receiveSetting_reDefault")
    @GetMapping(value = "restoreDefaultSettings")
    public Result.ReturnData restoreDefaultSettings(@RequestHeader Long userId) {
        Result.ReturnData<SysOrganization> returnData = auth.getOrgByUserId(userId);
        String orgCode = returnData.getBody().getCode();
        noticeSettingService.restoreDefaultSettings(orgCode);

        return Result.ok();
    }


    @ApiOperation(value = "修改提醒设置")
    @PostMapping(value = "updateNoticeSetting")
    public Result.ReturnData updateNoticeSetting(@RequestBody List<NoticeSettingUpdateReq> noticeSettingUpdateReqs) {
        logger.info("------get updateNoticeSetting request noticeSettingUpdateReqs[{}]", noticeSettingUpdateReqs);
        noticeSettingService.updateNoticeSetting(noticeSettingUpdateReqs);
        return Result.ok();
    }


    @ApiOperation(value = "查询意向标签")
    @GetMapping(value = "queryNoticeIntent")
    public Result.ReturnData queryNoticeIntent(@RequestHeader Long userId) {

        List<SettingIntent> list = noticeSettingService.queryNoticeIntent(userId);
        return Result.ok(list);
    }

    @ApiOperation(value = "修改意向标签,勾选的标签以逗号拼接传递到labels字段")
    @Jurisdiction("newsCenter_receiveSetting_noticeType1")
    @GetMapping(value = "updateNoticeIntent")
    public Result.ReturnData updateNoticeIntent(@RequestHeader Long userId,@NotNull(message="标签不能不传") String labels) {

        logger.info("----get updateNoticeIntent request labels[{}],userId[{}]",labels,userId);
        noticeSettingService.updateNoticeIntent(labels,userId);
        return Result.ok();
    }
}
