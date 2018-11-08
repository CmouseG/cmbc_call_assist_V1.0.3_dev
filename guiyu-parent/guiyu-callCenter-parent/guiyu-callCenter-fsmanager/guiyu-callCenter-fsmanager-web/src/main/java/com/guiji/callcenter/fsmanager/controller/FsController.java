package com.guiji.callcenter.fsmanager.controller;

import com.guiji.callcenter.fsmanager.service.FsService;
import com.guiji.component.result.Result;
import com.guiji.fsmanager.api.IFsResource;
import com.guiji.fsmanager.entity.FsBindVO;
import com.guiji.fsmanager.entity.ServiceTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FsController implements IFsResource {
    @Autowired
    FsService fsService;

    @Override
    public Result.ReturnData<FsBindVO> applyfs(String serviceId, ServiceTypeEnum serviceType) {
        FsBindVO fs=  fsService.applyfs(serviceId,serviceType);
        if(fs==null){
           return Result.error("0301002");
        }
        return Result.ok(fs);
    }

    @Override
    public Result.ReturnData<Boolean> releasefs(String serviceId) {
        fsService.releasefs(serviceId);
        return Result.ok(true);
    }
}
