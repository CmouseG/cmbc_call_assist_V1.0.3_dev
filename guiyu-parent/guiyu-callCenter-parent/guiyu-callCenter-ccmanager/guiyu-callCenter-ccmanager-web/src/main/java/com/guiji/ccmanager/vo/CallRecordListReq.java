package com.guiji.ccmanager.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * author:liyang
 * Date:2019/2/26 9:51
 * Description:
 */
@Data
public class CallRecordListReq {
    @ApiModelProperty(value = "开始时间,yyyy-MM-dd HH:mm:ss格式")
    String startDate;
    @ApiModelProperty(value = "结束时间,yyyy-MM-dd HH:mm:ss格式")
    String endDate;
    @ApiModelProperty(value = "每页大小")
    String pageSize;
    @ApiModelProperty(value = "第几页")
    String pageNo;
    @ApiModelProperty(value = "电话号码")
    String phoneNum;
    @ApiModelProperty(value = "拨打时长，最小值")
    String durationMin;
    @ApiModelProperty(value = "拨打时长，最大值")
    String durationMax;
    @ApiModelProperty(value = "接听时长，最小值")
    String billSecMin;
    @ApiModelProperty(value = "接听时长，最大值")
    String billSecMax;
    @ApiModelProperty(value = "意向标签，以逗号分隔")
    String accurateIntent;
    @ApiModelProperty(value = "直接传名称,以逗号分隔")
    String freason;
    @ApiModelProperty(value = "通话ID")
    String callId;
    @ApiModelProperty(value = "话术模板id")
    String tempId;
    @ApiModelProperty(value = "是否已读,0表示未读，1表示已读")
    String isRead;
    @ApiModelProperty(value = "客户id")
    String customerId;
    @ApiModelProperty(value = "是否已介入，0:未介入,1:已介入,不传或其他值则是全部")
    String intervened;
    @ApiModelProperty(value = "从第几条记录开始，默认从第一条开始")
    String startCount;
    @ApiModelProperty(value = "到多少条")
    String endCount;
    @ApiModelProperty(value = "批次号")
    Integer batchId;
    @ApiModelProperty(value = "线路ID")
    Integer lineId;

    @ApiModelProperty(value = "批次号")
    private List<Integer> orgIdList;

    @ApiModelProperty(value = "全选导出,如果为true，将忽略startCount和endCount")
    Boolean checkAll;
    @ApiModelProperty(value = "取消勾选的id列表")
    List<String> excludeList;

    @ApiModelProperty(value = "没有全选的时候，传递的id列表")
    List<String> includeList;

    @ApiModelProperty(value = "用户名称")
    String answerUser;
    @ApiModelProperty(value = "用户所属企业单位")
    String enterprise;
}
