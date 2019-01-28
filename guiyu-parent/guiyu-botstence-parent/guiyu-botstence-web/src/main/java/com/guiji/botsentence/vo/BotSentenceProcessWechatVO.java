package com.guiji.botsentence.vo;

import java.util.List;

public class BotSentenceProcessWechatVO {

	private List<BotSentenceProcessVO> list;
	private int finish;
	private int unfinish;
	public List<BotSentenceProcessVO> getList() {
		return list;
	}
	public void setList(List<BotSentenceProcessVO> list) {
		this.list = list;
	}
	public int getFinish() {
		return finish;
	}
	public void setFinish(int finish) {
		this.finish = finish;
	}
	public int getUnfinish() {
		return unfinish;
	}
	public void setUnfinish(int unfinish) {
		this.unfinish = unfinish;
	}
	
	
}
