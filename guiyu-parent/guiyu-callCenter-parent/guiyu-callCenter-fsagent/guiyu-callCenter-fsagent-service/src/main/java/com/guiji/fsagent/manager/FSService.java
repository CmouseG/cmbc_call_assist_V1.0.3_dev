package com.guiji.fsagent.manager;

import com.guiji.fsagent.config.FsConfig;
import com.guiji.fsagent.entity.FreeSWITCH;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
public class FSService {
    private FreeSWITCH freeSwitch;

    @Autowired
    FsConfig fsConfig;

   // @PostConstruct
    public void init() {
        freeSwitch = new FreeSWITCH(fsConfig.getHomeDir());
    }

    public FreeSWITCH getFreeSwitch() {
        return freeSwitch;
    }

}
