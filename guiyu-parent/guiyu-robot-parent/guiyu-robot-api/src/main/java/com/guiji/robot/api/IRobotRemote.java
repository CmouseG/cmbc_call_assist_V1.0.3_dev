package com.guiji.robot.api;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.guiji.component.result.Result;
import com.guiji.robot.model.AiCallLngKeyMatchReq;
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
@FeignClient("GUIYU-ROBOT-WEB")
public interface IRobotRemote {
	
	/************************1、资源服务************************/
	@ApiOperation(value = "导入任务时参数检查")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "checkParams", value = "拨打参数完整性校验", required = true)
    })
    @PostMapping(value = "/remote/checkParams")
	Result.ReturnData<List<CheckResult>> checkParams(@RequestBody CheckParams checkParams);
	
	
	
	/************************2、能力服务************************/
	@ApiOperation(value = "TTS语音下载")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ttsVoice", value = "tts语音合成请求信息", required = true)
    })
    @PostMapping(value = "/remote/ttsCompose")
	Result.ReturnData<List<TtsVoice>> ttsCompose(@RequestBody TtsVoice ttsVoice);
	
	
	@ApiOperation(value = "拨打AI电话")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "aiCallStartReq", value = "发起电话拨打请求", required = true)
    })
    @PostMapping(value = "/remote/aiCallStart")
	Result.ReturnData<AiCallNext> aiCallStart(@RequestBody AiCallStartReq aiCallStartReq);
	
	
	@ApiOperation(value = "sellbot关键字匹配，预校验下是否命中了关键字，命中后调用方再调aiCallNext，减轻主流程压力")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "aiCallLngKeyMatchReq", value = "关键字命中匹配", required = true)
    })
    @PostMapping(value = "/remote/aiLngKeyMatch")
	Result.ReturnData<AiCallNext> aiLngKeyMatch(@RequestBody AiCallLngKeyMatchReq aiCallLngKeyMatchReq);
	
	
	@ApiOperation(value = "用户语音AI响应")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "aiCallNextReq", value = "用户语音AI响应", required = true)
    })
    @PostMapping(value = "/remote/aiCallNext")
	Result.ReturnData<AiCallNext> aiCallNext(@RequestBody AiCallNextReq aiCallNextReq);
	
	
	
	@ApiOperation(value = "挂断电话释放资源")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "aiHangupReq", value = "挂断请求信息", required = true)
    })
    @PostMapping(value = "/remote/aiHangup")
	Result.ReturnData aiHangup(@RequestBody AiHangupReq aiHangupReq);
	
}