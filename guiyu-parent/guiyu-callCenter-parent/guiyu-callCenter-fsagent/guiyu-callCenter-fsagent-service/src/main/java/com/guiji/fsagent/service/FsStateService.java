package com.guiji.fsagent.service;

import com.guiji.common.result.Result;
import com.guiji.fsagent.api.FsStateApi;
import com.guiji.fsagent.entity.FsInfo;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FsStateService implements FsStateApi {
    @Override
    public Result.ReturnData<Boolean> ishealthy() {
        return Result.ok(true);
    }

    @Override
    public Result.ReturnData<FsInfo> fsinfo() {
        FsInfo fsinfo = new FsInfo();
        fsinfo.setFslineId("ffff");
        fsinfo.setFsIp("192.168.2.44");
        fsinfo.setFsInPort("50601");
        fsinfo.setFsOutPort("50602");
        fsinfo.setFsEslPort("18032");
        fsinfo.setFsEslPwd("xxx");
        return Result.ok(fsinfo);
    }
}
