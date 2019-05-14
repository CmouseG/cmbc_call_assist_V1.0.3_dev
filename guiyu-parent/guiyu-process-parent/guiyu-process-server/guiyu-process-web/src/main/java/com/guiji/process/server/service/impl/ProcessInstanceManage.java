package com.guiji.process.server.service.impl;

import com.guiji.common.model.process.ProcessInstanceVO;
import com.guiji.process.server.dao.entity.SysProcess;
import com.guiji.process.server.service.IProcessAgentManageService;
import com.guiji.process.server.service.IProcessInstanceManageService;
import com.guiji.process.server.service.ISysProcessService;
import com.guiji.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ProcessInstanceManage implements IProcessInstanceManageService {

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private ISysProcessService processService;

    public Map<Object, Object> query(String ip)
    {
        Map<Object, Object> proceMap = (Map<Object, Object>) redisUtil.hmget("GY_PROCESS_" + ip);
        if(proceMap == null)
        {
            proceMap = new ConcurrentHashMap<Object, Object>();
        }

        return proceMap;
    }


    public void add(ProcessInstanceVO process)
    {
        Map<Object, Object> agentMap = (Map<Object, Object>) redisUtil.hmget("GY_PROCESS_" + process.getIp());
        if(agentMap == null)
        {
            agentMap = new ConcurrentHashMap<Object, Object>();
        }

        ProcessInstanceVO oldProcessInstanceVO = get(process.getIp(), process.getPort());
        if(oldProcessInstanceVO != null)
        {
            process.setWhoUsed(oldProcessInstanceVO.getWhoUsed());
            process.setStatus(oldProcessInstanceVO.getStatus());
        }

        agentMap.put(process.getIp() + "_" + process.getPort(), process);

        updateAgent("GY_PROCESS_" + process.getIp(), agentMap);

        /*List<String> ipList = (List<String>)redisUtil.get("GY_PROCESS_" + process.getType());
        if (ipList == null || ipList.size() <= 0) {
            ipList = new ArrayList<String>();
            ipList.add(process.getIp());
        } else {
            boolean needAnd = true;
            for (String ip : ipList) {
                if (ip.equals(process.getIp())) {
                    needAnd = false;
                    break;
                }
            }
            if (needAnd) {
                ipList.add(process.getIp());
            }
        }
        redisUtil.set("GY_PROCESS_" + process.getType(),ipList);*/

        // 存入数据库
        SysProcess sysProcess = new SysProcess();
        sysProcess.setIp(process.getIp());
        sysProcess.setPort(String.valueOf(process.getPort()));
        sysProcess.setName(process.getName());
        sysProcess.setProcessKey(process.getProcessKey());
        sysProcess.setStatus(process.getStatus().getValue());
        sysProcess.setType(process.getType().getValue());
        sysProcess.setCreateTime(new Date());
        sysProcess.setUpdateTime(new Date());
        processService.insert(sysProcess);
    }

    public void del(ProcessInstanceVO process)
    {
        Map<Object, Object> agentMap = (Map<Object, Object>) redisUtil.hmget("GY_PROCESS_" + process.getIp());
        if(agentMap == null)
        {
            return;
        }

        Map<String, Object> deviceVOMapTmp = new ConcurrentHashMap<String, Object>();

        if(agentMap.containsKey(process.getIp()+ "_" + process.getPort()))
        {
           // agentMap.remove(agent.getIp());

            process.setWhoUsed("");
            agentMap.put(process.getIp()+ "_" + process.getPort(),process);

            updateAgent("GY_PROCESS_"  + process.getIp(), agentMap);

            // 存入数据库
            SysProcess sysProcess = new SysProcess();
            sysProcess.setIp(process.getIp());
            sysProcess.setPort(String.valueOf(process.getPort()));
            sysProcess.setStatus(process.getStatus().getValue());
            sysProcess.setUpdateTime(new Date());
            processService.update(sysProcess);
        }
    }

    @Override
    public ProcessInstanceVO get(String ip, int port) {
        Map<Object, Object> agentMap = (Map<Object, Object>) redisUtil.hmget("GY_PROCESS_"  + ip);
        if(agentMap == null)
        {
            return null;
        }

        return (ProcessInstanceVO) agentMap.get(ip + "_" + port);
    }

    public void updateStatus(ProcessInstanceVO process)
    {
        Map<Object, Object> deviceVOMap =  (Map<Object, Object>) redisUtil.hmget("GY_PROCESS_"   + process.getIp());
        if(deviceVOMap == null)
        {
            deviceVOMap = new ConcurrentHashMap<Object, Object>();
        }

        deviceVOMap.put(process.getIp()+ "_" + process.getPort(), process);

        updateAgent("GY_PROCESS_"  + process.getIp(), deviceVOMap);
    }

    private void updateAgent(String key, Map<Object, Object> agentMap)
    {
        Map<String, Object> deviceVOMapTmp = new ConcurrentHashMap<String, Object>();

        for (Map.Entry<Object, Object> ent:agentMap.entrySet()) {
            deviceVOMapTmp.put((String) ent.getKey(), ent.getValue());
        }
        redisUtil.hmset(key, deviceVOMapTmp);
    }


    public void update(String ip, Map<Object, Object> map)
    {
        Map<String, Object> deviceVOMapTmp = new ConcurrentHashMap<String, Object>();

        for (Map.Entry<Object, Object> ent:map.entrySet()) {
            deviceVOMapTmp.put((String) ent.getKey(), ent.getValue());
        }
        redisUtil.hmset("GY_PROCESS_"  + ip, deviceVOMapTmp);
    }
}
