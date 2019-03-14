package com.guiji.callcenter.fsmanager.controller;

import com.guiji.callcenter.fsmanager.config.Constant;
import com.guiji.callcenter.fsmanager.manager.EurekaManager;
import com.guiji.callcenter.fsmanager.service.LineService;
import com.guiji.component.result.Result;
import com.guiji.fsmanager.api.ILineOper;
import com.guiji.fsmanager.entity.LineInfoVO;
import com.guiji.fsmanager.entity.LineXmlnfoVO;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class LineController implements ILineOper {
    private final Logger logger = LoggerFactory.getLogger(LineController.class);

    @Autowired
    LineService lineService;
    @Autowired
    EurekaManager eurekaManager;

    @Override
    public Result.ReturnData addLineinfos(@RequestBody LineInfoVO lineInfo) {
        logger.info("收到增加线路接口请求，LineInfoVO[{}]", lineInfo);
        if (StringUtils.isBlank(lineInfo.getLineId()) || StringUtils.isBlank(lineInfo.getSipIp()) || StringUtils.isBlank(lineInfo.getSipPort())) {
            logger.info("增加线路接口请求失败，参数错误，为null或空");
            return Result.error(Constant.ERROR_CODE_PARAM);
        }
        if (!lineService.addLineinfos(lineInfo)) {
            logger.info("增加线路接口请求失败，线路重名");
            return Result.error(Constant.ERROR_CODE_LINE_REPEAT);
        }
        return Result.ok();
    }

    @Override
    public Result.ReturnData editLineinfos(@PathVariable("lineId") String lineId, @RequestBody LineInfoVO lineInfo) {
        logger.info("收到修改线路接口请求，lineId[{}]，LineInfoVO[{}]",lineId,lineInfo);
        if(StringUtils.isBlank(lineId)||StringUtils.isBlank(lineInfo.getSipIp())||StringUtils.isBlank(lineInfo.getSipPort())){
            logger.info("增加修改线路接口请求失败，参数错误，为null或空");
            return Result.error(Constant.ERROR_CODE_PARAM);
        }
        lineService.editLineinfos(lineId,lineInfo);
        return Result.ok();
    }

    @Override
    public Result.ReturnData deleteLineinfos(@PathVariable("lineId") String lineId) {
        logger.info("收到删除线路接口请求，lineId[{}]",lineId);
        if(StringUtils.isBlank(lineId)){
            logger.info("删除线路接口请求失败，参数错误，为null或空");
            return Result.error(Constant.ERROR_CODE_PARAM);
        }
        lineService.deleteLineinfos(lineId);
        return Result.ok();
    }

    @Override
    public Result.ReturnData linexmlinfos(@PathVariable(value = "lineId") String lineId) {
        logger.info("收到获取线路配置文件接口请求，lineId[{}]",lineId);
        if(StringUtils.isBlank(lineId)){
            logger.info("获取线路配置文件接口请求失败，参数错误，为null或空");
            return Result.error(Constant.ERROR_CODE_PARAM);
        }
        List<LineXmlnfoVO> list =  lineService.linexmlinfos(lineId);
      //  logger.info("获取线路配置文件接口请求返回：，LineXmlnfoVO[{}]",list);
        return Result.ok(list);
    }

    @Override
    public Result.ReturnData linexmlinfosAll() {
        logger.info("收到获取所有线路配置文件接口请求");
        List<LineXmlnfoVO> list =  lineService.linexmlinfosAll();
      //  logger.info("收到获取所有线路配置文件接口返回：，LineXmlnfoVO[{}]",list);
        return Result.ok(list);
    }

    @Override
    public Result.ReturnData batchLinesinfos(@RequestBody List<LineInfoVO> lineInfo) {
        logger.info("收到增加线路接口请求，LineInfoVO[{}]", lineInfo);
        lineService.batchLinesinfos(lineInfo);
        return Result.ok();
    }

    @Override
    public Result.ReturnData<List<LineXmlnfoVO>> batchlinexmlinfosAll(@RequestParam("lineIds") String lineIds) {
        String [] values =lineIds.split(",");
        List<LineXmlnfoVO> list =  lineService.batchlinexmlinfosAll( Arrays.asList(values));
        logger.info("获取线路配置文件接口请求返回：，LineXmlnfoVO[{}]",list);
        return Result.ok(list);
    }


}
