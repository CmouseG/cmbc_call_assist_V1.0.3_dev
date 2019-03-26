package com.guiji.fs.api;

import com.guiji.fs.FreeSWITCH;
import com.guiji.fs.pojo.Agent;
import com.guiji.fs.pojo.AgentStatus;
import com.guiji.fs.pojo.Queue;
import com.guiji.fs.pojo.Tier;
import com.guiji.fs.util.AgentUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class CallCenterApi {
    private FreeSWITCH freeSWITCH;

    public CallCenterApi(FreeSWITCH freeSWITCH){
        this.freeSWITCH = freeSWITCH;
    }

    /**
     * 从配置文件中加载指定的queue
     * @param queueId
     */
    public void loadQueue(String queueId){
        freeSWITCH.reloadXml();
        String cmd = String.format("callcenter_config queue load %s", queueId);
        freeSWITCH.execute(cmd);
    }

    /**
     * 从配置文件中重新加载指定的queue
     * @param queueId
     */
    public void reloadQueue(String queueId){
        freeSWITCH.reloadXml();
        String cmd = String.format("callcenter_config queue reload %s", queueId);
        freeSWITCH.execute(cmd);
    }


    /**
     * 从配置文件中重新加载queue
     * @param queues
     */
    public void reloadQueue(List<Queue> queues) {
        for(Queue queue: queues){
            reloadQueue(queue.getQueueId());
        }
    }

    /**
     * 卸载指定的queue
     * @param queueId
     */
    public void unloadQueue(String queueId){
        String cmd = String.format("callcenter_config queue unload %s", queueId);
        freeSWITCH.execute(cmd);
    }

    /**
     * 判断座席是否存在
     * @param agentId
     * @return
     */
    public boolean isAgentExist(String agentId){
        String cmd = String.format("callcenter_config agent list %s", agentId);
        String result = freeSWITCH.execute(cmd);
        if(result!=null && result.contains(agentId)){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 判断queue是否存在
     * @param queueId
     * @return
     */
    public boolean isQueueExist(String queueId){
        String result = freeSWITCH.execute("callcenter_config queue list");
        if(result!=null && result.contains(queueId)){
            return true;
        }else{
            return false;
        }
    }

    public void addTier(Tier tier){
        //如果在配置文件中增加，需要先reloadxml，再reload对应的queue
        String cmd = String.format("callcenter_config tier add %s %s 1 1",  tier.getQueueId(), tier.getAgentId());
        freeSWITCH.execute(cmd);
    }

    public void deleteTier(Tier tier){
        String cmd = String.format("callcenter_config tier del %s %s", tier.getQueueId(), tier.getAgentId());
        freeSWITCH.execute(cmd);
    }

    public void deleteAgent(String agentId){
        String cmd = String.format("callcenter_config agent del %s", agentId);
        freeSWITCH.execute(cmd);
    }

    /**
     * 增加座席
     * @param agent
     * @return
     */
    public boolean addAgent(Agent agent){
        //修改配置文件，不会立刻通过命令看到坐席。
        //只能坐席被分配了队列，队列被重载的时候，才能看到新增加的坐席
        String cmd1 = String.format("callcenter_config agent add %s callback", agent.getAgentId());
        String cmd2 = String.format("callcenter_config agent set contact %s %s", agent.getAgentId(), agent.getContact());
        String cmd3 = String.format("callcenter_config agent set status %s Logged Out", agent.getAgentId());
        String cmd4 = String.format("callcenter_config agent set max_no_answer %s 999999", agent.getAgentId());
        String cmd5 = String.format("callcenter_config agent set wrap_up_time %s 10", agent.getAgentId());
        String cmd6 = String.format("callcenter_config agent set reject_delay_time %s 10", agent.getAgentId());
        String cmd7 = String.format("callcenter_config agent set busy_delay_time %s 60", agent.getAgentId());
        String cmd8 = String.format("callcenter_config agent set no_answer_delay_time %s 10", agent.getAgentId());

        freeSWITCH.execute(cmd1);
        freeSWITCH.execute(cmd2);
        freeSWITCH.execute(cmd3);
        freeSWITCH.execute(cmd4);
        freeSWITCH.execute(cmd5);
        freeSWITCH.execute(cmd6);
        freeSWITCH.execute(cmd7);
        freeSWITCH.execute(cmd8);
        return true;
    }

    /**
     * 获取指定座席
     * @param agentId
     * @return
     */
    public Agent getAgent(String agentId){
        String cmd = String.format("callcenter_config agent list %s", AgentUtil.buildAgentId(agentId));
        List<String> strings = freeSWITCH.api(cmd);
        if(strings.size()>1){
            return buildAgent(strings.get(1));
        }

        return null;
    }

    /**
     * 获取所有座席
     * @return
     */
    public List<Agent> getAllAgents(){
        List<Agent> agentList = new ArrayList<>();

        String cmd = "callcenter_config agent list";
        List<String> strings = freeSWITCH.api(cmd);
        if(strings.size()>1){
            for(int i=1;i<strings.size() -1;i++){
                Agent agent = buildAgent(strings.get(i));
                agentList.add(agent);
            }
        }

        return agentList;
    }

    /**
     * 根据命令结果，构建座席对象
     * @param result
     * @return
     */
    private Agent buildAgent(String result) {
        String[] resultArray = result.split("\\|");
        Agent agent = new Agent();

        agent.setAgentId(AgentUtil.unbuildAgentId(resultArray[0]));
        agent.setContact(resultArray[4]);
        agent.setStatus(AgentStatus.getAgentStatus(resultArray[5]));
        agent.setState(resultArray[6]);
        agent.setMax_no_answer(resultArray[7]);
        agent.setWrap_up_time(resultArray[8]);
        agent.setReject_delay_time(resultArray[9]);
        agent.setBusy_delay_time(resultArray[10]);
        agent.setNo_answer_delay_time(resultArray[11]);
        agent.setLast_bridge_start(resultArray[12]);
        agent.setLast_bridge_end(resultArray[13]);
        agent.setLast_offered_call(resultArray[14]);
        agent.setLast_status_change(resultArray[15]);
        agent.setNo_answer_count(resultArray[16]);
        agent.setCalls_answered(resultArray[17]);
        agent.setTalk_time(resultArray[18]);
        agent.setReady_time(resultArray[19]);

        return agent;
    }


    /**
     * 设置座席状态
     * @param agentId
     * @param status
     */
    public void updateAgentStatus(String agentId, AgentStatus status) {
        String cmd = String.format("callcenter_config agent set status %s '%s'", agentId, status.getStrStatus());
        freeSWITCH.execute(cmd);
    }


    public void reloadAgent(String agentId){
        String cmd = String.format("callcenter_config agent reload %s", agentId);
        freeSWITCH.execute(cmd);
    }


    public void reloadTire(String queueId ,String agentId){
        String cmd = String.format("callcenter_config tier reload %s %s",queueId, agentId);
        freeSWITCH.execute(cmd);
    }


    /**
     * 设置座席状态
     * @param agentId
     * @param contact
     */
    public void updateAgentContact(String agentId, String contact) {
        String cmd = String.format("callcenter_config agent set contact %s '%s'", agentId, contact);
        freeSWITCH.execute(cmd);
    }

    /**
     * 获取队列排队数
     * @param queueId
     */
    public String getWaitCount(String queueId) {
        String cmd = String.format("callcenter_config queue list members %s", queueId);
        return  freeSWITCH.execute(cmd);
    }
}
