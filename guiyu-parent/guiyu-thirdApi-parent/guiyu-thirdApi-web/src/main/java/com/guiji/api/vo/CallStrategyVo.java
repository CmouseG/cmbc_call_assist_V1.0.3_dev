package com.guiji.api.vo;

import com.guiji.common.Encryptable;
import lombok.Data;

/**
 * 拨打策略详情
 */
@Data
public class CallStrategyVo implements Encryptable {

    //规则名，用户下唯一
    private String ruleName;

    //话术名称
    private String botenceName;

    //使用线路的id
    private String lineId;

    //是否清除
    private String isClear;

    //呼叫日期
    private String callDate;

    //呼叫时段
    private String callHour;

    //
    private String isSms;

    private String smsLabel;

}
