package com.guiji.wxapi.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Auther: 黎阳
 * @Date: 2018/10/29 0029 19:02
 * @Description:
 */
@Data
@ApiModel(description= "通话记录")
public class CallOutPlanVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private CallData callData;
    private List<CallOutDetailVO> voiceInfo;

}
