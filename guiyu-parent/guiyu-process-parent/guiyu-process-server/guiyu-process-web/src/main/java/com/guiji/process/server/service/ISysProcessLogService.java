package com.guiji.process.server.service;

import com.guiji.common.model.Page;
import com.guiji.process.core.vo.CmdTypeEnum;
import com.guiji.process.server.dao.entity.SysProcess;
import com.guiji.process.server.dao.entity.SysProcessLog;

import java.util.List;

/**
 * Created by ty on 2018/11/28.
 */
public interface ISysProcessLogService {
    public boolean insert(SysProcessLog sysProcessLog);

    public boolean delete(long id);

    public Page<SysProcessLog> queryProcessLogPage(int pageNo, int pageSize, SysProcessLog sysProcessLog);
}
