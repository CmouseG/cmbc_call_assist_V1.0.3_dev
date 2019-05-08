package com.guiji.callcenter.fsmanager.controller;

import com.guiji.callcenter.fsmanager.config.Constant;
import com.guiji.callcenter.fsmanager.service.FsService;
import com.guiji.component.result.Result;
import com.guiji.fsmanager.api.IFsResource;
import com.guiji.fsmanager.entity.FsBindVO;
import com.guiji.fsmanager.entity.FsConfigVO;
import com.guiji.fsmanager.entity.ServiceTypeEnum;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FsController implements IFsResource {
    private final Logger logger = LoggerFactory.getLogger(FsController.class);

    @Autowired
    FsService fsService;

    @Override
    public Result.ReturnData<FsBindVO> applyfs(@RequestParam("serviceId") String serviceId, @RequestParam("serviceType")ServiceTypeEnum serviceType) {
        logger.info("收到申请freeswitch资源接口请求，serviceId[{}], serviceType[{}]", serviceId,serviceType);

        if(StringUtils.isBlank(serviceId)){
            logger.info("申请freeswitch资源失败，参数错误，为null或空");
            return Result.error(Constant.ERROR_CODE_PARAM);
        }
        FsBindVO fs=  fsService.applyfs(serviceId,serviceType);
        if(fs==null){
            logger.info("申请freeswitch资源失败，没有空闲并可用的fs资源");
           return Result.error(Constant.ERROR_CODE_NONE_FREESWITCH);
        }
        return Result.ok(fs);
    }

    @Override
    public Result.ReturnData<Boolean> releasefs(@RequestParam("serviceId") String serviceId) {
        logger.info("收到释放freeswitch资源接口请求，serviceId[{}]", serviceId);

        if(StringUtils.isBlank(serviceId)){
            logger.info("释放freeswitch资源请求失败，参数错误，为null或空");
            return Result.error(Constant.ERROR_CODE_PARAM);
        }
        fsService.releasefs(serviceId);
        return Result.ok(true);
    }

    @Override
    public Result.ReturnData<List<FsConfigVO>> getFsConfig(@RequestParam("role") String role) {
        logger.info("收到根据role获取FsConfig接口请求，role[{}]", role);
        if(StringUtils.isBlank(role)){
            logger.info("根据role获取FsConfig请求失败，参数错误，为null或空");
            return Result.error(Constant.ERROR_CODE_PARAM);
        }
        List<FsConfigVO> list = fsService.getFsConfig(role);
        return Result.ok(list);
    }
}
