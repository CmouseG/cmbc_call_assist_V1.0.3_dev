package com.guiji.api.vo;

import com.guiji.common.Encryptable;
import lombok.Data;

/**
 * 通过批次号查询该批次的拨打情况
 * 结果信息
 */
@Data
public class BatchPlanCallSumary implements Encryptable {

    //批次名称
    private String batchName;

    //系统接收到的号码数量
    private Integer acceptCount;

    //呼叫完成的号码数量
    private Integer endCount;

    //计划中的号码数量
    private Integer planCount;

}
