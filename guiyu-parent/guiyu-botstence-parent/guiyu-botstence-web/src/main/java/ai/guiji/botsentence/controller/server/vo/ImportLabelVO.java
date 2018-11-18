package ai.guiji.botsentence.controller.server.vo;

/**
 * Label
 * @Description:
 * @author liyang  
 * @date 2018年8月22日  
 *
 */
public class ImportLabelVO {

	private String keywords;
	private int conversation_count;
	private int special_count;
	private int used_time_s;
	private int deny_count;
	private int busy_count;
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public int getConversation_count() {
		return conversation_count;
	}
	public void setConversation_count(int conversation_count) {
		this.conversation_count = conversation_count;
	}
	public int getSpecial_count() {
		return special_count;
	}
	public void setSpecial_count(int special_count) {
		this.special_count = special_count;
	}
	public int getUsed_time_s() {
		return used_time_s;
	}
	public void setUsed_time_s(int used_time_s) {
		this.used_time_s = used_time_s;
	}
	public int getDeny_count() {
		return deny_count;
	}
	public void setDeny_count(int deny_count) {
		this.deny_count = deny_count;
	}
	public int getBusy_count() {
		return busy_count;
	}
	public void setBusy_count(int busy_count) {
		this.busy_count = busy_count;
	}

}
