package com.guiji.api.vo;

import com.guiji.common.Encryptable;
import lombok.Data;

/**
 * 获取拨打任务详情
 */
@Data
public class PlanExecResultVo implements Encryptable {

    //批次名称
    private String batchName;

    //系统接收到的号码数量
    private Integer acceptCount;

    //处理成功的号码数量
    private Integer successCount;

    //处理失败的号码数量
    private Integer failCount;

    //处理失败的号码详情
    private String failMobilelist;
}
