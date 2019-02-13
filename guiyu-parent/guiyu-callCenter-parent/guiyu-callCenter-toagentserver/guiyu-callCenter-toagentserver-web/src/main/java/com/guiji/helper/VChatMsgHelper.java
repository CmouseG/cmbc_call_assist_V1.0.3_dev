package com.guiji.helper;

import com.guiji.util.CommonUtil;
import com.guiji.web.response.QueryRecordInDetail;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.util.Base64Utils;

import java.nio.charset.Charset;

/**
 * @Auther: 魏驰
 * @Date: 2019/2/2 11:53
 * @Project：guiyu-parent
 * @Description:
 */
public class VChatMsgHelper {
    /**
     * 构建呼叫实时消息
     * @param queryRecordInDetail
     * @return
     */
    public static String buildPhoneInfoMsg(QueryRecordInDetail queryRecordInDetail){
        VMsg vMsg = new VMsg("phoneinfo", queryRecordInDetail);
        String str = CommonUtil.beanToJson(vMsg);
        return Base64Utils.encodeToString(str.getBytes(Charset.forName("UTF-8")));
    }

    @Data
    @AllArgsConstructor
    public static class VMsg{
        private String msgtype;
        private Object data;
    }
}
