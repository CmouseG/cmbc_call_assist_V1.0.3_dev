package ai.guiji.botsentence.service;

import java.io.File;

public interface IImportProcessService {

    void importProcess(File templateFile, String templateType, String templatId, Long userId);

}
