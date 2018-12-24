package ai.guiji.botsentence.service;

import java.io.File;

import ai.guiji.botsentence.vo.BotSentenceProcessVO;

public interface IImportProcessService {

    void importProcess(File templateFile, BotSentenceProcessVO paramVO, Long userId);

}
