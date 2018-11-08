package com.guiji.fsagent.controller;

import com.guiji.component.result.Result;
import com.guiji.fsagent.api.IFsState;
import com.guiji.fsagent.entity.FsInfoVO;
import com.guiji.fsagent.service.impl.FsStateServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FsStateController implements IFsState {
    @Autowired
    FsStateServiceImpl fsStateManager;

    @Override
    public Result.ReturnData<Boolean> ishealthy() {
        return Result.ok(fsStateManager.ishealthy());
    }

    @Override
    public Result.ReturnData<FsInfoVO> fsinfo() {
       return  Result.ok(fsStateManager.fsinfo());
    }
}
