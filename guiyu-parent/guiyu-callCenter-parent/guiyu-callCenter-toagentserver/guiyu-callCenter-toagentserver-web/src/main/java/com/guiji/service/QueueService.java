package com.guiji.service;

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
    boolean addQueue(QueueInfo QueueInfo,String orgCodet,Long customerId) throws Exception;
    boolean deleteQueue(String queueId);
    void updateQueue(String queueId, QueueInfo QueueInfo, Long customerId,String orgCode)throws Exception;

    Paging queryQueues( String queueName, Integer page, Integer size,String systemUserId,String orgCode,int authLevel,Long customerId)throws Exception;
    QueryQueue getQueue(String queueId);

    Queue findByQueueId(Long queueId);

    List<Queue> findByOrgCode(String orgCode);

    void untyingLineinfos(String lineId);

   void  switchLineinfos( String lineId);
}
