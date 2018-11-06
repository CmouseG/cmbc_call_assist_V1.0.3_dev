package com.guiji.fsagent.job;

import com.guiji.fsagent.manager.ClearRecordManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class JobSchedule {
    @Autowired
    ClearRecordManager clearRecordManager;
    @Scheduled(cron = "${job.clearRecord}")
    public void clearTempJob(){
        clearRecordManager.clearRecordJob();
    }
}
