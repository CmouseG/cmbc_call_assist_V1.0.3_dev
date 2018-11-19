package ai.guiji.botsentence.service;

import java.io.File;
import java.io.IOException;

/**
 * 生成文件service
 * @Description:
 * @author liyang  
 * @date 2018年8月22日  
 *
 */
public interface IFileGenerateService {

	File fileGenerate(String processId, String dirName) throws IOException;


}
