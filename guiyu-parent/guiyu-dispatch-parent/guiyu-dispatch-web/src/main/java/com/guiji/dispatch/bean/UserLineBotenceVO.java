package com.guiji.dispatch.bean;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserLineBotenceVO  implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer userId;

//    private Integer lineId;

    private String botenceName;

    private Integer maxRobotCount;


}
