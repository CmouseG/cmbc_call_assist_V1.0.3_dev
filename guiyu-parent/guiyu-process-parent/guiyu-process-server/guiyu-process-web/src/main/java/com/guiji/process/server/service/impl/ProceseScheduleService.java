package com.guiji.process.server.service.impl;

import com.guiji.process.core.vo.CmdTypeEnum;
import com.guiji.process.core.vo.DeviceStatusEnum;
import com.guiji.process.core.vo.ProcessInstanceVO;
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

@Service
public class ProceseScheduleService implements IProceseScheduleService {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private ProcessManageService deviceManageService;

    @Override
    public List<ProcessInstanceVO> getTTS(String model, int requestCount) {

        return getDevices(model, requestCount);
    }

    @Override
    public List<ProcessInstanceVO> getSellbot(int requestCount) {
        return getDevices("", requestCount);
    }

    @Override
    public boolean release(List<ProcessInstanceVO> processInstances) {

        for (ProcessInstanceVO processInstance:processInstances) {

            processInstance.setWhoUsed("");
            processInstance.setStatus(DeviceStatusEnum.UP);
            deviceManageService.updateStatus(processInstance);
        }

        return true;
    }

    @Override
    public boolean releaseTts(String model, List<ProcessInstanceVO> processInstances) {
        for (ProcessInstanceVO processInstance:processInstances) {

            processInstance.setWhoUsed("");
            processInstance.setStatus(DeviceStatusEnum.UP);
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

        Map<String, Object> deviceVOMap = (Map<String, Object>) redisUtil.get(DeviceProcessConstant.ALL_DEVIECE_KEY);

        if(deviceVOMap !=  null && deviceVOMap.containsKey(deviceKey))
        {
            ProcessInstanceVO processInstanceVO = (com.guiji.process.core.vo.ProcessInstanceVO) deviceVOMap.get(deviceKey);
            processInstanceVO.setProcessKey(toModel);

            redisUtil.hmset(DeviceProcessConstant.ALL_DEVIECE_KEY, deviceVOMap);

            processInstanceVO.setWhoUsed(IdGenUtil.uuid());
            processInstanceVO.setStatus(DeviceStatusEnum.BUSYING);

            deviceManageService.updateStatus(processInstanceVO);
        }

        // 通知更换模型 TODO 同步 并设定TTS的状态
        deviceManageService.cmd(processInstance, CmdTypeEnum.RESTORE_MODEL);

        //processInstance.setWhoUsed(IdGenUtil.uuid());
        //updateActiveCacheList(DeviceTypeEnum.TTS.name()+ "_" + toModel, processInstance);
    }



    private List<ProcessInstanceVO> getDevices(String key, int requestCount)
    {
        List<ProcessInstanceVO> result = new ArrayList<ProcessInstanceVO>();

        Map<String, Object> deviceVOMap = (Map<String, Object>) redisUtil.get(DeviceProcessConstant.ALL_DEVIECE_KEY);
        if(deviceVOMap == null)
        {
            return result;
        }

        ProcessInstanceVO deviceVO = null;
        String whoUsed = IdGenUtil.uuid();

        int count = 0;
        for (Map.Entry<String, Object> ent: deviceVOMap.entrySet()) {

            if(count == requestCount)
            {
                break;
            }

            try {
                deviceVO = (ProcessInstanceVO) ent.getValue();
                if(deviceVO.getStatus() == DeviceStatusEnum.UP && StringUtils.isEmpty(deviceVO.getWhoUsed()) && StringUtils.equals(key, deviceVO.getProcessKey()))
                {
                    deviceVO.setStatus(DeviceStatusEnum.BUSYING);
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

}
