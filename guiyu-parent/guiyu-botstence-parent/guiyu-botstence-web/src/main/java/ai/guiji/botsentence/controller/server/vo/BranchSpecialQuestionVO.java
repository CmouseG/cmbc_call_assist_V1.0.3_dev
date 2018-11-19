package ai.guiji.botsentence.controller.server.vo;

/**
 * 一般问题的 special
 * @Description:
 * @author liyang  
 * @date 2018年8月22日  
 *
 */
public class BranchSpecialQuestionVO extends BranchNegativeVO{

	private String user_ask;
    
	public String getUser_ask() {
		return user_ask;
	}
	public void setUser_ask(String user_ask) {
		this.user_ask = user_ask;
	}
	
	
}
