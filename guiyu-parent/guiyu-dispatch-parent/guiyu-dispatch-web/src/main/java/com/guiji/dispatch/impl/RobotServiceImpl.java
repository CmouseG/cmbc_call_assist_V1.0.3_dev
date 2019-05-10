package com.guiji.dispatch.impl;

import com.guiji.auth.api.IAuth;
import com.guiji.botsentence.api.IBotSentenceProcess;
import com.guiji.botsentence.api.entity.BotSentenceProcess;
import com.guiji.dispatch.bean.UserLineBotenceVO;
import com.guiji.dispatch.constant.RedisConstant;
import com.guiji.dispatch.dao.ext.RobotMapper;
import com.guiji.dispatch.dto.DispatchRobotOpDto;
import com.guiji.dispatch.dto.QueryDisRobotOpDto;
import com.guiji.dispatch.entity.DispatchRobotOp;
import com.guiji.dispatch.enums.SysDefaultExceptionEnum;
import com.guiji.dispatch.enums.SysDelEnum;
import com.guiji.dispatch.exception.BaseException;
import com.guiji.dispatch.service.RobotService;
import com.guiji.dispatch.sys.ResultPage;
import com.guiji.dispatch.util.DaoHandler;
import com.guiji.dispatch.util.ResHandler;
import com.guiji.dispatch.vo.DispatchRobotOpVo;
import com.guiji.robot.model.UserResourceCache;
import com.guiji.user.dao.entity.SysUser;
import com.guiji.utils.JsonUtils;
import com.guiji.utils.RedisUtil;
import com.netflix.discovery.converters.Auto;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 机器人Service
 */
@Service
public class RobotServiceImpl implements RobotService {

    private Logger logger = LoggerFactory.getLogger(RobotServiceImpl.class);

    @Autowired
    private RobotMapper robotMapper;

    @Autowired
    private IBotSentenceProcess iBotSentenceProcess;

    @Autowired
    private IAuth iAuth;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public  ResultPage<DispatchRobotOpVo> queryDispatchRobotOp(DispatchRobotOpDto dispatchRobotOpDto, ResultPage<DispatchRobotOpVo> page) {
        List<DispatchRobotOpVo> pageList = new LinkedList<DispatchRobotOpVo>();
        int len = 0;
        //获取到用户机器人资源
        Map<Object,Object> map = redisUtil.hmget(RedisConstant.RedisConstantKey.ROBOT_USER_RESOURCE);
        if(null != map){
            String userNameReq = null != dispatchRobotOpDto.getUserName()?dispatchRobotOpDto.getUserName():"";
            //有序treemap
            Map<Object, Object> treeMap = new TreeMap<>(map);
            List<DispatchRobotOpVo> list = new ArrayList<DispatchRobotOpVo>();
            for(Map.Entry<Object, Object> entry: treeMap.entrySet()){
                String userId = (String)entry.getKey();
                SysUser user = ResHandler.getResObj(iAuth.getUserById(Long.valueOf(userId)));
                if (user == null) {
                    continue;
                }
                String userName = user.getUsername();
                if(!userName.contains(userNameReq)){
                    continue;
                }

                UserResourceCache res = (UserResourceCache)entry.getValue();
                if(null != res){
                    Integer maxRobotNum = res.getAiNum();
                    if(null != maxRobotNum && maxRobotNum>0) {
                        Map<String, Integer> tempAiNumMap = res.getTempAiNumMap();
                        if (null != tempAiNumMap) {
                            for (Map.Entry<String, Integer> aiNum : tempAiNumMap.entrySet()) {
                                String botstenceId = aiNum.getKey();
                                if(!StringUtils.isEmpty(botstenceId)) {
                                    DispatchRobotOpVo robot = new DispatchRobotOpVo();
                                    robot.setUserId(userId);
                                    robot.setUserName(null != user?user.getUsername():null);
                                    robot.setBotstenceName(this.getBotstenceName(botstenceId));
                                    //    Integer robotNum = aiNum.getValue();
                                    robot.setBotstenceId(botstenceId);
                                    robot.setMaxRobotNum(aiNum.getValue());
                                    robot.setRobotNum(this.getActualRobotCount(Integer.valueOf(userId), botstenceId));
                                    list.add(robot);
                                    len++;
                                }else{
                                    continue;
                                }
                            }
                        }
                    }
                }
            }

            int pageNo = page.getPageNo();
            int pageSize = page.getPageSize();
            if(len > 0 && (len >= (pageNo * pageSize)
                    || (len > ((pageNo-1) * pageSize)) && len <= (pageNo * pageSize))){
                //获取分页列表
                int startIdx = (pageNo - 1) * pageSize + 1;
                int endIdx = (len >= (pageNo * pageSize)?(pageNo * pageSize):len);
                for (int idx = startIdx; idx <= endIdx; idx++) {
                    pageList.add(list.get(idx - 1));
                }
            }
        }

        //匹配人工补充机器人数
        List<DispatchRobotOp> opList = robotMapper.queryDisRobotOpList(null, null);
        if(null != pageList && pageList.size()>0
                && null != opList && opList.size()>0){
            loopA : for(DispatchRobotOpVo robot: pageList){
                loopB : for(DispatchRobotOp opRobot: opList){
                    if(robot.getUserId().equals(opRobot.getUserId())
                            && robot.getBotstenceId().equals(opRobot.getBotstenceId())){
                        robot.setSupplNum(opRobot.getSupplNum());
                        continue  loopA;
                    }
                }
            }
        }
        //列表
        page.setList(pageList);
        //获取总条数和页数
        page.setTotalItemAndPageNumber(len);
        return page;
    }

    /**
     * 获取模板名称
     * @param botstenceId
     * @return
     */
    private String getBotstenceName(String botstenceId){
        String botstenceName = null;
        Object obj = redisUtil.get(RedisConstant.RedisConstantKey.TMP_BOTSTENCENAME + botstenceId);
        if(null != obj){
            botstenceName = (String)obj;
        }else {
            List<BotSentenceProcess> botList
                    = ResHandler.getBotsentenceResObj(iBotSentenceProcess.getTemplateById(botstenceId));
            if (null != botList && botList.size() > 0) {
                botstenceName = null != botList.get(0) ? botList.get(0).getTemplateName() : null;
                redisUtil.set(RedisConstant.RedisConstantKey.TMP_BOTSTENCENAME + botstenceId, botstenceName,
                        RedisConstant.RedisConstantKey.TMP_BOTSTENCENAME_TIMEOUT);
            }
        }
        return botstenceName;
    }

    /**
     * 实际动态分配数量
     * @param userId
     * @param botstenceId
     * @return
     */
    private Integer getActualRobotCount(Integer userId, String botstenceId){
        Integer actualRobotCount = 0;
        List<UserLineBotenceVO> userLineRobotList = (List<UserLineBotenceVO>)redisUtil.get(RedisConstant.RedisConstantKey.REDIS_USER_ROBOT_LINE_MAX_PLAN);
        if(null != userLineRobotList){
            for(UserLineBotenceVO ulb: userLineRobotList){
                if(userId == ulb.getUserId() && botstenceId.equals(ulb.getBotenceName())){
                    actualRobotCount = ulb.getMaxRobotCount();
                    break;
                }
            }
        }
        return actualRobotCount;
    }

    @Override
    public boolean opUserRobotNum(DispatchRobotOpDto opRobotDto) {
        boolean bool = false;
        if(null != opRobotDto){
            DispatchRobotOp opRobot = new DispatchRobotOp();
            BeanUtils.copyProperties(opRobotDto, opRobot, DispatchRobotOp.class);
            String botstenceUserId = opRobotDto.getBotstenceUserId();
            String botstenceId = opRobotDto.getBotstenceId();
            opRobot.setUserId(botstenceUserId);
            opRobot.setBotstenceId(botstenceId);
            DispatchRobotOp opRobotExist = robotMapper.queryDisRobotOp(botstenceUserId, botstenceId);
            if(null != opRobotExist){
                opRobot.setUpdTime(new Date());
                bool = DaoHandler.getMapperBoolRes(robotMapper.updDisRobotOp(opRobot));
            }else{
                opRobot.setAddTime(new Date());
                opRobot.setDelFlag(SysDelEnum.NORMAL.getState());
                bool = DaoHandler.getMapperBoolRes(robotMapper.addDisRobotOp(opRobot));
            }
        }else{
            throw new BaseException(SysDefaultExceptionEnum.NULL_PARAM_EXCEPTION.getErrorCode(),
                    SysDefaultExceptionEnum.NULL_PARAM_EXCEPTION.getErrorMsg());
        }
        return bool;
    }

    /**
     * 查询补充分配机器人操作列表
     * @param queryDisRobotOpDto
     * @param page
     * @return
     */
    @Override
    public List<DispatchRobotOp> queryDisRobotOpList(QueryDisRobotOpDto queryDisRobotOpDto, ResultPage<DispatchRobotOp> page) {
        DispatchRobotOp robotParam = null;
        if(null != queryDisRobotOpDto){
            robotParam = new DispatchRobotOp();
            robotParam.setUserId(queryDisRobotOpDto.getBotstenceUserId());
            robotParam.setBotstenceId(queryDisRobotOpDto.getBotstenceId());
        }
        return robotMapper.queryDisRobotOpList(robotParam, page);
    }
}
