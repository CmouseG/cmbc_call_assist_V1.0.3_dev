package com.guiji.api.vo;

import com.guiji.common.Encryptable;
import lombok.Data;

/**
 * 通过批次号查询该批次的号码列表
 */
@Data
public class BatchPlanListVo implements Encryptable {

    //总页数
    private Integer totalPage;

    //当前页
    private Integer page;

    //每页条数
    private Integer pageNum;

    //批次名称
    private String batchName;

    //号码列表
    private String mobileList;

    //系统成功接收到的号码的数量
    private Integer batchCount;
}
