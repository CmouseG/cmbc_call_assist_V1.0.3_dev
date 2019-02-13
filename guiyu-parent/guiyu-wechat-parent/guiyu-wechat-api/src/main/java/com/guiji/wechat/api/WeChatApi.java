package com.guiji.wechat.api;

import com.guiji.component.result.Result;
import com.guiji.wechat.vo.QRCodeReqVO;
import com.guiji.wechat.vo.QRCodeRpsVO;
import com.guiji.wechat.vo.SendMsgReqVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("guiyu-wechat-web")
@Api(value = "微信Api")
public interface WeChatApi {

    @ApiOperation(value = "生成微信二维码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "qrCodeReqVO", value = "二维码请求参数", required = true)
    })
    @PostMapping(value = "get/qrCode")
    Result.ReturnData<QRCodeRpsVO> getQRCode(@RequestBody QRCodeReqVO qrCodeReqVO);


    @ApiOperation(value = "发送模板消息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sendMsgReqVO", value = "发消息请求对象", required = true)
    })
    @PostMapping(value = "send/message")
    Result.ReturnData<String> send(@RequestBody SendMsgReqVO sendMsgReqVO);
}
