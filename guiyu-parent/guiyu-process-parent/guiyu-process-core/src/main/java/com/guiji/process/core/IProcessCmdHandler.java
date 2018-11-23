package com.guiji.process.core;


import com.guiji.process.core.message.CmdMessageVO;
import com.guiji.process.core.vo.ProcessInstanceVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

public interface IProcessCmdHandler {
        void excute(CmdMessageVO cmdMessageVO) throws  Exception;
}
