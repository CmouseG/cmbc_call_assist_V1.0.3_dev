package com.guiji.api.vo;

import com.guiji.common.Encryptable;
import lombok.Data;

/**
 * @Classname AddPlanRes
 * @Description TODO
 * @Date 2019/5/10 18:34
 * @Created by qinghua
 */
@Data
public class AddPlanRes implements Encryptable {

    /**
     * 错误的手机号信息，或者重复的信息
     */
    private String failMobileInfo;

    /**
     * 正确的数量
     */
    private Integer rightCount;

    /**
     * 错误的数量
     */
    private Integer failCount;

}
