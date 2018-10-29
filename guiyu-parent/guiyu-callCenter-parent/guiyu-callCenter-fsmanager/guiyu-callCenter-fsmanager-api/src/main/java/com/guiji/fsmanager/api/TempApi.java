package com.guiji.fsmanager.api;

import com.guiji.common.result.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Auther: 黎阳
 * @Date: 2018/10/26 0026 17:19
 * @Description:
 */
public interface TempApi {

    @ApiOperation(value = "下载模板录音")
    @GetMapping(value="downloadtempwav")
    public Result.ReturnData downloadtempwav(String tempId);
}
