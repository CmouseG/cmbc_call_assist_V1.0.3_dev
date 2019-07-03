package com.guiji.robot.service.impl;

import com.guiji.auth.api.IAuth;
import com.guiji.common.model.Page;
import com.guiji.component.lock.DistributedLockHandler;
import com.guiji.component.lock.Lock;
import com.guiji.component.result.Result.ReturnData;
import com.guiji.robot.constants.RobotConstants;
import com.guiji.robot.dao.UserAiCfgBaseInfoMapper;
import com.guiji.robot.dao.UserAiCfgHisInfoMapper;
import com.guiji.robot.dao.UserAiCfgInfoMapper;
import com.guiji.robot.dao.entity.*;
import com.guiji.robot.dao.entity.UserAiCfgInfoExample.Criteria;
import com.guiji.robot.exception.AiErrorEnum;
import com.guiji.robot.exception.RobotException;
import com.guiji.robot.model.UserResourceCache;
import com.guiji.robot.service.IUserAiCfgService;
import com.guiji.robot.service.vo.AiInuseCache;
import com.guiji.robot.service.vo.UserAiCfgBaseCondition;
import com.guiji.robot.service.vo.UserAiCfgQueryCondition;
import com.guiji.robot.util.ListUtil;
import com.guiji.user.dao.entity.SysOrganization;
import com.guiji.user.dao.entity.SysUser;
import com.guiji.utils.BeanUtil;
import com.guiji.utils.LocalCacheUtil;
import com.guiji.utils.StrUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.assertj.core.util.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @version V1.0
 * @ClassName: UserAiCfgServiceImpl
 * @Description: 用户-机器人配置服务
 * @date 2018年11月16日 下午2:21:53
 */
@Service
public class UserAiCfgServiceImpl implements IUserAiCfgService {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private UserAiCfgBaseInfoMapper userAiCfgBaseInfoMapper;
    @Autowired
    private UserAiCfgInfoMapper userAiCfgInfoMapper;
    @Autowired
    private UserAiCfgHisInfoMapper userAiCfgHisInfoMapper;
    @Autowired
    AiCacheService aiCacheService;
    @Autowired
    DistributedLockHandler distributedLockHandler;
    @Autowired
    IAuth iAuth;


    /**
     * 保存或者更新一条用户基本信息（不开放出去，必须调用变更服务）
     *
     * @param userAiCfgBaseInfo
     * @return
     */
    private UserAiCfgBaseInfo saveOrUpdate(UserAiCfgBaseInfo userAiCfgBaseInfo) {
        if (userAiCfgBaseInfo != null) {
            if (StrUtils.isEmpty(userAiCfgBaseInfo.getId())) {
                //如果主键为空，那么新增一条信息
                userAiCfgBaseInfo.setCrtTime(new Date());
                userAiCfgBaseInfoMapper.insert(userAiCfgBaseInfo);
            } else {
                //主键不为空，更新信息
                userAiCfgBaseInfo.setUpdateTime(new Date());
                userAiCfgBaseInfoMapper.updateByPrimaryKey(userAiCfgBaseInfo);
            }
        }
        return userAiCfgBaseInfo;
    }


    /**
     * 机器人数量总控配置
     *
     * @param userAiCfgBaseInfo
     * @return
     */
    @Transactional
    public UserAiCfgBaseInfo putupUserCfgBase(UserAiCfgBaseInfo userAiCfgBaseInfo) {
        if (userAiCfgBaseInfo != null) {
            UserAiCfgBaseInfo existUserAiCfgBaseInfo = null;
            if (StrUtils.isNotEmpty(userAiCfgBaseInfo.getId())) {
                //如果主键不为空，那主键查询
                existUserAiCfgBaseInfo = userAiCfgBaseInfoMapper.selectByPrimaryKey(userAiCfgBaseInfo.getId());
            } else if (StrUtils.isNotEmpty(userAiCfgBaseInfo.getUserId())) {
                //用户ID不为空，根据用户查询是否存在
                UserAiCfgBaseInfoExample example = new UserAiCfgBaseInfoExample();
                example.createCriteria().andUserIdEqualTo(userAiCfgBaseInfo.getUserId());
                List<UserAiCfgBaseInfo> list = userAiCfgBaseInfoMapper.selectByExample(example);
                if (ListUtil.isNotEmpty(list)) {
                    existUserAiCfgBaseInfo = list.get(0);
                }
            }
            //校验是否有超过企业机器人配置的上限
            this.checkOverOrgAiNum(userAiCfgBaseInfo, existUserAiCfgBaseInfo);
            if (existUserAiCfgBaseInfo != null) {
                //更新
                String userId = existUserAiCfgBaseInfo.getUserId();
                //查询用户拆分配置
                List<UserAiCfgInfo> cfgInfoList = this.queryUserAiCfgListByUserId(userId);
                boolean isCleanFlag = false;    //是否清数据
                if (userAiCfgBaseInfo.getAiTotalNum() < existUserAiCfgBaseInfo.getAiTotalNum()
                        || this.subTemplateChange(userAiCfgBaseInfo.getTemplateIds(), cfgInfoList)) {
                    //如果总数量减少了或者 模板变更减少了，删除现有拆分数据，并重新自动分配
                    if (ListUtil.isNotEmpty(cfgInfoList)) {
                        //删除用户数据
                        this.delUserAllCfg(userId);
                    }
                    isCleanFlag = true;
                }
                userAiCfgBaseInfo.setCrtTime(existUserAiCfgBaseInfo.getCrtTime());
                if (StrUtils.isEmpty(userAiCfgBaseInfo.getTemplateIds())) {
                    userAiCfgBaseInfo.setTemplateIds(existUserAiCfgBaseInfo.getTemplateIds());
                } else if (isCleanFlag || cfgInfoList == null || cfgInfoList.isEmpty()) {
                    //如果新的模板不为空，并且（数据被清理过了，或者原本就没有拆分数据），那么初始化下
                    UserAiCfgInfo userAiCfgInfo = new UserAiCfgInfo();
                    BeanUtil.copyProperties(userAiCfgBaseInfo, userAiCfgInfo);
                    userAiCfgInfo.setId(null); //清空id
                    userAiCfgInfo.setAiNum(userAiCfgBaseInfo.getAiTotalNum()); //机器人总数(初始化时为全部)
                    this.userAiCfgChange(userAiCfgBaseInfo, userAiCfgInfo);
                } else {
                    //如果模板增加了，那么从拆分项中取一条数据，将该模板新增上去
                    String nowTemplates = userAiCfgBaseInfo.getTemplateIds();    //本次最新模板
                    String existTemplates = existUserAiCfgBaseInfo.getTemplateIds();    //库中的模板
                    if (StrUtils.isNotEmpty(nowTemplates) && StrUtils.isNotEmpty(existTemplates)) {
                        String[] nowTemplateArray = nowTemplates.split(",");
                        String[] existTemplateArray = existTemplates.split(",");
                        List<String> addTemplateLis = ListUtil.removeAll(Arrays.asList(nowTemplateArray), Arrays.asList(existTemplateArray));    //获取本次新增的模板
                        if (addTemplateLis != null && !addTemplateLis.isEmpty()) {
                            String addTemplateStr = StringUtils.join(addTemplateLis.toArray(), ",");    //将本次新增的模板用逗号分隔
                            UserAiCfgInfo userAiCfgInfo = cfgInfoList.get(0);    //取一条拆分项，将模板增加进去
                            userAiCfgInfo.setTemplateIds(userAiCfgInfo.getTemplateIds() + "," + addTemplateStr);
                            this.userAiCfgChange(userAiCfgBaseInfo, userAiCfgInfo);
                        }
                    }
                }
                if (StrUtils.isEmpty(userAiCfgBaseInfo.getUserId())) {
                    userAiCfgBaseInfo.setUserId(userId);
                }
                userAiCfgBaseInfo.setId(existUserAiCfgBaseInfo.getId());
                userAiCfgBaseInfo.setOrgCode(existUserAiCfgBaseInfo.getOrgCode());
                userAiCfgBaseInfo.setCrtUser(existUserAiCfgBaseInfo.getCrtUser());
                userAiCfgBaseInfo.setCrtTime(existUserAiCfgBaseInfo.getCrtTime());
            } else {
                //新增
                //1、初始化一条用户机器人线路拆分
                UserAiCfgInfo userAiCfgInfo = new UserAiCfgInfo();
                BeanUtil.copyProperties(userAiCfgBaseInfo, userAiCfgInfo);
                userAiCfgInfo.setAiNum(userAiCfgBaseInfo.getAiTotalNum()); //机器人总数(初始化时为全部)
                this.userAiCfgChange(userAiCfgBaseInfo, userAiCfgInfo);
                //设置用户机构号
                //查询用户机构信息
                if (StrUtils.isNotEmpty(userAiCfgBaseInfo.getUserId())) {
                    SysUser sysUser = LocalCacheUtil.getT(userAiCfgBaseInfo.getUserId());
                    if (sysUser != null) {
                        //缓存中有，直接取
                        userAiCfgBaseInfo.setOrgCode(sysUser.getOrgCode());
                    } else {
                        //缓存中没有,重新查，并放入内存
                        ReturnData<SysUser> userRD = iAuth.getUserById(Long.valueOf(userAiCfgBaseInfo.getUserId()));
                        if (userRD != null && userRD.getBody() != null) {
                            //内存1个小时有效
                            LocalCacheUtil.set(userAiCfgBaseInfo.getUserId(), userRD.getBody(), LocalCacheUtil.ONE_HOUR);
                            userAiCfgBaseInfo.setOrgCode(userRD.getBody().getOrgCode());
                        } else {
                            logger.error("用户ID:{},查询不到用户信息，返回：", userAiCfgBaseInfo.getUserId(), userRD);
                        }
                    }
                }
            }
            //2、新增或者更新基本信息
            this.saveOrUpdate(userAiCfgBaseInfo);
        }
        return userAiCfgBaseInfo;
    }


    /**
     * 查询用户所属企业下可用的机器人数量
     *
     * @param userId
     * @return
     */
    @Override
    public int queryOrgAvailableAiNum(String userId) {
        if (StrUtils.isNotEmpty(userId)) {
            ReturnData<SysOrganization> orgData = iAuth.getOrgByUserId(Long.valueOf(userId));
            if (orgData != null && orgData.getBody() != null) {
                int orgAiNum = orgData.getBody().getRobot() == null ? 0 : orgData.getBody().getRobot();
                List<UserAiCfgBaseInfo> assignList = this.queryUserAiCfgBaseInfoByOrgCode(orgData.getBody().getCode());
                int assignAiNum = 0;
                if (ListUtil.isNotEmpty(assignList)) {
                    for (UserAiCfgBaseInfo cfg : assignList) {
                        assignAiNum = assignAiNum + (cfg.getAiTotalNum() == null ? 0 : cfg.getAiTotalNum());
                    }
                }
                logger.info("企业code：{},企业名称：{},已经分配的机器人数量:{},企业配置的总数量：{}", orgData.getBody().getCode(), orgData.getBody().getName(), assignAiNum, orgAiNum);
                int availableAiNum = orgAiNum - assignAiNum;
                if (availableAiNum < 0) availableAiNum = 0;
                return availableAiNum;
            } else {
                logger.error("查询用户企业失败,{}", orgData);
                throw new RobotException(AiErrorEnum.AI00060031.getErrorCode(), AiErrorEnum.AI00060031.getErrorMsg());
            }
        }
        return 0;
    }


    /**
     * 校验本次为用户分配机器人--是否有超过用户所属企业配置的机器人数量
     *
     * @param userAiCfgBaseInfo
     * @param existUserAiCfgBaseInfo
     */
    private void checkOverOrgAiNum(UserAiCfgBaseInfo userAiCfgBaseInfo, UserAiCfgBaseInfo existUserAiCfgBaseInfo) {
        //1、查询该用户所属企业所有已分配的机器人数量
        ReturnData<SysOrganization> orgData = iAuth.getOrgByUserId(Long.valueOf(userAiCfgBaseInfo.getUserId()));
        if (orgData != null && orgData.getBody() != null) {
            int orgAiNum = orgData.getBody().getRobot() == null ? 0 : orgData.getBody().getRobot();
            logger.info("用户：{},企业code:{},企业名称：{},企业配置的机器人上限：{}", userAiCfgBaseInfo.getUserId(), orgData.getBody().getCode(), orgData.getBody().getName(), orgAiNum);
            List<UserAiCfgBaseInfo> assignList = this.queryUserAiCfgBaseInfoByOrgCode(orgData.getBody().getCode());
            //本次操作将更新的数量，若为新增，则直接累加，若为修改，则需扣减（加上本次数量，减去原有数量）
            int assignAiNum = userAiCfgBaseInfo.getAiTotalNum() == null ? 0 : userAiCfgBaseInfo.getAiTotalNum();
            if (ListUtil.isNotEmpty(assignList)) {
                for (UserAiCfgBaseInfo cfg : assignList) {
                    assignAiNum = assignAiNum + (cfg.getAiTotalNum() == null ? 0 : cfg.getAiTotalNum());
                }
            }
            if (existUserAiCfgBaseInfo != null) {
                //如果是修改用户机器人，将之前的累加和再减去原始数据中的数量
                assignAiNum = assignAiNum - (existUserAiCfgBaseInfo.getAiTotalNum() == null ? 0 : existUserAiCfgBaseInfo.getAiTotalNum());
            }
            //2、查询用户所属企业配置的机器人上限
            logger.info("机构下变更后机器人数量:{},企业配置的机器人数量:{}", assignAiNum, orgAiNum);
            if (assignAiNum > orgAiNum) {
                throw new RobotException(AiErrorEnum.AI00060032.getErrorCode(), "用户本次配置的机器人数量超过了企业下可用机器人数量");
            }
        } else {
            logger.error("查询用户企业失败,{}", orgData);
            throw new RobotException(AiErrorEnum.AI00060031.getErrorCode(), AiErrorEnum.AI00060031.getErrorMsg());
        }
    }


    /**
     * 查询用户机器人配置基本信息
     *
     * @param userId
     * @return
     */
    @Override
    public UserAiCfgBaseInfo queryUserAiCfgBaseInfoByUserId(String userId) {
        if (StrUtils.isNotEmpty(userId)) {
            UserAiCfgBaseInfoExample example = new UserAiCfgBaseInfoExample();
            example.createCriteria().andUserIdEqualTo(userId);
            List<UserAiCfgBaseInfo> list = userAiCfgBaseInfoMapper.selectByExample(example);
            if (ListUtil.isNotEmpty(list)) {
                return list.get(0);
            }
        }
        return null;
    }


    /**
     * 查询机构号查询机器人配置基本信息
     *
     * @param orgCode
     * @return
     */
    public List<UserAiCfgBaseInfo> queryUserAiCfgBaseInfoByOrgCode(String orgCode) {
        if (StrUtils.isNotEmpty(orgCode)) {
            UserAiCfgBaseInfoExample example = new UserAiCfgBaseInfoExample();
            example.createCriteria().andOrgCodeLike(orgCode + "%");
            return userAiCfgBaseInfoMapper.selectByExample(example);
        }
        return null;
    }


    /**
     * 分页查询 用户机器人配置基本信息
     *
     * @param pageNo
     * @param pageSize
     * @param userId
     * @return
     */
    @Override
    public Page<UserAiCfgBaseInfo> queryUserAiCfgBaseInfoFroPageByUserId(UserAiCfgBaseCondition condition) {
        Page<UserAiCfgBaseInfo> page = new Page<UserAiCfgBaseInfo>();
        int pageNo = condition.getPageNo();
        int pageSize = condition.getPageSize();
        int totalRecord = 0;
        int limitStart = (pageNo - 1) * pageSize;    //起始条数
        int limitEnd = pageSize;    //查询条数
        UserAiCfgBaseInfoExample example = this.queryExample(condition);
        //查询总数
        totalRecord = userAiCfgBaseInfoMapper.countByExample(example);
        if (totalRecord > 0) {
            example.setLimitStart(limitStart);
            example.setLimitEnd(limitEnd);
            List<UserAiCfgBaseInfo> list = userAiCfgBaseInfoMapper.selectByExample(example);
            page.setRecords(list);
        }
        page.setPageNo(pageNo);
        page.setPageSize(pageSize);
        page.setTotal(totalRecord);
        return page;
    }

    /**
     * 查询条件
     *
     * @param condition
     * @return
     */
    private UserAiCfgBaseInfoExample queryExample(UserAiCfgBaseCondition condition) {
        UserAiCfgBaseInfoExample example = new UserAiCfgBaseInfoExample();
        if (condition != null) {
            com.guiji.robot.dao.entity.UserAiCfgBaseInfoExample.Criteria criteria = example.createCriteria();
            if (condition.getAuthLevel() != null) {
                if (RobotConstants.USER_DATA_AUTH_ME == condition.getAuthLevel()) {
                    criteria.andUserIdEqualTo(condition.getUserId());
                } else {
                    if (StrUtils.isNotEmpty(condition.getQUserId())) {
                        criteria.andUserIdEqualTo(condition.getQUserId());
                    }
                    if (RobotConstants.USER_DATA_AUTH_ORG == condition.getAuthLevel()) {
                        criteria.andOrgCodeEqualTo(condition.getOrgCode());
                    } else if (RobotConstants.USER_DATA_AUTH_ORGTREE == condition.getAuthLevel()) {
                        criteria.andOrgCodeLike(condition.getOrgCode() + "%");
                    }
                }
            } else {
                if (StrUtils.isNotEmpty(condition.getQUserId())) {
                    criteria.andUserIdEqualTo(condition.getQUserId());
                }
            }
        }
        example.setOrderByClause(" id desc");
        return example;
    }


    /**
     * 保存或者更新一条用户-机器人配置信息（不开放出去，必须调用变更服务）
     * 同时记录历史
     *
     * @param userAiCfgInfo
     * @return
     */
    private UserAiCfgInfo saveOrUpdate(UserAiCfgInfo userAiCfgInfo) {
        if (userAiCfgInfo != null) {
            UserAiCfgHisInfo record = new UserAiCfgHisInfo();
            if (StrUtils.isEmpty(userAiCfgInfo.getId())) {
                //如果主键为空，那么新增一条信息
                userAiCfgInfo.setStatus(RobotConstants.USER_CFG_STATUS_S); //正常状态
                userAiCfgInfo.setCrtTime(new Date());
                int id = userAiCfgInfoMapper.insert(userAiCfgInfo);
                userAiCfgInfo.setId(id);
                record.setHandleType(RobotConstants.HANDLE_TYPE_A); //新增
            } else {
                //主键不为空，更新信息
                userAiCfgInfo.setUpdateTime(new Date());
                userAiCfgInfoMapper.updateByPrimaryKey(userAiCfgInfo);
                record.setHandleType(RobotConstants.HANDLE_TYPE_U); //更新
            }
            //记录一条用户账户变更历史
            record.setCrtTime(new Date());
            record.setBusiId(userAiCfgInfo.getId());
            BeanUtil.copyProperties(userAiCfgInfo, record);
            record.setTemplateId(userAiCfgInfo.getTemplateIds());
            record.setId(null); //新增历史
            userAiCfgHisInfoMapper.insert(record);
        }
        return userAiCfgInfo;
    }


    /**
     * 根据用户编号查询用户-机器人配置信息列表
     *
     * @return
     */
    @Override
    public List<UserAiCfgInfo> queryUserAiCfgListByUserId(String userId) {
        if (userId != null) {
            UserAiCfgInfoExample example = new UserAiCfgInfoExample();
            example.createCriteria().andUserIdEqualTo(userId);
            return userAiCfgInfoMapper.selectByExample(example);
        }
        return null;
    }

    @Override
    public List<UserAiCfgBaseInfo> queryUserAiCfgInfoByCondition(UserAiCfgBaseCondition condition) {


        UserAiCfgBaseInfoExample example = this.queryExample(condition);


        return userAiCfgBaseInfoMapper.selectByExample(example);

    }

    /**
     * 根据用户编号查询用户符合话术模板的配置列表
     *
     * @return
     */
    @Override
    public List<UserAiCfgInfo> queryUserAiCfgListByUserId(String userId, String templateId) {
        if (StrUtils.isNotEmpty(userId) && StrUtils.isNotEmpty(templateId)) {
            //查询用户所有模板
            List<UserAiCfgInfo> list = this.queryUserAiCfgListByUserId(userId);
            List<UserAiCfgInfo> rtnList = new ArrayList<UserAiCfgInfo>();
            if (ListUtil.isNotEmpty(list)) {
                for (UserAiCfgInfo cfg : list) {
                    if (StrUtils.isNotEmpty(cfg.getTemplateIds()) && cfg.getTemplateIds().contains(templateId)) {
                        //如果该配置可以使用该话术模板，返回
                        rtnList.add(cfg);
                    }
                }
            }
            return rtnList;
        }
        return null;
    }


    /**
     * 查询用户下可以使用某个话术的机器人列表
     *
     * @param userId
     * @param templateId
     * @return
     */
    @Override
    public List<UserAiCfgInfo> queryUserAiCfgListByUserIdAndTemplate(String userId, String templateId) {
        if (StrUtils.isNotEmpty(userId) && StrUtils.isNotEmpty(templateId)) {
            //查询用户全部配置信息
            List<UserAiCfgInfo> userAllAiCfgList = this.queryUserAiCfgListByUserId(userId);
            if (ListUtil.isNotEmpty(userAllAiCfgList)) {
                Iterator<UserAiCfgInfo> it = userAllAiCfgList.iterator();
                while (it.hasNext()) {
                    UserAiCfgInfo aiCfg = it.next();
                    if (!aiCfg.getTemplateIds().contains(templateId)) {
                        it.remove();
                    }
                }
            }
            return userAllAiCfgList;
        }
        return null;
    }


    /**
     * 分页查询 用户机器人配置详情
     *
     * @param pageNo
     * @param pageSize
     * @param condition
     * @return
     */
    @Override
    public Page<UserAiCfgInfo> queryCustAccountForPage(int pageNo, int pageSize, UserAiCfgQueryCondition condition) {
        Page<UserAiCfgInfo> page = new Page<UserAiCfgInfo>();
        int totalRecord = 0;
        int limitStart = (pageNo - 1) * pageSize;    //起始条数
        int limitEnd = pageSize;    //查询条数
        UserAiCfgInfoExample example = this.queryExample(condition);
        //查询总数
        totalRecord = userAiCfgInfoMapper.countByExample(example);
        if (totalRecord > 0) {
            example.setLimitStart(limitStart);
            example.setLimitEnd(limitEnd);
            List<UserAiCfgInfo> list = userAiCfgInfoMapper.selectByExample(example);
            page.setRecords(list);
        }
        page.setPageNo(pageNo);
        page.setPageSize(pageSize);
        page.setTotal(totalRecord);
        return page;
    }


    /**
     * 用户资源变更服务
     * 用户新增线路、减少线路、变更绑定的模板 都需要更新下用户资源缓存数据
     * 因用户资源变更影响较大，所以此处增加缓存锁，防止并发的情况
     *
     * @param userBaseInfo  为空，实时查询，如果不为空直接使用（场景：新增用户机器人总量时初始化使用，此时库中还没有数据）
     * @param userAiCfgInfo
     * @return
     */
    @Override
    @Transactional
    public UserAiCfgInfo userAiCfgChange(UserAiCfgBaseInfo userBaseInfo, UserAiCfgInfo userAiCfgInfo) {
        Lock lock = new Lock(RobotConstants.LOCK_NAME_CFG + userAiCfgInfo.getUserId(), RobotConstants.LOCK_NAME_CFG + userAiCfgInfo.getUserId());
        if (distributedLockHandler.tryLock(lock)) { // 持锁
            try {
                Integer id = userAiCfgInfo.getId();
                //查询用户资源配置缓存数据
                UserResourceCache userResourceCache = aiCacheService.getUserResource(userAiCfgInfo.getUserId());
                if (userResourceCache == null) {
                    //初始化
                    userResourceCache = new UserResourceCache();
                    userResourceCache.setUserId(userAiCfgInfo.getUserId());
                }
                if (id != null) {
                    //Id不为空，更新
                    //更新1个配置项
                    //查询用户机器人数量
                    UserAiCfgInfo existUserAiCfgInfo = userAiCfgInfoMapper.selectByPrimaryKey(id);
                    userAiCfgInfo.setCrtUser(existUserAiCfgInfo.getCrtUser());
                    userAiCfgInfo.setCrtTime(existUserAiCfgInfo.getCrtTime());
                    userAiCfgInfo.setStatus(existUserAiCfgInfo.getStatus());
                    //如果本次调整的机器人数量减少了，或者话术模板变更了，都认为机器人变更是资源减少的，此处设置下减少，机器人管理那边会清空机器人，重新根据配置拉起。
                    if (userAiCfgInfo.getAiNum() < existUserAiCfgInfo.getAiNum()) {
                        userResourceCache.setAiNum(userResourceCache.getAiNum() + (userAiCfgInfo.getAiNum() - existUserAiCfgInfo.getAiNum())); //变更后数量
                        userResourceCache.setChgStatus(RobotConstants.USER_CHG_STATUS_S); //资源变更-减少
                    } else if (userAiCfgInfo.getAiNum() > existUserAiCfgInfo.getAiNum()) {
                        //本次变更机器人数量增加量
                        int addAiNum = userAiCfgInfo.getAiNum() - existUserAiCfgInfo.getAiNum();
                        //新增配置项
                        if (this.isOverAiNumCheck(userBaseInfo, userAiCfgInfo.getUserId(), addAiNum)) {
                            //校验是否有超过用户总机器人数量
                            throw new RobotException(AiErrorEnum.AI00060021.getErrorCode(), AiErrorEnum.AI00060021.getErrorMsg());
                        }
                        userResourceCache.setAiNum(userResourceCache.getAiNum() + addAiNum); //变更后数量
                        if (!RobotConstants.USER_CHG_STATUS_S.equals(userResourceCache.getChgStatus())) {
                            //如果目前的资源变更状态是减少，处理完成前，那么不能变为增加
                            userResourceCache.setChgStatus(RobotConstants.USER_CHG_STATUS_A); //资源变更-增加
                        }
                    }
                    if (!userAiCfgInfo.getTemplateIds().equals(existUserAiCfgInfo.getTemplateIds())) {
                        //如果模板发生了变化，不管机器人数量增加与否，都认为资源发生了减少，机器人重新分配
                        userResourceCache.setChgStatus(RobotConstants.USER_CHG_STATUS_S); //资源变更-减少
                    }
                } else {
                    //新增配置项
                    if (this.isOverAiNumCheck(userBaseInfo, userAiCfgInfo.getUserId(), userAiCfgInfo.getAiNum())) {
                        //校验是否有超过用户总机器人数量
                        throw new RobotException(AiErrorEnum.AI00060021.getErrorCode(), AiErrorEnum.AI00060021.getErrorMsg());
                    }
                    //新增下缓存中用户机器人总数量
                    userResourceCache.setAiNum(userResourceCache.getAiNum() + userAiCfgInfo.getAiNum());
                    if (!RobotConstants.USER_CHG_STATUS_S.equals(userResourceCache.getChgStatus())) {
                        //如果目前的资源变更状态是减少，处理完成前，那么不能变为增加
                        userResourceCache.setChgStatus(RobotConstants.USER_CHG_STATUS_A); //资源变更-增加
                    }
                }
                //更新用户资源缓存，重新检查下，如果现在用户本身没有分配的资源，不需要设置状态
                List<AiInuseCache> userAiInUseLis = aiCacheService.queryUserAiInUseList(userAiCfgInfo.getUserId());
                if (userAiInUseLis == null || userAiInUseLis.isEmpty()) {
                    userResourceCache.setChgStatus(null);
                }
                //资源变更，删除后，重新查询
                aiCacheService.delUserResource(userAiCfgInfo.getUserId());
                //落库
                userAiCfgInfo = this.saveOrUpdate(userAiCfgInfo);
                return userAiCfgInfo;
            } catch (RobotException e) {
                throw e;
            } catch (Exception e1) {
                logger.error("机器人线路拆分保存异常！", e1);
                throw new RobotException(AiErrorEnum.AI00060022.getErrorCode(), AiErrorEnum.AI00060022.getErrorMsg());
            } finally {
                //释放锁
                distributedLockHandler.releaseLock(lock);
            }
        } else {
            logger.warn("用户机器人配置变更未能获取锁！！！");
        }
        return null;
    }


    /**
     * 删除用户所有分配的数据
     *
     * @param userId
     * @param id
     */
    @Transactional
    public void delUserAllCfg(String userId) {
        if (StrUtils.isNotEmpty(userId)) {
            Lock lock = new Lock(RobotConstants.LOCK_NAME_CFG + userId, RobotConstants.LOCK_NAME_CFG + userId);
            if (distributedLockHandler.tryLock(lock)) { // 持锁
                try {
                    //删除表数据
                    UserAiCfgInfoExample example = new UserAiCfgInfoExample();
                    example.createCriteria().andUserIdEqualTo(userId);
                    List<UserAiCfgInfo> userAiCfgList = userAiCfgInfoMapper.selectByExample(example);
                    if (ListUtil.isNotEmpty(userAiCfgList)) {
                        for (UserAiCfgInfo uac : userAiCfgList) {
                            //逐条删除数据
                            userAiCfgInfoMapper.deleteByPrimaryKey(uac.getId());
                            //记录一条用户账户变更历史
                            UserAiCfgHisInfo record = new UserAiCfgHisInfo();
                            record.setCrtTime(new Date());
                            record.setBusiId(uac.getId());
                            BeanUtil.copyProperties(uac, record);
                            record.setId(null);
                            record.setHandleType(RobotConstants.HANDLE_TYPE_D); //删除
                            userAiCfgHisInfoMapper.insert(record);
                        }
                    }
                    //删除缓存数据
                    aiCacheService.delUserResource(userId);
                } catch (RobotException e) {
                    throw e;
                } catch (Exception e1) {
                    logger.error("机器人线路拆分删除异常！", e1);
                    throw new RobotException(AiErrorEnum.AI00060023.getErrorCode(), AiErrorEnum.AI00060023.getErrorMsg());
                } finally {
                    //释放锁
                    distributedLockHandler.releaseLock(lock);
                }
            } else {
                logger.error("用户资源删除未能获取到锁!");
            }
        } else {
            logger.error("userid不能为空！");
        }
    }


    /**
     * 删除用户一条资源配置信息
     *
     * @param userId
     * @param id
     */
    @Override
    @Transactional
    public void delUserCfg(String userId, int id) {
        if (StrUtils.isNotEmpty(id) && StrUtils.isNotEmpty(userId)) {
            Lock lock = new Lock(RobotConstants.LOCK_NAME_CFG + userId, RobotConstants.LOCK_NAME_CFG + userId);
            if (distributedLockHandler.tryLock(lock)) { // 持锁
                try {
                    //根据id查询用户存量缓存数据
                    UserAiCfgInfo existUserAiCfgInfo = userAiCfgInfoMapper.selectByPrimaryKey(id);
                    //删除数据
                    userAiCfgInfoMapper.deleteByPrimaryKey(id);
                    if (existUserAiCfgInfo != null) {
                        if (RobotConstants.USER_CFG_STATUS_S == existUserAiCfgInfo.getStatus()) {
                            //如果删除的是正常的数据，那么需要做下变更
                            //用户资源缓存
                            UserResourceCache userResourceCache = aiCacheService.getUserResource(existUserAiCfgInfo.getUserId());
                            if (userResourceCache == null) {
                                //初始化
                                userResourceCache = new UserResourceCache();
                                userResourceCache.setUserId(existUserAiCfgInfo.getUserId());
                            }
                            userResourceCache.setAiNum(userResourceCache.getAiNum() - existUserAiCfgInfo.getAiNum()); //机器人数量变更
                            userResourceCache.setChgStatus(RobotConstants.USER_CHG_STATUS_S); //资源变更-减少
                            //更新用户资源缓存，重新检查下，如果现在用户本身没有分配的资源，不需要设置状态
                            List<AiInuseCache> userAiInUseLis = aiCacheService.queryUserAiInUseList(existUserAiCfgInfo.getUserId());
                            if (userAiInUseLis == null || userAiInUseLis.isEmpty()) {
                                userResourceCache.setChgStatus(null);
                            }
                            //资源变更，删除后，重新查询
                            aiCacheService.delUserResource(existUserAiCfgInfo.getUserId());
                        } else {
                            logger.info("本次删除的是状态不正常的数据{}，不需要触发资源变更！", id);
                        }
                        //记录一条用户账户变更历史
                        UserAiCfgHisInfo record = new UserAiCfgHisInfo();
                        record.setCrtTime(new Date());
                        record.setBusiId(existUserAiCfgInfo.getId());
                        BeanUtil.copyProperties(existUserAiCfgInfo, record);
                        record.setId(null);
                        record.setHandleType(RobotConstants.HANDLE_TYPE_D); //删除
                        userAiCfgHisInfoMapper.insert(record);
                    } else {
                        logger.error("要删除的数据{}已不存在..", id);
                    }
                } catch (RobotException e) {
                    throw e;
                } catch (Exception e1) {
                    logger.error("机器人线路拆分删除异常！", e1);
                    throw new RobotException(AiErrorEnum.AI00060023.getErrorCode(), AiErrorEnum.AI00060023.getErrorMsg());
                } finally {
                    //释放锁
                    distributedLockHandler.releaseLock(lock);
                }
            } else {
                logger.error("用户资源删除未能获取到锁!");
            }
        } else {
            logger.error("user id/ id 不能为空！");
        }
    }

    /**
     * 查询拥有的话术id
     *
     * @param condition
     * @return
     */
    @Override
    public List<String> queryAllByCondition(UserAiCfgBaseCondition condition) {

        List<String> ids = new ArrayList<>();

        if (RobotConstants.USER_DATA_AUTH_ME == condition.getAuthLevel() && !condition.getIsSuperAdmin()) {
            UserAiCfgInfoExample example = new UserAiCfgInfoExample();

            example.createCriteria().andUserIdEqualTo(condition.getUserId()).andStatusEqualTo(RobotConstants.USER_CFG_STATUS_S);

            List<UserAiCfgInfo> userAiCfgInfos = userAiCfgInfoMapper.selectByExample(example);

            if (CollectionUtils.isNotEmpty(userAiCfgInfos)) {
                userAiCfgInfos.forEach(obj -> {
                    if (StringUtils.isNotEmpty(obj.getTemplateIds())) {
                        ids.addAll(Lists.newArrayList(obj.getTemplateIds().split(",")));
                    }
                });
            }
        } else {
            UserAiCfgBaseInfoExample example = new UserAiCfgBaseInfoExample();
            //若超管，查询组织及以下所有
            if (condition.getIsSuperAdmin()) {
                if (StringUtils.isNotEmpty(condition.getQOrgCode())) {
                    example.createCriteria().andOrgCodeLike(condition.getQOrgCode() + "%");
                }
            } else {
                if (RobotConstants.USER_DATA_AUTH_ORGTREE == condition.getAuthLevel()) {
                    if (StringUtils.isNotEmpty(condition.getQOrgCode())) {
                        example.createCriteria().andOrgCodeLike(condition.getQOrgCode() + "%");
                    }
                } else {
                    example.createCriteria().andOrgCodeEqualTo(condition.getOrgCode());
                }
            }
            List<UserAiCfgBaseInfo> userAiCfgBaseInfos = userAiCfgBaseInfoMapper.selectByExample(example);

            if (CollectionUtils.isNotEmpty(userAiCfgBaseInfos)) {
                userAiCfgBaseInfos.forEach(obj -> {

                    if (StringUtils.isNotEmpty(obj.getTemplateIds())) {
                        ids.addAll(Lists.newArrayList(obj.getTemplateIds().split(",")));
                    }

                });
            }
        }

        Set<String> idSet = new HashSet<>();

        ids.forEach(obj -> {
            idSet.add(obj);
        });

        return Lists.newArrayList(idSet);
    }

    /**
     * 校验用户本次新增机器人总数是否有超过总控数量
     *
     * @param userId
     * @param addAiNum
     * @return
     */
    private boolean isOverAiNumCheck(UserAiCfgBaseInfo userBaseInfo, String userId, int addAiNum) {
        if (StrUtils.isNotEmpty(userId) && addAiNum > 0) {
            //查询用户总控机器人数量
            if (userBaseInfo == null) {
                userBaseInfo = this.queryUserAiCfgBaseInfoByUserId(userId);
            }
            int totalNum = userBaseInfo.getAiTotalNum();
            //查询用户现有机器人数量配置
            List<UserAiCfgInfo> existCfgList = this.queryUserAiCfgListByUserId(userId);
            int existAiNum = 0;
            if (ListUtil.isNotEmpty(existCfgList)) {
                for (UserAiCfgInfo cfgInfo : existCfgList) {
                    if (RobotConstants.USER_CFG_STATUS_S == cfgInfo.getStatus()) {
                        //正常状态
                        existAiNum = existAiNum + cfgInfo.getAiNum();
                    }
                }
            }
            if (existAiNum + addAiNum > totalNum) {
                logger.error("已存在的AI数量:{}+本次增加的AI数量:{},大于用户配置的总AI数:{}", existAiNum, addAiNum, totalNum);
                return true;
            }
        }
        return false;
    }


    /**
     * 按用户查询用户配置的模板分配的机器人数量
     * 如：用户配置的总机器人50路
     * T1 - 10
     * T1,T2 - 30
     * T1,T3 - 10
     * 那么返回的是：{T1:50,T2:30,T3:10}
     *
     * @param userId
     * @return
     */
    @Override
    public Map<String, Integer> queryTemplateAi(String userId) {
        //现在用户按模板机器人数量的配置
        Map<String, Integer> tempAiCfgMap = new HashMap<String, Integer>();
        List<UserAiCfgInfo> userAiCfgList = this.queryUserAiCfgListByUserId(userId);
        //设置每个模板总计多少路机器人
        for (UserAiCfgInfo cfg : userAiCfgList) {
            if (RobotConstants.USER_CFG_STATUS_S == cfg.getStatus()) {
                String templates = cfg.getTemplateIds();
                String[] tempateArray = templates.split(",");
                for (String templateId : tempateArray) {
                    tempAiCfgMap.put(templateId, tempAiCfgMap.get(templateId) == null ? cfg.getAiNum() : tempAiCfgMap.get(templateId) + cfg.getAiNum());
                }
            }
        }
        return tempAiCfgMap;
    }


    /**
     * 查询用户机器人配置
     *
     * @param condition
     * @return
     */
    private UserAiCfgInfoExample queryExample(UserAiCfgQueryCondition condition) {
        UserAiCfgInfoExample example = new UserAiCfgInfoExample();
        if (condition != null) {
            Criteria criteria = example.createCriteria();
            if (StrUtils.isNotEmpty(condition.getUserId())) {
                //用户id精确匹配
                criteria.andUserIdEqualTo(condition.getUserId());
            }
            if (StrUtils.isNotEmpty(condition.getTemplateId())) {
                //话术模板模糊匹配
                criteria.andTemplateIdsLike("%" + condition.getTemplateId() + "%");
            }
        }
        return example;
    }


    /**
     * 获取最新的模板和已经拆分使用的模板，对比下是否有减少了，如果有减少返回true(默认不减少)
     *
     * @param nowTemps
     * @param cfgInfoList
     */
    private boolean subTemplateChange(String nowTemps, List<UserAiCfgInfo> cfgInfoList) {
        if (StrUtils.isNotEmpty(nowTemps) && ListUtil.isNotEmpty(cfgInfoList)) {
            List<String> nowTempList = Arrays.asList(nowTemps.split(","));
            List<String> existTempList = new ArrayList<String>();
            for (UserAiCfgInfo info : cfgInfoList) {
                String templates = info.getTemplateIds();
                if (StrUtils.isNotEmpty(templates)) {
                    existTempList.addAll(Arrays.asList(templates.split(",")));
                }
            }
            //如果新的没有包含老的
            if (!nowTempList.containsAll(existTempList)) {
                return true;
            }
        }
        return false;
    }
}
