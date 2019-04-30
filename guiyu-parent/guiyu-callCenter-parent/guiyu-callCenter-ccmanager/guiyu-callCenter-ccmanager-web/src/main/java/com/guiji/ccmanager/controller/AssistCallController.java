package com.guiji.ccmanager.controller;

import com.guiji.callcenter.dao.entity.CallOutPlan;
import com.guiji.calloutserver.api.IAssistCall;
import com.guiji.ccmanager.constant.Constant;
import com.guiji.ccmanager.service.AssistCallService;
import com.guiji.component.jurisdiction.Jurisdiction;
import com.guiji.component.result.Result;
import com.guiji.utils.FeignBuildUtil;
import io.swagger.annotations.ApiOperation;
import org.hibernate.validator.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.math.BigInteger;

/**
 * 废弃
 */
@RestController
@Validated
public class AssistCallController {

    private final Logger log = LoggerFactory.getLogger(AssistCallController.class);

    @Autowired
    AssistCallService assistCallService;

   /* @ApiOperation(value = "协呼，转人工")
    @GetMapping("/assistToAgent")
    public Result.ReturnData assistToAgent(@NotEmpty(message = "callId不能为空") String callId, @NotEmpty(message = "agentGroupId不能为空") String agentGroupId){

        log.info("协呼get assistToAgent request callId[{}],agentGroupId[{}]",callId,agentGroupId);
        CallOutPlan callOutplan = assistCallService.getCallOutplan(new BigInteger(callId));

        if(callOutplan!=null){

            if(callOutplan.getAgentId()!=null){//已经转人工
                return Result.error(Constant.ERROR_ASSIT_TOAGENTED);
            }else{
                String server = callOutplan.getServerid();
                if(server!=null){
                    IAssistCall iAssistCall = FeignBuildUtil.feignBuilderTarget(IAssistCall.class, Constant.PROTOCOL + server);
                    return iAssistCall.assistToAgent(callId,agentGroupId);
                }else{
                    return Result.error(Constant.ERROR_ASSIT_SERVERNOTEXITST);
                }
            }
        }else{
            return Result.error(Constant.ERROR_ASSIT_PLANNOTEXITST);
        }

    }

    @ApiOperation(value = "协呼，关闭机器人")
    @GetMapping("/assistCloseRobot")
    public Result.ReturnData assistCloseRobot(@NotEmpty(message = "callId不能为空") String callId){

        log.info("协呼get assistCloseRobot request callId[{}]",callId);
        CallOutPlan callOutplan = assistCallService.getCallOutplan(new BigInteger(callId));

        if (callOutplan != null) {
            String server = callOutplan.getServerid();
            if (server != null) {
                IAssistCall iAssistCall = FeignBuildUtil.feignBuilderTarget(IAssistCall.class, Constant.PROTOCOL + server);
                return iAssistCall.assistCloseRobot(callId);
            } else {
                return Result.error(Constant.ERROR_ASSIT_SERVERNOTEXITST);
            }
        } else {
            return Result.error(Constant.ERROR_ASSIT_PLANNOTEXITST);
        }

    }*/

    @ApiOperation(value = "协呼，转人工并且关闭机器人")
    @Jurisdiction("callCenter_workPlatform_helpCallHandUp")
    @GetMapping("/assistToAgentAndCloseRobot")
    public Result.ReturnData assistToAgentAndCloseRobot(@NotEmpty(message = "callId不能为空") String callId,@NotEmpty(message = "agentGroupId不能为空") String agentGroupId){
        log.info("协呼get assistToAgentAndCloseRobot request callId[{}],agentGroupId[{}]",callId,agentGroupId);

        //名为callId，实为uuid
        BigInteger bigIntegerId = null;
        Integer orgId = null;
        try {
            String[] arr = callId.split(Constant.UUID_SEPARATE);
            bigIntegerId = new BigInteger(arr[0]);
            orgId = new Integer(arr[1]);
        }catch (Exception e){
            return Result.error(Constant.ERROR_ASSIT_PLANNOTEXITST);
        }

        CallOutPlan callOutplan = assistCallService.getCallOutplan(bigIntegerId,orgId);

        if(callOutplan!=null){

            if(callOutplan.getAgentId()!=null){//已经转人工
                return Result.error(Constant.ERROR_ASSIT_TOAGENTED);
            }else{
                String server = callOutplan.getServerid();
                if(server!=null){
                    IAssistCall iAssistCall = FeignBuildUtil.feignBuilderTarget(IAssistCall.class, Constant.PROTOCOL + server);
                    return iAssistCall.assistToAgentAndCloseRobot(callId,agentGroupId);
                }else{
                    return Result.error(Constant.ERROR_ASSIT_SERVERNOTEXITST);
                }
            }
        }else{
            return Result.error(Constant.ERROR_ASSIT_PLANNOTEXITST);
        }

    }

}
