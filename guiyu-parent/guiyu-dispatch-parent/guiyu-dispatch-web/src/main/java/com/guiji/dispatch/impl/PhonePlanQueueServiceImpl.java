package com.guiji.dispatch.impl;

import com.guiji.component.lock.DistributedLockHandler;
import com.guiji.component.lock.Lock;
import com.guiji.dispatch.bean.UserResourceDto;
import com.guiji.dispatch.dao.entity.DispatchPlan;
import com.guiji.dispatch.service.IGetPhonesInterface;
import com.guiji.dispatch.service.IPhonePlanQueueService;
import com.guiji.utils.DateUtil;
import com.guiji.utils.RedisUtil;
import io.swagger.models.auth.In;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by ty on 2019/1/7.
 */
@Service
public class PhonePlanQueueServiceImpl implements IPhonePlanQueueService {
    static Logger logger = LoggerFactory.getLogger(PhonePlanQueueServiceImpl.class);
    private static final String REDIS_SYSTEM_MAX_PLAN = "REDIS_SYSTEM_MAX_PLAN";
    private static final String REDIS_PLAN_QUEUE = "REDIS_PLAN_QUEUE";
    private static final String REDIS_SYSTEM_MAX_PLAN_BY = "REDIS_SYSTEM_MAX_PLAN_BY";
    private static final String REDIS_USER_MAX_ROBOT = "REDIS_USER_MAX_ROBOT";
    private static final String REDIS_USER_MAX_LINE = "REDIS_USER_MAX_LINE";
    private static final int QUEUE_SIZE = 160;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private IGetPhonesInterface getPhonesInterface;
    @Autowired
    private DistributedLockHandler distributedLockHandler;

    @Override
    public void execute() throws Exception {
        while(true) {

            Lock lock = new Lock("redisPlanQueueLock", "redisPlanQueueLock");
            if (distributedLockHandler.isLockExist(lock)) { // 默认锁设置
                Thread.sleep(20);
                continue;
            }


            // 从redis获取系统最大并发数
            int systemMaxPlan = redisUtil.get(REDIS_SYSTEM_MAX_PLAN) == null ? 0 : (int) redisUtil.get(REDIS_SYSTEM_MAX_PLAN);
            if (systemMaxPlan == 0) {
                logger.error("从redis获取系统最大并发数失败，获取的最大并发数未0");
            }
            //从redis获取当前拨打队列
            long currentQueueSize = redisUtil.lGetListSize(REDIS_PLAN_QUEUE);

            //判断拨打队列中的拨打数量如果小于最大并发数*2时，获取锁成功后调用接口getDispatchPlan()获取1000条拨打计划并插入到redis队列中
            if (currentQueueSize < systemMaxPlan*2) {
                logger.debug("当前redis池中的拨打队列小于阈值[最大并发数的2倍]，开始拉去新一批数据入redis队列");
                //2.按小时获取当前时间段有拨打计划的用户，按1000条进redis拨打队列为总数，分别计算[用户、线路]拨打数量(按用户划分后，各用户线路均分)
                pushPlan2Queue(getDispatchPlan(DateUtil.getCurrentHour()));
            }
            Thread.sleep(20);
        }
    }

    @Override
    public List<DispatchPlan> getDispatchPlan(int hour) {
        // 获取当前时间段有拨打计划的用户集合
        List<Integer> userIds = getPhonesInterface.getUserIdsByCallHour(String.valueOf(hour));
        // 有拨打计划的用户资源集合
        List<UserResourceDto> userCallPlanList = new ArrayList<UserResourceDto>();
        int totalSize = 0;
        List<DispatchPlan> dispatchPlanList = new ArrayList<DispatchPlan>();
        String redisSysMaxPlanBy = (String)redisUtil.get(REDIS_SYSTEM_MAX_PLAN_BY);
        List<UserResourceDto> userResourceDtoList = new ArrayList<UserResourceDto>();
        if ("robot".equals(redisSysMaxPlanBy)) {
            userResourceDtoList = (List<UserResourceDto>) redisUtil.get(REDIS_USER_MAX_ROBOT);
        } else if ("line".equals(redisSysMaxPlanBy)) {
            userResourceDtoList = (List<UserResourceDto>) redisUtil.get(REDIS_USER_MAX_LINE);
        }

        for (Integer userId : userIds) {
            int size = 0;
            if (userResourceDtoList != null) {
                for (UserResourceDto userResourceDto : userResourceDtoList) {
                    if (userId == Integer.valueOf(userResourceDto.getUserId())) {
                        totalSize = totalSize + userResourceDto.getCount();
                        userCallPlanList.add(userResourceDto);
                        break;
                    }
                }
            }
        }
        // 获取每个用户在1000条拨打计划中的拨打数量
        List<UserResourceDto> userPlanList = getUserPlanList(userCallPlanList,totalSize,QUEUE_SIZE);

        // 根据redis中存储的[用户、线路]数量分别查询出计划数据，并保存在各自list中
        List<UserResourceDto> userLinePlanList = getUserLinePlanList(userPlanList,hour);
        // 根据算好的用户线路数量获取拨打计划
        List<List<DispatchPlan>> dispatchPlanLists = new ArrayList<List<DispatchPlan>>();
        for (UserResourceDto userLinePlan : userLinePlanList) {
            if (userLinePlan != null) {
                List<DispatchPlan> dispatchPlans = getPhonesInterface.getPhonesByParams(Integer.valueOf(userLinePlan.getUserId()),userLinePlan.getLineId(),String.valueOf(hour),userLinePlan.getCount());
                if (dispatchPlans != null) {
                    dispatchPlanLists.add(dispatchPlans);
                }
            }
        }
        // 按照比例，将各[用户、线路]数据顺序组合成一个list，并返回
        return sortPlan(dispatchPlanLists);
    }

    @Override
    public boolean pushPlan2Queue(List<DispatchPlan> dispatchPlanList) {
        return redisUtil.leftPushAll(REDIS_PLAN_QUEUE,dispatchPlanList);
    }

    /**
     * 拨打队列中拨打计划排序，将各用户各线路交错排序
     * @param dispatchPlanList
     * @return
     */
    private List<DispatchPlan> sortPlan(List<List<DispatchPlan>> dispatchPlanList) {
        logger.debug("拨打计划排序#start");
        List<DispatchPlan> dispatchPlanLists = new ArrayList<DispatchPlan>();

        int allCount = 0;
        if (dispatchPlanList == null || dispatchPlanList.isEmpty()) {
            return dispatchPlanLists;
        }

        for(List<DispatchPlan> list:dispatchPlanList) {
            allCount = allCount + list.size();
        }
        // 排序算法
        int firstIndex = dispatchPlanList.size();
        for (int i = 0; i < allCount; i++) {
            for (int j = 0; j < firstIndex; j++) {
                List<DispatchPlan> tmpList = dispatchPlanList.get(j);
                if(tmpList.size()>i) {
                    dispatchPlanLists.add(tmpList.get(i));
                }
            }

            if(dispatchPlanLists.size() == allCount) {
                break;
            }
        }

        logger.info("拨打计划排序#end");
        return dispatchPlanLists;
    }

    /**
     * 分配各用户在一定拨打计划总数中的拨打数量
     * @param userResourceDtoList
     * @param totalSize
     * @param queueSize
     * @return
     */
    private List<UserResourceDto> getUserPlanList(List<UserResourceDto> userResourceDtoList,int totalSize,int queueSize) {
        logger.debug("用户拨打计划分配#start");
        List<UserResourceDto> userPlanList = new ArrayList<UserResourceDto>();
        int distributedSize = 0;
        if (userResourceDtoList != null) {
            for (int i=0;i<userResourceDtoList.size();i++) {
                UserResourceDto userResourceDto = userResourceDtoList.get(i);
                String userId = userResourceDto.getUserId();
                int subSize = userResourceDto.getCount();
                if (i == userResourceDtoList.size() -1) {
                    int currentSize = queueSize - distributedSize;
                    userPlanList.add(new UserResourceDto(userId,currentSize));
                }else {
                    int currentSize = queueSize*subSize/totalSize;
                    userPlanList.add(new UserResourceDto(userId,currentSize));
                }
            }
        }
        logger.debug("用户拨打计划分配#end");
        return userPlanList;
    }

    /**
     * 分配各用户线路在一定拨打计划总数中的拨打数量
     * @param userPlanList
     * @param hour
     * @return
     */
    private List<UserResourceDto> getUserLinePlanList(List<UserResourceDto> userPlanList,int hour) {
        logger.debug("用户线路拨打计划分配#start");
        List<UserResourceDto> userLinePlanList = new ArrayList<UserResourceDto>();
        if (userPlanList != null) {
            for (int i=0;i<userPlanList.size();i++) {
                UserResourceDto userPlan = userPlanList.get(i);
                Integer userId = Integer.valueOf(userPlan.getUserId());
                int subQueueSize = userPlan.getCount();
                List<Integer> lineList = getPhonesInterface.getPhonesByCallHourAndUserId(String.valueOf(hour), userId);
                int distributedLineSize = 0;
                if (lineList != null) {
                    int lineSize = lineList.size();
                    for (int j=0;j<lineList.size();j++) {
                        if (j == lineSize -1) {
                            int currentSize = subQueueSize - distributedLineSize;
                            userLinePlanList.add(new UserResourceDto(String.valueOf(userId),lineList.get(j),currentSize));
                        }else {
                            int currentSize = 1*subQueueSize/lineSize;
                            distributedLineSize = distributedLineSize + currentSize;
                            userLinePlanList.add(new UserResourceDto(String.valueOf(userId),lineList.get(j),currentSize));
                        }
                    }
                }
            }
        }
        logger.debug("用户线路拨打计划分配#end");
        return userLinePlanList;
    }
}
