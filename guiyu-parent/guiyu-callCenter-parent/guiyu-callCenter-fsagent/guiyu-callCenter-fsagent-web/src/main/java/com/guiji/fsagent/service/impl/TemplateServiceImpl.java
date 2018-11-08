package com.guiji.fsagent.service.impl;

import com.guiji.fsagent.config.PathConfig;
import com.guiji.fsagent.service.TemplateService;
import com.guiji.fsagent.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TemplateServiceImpl implements TemplateService {
    @Autowired
    PathConfig pathConfig;
    @Override
    public Boolean istempexist(String tempId) {
       String tempPath =  pathConfig.getTempPath()+tempId;
        return FileUtil.isExist(tempPath);
    }

    @Override
    public Boolean downloadbotwav(String tempId) {
      String tempRecordPath = pathConfig.getTempRecordPath()+tempId;
        //判断模板录音是否已存在，存在则返回
        if(FileUtil.isExist(tempRecordPath)){
            return true;
        };


        return null;
    }
}
