package com.guiji.api.vo;

import com.guiji.common.Encryptable;
import lombok.Data;

/**
 * 获取用户话术
 */
@Data
public class BotenceVo implements Encryptable {

    private String botenceList;

}
