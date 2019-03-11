package com.guiji.fs.module;

import com.guiji.fs.FreeSWITCH;
import com.guiji.fs.api.CallCenterApi;
import com.guiji.fs.pojo.Agent;
import com.guiji.fs.pojo.AgentStatus;
import com.guiji.fs.pojo.Queue;
import com.guiji.fs.pojo.Tier;
import com.guiji.fs.xmlconfig.CallCenterConfig;
import com.guiji.web.request.QueueInfo;
import com.guiji.web.request.TierInfo;
import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 呼叫中心模块
 */
@Slf4j
public class ModCallCenter {
    private FreeSWITCH freeSWITCH;
    private String configFilePath;
    private CallCenterConfig callCenterConfig;
    private CallCenterApi callCenterApi;
    public ModCallCenter(FreeSWITCH freeSWITCH){
        this.freeSWITCH = freeSWITCH;
        this.configFilePath =  freeSWITCH.getConfigPath()+"/callcenter.conf.xml";
        callCenterConfig = new CallCenterConfig(this.configFilePath);
        callCenterApi = new CallCenterApi(freeSWITCH);
    }

    /**
     * 增加座席
     * @param agent
     * @return
     */
    public boolean addAgent(Agent agent){
        //不再同步更新xml文件
        //Preconditions.checkArgument(!callCenterConfig.isAgentExist(agent.getAgentId()), "agent already exist in config");
        Preconditions.checkArgument(!callCenterApi.isAgentExist(agent.getAgentId()), "agent already exist in memory");
       // callCenterConfig.addAgent(agent);
        return callCenterApi.addAgent(agent);
    }

    /**
     * 修改坐席（针对配置文件）
     * @param agent
     */
    public void updateAgent(Agent agent){
        //callcenter_config agent set [key(contact|status|state|type|max_no_answer|wrap_up_time|ready_time|reject_delay_time|busy_delay_time)] [agent name] [value]
        //callcenter_config agent set status 9998 Available
        callCenterConfig.updateAgent(agent);
    }

    public void updateAgent(List<Agent> agents) {
        callCenterConfig.updateAgent(agents);
        freeSWITCH.reloadXml();
    }

    /**
     * 删除座席
     * @param agentId
     * @return
     */
    public boolean deleteAgent(String agentId){
       // callCenterConfig.deleteAgent(agentId);
        callCenterApi.deleteAgent(agentId);
        return true;
    }

    /**
     * 更新座席状态
     * @param agentId
     * @param status
     */
    public void updateAgentStatus(String agentId, AgentStatus status){
        callCenterApi.updateAgentStatus(agentId, status);
    }
    /**
     * 更新座席Contact
     * @param agentId
     * @param contact
     */
    public void updateAgentContact(String agentId, String contact){
        callCenterApi.updateAgentContact(agentId, contact);
    }


    /**
     * 获取指定坐席
     * @return
     */
    public Agent getAgent(String agentId){
        Agent rtAgent = callCenterApi.getAgent(agentId);
        return rtAgent;
    }

    public List<Agent> getAllAgents(){
        List<Agent> rtAgents = callCenterApi.getAllAgents();
        return rtAgents;
    }

    public boolean addTier(Tier tier){
       // callCenterConfig.addTier(tier);
        callCenterApi.addTier(tier);
//        callCenterApi.reloadTire(tier.getQueueId(),tier.getAgentId());
//        callCenterApi.reloadQueue(tier.getQueueId());
        return true;
    }

    public void updateTier(Tier tier){
        // callcenter_config tier set state 8007@default 9999 Ready
    }

    public boolean deleteTier(Tier tier){
     //   callCenterConfig.deleteTier(tier);
        callCenterApi.deleteTier(tier);
        return true;
    }

    public List<TierInfo> getAllTiers(){
        return callCenterConfig.getAllTiers();
    }

    /**
     * 判断queue是否存在
     * @param queueId
     * @return
     */
    public boolean isQueueExist(String queueId){
        return callCenterApi.isQueueExist(queueId);
    }

    /**
     * 新增queue
     * @param queue
     */
    public boolean addQueue(Queue queue){
        Preconditions.checkState(!callCenterConfig.isQueueExist(queue.getQueueId()), "queue already exist in config");
        Preconditions.checkState(!callCenterApi.isQueueExist(queue.getQueueId()), "queue already exist in memory");

        callCenterConfig.addQueue(queue);
        callCenterApi.loadQueue(queue.getQueueId());
        return isQueueExist(queue.getQueueId());
    }

    /**
     * 删除指定的queue
     * @param queueId
     */
    public boolean deleteQueue(String queueId){
        Preconditions.checkArgument(isQueueExist(queueId), "queueId not exist");
        //TODO：判断是否还有座席在queue中，如果在的话，则删除失败
        callCenterApi.unloadQueue(queueId);
        callCenterConfig.deleteQueue(queueId);
        return true;
    }

    //获取在用的所有队列
    public List<QueueInfo> getAllQueues(){
        return callCenterConfig.getAllQueues();
    }

    public void updateQueue(Queue queue){
        //先更新文件，然后再reloadxml，再callcenter queue reload xxx@default
    }

    public void updateQueue(List<Queue> queues) {
        callCenterConfig.updateQueue(queues);
        callCenterApi.reloadQueue(queues);
    }

    public void updateTier(List<Tier> tiers) {
        callCenterConfig.updateTier(tiers);
    }

    /**
     * 根据Waiting字符串来获取排队数
     * @param queueId
     * @return
     */
    public Integer getWaitCount(String queueId) {
        String data =callCenterApi.getWaitCount(queueId);
        int count = 0;
        int index = 0;
        while ((index = data.indexOf("Waiting", index)) != -1) {
            index = index + "Waiting".length();
            count++;
        }
        return count;
    }

    /**
     * 基于模板批量创建坐席、队列和绑定关系
     * @param agentList
     * @param queueList
     * @param tierList
     * @return
     */
    public boolean initCallcenter(List<Agent> agentList,List<Queue> queueList,List<Tier> tierList){
       return  callCenterConfig.initCallcenter(agentList,queueList,tierList);
    }

}
