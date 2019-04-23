package com.guiji.fsagent.service;

import com.guiji.fsagent.entity.RecordReqVO;
import com.guiji.fsagent.entity.RecordVO;
import com.guiji.fsagent.entity.TtsWav;
import com.guiji.fsagent.entity.WavLengthVO;

import java.util.List;
import java.util.Map;

public interface TemplateService {
     boolean istempexist(String tempId);

    List<TtsWav> downloadttswav(String tempId, String planUuid, String callId);

   // RecordVO uploadrecord(RecordReqVO recordReqVO);

     List<WavLengthVO> getwavlength(String tempId);

//   void  uploadrecord2(RecordReqVO recordReqVO);
}
