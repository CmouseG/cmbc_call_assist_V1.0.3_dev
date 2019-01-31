package com.guiji.web.controller;

import com.guiji.callcenter.dao.entity.Agent;
import com.guiji.component.result.Result;
import com.guiji.entity.CustomSessionVar;
import com.guiji.fs.FsManager;
import com.guiji.service.QueueService;
import com.guiji.web.request.QueueInfo;
import com.guiji.web.response.Paging;
import com.guiji.web.response.QueryQueue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Slf4j
@RestController
@RequestMapping(value = "/rs")
public class QueueResource {
    @Autowired
    FsManager fsManager;
    @Autowired
    QueueService queueService;

    /**
     * 新增队列
     *
     * @param QueueInfo
     * @return
     */
    @RequestMapping(path = "/queues", method = RequestMethod.POST)
    public Result.ReturnData addQueue(@RequestBody QueueInfo QueueInfo, HttpSession session) {
        Agent agent = (Agent) session.getAttribute(CustomSessionVar.LOGIN_USER);
        log.info("收到创建队列请求QueueInfo:[{}]", QueueInfo.toString());
        try {
            queueService.addQueue(QueueInfo, agent);
        } catch (Exception e) {
            log.warn("创建队列出现异常", e);
            if(e.getMessage().equals("0307007")){
                return Result.error("0307007");
            }
        }
        return Result.ok();
    }

    /**
     * 修改队列
     *
     * @param queueId
     * @param request
     * @return
     */
    @RequestMapping(path = "/queues/{queueId}", method = RequestMethod.PUT)
    public Result.ReturnData updateQueue(@PathVariable String queueId, @RequestBody QueueInfo request, HttpSession session) {
        Agent agent = (Agent) session.getAttribute(CustomSessionVar.LOGIN_USER);
        log.info("收到更新队列请求queueId:[{}],QueueInfo:[{}]", queueId,request.toString());
        try {
            queueService.updateQueue(queueId, request, agent);
        } catch (Exception e) {
            log.warn("更新队列出现异常", e);
            if(e.getMessage().equals("0307007")){
                return Result.error("0307007");
            }
        }
        return Result.ok();
    }

    /**
     * 删除队列
     *
     * @param queueId
     * @return
     */
    @RequestMapping(path = "/queues/{queueId}", method = RequestMethod.DELETE)
    public Result.ReturnData deleteQueue(@PathVariable String queueId, HttpSession session) {
        log.info("收到删除队列请求queueId:[{}]", queueId);
        queueService.deleteQueue(queueId);
        return Result.ok();
    }

    /**
     * 获取指定座席组(根据座席组id)
     *
     * @param queueId
     * @return
     */
    @RequestMapping(path = "/queues/{queueId}", method = RequestMethod.GET)
    public Result.ReturnData<QueryQueue> getQueue(@PathVariable String queueId) {
        log.info("收到获取队列请求queueId:[{}]", queueId);
        QueryQueue queryQueue = queueService.getQueue(queueId);
        return Result.ok(queryQueue);
    }

    /**
     * 查询坐席组列表
     *
     * @return
     */
    @RequestMapping(path = "/queues", method = RequestMethod.GET)
    public Result.ReturnData<Paging> queryQueues(HttpSession session,
                                   @RequestParam(value = "queueName", defaultValue = "") String queueName,
                                   @RequestParam(value = "pageNo", defaultValue = "0") Integer page,
                                   @RequestParam(value = "pageSize", defaultValue = "10") Integer size) {
        log.info("收到查询坐席组列表请求queueName:[{}],pageNo:[{}],pageSize:[{}]",queueName,page,size);
        Agent agent = (Agent) session.getAttribute(CustomSessionVar.LOGIN_USER);
        Paging paging = queueService.queryQueues(agent,queueName,page,size);
        return Result.ok(paging);
    }
}
