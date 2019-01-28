package com.guiji.botsentence.service;

import com.guiji.botsentence.vo.ResponseSelfTestVO;
import com.guiji.botsentence.vo.SelfTestVO; /**
 * @Auther: 魏驰
 * @Date: 2019/1/9 15:30
 * @Project：guiji-parent
 * @Description:
 */
public interface ISelfTestService {
    ResponseSelfTestVO makeTest(SelfTestVO request, String userId) throws Exception;

    void endTest(String uuid);
}
