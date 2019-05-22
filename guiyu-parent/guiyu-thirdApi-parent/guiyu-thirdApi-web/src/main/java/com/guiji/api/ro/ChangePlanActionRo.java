package com.guiji.api.ro;

import com.guiji.common.GenericRo;
import lombok.Data;

/**
 * 控制批次任务的ro
 */
@Data
public class ChangePlanActionRo extends GenericRo {

    private Long userId;

    private String batchName;

}
