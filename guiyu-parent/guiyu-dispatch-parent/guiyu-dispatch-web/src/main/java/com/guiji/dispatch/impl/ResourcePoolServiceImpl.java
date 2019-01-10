package com.guiji.dispatch.impl;

import com.guiji.auth.api.IAuth;
import com.guiji.ccmanager.api.ICallManagerOut;
import com.guiji.ccmanager.entity.LineConcurrent;
import com.guiji.component.lock.DistributedLockHandler;
import com.guiji.component.lock.Lock;
import com.guiji.component.result.Result;
import com.guiji.dispatch.bean.UserResourceDto;
import com.guiji.dispatch.dao.entity.DispatchPlan;
import com.guiji.dispatch.service.IGetPhonesInterface;
import com.guiji.dispatch.service.IPhonePlanQueueService;
import com.guiji.dispatch.service.IResourcePoolService;
import com.guiji.robot.api.IRobotRemote;
import com.guiji.robot.model.UserAiCfgBaseInfoVO;
import com.guiji.user.dao.entity.SysUser;
import com.guiji.utils.DateUtil;
import com.guiji.utils.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ty on 2019/1/7.
 */
@Service
public class ResourcePoolServiceImpl implements IResourcePoolService {
    static Logger logger = LoggerFactory.getLogger(ResourcePoolServiceImpl.class);
    private static final String REDIS_SYSTEM_MAX_PLAN = "REDIS_SYSTEM_MAX_PLAN";
    private static final String REDIS_SYSTEM_MAX_ROBOT = "REDIS_SYSTEM_MAX_ROBOT";
    private static final String REDIS_SYSTEM_MAX_LINE = "REDIS_SYSTEM_MAX_LINE";
    private static final String REDIS_USER_MAX_ROBOT = "REDIS_USER_MAX_ROBOT";
    private static final String REDIS_USER_MAX_LINE = "REDIS_USER_MAX_LINE";
    private static final String REDIS_SYSTEM_MAX_PLAN_BY = "REDIS_SYSTEM_MAX_PLAN_BY";
    private static final String REDIS_PLAN_QUEUE = "REDIS_PLAN_QUEUE";

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private IAuth auth;
    @Autowired
    private ICallManagerOut callManagerOut;
    @Autowired
    private DistributedLockHandler distributedLockHandler;
    @Autowired
    private IGetPhonesInterface getPhonesInterface;
    @Autowired
    private IRobotRemote robotRemote;
    @Autowired
    private IPhonePlanQueueService phonePlanQueueService;
    @Override
    public boolean initResourcePool() {
        long start = System.currentTimeMillis();
        logger.info("初始化系统资源池#start");
        //调用机器人中心接口获取当前系统最大机器人数量
        int systemMaxRobot = getSystemMaxRobot();
        redisUtil.set(REDIS_SYSTEM_MAX_ROBOT,systemMaxRobot);
        //调用用户中心接口获取当前系统所有企业管理员和企业操作员作为用户集合
        List<SysUser> sysUserList = getAllCompanyUsers();
        //调用呼叫中心获取系统线路总并发数
        int systemMaxLine = getSystemMaxLine(sysUserList);
//        int systemMaxLine = 60;
        redisUtil.set(REDIS_SYSTEM_MAX_LINE,systemMaxLine);
        //所有用户线路并发数求和，与系统机器人总数比较，取最小值作为系统拨打任务最大值，存入redis
        if (systemMaxRobot <= systemMaxLine) {
            redisUtil.set(REDIS_SYSTEM_MAX_PLAN,systemMaxRobot);
            //调用机器人中心接口根据用户获取各个用户配置机器人数量，并存入redis
            List<UserResourceDto> userRobotList = getUserRobotByUserId(sysUserList);
            redisUtil.set(REDIS_USER_MAX_ROBOT,userRobotList);
            redisUtil.set(REDIS_SYSTEM_MAX_PLAN_BY,"robot");
        } else {
            redisUtil.set(REDIS_SYSTEM_MAX_PLAN,systemMaxLine);
            //调用呼叫中心接口根据用户获取各个用户配置线路并发数量，并存入redis
            List<UserResourceDto> userLineList = getUserLineByUserId(sysUserList);
            redisUtil.set(REDIS_USER_MAX_LINE,userLineList);
            redisUtil.set(REDIS_SYSTEM_MAX_PLAN_BY,"line");
        }
        long end = System.currentTimeMillis();
        long usedTime = end - start;
        logger.info("初始化系统资源池#end,耗时:" + usedTime);
        return true;
    }

    @Override
    public boolean distributeByUser() {
        logger.info("根据用户分配拨打号码比例#start");
        //1.获取redis锁，将拨打计划的redis锁住
        Lock lock = new Lock("redisPlanQueueLock", "redisPlanQueueLock");
        try {
            if (distributedLockHandler.tryLock(lock)) { // 默认锁设置
                //2.按小时获取当前时间段有拨打计划的用户，按1000条进redis拨打队列为总数，分别计算[用户、线路]拨打数量(按用户划分后，各用户线路均分)
                int hour = DateUtil.getCurrentHour();
                //2.1将redis拨打队列中的计划在数据库中status_sync状态回退到未进队列状态，并清空redis拨打队列
                List<String> planUuids = new ArrayList<String>();
                while (true) {
                    DispatchPlan dispatchPlan = (DispatchPlan)redisUtil.lrightPop(REDIS_PLAN_QUEUE);
                    if(dispatchPlan == null) {
                        break;
                    } else {
                        planUuids.add(dispatchPlan.getPlanUuid());
                    }
                }
                getPhonesInterface.resetPhoneSyncStatus(planUuids);
            }
        } catch (Exception e) {
            logger.info("ResourcePoolServiceImpl#distributeByUser", e);
            return false;
        } finally {
            distributedLockHandler.releaseLock(lock);
        }
        logger.info("根据用户分配拨打号码比例#end");
        return true;
    }

    private List<SysUser> getAllCompanyUsers() {
        List<SysUser> sysUserList = null;
        Result.ReturnData<List<SysUser>> userResult = auth.getAllCompanyUser();
        if (userResult.success) {
            if (userResult.getBody() != null) {
                sysUserList = userResult.getBody();
            }
        } else {
            logger.info("调用用户中心获取所有企业管理员和企业操作员集合失败");
        }
        return sysUserList;
    }

    private int getSystemMaxLine(List<SysUser> sysUserList) {
        logger.info("查询系统线路最大并发数#start");
        int systemMaxLine = 0;
        if (sysUserList != null && sysUserList.size() > 0) {
            for (SysUser sysUser:sysUserList) {
                //根据用户获取各个用户配置线路并发数量，调用呼叫中心接口
                List<LineConcurrent> lineList = null;
                Result.ReturnData<List<LineConcurrent>> lineResult = callManagerOut.getLineInfos(String.valueOf(sysUser.getId()));
                if (lineResult.success) {
                    if (lineResult.getBody() != null) {
                        lineList = lineResult.getBody();
                        for (LineConcurrent line : lineList) {
                            systemMaxLine = systemMaxLine + line.getConcurrent();
                        }
                    }
                } else {
                    logger.info("调用呼叫中心获取用户线路信息失败，用户id:" + sysUser.getId());
                }
            }
        }
        logger.info("查询系统线路最大并发数#end");
        return systemMaxLine;
    }

    private Integer getSystemMaxRobot() {
        Integer systemMaxRobot = 0;
        Result.ReturnData<Integer> userResult = robotRemote.queryRobotResNum();
        if (userResult.success) {
            if (userResult.getBody() != null) {
                systemMaxRobot = userResult.getBody();
            }
        } else {
            logger.info("调用机器人中心接口获取当前系统最大机器人数量失败:" + userResult.getMsg());
        }
        return systemMaxRobot;
    }

    private List<UserResourceDto> getUserLineByUserId(List<SysUser> sysUserList) {
        logger.info("查询每个用户线路最大并发数#start");
        List<UserResourceDto> userLineList = new ArrayList<UserResourceDto>();
        if (sysUserList != null && sysUserList.size() > 0) {
            for (SysUser sysUser:sysUserList) {
                //根据用户获取各个用户配置线路并发数量，调用呼叫中心接口
                List<LineConcurrent> lineList = null;
                String userId = String.valueOf(sysUser.getId());
                int maxLineConcurrency = 0;
                Result.ReturnData<List<LineConcurrent>> lineResult = callManagerOut.getLineInfos(userId);
                if (lineResult.success) {
                    if (lineResult.getBody() != null) {
                        lineList = lineResult.getBody();
                        for (LineConcurrent line : lineList) {
                            maxLineConcurrency = maxLineConcurrency + line.getConcurrent();
                        }
                    }
                }else {
                    logger.info("调用呼叫中心获取用户线路并发数失败，用户id:" + sysUser.getId()+"|错误信息:" + lineResult.getMsg());
                }
                userLineList.add(new UserResourceDto(userId,maxLineConcurrency));
            }
        }
        logger.info("查询每个用户线路最大并发数#end");
        return userLineList;
    }

    private List<UserResourceDto> getUserRobotByUserId(List<SysUser> sysUserList) {
        logger.info("查询每个用户机器人最大并发数#start");
        List<UserResourceDto> userRobotList = new ArrayList<UserResourceDto>();
        if (sysUserList != null && sysUserList.size() > 0) {
            for (SysUser sysUser:sysUserList) {
                //根据用户获取各个用户配置机器人数量，调用机器人中心接口
                String userId = String.valueOf(sysUser.getId());
                UserAiCfgBaseInfoVO userAiCfgBaseInfoVO = null;
                int maxRobotConcurrency = 0;
                Result.ReturnData<UserAiCfgBaseInfoVO> robotResult = robotRemote.queryCustBaseAccount(userId);
                if (robotResult.success) {
                    if (robotResult.getBody() != null) {
                        userAiCfgBaseInfoVO = robotResult.getBody();
                        if (userAiCfgBaseInfoVO.getAiTotalNum() != null) {
                            maxRobotConcurrency = userAiCfgBaseInfoVO.getAiTotalNum();
                        }
                    }
                }else {
                    logger.info("调用机器人中心获取用户机器人数量失败，用户id:" + sysUser.getId()+"|错误信息:" + robotResult.getMsg());
                }
                userRobotList.add(new UserResourceDto(userId,maxRobotConcurrency));
            }
        }
        logger.info("查询每个用户机器人最大并发数#end");
        return userRobotList;
    }
}
