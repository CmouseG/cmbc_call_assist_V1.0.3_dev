package com.guiji.web.controller;

import com.guiji.callcenter.dao.entity.Agent;
import com.guiji.callcenter.dao.entity.Queue;
import com.guiji.component.result.Result;
import com.guiji.config.ErrorConstant;
import com.guiji.entity.CustomSessionVar;
import com.guiji.manager.AuthManager;
import com.guiji.service.AgentService;
import com.guiji.service.QueueService;
import com.guiji.web.request.AgentRequest;
import com.guiji.web.response.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Slf4j
@RestController
@RequestMapping(value = "/rs")
public class AgentResource {
    @Autowired
    AgentService agentService;

    @Autowired
    QueueService queueService;

    @Autowired
    AuthManager authManager;


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
     * 获取所有的坐席
     * @return
     */
    @RequestMapping(path = "/agents", method = RequestMethod.GET)
    public Result.ReturnData<Paging> getAllAgent(HttpSession session,
                                   @RequestParam(value = "crmLoginId", defaultValue = "") String crmLoginId,
                                   @RequestParam(value = "queueId", defaultValue = "") String queueId,
                                   @RequestParam(value = "pageNo", defaultValue = "0") Integer page,
                                   @RequestParam(value = "pageSize", defaultValue = "10") Integer size){
        log.info("收到获取所有座席请求queueId:[{}],pageNo:[{}],pageSize:[{}]",queueId,page,size);
        Agent agent = (Agent) session.getAttribute(CustomSessionVar.LOGIN_USER);
        Paging paging = agentService.getAllAgent(agent,crmLoginId,queueId,page,size);
        return Result.ok(paging);
    }

    /**
     * 根据token获取指定的坐席
     * @return
     */
    @RequestMapping(path = "/user", method = RequestMethod.GET)
    public Result.ReturnData<QueryUser> getUser(HttpSession session){
        Agent user = (Agent) session.getAttribute(CustomSessionVar.LOGIN_USER);
        log.info("收到根据token获取指定的坐席请求token:[{}]",user.getUserId());
        QueryUser agent = agentService.getUser(user);
        return Result.ok(agent);
    }

    /**
     * 创建坐席的接口
     * @param request
     * @return
     */
    @RequestMapping(path = "/createAgent", method = RequestMethod.POST)
    public Result.ReturnData createAgent(@RequestBody AgentRequest request, HttpSession session){
        log.info("AgentRequest:[{}]", request.toString());
        Agent agent = (Agent) session.getAttribute(CustomSessionVar.LOGIN_USER);
        String crmUserid = (String) session.getAttribute(CustomSessionVar.CRM_USERID);
        Queue queue = queueService.findByQueueId(request.getQueueId());
        if(queue == null){
            return Result.error("0307001");
        }

        if(authManager.isUserNameExist(request.getCrmLoginId())){
            return Result.error("0307002");
        }

        try {
            agentService.createAgent(request,agent,Long.parseLong(crmUserid));
        } catch (Exception e) {
            if(e.getMessage().equals("0307002")) {
                return Result.error("0307002");
            }
        }
        return Result.ok();
    }

    /**
     * 修改坐席
     * @param userId
     * @param request
     * @return
     */
    @RequestMapping(path = "/agents/{userId}", method = RequestMethod.PUT)
    public Result.ReturnData updateAgent(@PathVariable String userId, @RequestBody AgentRequest request, HttpSession session){
        log.info("收到更新座席请求userId:[{}],AgentRequest:[{}]", userId,request.toString());
        Agent agent = (Agent) session.getAttribute(CustomSessionVar.LOGIN_USER);
        try {
            agentService.updateAgent(userId,request, agent);
        } catch (Exception e) {
            log.warn("更新座席出现异常", e);
            if(e.getMessage().equals("0307011")){
                return Result.error("0307011");
            }else if(e.getMessage().equals("0307006")){
                return Result.error("0307006");
            }else if(e.getMessage().equals("0307005")){
                return Result.error("0307005");
            }
        }
        return Result.ok();
    }

    /**
     * 删除坐席
     * @param userId
     * @return
     */
    @RequestMapping(path = "/agents/{userId}", method = RequestMethod.DELETE)
    public Result.ReturnData deleteAgent(@PathVariable String userId){
        log.info("收到删除座席请求userId:[{}]", userId);
        agentService.deleteAgent(userId);
        return Result.ok();
    }

    /**
     * 设置坐席状态
     * @param request
     * @return
     */
    @RequestMapping(path = "/agentstate", method = RequestMethod.POST)
    public Result.ReturnData agentState(@RequestBody AgentRequest request, HttpSession session){
        log.info("收到设置坐席状态请求AgentRequest:[{}]", request.toString());
        Agent agent = (Agent) session.getAttribute(CustomSessionVar.LOGIN_USER);
        try {
            agentService.agentState(request, agent);
        } catch (Exception e) {
            e.printStackTrace();
            log.warn("设置坐席状态出现异常", e);
            if(e.getMessage().equals("0307005")){
                return Result.error("0307005");
            }
        }
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
     * 根据token获取指定的坐席是否已经登录过
     * @return
     */
    @RequestMapping(path = "/prelogin", method = RequestMethod.GET)
    public Result.ReturnData vertoStatus(HttpSession session){
        Agent agent = (Agent) session.getAttribute(CustomSessionVar.LOGIN_USER);
        log.info("收到座席[{}]的预登陆接口", agent.getUserId());

        //检查该座席是否已登录
        boolean result = agentService.isAgentLogin(agent);
        log.info("该座席[{}]登录状态为[{}]，允许登录", agent.getUserId(), result);

        return Result.ok();
    }
}
