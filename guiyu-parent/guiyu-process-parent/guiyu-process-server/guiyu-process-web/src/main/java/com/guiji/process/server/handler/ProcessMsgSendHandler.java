package com.guiji.process.server.handler;

import com.guiji.process.core.IProcessCmdHandler;
import com.guiji.process.core.message.CmdMessageVO;
import com.guiji.process.core.message.CmdProtoMessage;
import com.guiji.process.core.message.MessageProto;
import com.guiji.process.core.util.CmdMessageUtils;
import com.guiji.process.core.vo.CmdMsgSenderMap;
import com.guiji.process.server.core.ConnectionPool;
import com.guiji.utils.JsonUtils;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhujy on 2018/11/17.
 */
@Service
public class ProcessMsgSendHandler {

    public void run()
    {
        List<CmdMessageVO> cmdMessageVOs = null;
        while(true)
        {
            try {

                cmdMessageVOs = CmdMsgSenderMap.getInstance().queryNeedResendMsgs();
                if(cmdMessageVOs == null)
                {
                    continue;
                }

                for (CmdMessageVO cmdMessageVO:cmdMessageVOs) {
                    // 调用底层通信，发送命令
                    ChannelHandlerContext ctx = ConnectionPool.getChannel(cmdMessageVO.getProcessInstanceVO().getIp());

                    CmdProtoMessage.ProtoMessage.Builder builder = CmdMessageUtils.convert(cmdMessageVO);
                    builder.setType(2);

                    ctx.writeAndFlush(builder);

                    CmdMsgSenderMap.getInstance().produce(cmdMessageVO);
                }

                Thread.sleep(300);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
