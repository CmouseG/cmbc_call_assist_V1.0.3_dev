package com.guiji.entity;

import com.guiji.util.CommonUtil;
import lombok.Data;
import org.springframework.util.Base64Utils;

import java.nio.charset.Charset;

@Data
public class VChatMsg {
    private String msgtype;
    private String callernum;
    private String agentnum;

    private String asrtext;
    private String asrtime;

    private String aitext;
    private String aitime;

    public static VChatMsg customerInstance(){
        VChatMsg chatMsg = new VChatMsg();
        chatMsg.setMsgtype("user");
        return chatMsg;
    }

    public static VChatMsg agentInstance(){
        VChatMsg chatMsg = new VChatMsg();
        chatMsg.setMsgtype("agent");
        return chatMsg;
    }

    public static VChatMsg aiInstance(){
        VChatMsg chatMsg = new VChatMsg();
        chatMsg.setMsgtype("ai");
        return chatMsg;
    }

    /**
     * 机器人对话结束消息，用于前端将机器人对话与座席对话分隔开
     * @return
     */
    public static VChatMsg aiEndInstance(){
        VChatMsg chatMsg = new VChatMsg();
        chatMsg.setMsgtype("ai_end");
        return chatMsg;
    }

    /**
     * 构建强制登出消息
     * @return
     */
    public static VChatMsg logoutInstance(){
        VChatMsg chatMsg = new VChatMsg();
        chatMsg.setMsgtype("logout");
        return chatMsg;
    }

    public String toBase64(){
        String str = CommonUtil.beanToJson(this);
        return Base64Utils.encodeToString(str.getBytes(Charset.forName("UTF-8")));
    }
}
