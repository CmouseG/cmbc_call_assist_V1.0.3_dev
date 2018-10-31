package com.guiji.fsagent.service;

import com.guiji.common.result.Result;
import com.guiji.fsagent.api.FsStateApi;
import com.guiji.fsagent.entity.FreeSWITCH;
import com.guiji.fsagent.entity.FsInfo;
import com.guiji.fsagent.manager.FSService;
import com.guiji.fsagent.manager.FsEslClient;
import com.guiji.fsagent.manager.FsStateManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.net.ConnectException;
import java.net.Socket;

@RestController
public class FsStateService implements FsStateApi {
    @Autowired
    FsStateManager fsStateManager;

    @Override
    public Result.ReturnData<Boolean> ishealthy() {
        return Result.ok(fsStateManager.ishealthy());
        //return Result.ok(true);
    }

    @Override
    public Result.ReturnData<FsInfo> fsinfo() {
       return  Result.ok(fsStateManager.fsinfo());
//        FsInfo fsinfo = new FsInfo();
//        fsinfo.setFsAgentId("ffff");
//        fsinfo.setFsIp("192.168.2.44");
//        fsinfo.setFsInPort("50601");
//        fsinfo.setFsOutPort("50602");
//        fsinfo.setFsEslPort("18032");
//        fsinfo.setFsEslPwd("xxx");
//        return Result.ok(fsinfo);
    }
}
