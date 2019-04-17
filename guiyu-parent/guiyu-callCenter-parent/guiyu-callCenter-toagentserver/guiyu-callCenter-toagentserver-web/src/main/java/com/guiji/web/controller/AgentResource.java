package com.guiji.web.controller;

import com.guiji.component.jurisdiction.Jurisdiction;
import com.guiji.component.result.Result;
import com.guiji.service.AgentService;
import com.guiji.service.QueueService;
import com.guiji.web.request.AgentRequest;
import com.guiji.web.response.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/rs")
public class AgentResource {
    @Autowired
    AgentService agentService;

    @Autowired
    QueueService queueService;

    /**
     * 获取指定的坐席
     * @param userId
     * @return
     */
    @RequestMapping(path = "/agents/{userId}", method = RequestMethod.GET)
    public Result.ReturnData<QueryAgent> getAgent(@PathVariable String userId){
        log.info("收到获取座席请求userId:[{}]", userId);
        QueryAgent agent = agentService.getAgent(userId);
        return Result.ok(agent);
    }

    /**
     * 分页查询坐席
     * @return
     */
    @Jurisdiction("callCenter_agentStaffManage_defquery")
    @RequestMapping(path = "/agents", method = RequestMethod.GET)
    public Result.ReturnData<Paging> getAllAgent(@RequestHeader int authLevel,@RequestHeader Long userId,@RequestHeader String orgCode,
                                   @RequestParam(value = "crmLoginId", defaultValue = "") String crmLoginId,
                                   @RequestParam(value = "queueId", defaultValue = "") String queueId,
                                   @RequestParam(value = "pageNo", defaultValue = "0") Integer page,
                                   @RequestParam(value = "pageSize", defaultValue = "10") Integer size){
        log.info("收到分页查询坐席请求queueId:[{}],pageNo:[{}],pageSize:[{}]",queueId,page,size);
        Paging paging = null;
        try {
            paging = agentService.getAllAgent(userId,crmLoginId,queueId,page,size,authLevel,orgCode);
        } catch (Exception e) {
            log.warn("分页查询坐席出现异常", e);
            if(e.getMessage().equals("0307014")){
                return Result.error("0307014");
            }
        }
        return Result.ok(paging);
    }

    /**
     * 根据customerId获取指定的坐席
     * @return
     */
    @RequestMapping(path = "/user", method = RequestMethod.GET)
    public Result.ReturnData<QueryUser> getUser(@RequestHeader Long userId,@RequestHeader String orgCode){
        log.info("收到根据customerId获取指定的坐席的请求customerId:[{}],orgCode:[{}]",userId,orgCode);
        if(orgCode.equals("1")){
            return Result.ok(null);
        }
        QueryUser agent = agentService.getUser(userId);
        return Result.ok(agent);
    }

    /**
     * 修改坐席
     * @param userId
     * @param request
     * @return
     */
    @Jurisdiction("callCenter_agentStaffManage_edit,callCenter_workPlatform_edit")
    @RequestMapping(path = "/agents/{agentId}", method = RequestMethod.PUT)
    public Result.ReturnData updateAgent(@PathVariable String agentId, @RequestBody AgentRequest request,
                                         @RequestHeader Long userId){
        log.info("收到更新座席请求userId:[{}],AgentRequest:[{}]", userId,request.toString());
        try {
            agentService.updateAgent(agentId,request,userId);
        } catch (Exception e) {
            log.warn("更新座席出现异常", e);
            if(e.getMessage().equals("0307011")){
                return Result.error("0307011");
            }else if(e.getMessage().equals("0307006")){
                return Result.error("0307006");
            }else if(e.getMessage().equals("0307005")){
                return Result.error("0307005");
            }else if(e.getMessage().equals("0307014")){
                return Result.error("0307014");
            }
        }
        return Result.ok();
    }

    /**
     * 设置坐席状态
     * @param request
     * @return
     */
    @RequestMapping(path = "/agentstate", method = RequestMethod.POST)
    public Result.ReturnData agentState(@RequestBody AgentRequest request){
        log.info("收到设置坐席状态请求AgentRequest:[{}]", request.toString());
        agentService.agentState(request);
        return Result.ok();
    }

    /**
     * 获取座席通话信息
     * @param userId
     * @return
     */
    @RequestMapping(path = "/agentcalls/{userId}", method = RequestMethod.GET)
    public Result.ReturnData<QueryCalls> agentcalls(@PathVariable String userId){
        log.info("收到获取座席通话信息请求userId:[{}]",userId);
        QueryCalls queryCalls = agentService.agentcalls(userId);
        return Result.ok(queryCalls);
    }

    /**
     * 根据header中的userId获取指定的坐席是否已经登录过
     * @return
     */
    @RequestMapping(path = "/prelogin", method = RequestMethod.GET)
    public Result.ReturnData vertoStatus(@RequestHeader Long userId){
        log.info("收到座席[{}]的预登陆接口", userId);
        //检查该座席是否已登录
        boolean result = false;
        try {
            result = agentService.isAgentLogin(userId);
        } catch (Exception e) {
            log.warn("预登陆接口查询出现异常", e);
            if(e.getMessage().equals("0307014")){
                return Result.error("0307014");
            }
        }
        log.info("该座席[{}]登录状态为[{}]，允许登录", userId, result);
        return Result.ok();
    }
}
