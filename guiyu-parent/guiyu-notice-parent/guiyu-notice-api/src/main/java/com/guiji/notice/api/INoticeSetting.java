package com.guiji.notice.api;

import com.guiji.component.result.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("guiyu-notice-web")
public interface INoticeSetting {

    @ApiOperation(value = "在新增一个组织机构(或者是开户)的时候调用,初始化企业的消息设置")
    @GetMapping(value="addNoticeSetting")
    Result.ReturnData addNoticeSetting(@RequestParam("orgCode") String orgCode);


    @ApiOperation(value = "在新增一个企业管理员的时候，调用该方法。加入到消息的接收者中去")
    @GetMapping(value="addNoticeSettingReceiver")
    Result.ReturnData addNoticeSettingReceiver(@RequestParam("userId") Long userId);


    @ApiOperation(value = "在绑定微信的时候，调用该方法。加入到消息的接收者中去")
    @GetMapping(value="addWeixinNoticeSettingReceiver")
    Result.ReturnData addWeixinNoticeSettingReceiver(@RequestParam("userId") Long userId);
}
