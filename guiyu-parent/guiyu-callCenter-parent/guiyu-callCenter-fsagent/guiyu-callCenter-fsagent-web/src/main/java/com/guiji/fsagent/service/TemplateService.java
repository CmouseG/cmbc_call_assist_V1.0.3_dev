package com.guiji.fsagent.service;

import com.guiji.component.result.Result;
import com.guiji.fsagent.entity.RecordReqVO;
import com.guiji.fsagent.entity.RecordVO;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface TemplateService {
    public Boolean istempexist(String tempId);

    public Boolean downloadbotwav(String tempId);

    public Result.ReturnData<Boolean> downloadttswav(String tempId,String callId);

    public Result.ReturnData<RecordVO> uploadrecord(RecordReqVO recordReqVO);
}
