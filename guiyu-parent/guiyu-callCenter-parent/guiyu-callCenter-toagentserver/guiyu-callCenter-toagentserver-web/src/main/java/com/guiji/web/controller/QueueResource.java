package com.guiji.web.controller;

import com.guiji.component.jurisdiction.Jurisdiction;
import com.guiji.component.result.Result;
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
    @Jurisdiction("callCenter_agentGroupManage_add")
    @RequestMapping(path = "/queues", method = RequestMethod.POST)
    public Result.ReturnData addQueue(@RequestBody QueueInfo QueueInfo,@RequestHeader Long userId,@RequestHeader String orgCode) {
        log.info("收到创建队列请求QueueInfo:[{}],userId:[{}],orgCode[{}]", QueueInfo.toString(),userId,orgCode);
        try {
            queueService.addQueue(QueueInfo,orgCode,userId);
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
    @Jurisdiction("callCenter_agentGroupManage_edit")
    @RequestMapping(path = "/queues/{queueId}", method = RequestMethod.PUT)
    public Result.ReturnData updateQueue(@PathVariable String queueId, @RequestBody QueueInfo request,
                                         @RequestHeader Long userId,@RequestHeader String orgCode) {
        log.info("收到更新队列请求queueId:[{}],QueueInfo:[{}],userId:[{}],orgCode[{}]", queueId,request.toString(),userId,orgCode);
        try {
            queueService.updateQueue(queueId, request, userId,orgCode);
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
    @Jurisdiction("callCenter_agentGroupManage_delete")
    @RequestMapping(path = "/queues/{queueId}", method = RequestMethod.DELETE)
    public Result.ReturnData deleteQueue(@PathVariable String queueId) {
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
    @Jurisdiction("callCenter_agentGroupManage_defquery")
    @RequestMapping(path = "/queues", method = RequestMethod.GET)
    public Result.ReturnData<Paging> queryQueues(
            @RequestParam(value = "systemUserId", defaultValue = "") String systemUserId,
            @RequestParam(value = "queueName", defaultValue = "") String queueName,
            @RequestParam(value = "pageNo", defaultValue = "0") Integer page,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer size,
            @RequestHeader int authLevel,
            @RequestHeader Long userId,
            @RequestHeader String orgCode) {
        log.info("收到查询坐席组列表请求queueName:[{}],pageNo:[{}],pageSize:[{}],authLevel:[{}],userId:[{}],orgCode[{}]",queueName,page,size,authLevel,userId,orgCode);
        Paging paging = null;
        try {
            paging = queueService.queryQueues(queueName,page,size,systemUserId,orgCode,authLevel,userId);
        } catch (Exception e) {
            log.warn("查询坐席组列表出现异常", e);
            if(e.getMessage().equals("0307014")){
                return Result.error("0307014");
            }
        }
        return Result.ok(paging);
    }
}
