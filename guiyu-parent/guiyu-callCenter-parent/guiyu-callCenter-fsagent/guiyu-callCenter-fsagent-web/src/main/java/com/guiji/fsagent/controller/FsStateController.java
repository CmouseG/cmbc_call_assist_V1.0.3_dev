package com.guiji.fsagent.controller;

import com.guiji.component.result.Result;
import com.guiji.fsagent.api.IFsStateApi;
import com.guiji.fsagent.entity.FsInfoVO;
import com.guiji.fsagent.service.impl.FsStateServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FsStateController implements IFsStateApi {
    @Autowired
    FsStateServiceImpl fsStateManager;

    @Override
    public Result.ReturnData<Boolean> ishealthy() {
        return Result.ok(fsStateManager.ishealthy());
       // return Result.ok(true);
    }

    @Override
    public Result.ReturnData<FsInfoVO> fsinfo() {
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
