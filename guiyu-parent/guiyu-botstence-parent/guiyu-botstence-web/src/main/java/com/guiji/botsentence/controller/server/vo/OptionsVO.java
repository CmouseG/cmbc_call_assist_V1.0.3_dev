package com.guiji.botsentence.controller.server.vo;

import java.util.List;

import com.guiji.botsentence.dao.entity.BotSentenceOptions;
import com.guiji.botsentence.dao.entity.VoliceInfo;

public class OptionsVO extends BotSentenceOptions {

	private String optionsId;
	private String processId;
	private String templateId;
	/*private boolean non_interruptable_start;
	private boolean silence_wait_start;
	private boolean silence_as_empty_start;
	private boolean user_define_match_order_start;
	private boolean grub_start;
	private boolean interruptable_domain_start;
	private boolean global_interruptable_domains_start;
	private boolean ignore_but_domains_start;
	private boolean ignore_user_sentence_start;
	private boolean ignore_but_negative_start;
	private boolean not_match_less4_to_start;
	private boolean not_match_to_start;
	private boolean no_words_to_start;
	private boolean new_interrupt_start;
	private boolean special_limit_start;*/
	
	private String voliceContent;
	private List<DomainParamVO> ignoreButDomains;
	private List<String> ignoreUserSentences;
	private List<String> ignoreButNegatives;
    private List<DomainParamVO> matchOrders;
    private List<DomainParamVO> notMatchLess4Tos;
    private List<DomainParamVO> notMatchTos;
    private List<DomainParamVO> noWordsTos;
    private List<VoliceInfo> silenceVoliceList;
	private List<String> isSpecialLimitFreeList;
	private String flag;
	
	public String getOptionsId() {
		return optionsId;
	}

	public void setOptionsId(String optionsId) {
		this.optionsId = optionsId;
	}

	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}
/*
	public boolean isNon_interruptable_start() {
		return non_interruptable_start;
	}

	public void setNon_interruptable_start(boolean non_interruptable_start) {
		this.non_interruptable_start = non_interruptable_start;
	}

	public boolean isSilence_wait_start() {
		return silence_wait_start;
	}

	public void setSilence_wait_start(boolean silence_wait_start) {
		this.silence_wait_start = silence_wait_start;
	}

	public boolean isSilence_as_empty_start() {
		return silence_as_empty_start;
	}

	public void setSilence_as_empty_start(boolean silence_as_empty_start) {
		this.silence_as_empty_start = silence_as_empty_start;
	}

	public boolean isUser_define_match_order_start() {
		return user_define_match_order_start;
	}

	public void setUser_define_match_order_start(boolean user_define_match_order_start) {
		this.user_define_match_order_start = user_define_match_order_start;
	}

	public boolean isGrub_start() {
		return grub_start;
	}

	public void setGrub_start(boolean grub_start) {
		this.grub_start = grub_start;
	}

	public boolean isInterruptable_domain_start() {
		return interruptable_domain_start;
	}

	public void setInterruptable_domain_start(boolean interruptable_domain_start) {
		this.interruptable_domain_start = interruptable_domain_start;
	}

	public boolean isGlobal_interruptable_domains_start() {
		return global_interruptable_domains_start;
	}

	public void setGlobal_interruptable_domains_start(boolean global_interruptable_domains_start) {
		this.global_interruptable_domains_start = global_interruptable_domains_start;
	}

	public boolean isIgnore_but_domains_start() {
		return ignore_but_domains_start;
	}

	public void setIgnore_but_domains_start(boolean ignore_but_domains_start) {
		this.ignore_but_domains_start = ignore_but_domains_start;
	}

	public boolean isIgnore_user_sentence_start() {
		return ignore_user_sentence_start;
	}

	public void setIgnore_user_sentence_start(boolean ignore_user_sentence_start) {
		this.ignore_user_sentence_start = ignore_user_sentence_start;
	}

	public boolean isIgnore_but_negative_start() {
		return ignore_but_negative_start;
	}

	public void setIgnore_but_negative_start(boolean ignore_but_negative_start) {
		this.ignore_but_negative_start = ignore_but_negative_start;
	}

	public boolean isNot_match_less4_to_start() {
		return not_match_less4_to_start;
	}

	public void setNot_match_less4_to_start(boolean not_match_less4_to_start) {
		this.not_match_less4_to_start = not_match_less4_to_start;
	}

	public boolean isNot_match_to_start() {
		return not_match_to_start;
	}

	public void setNot_match_to_start(boolean not_match_to_start) {
		this.not_match_to_start = not_match_to_start;
	}

	public boolean isNo_words_to_start() {
		return no_words_to_start;
	}

	public void setNo_words_to_start(boolean no_words_to_start) {
		this.no_words_to_start = no_words_to_start;
	}

	public boolean isNew_interrupt_start() {
		return new_interrupt_start;
	}

	public void setNew_interrupt_start(boolean new_interrupt_start) {
		this.new_interrupt_start = new_interrupt_start;
	}

	public boolean isSpecial_limit_start() {
		return special_limit_start;
	}

	public void setSpecial_limit_start(boolean special_limit_start) {
		this.special_limit_start = special_limit_start;
	}*/

	public List<DomainParamVO> getIgnoreButDomains() {
		return ignoreButDomains;
	}

	public void setIgnoreButDomains(List<DomainParamVO> ignoreButDomains) {
		this.ignoreButDomains = ignoreButDomains;
	}

	

	public List<String> getIgnoreUserSentences() {
		return ignoreUserSentences;
	}

	public void setIgnoreUserSentences(List<String> ignoreUserSentences) {
		this.ignoreUserSentences = ignoreUserSentences;
	}

	public List<DomainParamVO> getMatchOrders() {
		return matchOrders;
	}

	public void setMatchOrders(List<DomainParamVO> matchOrders) {
		this.matchOrders = matchOrders;
	}


	public List<DomainParamVO> getNotMatchLess4Tos() {
		return notMatchLess4Tos;
	}

	public void setNotMatchLess4Tos(List<DomainParamVO> notMatchLess4Tos) {
		this.notMatchLess4Tos = notMatchLess4Tos;
	}

	public List<DomainParamVO> getNotMatchTos() {
		return notMatchTos;
	}

	public void setNotMatchTos(List<DomainParamVO> notMatchTos) {
		this.notMatchTos = notMatchTos;
	}

	public List<DomainParamVO> getNoWordsTos() {
		return noWordsTos;
	}

	public void setNoWordsTos(List<DomainParamVO> noWordsTos) {
		this.noWordsTos = noWordsTos;
	}

	public String getVoliceContent() {
		return voliceContent;
	}

	public void setVoliceContent(String voliceContent) {
		this.voliceContent = voliceContent;
	}

	public List<VoliceInfo> getSilenceVoliceList() {
		return silenceVoliceList;
	}

	public void setSilenceVoliceList(List<VoliceInfo> silenceVoliceList) {
		this.silenceVoliceList = silenceVoliceList;
	}

	public List<String> getIgnoreButNegatives() {
		return ignoreButNegatives;
	}

	public List<String> getIsSpecialLimitFreeList() {
		return isSpecialLimitFreeList;
	}

	public void setIsSpecialLimitFreeList(List<String> isSpecialLimitFreeList) {
		this.isSpecialLimitFreeList = isSpecialLimitFreeList;
	}

	public void setIgnoreButNegatives(List<String> ignoreButNegatives) {
		this.ignoreButNegatives = ignoreButNegatives;
	}

	
}
