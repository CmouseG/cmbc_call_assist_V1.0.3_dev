package com.guiji.process.agent.service;

import com.guiji.common.model.process.ProcessStatusEnum;
import com.guiji.process.core.message.CmdMessageVO;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by zhujiayu on 2018/11/21.
 */
public class ProcessStatusLocal {

    private static final ProcessStatusLocal instance = new ProcessStatusLocal();

    private static Map<Integer, ProcessStatusEnum> lacalStatus = null;

    private ProcessStatusLocal()
    {
        lacalStatus = new ConcurrentHashMap<Integer, ProcessStatusEnum>();
    }

    public static ProcessStatusLocal getInstance()
    {
        if(lacalStatus == null)
        {
            lacalStatus = new ConcurrentHashMap<Integer, ProcessStatusEnum>();
        }
        return ProcessStatusLocal.instance;
    }

    public void put(Integer port, ProcessStatusEnum processStatus) {
        lacalStatus.put(port, processStatus);
    }

    public boolean hasChanged(Integer port, ProcessStatusEnum processStatus) {
        boolean result = true;

        if( lacalStatus.get(port) == processStatus)
        {
            result = false;
        }
        return result;
    }

}
