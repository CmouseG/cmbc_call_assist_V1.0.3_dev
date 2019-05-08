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
        // 将tts合成的语音文件删掉
        deleteTtsFiles(new File(pathConfig.getTempPath()));
    }


    private void deleteTtsFiles(File fileP){
        for (File file : fileP.listFiles()) {
            if(file.isDirectory()){
                if(file.getName().equals("tts")){
                    FileUtil.deleteFile(file);
                }else{
                    deleteTtsFiles(file);
                }
            }
        }
    }
}
