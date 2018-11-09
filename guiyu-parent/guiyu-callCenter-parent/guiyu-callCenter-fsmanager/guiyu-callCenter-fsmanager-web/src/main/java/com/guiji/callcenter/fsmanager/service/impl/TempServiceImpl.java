package com.guiji.callcenter.fsmanager.service.impl;

import com.guiji.callcenter.fsmanager.config.Constant;
import com.guiji.callcenter.fsmanager.manager.EurekaManager;
import com.guiji.callcenter.fsmanager.service.TempService;
import com.guiji.component.result.Result;
import com.guiji.fsagent.api.ITemplate;
import com.guiji.utils.FeignBuildUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TempServiceImpl implements TempService{
    private final Logger logger = LoggerFactory.getLogger(TempServiceImpl.class);

    @Autowired
    EurekaManager eurekaManager;

    @Override
    public Boolean istempexist(String tempId) {
        logger.info("调用所有的fsagent服务的模板是否存在接口");
        List<String> serverList =  eurekaManager.getInstances(Constant.SERVER_NAME_FSAGENT);
        for(String server:serverList){
            ITemplate iTemplateApi = FeignBuildUtil.feignBuilderTarget(ITemplate.class,Constant.PROTOCOL +server);
            //调用fsagent模板是否存在接口
            Result.ReturnData<Boolean> result = iTemplateApi.istempexist(tempId);
            if(!result.body){
                return false;
            }
        }
        return true;
    }

    @Override
    public Boolean downloadtempwav(String tempId) {
        List<String> serverList =  eurekaManager.getInstances(Constant.SERVER_NAME_FSAGENT);
        for(String server:serverList){
            ITemplate iTemplateApi = FeignBuildUtil.feignBuilderTarget(ITemplate.class,Constant.PROTOCOL +server);
            //调用fsagent下载模板录音接口
            Result.ReturnData<Boolean> result = iTemplateApi.downloadbotwav(tempId);
            //疑问：是否需要将失败的fsagent serviceId返回出来
        }
        return true;
    }
}
