package com.guiji.ai.api;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.guiji.ai.vo.TaskListReqVO;
import com.guiji.ai.vo.TaskListRspVO;
import com.guiji.ai.vo.TaskReqVO;
import com.guiji.ai.vo.TaskRspVO;
import com.guiji.ai.vo.TtsGpuReqVO;
import com.guiji.ai.vo.TtsGpuRspVO;
import com.guiji.ai.vo.TtsReqVO;
import com.guiji.ai.vo.TtsRspVO;
import com.guiji.ai.vo.TtsStatusReqVO;
import com.guiji.ai.vo.TtsStatusRspVO;
import com.guiji.component.result.Result.ReturnData;

import io.swagger.annotations.ApiOperation;

/**
 * TTS对外服务
 * Created by ty on 2018/11/13.
 */
@FeignClient("guiyu-ai-web")
public interface ITts {

    /**
     * 语音合成服务
     * @param ttsReqVO
     * @return
     */
    @ApiOperation(value="语音合成")
    @PostMapping(value = "translate")
    public ReturnData<String> translate(TtsReqVO ttsReqVO);
    
    /**
     * 根据busiId查询TTS处理结果
     * @param busiId
     * @return
     */
    @ApiOperation(value="根据busiId查询TTS处理结果")
    @PostMapping(value = "getTtsResultByBusId")
    public ReturnData<TtsRspVO> getTtsResultByBusId(String busId);
    
    /**
     * 查询TTS处理状态
     * @param busiId
     * @return
     */
    @ApiOperation(value="查询TTS处理状态")
    @PostMapping(value = "getTtsStatusList")
    public ReturnData<List<TtsStatusRspVO>> getTtsStatusList(TtsStatusReqVO ttsStatusReqVO); 
    
    /**
     * 获取GPU模型列表
     * @return
     */
    @ApiOperation(value="获取GPU模型列表")
    @PostMapping(value = "getGpuList")
    public ReturnData<TtsGpuRspVO> getGpuList(TtsGpuReqVO ttsGpuReqVO);
    
    /**
     * 获取任务列表
     * @param ttsGpuReqVO
     * @return
     */
    @ApiOperation(value="获取任务列表")
    @PostMapping(value = "getTaskList")
    public ReturnData<TaskListRspVO> getTaskList(TaskListReqVO taskListReqVO);
    
    /**
     * 任务插队
     * @param busId
     * @return
     */
    @ApiOperation(value="任务插队")
    @PostMapping(value = "jumpQueue")
    public ReturnData<Boolean> jumpQueue(String busId);
    
    /**
     * 累计接受任务
     * @return
     */
    @ApiOperation(value="累计接受任务")
    @PostMapping(value = "getAcceptTasks")
    public ReturnData<TaskRspVO> getAcceptTasks(TaskReqVO taskReqVO);
    
    /**
     * 累计完成任务
     */
    @ApiOperation(value="累计完成任务")
    @PostMapping(value = "getCompleteTasks")
    public ReturnData<TaskRspVO> getCompleteTasks(TaskReqVO taskReqVO);
    
    /**
     * 待合成任务数（分模型）
     * @return
     */
    @ApiOperation(value="待合成任务数")
    @GetMapping(value = "getWaitTasks")
    public ReturnData<TaskRspVO> getWaitTasks();
}
