package com.guiji.callcenter.fsmanager.controller;

import com.guiji.callcenter.fsmanager.service.FsService;
import com.guiji.common.result.Result;
import com.guiji.fsmanager.api.IFsResourceApi;
import com.guiji.fsmanager.entity.FsBindVO;
import com.guiji.fsmanager.entity.ServiceTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class FsController implements IFsResourceApi {
    @Autowired
    FsService fsService;

    @Override
    public Result.ReturnData<FsBindVO> applyfs(String serviceId, ServiceTypeEnum serviceType) {
        fsService.applyfs(serviceId,serviceType);

        FsBindVO fs =new FsBindVO();
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
        fsService.releasefs(serviceId);
        return Result.ok();
    }
}
