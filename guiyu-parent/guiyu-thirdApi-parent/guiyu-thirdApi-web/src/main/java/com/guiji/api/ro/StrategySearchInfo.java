package com.guiji.api.ro;

import com.guiji.common.GenericRo;
import lombok.Data;

/**
 * 查询策略信息
 */
@Data
public class StrategySearchInfo extends GenericRo {

    private String ruleName;

    private Long userId;

}
