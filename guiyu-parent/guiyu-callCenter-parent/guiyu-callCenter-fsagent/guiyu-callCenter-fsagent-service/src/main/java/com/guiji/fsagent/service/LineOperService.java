package com.guiji.fsagent.service;

import com.guiji.common.result.Result;
import com.guiji.fsagent.api.LineOperApi;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LineOperService implements LineOperApi {
    @Override
    public Result.ReturnData updatenotify(String type, String lineId) {
        return Result.ok(true);
    }

    @Override
    public Result.ReturnData<Boolean> deleteLineinfos(String lineId) {
        return Result.ok(true);
    }
}
