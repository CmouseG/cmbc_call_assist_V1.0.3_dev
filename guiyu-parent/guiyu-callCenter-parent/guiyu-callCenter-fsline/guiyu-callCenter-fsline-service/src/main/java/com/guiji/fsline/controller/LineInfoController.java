package com.guiji.fsline.controller;

import com.guiji.common.result.Result;
import com.guiji.fsline.api.IFsLineApi;
import com.guiji.fsline.entity.FsLineInfo;
import com.guiji.fsmanager.api.FsResourceApi;
import com.guiji.utils.ServerUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class LineInfoController implements IFsLineApi {
    @Autowired
    Registration registration;

    @Override
    public Result.ReturnData<FsLineInfo> getFsInfo() {
        String instanceId = ServerUtil.getInstanceId(registration);

        FsLineInfo info = new FsLineInfo();
        info.setFsLineId(instanceId);
        info.setFsInPort("50601");
        info.setFsOutPort("50602");
        info.setFsIp("127.0.0.1");

//        Result.ReturnData<FsBind> fsline = fsResourceApi.applyfs(instanceId, "fsline");
//        FsBind fsbind = fsline.getBody();

        return Result.ok(info);
    }
}
