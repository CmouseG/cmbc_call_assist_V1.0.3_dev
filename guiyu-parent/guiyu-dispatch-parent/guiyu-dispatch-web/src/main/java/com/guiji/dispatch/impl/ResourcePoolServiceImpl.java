package com.guiji.dispatch.impl;

import com.guiji.auth.api.IAuth;
import com.guiji.ccmanager.api.ICallManagerOut;
import com.guiji.component.lock.DistributedLockHandler;
import com.guiji.component.lock.Lock;
import com.guiji.component.result.Result;
import com.guiji.dispatch.bean.PlanUserIdLineRobotDto;
import com.guiji.dispatch.bean.UserLineBotenceVO;
import com.guiji.dispatch.bean.UserResourceDto;
import com.guiji.dispatch.constant.RedisConstant;
import com.guiji.dispatch.entity.DispatchRobotOp;
import com.guiji.dispatch.service.IGetPhonesInterface;
import com.guiji.dispatch.service.IPhonePlanQueueService;
import com.guiji.dispatch.service.IResourcePoolService;
import com.guiji.dispatch.service.RobotService;
import com.guiji.dispatch.util.AllotUserLineBotenceUtil;
import com.guiji.robot.api.IRobotRemote;
import com.guiji.robot.model.UserAiCfgBaseInfoVO;
import com.guiji.robot.model.UserResourceCache;
import com.guiji.user.dao.entity.SysUser;
import com.guiji.utils.DateUtil;
import com.guiji.utils.JsonUtils;
import com.guiji.utils.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    private static final String REDIS_SYSTEM_MAX_PLAN_BY = "REDIS_SYSTEM_MAX_PLAN_BY";
    private static final String REDIS_USER_ROBOT_LINE_MAX_PLAN = "REDIS_USER_ROBOT_LINE_MAX_PLAN";

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

    @Autowired
    private RobotService robotService;

    @Override
    public boolean initResourcePool() {
        long start = System.currentTimeMillis();
        logger.info("初始化系统资源池#start");
        //调用机器人中心接口获取当前系统最大机器人数量
        int systemMaxRobot = getSystemMaxRobot();
        redisUtil.set(REDIS_SYSTEM_MAX_ROBOT,systemMaxRobot);
        //调用用户中心接口获取当前系统所有企业管理员和企业操作员作为用户集合
//        List<SysUser> sysUserList = getAllCompanyUsers();
        //调用呼叫中心获取系统线路总并发数
//        int systemMaxLine = getSystemMaxLine(sysUserList);
//        redisUtil.set(REDIS_SYSTEM_MAX_LINE,systemMaxLine);
        //所有用户线路并发数求和，与系统机器人总数比较，取最小值作为系统拨打任务最大值，存入redis
//        if (systemMaxRobot <= systemMaxLine) {
		redisUtil.set(REDIS_SYSTEM_MAX_PLAN, systemMaxRobot);
		redisUtil.set(REDIS_SYSTEM_MAX_PLAN_BY, "robot");
//        } else {
//            redisUtil.set(REDIS_SYSTEM_MAX_PLAN,systemMaxLine);
//            redisUtil.set(REDIS_SYSTEM_MAX_PLAN_BY,"line");
//        }
        long end = System.currentTimeMillis();
        long usedTime = end - start;
        logger.info("初始化系统资源池#end,耗时:" + usedTime);
        return true;
    }

    @Override
    public boolean distributeByUser() throws Exception{
        Lock lock = new Lock("planDistributeJobHandler.lock","planDistributeJobHandler.lock");
        if (distributedLockHandler.tryLock(lock, 1000L)) {
            try {

                logger.info("根据用户模板线路分配拨打号码比例#start");
                //查询当前时间段有拨打计划的[用户|线路|模板]
                String hour = String.valueOf(DateUtil.getCurrentHour());
               //mod by xujin
                List<PlanUserIdLineRobotDto> userLineRobotList = getPhonesInterface.selectPlanGroupByUserIdRobot(hour);
                List<UserLineBotenceVO> userLineBotenceVOList = new ArrayList<UserLineBotenceVO>();
                if (userLineRobotList != null) {
                    for (PlanUserIdLineRobotDto dto:userLineRobotList) {
                        UserLineBotenceVO userLineBotenceVO = new UserLineBotenceVO();
                        userLineBotenceVO.setUserId(dto.getUserId());
//                        userLineBotenceVO.setLineId(dto.getLineId());
                        userLineBotenceVO.setBotenceName(dto.getRobot());
                        userLineBotenceVOList.add(userLineBotenceVO);
                    }
                }
                //查询用户各个模板配置的机器人数量
                List<UserResourceCache> userBotstenceRobotList = getUserBotstenceRobotByUserId(getAllCompanyUsers());
                // 从redis获取系统最大并发数
                int systemMaxPlan = redisUtil.get(REDIS_SYSTEM_MAX_PLAN) == null ? 0
                        : (int) redisUtil.get(REDIS_SYSTEM_MAX_PLAN);
                if (systemMaxPlan == 0) {
                    logger.error("从redis获取系统最大并发数失败，获取的最大并发数为0");
                }

                /*******分别封装补充机器人数据，未做补充机器人数据    begin**********************************/
                //查询人工操作分配机器人操作数据
                List<DispatchRobotOp> allDisRobotList = robotService.queryDisRobotOpList(null, null);
                //封装未补充操作机器人数据列表(即,去除需要补充的机器人数据)
                List<UserLineBotenceVO> filterUserLineBotenceVOList = new ArrayList<UserLineBotenceVO>();
                //封装人工补充操作机器人数据列表，剔除不做补充机器人列表
                List<DispatchRobotOp> opDisRobotList = new ArrayList<DispatchRobotOp>();
                if(null != allDisRobotList){
                    for(UserLineBotenceVO userBotence : userLineBotenceVOList){
                        int i = 0;
                        for(DispatchRobotOp robotOp: allDisRobotList){
                            //判断用户ID，话术模板ID是否相同，相同则表示记录已经进行了补充机器人操作
                            if(robotOp.getUserId().equals(userBotence.getUserId()+"")
                                && robotOp.getBotstenceId().equals(userBotence.getBotenceName())){
                                opDisRobotList.add(robotOp);//封装加入人工补充操作机器人数据
                                i++;
                            }
                        }
                        if(i == 0){//匹配未做补充机器人操作，加入列表
                            filterUserLineBotenceVOList.add(userBotence);
                        }
                    }
                }else{
                    filterUserLineBotenceVOList = userLineBotenceVOList;
                }
                logger.info("人工操作机器人:{}", JsonUtils.bean2Json(opDisRobotList));
                /*******分别封装补充机器人数据，未做补充机器人数据    end**********************************/

                /**************计算机器人总数减去用户分配机器人数 和 补充机器人数   begin*********/
                logger.info("最大机器人总数减去手动分配机器人之前数量:{}",systemMaxPlan);
                systemMaxPlan = subSupplRobotNum(opDisRobotList, userBotstenceRobotList, systemMaxPlan);
                logger.info("最大机器人总数减去手动分配机器人之后数量:{}",systemMaxPlan);
                /**************计算机器人总数减去用户分配机器人数 和 补充机器人数   end*********/

                //计算分配机器人数
            //    List<UserLineBotenceVO> userLineBotenceVOS = AllotUserLineBotenceUtil.allot(userLineBotenceVOList,userBotstenceRobotList,systemMaxPlan);
                List<UserLineBotenceVO> userLineBotenceVOS = AllotUserLineBotenceUtil.allot(filterUserLineBotenceVOList,userBotstenceRobotList,systemMaxPlan);
                //将补充操作的机器人分配数据重新加入到用户机器人分配列表中去
                List<UserLineBotenceVO> opList = filterOpUserBotstence(opDisRobotList);
                if(null != opList && opList.size()>0){
                    userLineBotenceVOS.addAll(opList);
                }
                //从redis获取用户机器人分配数据
                List<UserLineBotenceVO> userLineBotenceVOSFromRedis = (List<UserLineBotenceVO>)redisUtil.get(RedisConstant.RedisConstantKey.REDIS_USER_ROBOT_LINE_MAX_PLAN);

                if(!isEquals(userLineBotenceVOS, userLineBotenceVOSFromRedis))
                {
                    phonePlanQueueService.cleanQueue();
                    redisUtil.set(RedisConstant.RedisConstantKey.REDIS_USER_ROBOT_LINE_MAX_PLAN,userLineBotenceVOS);
                }

                logger.info("根据用户模板线路分配拨打号码比例#end");
            } finally {
                distributedLockHandler.releaseLock(lock);
            }
        }

        return true;
    }

    /**
     * 封装人工操作的用户分配机器人数据
     * @param opDisRobotList
     * @return
     */
    private List<UserLineBotenceVO> filterOpUserBotstence(List<DispatchRobotOp> opDisRobotList){
        List<UserLineBotenceVO> opList = null;
        if(null != opDisRobotList && opDisRobotList.size()>0){
            opList = new ArrayList<UserLineBotenceVO>();
            for(DispatchRobotOp opRobot: opDisRobotList){
                UserLineBotenceVO userLineBotence = new UserLineBotenceVO();
                userLineBotence.setUserId(Integer.valueOf(opRobot.getUserId()));
                userLineBotence.setBotenceName(opRobot.getBotstenceId());
                userLineBotence.setMaxRobotCount(opRobot.getCurrentNum());
                opList.add(userLineBotence);
            }
        }
        logger.info("封装人工操作的用户分配机器人数据:{}", null != opList?JsonUtils.bean2Json(opList):"空");
        return opList;
    }


    /**
     * 计算机器人总数减去用户分配机器人数 和 补充机器人数
     * @param opDisRobotList
     * @param userBotstenceRobotList
     * @param systemMaxPlan
     * @return
     */
    private int subSupplRobotNum(List<DispatchRobotOp> opDisRobotList, List<UserResourceCache> userBotstenceRobotList,
                                 int systemMaxPlan){
        //用户各个模板配置的机器人数量，拼接key:userId+templateId
        Map<String, Integer> allUserBotenceCfg = AllotUserLineBotenceUtil.convertUserRes2Map(userBotstenceRobotList);
        if(null != allUserBotenceCfg && (null != opDisRobotList && opDisRobotList.size()>0)){
            for(DispatchRobotOp opRobot : opDisRobotList){
                String userBotstenceKey = opRobot.getUserId()+"-" +opRobot.getBotstenceId();
                if(allUserBotenceCfg.containsKey(userBotstenceKey)){//用户模板机器人数包涵用户机器人
                    Integer robotNum = allUserBotenceCfg.get(userBotstenceKey);//robot分配模板机器人数量
                    Integer supplNum = opRobot.getSupplNum();//人工操作机器人数量
                    Integer supplType = opRobot.getSupplType();//操作标识   1-增加补充  2-扣减
                    opRobot.setRobotNum(robotNum);
                    systemMaxPlan -= robotNum;  //减除robot分配机器人数量
                    if(1 == supplType){//总数
                        systemMaxPlan -= supplNum;//减除人工手动补充机器人数量
                        opRobot.setCurrentNum(robotNum+supplNum);
                    }else if(2 == supplNum){
                        systemMaxPlan += supplNum;
                        opRobot.setCurrentNum(robotNum-supplNum);
                    }else{
                        opRobot.setCurrentNum(robotNum);
                    }
                }
            }
        }
        return systemMaxPlan;
    }


    private boolean isEquals(List<UserLineBotenceVO> userLineBotenceVOS, List<UserLineBotenceVO> userLineBotenceVOSFromRedis)
    {
        if(userLineBotenceVOS == userLineBotenceVOSFromRedis)
        {
            return true;
        }

        if(userLineBotenceVOS == null || userLineBotenceVOSFromRedis == null)
        {
            return false;
        }

        if(userLineBotenceVOS.size() != userLineBotenceVOSFromRedis.size())
        {
            return false;
        }

        List<String> tmpList = new ArrayList<>();
        for (UserLineBotenceVO dto:userLineBotenceVOS) {
//            tmpList.add(dto.getUserId() +"-"+ dto.getLineId() +"-"+ dto.getBotenceName() +"-"+ dto.getMaxRobotCount());
        	tmpList.add(dto.getUserId() +"-"+ dto.getBotenceName() +"-"+ dto.getMaxRobotCount());
        }

        for (UserLineBotenceVO dto:userLineBotenceVOSFromRedis) {
//            if(!tmpList.contains(dto.getUserId() +"-"+ dto.getLineId() +"-"+ dto.getBotenceName() +"-"+ dto.getMaxRobotCount()))
        	 if(!tmpList.contains(dto.getUserId()  +"-"+ dto.getBotenceName() +"-"+ dto.getMaxRobotCount()))
            {
                return false;
            }
        }

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

//    private int getSystemMaxLine(List<SysUser> sysUserList) {
//        logger.info("查询系统线路最大并发数#start");
//        int systemMaxLine = 0;
//        if (sysUserList != null && sysUserList.size() > 0) {
//            for (SysUser sysUser:sysUserList) {
//                //根据用户获取各个用户配置线路并发数量，调用呼叫中心接口
//                List<LineConcurrent> lineList = null;
//                Result.ReturnData<List<LineConcurrent>> lineResult = callManagerOut.getLineInfos(String.valueOf(sysUser.getId()));
//                if (lineResult.success) {
//                    if (lineResult.getBody() != null) {
//                        lineList = lineResult.getBody();
//                        for (LineConcurrent line : lineList) {
//                            systemMaxLine = systemMaxLine + line.getConcurrent();
//                        }
//                    }
//                } else {
//                    logger.info("调用呼叫中心获取用户线路信息失败，用户id:" + sysUser.getId());
//                }
//            }
//        }
//        logger.info("查询系统线路最大并发数#end");
//        return systemMaxLine;
//    }

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

//    private List<UserResourceDto> getUserLineByUserId(List<SysUser> sysUserList) {
//        logger.info("查询每个用户线路最大并发数#start");
//        List<UserResourceDto> userLineList = new ArrayList<UserResourceDto>();
//        if (sysUserList != null && sysUserList.size() > 0) {
//            for (SysUser sysUser:sysUserList) {
//                //根据用户获取各个用户配置线路并发数量，调用呼叫中心接口
//                List<LineConcurrent> lineList = null;
//                String userId = String.valueOf(sysUser.getId());
//                int maxLineConcurrency = 0;
//                Result.ReturnData<List<LineConcurrent>> lineResult = callManagerOut.getLineInfos(userId);
//                if (lineResult.success) {
//                    if (lineResult.getBody() != null) {
//                        lineList = lineResult.getBody();
//                        for (LineConcurrent line : lineList) {
//                            maxLineConcurrency = maxLineConcurrency + line.getConcurrent();
//                        }
//                    }
//                }else {
//                    logger.info("调用呼叫中心获取用户线路并发数失败，用户id:" + sysUser.getId()+"|错误信息:" + lineResult.getMsg());
//                }
//                userLineList.add(new UserResourceDto(userId,maxLineConcurrency));
//            }
//        }
//        logger.info("查询每个用户线路最大并发数#end");
//        return userLineList;
//    }

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

    private List<UserResourceCache> getUserBotstenceRobotByUserId(List<SysUser> sysUserList) {
        logger.info("查询每个用户各个模板配置的机器人最大并发数#start");
        List<UserResourceCache> userBotstenceRobotList = new ArrayList<UserResourceCache>();
        if (sysUserList != null && sysUserList.size() > 0) {
            for (SysUser sysUser:sysUserList) {
                //根据用户获取各个用户配置机器人数量，调用机器人中心接口
                String userId = String.valueOf(sysUser.getId());
                UserResourceCache userResourceCache = null;
                Result.ReturnData<UserResourceCache> robotResult = robotRemote.queryUserResourceCache(userId);
                if (robotResult.success) {
                    if (robotResult.getBody() != null) {
                        userResourceCache = robotResult.getBody();
                        userBotstenceRobotList.add(userResourceCache);
                    }
                }else {
                    logger.info("调用机器人中心获取每个用户各个模板配置的机器人最大并发数失败，用户id:" + sysUser.getId()+"|错误信息:" + robotResult.getMsg());
                }
            }
        }
        logger.info("查询每个用户各个模板配置的机器人最大并发数#end");
        return userBotstenceRobotList;
    }
}
