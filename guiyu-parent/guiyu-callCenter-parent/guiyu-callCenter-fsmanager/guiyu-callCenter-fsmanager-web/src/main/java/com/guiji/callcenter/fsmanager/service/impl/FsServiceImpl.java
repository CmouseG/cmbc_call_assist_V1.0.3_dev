package com.guiji.callcenter.fsmanager.service.impl;

import com.guiji.common.result.Result;
import com.guiji.fsmanager.entity.FsBind;
import com.guiji.fsmanager.entity.ServiceTypeEnum;
import org.springframework.stereotype.Service;

@Service
public class FsServiceImpl {
    public FsBind applyfs(String serviceId, ServiceTypeEnum serviceType) {
        FsBind fs =new FsBind();
        fs.setServiceId("nanjing");
        fs.setFsAgentId("xx");
        fs.setFsAgentAddr("192.168.1.12:8088");
        fs.setFsEslPort("18021");
        fs.setFsEslPwd("123456qwert");
        fs.setFsInPort("50601");
        fs.setFsOutPort("50602");
        return fs;
    }
}
