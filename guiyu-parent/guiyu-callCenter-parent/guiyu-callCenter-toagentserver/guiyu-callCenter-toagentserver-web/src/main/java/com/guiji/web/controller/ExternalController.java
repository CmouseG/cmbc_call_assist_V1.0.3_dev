package com.guiji.web.controller;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.guiji.callcenter.dao.entity.Agent;
import com.guiji.callcenter.dao.entity.Queue;
import com.guiji.component.result.Result;
import com.guiji.config.ErrorConstant;
import com.guiji.entity.EAnswerType;
import com.guiji.entity.EUserState;
import com.guiji.fs.FreeSWITCH;
import com.guiji.fs.FsManager;
import com.guiji.fs.pojo.GlobalVar;
import com.guiji.service.AgentService;
import com.guiji.service.QueueService;
import com.guiji.toagentserver.api.IAgentGroup;
import com.guiji.toagentserver.entity.AgentGroupInfo;
import com.guiji.toagentserver.entity.FsInfoVO;
import com.guiji.web.response.AgentSumResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: 魏驰
 * @Date: 2019/1/21 10:24
 * @Project：guiyu-parent
 * @Description: 用于提供对外的接口
 */
@Slf4j
@RestController
public class ExternalController implements IAgentGroup{
    @Autowired
    QueueService queueService;

    @Autowired
    AgentService agentService;

    @Autowired
    FsManager fsManager;

    @Override
    public Result.ReturnData<List<AgentGroupInfo>> getGroups(String orgCode) {
        log.info("收到获取队列列表请求，org[{}]", orgCode);

        List<Queue> queueList = queueService.findByOrgCode(orgCode);
        List<AgentGroupInfo> groups = new ArrayList<>(queueList.size());
        for (Queue queue : queueList) {
            AgentGroupInfo groupInfo = new AgentGroupInfo(queue.getQueueId().toString(), queue.getQueueName());
            groups.add(groupInfo);
        }

        log.info("获取队列请求返回结果为[{}]", groups);
        return Result.ok(groups);
    }

    @Override
    public Result.ReturnData<FsInfoVO> getFsInfo() {
        log.info("收到获取转人工fs基本信息接口");

        FsInfoVO fsInfoVO = new FsInfoVO();

        FreeSWITCH fs =fsManager.getFS();
        if(fs!=null){
            GlobalVar globalVar = fs.getGlobalVar();
            if(!Strings.isNullOrEmpty(globalVar.getDomain())){
                fsInfoVO.setFsInPort(globalVar.getInternal_sip_port());
                fsInfoVO.setFsIp(globalVar.getDomain());
                fsInfoVO.setFsOutPort(globalVar.getExternal_sip_port());
                log.info("获取转人工fs返回结果[{}]", fsInfoVO);
                return Result.ok(fsInfoVO);
            }
        }

        return Result.error(ErrorConstant.FS_CONNECT_FAIL);
    }

    @RequestMapping(path = "/agentsum", method = RequestMethod.GET)
    public Result.ReturnData<AgentSumResponse> getAgentStateSum(@RequestParam String orgCode){
        log.info("收到获取座席状态统计，orgCode[{}]", orgCode);

        AgentSumResponse agentSumResponse = new AgentSumResponse();
        List<Agent> agentList = agentService.findByOrgCode(orgCode);
        if(agentList!=null && agentList.size()>0){
            agentSumResponse.setTotalCount(agentList.size());
            int onlineCount = 0;
            for (Agent agent : agentList) {
                if(agent.getAnswerType()== EAnswerType.MOBILE.ordinal()){
                    if(agent.getUserState()== EUserState.ONLINE.ordinal()){
                        onlineCount++;
                    }
                }else{
                    if(agentService.agentVertoState(agent)){
                        onlineCount++;
                    }
                }
            }

            agentSumResponse.setOnlineCount(onlineCount);
        }

        log.info("获取座席状态统计，返回结果为[{}]", agentSumResponse);
        return Result.ok(agentSumResponse);
    }
}
