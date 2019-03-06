package com.guiji.dispatch.impl;

import com.guiji.auth.api.IAuth;
import com.guiji.botsentence.api.IBotSentenceProcess;
import com.guiji.botsentence.api.entity.BotSentenceProcess;
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
import com.guiji.utils.RedisUtil;
import com.netflix.discovery.converters.Auto;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
    public List<DispatchRobotOpVo> queryDispatchRobotOp() {
        List<DispatchRobotOpVo> list = new ArrayList<DispatchRobotOpVo>();
        Map<Object,Object> map = redisUtil.hmget(RedisConstant.RedisConstantKey.ROBOT_USER_RESOURCE);
        if(null != map){
            for(Map.Entry<Object, Object> entry: map.entrySet()){
                DispatchRobotOpVo robot = new DispatchRobotOpVo();
                String userId = (String)entry.getKey();
                UserResourceCache res = (UserResourceCache)entry.getValue();
                if(null != res){
                    Integer maxRobotNum = res.getAiNum();
                    if(null != maxRobotNum && maxRobotNum > 0) {
                        Map<String, Integer> tempAiNumMap = res.getTempAiNumMap();
                        if (null != tempAiNumMap) {
                            for (Map.Entry<String, Integer> aiNum : tempAiNumMap.entrySet()) {
                                String botstenceId = aiNum.getKey();
                                if(!StringUtils.isEmpty(botstenceId)) {
                                    SysUser user = ResHandler.getResObj(iAuth.getUserById(Long.valueOf(userId)));
                                    robot.setUserName(null != user?user.getUsername():null);
                                    List<BotSentenceProcess> botList = ResHandler.getBotsentenceResObj(iBotSentenceProcess.getTemplateById(botstenceId));
                                    if(null != botList){
                                        robot.setBotstenceName(botList.get(0).getTemplateName());
                                    }
                                    Integer robotNum = aiNum.getValue();
                                    robot.setBotstenceId(botstenceId);
                                    robot.setRobotNum(robotNum);
                                }else{
                                    continue;
                                }
                            }
                        }
                    }else{
                        continue;
                    }
                    robot.setMaxRobotNum(maxRobotNum);
                }
                robot.setUserId(userId);
                list.add(robot);
            }
        }

        //匹配人工补充机器人数
        List<DispatchRobotOp> opList = robotMapper.queryDisRobotOpList(null, null);
        if(null != list && list.size()>0
            && null != opList && opList.size()>0){
            for(DispatchRobotOpVo robot: list){
                for(DispatchRobotOp opRobot: opList){
                    if(robot.getUserId().equals(opRobot.getUserId())
                        && robot.getBotstenceId().equals(opRobot.getBotstenceId())){
                        robot.setSupplNum(opRobot.getSupplNum());
                    }
                }
            }
        }

        return list;
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
