package com.guiji.service;

import com.guiji.callcenter.dao.entity.Agent;

import java.util.List;

/**
 * @Auther: 魏驰
 * @Date: 2018/12/17 14:32
 * @Project：ccserver
 * @Description:
 */
public interface TierService {
    List<Agent> findAgentsByQueueId(Long queueId);
}
