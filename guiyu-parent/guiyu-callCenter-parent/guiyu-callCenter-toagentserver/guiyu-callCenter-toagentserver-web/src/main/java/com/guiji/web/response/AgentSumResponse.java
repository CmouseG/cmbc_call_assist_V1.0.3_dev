package com.guiji.web.response;

import lombok.Data;

/**
 * @Auther: 魏驰
 * @Date: 2019/1/22 09:56
 * @Project：guiyu-parent
 * @Description:
 */
@Data
public class AgentSumResponse {
    /**
     * 在线座席
     */
    private Integer onlineCount =0;

    /**
     * 所有座席
     */
    private Integer totalCount = 0;
}
