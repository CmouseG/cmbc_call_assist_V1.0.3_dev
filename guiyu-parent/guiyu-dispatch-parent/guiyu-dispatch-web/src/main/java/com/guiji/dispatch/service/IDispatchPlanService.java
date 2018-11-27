package com.guiji.dispatch.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.guiji.ccmanager.entity.LineConcurrent;
import com.guiji.common.model.Page;
import com.guiji.dispatch.bean.IdsDto;
import com.guiji.dispatch.bean.MessageDto;
import com.guiji.dispatch.dao.entity.DispatchPlan;
import com.guiji.dispatch.dao.entity.DispatchPlanBatch;

public interface IDispatchPlanService {

    /**
     * 写入任务
     *
     * @param schedule 任务
     * @return 响应报文
     * @throws Exception 
     */
  boolean addSchedule( DispatchPlan dispatchPlan,Long userId) throws Exception;

    /**
     * 查询任务列表
     *
     * @param userId 用户id
     * @return 响应报文
     */
  Page<DispatchPlan> querySchedules(final Integer userId,int pagenum, int pagesize) ;

    /**
     * 暂停任务
     *
     * @param planUuid 任务id
     * @return 响应报文
     */
    boolean pauseSchedule(final String planUuid) ;

    /**
     * 恢复任务
     *
     * @param planUuid 任务id
     * @return 响应报文
     */
    boolean resumeSchedule(final String planUuid) ;
    
    
    /**
     * 删除任务
     *
     * @param planUuid 任务id
     * @return 响应报文
     */
    boolean deleteSchedule(final String planUuid) ;
    
    /**
     * 取消
     *
     * @param planUuid 任务id
     * @return 响应报文
     */
    boolean cancelSchedule(final String planUuid) ;

    /**

    /**
     * 返回可以拨打的任务给呼叫中心
     *
     * @param schedule 请求参数
     * @return 响应报文
     */
    List<DispatchPlan> queryAvailableSchedules(Integer userId, int requestCount, int lineId,DispatchPlan dispatchPlan) ;

    /**
     * 查询任务提交处理结果
     *
     * @param planUuid 任务id
     * @return 响应报文
     */
    List<DispatchPlan> queryExecuteResult(String planUuid) ;

    

	/**
	 * 根据客户操作所有的批量计划
	 * @param dispatchPlanBatch
	 * @return
	 */
    public boolean OperationAllDispatchByUserId(Integer userId,Integer status);
    
    /**
     * 完成
     *
     * @param planUuid 任务id
     * @return 响应报文
     */
    boolean successSchedule(String planUuid) ;

    /**
     * 批量导入
     * @param fileName
     * @param file
     * @return
     * @throws IOException 
     * @throws Exception 
     */
    public boolean batchImport(String fileName, Long userId,MultipartFile file,String dispatchPlan) throws IOException, Exception ;

	/**
	 * 写入批次
	 * @param dispatchPlanBatch
	 * @return
	 */
    public boolean dispatchPlanBatch(DispatchPlanBatch dispatchPlanBatch);

    /**
     * 根据批次分页查询
     * @param batchId
     * @param pagenum
     * @param pagesize
     * @return
     */
	public Page<DispatchPlan> queryDispatchPlanByBatchId(Integer batchId,int pagenum,int pagesize );
	
	/**
	 * 
	 * @param phone
	 * @param planStaus
	 * @param startTime
	 * @param endTime
	 * @param pagenum
	 * @param pagesize
	 * @return
	 */
	public Page<DispatchPlan> queryDispatchPlanByParams(String phone,String planStaus,String startTime,String endTime,Integer batchId, String replayType,int pagenum,int pagesize,Long userId,boolean isSuperAdmin);
	
	/**
	 * 获取客户呼叫计划 
	 * @param userId
	 * @param requestCount
	 * @param lineId
	 * @return
	 */
//	public List<DispatchPlan> queryDispatchOutParams(Integer userId, int requestCount, int lineId);

	
	/**
	 * 获取客户线路列表
	 * @param userId
	 * @return
	 */
	List<LineConcurrent> outLineinfos(String userId);
	
	/**
	 * 根据当前当前时间查询号码
	 * @return
	 */
	List<DispatchPlan> selectPhoneByDate();
	
	/**
	 * 批量修改状态
	 * @param dto
	 * @return
	 */
	boolean  batchUpdatePlans(IdsDto[] dto);

	/**
	 * 一键操作状态* 
	 * @param batchId
	 * @param status
	 * @return
	 */
	boolean operationAllPlanByBatchId(Integer batchId,String status,Long userId);
	
	/**
	 * 批量删除
	 * @param dto
	 * @return
	 */
	boolean  batchDeletePlans(IdsDto[] dto);
	

	/**
	 * 查询批次
	 * @return
	 */
	List<DispatchPlanBatch> queryDispatchPlanBatch(Long userId, Boolean isSuperAdmin);
	
	/**
	 * 根据当前时间刷新日期
	 * @return
	 */
	boolean updateReplayDate();
	
	/**
	 * 检查批次是否存在
	 * @return
	 */
	boolean checkBatchId(String name);
}