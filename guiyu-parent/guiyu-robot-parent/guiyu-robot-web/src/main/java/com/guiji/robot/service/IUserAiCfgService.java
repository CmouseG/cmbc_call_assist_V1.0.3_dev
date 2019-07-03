package com.guiji.robot.service;

import com.guiji.common.model.Page;
import com.guiji.robot.dao.entity.UserAiCfgBaseInfo;
import com.guiji.robot.dao.entity.UserAiCfgInfo;
import com.guiji.robot.service.vo.UserAiCfgBaseCondition;
import com.guiji.robot.service.vo.UserAiCfgQueryCondition;

import java.util.List;
import java.util.Map;

/**
 * @version V1.0
 * @ClassName: IUserAiCfgService
 * @Description: 用户-机器人配置服务
 * @date 2018年11月15日 下午8:24:20
 */
public interface IUserAiCfgService {

    /**
     * 机器人数量总控配置
     *
     * @param userAiCfgBaseInfo
     * @return
     */
    public UserAiCfgBaseInfo putupUserCfgBase(UserAiCfgBaseInfo userAiCfgBaseInfo);


    /**
     * 查询用户机器人配置基本信息
     *
     * @param userId
     * @return
     */
    public UserAiCfgBaseInfo queryUserAiCfgBaseInfoByUserId(String userId);


    /**
     * 查询机构号查询机器人配置基本信息
     *
     * @param orgCode
     * @return
     */
    public List<UserAiCfgBaseInfo> queryUserAiCfgBaseInfoByOrgCode(String orgCode);

    /**
     * 查询用户所属企业下可用的机器人数量
     *
     * @param userId
     * @return
     */
    public int queryOrgAvailableAiNum(String userId);

    /**
     * 分页查询 用户机器人配置基本信息
     *
     * @param pageNo
     * @param pageSize
     * @param userId
     * @return
     */
    public Page<UserAiCfgBaseInfo> queryUserAiCfgBaseInfoFroPageByUserId(UserAiCfgBaseCondition condition);

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
    public Map<String, Integer> queryTemplateAi(String userId);


    /**
     * 根据用户编号查询用户-机器人配置信息列表
     *
     * @return
     */
    List<UserAiCfgInfo> queryUserAiCfgListByUserId(String userId);

    /**
     * 权限下查询用户所能查到的模板信息
     */
    List<UserAiCfgBaseInfo> queryUserAiCfgInfoByCondition(UserAiCfgBaseCondition condition);


    /**
     * 分页查询 用户机器人配置详情
     *
     * @param pageNo
     * @param pageSize
     * @param condition
     * @return
     */
    public Page<UserAiCfgInfo> queryCustAccountForPage(int pageNo, int pageSize, UserAiCfgQueryCondition condition);

    /**
     * 根据用户编号查询用户符合话术模板的配置列表
     *
     * @param userId
     * @param templateId
     * @return
     */
    public List<UserAiCfgInfo> queryUserAiCfgListByUserId(String userId, String templateId);


    /**
     * 查询用户下可以使用某个话术的机器人列表
     *
     * @param userId
     * @param templateId
     * @return
     */
    List<UserAiCfgInfo> queryUserAiCfgListByUserIdAndTemplate(String userId, String templateId);


    /**
     * 用户资源变更服务
     *
     * @param userAiCfgInfo
     * @return
     */
    UserAiCfgInfo userAiCfgChange(UserAiCfgBaseInfo userBaseInfo, UserAiCfgInfo userAiCfgInfo);

    /**
     * 删除用户一条资源配置信息
     *
     * @param id
     */
    void delUserCfg(String userId, int id);

    /**
     * 查询拥有的模板ids
     * @param condition
     * @return
     */
    List<String> queryAllByCondition(UserAiCfgBaseCondition condition);
}
