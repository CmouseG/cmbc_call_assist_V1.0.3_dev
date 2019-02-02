package com.guiji.notice.api;

import com.guiji.component.result.Result;
import com.guiji.notice.entity.MessageSend;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient("guiyu-notice-web")
public interface INoticeSend {

    @ApiOperation(value = "发送消息")
    @PostMapping(value="sendMessage")
    Result.ReturnData sendMessage(@RequestBody MessageSend messageSend);

}
