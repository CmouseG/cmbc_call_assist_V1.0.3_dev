package com.guiji.fs.xmlconfig;

import com.guiji.fs.pojo.*;
import com.guiji.fs.util.AgentUtil;
import com.guiji.fs.util.QueueUtil;
import com.guiji.fs.xmlpojo.XAgent;
import com.guiji.fs.xmlpojo.XCallCenter;
import com.guiji.fs.xmlpojo.XQueue;
import com.guiji.fs.xmlpojo.XTier;
import com.guiji.util.ClassPathResourceReader;
import com.guiji.util.CommonUtil;
import com.guiji.web.request.QueueInfo;
import com.guiji.web.request.TierInfo;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class CallCenterConfig {
    private XCallCenter callCenterConfig;
    //队列模板文件，用于生产新的队列
    @Getter
    private XQueue templateQueue;
    //座席模板文件，用于产生新的座席
    private XAgent templateAgent;

    private File configFile;

    private InputStream init;

    public CallCenterConfig(String confFilePath){
        configFile = new File(confFilePath);
        callCenterConfig = CommonUtil.xmlToBean(configFile, XCallCenter.class);

        String templateContent = new ClassPathResourceReader("fstemplate/queue.xml").getContent();
        templateQueue = CommonUtil.xmlToBean(templateContent, XQueue.class);

        String strAgent = new ClassPathResourceReader("fstemplate/agent.xml").getContent();
        templateAgent = CommonUtil.xmlToBean(strAgent, XAgent.class);
    }

    /**
     * 增加座席
     * @param agent
     * @return
     */
    public boolean addAgent(Agent agent){
        reloadConfig();
        Preconditions.checkState(!isAgentExist(agent.getAgentId()), "agent exist");
        XAgent xa = new XAgent();
        BeanUtils.copyProperties(templateAgent, xa);
        xa.setName(agent.getAgentId());
        xa.setContact(getAgentContact(agent));
        xa.setMax_no_answer("999999");
        callCenterConfig.getAgents().getAgent().add(xa);
        flushConfig();
        return true;
    }

    /**
     * 删除座席
     * @param agentId
     * @return
     */
    public boolean deleteAgent(String agentId){
        reloadConfig();

        int key = -1;
        List<XAgent> agentList = callCenterConfig.getAgents().getAgent();
        for(int i=0;i<agentList.size();i++){
            if(agentList.get(i).getName().equals(agentId)){
                key = i;
                break;
            }
        }

        if(key>=0){
            log.info("删除成功，找到座席[{}]", agentId);
            agentList.remove(key);
            flushConfig();
            return true;
        }else{
            log.info("删除失败，未找到座席[{}]", agentId);
            return false;
        }
    }

    /**
     * 获取指定agent
     * @param agentId
     * @return
     */
    public Agent getAgent(String agentId){
        reloadConfig();
        List<XAgent> xAgents = callCenterConfig.getAgents().getAgent();
        for(XAgent xa: xAgents){
            if(xa.getName().equals(agentId)){
                return buildAgent(xa);
            }
        }

        return null;
    }

    /**
     * 获取所有座席
     * @return
     */
    public List<Agent> getAllAgents(){
        reloadConfig();
        List<XAgent> xAgents = callCenterConfig.getAgents().getAgent();
        List<Agent> agentList = new ArrayList<>(xAgents.size());

        for(XAgent xa: xAgents){
            Agent agent = buildAgent(xa);
            agentList.add(agent);
        }

        return agentList;
   }

    /**
     * 构建座席
     * @param xa
     * @return
     */
    private Agent buildAgent(XAgent xa) {
        Agent agent = new Agent();
        agent.setAgentId(AgentUtil.unbuildAgentId(xa.getName()));

        if(xa.getContact().contains("verto")){
            agent.setAgentType(AgentType.Verto);
        }

        agent.setStatus(AgentStatus.getAgentStatus(xa.getStatus()));
        agent.setContact(xa.getContact());

        return agent;
    }

    /**
     * 基于模板创建一个新的queue
     * @param queue
     * @return
     */
    public boolean addQueue(Queue queue){
        reloadConfig();
        XQueue xq = new XQueue();
        BeanUtils.copyProperties(templateQueue, xq);
        xq.setName(queue.getQueueId());

        callCenterConfig.getQueues().getQueue().add(xq);
        flushConfig();
        return true;
    }

    /**
     * 判断座席是否存在
     * @param agentId
     * @return
     */
    public boolean isAgentExist(String agentId) {
        reloadConfig();

        List<XAgent> agentList = callCenterConfig.getAgents().getAgent();
        for(XAgent xa: agentList){
            if(xa.getName().equals(agentId)){
                return true;
            }
        }

        return false;
    }

    /**
     * 判断queue是否存在
     * @param queueId
     * @return
     */
    public boolean isQueueExist(String queueId){
        reloadConfig();

        List<XQueue> queueList = callCenterConfig.getQueues().getQueue();
        for(XQueue queue: queueList){
            if(queue.getName().equals(queueId)){
                return true;
            }
        }

        return false;
    }

    public boolean isTierExist(Tier tier){
        reloadConfig();

        List<XTier> tierList = callCenterConfig.getTiers().getTier();
        for(XTier xtier: tierList){
            if(xtier.getAgent().equals(tier.getCCAgentId()) && xtier.getQueue().equals(tier.getCCQueueId())) {
                return true;
            }
        }

        return false;
    }


    /**
     * 删除指定的队列
     * @param queueId
     */
    public void deleteQueue(String queueId){
        reloadConfig();
        List<XQueue> queues = callCenterConfig.getQueues().getQueue();
        int key = -1;
        for(int i=0;i<queues.size();i++){
            if(queues.get(i).getName().equals(queueId)){
                key = i;
                break;
            }
        }

        if(key >= 0){
            queues.remove(key);
        }

        flushConfig();
    }

    /**
     * 获取所有队列
     * @return
     */
    public List<QueueInfo> getAllQueues() {
        List<QueueInfo> queueList = new ArrayList<>();

        reloadConfig();
        List<XQueue> xQueues = callCenterConfig.getQueues().getQueue();
        for(XQueue queue: xQueues){
            QueueInfo queueInfo = new QueueInfo();
            //queueInfo.setQueueId(QueueUtil.unbuildQueueId(queue.getName()));
            queueList.add(queueInfo);
        }

        return queueList;
    }


    /**
     * 获取所有的Tier
     * @return
     */
    public List<TierInfo> getAllTiers() {
        List<TierInfo> tierList = new ArrayList<>();

        reloadConfig();
        List<XTier> xQueues = callCenterConfig.getTiers().getTier();
        for(XTier tier: xQueues){
            TierInfo tierInfo = new TierInfo();
            tierInfo.setQueueId(QueueUtil.unbuildQueueId(tier.getQueue()));
            tierInfo.setAgentId(AgentUtil.unbuildAgentId(tier.getAgent()));

            tierList.add(tierInfo);
        }

        return tierList;
    }

    public boolean addTier(Tier tier){
        if(isTierExist(tier)){
            log.info("队列绑定已经存在，跳过处理");
            return true;
        }

        reloadConfig();

        XTier xTier = new XTier();
        xTier.setAgent(tier.getCCAgentId());
        xTier.setQueue(tier.getCCQueueId());

        callCenterConfig.getTiers().getTier().add(xTier);
        flushConfig();
        return true;
    }

    public boolean deleteTier(Tier tier){
        List<XTier> xTiers = callCenterConfig.getTiers().getTier();
        int key = -1;
        XTier xTier;
        for(int i=0;i<xTiers.size();i++){
            xTier = xTiers.get(i);
            if(xTier.getQueue().equals(tier.getCCQueueId()) && xTier.getAgent().equals(tier.getCCAgentId())){
                key = i;
                break;
            }
        }

        if(key>=0){
            log.warn("找到指定的tier[{}], key[{}],准备删除", tier, key);
            xTiers.remove(key);
            flushConfig();
            return true;
        }else{
            log.warn("删除失败，tier[{}]不存在", tier);
            return false;
        }
    }

    /**
     * 获取座席contact信息
     * @param agent
     * @return
     */
    private String getAgentContact(Agent agent){
        if(agent.getAgentType() == AgentType.Verto){
            return String.format("${verto_contact(%s)}", agent.getAgentId());
        }else{
            return String.format("loopback/%s", agent.getMobile());
        }
    }

    /**
     * 将配置回写到配置文件中
     */
    public void flushConfig(){
        CommonUtil.beanToXML(callCenterConfig, configFile);
    }

    /**
     * 从配置文件中加载配置
     */
    public void reloadConfig(){
        callCenterConfig = CommonUtil.xmlToBean(configFile,XCallCenter.class);
    }

    /**
     * 更新座席
     * @param agent
     * @param isFlush  修改完成后，是否立刻提交到文件中
     */
    private void updateAgent(Agent agent, boolean isFlush){
        if(!isAgentExist(agent.getAgentId())){
            log.info("更新座席，座席[{}]不存在，需要新建", agent);
            addAgent(agent);
            return;
        }

        List<XAgent> agentList = callCenterConfig.getAgents().getAgent();
        for(XAgent xa: agentList){
            if(xa.getName().equals(AgentUtil.buildAgentId(agent.getAgentId()))){
                if(agent.getStatus()!=null){
                    xa.setStatus(agent.getStatus().getStrStatus());
                }

                if(!Strings.isNullOrEmpty(agent.getContact())){
                    xa.setContact(agent.getContact());
                }
            }
        }

        if(isFlush){
            flushConfig();
        }
    }

    /**
     * 更新座席
     * @param agent
     */
    public void updateAgent(Agent agent){
        reloadConfig();
        updateAgent(agent, true);
    }

    /**
     * 更新座席
     * @param agents
     */
    public void updateAgent(List<Agent> agents) {
        reloadConfig();
        for(Agent agent: agents){
            updateAgent(agent, false);
        }

        flushConfig();
    }

    /**
     * 更新绑定
     * @param tier
     * @param isFlush 是否立刻回写到配置文件中
     */
    private void updateTier(Tier tier, Boolean isFlush){
        if(!isTierExist(tier)){
            log.info("更新Tier，Tier[{}]不存在，需要新建", tier);
            addTier(tier);
            return;
        }

        List<XTier> xTierList = callCenterConfig.getTiers().getTier();
        for(XTier xTier: xTierList){
            if(xTier.getQueue().equals(tier.getCCQueueId()) && xTier.getAgent().equals(tier.getCCAgentId())){
                if(!Strings.isNullOrEmpty(tier.getLevel())){
                    xTier.setLevel(tier.getLevel());
                }
                if(!Strings.isNullOrEmpty(tier.getPosition())){
                    xTier.setPosition(tier.getPosition());
                }
                break;
            }
        }

        if(isFlush){
            flushConfig();
        }
    }

    public void updateTier(Tier tier){
        updateTier(tier, true);
    }

    public void updateTier(List<Tier> tiers) {
        reloadConfig();
        for(Tier tier: tiers){
            updateTier(tier, false);
        }

        flushConfig();
    }

    private void updateQueue(Queue queue, boolean isFlush){
        if(!isQueueExist(queue.getQueueId())){
            log.info("更新queue，queue[{}]不存在，需要新建", queue);
            addQueue(queue);
            return;
        }

        List<XQueue> xQueues = callCenterConfig.getQueues().getQueue();
        for(XQueue xq: xQueues){
            if(xq.getName().equals(QueueUtil.buildQueueId(queue.getQueueId()))){
                //TODO:需要更新队列的其他属性，
                //时间较赶，暂时忽略
                break;
            }
        }

        if(isFlush){
            flushConfig();
        }
    }

    public void updateQueue(Queue queue){
        updateQueue(queue, true);
    }

    public void updateQueue(List<Queue> queues) {
        reloadConfig();
        for(Queue queue: queues){
            updateQueue(queue, false);
        }

        flushConfig();
    }

    /**
     * 将配置回写到配置文件中
     */
    public void flushConfig1(){
        CommonUtil.beanToXML(callCenterConfig, configFile);
    }

    /**
     * 从配置文件中加载配置
     */
    public void reloadConfig1(){
        init = getClass().getResourceAsStream("/resources/fstemplate/callcenter.conf.xml");
        if (init == null) {
            init = getClass().getClassLoader().getResourceAsStream("fstemplate/callcenter.conf.xml");
        }
        callCenterConfig = CommonUtil.toObj(XCallCenter.class,init);

    }

    /**
     * 基于模板批量创建坐席、队列和绑定关系
     * @param agentList
     * @param queueList
     * @param tierList
     * @return
     */
    public boolean initCallcenter(List<Agent> agentList,List<Queue> queueList,List<Tier> tierList){
        reloadConfig1();
        for (Agent agent:agentList) {
            XAgent xa = new XAgent();
            BeanUtils.copyProperties(templateAgent, xa);
            xa.setName(agent.getAgentId());
            xa.setContact(agent.getContact());
            xa.setMax_no_answer("999999");
            xa.setStatus(agent.getStatus().getStrStatus());
            callCenterConfig.getAgents().getAgent().add(xa);
        }
        for (Queue queue:queueList) {
            XQueue xq = new XQueue();
            BeanUtils.copyProperties(templateQueue, xq);
            xq.setName(queue.getQueueId());
            callCenterConfig.getQueues().getQueue().add(xq);
        }
        for (Tier tier:tierList) {
            XTier xTier = new XTier();
            xTier.setAgent(tier.getCCAgentId());
            xTier.setQueue(tier.getCCQueueId());
            callCenterConfig.getTiers().getTier().add(xTier);
        }
        flushConfig1();
        return true;
    }
}
