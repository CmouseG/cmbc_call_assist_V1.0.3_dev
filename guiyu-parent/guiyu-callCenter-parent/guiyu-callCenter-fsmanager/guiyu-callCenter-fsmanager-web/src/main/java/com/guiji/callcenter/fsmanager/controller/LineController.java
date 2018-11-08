package com.guiji.callcenter.fsmanager.controller;

import com.guiji.callcenter.fsmanager.config.Constant;
import com.guiji.callcenter.fsmanager.manager.EurekaManager;
import com.guiji.callcenter.fsmanager.service.LineService;
import com.guiji.component.result.Result;
import com.guiji.fsagent.api.ILineOperate;
import com.guiji.fsmanager.api.ILineOper;
import com.guiji.fsmanager.entity.LineInfoVO;
import com.guiji.fsmanager.entity.LineXmlnfoVO;
import com.guiji.utils.FeignBuildUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LineController implements ILineOper {
    @Autowired
    LineService lineService;
    @Autowired
    EurekaManager eurekaManager;

    @Override
    public Result.ReturnData addLineinfos(@RequestBody LineInfoVO lineInfo) {
        if(StringUtils.isBlank(lineInfo.getLineId())||StringUtils.isBlank(lineInfo.getSipIp())||StringUtils.isBlank(lineInfo.getSipPort())){
            return Result.error("3001001");
        }
           if(!lineService.addLineinfos(lineInfo)){
               return  Result.error("0301003");
           }
        return Result.ok();
    }

    @Override
    public Result.ReturnData editLineinfos(@PathVariable("lineId") String lineId, @RequestBody LineInfoVO lineInfo) {
        if(StringUtils.isBlank(lineId)||StringUtils.isBlank(lineInfo.getSipIp())||StringUtils.isBlank(lineInfo.getSipPort())){
            return Result.error("3001001");
        }
        lineService.editLineinfos(lineId,lineInfo);
        return Result.ok();
    }

    @Override
    public Result.ReturnData deleteLineinfos(@PathVariable("lineId") String lineId) {
        lineService.deleteLineinfos(lineId);
        return Result.ok();
    }

    @Override
    public Result.ReturnData linexmlinfos(@PathVariable(value = "lineId") String lineId) {
        if(StringUtils.isBlank(lineId)){
            return Result.error("3001001");
        }
        List<LineXmlnfoVO> list =  lineService.linexmlinfos(lineId);
        return Result.ok(list);
    }

    @Override
    public Result.ReturnData linexmlinfosAll() {
        List<LineXmlnfoVO> list =  lineService.linexmlinfosAll();
        return Result.ok(list);
    }
}
