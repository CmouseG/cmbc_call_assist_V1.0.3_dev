package com.guiji.ccmanager.controller;

import com.guiji.ccmanager.service.LineInfoService;
import com.guiji.ccmanager.vo.LineInfoVO;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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
    public List getLineInfoByCustom(String customerId){

        List list =  lineInfoService.getLineInfoByCustom(customerId);
        return list;
    }

    @ApiOperation(value = "增加线路接口")
    @PostMapping(value="lineinfos")
    public Boolean addLineInfo(@RequestBody LineInfoVO lineInfoVO){
        return lineInfoService.addLineInfo(lineInfoVO);
    }

    @ApiOperation(value = "修改线路接口")
    @PutMapping(value="lineinfos/{id}")
    public Boolean updateLineInfo(@PathVariable("id") String id,@RequestBody LineInfoVO lineInfoVO){

         return lineInfoService.updateLineInfo(lineInfoVO);
    }

    @ApiOperation(value = "删除线路接口")
    @DeleteMapping(value="lineinfos/{id}")
    public Boolean deleteLineInfo(@PathVariable("id") String id){

        return lineInfoService.delLineInfo(id);
    }



}
