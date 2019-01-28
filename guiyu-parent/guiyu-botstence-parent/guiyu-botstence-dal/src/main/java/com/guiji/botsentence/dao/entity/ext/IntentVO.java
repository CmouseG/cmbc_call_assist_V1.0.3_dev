package com.guiji.botsentence.dao.entity.ext;

import com.guiji.botsentence.dao.entity.BotSentenceIntent;

public class IntentVO extends BotSentenceIntent{

	private long oldIntentId;

	public long getOldIntentId() {
		return oldIntentId;
	}

	public void setOldIntentId(long oldIntentId) {
		this.oldIntentId = oldIntentId;
	}
	
	
}
