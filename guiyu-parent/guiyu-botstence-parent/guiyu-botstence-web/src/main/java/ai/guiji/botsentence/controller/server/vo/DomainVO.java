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
public class DomainVO {

	private String com_domain;
	private List<String> enter;
	private List<String> failed_enter;
	private List<String> ignore_but_domains;
	private List<Map> branch;
	
	private int limit;
	
	public String getCom_domain() {
		return com_domain;
	}
	public void setCom_domain(String com_domain) {
		this.com_domain = com_domain;
	}
	public List<String> getEnter() {
		return enter;
	}
	public void setEnter(List<String> enter) {
		this.enter = enter;
	}
	public List<String> getFailed_enter() {
		return failed_enter;
	}
	public void setFailed_enter(List<String> failed_enter) {
		this.failed_enter = failed_enter;
	}
	public List<String> getIgnore_but_domains() {
		return ignore_but_domains;
	}
	public void setIgnore_but_domains(List<String> ignore_but_domains) {
		this.ignore_but_domains = ignore_but_domains;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	
	public List<Map> getBranch() {
		return branch;
	}
	public void setBranch(List<Map> branch) {
		this.branch = branch;
	}
	
	
}
