package com.guiji.process.server.model;

import com.guiji.process.server.dao.entity.SysProcess;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ty on 2018/11/26.
 */
public class ProcessCmdVO implements Serializable{
    private List<SysProcess> sysProcessList;

    public List<SysProcess> getSysProcessList() {
        return sysProcessList;
    }

    public void setSysProcessList(List<SysProcess> sysProcessList) {
        this.sysProcessList = sysProcessList;
    }

    @Override
    public String toString() {
        return "ProcessCmdVO{" +
                "sysProcessList=" + sysProcessList +
                '}';
    }
}
