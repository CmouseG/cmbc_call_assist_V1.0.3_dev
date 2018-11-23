package com.guiji.fsagent.service;

import com.guiji.fsagent.entity.RecordReqVO;
import com.guiji.fsagent.entity.RecordVO;
import com.guiji.fsagent.entity.WavLengthVO;

import java.util.List;

public interface TemplateService {
     boolean istempexist(String tempId);

     boolean downloadttswav(String tempId,String callId);

     RecordVO uploadrecord(RecordReqVO recordReqVO);

     List<WavLengthVO> getwavlength(String tempId);
}
