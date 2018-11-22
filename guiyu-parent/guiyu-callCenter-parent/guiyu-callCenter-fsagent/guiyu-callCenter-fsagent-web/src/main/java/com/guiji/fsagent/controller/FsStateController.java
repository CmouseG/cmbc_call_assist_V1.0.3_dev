package com.guiji.fsagent.controller;

import com.guiji.component.result.Result;
import com.guiji.fsagent.api.IFsState;
import com.guiji.fsagent.config.Constant;
import com.guiji.fsagent.entity.FsInfoVO;
import com.guiji.fsagent.service.FsStateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FsStateController implements IFsState {
    private final Logger logger = LoggerFactory.getLogger(FsStateController.class);

    @Autowired
    FsStateService fsStateService;

    @Override
    public Result.ReturnData<Boolean> ishealthy() {
        logger.info("收到检查服务健康状态请求");
        boolean result = fsStateService.ishealthy();
        if(!result){
            logger.info("freeswitch状态不正常");
            return Result.error(Constant.ERROR_CODE_FSSTATE_ERROR);
        }
        return Result.ok();
    }

    @Override
    public Result.ReturnData<FsInfoVO> fsinfo() {
        logger.info("获取freeswitch基本信息");
        FsInfoVO vo = fsStateService.fsinfo();
        logger.info("获取freeswitch基本信息接口返回FsInfoVO[{}]",vo);
       return  Result.ok(vo);
    }
}
