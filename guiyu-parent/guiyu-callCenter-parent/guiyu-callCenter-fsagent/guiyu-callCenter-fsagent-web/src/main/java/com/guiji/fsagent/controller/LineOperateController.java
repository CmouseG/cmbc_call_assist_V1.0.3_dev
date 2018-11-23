package com.guiji.fsagent.controller;

import com.guiji.component.result.Result;
import com.guiji.fsagent.api.ILineOperate;
import com.guiji.fsagent.config.Constant;
import com.guiji.fsagent.config.FsConfig;
import com.guiji.fsagent.entity.FreeSWITCH;
import com.guiji.fsagent.manager.ApplicationInit;
import com.guiji.fsagent.service.LineOperateService;
import com.guiji.fsagent.util.FileUtil;

import com.guiji.fsmanager.api.ILineOper;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class LineOperateController implements ILineOperate {
    private final Logger logger = LoggerFactory.getLogger(LineOperateController.class);

    @Autowired
    LineOperateService lineOperateService;
    @Autowired
    ILineOper lineOperApiFeign;
    @Autowired
    FsConfig fsConfig;
    @Autowired
    ApplicationInit pplicationInit;
    @Override
    @RequestMapping(value = "/updatenotify", method = RequestMethod.GET)
    public Result.ReturnData<Boolean> updatenotify(@RequestParam("type") String type, @RequestParam("lineId") String lineId) {
        logger.info("收到更新配置信息通知请求，type[{}], lineId[{}]", type,lineId);

        if(StringUtils.isBlank(type)||StringUtils.isBlank(lineId)){
            logger.info("更新配置信息通知请求失败，参数错误，为null或空");
            return Result.error(Constant.ERROR_CODE_PARAM);
        }
        if(!type.equals("line")){
            logger.info("更新配置信息通知请求失败，请求参数type不是line，直接返回错误");
              return Result.error(Constant.ERROR_CODE_NOT_LINE);
        }
        return Result.ok(lineOperateService.updatenotify(lineId));
    }

    @Override
    public Result.ReturnData<Boolean> deleteLineinfos(@RequestParam ("lineId") String lineId) {
        logger.debug("收到删除线路配置的请求，lineId[{}]",lineId);
        if(StringUtils.isBlank(lineId)){
            logger.info("除线路配置的请求失败，参数错误，为null或空");
            return Result.error(Constant.ERROR_CODE_PARAM);
        }
        //获取fs对象
        FreeSWITCH fs = pplicationInit.getFreeSwitch();
        FileUtil.delete(fs.getDialplan()+"01_"+lineId+".xml");
        FileUtil.delete(fs.getGateway()+"gw_"+lineId+".xml");
        fs.execute("sofia profile external killgw gw_"+ lineId);
        return Result.ok();
    }
}
