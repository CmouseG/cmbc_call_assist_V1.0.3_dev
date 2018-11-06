package com.guiji.robot.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 机器人
 */
@ApiModel(value="RobotVO对象",description="机器人")
public class RobotVO implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value="ID",required=true)
    private String id;
    @ApiModelProperty(value="机器人名称",required=true)
    private String robotName;
    @ApiModelProperty(value="机器人代号",required=true)
    private String robotKey;
    @ApiModelProperty(value="是否需要TTS")
    private Integer isTts;
    @ApiModelProperty(value="是否回访类型")
    private Integer isVisit;
    @ApiModelProperty(value="机器人描述")
    private String robotDesc;
    @ApiModelProperty(value="机器人状态")
    private Integer isShow;
}
