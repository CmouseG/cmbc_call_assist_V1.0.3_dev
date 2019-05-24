package com.guiji.dispatch.bean;

import com.guiji.dispatch.dao.entity.DispatchPlanBatch;
import com.guiji.dispatch.model.DispatchPlanForApiRo;
import com.guiji.robot.model.CustTemplateVo;
import com.guiji.user.dao.entity.SysOrganization;
import com.guiji.user.dao.entity.SysUser;
import lombok.Data;

/**
 * @Classname AddPlanAsyncEntity
 * @Description TODO
 * @Date 2019/5/24 15:53
 * @Created by qinghua
 */
@Data
public class AddPlanAsyncEntity {

    private DispatchPlanForApiRo ro;

    private DispatchPlanBatch batch;

    private SysUser sysUser;

    private CustTemplateVo custTemplateVo;

    private SysOrganization orgInfo;

    private Integer lineType;
}
