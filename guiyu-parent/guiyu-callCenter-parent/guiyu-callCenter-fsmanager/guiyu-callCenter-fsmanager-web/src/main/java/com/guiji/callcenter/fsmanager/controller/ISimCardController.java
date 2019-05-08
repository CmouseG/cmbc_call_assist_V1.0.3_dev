package com.guiji.callcenter.fsmanager.controller;

import com.guiji.callcenter.fsmanager.config.Constant;
import com.guiji.callcenter.fsmanager.service.ISimCardService;
import com.guiji.component.result.Result;
import com.guiji.fsmanager.api.ISimCard;
import com.guiji.fsmanager.entity.FsSipVO;
import com.guiji.fsmanager.entity.SimCardVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
public class ISimCardController implements ISimCard {
    @Autowired
    ISimCardService iSimCardService;
    @Override
    public Result.ReturnData<FsSipVO> createGateway(@RequestBody SimCardVO simCardVO) {
        if (simCardVO.getStartCount()<=0 || simCardVO.getStartPwd()<=0 ||
                simCardVO.getCountsStep()<=0 || simCardVO.getPwdStep()<=0 ||
                simCardVO.getCountNum()<=0){
            log.info("创建sim卡网关请求失败，参数错误，为null或空");
            return Result.error(Constant.ERROR_CODE_PARAM);
        }
        log.info("收到创建sim卡网关请求",simCardVO.toString());
        return Result.ok(iSimCardService.createGateway(simCardVO));
    }

    @Override
    public Result.ReturnData<Boolean> deleteGateway(@PathVariable(value = "gatewayId") String gatewayId) {
        if (StringUtils.isBlank(gatewayId)) {
            log.info("删除sim卡网关请求失败，参数错误，为null或空");
            return Result.error(Constant.ERROR_CODE_PARAM);
        }
        log.info("收到删除sim卡网关的请求",gatewayId);
        return Result.ok(iSimCardService.deleteGateway(gatewayId));
    }

    @Override
    public Result.ReturnData<List<SimCardVO>> getGatewayAccount(@RequestParam(value = "serviceId") String  serviceId) {
        if (StringUtils.isBlank(serviceId)) {
            log.info("根据serviceId查询网关账号信息的请求失败，参数错误，为null或空");
            return Result.error(Constant.ERROR_CODE_PARAM);
        }
        log.info("收到根据serviceId查询网关账号信息的请求",serviceId);
        return Result.ok(iSimCardService.getGatewayAccount(serviceId));
    }
}
