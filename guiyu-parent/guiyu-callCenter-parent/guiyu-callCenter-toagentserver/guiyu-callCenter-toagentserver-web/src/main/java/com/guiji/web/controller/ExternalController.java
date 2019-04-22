package com.guiji.web.controller;

import com.google.common.base.Strings;
import com.guiji.callcenter.dao.entity.Agent;
import com.guiji.callcenter.dao.entity.Queue;
import com.guiji.component.result.Result;
import com.guiji.config.ErrorConstant;
import com.guiji.fs.FreeSWITCH;
import com.guiji.fs.FsManager;
import com.guiji.fs.pojo.GlobalVar;
import com.guiji.service.AgentService;
import com.guiji.service.QueueService;
import com.guiji.toagentserver.api.IAgentGroup;
import com.guiji.toagentserver.entity.AgentGroupInfo;
import com.guiji.toagentserver.entity.AgentMembrVO;
import com.guiji.toagentserver.entity.FsInfoVO;
import com.guiji.web.response.AgentSumResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @Override
    public Result.ReturnData untyingLineinfos(String lineId) {
        log.info("收到解绑队列线路的请求lineId：[{}]",lineId);
        if(!StringUtils.isBlank(lineId)){
            queueService.untyingLineinfos(lineId);
        }
        return Result.ok();
    }

    @Override
    public Result.ReturnData syncAgentMembers(@RequestBody List<AgentMembrVO> agentMembers) {
        log.info("收到同步坐席用户的接口");
        if(agentMembers!=null&&agentMembers.size()>0){
            agentService.syncAgentMembers(agentMembers);
        }
        return Result.ok();
    }

    @Override
    public Result.ReturnData delAgentMembers(@RequestBody List<Long> customerIds) {
        log.info("收到删除坐席用户的接口");
        if(customerIds!=null&&customerIds.size()>0) {
            agentService.delAgentMembers(customerIds);
        }
        return Result.ok();
    }


    @RequestMapping(path = "/agentsum", method = RequestMethod.GET)
    public Result.ReturnData<AgentSumResponse> getAgentStateSum(@RequestParam String orgCode,@RequestHeader Long userId){
        log.info("收到获取座席状态统计，orgCode[{}]", orgCode);
        Agent agentUser = agentService.getAgentByCustomerId(userId);
        if(agentUser==null||orgCode.equals("1")){
            return Result.ok(null);
        }
        AgentSumResponse agentSumResponse = new AgentSumResponse();
        List<Agent> agentList = agentService.findByOrgCode(orgCode);
        if(agentList!=null && agentList.size()>0){
            agentSumResponse.setTotalCount(agentList.size());
            int onlineCount = 0;
            for (Agent agent : agentList) {
                if (agent.getUserState() == 1) {
                    if (agent.getAnswerType() == 0) {
                        if (fsManager.getVertoStatus(agent.getUserId() + "")) {
                            onlineCount++;
                        }
                    } else {
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
