package com.guiji.fsagent.controller;

import com.guiji.common.result.Result;
import com.guiji.fsagent.api.TemplateApi;
import com.guiji.fsagent.entity.Record;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TemplateController implements TemplateApi {
    @Override
    public Result.ReturnData<Boolean> istempexist(String tempId) {
        return Result.ok(true);
    }

    @Override
    public Result.ReturnData<Boolean> downloadbotwav(String tempId) {
        return Result.ok(true);
    }

    @Override
    public Result.ReturnData<Boolean> downloadttswav(String tempId, String callId) {
        return Result.ok(true);
    }

    @Override
    public Result.ReturnData<Record> uploadrecord(String fileName) {
        Record record = new Record();
        record.setFileName(fileName);
        record.setFileUrl("http://192.168.1.22/"+fileName);
        return Result.ok(record);
    }
}
