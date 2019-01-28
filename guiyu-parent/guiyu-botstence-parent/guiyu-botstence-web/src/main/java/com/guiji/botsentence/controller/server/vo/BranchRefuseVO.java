package com.guiji.botsentence.controller.server.vo;

/**
 * refuse 挽回branch
 * @Description:
 * @author liyang  
 * @date 2018年8月242日  
 *
 */
public class BranchRefuseVO extends BranchNegativeVO{

	private boolean is_special_limit_free;

	public boolean isIs_special_limit_free() {
		return is_special_limit_free;
	}

	public void setIs_special_limit_free(boolean is_special_limit_free) {
		this.is_special_limit_free = is_special_limit_free;
	}
    
	
}
