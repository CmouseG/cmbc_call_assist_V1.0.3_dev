package com.guiji.ccmanager.api;

import com.guiji.component.result.Result;
import io.swagger.annotations.ApiOperation;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.NotNull;

@FeignClient("guiyu-callcenter-ccmanager")
public interface INoticeLabel {

    @ApiOperation(value = "查询意向标签")
    @GetMapping(value = "queryNoticeIntent")
    Result.ReturnData<String> queryNoticeIntent(@NotEmpty(message="企业的组织编码不能为空") @RequestParam("orgCode") String orgCode);

    @ApiOperation(value = "修改意向标签,勾选的标签以逗号拼接传递到labels字段")
    @GetMapping(value = "updateNoticeIntent")
    Result.ReturnData updateNoticeIntent(@NotEmpty(message="企业的组织编码不能为空") @RequestParam("orgCode") String orgCode,
                                         @NotNull(message="标签不能不传")  @RequestParam("labels") String labels);
}
