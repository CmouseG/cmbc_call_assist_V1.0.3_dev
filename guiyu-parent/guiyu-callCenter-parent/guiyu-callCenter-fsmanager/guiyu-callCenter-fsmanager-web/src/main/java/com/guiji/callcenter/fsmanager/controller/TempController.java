package com.guiji.callcenter.fsmanager.controller;

import com.guiji.common.result.Result;
import com.guiji.fsmanager.api.ITempApi;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TempController implements ITempApi {
    @Override
    public Result.ReturnData downloadtempwav(String tempId) {
        return Result.ok();
    }

    @Override
    public Result.ReturnData<Boolean> istempexist(String tempId) {
        return Result.ok(true);
    }
}
