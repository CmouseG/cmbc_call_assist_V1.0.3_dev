package com.guiji.fsagent.controller;

import com.guiji.component.result.Result;
import com.guiji.fsagent.api.ITemplateApi;
//import com.guiji.fsagent.api.TemplateApi;
import com.guiji.fsagent.entity.RecordVO;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TemplateController implements ITemplateApi {
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
    public Result.ReturnData<RecordVO> uploadrecord(String fileName) {
        RecordVO record = new RecordVO();
        record.setFileName(fileName);
        record.setFileUrl("http://192.168.1.22/"+fileName);
        return Result.ok(record);
    }
}
