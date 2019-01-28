package com.guiji.botsentence.service;

import java.io.File;

import com.guiji.botsentence.vo.BotSentenceProcessVO;

public interface IImportProcessService {

	void importProcess(File templateFile, BotSentenceProcessVO paramVO, String userId);

}
