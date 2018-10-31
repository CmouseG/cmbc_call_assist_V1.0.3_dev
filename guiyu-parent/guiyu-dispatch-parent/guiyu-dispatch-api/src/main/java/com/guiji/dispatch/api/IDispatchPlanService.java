package com.guiji.dispatch.api;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.guiji.dispatch.model.CommonResponse;
import com.guiji.dispatch.model.DispatchPlan;
import com.guiji.dispatch.model.Schedule;
import com.guiji.dispatch.model.ScheduleList;
/**
 * 调度中心任务调度接口
 *
 * @version V1.0
 * @Description: 调度中心任务调度接口
 * @author: gaojianfeng
 * @date: 2018.10.22
 */
@Service
public interface IDispatchPlanService {

    /**
     * 向调度中心提交任务
     *
     * @param schedule 任务
     * @return 响应报文
     * @throws Exception 异常
     */
  boolean addSchedule( DispatchPlan dispatchPlan) throws Exception;

    /**
     * 查询任务列表
     *
     * @param userId 用户id
     * @return 响应报文
     * @throws Exception 异常
     */
  List<DispatchPlan> querySchedules(final String userId) throws Exception;

    /**
     * 暂停任务
     *
     * @param planUuid 任务id
     * @return 响应报文
     * @throws Exception 异常
     */
    boolean pauseSchedule(final String planUuid) throws Exception;

    /**
     * 恢复任务
     *
     * @param planUuid 任务id
     * @return 响应报文
     * @throws Exception 异常
     */
    boolean resumeSchedule(final String planUuid) throws Exception;

    /**
     * 停止任务
     *
     * @param planUuid 任务id
     * @return 响应报文
     * @throws Exception 异常
     */
    boolean stopSchedule(final String planUuid) throws Exception;

    /**
     * 返回可以拨打的任务给呼叫中心
     *
     * @param schedule 请求参数
     * @return 响应报文
     * @throws Exception 异常
     */
    List<DispatchPlan> queryAvailableSchedules(final Schedule schedule) throws Exception;

    /**
     * 查询任务提交处理结果
     *
     * @param planUuid 任务id
     * @return 响应报文
     * @throws Exception 异常
     */
    List<DispatchPlan> queryExecuteResult(final String planUuid) throws Exception;

    /**
     * 查询任务提交处理结果
     *
     * @param scheduleList 任务id
     * @return 响应报文
     * @throws Exception 异常
     */
    boolean updatePlanBatch(final ScheduleList scheduleList) throws Exception;
    
    /**
     * 批量导入
     * @param fileName
     * @param file
     * @return
     * @throws Exception
     */
    public boolean batchImport(String fileName, MultipartFile file) throws Exception;
}

