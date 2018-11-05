package com.guiji.robot.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 用户和机器人绑定关系
 */
@ApiModel(value="UserRobotRelationVO对象",description="用户和机器人绑定关系")
public class UserRobotRelationVO implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value="ID",required=true)
    private String id;
    @ApiModelProperty(value="用户ID",required=true)
    private String userId;
    @ApiModelProperty(value="用户名")
    private String userName;
    @ApiModelProperty(value="机器人名称")
    private String robotName;
    @ApiModelProperty(value="机器人代号",required=true)
    private String robotKey;
    @ApiModelProperty(value="订购时间")
    private String gmt_create;
    @ApiModelProperty(value="更新时间")
    private String gmt_modified;
    @ApiModelProperty(value="状态")
    private Integer status;
}
