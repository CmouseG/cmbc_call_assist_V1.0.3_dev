package com.guiji.fsagent.job;

import com.guiji.fsagent.manager.ClearRecordManager;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//@Component
//@EnableScheduling
//public class JobSchedule {
//    @Autowired
//    ClearRecordManager clearRecordManager;
//    @Scheduled(cron = "${job.clearRecord}")
//    public void clearTempJob(){
//        clearRecordManager.clearRecordJob();
//    }
//}
@JobHandler(value = "FsAgentClearTTSRecordJobHandler")
@Component
@Slf4j
public class JobSchedule extends IJobHandler {
    @Autowired
    ClearRecordManager clearRecordManager;

    @Override
    public ReturnT<String> execute(String param) throws Exception {
        log.info("每天刪除tts合成录音文件的job，FsAgentClearTTSRecordJobHandler开始");
        clearRecordManager.clearRecordJob();
        log.info("每天刪除tts合成录音文件的job，FsAgentClearTTSRecordJobHandler结束");
        return SUCCESS;
    }
}
