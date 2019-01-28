package com.guiji.calloutserver.manager.impl;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.guiji.calloutserver.manager.ToAgentManager;
import com.guiji.component.result.Result;
import com.guiji.toagentserver.api.IAgentGroup;
import com.guiji.toagentserver.entity.FsInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class ToAgentManagerImpl implements ToAgentManager {

    @Autowired
    IAgentGroup agentGroup;

    private Cache<String, String> fsCaches;

    @PostConstruct
    public void init(){
        fsCaches = CacheBuilder.newBuilder().build();
    }

    @Override
    public String findToAgentFsAdder() {

        String fsAddr = fsCaches.getIfPresent("fsAddr");
        if(fsAddr!=null){
            return fsAddr;
        }else{
            Result.ReturnData<FsInfoVO> result = agentGroup.getFsInfo();
            FsInfoVO fsInfoVO = result.getBody();
            fsAddr = fsInfoVO.getFsIp() +":"+ fsInfoVO.getFsInPort();
            fsCaches.put("fsAddr",fsAddr);
            return fsAddr;
        }
    }





}
