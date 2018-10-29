package com.guiji.fsline.controller;

import com.guiji.common.result.Result;
import com.guiji.fsline.api.IFsLineApi;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LineInfoController implements IFsLineApi {
    @Override
    @GetMapping("/fsinfo")
    public Result.ReturnData getFsInfo() {
        return Result.ok("test hello world");
    }
}
