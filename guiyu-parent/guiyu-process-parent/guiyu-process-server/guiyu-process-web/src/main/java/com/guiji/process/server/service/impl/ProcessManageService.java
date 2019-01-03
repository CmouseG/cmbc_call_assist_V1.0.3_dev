package com.guiji.process.server.service.impl;

import com.guiji.common.constant.RedisConstant;
import com.guiji.common.exception.GuiyuException;
import com.guiji.common.model.process.ProcessInstanceVO;
import com.guiji.common.model.process.ProcessStatusEnum;
import com.guiji.common.model.process.ProcessTypeEnum;
import com.guiji.process.core.message.CmdMessageVO;
import com.guiji.process.core.message.CmdMsgTypeEnum;
import com.guiji.process.core.message.CmdProtoMessage;
import com.guiji.process.core.message.MessageProto;
import com.guiji.process.core.util.CmdMessageUtils;
import com.guiji.process.core.vo.CmdMsgSenderMap;
import com.guiji.process.core.vo.CmdTypeEnum;
import com.guiji.process.server.core.ConnectionPool;
import com.guiji.process.server.dao.entity.SysProcess;
import com.guiji.process.server.dao.entity.SysProcessTask;
import com.guiji.process.server.exception.GuiyuProcessExceptionEnum;
import com.guiji.process.server.model.DeviceProcessConstant;
import com.guiji.process.server.service.*;
import com.guiji.process.server.util.DeviceProcessUtil;
import com.guiji.utils.IdGenUtil;
import com.guiji.utils.JsonUtils;
import com.guiji.utils.RedisUtil;
import io.netty.channel.ChannelHandlerContext;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ProcessManageService implements IProcessManageService {

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private ISysProcessService processService;
    @Autowired
    private ISysProcessTaskService processTaskService;
    @Autowired
    private IProcessInstanceManageService processInstanceManageService;
    @Autowired
    private IProcessAgentManageService processAgentManageService;

    /**
     * 注册
     * @param processInstanceVOS 设备
     */
    @Override
    public void register(List<ProcessInstanceVO> processInstanceVOS) {

        for (ProcessInstanceVO processInstanceVO : processInstanceVOS) {

            if(processInstanceVO.getType() == ProcessTypeEnum.AGENT)
            {
                processAgentManageService.add(processInstanceVO);
            }
            else
            {
                processInstanceManageService.add(processInstanceVO);
            }
        }
    }





    /**
     * 注销
     * @param processInstanceVOS 设备
     */
    @Override
    public void unRegister(List<ProcessInstanceVO> processInstanceVOS) {

        for (ProcessInstanceVO processInstanceVO : processInstanceVOS) {

            if(processInstanceVO.getType() == ProcessTypeEnum.AGENT)
            {
                processAgentManageService.del(processInstanceVO);
            }
            else
            {
                processInstanceManageService.del(processInstanceVO);
            }
        }
    }

    @Override
    public boolean cmd(ProcessInstanceVO processInstanceVO, CmdTypeEnum cmdType, List<String> parameters,Long userId,String reqKey) {
        /*String hasRun = (String)redisUtil.get(RedisConstant.REDIS_PROCESS_TASK_PREFIX + processInstanceVO.getIp()+"_" + processInstanceVO.getPort()+"_"+cmdType);
        if (StringUtils.isNotEmpty(hasRun)) {
            throw new GuiyuException(GuiyuProcessExceptionEnum.PROCESS08000002.getErrorCode(),GuiyuProcessExceptionEnum.PROCESS08000002.getMsg());
        }*/

        if(processInstanceVO == null || cmdType == null)
        {
            return false;
        }

        // 调用底层通信，发送命令
        ChannelHandlerContext ctx = ConnectionPool.getChannel(processInstanceVO.getIp());
        CmdMessageVO cmdMessageVO = new CmdMessageVO();
        cmdMessageVO.setReqKey(reqKey);
        cmdMessageVO.setMsgTypeEnum(CmdMsgTypeEnum.REQ);
        cmdMessageVO.setCmdType(cmdType);
        cmdMessageVO.setProcessInstanceVO(processInstanceVO);
        cmdMessageVO.setParameters(parameters);
        CmdProtoMessage.ProtoMessage.Builder builder = CmdMessageUtils.convert(cmdMessageVO);
        builder.setType(2);

        // 更新数据库
        // 新增sys_process_task
        SysProcess sysProcessTmp = new SysProcess();
        sysProcessTmp.setIp(processInstanceVO.getIp());
        sysProcessTmp.setPort(String.valueOf(processInstanceVO.getPort()));
        List<SysProcess> sysProcessList = processService.list(sysProcessTmp);
        Integer processId = null;
        if (sysProcessList != null && sysProcessList.size()>0) {
            processId = sysProcessList.get(0).getId();
        }

        SysProcessTask sysProcessTask = new SysProcessTask();
        sysProcessTask.setIp(processInstanceVO.getIp());
        sysProcessTask.setPort(String.valueOf(processInstanceVO.getPort()));
        sysProcessTask.setCmdType(cmdType.getValue());
        sysProcessTask.setProcessKey(processInstanceVO.getProcessKey());
        sysProcessTask.setParameters(parameters == null?null:parameters.toString());
        sysProcessTask.setExecStatus(1);
        sysProcessTask.setReqKey(cmdMessageVO.getReqKey());
        sysProcessTask.setProcessId(processId);
        sysProcessTask.setCreateTime(new Date());
        sysProcessTask.setUpdateTime(new Date());
        sysProcessTask.setCreateBy(userId);
        sysProcessTask.setUpdateBy(userId);
        processTaskService.insert(sysProcessTask);
        // 操作写入缓存，控制5分钟内不能重复发起命令
        redisUtil.set(RedisConstant.REDIS_PROCESS_TASK_PREFIX + processInstanceVO.getIp()+"_" + processInstanceVO.getPort()+"_"+cmdType,"hasRun");
        redisUtil.expire(RedisConstant.REDIS_PROCESS_TASK_PREFIX + processInstanceVO.getIp()+"_" + processInstanceVO.getPort()+"_"+cmdType,RedisConstant.REDIS_PROCESS_TASK_EXPIRE);

        ctx.writeAndFlush(builder);
        CmdMsgSenderMap.getInstance().produce(cmdMessageVO);
        return true;

    }

    @Override
    public ProcessStatusEnum getDeviceStatus(ProcessTypeEnum type, String ip, int port) {

        ProcessInstanceVO processInstanceVO = getDevice(type, ip, port);

        if (processInstanceVO == null)
        {
            return ProcessStatusEnum.UNKNOWN;
        }

        return processInstanceVO.getStatus();
    }

    @Override
    public ProcessInstanceVO getDevice(ProcessTypeEnum type, String ip, int port) {

        if(type == ProcessTypeEnum.AGENT)
        {
            return processAgentManageService.get(ip, port);
        }
        else
        {
            return processInstanceManageService.get(ip, port);
        }
    }

    @Override
    public void updateStatus(ProcessInstanceVO processInstanceVO) {

        ProcessInstanceVO oldProcessInstanceVO = getDevice(processInstanceVO.getType(), processInstanceVO.getIp(), processInstanceVO.getPort());

        if(oldProcessInstanceVO != null && oldProcessInstanceVO.getStatus() == processInstanceVO.getStatus())
        {
            return;
        }

        if(processInstanceVO.getType() == ProcessTypeEnum.AGENT)
        {
            processAgentManageService.updateStatus(processInstanceVO);
        }
        else
        {
            processInstanceManageService.updateStatus(processInstanceVO);
        }
    }

    @Override
    public void updateUnRegister(ProcessTypeEnum type, String ip, int port, ProcessStatusEnum status, String whoUsed) {

        ProcessInstanceVO processInstanceVO = new ProcessInstanceVO();
        processInstanceVO.setIp(ip);
        processInstanceVO.setPort(port);
        processInstanceVO.setType(type);
        processInstanceVO.setStatus(status);
        processInstanceVO.setWhoUsed(whoUsed);

        updateStatus(processInstanceVO);
    }

    @Override
    public void updateStatus(ProcessTypeEnum type, String ip, int port, ProcessStatusEnum status) {
        ProcessInstanceVO processInstanceVO = getDevice(type, ip, port);

        if (processInstanceVO == null)
        {
            return;
        }

        if(processInstanceVO.getStatus() == status)
        {
            return;
        }

        processInstanceVO.setStatus(status);

        // 存入数据库
        SysProcess sysProcess = new SysProcess();
        sysProcess.setIp(ip);
        sysProcess.setPort(String.valueOf(port));
        if (status != null) {
            sysProcess.setStatus(status.getValue());
        }
        sysProcess.setUpdateTime(new Date());
        processService.update(sysProcess);
        // 更新redis缓存
        updateStatus(processInstanceVO);
    }
}
