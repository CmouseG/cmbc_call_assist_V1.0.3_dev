package com.guiji.service;

import com.guiji.callcenter.dao.entity.Agent;
import com.guiji.entity.EUserState;
import com.guiji.toagentserver.entity.AgentMembrVO;
import com.guiji.web.request.AgentRequest;
import com.guiji.web.request.CrmUserVO;
import com.guiji.web.response.Paging;
import com.guiji.web.response.QueryAgent;
import com.guiji.web.response.QueryCalls;
import com.guiji.web.response.QueryUser;

import java.util.List;

/**
 * @Auther: 魏驰
 * @Date: 2018/12/17 14:32
 * @Project：ccserver
 * @Description:
 */
public interface AgentService {

    boolean updateAgent(String agentId, AgentRequest request,Long customerId)throws Exception;

    boolean agentState(AgentRequest request);

    QueryAgent getAgent(String userId);

    Paging getAllAgent(Long customerId,String crmLoginId,String queueId, Integer page, Integer size,int authLevel,String orgCode)throws Exception;

    QueryCalls agentcalls(String userId);

    QueryUser getUser(Long customerId);

    void update(Agent agent);

    boolean isAgentLogin(Long customerId)throws Exception;

    boolean agentVertoState(Agent agent);

    /**
     * 座席是否处于通话状态
     * @param agentId
     * @return
     */
    boolean isAgentBusy(String agentId);

    /**
     * 向座席发送消息，让其退出verto登录
     * @param agent
     */
    void alertToLogout(Agent agent);

    List<Agent> findByOrgCode(String orgCode);

    Agent findById(String agentId);

    void initCallcenter();

    boolean agentStateByVerto(EUserState eUserState, Agent agent);


    boolean syncAgentMembers( List<AgentMembrVO> agentMembers);

    boolean  delAgentMembers( List<Long> customerIds);

    Agent getAgentByCustomerId(Long customerId);

}
