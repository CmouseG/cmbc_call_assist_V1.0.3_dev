package com.guiji.fsagent.manager;

import com.guiji.fsagent.config.PathConfig;
import com.guiji.fsagent.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class ClearRecordManager {
    @Autowired
    PathConfig pathConfig;
    public void clearRecordJob(){
        FileUtil.deleteFilesByDay(pathConfig.getRecordPath(),3);
        // 将tts合成的语音文件删掉
        FileUtil.deleteFile(new File(pathConfig.getTempPath()+"tts"));
    }
}
