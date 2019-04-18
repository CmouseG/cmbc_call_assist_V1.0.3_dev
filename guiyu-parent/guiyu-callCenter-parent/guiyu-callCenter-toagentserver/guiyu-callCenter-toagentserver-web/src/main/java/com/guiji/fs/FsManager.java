package com.guiji.fs;

import com.guiji.config.FsBotConfig;
import com.guiji.fs.module.ModCallCenter;
import com.guiji.fs.module.ModVerto;
import com.guiji.fs.pojo.*;
import com.guiji.fsmanager.entity.FsBindVO;
import com.guiji.web.request.AgentInfo;
import com.guiji.web.request.QueueInfo;
import com.guiji.web.request.TierInfo;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class FsManager {

    @Autowired
    FsEventHandler fsEventHandler;

    private FreeSWITCH freeSWITCH;

    @Getter
    private ModCallCenter modCallCenter;

    @Getter
    private ModVerto modVerto;
    private FsBindVO fsBindInfo;
    @Autowired
    FsWatchDog fsWatchDog;
    @Autowired
    FsBotConfig fsBotConfig;

    public void init(FsBindVO fsBindInfo){
        this.fsBindInfo = fsBindInfo;
        freeSWITCH = new FreeSWITCH(fsBindInfo,fsEventHandler,fsBotConfig.getHomeDir());
        modCallCenter = freeSWITCH.getCallCenter();
        modVerto = freeSWITCH.getModVerto();

        fsWatchDog.monitor();
    }

  public void reconnect(){
      FsEslClient fsEslClient = freeSWITCH.getFsEslClient();
      fsEslClient.getFsClient();
  }

    public FreeSWITCH getFS(){
        return freeSWITCH;
    }

    public boolean addAgent(AgentInfo agentInfo){
        Agent agent = new Agent();
        agent.setAgentId(agentInfo.getAgentId());
        agent.setContact(agentInfo.getContact());
        boolean result = modCallCenter.addAgent(agent);
        return result;
    }

    public AgentInfo getAgent(String agentId){
        Agent agent = modCallCenter.getAgent(agentId);
        Preconditions.checkNotNull(agent, "agent not exist: " + agentId);
        AgentInfo agentInfo = new AgentInfo();
        BeanUtils.copyProperties(agent, agentInfo);
        AgentState agentState = buildAgentState(agent.getState(), modVerto.getStatus(agent.getAgentId()), agent.getStatus());
        agentInfo.setState(agentState);
        return agentInfo;
    }

    public List<AgentInfo> getAllAgent(){
        List<Agent> allAgents = modCallCenter.getAllAgents();

        List<AgentInfo> agentInfos = new ArrayList<>(allAgents.size());
        for(Agent agent: allAgents){
            AgentInfo agentInfo = new AgentInfo();
            BeanUtils.copyProperties(agent, agentInfo);

            AgentState agentState = buildAgentState(agent.getState(), modVerto.getStatus(agent.getAgentId()), agent.getStatus());
            agentInfo.setState(agentState);
            agentInfos.add(agentInfo);
        }

        return agentInfos;
    }

    private AgentState buildAgentState(String ccState, VertoStatus vertoStatus, AgentStatus agentStatus){
        AgentState agentState = AgentState.getState(ccState);
        if(vertoStatus == VertoStatus.offline){
            agentState = AgentState.Offline;
        }else if(vertoStatus == VertoStatus.online){
            if(agentStatus == AgentStatus.Logged_Out || agentStatus == AgentStatus.On_Break){
                agentState = AgentState.CheckOut;
            }else if(agentStatus == AgentStatus.Available && agentState == AgentState.CheckIn){
                agentState = AgentState.CheckIn;
            }
        }

        return agentState;
    }

    /**
     * 更新坐席
     * @param agentInfo
     * @return
     */
    public boolean updateAgent(AgentInfo agentInfo){
        Preconditions.checkArgument(agentInfo!=null && agentInfo.getAgentId()!=null, "null agentInfo");
//        Agent agent = new Agent();
//        agent.setAgentId(agentInfo.getAgentId());
        if(agentInfo.getStatus()!=null){
            modCallCenter.updateAgentStatus(agentInfo.getAgentId(),agentInfo.getStatus());
//            agent.setStatus(agentInfo.getStatus());
        }
        if(agentInfo.getContact()!=null){
            modCallCenter.updateAgentContact(agentInfo.getAgentId(),agentInfo.getContact());
 //           agent.setContact(agentInfo.getContact());
        }
//        //修改配置文件
//        modCallCenter.updateAgent(agent);
        return true;
    }

    public boolean deleteAgent(String agentId){
        return modCallCenter.deleteAgent(agentId);
    }

   public Boolean createQueue(String  queueId) {
        Queue queue = new Queue();
        queue.setQueueId(queueId);
        return modCallCenter.addQueue(queue);
    }

    public boolean deleteQueue(String queueId) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(queueId), "null queueId");
        return modCallCenter.deleteQueue(queueId);
    }

    public Boolean addTier(TierInfo tierInfo) {
        Tier tier = new Tier();
        BeanUtils.copyProperties(tierInfo, tier);
        return modCallCenter.addTier(tier);
    }

    public boolean deleteTier(TierInfo tierInfo) {
        Tier tier = new Tier();
        BeanUtils.copyProperties(tierInfo, tier);
        return modCallCenter.deleteTier(tier);
    }

    public List<QueueInfo> getAllQueues() {
        return modCallCenter.getAllQueues();
    }

    public List<TierInfo> getAllTiers() {
        return modCallCenter.getAllTiers();
    }

    public void updateAgent(List<AgentInfo> agentInfos){
        log.info("开始更新座席信息[{}]", agentInfos);
        List<User> userList = agentInfos.stream().map(agentInfo -> new User(agentInfo.getAgentId(), agentInfo.getPassword())).collect(Collectors.toList());

        List<Agent> agentList = agentInfos.stream().map(agentInfo -> {
            Agent agent = new Agent();
            BeanUtils.copyProperties(agentInfo, agent);
            return agent;
        }).collect(Collectors.toList());
        log.info("开始更新座席信息");
        modCallCenter.updateAgent(agentList);
    }

    public void updateQueue(List<String> queueInfos){
        List<Queue> queueList = queueInfos.stream().map(queueInfo -> new Queue(queueInfo)).collect(Collectors.toList());
        modCallCenter.updateQueue(queueList);
    }

    public void updateTier(List<TierInfo> tierInfos){
        log.info("开始更新绑定信息[{}]", tierInfos);
        List<Tier> tierList = tierInfos.stream().map(tierInfo -> new Tier(tierInfo.getQueueId(), tierInfo.getAgentId())).collect(Collectors.toList());
        modCallCenter.updateTier(tierList);
    }

    /**
     * 更新坐席状态
     * @param agentInfo
     * @return
     */
    public boolean updateAgentState(AgentInfo agentInfo){
        Preconditions.checkArgument(agentInfo!=null && agentInfo.getAgentId()!=null, "null agentInfo");
        Preconditions.checkArgument(agentInfo.getStatus()!=null,"null agentStatus");
        modCallCenter.updateAgentStatus(agentInfo.getAgentId(),agentInfo.getStatus());

//        Agent agent = new Agent();
//        agent.setAgentId(agentInfo.getAgentId());
//        agent.setStatus(agentInfo.getStatus());
//        //修改配置文件
//        modCallCenter.updateAgent(agent);
        return true;
    }


    /**
     * 将通道中播放的旧音频替换为新的
     * @param uuid
     * @param oldWav    正在播放的音频文件, 可以为空
     * @param newWav    准备播放的新音频文件
     */
    public void displace(String uuid, String oldWav, String newWav){
        Preconditions.checkArgument(!Strings.isNullOrEmpty(uuid), "null uuid");
        Preconditions.checkArgument(!Strings.isNullOrEmpty(newWav), "null newWav");

        if(!Strings.isNullOrEmpty(oldWav)){
            String cmd = String.format("uuid_displace %s stop %s 0 mux", uuid, oldWav);
            //executeAsync(cmd);
            freeSWITCH.executeAsync(cmd);
        }

        String cmd = String.format("uuid_displace %s start %s 0 mux", uuid, newWav);
        freeSWITCH.execute(cmd);
    }

    /**
     * 向verto客户端发送消息
     * @param msg
     */
    public void vchat(String recvNum, Object msg){
        String cmd = String.format("lua vchat.lua %s %s", recvNum, msg.toString());
        freeSWITCH.executeAsync(cmd);
    }

    /**
     * 向通道播放录音文件，如果通道中已有录音在播放，则停止，播放最新的文件
     * @param uuid
     * @param wavFile
     */
    public void playToChannel(String uuid, String wavFile){
        String hangupCmd = String.format("lua playfile.lua %s %s", uuid, wavFile);
        freeSWITCH.executeAsync(hangupCmd);
    }

    /**
     * 预约挂断, 让通道在多长时间时间后挂断
     * @param uuid
     * @param timeOut
     */
    public void scheduleHangup(String uuid, Double timeOut){
        String hangupCmd = String.format("sched_api +%s  uuid_kill %s", timeOut, uuid);
        freeSWITCH.executeAsync(hangupCmd);
    }

    /**
     * 将呼叫转到指定CallCenter中的座席组
     * @param uuid
     * @param agentGroupId
     */
    public void transferToAgentGroup(String uuid, String customerNum, String agentGroupId){
        String command = String.format("uuid_transfer %s ag_%s_%s",
                uuid,
                customerNum,
                agentGroupId);
        String response = freeSWITCH.executeAsync(command);
        log.info("FreeSWITCH异步命令[{}]返回结果为[{}]", command, response);
    }

    public void transfer(String uuid, String destNum){
        String command = String.format("uuid_transfer %s %s", uuid,destNum);
        String response = freeSWITCH.executeAsync(command);
        log.info("FreeSWITCH异步命令[{}]返回结果为[{}]", command, response);
    }

    public void executeAsync(String command){
        String response = freeSWITCH.executeAsync(command);
        log.info("FreeSWITCH异步命令[{}]返回结果为[{}]", command, response);
    }

    public Integer getWaitCount(String queueId) {
        return modCallCenter.getWaitCount(queueId);
    }

    /**
     * 根据坐席Id查看该坐席是否已经在verto中登录（已登录返回false）
     * @param agentId
     * @return
     */
    public boolean getVertoStatus(String agentId) {
        VertoStatus vertoStatus =modVerto.getStatus(agentId);
        if(vertoStatus==VertoStatus.offline){
            return false;
        }else{
            return true;
        }
    }

    /**
     * 调用lua脚本同步fs用户
     * @param startId
     * @param endId
     */
    public void syncUser(String startId, String endId){
        String cmd = String.format("lua number_generator.lua %s %s", startId, endId);
        freeSWITCH.executeAsync(cmd);
    }

    /**
     * 调用lua脚本同步callcenter.conf.xml
     * @param url
     */
    public void syncCallcenter(String url,String other){
        String cmd ="";
        if(StringUtils.isBlank(other)){
             cmd = String.format("lua configfs.lua %s autoload_configs/callcenter.conf.xml", url);
        }else{
            cmd = String.format("lua configfs.lua %s autoload_configs/callcenter.conf.xml %s", url,other);
        }
        freeSWITCH.executeAsync(cmd);
    }

    /**
     * 基于模板批量创建坐席、队列和绑定关系
     * @param agentInfoList
     * @param queueIdList
     * @param tierInfoList
     * @return
     */
    public boolean initCallcenter(List<AgentInfo> agentInfoList,List<String> queueIdList,List<TierInfo> tierInfoList){
        List<Agent>  agentList = new ArrayList<>();
        List<Queue>  queueList = new ArrayList<>();
        List<Tier>  tierList = new ArrayList<>();
        if(agentInfoList!=null){
            for (AgentInfo agentInfo:agentInfoList) {
                Agent agent = new Agent();
                agent.setAgentId(agentInfo.getAgentId());
                agent.setContact(agentInfo.getContact());
                agent.setStatus(agentInfo.getStatus());
                agentList.add(agent);
            }
        }
        if(queueIdList!=null) {
            for (String queueId : queueIdList) {
                Queue queue = new Queue();
                queue.setQueueId(queueId);
                queueList.add(queue);
            }
        }
        if(tierInfoList!=null){
            for (TierInfo tierInfo: tierInfoList) {
                Tier tier = new Tier();
                BeanUtils.copyProperties(tierInfo, tier);
                tierList.add(tier);
            }
        }
     return   modCallCenter.initCallcenter(agentList,queueList,tierList);
    }

}
