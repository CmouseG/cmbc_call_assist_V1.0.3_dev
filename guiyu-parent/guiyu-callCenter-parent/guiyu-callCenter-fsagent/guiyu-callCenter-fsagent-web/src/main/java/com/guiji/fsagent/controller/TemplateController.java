package com.guiji.fsagent.controller;

import com.guiji.component.result.Result;
import com.guiji.fsagent.api.ITemplate;
import com.guiji.fsagent.entity.RecordReqVO;
import com.guiji.fsagent.entity.RecordVO;
import com.guiji.fsagent.service.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TemplateController implements ITemplate {
    @Autowired
    TemplateService templateService;
    @Override
    public Result.ReturnData<Boolean> istempexist(String tempId) {
        return Result.ok(templateService.istempexist(tempId));
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
    public Result.ReturnData<RecordVO> uploadrecord(@RequestBody RecordReqVO recordReqVO) {
        RecordVO record = new RecordVO();
        record.setFileName(recordReqVO.getFileName());
        record.setFileUrl("http://192.168.1.22/"+recordReqVO.getFileName());
        return Result.ok(record);
    }
}
