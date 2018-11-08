package com.guiji.callcenter.fsmanager.controller;

import com.guiji.callcenter.fsmanager.service.TempService;
import com.guiji.component.result.Result;
import com.guiji.fsmanager.api.ITemp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TempController implements ITemp {
    @Autowired
    TempService tempService;
    @Override
    public Result.ReturnData downloadtempwav(String tempId) {
        return Result.ok(tempService.downloadtempwav(tempId));
    }

    @Override
    public Result.ReturnData<Boolean> istempexist(String tempId) {
        return Result.ok(tempService.istempexist(tempId));
    }
}
