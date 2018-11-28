package com.guiji.process.server.service.impl;

import com.guiji.common.model.process.ProcessInstanceVO;
import com.guiji.common.model.process.ProcessStatusEnum;
import com.guiji.common.model.process.ProcessTypeEnum;
import com.guiji.process.core.message.CmdMessageVO;
import com.guiji.process.core.message.MessageProto;
import com.guiji.process.core.vo.CmdTypeEnum;
import com.guiji.process.server.core.ConnectionPool;
import com.guiji.process.server.dao.entity.SysProcess;
import com.guiji.process.server.model.DeviceProcessConstant;
import com.guiji.process.server.service.IDeviceManageService;
import com.guiji.process.server.service.IProcessAgentManageService;
import com.guiji.process.server.service.ISysProcessService;
import com.guiji.process.server.util.DeviceProcessUtil;
import com.guiji.utils.JsonUtils;
import com.guiji.utils.RedisUtil;
import io.netty.channel.ChannelHandlerContext;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ProcessAgentManage implements IProcessAgentManageService {

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private ISysProcessService processService;

    public Map<Object, Object> query()
    {
        Map<Object, Object> agentMap = (Map<Object, Object>) redisUtil.hmget("GY_PROCESS_AGENTS");
        if(agentMap == null)
        {
            agentMap = new ConcurrentHashMap<Object, Object>();
        }

        return agentMap;
    }


    public void add(ProcessInstanceVO agent)
    {
        Map<Object, Object> agentMap = (Map<Object, Object>) redisUtil.hmget("GY_PROCESS_AGENTS");
        if(agentMap == null)
        {
            agentMap = new ConcurrentHashMap<Object, Object>();
        }

        agentMap.put(agent.getIp(), agent);

        updateAgent(agentMap);

        // 存入数据库
        SysProcess sysProcess = new SysProcess();
        sysProcess.setIp(agent.getIp());
        sysProcess.setPort(String.valueOf(agent.getPort()));
        sysProcess.setName(agent.getName());
        sysProcess.setProcessKey(agent.getProcessKey());
        sysProcess.setStatus(agent.getStatus().getValue());
        sysProcess.setType(agent.getType().getValue());
        sysProcess.setCreateTime(new Date());
        sysProcess.setUpdateTime(new Date());
        processService.insert(sysProcess);
    }


    public void del(ProcessInstanceVO agent)
    {
        Map<Object, Object> agentMap = (Map<Object, Object>) redisUtil.hmget("GY_PROCESS_AGENTS");
        if(agentMap == null)
        {
            return;
        }

        if(agentMap.containsKey(agent.getIp()))
        {
           // agentMap.remove(agent.getIp());
            agent.setWhoUsed("");
            agentMap.put(agent.getIp(),agent);

            updateAgent(agentMap);

            // 存入数据库
            SysProcess sysProcess = new SysProcess();
            sysProcess.setIp(agent.getIp());
            sysProcess.setPort(String.valueOf(agent.getPort()));
            sysProcess.setStatus(agent.getStatus().getValue());
            sysProcess.setUpdateTime(new Date());
            processService.update(sysProcess);
        }
    }


    @Override
    public ProcessInstanceVO get(String ip, int port) {
        Map<Object, Object> agentMap = (Map<Object, Object>) redisUtil.hmget("GY_PROCESS_AGENTS" );
        if(agentMap == null)
        {
            return null;
        }

        return (ProcessInstanceVO) agentMap.get(ip);
    }


    public void updateStatus(ProcessInstanceVO process)
    {
        Map<Object, Object> deviceVOMap =  (Map<Object, Object>) redisUtil.hmget("GY_PROCESS_AGENTS" );
        if(deviceVOMap == null)
        {
            return;
        }

        deviceVOMap.put(process.getType(), process);

        updateAgent(deviceVOMap);
    }

    private void updateAgent(Map<Object, Object> agentMap)
    {
        Map<String, Object> deviceVOMapTmp = new ConcurrentHashMap<String, Object>();

        for (Map.Entry<Object, Object> ent:agentMap.entrySet()) {
            deviceVOMapTmp.put((String) ent.getKey(), ent.getValue());
        }
        redisUtil.hmset("GY_PROCESS_AGENTS", deviceVOMapTmp);
    }
}
