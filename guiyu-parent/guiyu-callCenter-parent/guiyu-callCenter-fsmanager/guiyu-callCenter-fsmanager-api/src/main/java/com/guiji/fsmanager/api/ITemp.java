package com.guiji.fsmanager.api;

import com.guiji.component.result.Result;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Auther: 黎阳
 * @Date: 2018/10/26 0026 17:19
 * @Description:
 */
@FeignClient("guiyu-callcenter-fsmanager")
public interface ITemp {
    @ApiOperation(value = "模板是否存在")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tempId", value = "模板Id", dataType = "String", paramType = "query")
    })
    @RequestMapping(value = "/istempexist", method = RequestMethod.GET)
     Result.ReturnData<Boolean> istempexist(@RequestParam("tempId") String tempId);
}
