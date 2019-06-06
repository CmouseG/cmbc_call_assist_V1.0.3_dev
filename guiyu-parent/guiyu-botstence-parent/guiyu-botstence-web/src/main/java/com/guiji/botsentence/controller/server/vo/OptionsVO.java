package com.guiji.botsentence.controller.server.vo;

import java.util.List;

import com.guiji.botsentence.dao.entity.BotSentenceOptions;
import com.guiji.botsentence.dao.entity.VoliceInfo;

public class OptionsVO extends BotSentenceOptions {

	private String optionsId;
	private String processId;
	private String templateId;
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
