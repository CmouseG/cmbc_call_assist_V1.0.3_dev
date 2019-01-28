package com.guiji.service.impl;

import com.guiji.callcenter.dao.AgentMapper;
import com.guiji.callcenter.dao.TierMapper;
import com.guiji.callcenter.dao.entity.Agent;
import com.guiji.callcenter.dao.entity.Tier;
import com.guiji.callcenter.dao.entity.TierExample;
import com.guiji.service.TierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: 魏驰
 * @Date: 2018/12/17 14:33
 * @Project：ccserver
 * @Description:
 */
@Service
public class TierServiceImpl implements TierService {
    @Autowired
    AgentMapper agentMapper;

    @Autowired
    TierMapper tierMapper;

    @Override
    public List<Agent> findAgentsByQueueId(Long queueId) {
        List<Agent> agentList = new ArrayList<>();
        TierExample tierExample = new TierExample();
        tierExample.createCriteria().andQueueIdEqualTo(queueId);
        List<Tier> tierList =tierMapper.selectByExample(tierExample);
        for(Tier tier: tierList){
            Agent agent = agentMapper.selectByPrimaryKey(tier.getUserId());
            agentList.add(agent);
        }
        return agentList;
    }
}
