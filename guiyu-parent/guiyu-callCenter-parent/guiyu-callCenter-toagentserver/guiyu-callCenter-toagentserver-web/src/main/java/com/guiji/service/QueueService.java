package com.guiji.service;

import com.guiji.callcenter.dao.entity.Agent;
import com.guiji.callcenter.dao.entity.Queue;
import com.guiji.web.request.QueueInfo;
import com.guiji.web.response.Paging;
import com.guiji.web.response.QueryQueue;

import java.util.List;

/**
 * @Auther: 魏驰
 * @Date: 2018/12/17 14:32
 * @Project：ccserver
 * @Description:
 */
public interface QueueService {
    boolean addQueue(QueueInfo QueueInfo, Agent agent) throws Exception;
    boolean deleteQueue(String queueId);
    void updateQueue(String queueId, QueueInfo QueueInfo, Agent agent)throws Exception;

    Paging queryQueues(Agent agent, String queueName, Integer page, Integer size,String systemUserId);
    QueryQueue getQueue(String queueId);

    Queue findByQueueId(Long queueId);

    List<Queue> findByOrgCode(String orgCode);

    void untyingLineinfos(String lineId);
}
