package com.guiji.clm.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.guiji.clm.constant.ClmConstants;
import com.guiji.clm.dao.entity.*;
import com.guiji.clm.dao.entity.ext.SipLineQuery;
import com.guiji.clm.dao.ext.SipLineExclusiveMapperExt;
import com.guiji.clm.enm.SipLineStatusEnum;
import com.guiji.clm.exception.ClmErrorEnum;
import com.guiji.clm.exception.ClmException;
import com.guiji.clm.service.sip.*;
import com.guiji.clm.service.voip.VoipGwManager;
import com.guiji.clm.util.AreaDictUtil;
import com.guiji.clm.util.DataLocalCacheUtil;
import com.guiji.clm.vo.*;
import com.guiji.common.model.Page;
import com.guiji.component.jurisdiction.Jurisdiction;
import com.guiji.component.result.Result;
import com.guiji.user.dao.entity.SysOrganization;
import com.guiji.user.dao.entity.SysUser;
import com.guiji.utils.StrUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @version V1.0
 * @Description: 线路市场Controller
 * @Author: weiyunbo
 * @date 2019年1月31日 上午10:41:03
 */
@Slf4j
@RestController
@RequestMapping(value = "/sip")
public class LineMarketController {
    @Autowired
    SipLineApplyService sipLineApplyService;
    @Autowired
    SipLineExclusiveService sipLineExclusiveService;
    @Autowired
    SipLineInfoService sipLineInfoService;
    @Autowired
    SipLineShareService sipLineShareService;
    @Autowired
    SipLineManager sipLineManager;
    @Autowired
    DataLocalCacheUtil dataLocalCacheUtil;
    @Autowired
    SipLineExclusiveMapperExt sipLineExclusiveMapperExt;

    /**
     * 统计线路数量
     * 一般客户统计自己的
     * 管理员按企业统计
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/querySipLineExclusiveNum", method = RequestMethod.POST)
    public Result.ReturnData querySipLineExclusiveNum(
            @RequestHeader Long userId,
            @RequestHeader String orgCode,
            @RequestHeader Boolean isSuperAdmin,
            @RequestHeader Integer authLevel) {
        SipLineQuery query = new SipLineQuery();
        List<Integer> lineStatusList = new ArrayList<Integer>();
        lineStatusList.add(SipLineStatusEnum.OK.getCode());    //正常线路
        query.setLineStatusList(lineStatusList);
        if (isSuperAdmin) {
            //查询全部
        } else {
            //如果数据查询权限是本人，那么查询本人线路数据，其他查询企业线路
            if (ClmConstants.USER_DATA_AUTH_ME == authLevel) {
                query.setUserId(userId.toString());
            } else {
                query.setOrgCode(orgCode);
            }
        }
        Integer totalNum = sipLineExclusiveMapperExt.querySipLineExclusiveNum(query);
        return Result.ok(totalNum);
    }

    /**
     * 新增/修改第三方线路
     *
     * @param sipLineBaseInfo
     * @param userId
     * @return
     */
    @Jurisdiction("lineMarket_newLine,lineMarket_edit")
    @RequestMapping(value = "/saveSipLine", method = RequestMethod.POST)
    public Result.ReturnData<SipLineBaseInfo> saveSipLine(
            @RequestBody SipLineBaseInfo sipLineBaseInfo,
            @RequestHeader Long userId,
            @RequestHeader Boolean isSuperAdmin) {
        if (sipLineBaseInfo != null) {
            sipLineBaseInfo.setCrtUser(userId.toString());
            sipLineBaseInfo.setUpdateUser(userId.toString());
            sipLineBaseInfo = sipLineManager.thirdSipLineCfg(sipLineBaseInfo, isSuperAdmin);
            return Result.ok(sipLineBaseInfo);
        }
        return Result.ok();
    }

    /**
     * 删除第三方线路
     *
     * @param id
     * @return
     */
    @Jurisdiction("lineMarket_delete")
    @RequestMapping(value = "/delThirdSipLine", method = RequestMethod.POST)
    public Result.ReturnData delThirdSipLine(@RequestParam(value = "id", required = true) Integer id) {
        sipLineManager.delThirdSipLineCfg(id);
        return Result.ok();
    }


    /**
     * 生效第三方线路
     *
     * @param id
     * @return
     */
    @Jurisdiction("lineMarket_effectable")
    @RequestMapping(value = "/effectThirdSip", method = RequestMethod.POST)
    public Result.ReturnData effectThirdSip(
            @RequestParam(value = "id", required = true) Integer id,
            @RequestHeader Long userId,
            @RequestHeader Boolean isSuperAdmin) {
        sipLineManager.effectThirdSip(id, isSuperAdmin);
        return Result.ok();
    }

    /**
     * 查询第三方线路列表-分页
     *
     * @param condition
     * @return
     */
    @RequestMapping(value = "/querySipLineBaseForPageByCondition", method = RequestMethod.POST)
    public Result.ReturnData<Page<SipLineBaseInfoVO>> querySipLineBaseForPageByCondition(
            @RequestBody SipLineInfoQueryCondition condition,
            @RequestHeader Long userId,
            @RequestHeader String orgCode,
            @RequestHeader Integer authLevel,
            @RequestHeader Boolean isSuperAdmin) {

        //分页查询申请线路列表
        if (condition == null) {
            condition = new SipLineInfoQueryCondition();
        }
        if (condition.getStatusList() == null || condition.getStatusList().isEmpty()) {
            //默认查询未生效和正常数据
            condition.setStatusList(Lists.newArrayList(SipLineStatusEnum.INIT.getCode(),SipLineStatusEnum.OK.getCode()));
        }

        if (!isSuperAdmin) {
            //如果数据查询权限是本人，那么查询本人线路数据，其他查询企业线路
            condition.setCrtUser(userId.toString());
            condition.setOrgCode(orgCode);
            condition.setAuthLevel(authLevel);
        }

        Page<SipLineBaseInfo> sipLineBasePage = sipLineInfoService.querySipLineBaseForPageByCondition(condition);
        Page<SipLineBaseInfoVO> rtnPage = new Page<SipLineBaseInfoVO>(condition.getPageNo(), sipLineBasePage.getTotalRecord(), this.baseLine2VO(sipLineBasePage.getRecords()));
        return Result.ok(rtnPage);
    }


    /**
     * 查询可申请线路-分页
     *
     * @param condition
     * @return
     */
    @RequestMapping(value = "/querySipLineSharePage", method = RequestMethod.POST)
    public Result.ReturnData<Page<SipLineShareVO>> querySipLineSharePage(@RequestBody SipLineShareQueryCondition condition) {
        //分页查询申请线路列表
        if (condition == null) {
            condition = new SipLineShareQueryCondition();
        }
        if (condition.getStatusList() == null || condition.getStatusList().isEmpty()) {
            //默认查询正常
            condition.setStatusList(Lists.newArrayList(SipLineStatusEnum.OK.getCode()));
        }
        Page<SipLineShare> shareLinePage = sipLineShareService.querySipShareForPageByCondition(condition);
        Page<SipLineShareVO> rtnPage = new Page<SipLineShareVO>(condition.getPageNo(), shareLinePage.getTotalRecord(), this.shareLine2VO(shareLinePage.getRecords()));
        return Result.ok(rtnPage);
    }


    /**
     * 申请线路
     *
     * @param sipLineApply
     * @return
     */
    @Jurisdiction("comManage_application")
    @RequestMapping(value = "/sipLineApply", method = RequestMethod.POST)
    public Result.ReturnData sipLineApply(@RequestBody SipLineApply sipLineApply, @RequestHeader Long userId) {
        sipLineApply.setApplyUser(userId.toString());
        sipLineApply.setCrtUser(userId.toString());
        sipLineApply.setUpdateUser(userId.toString());
        sipLineManager.applySipLine(sipLineApply);
        return Result.ok();
    }

    /**
     * 根据共享线路ID查询真实第三方线路列表
     *
     * @param sipShareId
     * @return
     */
    @RequestMapping(value = "/queryThirdSipLineByShareId", method = RequestMethod.POST)
    public Result.ReturnData<List<SipLineBaseInfoVO>> queryThirdSipLineByShareId(@RequestParam(value = "sipShareId", required = true) Integer sipShareId) {
        SipLineInfoQueryCondition condition = new SipLineInfoQueryCondition();
        condition.setSipShareId(sipShareId);
        if (condition.getStatusList() == null || condition.getStatusList().isEmpty()) {
            //默认查询正常
            condition.setStatusList(Lists.newArrayList(SipLineStatusEnum.OK.getCode()));
        }
        List<SipLineBaseInfo> list = sipLineInfoService.querySipLineBaseListByCondition(condition);
        return Result.ok(this.baseLine2VO(list));
    }


    /**
     * 查询申请的线路列表-分页
     * 申请、审批都这个查询
     *
     * @return
     */
    @RequestMapping(value = "/queryApplyLinePage", method = RequestMethod.POST)
    public Result.ReturnData<Page<SipLineApplyVO>> queryApplyLinePage(
            @RequestBody SipLineApplyQueryCondition condition,
            @RequestHeader Long userId,
            @RequestHeader String orgCode,
            @RequestHeader Integer authLevel) {

        condition.setApplyUserId(userId.toString());
        condition.setApplyOrgCode(orgCode);
        condition.setAuthLevel(authLevel);

        Page<SipLineApply> page = sipLineApplyService.querySipLineApplyForPageByCondition(condition);
        Page<SipLineApplyVO> rtnPage = new Page<SipLineApplyVO>(page.getPageNo(), page.getTotalRecord(), this.applyLine2VO(page.getRecords()));
        return Result.ok(rtnPage);
    }


    /**
     * SIP线路审批
     *
     * @param sipLineApply
     * @return
     */
    @Jurisdiction("approveAdmin_approve")
    @RequestMapping(value = "/approveSipLine", method = RequestMethod.POST)
    public Result.ReturnData approveSipLine(@RequestBody SipLineApply sipLineApply, @RequestHeader Long userId) {
        sipLineApply.setApproveUser(userId.toString());
        sipLineApply.setUpdateUser(userId.toString());
        sipLineManager.approveSipLine(sipLineApply);
        return Result.ok();
    }

    /**
     * 查询我的线路-分页
     *
     * @param sipLineApply
     * @return
     */
    @RequestMapping(value = "/queryMyExclusiveSipLinePage", method = RequestMethod.POST)
    public Result.ReturnData<Page<SipLineExclusiveVO>> queryMyExclusiveSipLinePage(@RequestBody SipLineExclusiveQueryCondition condition, @RequestHeader Long userId) {
        if (condition == null) condition = new SipLineExclusiveQueryCondition();
        if (StrUtils.isEmpty(condition.getUserId())) {
            condition.setUserId(userId.toString());
        }
        if (condition.getStatusList() == null || condition.getStatusList().isEmpty()) {
            //默认查询正常
            condition.setStatusList(Lists.newArrayList(SipLineStatusEnum.OK.getCode()));
        }
        Page<SipLineExclusive> page = sipLineExclusiveService.querySipLineExclusiveForPageByCondition(condition);
        Page<SipLineExclusiveVO> rtnPage = new Page<SipLineExclusiveVO>(condition.getPageNo(), page.getTotalRecord(), this.exclusiveLine2VO(page.getRecords()));
        return Result.ok(rtnPage);
    }

    /**
     * 查询我的线路
     *
     * @param sipLineApply
     * @return
     */
    @RequestMapping(value = "/queryMyExclusiveSipLine", method = RequestMethod.POST)
    public Result.ReturnData<List<SipLineExclusiveVO>> queryMyExclusiveSipLine(
            @RequestBody SipLineExclusiveQueryCondition condition,
            @RequestHeader Long userId,
            @RequestHeader String orgCode) {
        if (condition == null) condition = new SipLineExclusiveQueryCondition();
        if (StrUtils.isEmpty(condition.getUserId())) {
            condition.setUserId(userId.toString());
        }
        if (condition.getStatusList() == null || condition.getStatusList().isEmpty()) {
            //默认查询正常
            condition.setStatusList(Lists.newArrayList(SipLineStatusEnum.OK.getCode()));
        }
        condition.setOrgCode(orgCode);
        List<SipLineExclusive> list = sipLineExclusiveService.querySipLineExclusiveList(condition);
        return Result.ok(this.exclusiveLine2VO(list));
    }

    /**
     * 话费分析中查询线路信息
     * qUserId为空时，则根据当前用户权限查询当前用户所能看到的线路
     * qUserId不为空时，则查询当前用户组织下，该用户组织下的该用户线路
     * @param qUserId
     * @return
     */
    @RequestMapping(value = "/queryUserExclusiveSipLine", method = RequestMethod.POST)
    public Result.ReturnData<List<SipLineExclusiveVO>> queryUserExclusiveSipLine(
            @RequestParam Long qUserId,
            @RequestHeader Long userId,
            @RequestHeader String orgCode,
            @RequestHeader Integer authLevel,
            @RequestHeader Boolean isSuperAdmin) {

        SipLineExclusiveQueryCondition condition = new SipLineExclusiveQueryCondition();

        if(qUserId == null) {
            condition.setAuthLevel(authLevel);
            condition.setOrgCode(orgCode);
            condition.setUserId(userId.toString());
        } else {
            condition.setOrgCode(orgCode);
            condition.setUserId(userId.toString());
            condition.setQUserId(qUserId.toString());
        }
        condition.setStatusList(Lists.newArrayList(SipLineStatusEnum.OK.getCode()));

        condition.setOrgCode(orgCode);
        List<SipLineExclusive> list = sipLineExclusiveService.queryUserExclusiveSipLine(condition);
        return Result.ok(this.exclusiveLine2VO(list));
    }

    @Autowired
    VoipGwManager voipGwManager;

    /**
     * 通话记录中查询线路信息，返回sip和sim的合并结果集
     * @param qOrgCode
     * @return
     */
    @RequestMapping(value = "/queryExclusiveSipLineByOrg", method = RequestMethod.POST)
    public Result.ReturnData<List<LineVo>> queryExclusiveSipLineByOrg(
            @RequestParam String qOrgCode,
            @RequestHeader Long userId,
            @RequestHeader String orgCode,
            @RequestHeader Integer authLevel,
            @RequestHeader Boolean isSuperAdmin) {

        if(StringUtils.isEmpty(qOrgCode)) {
            throw new ClmException(ClmErrorEnum.C00060001.getErrorCode(), ClmErrorEnum.C00060001.getErrorMsg());
        }

        SipLineExclusiveQueryCondition condition = new SipLineExclusiveQueryCondition();

        condition.setStatusList(Lists.newArrayList(SipLineStatusEnum.OK.getCode()));
        condition.setOrgCode(qOrgCode);

        String voipUserId = "";

        if(authLevel.equals(ClmConstants.USER_DATA_AUTH_ME)) {
            condition.setUserId(userId.toString());
            voipUserId = userId.toString();
        }

        List<LineVo> list = new ArrayList<>();

        List<SipLineExclusive> sipLineExclusives = sipLineExclusiveService.querySipLineExclusiveList(condition);

        List<VoipGwPort> voipGwPorts = voipGwManager.queryByOrgCode(qOrgCode, voipUserId);

        sipLineExclusives.forEach(obj -> {
            LineVo vo = new LineVo();

            vo.setLineId(obj.getLineId());
            vo.setLineName(obj.getLineName());

            list.add(vo);
        });

        voipGwPorts.forEach(obj -> {
            LineVo vo = new LineVo();

            vo.setLineId(obj.getLineId());
            vo.setLineName(obj.getPhoneNo());

            list.add(vo);
        });

        return Result.ok(list);

    }

    /**
     * 根据条件查询线路信息
     *
     * @param sipLineApply
     * @return
     */
    @RequestMapping(value = "/queryConditionExclusiveSipLinePage", method = RequestMethod.POST)
    public Result.ReturnData<Page<SipLineExclusiveVO>> queryConditionExclusiveSipLinePage(
            @RequestBody SipLineExclusiveQueryCondition condition,
            @RequestHeader Long userId,
            @RequestHeader String orgCode) {
        if (condition == null) condition = new SipLineExclusiveQueryCondition();
        if (condition.getStatusList() == null || condition.getStatusList().isEmpty()) {
            //默认查询正常
            condition.setStatusList(Lists.newArrayList(SipLineStatusEnum.OK.getCode()));
        }
        condition.setOrgCode(orgCode);
        Page<SipLineExclusive> page = sipLineExclusiveService.querySipLineExclusiveForPageByCondition(condition);
        Page<SipLineExclusiveVO> rtnPage = new Page<SipLineExclusiveVO>(condition.getPageNo(), page.getTotalRecord(), this.exclusiveLine2VO(page.getRecords()));
        return Result.ok(rtnPage);
    }


    /**
     * 将共享线路转VO返回前端页面
     *
     * @param list
     * @return
     */
    private List<SipLineShareVO> shareLine2VO(List<SipLineShare> list) {
        if (list != null && !list.isEmpty()) {
            List<SipLineShareVO> voList = new ArrayList<SipLineShareVO>();
            for (SipLineShare sipLineShare : list) {
                SipLineShareVO vo = new SipLineShareVO();
                BeanUtil.copyProperties(sipLineShare, vo, CopyOptions.create().setIgnoreNullValue(true).setIgnoreError(true));
                //TODO 接通率
                vo.setUnivalentStr(sipLineShare.getUnivalent() + "元/分钟");
                //线路拥有者
                vo.setLineOwner(sipLineManager.getLineOwner(dataLocalCacheUtil.queryOrgByCode(sipLineShare.getOrgCode())));
                if (StrUtils.isNotEmpty(vo.getOvertArea())) {
                    //外显归属地
                    vo.setOvertAreaName(AreaDictUtil.getAreaName(vo.getOvertArea()));
                }
                if (StrUtils.isNotEmpty(vo.getAreas())) {
                    //地区
                    vo.setAreasName(AreaDictUtil.getAreaName(vo.getAreas()));
                }
                if (StrUtils.isNotEmpty(vo.getExceptAreas())) {
                    //盲区
                    vo.setExceptAreasName(AreaDictUtil.getLowAreaNames(vo.getExceptAreas()));
                }
                voList.add(vo);
            }
            return voList;
        }
        return null;
    }


    /**
     * 将线路基本信息转VO返回前端页面
     *
     * @param list
     * @return
     */
    private List<SipLineBaseInfoVO> baseLine2VO(List<SipLineBaseInfo> list) {
        if (list != null && !list.isEmpty()) {
            List<SipLineBaseInfoVO> voList = new ArrayList<SipLineBaseInfoVO>();
            for (SipLineBaseInfo sipLineBaseInfo : list) {
                SipLineBaseInfoVO vo = new SipLineBaseInfoVO();
                BeanUtil.copyProperties(sipLineBaseInfo, vo, CopyOptions.create().setIgnoreNullValue(true).setIgnoreError(true));
                if (StrUtils.isNotEmpty(vo.getOvertArea())) {
                    //外显归属地
                    vo.setOvertAreaName(AreaDictUtil.getAreaName(vo.getOvertArea()));
                }
                if (StrUtils.isNotEmpty(vo.getAreas())) {
                    //地区
                    vo.setAreasName(AreaDictUtil.getAreaName(vo.getAreas()));
                }
                if (StrUtils.isNotEmpty(vo.getExceptAreas())) {
                    //盲区
                    vo.setExceptAreasName(AreaDictUtil.getLowAreaNames(vo.getExceptAreas()));
                }
                //企业名称
                if (StrUtils.isNotEmpty(sipLineBaseInfo.getBelongOrgCode())) {
                    SysOrganization org = dataLocalCacheUtil.queryOrgByCode(sipLineBaseInfo.getBelongOrgCode());
                    if (org != null) {
                        vo.setBelongOrgName(org.getName());
                    }
                }
                //线路拥有者(查询原线路的归属企业)
                vo.setLineOwner(sipLineManager.getLineOwner(sipLineBaseInfo));
                vo.setContractUnivalentStr(sipLineBaseInfo.getContractUnivalent() + "元/分钟");
                vo.setUnivalentStr(sipLineBaseInfo.getUnivalent() + "元/分钟");
                if (StrUtils.isEmpty(sipLineBaseInfo.getBelongOrgCode())) {
                    //没有归属企业 - 共享线路
                    if (SipLineStatusEnum.INIT.getCode() == sipLineBaseInfo.getLineStatus()) {
                        //共享线路没有生效前，可以编辑
                        vo.setEditable(true);
                        vo.setEffectable(true); //需要显示生效按钮
                    } else {
                        vo.setEditable(false);
                        vo.setEffectable(false);
                    }
                } else {
                    //自备线路
                    vo.setEditable(true);
                    vo.setEffectable(false);
                }
                voList.add(vo);
            }
            return voList;
        }
        return null;
    }

    /**
     * 将线路基本信息转VO返回前端页面
     *
     * @param list
     * @return
     */
    private List<SipLineExclusiveVO> exclusiveLine2VO(List<SipLineExclusive> list) {
        if (list != null && !list.isEmpty()) {
            List<SipLineExclusiveVO> voList = new ArrayList<SipLineExclusiveVO>();
            for (SipLineExclusive sipLineExclusive : list) {
                SipLineExclusiveVO vo = new SipLineExclusiveVO();
                BeanUtil.copyProperties(sipLineExclusive, vo, CopyOptions.create().setIgnoreNullValue(true).setIgnoreError(true));
                if (StrUtils.isNotEmpty(vo.getOvertArea())) {
                    //外显归属地
                    vo.setOvertAreaName(AreaDictUtil.getAreaName(vo.getOvertArea()));
                }
                if (StrUtils.isNotEmpty(vo.getAreas())) {
                    //地区
                    vo.setAreasName(AreaDictUtil.getAreaName(vo.getAreas()));
                }
                if (StrUtils.isNotEmpty(vo.getExceptAreas())) {
                    //盲区
                    vo.setExceptAreasName(AreaDictUtil.getLowAreaNames(vo.getExceptAreas()));
                }
                //线路拥有者(查询原线路的归属企业)
                Integer sipLineId = sipLineExclusive.getSipLineId();
                SipLineBaseInfo sipLineBaseInfo = sipLineInfoService.queryById(sipLineId);
                vo.setLineOwner(sipLineManager.getLineOwner(sipLineBaseInfo));
                vo.setUnivalentStr(sipLineExclusive.getUnivalent() + "元/分钟");
                voList.add(vo);
            }
            return voList;
        }
        return null;
    }


    /**
     * 将线路申请信息转VO返回前端页面
     *
     * @param list
     * @return
     */
    private List<SipLineApplyVO> applyLine2VO(List<SipLineApply> list) {
        if (list != null && !list.isEmpty()) {
            List<SipLineApplyVO> voList = new ArrayList<SipLineApplyVO>();
            for (SipLineApply sipLineApply : list) {
                SipLineApplyVO vo = new SipLineApplyVO();
                BeanUtil.copyProperties(sipLineApply, vo, CopyOptions.create().setIgnoreNullValue(true).setIgnoreError(true));
                if (StrUtils.isNotEmpty(vo.getApplyUser())) {
                    SysUser sysUser = dataLocalCacheUtil.queryUser(vo.getApplyUser());
                    if (sysUser != null) {
                        vo.setApplyUserName(sysUser.getUsername());
                    }
                }
                if (StrUtils.isNotEmpty(vo.getApplyOrgCode())) {
                    SysOrganization org = dataLocalCacheUtil.queryOrgByCode(vo.getApplyOrgCode());
                    if (org != null) {
                        vo.setApplyOrgName(org.getName());
                    }
                }
                //设置共享线路部分字段
                Integer agentLineId = sipLineApply.getAgentLineId();    //共享代理线路ID
                if (agentLineId != null) {
                    SipLineShare sipLineShare = sipLineShareService.queryById(agentLineId);
                    if (StrUtils.isNotEmpty(sipLineShare.getOvertArea())) {
                        //外显归属地
                        vo.setOvertAreaName(AreaDictUtil.getAreaName(sipLineShare.getOvertArea()));
                    }
                    if (StrUtils.isNotEmpty(sipLineShare.getAreas())) {
                        //地区
                        vo.setAreasName(AreaDictUtil.getAreaName(sipLineShare.getAreas()));
                    }
                    if (StrUtils.isNotEmpty(sipLineShare.getExceptAreas())) {
                        //盲区
                        vo.setExceptAreasName(AreaDictUtil.getLowAreaNames(sipLineShare.getExceptAreas()));
                    }
                }
                //线路拥有者
                vo.setLineOwner(sipLineManager.getLineOwner(dataLocalCacheUtil.queryOrgByCode(sipLineApply.getOrgCode())));
                vo.setUnivalentStr(sipLineApply.getUnivalent() + "元/分钟");
                voList.add(vo);
            }
            return voList;
        }
        return null;
    }


}
