package com.guiji.clm.controller;

import com.guiji.clm.dao.SipLineExclusiveMapper;
import com.guiji.clm.dao.entity.SipLineExclusive;
import com.guiji.clm.enm.SipLineStatusEnum;
import com.guiji.clm.exception.ClmErrorEnum;
import com.guiji.clm.exception.ClmException;
import com.guiji.clm.service.sip.SipLineExclusiveService;
import com.guiji.clm.util.DataLocalCacheUtil;
import com.guiji.clm.vo.LineAssignInfo;
import com.guiji.clm.vo.SipLineExclusiveQueryCondition;
import com.guiji.component.result.Result;
import com.guiji.user.dao.entity.SysUser;
import com.guiji.utils.StrUtils;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @version V1.0
 * @Description: 线路市场-线路服务(主要替代原在呼叫中心提供的线路服务)
 * @Author: weiyunbo
 * @date 2019年1月31日 下午7:43:24
 */
@Slf4j
@RestController
@RequestMapping(value = "/line")
public class LineController {
    @Autowired
    SipLineExclusiveMapper sipLineExclusiveMapper;
    @Autowired
    SipLineExclusiveService sipLineExclusiveService;
    @Autowired
    DataLocalCacheUtil dataLocalCacheUtil;

    /**
     * 用户资源分配中查询用户分配的线路
     *
     * @param customerId
     * @return
     */
    @RequestMapping(value = "/getLineInfos4Allot")
    public Result.ReturnData<List<LineAssignInfo>> getLineInfos4Allot(@RequestParam(value = "customerId", required = true) String customerId) {
        SysUser sysUser = dataLocalCacheUtil.queryUser(customerId);
        if (sysUser != null) {
            SipLineExclusiveQueryCondition condition = new SipLineExclusiveQueryCondition();
            condition.setOrgCode(sysUser.getOrgCode());    //查询本企业下线路
            condition.setStatusList(Lists.newArrayList(SipLineStatusEnum.OK.getCode())); //正常状态
            List<SipLineExclusive> list = sipLineExclusiveService.querySipLineExclusiveList(condition);
            if (list != null && !list.isEmpty()) {
                List<LineAssignInfo> rtnList = new ArrayList<LineAssignInfo>();
                for (SipLineExclusive line : list) {
                    LineAssignInfo vo = new LineAssignInfo();
                    vo.setId(line.getId());
                    vo.setLineName(line.getLineName());
                    if (StrUtils.isEmpty(line.getBelongUser())) {
                        //未分配到人
                        vo.setIsAlloted(false);
                    } else if (line.getBelongUser().equals(customerId)) {
                        //已分配给我
                        vo.setIsAlloted(true);
                    } else {
                        //不是分配给我的不显示
                        continue;
                    }
                    rtnList.add(vo);
                }
                return Result.ok(rtnList);
            }
        }
        return Result.ok();
    }

    /**
     * 在用户资源处给用户重新分配线路
     *
     * @param reqMap
     * @param userId
     * @param isSuperAdmin
     * @param orgCode
     * @return
     */
    @PostMapping(value = "/allotLineInfo")
    public Result.ReturnData allotLineInfo(
            @RequestBody Map<String, String> reqMap,
            @RequestHeader Long userId,
            @RequestHeader Boolean isSuperAdmin,
            @RequestHeader String orgCode) {
        String customerId = reqMap.get("customerId");
        String lineIds = reqMap.get("lineIds");
        if (StrUtils.isEmpty(customerId)) {
            //非空校验
            throw new ClmException(ClmErrorEnum.C00060001.getErrorCode(), ClmErrorEnum.C00060001.getErrorMsg());
        }
        Integer[] ids = null;
        if (StrUtils.isNotEmpty(lineIds)) {
            String[] arr = lineIds.split(",");
            ids = new Integer[arr.length];
            for (int i = 0; i < arr.length; i++) {
                ids[i] = Integer.valueOf(arr[i]);
            }
        }
        sipLineExclusiveService.sipExclusiveLineAssign(customerId, userId.toString(), ids);
        return Result.ok();
    }

}
