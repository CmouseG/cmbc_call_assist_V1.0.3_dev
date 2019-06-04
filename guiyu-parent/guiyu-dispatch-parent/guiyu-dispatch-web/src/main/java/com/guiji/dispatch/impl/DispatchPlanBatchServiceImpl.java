package com.guiji.dispatch.impl;

import com.google.common.collect.Lists;
import com.guiji.common.exception.GuiyuException;
import com.guiji.component.lock.DistributedLockHandler;
import com.guiji.component.lock.Lock;
import com.guiji.dispatch.dao.DispatchPlanBatchMapper;
import com.guiji.dispatch.dao.entity.DispatchPlan;
import com.guiji.dispatch.dao.entity.DispatchPlanBatch;
import com.guiji.dispatch.dao.entity.DispatchPlanBatchExample;
import com.guiji.dispatch.dao.ext.BatchPlanExtMapper;
import com.guiji.dispatch.enums.BatchNotifyStatusEnum;
import com.guiji.dispatch.enums.PlanStatusEnum;
import com.guiji.dispatch.enums.SysDefaultExceptionEnum;
import com.guiji.dispatch.exception.BaseException;
import com.guiji.dispatch.exception.DispatchCodeExceptionEnum;
import com.guiji.dispatch.model.IPlanThirdBatchDialVo;
import com.guiji.dispatch.model.IPlanThirdBatchPhoneVo;
import com.guiji.dispatch.model.PhoneVo;
import com.guiji.dispatch.model.QueryPlanThirdRo;
import com.guiji.dispatch.service.GetApiService;
import com.guiji.dispatch.service.IDispatchPlanBatchService;
import com.guiji.dispatch.sys.ResultPage;
import com.guiji.dispatch.util.Constant;
import com.guiji.user.dao.entity.SysUser;
import com.guiji.utils.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Classname DispatchPlanBatchServiceImpl
 * @Description TODO
 * @Date 2019/5/21 18:25
 * @Created by qinghua
 */
@Service
public class DispatchPlanBatchServiceImpl implements IDispatchPlanBatchService {

    @Autowired
    DispatchPlanBatchMapper dispatchPlanBatchMapper;

    @Autowired
    GetApiService userService;

    @Autowired
    DistributedLockHandler distributedLockHandler;


    @Autowired
    BatchPlanExtMapper batchPlanExtMapper;

    /**
     * 添加批次
     * @param dispatchPlanBatch
     * @return
     */
    @Override
    public DispatchPlanBatch addDispatchPlanBatch(DispatchPlanBatch dispatchPlanBatch) {

        SysUser sysUser = userService.getUserById(dispatchPlanBatch.getUserId().toString());

        Lock lock = new Lock("dispatch.add.batch.lock."+dispatchPlanBatch.getUserId()+dispatchPlanBatch.getName(), "1");

        if(distributedLockHandler.tryLock(lock)) {
            try {

                DispatchPlanBatchExample example = new DispatchPlanBatchExample();

                example.createCriteria().andUserIdEqualTo(dispatchPlanBatch.getUserId()).andNameEqualTo(dispatchPlanBatch.getName());

                List<DispatchPlanBatch> dispatchPlanBatches = dispatchPlanBatchMapper.selectByExample(example);

                if(CollectionUtils.isEmpty(dispatchPlanBatches)) {
                    dispatchPlanBatch.setGmtModified(DateUtil.getCurrent4Time());
                    dispatchPlanBatch.setGmtCreate(DateUtil.getCurrent4Time());
                    dispatchPlanBatch.setStatusNotify(BatchNotifyStatusEnum.WAITING.getStatus());
                    dispatchPlanBatch.setOrgCode(sysUser.getOrgCode());
                    dispatchPlanBatch.setStatusShow(Constant.BATCH_STATUS_SHOW);

                    dispatchPlanBatchMapper.insert(dispatchPlanBatch);

                    return dispatchPlanBatch;
                } else {
                    throw new GuiyuException(DispatchCodeExceptionEnum.DUPLICATE_USER_BATCH_NAME.getErrorCode(), DispatchCodeExceptionEnum.DUPLICATE_USER_BATCH_NAME.getErrorMsg());
                }

            } catch (Exception ex) {
                throw ex;
            } finally {
                distributedLockHandler.releaseLock(lock);
            }
        }

        throw new BaseException(DispatchCodeExceptionEnum.DUPLICATE_USER_BATCH_NAME.getErrorCode(), DispatchCodeExceptionEnum.DUPLICATE_USER_BATCH_NAME.getErrorMsg());
    }

    /**
     * 根据批次名和用户id查询批次信息
     * @param batchName
     * @param userId
     * @return
     */
    @Override
    public DispatchPlanBatch queryPlanBatchByName(String batchName, Integer userId) {

        DispatchPlanBatchExample example = new DispatchPlanBatchExample();

        example.createCriteria().andUserIdEqualTo(userId).andNameEqualTo(batchName);

        List<DispatchPlanBatch> dispatchPlanBatches = dispatchPlanBatchMapper.selectByExample(example);

        if(CollectionUtils.isEmpty(dispatchPlanBatches)) {

            throw new BaseException(DispatchCodeExceptionEnum.NO_THIS_DISPATCH.getErrorCode(), DispatchCodeExceptionEnum.NO_THIS_DISPATCH.getErrorMsg());

        }

        return dispatchPlanBatches.get(0);
    }

    /**
     * 变更计划状态，恢复、暂停、停止
     * @param batchId
     * @param orgIds
     * @param status
     */
    @Override
    public void updatePlanBatchStatus(Integer batchId, List<Integer> orgIds, PlanStatusEnum status) {

        if(PlanStatusEnum.STOP.equals(status)) {

            batchPlanExtMapper.stopPlanPhoneByBatch(batchId, orgIds, new Date());

        } else if(PlanStatusEnum.SUSPEND.equals(status)) {

            batchPlanExtMapper.suspendPlanPhoneByBatch(batchId, orgIds, new Date());

        } else if(PlanStatusEnum.DOING.equals(status)) {

            batchPlanExtMapper.recoveryPlanPhoneByBatch(batchId, orgIds, new Date());

        }

    }

//    @Override
//    public List<DispatchPlan> getPlanThirdBatchDetail(Integer batchId) {
//
//        List<DispatchPlan> list = batchPlanExtMapper.queryErrorPhoneByBatch(batchId);
//
//        return list;
//
//    }

    /**
     * 根据批次名和userId查询
     * @param batchName
     * @param userId
     * @return
     */
    @Override
    public IPlanThirdBatchDialVo getPlanThirdBatchDial(String batchName, Integer userId) {
        IPlanThirdBatchDialVo planThirdVo = new IPlanThirdBatchDialVo();
        DispatchPlanBatch planBatch = queryPlanBatchByName(batchName, userId);
        if (null == planBatch) {
            throw new GuiyuException("批次不存在");
        }
        Integer orgId = userService.getOrgIdByUser(userId.toString());//获取orgId

        //计划中
        int planCount = batchPlanExtMapper.totalPlanCountByStatus(planBatch.getId(),
                PlanStatusEnum.DOING.getStatus(),
                Lists.newArrayList(orgId));

        //已完成
        int endCount = batchPlanExtMapper.totalPlanCountByStatus(planBatch.getId(),
                PlanStatusEnum.FINISH.getStatus(),
                Lists.newArrayList(orgId));

        planThirdVo.setBatchName(batchName);
        planThirdVo.setAcceptCount(planBatch.getTotalNum());//导入总数
        planThirdVo.setEndCount(endCount);//拨打完成数
        planThirdVo.setPlanCount(planCount);
        return planThirdVo;
    }

    /**
     * 分页查询号码列表
     * @param ro
     * @return
     */
    @Override
    public IPlanThirdBatchPhoneVo queryPlanThirdBatchPage(QueryPlanThirdRo ro) {

        String batchName = ro.getBatchName();
        Integer userId = ro.getUserId();

        DispatchPlanBatch planBatch = queryPlanBatchByName(batchName, userId);
        if (null == planBatch) {
            throw new GuiyuException("批次不存在");
        }
        Integer orgId = userService.getOrgIdByUser(userId.toString());//获取orgId

        int pageNo = ro.getPage() > 0 ? ro.getPage() : 1;
        ResultPage<com.guiji.dispatch.dao.entity.DispatchPlan> page = new ResultPage<>(pageNo, ro.getPageNum());

        List<DispatchPlan> list = batchPlanExtMapper.queryPlanThirdBatchPage(planBatch.getId(), Lists.newArrayList(orgId), page);
        int count = batchPlanExtMapper.queryPlanThirdCount(planBatch.getId(), Lists.newArrayList(orgId));
        page.setList(list);
        page.setTotalItemAndPageNumber(count);

        List<PhoneVo> phoneList = new ArrayList<>();

        list.forEach(obj -> {
            PhoneVo dto = new PhoneVo();

            dto.setAttach(obj.getAttach());
            dto.setCustCompany(obj.getCustCompany());
            dto.setCustName(obj.getCustName());
            dto.setPhoneNo(obj.getPhone());
            dto.setParams(obj.getParams());

            phoneList.add(dto);
        });

        IPlanThirdBatchPhoneVo planThirdVo = new IPlanThirdBatchPhoneVo();
        planThirdVo.setBatchName(batchName);
        planThirdVo.setBatchCount(count);
        planThirdVo.setMobileList(phoneList);
        planThirdVo.setPageNo(page.getPageNo());
        planThirdVo.setPageNum(page.getPageSize());
        planThirdVo.setTotalPage(page.getTotalPageNumber());
        return planThirdVo;

    }

}
