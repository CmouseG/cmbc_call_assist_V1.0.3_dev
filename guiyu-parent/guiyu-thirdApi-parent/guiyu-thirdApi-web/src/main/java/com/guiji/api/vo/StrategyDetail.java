package com.guiji.api.vo;

import com.guiji.common.Encryptable;
import lombok.Data;

/**
 * 策略详情
 */
@Data
public class StrategyDetail implements Encryptable {

    //规则名，用户下唯一
    private String ruleName;

    //话术id
    private String botenceId;

    //话术名称
    private String botenceName;

    //使用线路的id
    private String lineId;

    //是否清除
    private String isClear;

    //呼叫时段
    private String callHour;

    //发送短信
    private Integer isSms;

    //发送短信的标签
    private String smsLabel;

    //创建时间
    private String createTime;

}
