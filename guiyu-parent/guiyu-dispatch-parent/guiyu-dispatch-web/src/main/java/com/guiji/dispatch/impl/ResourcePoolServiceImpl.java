package com.guiji.dispatch.impl;

import com.guiji.auth.api.IAuth;
import com.guiji.component.lock.DistributedLockHandler;
import com.guiji.component.lock.Lock;
import com.guiji.component.result.Result;
import com.guiji.dispatch.bean.PlanUserIdLineRobotDto;
import com.guiji.dispatch.bean.UserBotenceRes;
import com.guiji.dispatch.bean.UserLineBotenceVO;
import com.guiji.dispatch.bean.UserResourceDto;
import com.guiji.dispatch.constant.RedisConstant;
import com.guiji.dispatch.entity.DispatchRobotOp;
import com.guiji.dispatch.enums.BotstenceTypeEnum;
import com.guiji.dispatch.service.IGetPhonesInterface;
import com.guiji.dispatch.service.IPhonePlanQueueService;
import com.guiji.dispatch.service.IResourcePoolService;
import com.guiji.dispatch.service.RobotService;
import com.guiji.dispatch.util.AllotUserLineBotenceUtil;
import com.guiji.robot.api.IRobotRemote;
import com.guiji.robot.model.TemplateInfo;
import com.guiji.robot.model.UserAiCfgBaseInfoVO;
import com.guiji.robot.model.UserResourceCacheWithVersion;
import com.guiji.user.dao.entity.SysUser;
import com.guiji.utils.DateUtil;
import com.guiji.utils.IdGenUtil;
import com.guiji.utils.JsonUtils;
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
    private static final String REDIS_SYSTEM_MAX_PLAN_BY = "REDIS_SYSTEM_MAX_PLAN_BY";
    private static final String REDIS_USER_ROBOT_LINE_MAX_PLAN = "REDIS_USER_ROBOT_LINE_MAX_PLAN";

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private IAuth auth;
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
        redisUtil.set(REDIS_SYSTEM_MAX_ROBOT, systemMaxRobot);
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
    public boolean distributeByUser() throws Exception {
        Lock lock = new Lock("planDistributeJobHandler.Joblock", "planDistributeJobHandler.Joblock");
        if (distributedLockHandler.tryLock(lock, 1000L)) {
            try {
                logger.info("根据用户模板线路分配拨打号码比例#start");

                List<String> userIdList = (List<String>) redisUtil.get("USER_BILLING_DATA");

                //查询当前时间段有拨打计划的[用户|线路|模板]
                String hour = String.valueOf(DateUtil.getCurrentHour());
                //mod by xujin
                List<PlanUserIdLineRobotDto> userLineRobotList = getPhonesInterface.selectPlanGroupByUserIdRobot(hour);//this.getTestUserLineRobot();//
                List<UserLineBotenceVO> userLineBotenceVOList = new ArrayList<UserLineBotenceVO>();
                if (userLineRobotList != null) {
                    for (PlanUserIdLineRobotDto dto : userLineRobotList) {
                        UserLineBotenceVO userLineBotenceVO = new UserLineBotenceVO();
                        userLineBotenceVO.setUserId(dto.getUserId());
//                        userLineBotenceVO.setLineId(dto.getLineId());
                        userLineBotenceVO.setBotenceName(dto.getRobot());
                        userLineBotenceVOList.add(userLineBotenceVO);
                    }
                }
                //查询用户各个模板配置的机器人数量
                List<UserResourceCacheWithVersion> userBotstenceRobotList = getUserBotstenceRobotByUserId(getAllCompanyUsers());//this.getTestUserResource();//
                List<UserBotenceRes> ubList = this.filterUserBotstence(userBotstenceRobotList);//封装

                // 从redis获取系统最大并发数
                int systemMaxPlan = redisUtil.get(RedisConstant.RedisConstantKey.REDIS_SYSTEM_MAX_PLAN) == null ? 0
                        : (int) redisUtil.get(RedisConstant.RedisConstantKey.REDIS_SYSTEM_MAX_PLAN);
                if (systemMaxPlan == 0) {
                    logger.error("从redis获取系统最大并发数失败，获取的最大并发数为0");
                }

                List<UserLineBotenceVO> filterUserLineBotenceVOList = new ArrayList<UserLineBotenceVO>();
                /*******分别封装补充机器人数据，未做补充机器人数据    begin**********************************/
                //查询人工操作分配机器人操作数据
                List<DispatchRobotOp> allDisRobotList = robotService.queryDisRobotOpList(null, null);
                //封装人工补充操作机器人数据列表，剔除不做补充机器人列表
                List<DispatchRobotOp> opDisRobotList = new ArrayList<DispatchRobotOp>();
                //封装未补充操作机器人数据列表(即,去除需要补充的机器人数据)
                List<UserLineBotenceVO> excludeDisList = this.filterArtificialBotstence(allDisRobotList, opDisRobotList, userLineBotenceVOList);
                logger.info("人工操作机器人:{}", JsonUtils.bean2Json(opDisRobotList));
                /*******分别封装补充机器人数据，未做补充机器人数据    end**********************************/

                /**************计算机器人总数减去用户分配机器人数 和 补充机器人数   begin*********/
                logger.info("最大机器人总数减去手动分配机器人之前数量:{}", systemMaxPlan);
                //    systemMaxPlan = subSupplRobotNum(opDisRobotList, userBotstenceRobotList, systemMaxPlan);
                int artificialRobotNum = this.subSupplRobotNum(opDisRobotList, userBotstenceRobotList);
                systemMaxPlan -= artificialRobotNum;
                logger.info("最大机器人总数减去手动分配机器人之后数量:{}", systemMaxPlan);
                /**************计算机器人总数减去用户分配机器人数 和 补充机器人数   end*********/

                /************飞龙模板   先过滤不计入分配    begin******************************************/
                List<UserLineBotenceVO> flList = new ArrayList<UserLineBotenceVO>();
                int flRobotNum = this.filterFlBotstence(excludeDisList, ubList, flList, filterUserLineBotenceVOList);
                logger.info("飞龙模板数据:{}", JsonUtils.bean2Json(flList));
                systemMaxPlan -= flRobotNum;
                /************飞龙模板   先过滤不计入分配    end********************************************/

                //计算分配机器人数
                //    List<UserLineBotenceVO> userLineBotenceVOS = AllotUserLineBotenceUtil.allot(userLineBotenceVOList,userBotstenceRobotList,systemMaxPlan);
                List<UserLineBotenceVO> userLineBotenceVOS = AllotUserLineBotenceUtil.allot(filterUserLineBotenceVOList, userBotstenceRobotList, systemMaxPlan);
                //将补充操作的机器人分配数据重新加入到用户机器人分配列表中去
                List<UserLineBotenceVO> opList = filterOpUserBotstence(opDisRobotList);
                if (null != opList && opList.size() > 0) {
                    userLineBotenceVOS.addAll(opList);
                }
                //将飞龙模板机器人分配数据重新加入到用户机器人分配列表中
                if (null != flList && flList.size() > 0) {
                    userLineBotenceVOS.addAll(flList);
                }

                //从redis获取用户机器人分配数据
                List<UserLineBotenceVO> userLineBotenceVOSFromRedis = (List<UserLineBotenceVO>) redisUtil.get(RedisConstant.RedisConstantKey.REDIS_USER_ROBOT_LINE_MAX_PLAN);

                if (!isEquals(userLineBotenceVOS, userLineBotenceVOSFromRedis)) {
                    logger.info("机器人分配发生改变，重新分配开始，old:{},now:{}",userLineBotenceVOSFromRedis, userLineBotenceVOS);
                    Lock lockChange = new Lock("planDistributeJobHandler.lock", "planDistributeJobHandler.lock");
                    if (distributedLockHandler.tryLock(lockChange, 1000L)) {
                        try {
                            redisUtil.set(RedisConstant.RedisConstantKey.REDIS_USER_ROBOT_LINE_MAX_PLAN_VER, IdGenUtil.uuid());
                            redisUtil.set(RedisConstant.RedisConstantKey.REDIS_USER_ROBOT_LINE_MAX_PLAN, userLineBotenceVOS);
                            cleanQueueOfDeleted(userLineBotenceVOS, userLineBotenceVOSFromRedis);

                            logger.info("机器人分配发生改变，重新分配结束");
                        } finally {
                            distributedLockHandler.releaseLock(lockChange);
                        }
                    }
                }

                logger.info("根据用户模板线路分配拨打号码比例#end");
            } finally {
                distributedLockHandler.releaseLock(lock);
            }
        }

        return true;
    }


    private void cleanQueueOfDeleted(List<UserLineBotenceVO> newList, List<UserLineBotenceVO> oldList) {
        List<String> oldIdList = getUserBotenceList(oldList);
        List<String> newIdList = getUserBotenceList(newList);

        for (String str : oldIdList) {
            if (!newIdList.contains(str)) {
                String queue = RedisConstant.RedisConstantKey.REDIS_PLAN_QUEUE_USER_LINE_ROBOT + str;
                phonePlanQueueService.cleanQueueByQueueName(queue);
            }
        }
    }

    private List<String> getUserBotenceList(List<UserLineBotenceVO> list) {
        List<String> tmpList = new ArrayList<>();
        if (list == null || list.isEmpty()) {
            return tmpList;
        }
        for (UserLineBotenceVO dto : list) {
            tmpList.add(dto.getUserId() + "_" + dto.getBotenceName());
        }

        return tmpList;
    }

    /**
     * 封装人工配置机器人、排除
     *
     * @param allDisRobotList
     * @param opDisRobotList
     * @param userLineBotenceVOList
     * @return
     */
    private List<UserLineBotenceVO> filterArtificialBotstence(List<DispatchRobotOp> allDisRobotList, List<DispatchRobotOp> opDisRobotList,
                                                              List<UserLineBotenceVO> userLineBotenceVOList) {
        List<UserLineBotenceVO> excludeDisList = new ArrayList<UserLineBotenceVO>();
        if (null != allDisRobotList) {
            for (UserLineBotenceVO userBotence : userLineBotenceVOList) {
                int i = 0;
                for (DispatchRobotOp robotOp : allDisRobotList) {
                    //判断用户ID，话术模板ID是否相同，相同则表示记录已经进行了补充机器人操作
                    if (robotOp.getUserId().equals(userBotence.getUserId() + "")
                            && robotOp.getBotstenceId().equals(userBotence.getBotenceName())) {
                        opDisRobotList.add(robotOp);//封装加入人工补充操作机器人数据
                        i++;
                    }
                }
                if (i == 0) {//匹配未做补充机器人操作，加入列表
                    excludeDisList.add(userBotence);
                }
            }
        } else {
            excludeDisList = userLineBotenceVOList;
        }

        return excludeDisList;
    }

    private List<PlanUserIdLineRobotDto> getTestUserLineRobot() {
        List<PlanUserIdLineRobotDto> list = new ArrayList<PlanUserIdLineRobotDto>();
        for (int i = 1; i <= 10; i++) {
            PlanUserIdLineRobotDto r = new PlanUserIdLineRobotDto();
            r.setUserId(i);
            r.setRobot(i + "");
            list.add(r);
        }
        return list;
    }


    private List<UserResourceCacheWithVersion> getTestUserResource() {
        List<UserResourceCacheWithVersion> list = new ArrayList<UserResourceCacheWithVersion>();
        for (int i = 1; i <= 10; i++) {
            UserResourceCacheWithVersion ur = new UserResourceCacheWithVersion();
            ur.setUserId(i + "");
            ur.setAiNum(10);
            ur.setChgStatus("A");
            TemplateInfo t = new TemplateInfo();
            t.setTemplateId(i + "");
            t.setNum(i);
            t.setVersion(i % 2 == 0 ? 1 : 2);
            Map<String, TemplateInfo> map = new HashMap<String, TemplateInfo>();
            map.put(i + "", t);
            ur.setTempInfoMap(map);
            list.add(ur);
        }
        return list;
    }

    /**
     * 封装飞龙模板数据
     *
     * @param excludeDisList
     * @param ubList
     * @param filterUserLineBotenceVOList
     * @return
     */
    private int filterFlBotstence(List<UserLineBotenceVO> excludeDisList, List<UserBotenceRes> ubList, List<UserLineBotenceVO> flList,
                                  List<UserLineBotenceVO> filterUserLineBotenceVOList) {
        //    List<UserLineBotenceVO> flList = new ArrayList<UserLineBotenceVO>();
        int flRobotNum = 0;
        if (excludeDisList.size() > 0) {
            loopA:
            for (UserLineBotenceVO filterE : excludeDisList) {
                int i = 0;
                loopB:
                for (UserBotenceRes ub : ubList) {
                    if (null != ub && String.valueOf(filterE.getUserId()).equals(ub.getUserId())
                            && filterE.getBotenceName().equals(ub.getBotstenceId())
                            && BotstenceTypeEnum.FL.getType() == ub.getType()) {//飞龙模板
                        i++;
                        UserLineBotenceVO fl = new UserLineBotenceVO();
                        fl.setUserId(Integer.valueOf(ub.getUserId()));
                        fl.setBotenceName(ub.getBotstenceId());
                        fl.setMaxRobotCount(ub.getRobotDisNum());
                        flList.add(fl);

                        flRobotNum += ub.getRobotDisNum();

                        continue loopA;
                    }
                }
                if (i == 0) {//不属于飞龙
                    filterUserLineBotenceVOList.add(filterE);
                }
            }
        }
        return flRobotNum;
    }

    /**
     * 封装ROBOT分配的用户+话术模板及机器人数量
     *
     * @param userBotstenceRobotList
     * @return
     */
    private List<UserBotenceRes> filterUserBotstence(List<UserResourceCacheWithVersion> userBotstenceRobotList) {
        List<UserBotenceRes> ubList = new ArrayList<UserBotenceRes>();
        if (null != userBotstenceRobotList && userBotstenceRobotList.size() > 0) {
            for (UserResourceCacheWithVersion urc : userBotstenceRobotList) {
                String userId = urc.getUserId();
                urc.getTempInfoMap().forEach((k, v) -> {
                    String botstenceId = k;
                    TemplateInfo templ = null != v ? (TemplateInfo) v : null;

                    UserBotenceRes ub = new UserBotenceRes();
                    ub.setUserId(userId);
                    ub.setBotstenceId(botstenceId);
                    ub.setRobotMaxNum(urc.getAiNum());//分配给人的机器人总数
                    ub.setRobotDisNum(templ.getNum());//分配给人+模板的机器人数量
                    ub.setType(templ.getVersion());
                    ubList.add(ub);
                });
            }
        }
        return ubList;
    }

    /**
     * 封装人工操作的用户分配机器人数据
     *
     * @param opDisRobotList
     * @return
     */
    private List<UserLineBotenceVO> filterOpUserBotstence(List<DispatchRobotOp> opDisRobotList) {
        List<UserLineBotenceVO> opList = null;
        if (null != opDisRobotList && opDisRobotList.size() > 0) {
            opList = new ArrayList<UserLineBotenceVO>();
            for (DispatchRobotOp opRobot : opDisRobotList) {
                UserLineBotenceVO userLineBotence = new UserLineBotenceVO();
                userLineBotence.setUserId(Integer.valueOf(opRobot.getUserId()));
                userLineBotence.setBotenceName(opRobot.getBotstenceId());
                userLineBotence.setMaxRobotCount(opRobot.getCurrentNum());
                opList.add(userLineBotence);
            }
        }
        logger.info("封装人工操作的用户分配机器人数据:{}", null != opList ? JsonUtils.bean2Json(opList) : "空");
        return opList;
    }


    /**
     * 计算机器人总数减去用户分配机器人数 和 补充机器人数
     *
     * @param opDisRobotList
     * @param userBotstenceRobotList
     * @return
     */
    private int subSupplRobotNum(List<DispatchRobotOp> opDisRobotList, List<UserResourceCacheWithVersion> userBotstenceRobotList) {
        int opDisRobotNum = 0;
        //用户各个模板配置的机器人数量，拼接key:userId+templateId
        Map<String, Integer> allUserBotenceCfg = AllotUserLineBotenceUtil.convertUserRes2Map(userBotstenceRobotList);
        if (null != allUserBotenceCfg && (null != opDisRobotList && opDisRobotList.size() > 0)) {
            for (DispatchRobotOp opRobot : opDisRobotList) {
                String userBotstenceKey = opRobot.getUserId() + "-" + opRobot.getBotstenceId();
                if (allUserBotenceCfg.containsKey(userBotstenceKey)) {//用户模板机器人数包涵用户机器人
                    Integer robotNum = allUserBotenceCfg.get(userBotstenceKey);//robot分配模板机器人数量
                    Integer supplNum = opRobot.getSupplNum();//人工操作机器人数量
                    Integer supplType = opRobot.getSupplType();//操作标识   1-增加补充  2-扣减
                    opRobot.setRobotNum(robotNum);
                    //   systemMaxPlan -= robotNum;  //减除robot分配机器人数量
                    opDisRobotNum += robotNum;
                    if (1 == supplType) {//手动添加补充机器人
                        //        systemMaxPlan -= supplNum;//减除人工手动补充机器人数量
                        opRobot.setCurrentNum(robotNum + supplNum);
                        opDisRobotNum += supplNum;
                    } else if (2 == supplNum) {//手动减配机器人
                        //        systemMaxPlan += supplNum;
                        opRobot.setCurrentNum(robotNum - supplNum);
                        opDisRobotNum -= supplNum;
                    } else {
                        opRobot.setCurrentNum(robotNum);
                    }
                }
            }
        }
        //    return systemMaxPlan;
        return opDisRobotNum;
    }


    private boolean isEquals(List<UserLineBotenceVO> userLineBotenceVOS, List<UserLineBotenceVO> userLineBotenceVOSFromRedis) {
        if (userLineBotenceVOS == userLineBotenceVOSFromRedis) {
            return true;
        }

        if (userLineBotenceVOS == null || userLineBotenceVOSFromRedis == null) {
            return false;
        }

        if (userLineBotenceVOS.size() != userLineBotenceVOSFromRedis.size()) {
            return false;
        }

        List<String> tmpList = new ArrayList<>();
        for (UserLineBotenceVO dto : userLineBotenceVOS) {
//            tmpList.add(dto.getUserId() +"-"+ dto.getLineId() +"-"+ dto.getBotenceName() +"-"+ dto.getMaxRobotCount());
            tmpList.add(dto.getUserId() + "-" + dto.getBotenceName() + "-" + dto.getMaxRobotCount());
        }

        for (UserLineBotenceVO dto : userLineBotenceVOSFromRedis) {
//            if(!tmpList.contains(dto.getUserId() +"-"+ dto.getLineId() +"-"+ dto.getBotenceName() +"-"+ dto.getMaxRobotCount()))
            if (!tmpList.contains(dto.getUserId() + "-" + dto.getBotenceName() + "-" + dto.getMaxRobotCount())) {
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
            for (SysUser sysUser : sysUserList) {
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
                } else {
                    logger.info("调用机器人中心获取用户机器人数量失败，用户id:" + sysUser.getId() + "|错误信息:" + robotResult.getMsg());
                }
                userRobotList.add(new UserResourceDto(userId, maxRobotConcurrency));
            }
        }
        logger.info("查询每个用户机器人最大并发数#end");
        return userRobotList;
    }

    private List<UserResourceCacheWithVersion> getUserBotstenceRobotByUserId(List<SysUser> sysUserList) {
        logger.info("查询每个用户各个模板配置的机器人最大并发数#start");
        List<UserResourceCacheWithVersion> userBotstenceRobotList = new ArrayList<UserResourceCacheWithVersion>();
        if (sysUserList != null && sysUserList.size() > 0) {
            for (SysUser sysUser : sysUserList) {
                //根据用户获取各个用户配置机器人数量，调用机器人中心接口
                String userId = String.valueOf(sysUser.getId());
                UserResourceCacheWithVersion userResourceCache = null;
                //    Result.ReturnData<UserResourceCache> robotResult = robotRemote.queryUserResourceCache(userId);
                Result.ReturnData<UserResourceCacheWithVersion> robotResult = robotRemote.queryUserResourceCacheWithVersion(userId);
                if (robotResult.success) {
                    if (robotResult.getBody() != null) {
                        userResourceCache = robotResult.getBody();
                        userBotstenceRobotList.add(userResourceCache);
                    }
                } else {
                    logger.info("调用机器人中心获取每个用户各个模板配置的机器人最大并发数失败，用户id:" + sysUser.getId() + "|错误信息:" + robotResult.getMsg());
                }
            }
        }
        logger.info("查询每个用户各个模板配置的机器人最大并发数#end");
        return userBotstenceRobotList;
    }
}
