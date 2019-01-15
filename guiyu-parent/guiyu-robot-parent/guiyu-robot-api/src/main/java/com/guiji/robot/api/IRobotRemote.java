package com.guiji.robot.api;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.guiji.component.result.Result;
import com.guiji.robot.model.AiCallApplyReq;
import com.guiji.robot.model.AiCallLngKeyMatchReq;
import com.guiji.robot.model.AiCallNext;
import com.guiji.robot.model.AiCallNextReq;
import com.guiji.robot.model.AiCallStartReq;
import com.guiji.robot.model.AiFlowMsgPushReq;
import com.guiji.robot.model.AiHangupReq;
import com.guiji.robot.model.CheckParamsReq;
import com.guiji.robot.model.CheckResult;
import com.guiji.robot.model.TtsCallback;
import com.guiji.robot.model.TtsComposeCheckRsp;
import com.guiji.robot.model.TtsVoice;
import com.guiji.robot.model.TtsVoiceReq;
import com.guiji.robot.model.UserAiCfgBaseInfoVO;
import com.guiji.robot.model.UserAiCfgVO;
import com.guiji.robot.model.UserResourceCache;

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
@FeignClient("guiyu-robot-web")
public interface IRobotRemote {
	
	/************************1、资源服务************************/
	@ApiOperation(value = "导入任务时参数检查")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "checkParams", value = "拨打参数完整性校验", required = true)
    })
    @PostMapping(value = "/remote/checkParams")
	Result.ReturnData<List<CheckResult>> checkParams(@RequestBody CheckParamsReq checkParamsReq);
	
	
	
	/************************2、能力服务************************/
	@ApiOperation(value = "TTS语音合成结果检查")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "seqIdList", value = "会话id列表", required = true)
    })
    @PostMapping(value = "/remote/ttsComposeCheck")
	Result.ReturnData<List<TtsComposeCheckRsp>> ttsComposeCheck(@RequestBody List<TtsVoiceReq> ttsVoiceReqList);
	
	
	@ApiOperation(value = "TTS语音下载")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ttsVoice", value = "tts语音合成请求信息", required = true)
    })
    @PostMapping(value = "/remote/ttsCompose")
	Result.ReturnData<List<TtsVoice>> ttsCompose(@RequestBody TtsVoiceReq ttsVoice);
	
	
	@ApiOperation(value = "TTS语音合成后的回call服务")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ttsCallbackList", value = "tts语音合成回调信息列表", required = true)
    })
    @PostMapping(value = "/remote/ttsCallback")
	Result.ReturnData ttsCallback(@RequestBody List<TtsCallback> ttsCallbackList);

	
	@ApiOperation(value = "AI资源申请")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "aiCallApplyReq", value = "机器人资源申请", required = true)
    })
    @PostMapping(value = "/remote/aiCallApply")
	Result.ReturnData<AiCallNext> aiCallApply(@RequestBody AiCallApplyReq aiCallApplyReq);
	
	
	@ApiOperation(value = "拨打AI电话")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "aiCallStartReq", value = "发起电话拨打请求", required = true)
	})
	@PostMapping(value = "/remote/aiCallStart")
	Result.ReturnData<AiCallNext> aiCallStart(@RequestBody AiCallStartReq aiCallStartReq);
	
	
	
	@ApiOperation(value = "AI消息流推送")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "aiFlowMsgPushReq", value = "通话过程中的消息推送", required = true)
    })
    @PostMapping(value = "/remote/flowMsgPush")
	Result.ReturnData flowMsgPush(@RequestBody AiFlowMsgPushReq aiFlowMsgPushReq);
	
	
	
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
	
	
	@ApiOperation(value = "查询用户机器人配置基本信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户编号", required = true)
    })
    @PostMapping(value = "/remote/queryCustBaseAccount")
	Result.ReturnData<UserAiCfgBaseInfoVO> queryCustBaseAccount(@RequestParam(value="userId",required=true)String userId);
	
	
	@ApiOperation(value = "查询用户机器人拆分详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户编号", required = true)
    })
    @PostMapping(value = "/remote/queryCustAccount")
	Result.ReturnData<UserAiCfgVO> queryCustAccount(@RequestParam(value="userId",required=true)String userId);
	
	
	@ApiOperation(value = "查询机器人资源数量（sellbot）")
    @PostMapping(value = "/remote/queryRobotResNum")
	Result.ReturnData<Integer> queryRobotResNum();
	
	@ApiOperation(value = "重新加载sellbot机器人资源（sellbot）")
    @PostMapping(value = "/remote/reloadSellbot")
	Result.ReturnData<Integer> reloadSellbot();
	
	
	@ApiOperation(value = "查询用户机器人配置信息")
    @PostMapping(value = "/remote/queryUserResourceCache")
	Result.ReturnData<UserResourceCache> queryUserResourceCache(@RequestParam(value="userId",required=true) String userId);
}
