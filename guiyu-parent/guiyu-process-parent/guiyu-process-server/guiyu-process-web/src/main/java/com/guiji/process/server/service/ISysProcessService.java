package com.guiji.process.server.service;

import com.guiji.common.model.Page;
import com.guiji.process.core.vo.CmdTypeEnum;
import com.guiji.process.server.dao.entity.SysProcess;

import java.util.List;

/**
 * Created by ty on 2018/11/24.
 */
public interface ISysProcessService {
    public boolean insert(SysProcess sysProcess);

    public boolean update(SysProcess sysProcess);

    public List<SysProcess> list(SysProcess sysProcess);

    public boolean delete(int id);

    public Page<SysProcess> queryProcessPage(int pageNo, int pageSize, SysProcess sysProcess);

    public Page<SysProcess> queryAgentPage(int pageNo, int pageSize, SysProcess sysProcess);

    public void executeCmd(List<SysProcess> sysProcessList, CmdTypeEnum cmdTypeEnum,Long userId);
}
