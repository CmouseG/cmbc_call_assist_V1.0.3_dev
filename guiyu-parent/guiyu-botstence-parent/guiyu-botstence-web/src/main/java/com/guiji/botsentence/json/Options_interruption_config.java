package com.guiji.botsentence.json;

public class Options_interruption_config {

	//允许打断的字数    
	private int interrupt_words_num;
	
	//两次打断之间最小间隔的秒数
	private int interrupt_min_interval;
	
	//和voice机器人在打断后重复上句时需要加上的前缀语
	private String voice;

	public int getInterrupt_words_num() {
		return interrupt_words_num;
	}

	public void setInterrupt_words_num(int interrupt_words_num) {
		this.interrupt_words_num = interrupt_words_num;
	}

	public int getInterrupt_min_interval() {
		return interrupt_min_interval;
	}

	public void setInterrupt_min_interval(int interrupt_min_interval) {
		this.interrupt_min_interval = interrupt_min_interval;
	}

	public String getVoice() {
		return voice;
	}

	public void setVoice(String voice) {
		this.voice = voice;
	}
	
	
}
