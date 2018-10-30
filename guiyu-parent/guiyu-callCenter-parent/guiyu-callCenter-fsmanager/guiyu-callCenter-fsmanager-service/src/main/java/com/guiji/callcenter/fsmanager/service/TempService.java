package com.guiji.callcenter.fsmanager.service;

import com.guiji.common.result.Result;
import com.guiji.fsmanager.api.TempApi;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TempService implements TempApi {
    @Override
    public Result.ReturnData downloadtempwav(String tempId) {
        return Result.ok();
    }

    @Override
    public Result.ReturnData<Boolean> istempexist(String tempId) {
        return Result.ok(true);
    }
}
