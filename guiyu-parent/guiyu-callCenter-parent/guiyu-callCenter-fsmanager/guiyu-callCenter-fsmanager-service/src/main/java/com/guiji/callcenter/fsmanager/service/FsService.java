package com.guiji.callcenter.fsmanager.service;

import com.guiji.common.result.Result;
import com.guiji.fsmanager.api.FsResourceApi;
import com.guiji.fsmanager.entity.FsBind;
import com.guiji.fsmanager.entity.ServiceTypeEnum;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class FsService implements FsResourceApi{
    @Override
    public Result.ReturnData applyfs(String serviceId, ServiceTypeEnum serviceType) {
        FsBind fs =new FsBind();
        fs.setServiceId("nanjing");
        fs.setFsAgentId("xx");
        fs.setFsAgentAddr("192.168.1.12:8088");
        fs.setFsEslPort("18021");
        fs.setFsEslPwd("123456qwert");
        fs.setFsInPort("50601");
        fs.setFsOutPort("50602");
        return Result.ok(fs);
    }

    @Override
    public Result.ReturnData releasefs(String serviceId) {
        List<FsBind> list = new ArrayList<FsBind>();

        FsBind fs =new FsBind();
        fs.setServiceId("nanjing");
        fs.setFsAgentId("xx");
        fs.setFsAgentAddr("192.168.1.12:8088");
        fs.setFsEslPort("18021");
        fs.setFsEslPwd("123456qwert");
        fs.setFsInPort("50601");
        fs.setFsOutPort("50602");

        FsBind fs1 =new FsBind();
        fs1.setServiceId("shanghai");
        fs1.setFsAgentId("xx");
        fs1.setFsAgentAddr("192.168.1.13:8088");
        fs1.setFsEslPort("18021");
        fs1.setFsEslPwd("123456qwert");
        fs1.setFsInPort("50601");
        fs1.setFsOutPort("50602");
        list.add(fs);
        list.add(fs1);
        return Result.ok(list);
    }
}
