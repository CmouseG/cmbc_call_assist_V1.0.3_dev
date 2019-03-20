package com.guiji.process.server.service.impl;

import com.guiji.common.exception.GuiyuException;
import com.guiji.common.model.process.ProcessInstanceVO;
import com.guiji.common.model.process.ProcessStatusEnum;
import com.guiji.common.model.process.ProcessTypeEnum;
import com.guiji.process.core.vo.*;
import com.guiji.process.model.ProcessReleaseVO;
import com.guiji.process.model.PublishBotstenceTaskVO;
import com.guiji.process.server.exception.GuiyuProcessExceptionEnum;
import com.guiji.process.server.model.DeviceProcessConstant;
import com.guiji.process.server.model.ProcessTask;
import com.guiji.process.server.service.IProceseScheduleService;
import com.guiji.process.server.service.IProcessAgentManageService;
import com.guiji.process.server.service.IProcessInstanceManageService;
import com.guiji.process.server.util.DeviceProcessUtil;
import com.guiji.utils.IdGenUtil;
import com.guiji.utils.RedisUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ProceseScheduleService implements IProceseScheduleService {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private ProcessManageService deviceManageService;

    @Autowired
    private IProcessInstanceManageService processInstanceManageService;
    @Autowired
    private IProcessAgentManageService processAgentManageService;
    @Override
    public List<ProcessInstanceVO> getTTS(String model, int requestCount) {
        if (StringUtils.isEmpty(model)) {
            throw new GuiyuException(GuiyuProcessExceptionEnum.EXCP_PROCESS_MODEL_NULL);
        }

        return getDevices(ProcessTypeEnum.TTS,model, requestCount);
    }

    @Override
    public List<ProcessInstanceVO> getTTS() {
        return getDevices(ProcessTypeEnum.TTS, null, 65565);
    }

    @Override
    public int sellbotCount() {
        return deviceCount(ProcessTypeEnum.SELLBOT);
    }

    @Override
    public List<ProcessInstanceVO> getSellbot(int requestCount) {
        return getDevices(ProcessTypeEnum.SELLBOT,null, requestCount);
    }

    @Override
    public boolean release(ProcessReleaseVO processReleaseVO) {

        for (ProcessInstanceVO processInstance:processReleaseVO.getProcessInstanceVOS()) {
            ProcessInstanceVO processInstanceVOOld= deviceManageService.getDevice(processInstance.getType(), processInstance.getIp(), processInstance.getPort());
            if (processInstanceVOOld != null) {
                if(processInstanceVOOld.getStatus() == ProcessStatusEnum.BUSYING) {
                    processInstanceVOOld.setWhoUsed("");
                    processInstanceVOOld.setStatus(ProcessStatusEnum.UP);
                }
                deviceManageService.updateStatus(processInstanceVOOld);
            }
        }

        return true;
    }

    @Override
    public boolean releaseTts(String model, List<ProcessInstanceVO> processInstances) {
        for (ProcessInstanceVO processInstance:processInstances) {

            processInstance.setWhoUsed("");
            processInstance.setStatus(ProcessStatusEnum.UP);
            if(deviceManageService.getDevice(processInstance.getType(), processInstance.getIp(), processInstance.getPort()) != null)
            {
                deviceManageService.updateStatus(processInstance);
            }
        }

        return true;
    }

    @Override
    public void restoreTtsModel(String srcModel, String toModel, ProcessInstanceVO processInstance,Long userId) {

        if(StringUtils.equals(srcModel, toModel))
        {
            return;
        }

        ProcessInstanceVO processInstanceVO = processInstanceManageService.get(processInstance.getIp(), processInstance.getPort());
        if(processInstanceVO == null)
        {
            return;
        }
        processInstanceVO.setWhoUsed(IdGenUtil.uuid());
        processInstanceVO.setProcessKey(toModel);
        deviceManageService.updateStatus(processInstanceVO);


        // 通知更换模型
        List<String> parameters = new ArrayList<String>();
        parameters.add(srcModel);
        parameters.add(toModel);
        deviceManageService.cmd(processInstance, CmdTypeEnum.RESTORE_MODEL, parameters,userId,IdGenUtil.uuid());

        //processInstance.setWhoUsed(IdGenUtil.uuid());
        //updateActiveCacheList(DeviceTypeEnum.TTS.name()+ "_" + toModel, processInstance);
    }

    @Override
    public PublishBotstenceTaskVO publishResource(ProcessTypeEnum processTypeEnum, String tmplId, String file, Long userId) {
        CmdTypeEnum cmdType = CmdTypeEnum.PULBLISH_SELLBOT_BOTSTENCE;
        PublishBotstenceTaskVO publisthBotstenceTaskVo = new PublishBotstenceTaskVO();
        if(processTypeEnum == ProcessTypeEnum.SELLBOT)
        {
            cmdType = CmdTypeEnum.PULBLISH_SELLBOT_BOTSTENCE;
        }
        else if(processTypeEnum == ProcessTypeEnum.FREESWITCH)
        {
            cmdType = CmdTypeEnum.PULBLISH_FREESWITCH_BOTSTENCE;
        }
        else if(processTypeEnum == ProcessTypeEnum.ROBOT)
        {
            cmdType = CmdTypeEnum.PUBLISH_ROBOT_BOTSTENCE;
        }
        else
        {
            return publisthBotstenceTaskVo;
        }

        List<String> parameters = new ArrayList<String>();
        parameters.add(file);
        parameters.add(tmplId);

        List<String> allIp = (List<String>) redisUtil.get("GY_PROCESS_" + processTypeEnum);
        Map<Object, Object> allAgent = (Map<Object, Object>) processAgentManageService.query();
        if(allAgent == null || allIp == null)
        {
            return publisthBotstenceTaskVo;
        }
        List<String> subJobIds = new ArrayList<String>();
        String jobId = IdGenUtil.uuid();
        publisthBotstenceTaskVo.setJobId(jobId);
        Map<String,ProcessTask> processTaskMap = new ConcurrentHashMap<String,ProcessTask>();
        for (Map.Entry<Object, Object> agentEnv: allAgent.entrySet()) {
            ProcessInstanceVO agent = (ProcessInstanceVO) agentEnv.getValue();
            if(!allIp.contains(agent.getIp())) {
                continue;
            }
            String reqKey = IdGenUtil.uuid();
            ProcessTask processTask = new ProcessTask();
            processTask.setRetryCount(1);
            processTask.setExeTime(new Date());
            processTask.setProcessInstanceVO(agent);
            processTask.setCmdType(cmdType);
            processTask.setParameters(parameters);
            processTask.setUserId(userId);
            processTask.setReqKey(reqKey);
            processTaskMap.put(reqKey,processTask);

            subJobIds.add(reqKey);
        }
        publisthBotstenceTaskVo.setSubJobIds(subJobIds);
        List<String> jobList = (List<String>) redisUtil.get("GY_PROCESS_JOB");
        if (jobList == null) {
            jobList = new ArrayList<String>();
            jobList.add(jobId);
        } else {
            jobList.add(jobId);
        }
        redisUtil.set("GY_PROCESS_JOB",jobList);
        redisUtil.set(jobId,processTaskMap);
        redisUtil.expire(jobId,3600);


        for (Map.Entry<String, ProcessTask> entry: processTaskMap.entrySet()) {
            deviceManageService.cmd(entry.getValue().getProcessInstanceVO(), cmdType, parameters,userId,entry.getKey());
        }
        return publisthBotstenceTaskVo;
    }



    private List<ProcessInstanceVO> getDevices(ProcessTypeEnum processTypeEnum, String key, int requestCount)
    {
        List<ProcessInstanceVO> result = new ArrayList<ProcessInstanceVO>();

        Map<Object, Object> allAgent = (Map<Object, Object>) processAgentManageService.query();
        if(allAgent == null)
        {
            return result;
        }


        String whoUsed = IdGenUtil.uuid();
        int count = 0;
        for (Map.Entry<Object, Object> agentEnv: allAgent.entrySet()) {

            if(requestCount == count)
            {
                break;
            }
            ProcessInstanceVO agent = (ProcessInstanceVO) agentEnv.getValue();
            Map<Object, Object> agentProcesses = (Map<Object, Object>) processInstanceManageService.query(((ProcessInstanceVO)agentEnv.getValue()).getIp());

            boolean hasChanged = false;
            for (Map.Entry<Object, Object> processesEnv: agentProcesses.entrySet()) {
                if(requestCount == count)
                {
                    break;
                }

                ProcessInstanceVO processInstance = (ProcessInstanceVO) processesEnv.getValue();

                boolean keyFlg = true;
                if(StringUtils.isNotEmpty(key))
                {
                    keyFlg = StringUtils.equals(key, processInstance.getProcessKey());
                }

                if(processInstance.getStatus() == ProcessStatusEnum.UP && StringUtils.isEmpty(processInstance.getWhoUsed()) && processInstance.getType() == processTypeEnum && keyFlg)
                {
                    processInstance.setStatus(ProcessStatusEnum.BUSYING);
                    processInstance.setWhoUsed(whoUsed);

                    deviceManageService.updateStatus(processInstance);

                    result.add(processInstance);
                    count++;
                    hasChanged = true;
                }
            }

            if(hasChanged)
            {
                processInstanceManageService.update(agent.getIp(), agentProcesses);
            }
        }
        return  result;
    }

    private int deviceCount(ProcessTypeEnum processTypeEnum){
        List<ProcessInstanceVO> result = new ArrayList<ProcessInstanceVO>();
        int count = 0;

        Map<Object, Object> allAgent = (Map<Object, Object>) processAgentManageService.query();
        if(allAgent == null){
            return count;
        }
        for (Map.Entry<Object, Object> agentEnv: allAgent.entrySet()) {
            Map<Object, Object> agentProcesses = (Map<Object, Object>) processInstanceManageService.query(((ProcessInstanceVO)agentEnv.getValue()).getIp());
            for (Map.Entry<Object, Object> processesEnv: agentProcesses.entrySet()) {
                ProcessInstanceVO processInstance = (ProcessInstanceVO) processesEnv.getValue();
                if(processInstance.getType() == processTypeEnum && (processInstance.getStatus() == ProcessStatusEnum.UP || processInstance.getStatus() == ProcessStatusEnum.BUSYING)){
                    count++;
                }
            }
        }
        return  count;
    }

}
