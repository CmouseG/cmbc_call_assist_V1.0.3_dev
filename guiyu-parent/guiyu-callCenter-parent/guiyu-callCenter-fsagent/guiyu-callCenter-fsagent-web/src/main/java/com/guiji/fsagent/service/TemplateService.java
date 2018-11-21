package com.guiji.fsagent.service;

import com.guiji.component.result.Result;
import com.guiji.fsagent.entity.RecordReqVO;
import com.guiji.fsagent.entity.RecordVO;
import com.guiji.fsagent.entity.WavLengthVO;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface TemplateService {
     Boolean istempexist(String tempId);

     Boolean downloadbotwav(String tempId);

     Result.ReturnData<Boolean> downloadttswav(String tempId,String callId);

     Result.ReturnData<RecordVO> uploadrecord(RecordReqVO recordReqVO);

     Result.ReturnData<List<WavLengthVO>> getwavlength(String tempId);
}
