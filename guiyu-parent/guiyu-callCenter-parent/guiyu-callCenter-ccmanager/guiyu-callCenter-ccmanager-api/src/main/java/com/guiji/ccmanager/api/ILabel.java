package com.guiji.ccmanager.api;

import com.guiji.component.result.Result;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("guiyu-callcenter-ccmanager")
public interface ILabel {

    @GetMapping("getAllLabelOneMonth")
    Result.ReturnData<List<String>> getAllLabelOneMonth(@RequestParam(value="orgCode") String orgCode,@RequestParam(value="userId") Long userId,
                                                        @RequestParam(value="authLevel") Integer authLevel);
}
