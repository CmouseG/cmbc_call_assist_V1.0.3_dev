package com.guiji.robot.model;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/** 
* @ClassName: UserAiCfgInfoVO 
* @Description: 用户机器人配置VO
* @date 2018年12月3日 下午8:19:26 
* @version V1.0  
*/
@Data
@ApiModel(value="UserAiCfgDetailVO对象",description="用户机器人配置详情")
public class UserAiCfgDetailVO {
	@ApiModelProperty(value="编号",required=true)
	private Integer id;
	@ApiModelProperty(value="用户编号",required=true)
    private String userId;
	@ApiModelProperty(value="用户名称")
  	private String userName;
    @ApiModelProperty(value="机器人数量",required=true)
    private Integer aiNum;
    @ApiModelProperty(value="模板编号,多模板逗号分隔",required=true)
    private String templateIds;
    @ApiModelProperty(value="模板名称,多模板逗号分隔",required=true)
	private String templateNames;
    @ApiModelProperty(value="开户日期")
    private String openDate;
    @ApiModelProperty(value="到期日")
    private String invalidDate;
    @ApiModelProperty(value="状态(S-正常;V-失效)",required=true)
    private String status;
    @ApiModelProperty(value="创建时间")
    private Date crtTime;
    @ApiModelProperty(value="创建人")
    private String crtUser;
    @ApiModelProperty(value="创建人名称")
  	private String crtUserName;
    @ApiModelProperty(value="更新时间")
    private Date updateTime;
    @ApiModelProperty(value="更新人")
    private String updateUser;
  	@ApiModelProperty(value="更新人名称")
  	private String updateUserName;
}
