package com.guiji.process.server.service;

import com.guiji.common.model.process.ProcessInstanceVO;

import java.util.Map;

public interface IProcessInstanceManageService {

    /**
     * 注册
     * @param ip 设备
     * @return
     */
     Map<Object, Object> query(String ip);

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


    void update(String ip, Map<Object, Object> map);

}
