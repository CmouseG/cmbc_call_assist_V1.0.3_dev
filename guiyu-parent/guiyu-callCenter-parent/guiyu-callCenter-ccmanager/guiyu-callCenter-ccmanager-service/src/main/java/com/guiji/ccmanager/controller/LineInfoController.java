package com.guiji.ccmanager.controller;

import com.guiji.callcenter.dao.entity.LineInfo;
import com.guiji.ccmanager.entity.LineConcurrent;
import com.guiji.ccmanager.service.LineInfoService;
import com.guiji.ccmanager.vo.LineInfoVO;
import com.guiji.utils.BeanUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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


    @ApiOperation(value = "查看客户所有线路接口")
    @GetMapping(value="lineinfos")
    public List getLineInfoByCustom(String customerId){

        List list =  lineInfoService.getLineInfoByCustom(customerId);
        return list;
    }

    @ApiOperation(value = "增加线路接口")
    @PostMapping(value="lineinfos")
    public Boolean addLineInfo(LineInfoVO lineInfoVO){

        lineInfoService.addLineInfo(lineInfoVO);
        return true;
    }

    @ApiOperation(value = "修改线路接口")
    @PutMapping(value="lineinfos/{id}")
    public Boolean updateLineInfo(@PathVariable("id") String id,LineInfoVO lineInfoVO){

        lineInfoService.updateLineInfo(lineInfoVO);
        return true;
    }

    @ApiOperation(value = "删除线路接口")
    @DeleteMapping(value="lineinfos/{id}")
    public Boolean deleteLineInfo(@PathVariable("id") String id){

        lineInfoService.delLineInfo(id);
        return true;
    }



}
