package com.guiji.ccmanager.controller;

import com.guiji.callcenter.dao.entity.LineInfo;
import com.guiji.ccmanager.constant.Constant;
import com.guiji.ccmanager.service.LineInfoService;
import com.guiji.ccmanager.vo.LineInfoVO;
import com.guiji.common.result.Result;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Auther: 黎阳
 * @Date: 2018/10/25 0025 17:31
 * @Description: 线路的增删改查
 */
@RestController
public class LineInfoController {

    @Autowired
    private LineInfoService lineInfoService;


    @ApiOperation(value = "查看客户所有线路接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "customerId", value = "客户Id", dataType = "String", paramType = "query")
    })
    @GetMapping(value="lineinfos")
    public Result.ReturnData<List<LineInfo>> getLineInfoByCustom(String customerId){

        if(StringUtils.isBlank(customerId)){
            return Result.error(Constant.ERROR_PARAM);
        }
        List<LineInfo> list =  lineInfoService.getLineInfoByCustom(customerId);
        return Result.ok(list);
    }

    @ApiOperation(value = "增加线路接口")
    @PostMapping(value="lineinfos")
    public Result.ReturnData<Boolean> addLineInfo(@RequestBody LineInfoVO lineInfoVO){

        if(lineInfoVO.getCustomerId()==null || lineInfoVO.getSipIp()==null || lineInfoVO.getSipPort() == null){
            return Result.error(Constant.ERROR_PARAM);
        }

        boolean b = lineInfoService.addLineInfo(lineInfoVO);
        return Result.ok(b);
    }

    @ApiOperation(value = "修改线路接口")
    @PutMapping(value="lineinfos/{id}")
    public Result.ReturnData<Boolean> updateLineInfo(@PathVariable("id") String id,@RequestBody LineInfoVO lineInfoVO){

        if(lineInfoVO.getLineId()==0){
            return Result.error(Constant.ERROR_PARAM);
        }

        return lineInfoService.updateLineInfo(lineInfoVO);
    }

    @ApiOperation(value = "删除线路接口")
    @DeleteMapping(value="lineinfos/{id}")
    public Result.ReturnData<Boolean> deleteLineInfo(@PathVariable("id") String id){
        boolean b = lineInfoService.delLineInfo(id);
        return Result.ok(b);
    }

}
