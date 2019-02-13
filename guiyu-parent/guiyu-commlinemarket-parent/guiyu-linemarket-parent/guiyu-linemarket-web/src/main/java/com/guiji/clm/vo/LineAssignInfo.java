package com.guiji.clm.vo;

import lombok.Data;

/** 
* @Description: 线路分配信息 
* @Author: weiyunbo
* @date 2019年1月31日 下午9:17:23 
* @version V1.0  
*/
@Data
public class LineAssignInfo {
	private Integer id;
    private Boolean isAlloted;
    private String lineName;
}
