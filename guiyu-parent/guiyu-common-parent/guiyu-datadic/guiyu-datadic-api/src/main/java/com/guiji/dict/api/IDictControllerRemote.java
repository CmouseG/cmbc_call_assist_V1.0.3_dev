package com.guiji.dict.api;

import com.guiji.common.result.Result;
import com.guiji.dict.dao.entity.SysDict;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created by ty on 2018/10/29.
 */
@Api(tags="数据字典接口(Feign接口)")
@FeignClient("datadic")
public interface IDictControllerRemote {
    @ApiOperation(value="查询字典", notes="根据字典类型")
    @ApiImplicitParams({
            @ApiImplicitParam(name="dictType",value="字典类型",required=true)
    })
    @RequestMapping(value = "/remote/getDictByType", method = RequestMethod.POST)
    public Result.ReturnData<List<SysDict>> getDictByType(String dictType);

    @ApiOperation(value="查询字典", notes="根据字典类型和字典标签名")
    @ApiImplicitParams({
            @ApiImplicitParam(name="dictType",value="字典类型",required=true),
            @ApiImplicitParam(name="dictKey",value="字典标签",required=true)
    })
    @RequestMapping(value = "/remote/getDictValue", method = RequestMethod.POST)
    public Result.ReturnData<List<SysDict>> getDictValue(String dictType, String dictKey);
}
