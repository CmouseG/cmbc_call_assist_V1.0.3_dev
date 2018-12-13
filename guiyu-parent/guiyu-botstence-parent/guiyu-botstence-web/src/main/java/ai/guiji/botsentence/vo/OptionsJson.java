package ai.guiji.botsentence.vo;

public class OptionsJson {

	private boolean check_sim;
	private boolean cur_domain_prior;
	private boolean use_endfiles_list;
	private boolean use_not_match_logic;
	private String not_match_solution;
	private String trade;
	private String tempname;
	private String dianame;
	private String des;
	public boolean isCheck_sim() {
		return check_sim;
	}
	public void setCheck_sim(boolean check_sim) {
		this.check_sim = check_sim;
	}
	public boolean isCur_domain_prior() {
		return cur_domain_prior;
	}
	public void setCur_domain_prior(boolean cur_domain_prior) {
		this.cur_domain_prior = cur_domain_prior;
	}
	public boolean isUse_endfiles_list() {
		return use_endfiles_list;
	}
	public void setUse_endfiles_list(boolean use_endfiles_list) {
		this.use_endfiles_list = use_endfiles_list;
	}
	public boolean isUse_not_match_logic() {
		return use_not_match_logic;
	}
	public void setUse_not_match_logic(boolean use_not_match_logic) {
		this.use_not_match_logic = use_not_match_logic;
	}
	public String getNot_match_solution() {
		return not_match_solution;
	}
	public void setNot_match_solution(String not_match_solution) {
		this.not_match_solution = not_match_solution;
	}
	public String getTrade() {
		return trade;
	}
	public void setTrade(String trade) {
		this.trade = trade;
	}
	public String getTempname() {
		return tempname;
	}
	public void setTempname(String tempname) {
		this.tempname = tempname;
	}
	public String getDianame() {
		return dianame;
	}
	public void setDianame(String dianame) {
		this.dianame = dianame;
	}
	public String getDes() {
		return des;
	}
	public void setDes(String des) {
		this.des = des;
	}
}
