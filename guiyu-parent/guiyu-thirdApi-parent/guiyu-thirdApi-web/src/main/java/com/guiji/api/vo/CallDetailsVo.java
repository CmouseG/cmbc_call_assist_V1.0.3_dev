package com.guiji.api.vo;

import com.guiji.common.Encryptable;
import lombok.Data;

/**
 * 通过批次号查询该批次的通话列表
 */
@Data
public class CallDetailsVo implements Encryptable {

    private String batchName;

    private Integer batchCount;

    private String callList;

    private Integer totalPage;

    private Integer page;

    private Integer pageNum;


}
