package com.guiji.robot.api;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import com.guiji.component.result.Result;
import com.guiji.robot.model.AiCallNext;
import com.guiji.robot.model.AiCallNextReq;
import com.guiji.robot.model.AiCallStartReq;
import com.guiji.robot.model.AiHangupReq;
import com.guiji.robot.model.CheckAiReady;
import com.guiji.robot.model.CheckParams;
import com.guiji.robot.model.CheckResult;
import com.guiji.robot.model.TtsVoice;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/** 
* @ClassName: IRobot2 
* @Description: 机器人能力中心对外服务
* @date 2018年11月15日 上午10:29:45 
* @version V1.0  
*/
@Api(tags="机器人能力中心")
@FeignClient("robot")
public interface IRobotRemote {
	
	/************************1、资源服务************************/
	@ApiOperation(value = "导入任务时参数检查")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "checkParams", value = "拨打参数完整性校验", required = true)
    })
    @PostMapping(value = "/remote/checkParams")
	Result.ReturnData<CheckResult> checkParams(CheckParams checkParams);
	
	
	
	@ApiOperation(value = "拨打电话前AI资源准备校验")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "checkAiReady", value = "检查请求信息", required = true)
    })
    @PostMapping(value = "/remote/checkAiResourceReady")
	Result.ReturnData<CheckResult> checkAiResourceReady(CheckAiReady checkAiReady); 
	
	
	
	/************************2、能力服务************************/
	@ApiOperation(value = "TTS语音下载")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ttsVoice", value = "tts语音合成请求信息", required = true)
    })
    @PostMapping(value = "/remote/ttsCompose")
	Result.ReturnData<List<TtsVoice>> ttsCompose(TtsVoice ttsVoice);
	
	
	@ApiOperation(value = "拨打AI电话")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "aiCallStartReq", value = "发起电话拨打请求", required = true)
    })
    @PostMapping(value = "/remote/aiCallStart")
	Result.ReturnData<AiCallNext> aiCallStart(AiCallStartReq aiCallStartReq);
	
	
	@ApiOperation(value = "用户语音AI响应")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "aiCallNextReq", value = "用户语音AI响应", required = true)
    })
    @PostMapping(value = "/remote/aiCallNext")
	Result.ReturnData<AiCallNext> aiCallNext(AiCallNextReq aiCallNextReq);
	
	
	
	@ApiOperation(value = "挂断电话释放资源")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "aiHangupReq", value = "挂断请求信息", required = true)
    })
    @PostMapping(value = "/remote/aiHangup")
	Result.ReturnData aiHangup(AiHangupReq aiHangupReq);
	
}
