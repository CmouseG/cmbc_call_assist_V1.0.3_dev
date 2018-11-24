package com.guiji.process.core;


import com.guiji.process.core.message.CmdMessageVO;

public interface IProcessCmdHandler {
        void excute(CmdMessageVO cmdMessageVO) throws  Exception;
}
