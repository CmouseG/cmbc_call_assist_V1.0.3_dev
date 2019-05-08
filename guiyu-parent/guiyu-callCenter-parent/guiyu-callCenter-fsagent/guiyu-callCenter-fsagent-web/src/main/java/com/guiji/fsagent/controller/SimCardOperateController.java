package com.guiji.fsagent.controller;

import com.guiji.component.result.Result;

import com.guiji.fsagent.api.ISimCardOperate;
import com.guiji.fsagent.config.Constant;
import com.guiji.fsagent.entity.FsSipOprVO;
import com.guiji.fsagent.entity.SimCardOprVO;
import com.guiji.fsagent.service.SimCardOperateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class SimCardOperateController implements ISimCardOperate {

    @Autowired
    SimCardOperateService simCardOperateService;

    @Override
    public Result.ReturnData<FsSipOprVO> createGateway(@RequestBody SimCardOprVO simCardOprVO) {
        if (simCardOprVO.getStartCount()<=0 || simCardOprVO.getStartPwd()<=0 ||
                simCardOprVO.getCountsStep()<=0 || simCardOprVO.getPwdStep()<=0 ||
                simCardOprVO.getCountNum()<=0){
            log.info("创建sim卡网关请求失败，参数错误，为null或空");
            return Result.error(Constant.ERROR_CODE_PARAM);
        }
        log.info("收到创建sim卡网关的请求simCardVO[{}]", simCardOprVO);
        FsSipOprVO result = simCardOperateService.createGateway(simCardOprVO);
        return Result.ok(result);
    }

    @Override
    public Result.ReturnData<Boolean> deleteGateway(@RequestParam(value = "startCount") int startCount, @RequestParam(value = "countsStep") int countsStep, @RequestParam(value = "countNum") int countNum) {
        boolean result= simCardOperateService.deleteGateway(startCount,  countsStep,  countNum);
        return Result.ok(result);
    }
}
