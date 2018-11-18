package com.guiji.callcenter.fsmanager.controller;

import com.guiji.callcenter.fsmanager.config.Constant;
import com.guiji.callcenter.fsmanager.service.FsService;
import com.guiji.component.result.Result;
import com.guiji.fsmanager.api.IFsResource;
import com.guiji.fsmanager.entity.FsBindVO;
import com.guiji.fsmanager.entity.ServiceTypeEnum;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FsController implements IFsResource {
    private final Logger logger = LoggerFactory.getLogger(FsController.class);

    @Autowired
    FsService fsService;

    @Override
    public Result.ReturnData<FsBindVO> applyfs(@RequestParam("serviceId") String serviceId, @RequestParam("serviceType")ServiceTypeEnum serviceType) {
        logger.debug("收到申请freeswitch资源接口请求，serviceId[{}], serviceType[{}]", serviceId,serviceType);

        if(StringUtils.isBlank(serviceId)){
            logger.info("申请freeswitch资源失败，参数错误，为null或空");
            return Result.error(Constant.ERROR_CODE_PARAM);
        }
        FsBindVO fs=  fsService.applyfs(serviceId,serviceType);
        if(fs==null){
            logger.info("申请freeswitch资源失败，没有空闲并可用的fs资源");
           return Result.error("0301002");
        }
        return Result.ok(fs);
    }

    @Override
    public Result.ReturnData<Boolean> releasefs(@RequestParam("serviceId") String serviceId) {
        logger.debug("收到释放freeswitch资源接口请求，serviceId[{}]", serviceId);

        if(StringUtils.isBlank(serviceId)){
            logger.info("释放freeswitch资源请求失败，参数错误，为null或空");
            return Result.error(Constant.ERROR_CODE_PARAM);
        }
        fsService.releasefs(serviceId);
        return Result.ok(true);
    }
}
