package com.guiji.ccmanager.controller;

import com.guiji.ccmanager.feign.LineOperApiFeign;
import com.guiji.ccmanager.service.LineInfoService;
import com.guiji.common.model.ServerResult;
import com.guiji.fsmanager.entity.LineInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Auther: 黎阳
 * @Date: 2018/10/25 0025 17:31
 * @Description:
 */
@RestController
public class LineInfoController {

    @Autowired
    private LineInfoService lineInfoService;
    @Autowired
    private LineOperApiFeign lineOperApiFeign;

    @ApiOperation(value = "查看客户所有线路接口")
    @GetMapping(value="lineinfos")
    public ServerResult getLineInfoByCustom(String customerId){

        List list =  lineInfoService.getLineInfoByCustom(customerId);
        return ServerResult.create("0300000","success",list);
    }

    @ApiOperation(value = "增加线路接口")
    @PostMapping(value="lineinfos")
    public ServerResult addLineInfo(){

        LineInfo LineInfo = new LineInfo();
//        lineOperApiFeign.addLineinfos(LineInfo);

        return ServerResult.create("0300000","success",null);
    }


}
