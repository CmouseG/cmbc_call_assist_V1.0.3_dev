package com.guiji.calloutserver;

import com.google.common.base.Strings;
import com.guiji.callcenter.dao.entity.CallOutDetail;
import com.guiji.callcenter.dao.entity.CallOutDetailRecord;
import com.guiji.callcenter.dao.entity.CallOutRecord;
import com.guiji.calloutserver.enm.ECallDetailType;
import com.guiji.calloutserver.eventbus.handler.AfterCallHandler;
import com.guiji.calloutserver.manager.impl.FsAgentManagerImpl;
import com.guiji.calloutserver.service.CallOutDetailRecordService;
import com.guiji.calloutserver.service.CallOutDetailService;
import com.guiji.calloutserver.service.CallOutRecordService;
import com.guiji.component.result.Result;
import com.guiji.fsagent.api.ITemplate;
import com.guiji.fsagent.entity.RecordReqVO;
import com.guiji.fsagent.entity.RecordVO;
import com.guiji.robot.api.IRobotRemote;
import com.guiji.robot.model.AiCallNext;
import com.guiji.robot.model.AiCallStartReq;
import com.guiji.utils.FeignBuildUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * @Auther: 黎阳
 * @Date: 2018/11/19 0019 18:18
 * @Description:
 */
@RestController
public class Test {

    @Autowired
    IRobotRemote robotRemote;
    @Autowired
    CallOutRecordService callOutRecordService;
    @Autowired
    CallOutDetailRecordService callOutDetailRecordService;
    @Autowired
    AfterCallHandler afterCallHandler;
    @Autowired
    FsAgentManagerImpl fsAgentManagerImpl;

    private ITemplate iTemplate;

    @RequestMapping("test1")
    public void test1() {

        fsAgentManagerImpl.uploadRecord("1e3d9806da3345b581781ee68e29bs06", "1e3d9806da3345b581781ee68e29bs06.wav", "mainrecord");

       /* CallOutRecord callRecord = callOutRecordService.findByCallId("1e3d9806da3345b581781ee68e29bq06");
        afterCallHandler.uploadMainRecord(callRecord);

        List<CallOutDetailRecord> callOutDetailRecords = callOutDetailRecordService.findByCallId("1e3d9806da3345b581781ee68e29bq06");
        afterCallHandler.uploadDetailsRecord(callOutDetailRecords);*/
/*        iTemplate = FeignBuildUtil.feignBuilderTarget(ITemplate.class,"http://192.168.1.78:18006");
        RecordReqVO request = new RecordReqVO();
        request.setBusiId("1e3d9806da3345b581781ee68e29bq06");
        request.setFileName("1e3d9806da3345b581781ee68e29bq06.wav");
        request.setSysCode("guiyu-callCenter-calloutserver");
        request.setBusiType("mainrecord");
        iTemplate.uploadrecord(request);*/

    }


}
