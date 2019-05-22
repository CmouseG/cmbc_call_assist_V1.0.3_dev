package com.guiji.api.ro;

import com.guiji.common.GenericRo;
import lombok.Data;

/**
 * 添加策略ro
 */
@Data
public class CallStrategyRo extends GenericRo {

    //规则名，用户下唯一
    private String ruleName;

    //话术id
    private String botenceId;

    //话术名称
    private String botenceName;

    //使用线路的id
    private String lineId;

    //是否清除
    private Integer isClear;

    //呼叫时段
    private String callHour;

    //发送短信
    private Integer isSms;

    //发送短信的标签
    private String smsLabel;

    //线路类型 1-SIP，2-网关
    private Integer lineType;

    //groupId
    private Integer groupId;

    //时间策略
    private String timeStrategy;

}
