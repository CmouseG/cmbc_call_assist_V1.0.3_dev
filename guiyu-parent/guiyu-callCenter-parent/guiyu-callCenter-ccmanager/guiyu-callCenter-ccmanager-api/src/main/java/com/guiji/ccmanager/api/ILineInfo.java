package com.guiji.ccmanager.api;

import com.guiji.ccmanager.vo.LineInfoVO;
import com.guiji.component.result.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Auther: 黎阳
 * @Date: 2018/11/27 0027 14:56
 * @Description:
 */
@FeignClient("guiyu-callcenter-ccmanager")
public interface ILineInfo {

    @ApiOperation(value = "修改线路接口")
    @PostMapping(value="updateLineInfo")
    Result.ReturnData<Boolean> updateLineInfo(@RequestBody LineInfoVO lineInfoVO);
}
