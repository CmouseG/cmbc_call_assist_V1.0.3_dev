package com.guiji.process.server.service;

import com.guiji.common.model.process.ProcessInstanceVO;
import com.guiji.common.model.process.ProcessStatusEnum;
import com.guiji.common.model.process.ProcessTypeEnum;
import com.guiji.process.core.vo.CmdTypeEnum;

import java.util.List;
import java.util.Map;

public interface IProcessAgentManageService {

    /**
     * 注册
     * @return
     */
    Map<Object, Object> query();

    /**
     * 注册
     * @param processInstance 设备
     * @return
     */
    void add(ProcessInstanceVO processInstance);

    /**
     * 注销
     * @param processInstance 设备
     * @return
     */
    void del(ProcessInstanceVO processInstance);

    /**
     * 注销
     * @param processInstance 设备
     * @return
     */
    ProcessInstanceVO get(String ip, int port);

    void updateStatus(ProcessInstanceVO process);

}
