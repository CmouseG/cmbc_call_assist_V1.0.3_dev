package com.guiji.dispatch.api;

import com.guiji.dispatch.dao.model.CommonResponse;
import com.guiji.dispatch.dao.model.Schedule;
import org.springframework.stereotype.Service;

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
    CommonResponse addSchedule(final Schedule schedule) throws Exception;

    /**
     * 查询任务列表
     *
     * @param userId 用户id
     * @return 响应报文
     * @throws Exception 异常
     */
    CommonResponse querySchedules(final String userId) throws Exception;

    /**
     * 暂停任务
     *
     * @param planUuid 任务id
     * @return 响应报文
     * @throws Exception 异常
     */
    CommonResponse pauseSchedule(final String planUuid) throws Exception;

    /**
     * 恢复任务
     *
     * @param planUuid 任务id
     * @return 响应报文
     * @throws Exception 异常
     */
    CommonResponse resumeSchedule(final String planUuid) throws Exception;

    /**
     * 停止任务
     *
     * @param planUuid 任务id
     * @return 响应报文
     * @throws Exception 异常
     */
    CommonResponse stopSchedule(final String planUuid) throws Exception;

    /**
     * 返回可以拨打的任务给呼叫中心
     *
     * @param userId  用户id
     * @param taskNum 获取任务数量
     * @return 响应报文
     * @throws Exception 异常
     */
    CommonResponse queryAvailableSchedule(final String userId, final String taskNum) throws Exception;

    /**
     * 查询任务提交处理结果
     *
     * @param planUuid 任务id
     * @return 响应报文
     * @throws Exception 异常
     */
    CommonResponse queryExecuteResult(final String planUuid) throws Exception;
}
