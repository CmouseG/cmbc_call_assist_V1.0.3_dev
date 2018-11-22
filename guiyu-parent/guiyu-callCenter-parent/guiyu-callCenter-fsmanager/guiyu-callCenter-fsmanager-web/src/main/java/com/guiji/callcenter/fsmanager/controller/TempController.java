package com.guiji.callcenter.fsmanager.controller;

import com.guiji.callcenter.fsmanager.config.Constant;
import com.guiji.callcenter.fsmanager.service.TempService;
import com.guiji.component.result.Result;
import com.guiji.fsmanager.api.ITemp;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TempController implements ITemp {
    private final Logger logger = LoggerFactory.getLogger(TempController.class);
    @Autowired
    TempService tempService;
    @Override
    public Result.ReturnData<Boolean> istempexist(String tempId) {
        logger.info("收到判断模板录音是否存在的请求，tempId[{}]", tempId);
        if(StringUtils.isBlank(tempId)){
            logger.info("判断模板录音是否存在的请求失败，参数错误，为null或空");
            return Result.error(Constant.ERROR_CODE_PARAM);
        }
        return Result.ok(tempService.istempexist(tempId));
    }
}
