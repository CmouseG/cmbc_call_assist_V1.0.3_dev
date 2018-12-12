package ai.guiji.botsentence.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import ai.guiji.botsentence.constant.Constant;

public class BotSentenceUtil {

	public static List<String> getKeywords(String keyword) {
		List<String> result = new ArrayList<>();
		List<String> yulius = new ArrayList<>();
		keyword = keyword.substring(1,keyword.length()-1);
		while(keyword.contains("[")) {
			int start = keyword.indexOf("[");
			int end1 = keyword.indexOf("]");
			yulius.add(keyword.substring(start,end1+1));
			//System.out.println(aa.substring(start,end+1));
			if(end1+2 > keyword.length()) {
				keyword = keyword.substring(0, start) + keyword.substring(keyword.length(), keyword.length());
			}else {
				keyword = keyword.substring(0, start) + keyword.substring(end1+2, keyword.length());
			}
			
		}
		
		String yuliuStr = "";
		for(String temp1 : yulius) {
			yuliuStr = yuliuStr + temp1 + ",";
		}
		if(StringUtils.isNotBlank(yuliuStr)) {
			yuliuStr = yuliuStr.substring(0, yuliuStr.length()-1);
		}
		
		result.add(keyword);
		result.add(yuliuStr);
		
		return result;
	}
	
	public static String[] getResponses(String resp){
		if(StringUtils.isBlank(resp) || "[]".equals(resp.trim())) {
			return null;
		}
		String[] respArray = resp.substring(1,resp.length()-1).split(",");
		return respArray;
	}
	
	/**
	 * 根据前台的关键字参数，生成json格式
	 * 例如:不要，不需要，不好====>>["不要","不需要","不好"]
	 * @param keywords
	 * @return
	 */
	public static String generateKeywords(String keywords) {
		
		String new_key_words = "[";
		if(StringUtils.isNotBlank(keywords)) {
			String replaceKeyWords = keywords.replaceAll("，", ",");
			String []keywords_array = replaceKeyWords.split(",");
			for(int i = 0 ; i < keywords_array.length ; i++) {
				new_key_words = new_key_words + "\"" + keywords_array[i].trim() + "\"" + ",";
			}
			new_key_words = new_key_words.substring(0, new_key_words.length() -1);
		}
		new_key_words = new_key_words+"]";
		
		
		  return new_key_words;
	}
	
	
	/**
	 * 根据后台存储的关键字，转化成前台显示的格式
	 * 例如:["不要","不需要","不好"]====>>"不要，不需要，不好"
	 * @param keywords
	 * @return
	 */
	public static String generateShowKeywords(String keywords) {
		String new_key_words = "";
		if(StringUtils.isNotBlank(keywords)) {
			List<String> list = getKeywords(keywords);
			if(null != list && list.size() > 0) {
				new_key_words = list.get(0).replace("\"", "");
			}
		}
		return new_key_words;
	}
	
	/**
	 * 根据给定的指定列表获取最大的一个数值
	 * @param lilst
	 * @return
	 */
	public static int getMaxNum(List<Integer> list) {
		if(null != list) {
			return Collections.max(list);
		}
		return 0;
	}
	
	
	public static void main(String[] args) {
		String forselect_keywords = "[\"做哪方面\",\"机构\",\"什么事\",\"介绍公司\",\"什么公司\",\"什么楼盘\",\"项目名称\",\"哪个楼盘的\",[\"公司*!\",\"哪个\"],[\"介绍下*!\",\"公司*!\"],[\"你叫什么*!\",\"公司*!\"],\"有没有在听\",\"答非所问\",\"等一会\",\"声音好听\",\"请你吃饭\",\"停车场\",\"车位售价\",[\"停车场*!\",\"能买\"],[\"停车场*!\"],[\"停车场*!\"],[\"停车场*!\",\"免费\"],[\"停车场*!\",\"够\"],[\"停车场*!\",\"出租\"],[\"一平*!\",\"多少\"],[\"交房*!\",\"什么时候\"],[\"交付*!\",\"什么时候\"],[\"什么时候\",\"开盘*!\"],[\"什么时候*!\",\"项目\"],[\"多少费用*!\",\"能买\"],[\"什么位置\",\"介绍公司*!\",\"公司位置\",\"地址\"],\"得房率\",\"公摊面积\",\"设施占比\",[\"公摊*!\"],\"手续费\",[\"多少钱\",\"手续费*!\"],\"物业\",\"物业费\",\"水电费怎么付款\",\"水电费\",\"中介费\",[\"收费\",\"物业费*!\",\"谁来付\"],[\"中介费*!\",\"多少钱\"],[\"怎么算\",\"水电*!\"],[\"付款\",\"物业费*!\"],[\"付款\",\"水电费*!\"],\"精装\",\"毛坯房\",\"装修怎么保障\",\"装修标准\",\"打包入住\",\"风格\",[\"房子\",\"装修*!\"],[\"开发商\",\"装修*!\"],\"样板间\",\"楼距\",\"付款\",\"按揭\",\"首付\",\"汇款\",\"银行贷款\",\"外汇管制\",\"收费方式\",\"贷款\",\"分期\",\"可以贷款\",\"公积金\",[\"如何\",\"怎么\",\"汇钱*!\"],[\"买房\",\"贷款*!\"],[\"公积金*!\",\"可以贷款\"],\"公司代理\",\"开发商\",\"地产商\",\"优势\",[\"哪个\",\"开发商*!\",\"那位\"],[\"你叫什么\",\"开发商*!\"],[\"土地\",\"年的*!\"],[\"多长时间\",\"房子*!\"],\"骗人\",\"靠谱\",\"保证\",\"诈骗电话\",\"信任你们\",\"不放心\",\"合法\",[\"公司\",\"合法*!\"],[\"保证*!\",\"开发商\"],\"泄露\",[\"信息\",\"我的信息\",\"泄露*!\"],\"跑路\",\"限购\",\"购买条件\",\"购房条件\",\"不是本地\",\"什么要求\",\"没社保\",\"能买\",\"不能买\",\"社保:公积金\",[\"一年\",\"半年\",\"社保*!\"],\"年龄\",\"太不专业\",\"直接介绍\",\"直接说\",[\"就要*!\",\"给我介绍\"],[\"就要\",\"跟你说*!\"],[\"不能*!\",\"给我介绍\"],\"机器人\",\"录音\",\"从哪知道信息\",\"我资料\",\"我电话\",\"怎么拿到\",\"哪里弄得\",[\"哪里弄的*!\",\"我电话\",\"我资料\"],[\"怎么拿到*!\",\"手机号\"],[\"怎么拿到*!\",\"我资料\"],[\"哪位*!\",\"我电话\",\"我资料\"],[\"哪个*!\",\"我电话\",\"我资料\"],[\"哪里\",\"我资料*!\"],\"怎么联系\",\"你的联系方式\",\"号码多少\",\"找不到微信\",\"加下微信\",\"发我手机上\",\"短信\",\"发送\",[\"发送*!\",\"我电话\",\"短信\"],\"有学校么\",\"有医院么\",\"生活配套怎么样\",\"生活便利吗\",\"日常生活怎么样\",\"哪个区\",\"哪个城市\",\"哪个市区\",\"哪边\",\"地址\",\"在哪\",\"公司位置\",\"在什么旁边\",\"什么位置\",\"你们公司在那边\",\"什么地方\",\"房子在哪里\",\"收费\",\"多少费用\",\"价格\",\"均价\",\"价格多少\",\"大概多少钱\",\"费用问题\",\"大概收多少\",\"多贵\",\"多少一平\",\"交房\",\"开盘\",\"怎么卖\",\"优惠\",\"交付时间\",\"交房时间;面积范围\",\"面积多大;户型大小.房子多大\",\"多大面积\",\"面积\",\"多少年的\",\"是40年的么\",\"40年还是50年的\",\"有房本么\",\"产权多少年\",\"多少年产权的\",\"年限多少\",\"是工业用地还是商业用地\",\"是商业用地吗\",\"土地性质\",\"什么土地性质\",\"产权\",\"你们这个有没有产权的\"]";
		
		String deleteKeyword = "[\"做哪方面\",\"什么位置\",\"介绍公司\",\"做哪方面\"]";
		
		String addKeyword = "什么位置222,介绍公司,做哪方面222";
		
		String newkeyword = deleteKeywords(deleteKeyword, forselect_keywords);
		System.out.println(newkeyword);
		newkeyword = addKeywords(addKeyword, newkeyword);
		System.out.println(newkeyword);
	}
	
	public static List<String> getSimtxtKeywords(String simTxt){
		List<String> list = new ArrayList<>();
		
		String [] line_array = simTxt.split("\n");
		if(null != line_array && line_array.length > 0) {
			for(int i = 0 ; i < line_array.length ; i++) {
				String line = line_array[i];
				String []array2 = line.split(":");
				if(null != array2 && array2.length > 1) {
					list.add(array2[0].trim());
					String[] keywords = array2[1].split("\\s+");
					for(int j = 0 ; j < keywords.length ; j++) {
						list.add(keywords[j]);
					}
				}
			}
		}
		return list;
	}
	
	public static String deleteSimtxtKeywords(String simTxt, List<String> deletes){
		
		String new_simTxt = "";
		String [] line_array = simTxt.split("\n");
		if(null != line_array && line_array.length > 0) {
			for(int i = 0 ; i < line_array.length ; i++) {
				List<String> list = new ArrayList<>();
				
				boolean exist = false;
				
				String line = line_array[i];
				line = line.replaceAll("：", ":");
				String []array2 = line.split(":");
				if(null != array2 && array2.length > 1) {
					list.add(array2[0]);//添加母关键词
					String[] keywords = array2[1].split("\\s+");
					for(int j = 0 ; j < keywords.length ; j++) {
						list.add(keywords[j]);
					}
				}
				
				for(int j = 0 ; j < list.size() ; j++) {
					if(deletes.contains(list.get(j))) {//存在需要删除的关键字，则这一行要删除掉
						exist = true;
						break;
					}
				}
				
				if(!exist) {//如果不存在，才添加
					new_simTxt = new_simTxt + line + "\n";
				}
				
			}
		}
		return new_simTxt;
	}
	
	
	public static String addKeywords(String addKeyword, String forselect_keywords) {
		String[] keys = addKeyword.split(",");
		
		List<String> selectKeywordList = BotSentenceUtil.getKeywords(forselect_keywords);
		String leftSelectKeyWord = selectKeywordList.get(0);
		
		String[] select_keyword_array = leftSelectKeyWord.split(",");
		List<String> select_keyword_list = Arrays.asList(select_keyword_array);
		
		
		for(int i = 0 ; i < keys.length ; i++) {
			if(!select_keyword_list.contains("\"" + keys[i] + "\"")) {
				forselect_keywords = forselect_keywords.substring(0, forselect_keywords.length() -1 ) + ",\"" + keys[i] + "\"]";
			}
		}
		
		
		return forselect_keywords;
	}
	
	public static String deleteKeywords(String deleteKeyword, String forselect_keywords) {
		if(StringUtils.isEmpty(deleteKeyword)) {
			return forselect_keywords;
		}
		List<String> selectKeywordList = BotSentenceUtil.getKeywords(forselect_keywords);
		String leftSelectKeyWord = selectKeywordList.get(0);
		String yuliuSelectKeyWord = null;
		if(selectKeywordList.size() > 1) {
			yuliuSelectKeyWord = selectKeywordList.get(1);
		}
		
		String[] select_keyword_array = leftSelectKeyWord.split(",");
		List<String> select_keyword_list = Arrays.asList(select_keyword_array);
		List<String> new_select_keyword_list = new ArrayList<>();
		
		
		List<String> deleteKeywordList = BotSentenceUtil.getKeywords(deleteKeyword);
		
		String left = deleteKeywordList.get(0);
		String[] delete_keyword_array = left.split(",");
		List<String> delete_keyword_List = Arrays.asList(delete_keyword_array);
		
		for(String temp : select_keyword_list) {
			if(delete_keyword_List.contains(temp)) {
				continue;
			}
			new_select_keyword_list.add(temp);
		}
		
		String left_select_keyword = "";
		for(int i = 0 ; i < new_select_keyword_list.size() ; i++) {
			if(i == 0){
				left_select_keyword = new_select_keyword_list.get(i);
			}else {
				left_select_keyword = left_select_keyword + "," + new_select_keyword_list.get(i);
			}
		}
		
		if(org.apache.commons.lang.StringUtils.isNotBlank(yuliuSelectKeyWord)) {
			forselect_keywords = left_select_keyword+ "," + yuliuSelectKeyWord;
		}else {
			forselect_keywords = left_select_keyword;
		}
		forselect_keywords = "[" + forselect_keywords + "]";
		return forselect_keywords;
	}
	
	
	/**
	 * 校验是否包含变量，例如：$1000
	 * @param str
	 * @return
	 */
	public static boolean validateContainParam (String str) {
		 String regEx = Constant.TTS_REG_EX;
	    // 编译正则表达式
	    Pattern pattern = Pattern.compile(regEx);
	    Matcher matcher = pattern.matcher(str);
	    // 字符串是否与正则表达式相匹配
	    boolean rs = matcher.find();
	    return rs;
	}
	
	
	public static Map<String, String> getSimtxtKeywordsByKeyword(List<List<String>> simList, String[] keys){
		Map<String, String> map = new HashMap<>();
		List<String> result = new ArrayList<>();
		if(null != simList && simList.size() > 0) {
			for(int i = 0 ; i < simList.size() ; i++) {
				List<String> line = simList.get(i);
				for(int j = 0 ; j < keys.length ; j++) {
					if(line.contains(keys[j])) {//当前行包含该关键词，则返回当前行的关键字列表
						//result.addAll(line);
						for(int m = 0 ; m < line.size() ; m++) {
							map.put(line.get(m), keys[j]);
						}
					}
				}
			}
		}
		return map;
	}
	
	public static List<List<String>> getSimtxtKeywordsList(String simTxt){
		List<List<String>> result = new ArrayList<>();
		Map<String, String> map = new HashMap<>();
		String [] line_array = simTxt.split("\n");
		if(null != line_array && line_array.length > 0) {
			for(int i = 0 ; i < line_array.length ; i++) {
				List<String> list = new ArrayList<>();
				String line = line_array[i];
				String []array2 = line.split(":");
				if(null != array2 && array2.length > 1) {
					list.add(array2[0].trim());
					String[] keywords = array2[1].split("\\s+");
					for(int j = 0 ; j < keywords.length ; j++) {
						//map.put(keywords[j], array2[0].trim());
						list.add(keywords[j]);
					}
				}
				result.add(list);
			}
		}
		return result;
	}
	
}
