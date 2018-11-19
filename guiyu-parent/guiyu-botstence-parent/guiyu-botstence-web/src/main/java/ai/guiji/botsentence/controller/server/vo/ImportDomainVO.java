package ai.guiji.botsentence.controller.server.vo;

import java.util.List;
import java.util.Map;

/**
 * domain
 * @Description:
 * @author liyang  
 * @date 2018年8月22日  
 *
 */
public class ImportDomainVO {

	private String com_domain;
	private String enter;
	private String failed_enter;
	private String ignore_but_domains;
	private String branch;
	
	private int limit;
	private boolean ignore_user_sentence;
	private boolean ignore_but_negative;
	private List<String> ignore;
	
	public String getCom_domain() {
		return com_domain;
	}
	public void setCom_domain(String com_domain) {
		this.com_domain = com_domain;
	}
	public String getEnter() {
		return enter;
	}
	public void setEnter(String enter) {
		this.enter = enter;
	}
	public String getFailed_enter() {
		return failed_enter;
	}
	public void setFailed_enter(String failed_enter) {
		this.failed_enter = failed_enter;
	}
	public String getIgnore_but_domains() {
		return ignore_but_domains;
	}
	public void setIgnore_but_domains(String ignore_but_domains) {
		this.ignore_but_domains = ignore_but_domains;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public boolean isIgnore_user_sentence() {
		return ignore_user_sentence;
	}
	public void setIgnore_user_sentence(boolean ignore_user_sentence) {
		this.ignore_user_sentence = ignore_user_sentence;
	}
	public boolean isIgnore_but_negative() {
		return ignore_but_negative;
	}
	public void setIgnore_but_negative(boolean ignore_but_negative) {
		this.ignore_but_negative = ignore_but_negative;
	}
	public List<String> getIgnore() {
		return ignore;
	}
	public void setIgnore(List<String> ignore) {
		this.ignore = ignore;
	}
	
	
	
	
}
