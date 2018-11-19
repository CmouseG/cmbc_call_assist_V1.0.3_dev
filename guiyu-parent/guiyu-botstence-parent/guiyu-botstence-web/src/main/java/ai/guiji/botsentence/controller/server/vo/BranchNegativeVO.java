package ai.guiji.botsentence.controller.server.vo;

import java.util.List;

/**
 * negative 和 special,不包括一般问题的special
 * 
 * @Description:
 * @author liyang
 * @date 2018年8月22日
 *
 */
public class BranchNegativeVO extends BranchPositiveVO {

	private String end;
	private List<String> response;

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public List<String> getResponse() {
		return response;
	}

	public void setResponse(List<String> response) {
		this.response = response;
	}


}
