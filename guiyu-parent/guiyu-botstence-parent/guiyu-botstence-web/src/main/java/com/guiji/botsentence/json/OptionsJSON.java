package com.guiji.botsentence.json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.guiji.botsentence.util.BotSentenceUtil;

/**
 * @author 张朋 
 * @description 话术模板options.json对象
 *
 */
//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class OptionsJSON {

	//检查相似词
	private boolean check_sim;
	
	//优先匹配当前域
	private boolean cur_domain_prior;
	
	//使用未匹配逻辑
	private boolean use_not_match_logic;
	
	//未匹配解决方案:方案2
	private String not_match_solution;
	
	//使用结束列表:true(以结束开头的域会自动挂断)
	private boolean use_endfiles_list;
	
	//模板类型
	private String trade;
	
	//模板编号
	private String tempname;
	private String des;
	
	//模板名称
	private String dianame;
	
	//不打断配置，多个域用空格分割，表示是不可打断的
	private String non_interruptable;
	
	//（静音超时时间）即多少秒没说话会产生一次静音超时事件
	private int silence_wait_secs;
	
	//（允许静音超时次数超过这个次数就该挂电话了）
	private int silence_wait_time;
	
	//true代表此模板静音走空规则
	private boolean silence_as_empty;
	
	//限制special被问的次数
	private int special_limit;
	
	//表示哪些域里的回答有引导
	private String grub_domain;
	
	//表示某个域里一般问题的回答根据询问的计数加"尾巴"的"位置",也就是哪次回答需要加"尾巴"，比如"开场白":[1,3,5]表示在开场白里，对于询问到的一般问题，第1、3、5次询问到的问题需要加"尾巴"，而处于2、4、6、7、8次问到的一般问题不予加"尾巴"处理。*
	private Map<String, List<Integer>> flow_grub_tail_pos;
	
	//用户自定义匹配优先级,这个选项会和模板中各个域里面的match_order配合使用,match_order中定义的是匹配顺序如    
	private boolean user_define_match_order;
	
	//需要配置的优先级的域增加match_order字段，且受special_first字段影响
	private List<String> match_order;
	
	//所有关键词中是否增加肯定POSITIVE域的关键词, 默认是不添加，严格匹配的模板可添加
	private boolean positive_interruptable;
	
	//设置打断域:用于设置开场白重定向之后可达的域，一般包括解释开场白
	private List<String> interruptable_domain;
	
	//全局打断域:全局那些域可以被打断  
	private List<String> global_interruptable_domains;
	
	//识别引擎
	private String asr_engine;
	
	//是否使用多关键词
	private boolean multi_keyword_all;
	
	//小于等于4个字的走向，默认走肯定
	private String not_match_less4_to;
	
	//未匹配到任何关键词的走向，默认"不知道
	private String not_match_to;
	
	//无声音时的走向，默认"不清楚"
	private String no_words_to;
	
	//打断新规
	private Options_interruption_config interruption_config;
	
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
	public boolean isUse_endfiles_list() {
		return use_endfiles_list;
	}
	public void setUse_endfiles_list(boolean use_endfiles_list) {
		this.use_endfiles_list = use_endfiles_list;
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
	public String getDes() {
		return des;
	}
	public void setDes(String des) {
		this.des = des;
	}
	public String getNon_interruptable() {
		return non_interruptable;
	}
	public void setNon_interruptable(String non_interruptable) {
		this.non_interruptable = non_interruptable;
	}
	public int getSilence_wait_secs() {
		return silence_wait_secs;
	}
	public void setSilence_wait_secs(int silence_wait_secs) {
		this.silence_wait_secs = silence_wait_secs;
	}
	public int getSilence_wait_time() {
		return silence_wait_time;
	}
	public void setSilence_wait_time(int silence_wait_time) {
		this.silence_wait_time = silence_wait_time;
	}
	public boolean getSilence_as_empty() {
		return silence_as_empty;
	}
	public void setSilence_as_empty(boolean silence_as_empty) {
		this.silence_as_empty = silence_as_empty;
	}
	public int getSpecial_limit() {
		return special_limit;
	}
	public void setSpecial_limit(int special_limit) {
		this.special_limit = special_limit;
	}
	public String getGrub_domain() {
		return grub_domain;
	}
	public void setGrub_domain(String grub_domain) {
		this.grub_domain = grub_domain;
	}
	
	public Map<String, List<Integer>> getFlow_grub_tail_pos() {
		return flow_grub_tail_pos;
	}
	public void setFlow_grub_tail_pos(Map<String, List<Integer>> flow_grub_tail_pos) {
		this.flow_grub_tail_pos = flow_grub_tail_pos;
	}
	public boolean isUser_define_match_order() {
		return user_define_match_order;
	}
	public void setUser_define_match_order(boolean user_define_match_order) {
		this.user_define_match_order = user_define_match_order;
	}
	public List<String> getMatch_order() {
		return match_order;
	}
	public void setMatch_order(List<String> match_order) {
		this.match_order = match_order;
	}
	public boolean isPositive_interruptable() {
		return positive_interruptable;
	}
	public void setPositive_interruptable(boolean positive_interruptable) {
		this.positive_interruptable = positive_interruptable;
	}
	public List<String> getInterruptable_domain() {
		return interruptable_domain;
	}
	public void setInterruptable_domain(List<String> interruptable_domain) {
		this.interruptable_domain = interruptable_domain;
	}
	public List<String> getGlobal_interruptable_domains() {
		return global_interruptable_domains;
	}
	public void setGlobal_interruptable_domains(List<String> global_interruptable_domains) {
		this.global_interruptable_domains = global_interruptable_domains;
	}
	public String getAsr_engine() {
		return asr_engine;
	}
	public void setAsr_engine(String asr_engine) {
		this.asr_engine = asr_engine;
	}
	public boolean isMulti_keyword_all() {
		return multi_keyword_all;
	}
	public void setMulti_keyword_all(boolean multi_keyword_all) {
		this.multi_keyword_all = multi_keyword_all;
	}
	public String getNot_match_less4_to() {
		return not_match_less4_to;
	}
	public void setNot_match_less4_to(String not_match_less4_to) {
		this.not_match_less4_to = not_match_less4_to;
	}
	public String getNot_match_to() {
		return not_match_to;
	}
	public void setNot_match_to(String not_match_to) {
		this.not_match_to = not_match_to;
	}
	public String getNo_words_to() {
		return no_words_to;
	}
	public void setNo_words_to(String no_words_to) {
		this.no_words_to = no_words_to;
	}
	public Options_interruption_config getInterruption_config() {
		return interruption_config;
	}
	public void setInterruption_config(Options_interruption_config interruption_config) {
		this.interruption_config = interruption_config;
	}
	
	
	public String getDianame() {
		return dianame;
	}
	public void setDianame(String dianame) {
		this.dianame = dianame;
	}
	public static void main(String[] args) throws Exception {
		OptionsJSON obj = new OptionsJSON();
		
		Map<String, List<Integer>> map1 = new HashMap<>();
		List<Integer> list1 = new ArrayList<>();
		list1.add(1);
		list1.add(3);
		map1.put("开场白", list1);
		
		List<Integer> list2 = new ArrayList<>();
		list1.add(2);
		list1.add(6);
		map1.put("流程2_1", list2);
		
		obj.setFlow_grub_tail_pos(map1);
		String str = BotSentenceUtil.formatJavaToJson(obj, OptionsJSON.class);
		System.out.println(str);
	}
	
}
