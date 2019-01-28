package com.guiji.botsentence.vo;

import com.google.common.base.Strings;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AIResponseVO {
    /**
     * state : 开场白
     * answer : 您好，泰国海边的，年投资回报率8%以上的房产项目，也适合度假养老的，给您介绍一下，可以吗？
     * wav_filename : xtw_rec/3.wav
     * sentence :
     * intent : 100
     * accurate_intent : D
     * reason : 未说话挂断
     * user_attentions : 开场白
     * UserInfo : 开场白
     * log_file : /home/log_sellbot/logic/20180720_15051/alllll.log
     * app_log : /home/log_sellbot/app/20180720_15051/alllll_app.log
     * app_port : 15051
     * end : 0
     * wait_wavs : ["xtw_rec/177.wav","xtw_rec/178.wav","xtw_rec/179.wav","xtw_rec/180.wav","xtw_rec/181.wav","xtw_rec/182.wav","xtw_rec/183.wav","xtw_rec/184.wav"]
     * interrupt_wavs : ["xtw_rec/159.wav","xtw_rec/160.wav","xtw_rec/161.wav"]
     * dtmfflag : false
     * dtmflen : 1
     * dtmftimeout : 10
     * dtmfend :
     * dtmfresult : -1
     */

    private String state;
    private String answer;
    private String wav_filename;
    private String sentence;
    private String intent;
    private String accurate_intent;
    private String reason;
    private String user_attentions;
    private String UserInfo;
    private String log_file;
    private String app_log;
    private String app_port;
    private int end;
    private int port;

    //值为 0 的时候代表精确命中关键词
    //值为 1，代表未命中
    private int match_nothing;

    private String dtmfflag;
    private String dtmflen;
    private String dtmftimeout;
    private String dtmfend;
    private String dtmfresult;
    private List<String> wait_wavs;
    private List<String> interrupt_wavs;

    private String word_segment_result;
    private int match_type;
    private String keywords;
    private int match_method;
    private String intention_confidence;

    public boolean isValid(){
        return !Strings.isNullOrEmpty(wav_filename) && !Strings.isNullOrEmpty(answer);
    }

    public String getKeywords(){
        if(match_type == 2){
            return keywords;
        }else{
            switch (match_type){
                case 0: return "识别为空";
                case 1: return "未匹配";
                case 3: return "静音超时";
                case 4: return "忽略";
                case 5: return "轮数超限";
                case 10: return "无匹配默认肯定";
                case 11: return "说啊默认肯定";
                case 12: return "无匹配且小于4个字";
                case 99: return "未分类";
            }
        }

        return "";
    }

    /**
     * 是否命中关键词
     * @return
     */
    public boolean isMatch(){
        return !state.equals("未匹配响应");
    }
}
