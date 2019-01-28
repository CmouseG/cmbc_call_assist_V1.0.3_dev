package com.guiji.botsentence.manager;

import com.guiji.botsentence.vo.AIHelloRequestVO;
import com.guiji.botsentence.vo.AIResponseVO;
import com.guiji.botsentence.vo.AIRestoreRequestVO;

/**
 * @Auther: 魏驰
 * @Date: 2019/1/9 18:22
 * @Project：guiji-parent
 * @Description:
 */
public interface IAIManager {
    AIResponseVO restore(AIRestoreRequestVO aiRequest) throws Exception;

    boolean isUuidExist(String uuid);

    AIResponseVO hello(AIHelloRequestVO aiRequest) throws Exception;

    void end(String uuid);
    
    void endAll();
}
