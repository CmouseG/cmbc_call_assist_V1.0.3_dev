package com.guiji.dict.api;

import com.guiji.component.result.Result;
import com.guiji.component.result.Result.ReturnData;
import com.guiji.dict.dao.entity.SysDict;
import com.guiji.dict.vo.SysDictVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by ty on 2018/10/29.
 */
@Api(tags="数据字典接口(Feign接口)")
@FeignClient("guiyu-datadic")
public interface ISysDict {
    @ApiOperation(value="查询字典", notes="根据字典类型")
    @ApiImplicitParams({
            @ApiImplicitParam(name="dictType",value="字典类型",required=true)
    })
    @RequestMapping(value = "/getDictByType", method = RequestMethod.POST)
    ReturnData<List<SysDictVO>> getDictByType(String dictType);

   /* @ApiOperation(value="查询字典", notes="根据字典类型和字典标签名")
    @ApiImplicitParams({
            @ApiImplicitParam(name="dictType",value="字典类型",required=true),
            @ApiImplicitParam(name="dictKey",value="字典标签",required=true)
    })
    @RequestMapping(value = "/getDictValue", method = RequestMethod.POST)
    ReturnData<List<SysDictVO>> getDictValue(String dictType, String dictKey);*/

    @ApiOperation(value="查询字典", notes="根据字典类型和字典标签名")
    @ApiImplicitParams({
            @ApiImplicitParam(name="dictType",value="字典类型",required=true),
            @ApiImplicitParam(name="dictKey",value="字典标签",required=true)
    })
    @GetMapping(value = "/getDictValueByTypeKey")
    ReturnData<List<SysDictVO>> getDictValueByTypeKey(@RequestParam("dictType")String dictType, @RequestParam("dictKey")String dictKey);
}
