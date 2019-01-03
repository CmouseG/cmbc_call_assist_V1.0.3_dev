package com.guiji.process.server.service;

import com.guiji.common.model.Page;
import com.guiji.process.server.dao.entity.SysProcess;
import com.guiji.process.server.dao.entity.SysProcessTask;

import java.util.List;

/**
 * Created by ty on 2018/11/28.
 */
public interface ISysProcessTaskService {
    public boolean insert(SysProcessTask sysProcessTask);

    public boolean delete(Integer id);

    public Page<SysProcessTask> queryProcessTaskPage(int pageNo, int pageSize, SysProcessTask sysProcessTask);

    public List<SysProcessTask> list(SysProcessTask sysProcessTask);


    public boolean update(SysProcessTask sysProcessTask);
}
