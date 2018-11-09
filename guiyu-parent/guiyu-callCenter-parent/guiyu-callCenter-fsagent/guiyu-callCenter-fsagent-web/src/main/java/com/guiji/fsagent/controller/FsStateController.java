package com.guiji.fsagent.controller;

import com.guiji.component.result.Result;
import com.guiji.fsagent.api.IFsState;
import com.guiji.fsagent.entity.FsInfoVO;
import com.guiji.fsagent.service.impl.FsStateServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FsStateController implements IFsState {
    private final Logger logger = LoggerFactory.getLogger(FsStateController.class);

    @Autowired
    FsStateServiceImpl fsStateManager;

    @Override
    public Result.ReturnData<Boolean> ishealthy() {
        logger.debug("收到检查服务健康状态请求");
        return Result.ok(fsStateManager.ishealthy());
    }

    @Override
    public Result.ReturnData<FsInfoVO> fsinfo() {
        logger.debug("获取freeswitch基本信息");
        FsInfoVO vo = fsStateManager.fsinfo();
        logger.debug("获取freeswitch基本信息接口返回FsInfoVO[{}]",vo);
       return  Result.ok(vo);
    }
}
