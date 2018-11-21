package com.guiji.robot.service.vo;

import java.util.Map;

import lombok.Data;

/** 
* @ClassName: HsReplace 
* @Description: 话术模板-对应的是replace.json文件
* @date 2018年11月20日 下午3:54:43 
* @version V1.0  
*/
@Data
public class HsReplace {
	
	private boolean template_tts_flag;  //这几个都表示是否需要tts合成
	private boolean tts_new;	//这几个都表示是否需要tts合成
	private boolean tts_partial;	//这几个都表示是否需要tts合成
	
	private String use_speaker_flag;	//模型编号（录音师）
	private String[] num_sentence_merge_lst; //几段需要tts合成的录音wav文件id
	
	private Map<String,String[]> rec_tts_wav; //每段tts wav语音需要哪几段语音合并
	
	private Map<String,String> tts_pos; //每个支段录音的文本（带参）
	
	private String[] replace_variables_flag;	//本模板需要的参数
	private String[] replace_variables_type;	//本模板需要的参数类型
}






/********************************************************示例**********************************************************************/
//{
//    "template_tts_flag": true,
//    "tts_new": true,
//    "tts_partial" : true,
//    "use_speaker_flag":"5",
//    "num_sentence_merge_lst":[1, 2, 21, 24,43],
//    "old_num_sentence_merge_lst":[1, 2, 21, 24,43],
//    "rec_tts_wav":
//    {
//        "1":["1_1", "1_2"],
//        "2":["2_1", "2_2"],
//        "21":["21_1","21_2","21_3"],
//        "24":["24_1","24_2"],
//        "43":["43_1", "43_2","43_3"]
//    },
//    "tts_pos":
//    {
//        "1_2": "请问是$0000吗",
//        "2_2": "请您立即处理欠款$1111",
//        "21_2": "$1111",
//        "24_1": "您的贷款已拖欠:$3333天了",
//        "43_2": "如果您认识$0000"
//    },
//    "replace_variables_flag":["$0000", "$1111","$3333"],
//    "replace_variables_type":["name", "money","normal"],
//    "1_backup": "您好，请问是是本人吗",
//    "2_backup": "这边是360借条贷后管理部，您的贷款已逾期，为了不影响您的信用,请您立即处理欠款",
//    "21_backup": "为避免给您造成不必要的困扰，请务必于今日足额还清。",
//    "24_backup": "你的贷款已逾期,为避免影响您的人行征信信息，请务必于今日足额还款。",
//    "43_backup": "这边是360借条贷后管理部，如果您认识他,请您转告他及时回电:01053779068，01053779068，可以吗？",
//    "1_replace_sentence": "您好，$1_2",
//    "2_replace_sentence": "这边是360借条贷后管理部，您的贷款已逾期，为了不影响您的信用,请您立即处理欠款$2_2",
//    "21_replace_sentence": "您的欠款金额为：$21_2，为避免给您造成不必要的困扰，请务必于今日足额还清。",
//    "24_replace_sentence": "$24_1，为避免影响您的人行征信信息，请务必于今日足额还款。",
//    "43_replace_sentence": " 这边是($360)借条贷后管理部,$43_2,请您转告他及时回电:01053779068，01053779068，可以吗？"
//}
