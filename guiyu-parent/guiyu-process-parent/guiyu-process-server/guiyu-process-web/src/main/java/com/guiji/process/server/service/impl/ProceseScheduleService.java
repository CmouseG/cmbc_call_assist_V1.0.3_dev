package com.guiji.process.server.service.impl;

import com.guiji.common.exception.GuiyuException;
import com.guiji.process.core.vo.CmdTypeEnum;
import com.guiji.process.core.vo.ProcessStatusEnum;
import com.guiji.process.core.vo.ProcessTypeEnum;
import com.guiji.process.core.vo.ProcessInstanceVO;
import com.guiji.process.server.exception.GuiyuProcessExceptionEnum;
import com.guiji.process.server.model.DeviceProcessConstant;
import com.guiji.process.server.service.IProceseScheduleService;
import com.guiji.process.server.util.DeviceProcessUtil;
import com.guiji.utils.IdGenUtil;
import com.guiji.utils.RedisUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ProceseScheduleService implements IProceseScheduleService {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private ProcessManageService deviceManageService;

    @Override
    public List<ProcessInstanceVO> getTTS(String model, int requestCount) {
        if (StringUtils.isEmpty(model)) {
            throw new GuiyuException(GuiyuProcessExceptionEnum.EXCP_PROCESS_MODEL_NULL);
        }

        return getDevices(ProcessTypeEnum.TTS,model, requestCount);
    }

    @Override
    public List<ProcessInstanceVO> getTTS() {
        return getAllDevices(ProcessTypeEnum.TTS);
    }

    @Override
    public List<ProcessInstanceVO> getSellbot(int requestCount) {
        return getDevices(ProcessTypeEnum.SELLBOT,null, requestCount);
    }

    @Override
    public boolean release(List<ProcessInstanceVO> processInstances) {

        for (ProcessInstanceVO processInstance:processInstances) {

            processInstance.setWhoUsed("");
            processInstance.setStatus(ProcessStatusEnum.UP);
            deviceManageService.updateStatus(processInstance);
        }

        return true;
    }

    @Override
    public boolean releaseTts(String model, List<ProcessInstanceVO> processInstances) {
        for (ProcessInstanceVO processInstance:processInstances) {

            processInstance.setWhoUsed("");
            processInstance.setStatus(ProcessStatusEnum.UP);
            deviceManageService.updateStatus(processInstance);
        }

        return true;
    }

    @Override
    public void restoreTtsModel(String srcModel, String toModel, ProcessInstanceVO processInstance) {

        if(StringUtils.equals(srcModel, toModel))
        {
            return;
        }

        String deviceKey = DeviceProcessUtil.getDeviceKey(processInstance.getType(), processInstance.getIp(), processInstance.getPort());

        Map<Object, Object> deviceVOMap = (Map<Object, Object>) redisUtil.hmget(DeviceProcessConstant.ALL_DEVIECE_KEY);

        if(deviceVOMap !=  null && deviceVOMap.containsKey(deviceKey))
        {
            ProcessInstanceVO processInstanceVO = (com.guiji.process.core.vo.ProcessInstanceVO) deviceVOMap.get(deviceKey);
            processInstanceVO.setProcessKey(toModel);

            Map<String, Object> deviceVOMapTmp = new ConcurrentHashMap<String, Object>();
            for (Map.Entry<Object, Object> ent:deviceVOMap.entrySet()) {
                deviceVOMapTmp.put((String) ent.getKey(), ent.getValue());
            }
            redisUtil.hmset(DeviceProcessConstant.ALL_DEVIECE_KEY, deviceVOMapTmp);

            processInstanceVO.setWhoUsed(IdGenUtil.uuid());
            processInstanceVO.setStatus(ProcessStatusEnum.BUSYING);

            deviceManageService.updateStatus(processInstanceVO);
        }

        // 通知更换模型 TODO 同步 并设定TTS的状态
        deviceManageService.cmd(processInstance, CmdTypeEnum.RESTORE_MODEL);

        //processInstance.setWhoUsed(IdGenUtil.uuid());
        //updateActiveCacheList(DeviceTypeEnum.TTS.name()+ "_" + toModel, processInstance);
    }



    private List<ProcessInstanceVO> getDevices(ProcessTypeEnum processTypeEnum, String key, int requestCount)
    {
        List<ProcessInstanceVO> result = new ArrayList<ProcessInstanceVO>();

        Map<Object, Object> deviceVOMap = (Map<Object, Object>) redisUtil.hmget(DeviceProcessConstant.ALL_DEVIECE_KEY);
        if(deviceVOMap == null)
        {
            return result;
        }

        ProcessInstanceVO deviceVO = null;
        String whoUsed = IdGenUtil.uuid();

        int count = 0;
        for (Map.Entry<Object, Object> ent: deviceVOMap.entrySet()) {

            if(count == requestCount)
            {
                break;
            }

            try {
                deviceVO = (ProcessInstanceVO) ent.getValue();
                if(deviceVO.getStatus() == ProcessStatusEnum.UP && StringUtils.isEmpty(deviceVO.getWhoUsed()) && StringUtils.equals(key, deviceVO.getProcessKey()) && deviceVO.getType() == processTypeEnum)
                {
                    deviceVO.setStatus(ProcessStatusEnum.BUSYING);
                    deviceVO.setWhoUsed(whoUsed);

                    deviceManageService.updateStatus(deviceVO);

                    result.add((ProcessInstanceVO) deviceVO.clone());
                    count++;
                }
            }
            catch (Exception e)
            {

            }

        }

        return  result;
    }

    private List<ProcessInstanceVO> getAllDevices(ProcessTypeEnum processTypeEnum)
    {
        List<ProcessInstanceVO> result = new ArrayList<ProcessInstanceVO>();

        Map<Object, Object> deviceVOMap = (Map<Object, Object>) redisUtil.hmget(DeviceProcessConstant.ALL_DEVIECE_KEY);
        if(deviceVOMap == null)
        {
            return result;
        }

        ProcessInstanceVO deviceVO = null;
        String whoUsed = IdGenUtil.uuid();

        for (Map.Entry<Object, Object> ent: deviceVOMap.entrySet()) {
            try {
                deviceVO = (ProcessInstanceVO) ent.getValue();
                if(deviceVO.getStatus() == ProcessStatusEnum.UP && StringUtils.isEmpty(deviceVO.getWhoUsed()) && deviceVO.getType() == processTypeEnum)
                {
                    deviceVO.setStatus(ProcessStatusEnum.BUSYING);
                    deviceVO.setWhoUsed(whoUsed);

                    deviceManageService.updateStatus(deviceVO);

                    result.add((ProcessInstanceVO) deviceVO.clone());
                }
            } catch (Exception e){

            }

        }
        return  result;
    }

}
