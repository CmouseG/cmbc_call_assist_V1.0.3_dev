package com.guiji.botsentence.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.URI;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.guiji.botsentence.constant.Constant;
import com.guiji.botsentence.json.JSONConstant;
import com.guiji.component.client.util.FileUtil;
import com.guiji.component.client.util.JsonBase64Crypter;

public class BotSentenceUtil {

	private static Logger logger = LoggerFactory.getLogger(BotSentenceUtil.class);
	
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
	
	
	/**
	 * 根据意图表的关键词获取多关键词，参数["","","",["",""]]，返回值带双引号
	 */
	public static List<String> getComplexKeywordsWithQuot(String keyword) {
		List<String> yulius = new ArrayList<>();
		keyword = keyword.substring(1,keyword.length()-1);
		while(keyword.contains("[")) {
			int start = keyword.indexOf("[");
			int end1 = keyword.indexOf("]");
			yulius.add(keyword.substring(start,end1+1));
			if(end1+2 > keyword.length()) {
				keyword = keyword.substring(0, start) + keyword.substring(keyword.length(), keyword.length());
			}else {
				keyword = keyword.substring(0, start) + keyword.substring(end1+2, keyword.length());
			}
			
		}
		return yulius;
	}
	
	
	/**
	 * 根据意图表的关键词获取多关键词，参数["","","",["",""]]，返回值不带双引号
	 */
	public static List<String> getComplexKeywordsWithOutQuot(String keyword) {
		List<String> yulius = new ArrayList<>();
		keyword = keyword.replace("\"", "");
		keyword = keyword.substring(1,keyword.length()-1);
		while(keyword.contains("[")) {
			int start = keyword.indexOf("[");
			int end1 = keyword.indexOf("]");
			yulius.add(keyword.substring(start,end1+1));
			if(end1+2 > keyword.length()) {
				keyword = keyword.substring(0, start) + keyword.substring(keyword.length(), keyword.length());
			}else {
				keyword = keyword.substring(0, start) + keyword.substring(end1+2, keyword.length());
			}
			
		}
		return yulius;
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
		if (StringUtils.isNotBlank(keywords)) {
			String replaceKeyWords = keywords.replaceAll("，", ",");// 替换中文逗号
			replaceKeyWords = replaceKeyWords.replace("\n", "");// 替换换行符
			replaceKeyWords = replaceKeyWords.replace("\r", "");// 替换换行符
			replaceKeyWords = replaceKeyWords.replace("\"", "");// 替换英文双引号
			replaceKeyWords = replaceKeyWords.replace("“", "");// 替换中文双引号
			replaceKeyWords = replaceKeyWords.replace("$", "");// 替换中文双引号
			replaceKeyWords = replaceKeyWords.replace("(", "");// 替换中文双引号
			replaceKeyWords = replaceKeyWords.replace(")", "");// 替换中文双引号
			replaceKeyWords = replaceKeyWords.replace("*", "");// 替换中文双引号
			replaceKeyWords = replaceKeyWords.replace("&", "");// 替换中文双引号
			replaceKeyWords = replaceKeyWords.replace("+", "");// 替换中文双引号
			replaceKeyWords = replaceKeyWords.replace("=", "");// 替换中文双引号
			replaceKeyWords = replaceKeyWords.replace("-", "");// 替换中文双引号
			replaceKeyWords = replaceKeyWords.replace("[", "");// 替换中文双引号
			replaceKeyWords = replaceKeyWords.replace("]", "");// 替换中文双引号
			replaceKeyWords = replaceKeyWords.replace("?", "");// 替换中文双引号
			replaceKeyWords = replaceKeyWords.replace(".", "");// 替换中文双引号
			replaceKeyWords = replaceKeyWords.replace("。", "");// 替换中文双引号
			replaceKeyWords = replaceKeyWords.replace("{", "");// 替换中文双引号
			replaceKeyWords = replaceKeyWords.replace("}", "");// 替换中文双引号
			replaceKeyWords = replaceKeyWords.replace("|", "");// 替换中文双引号
			replaceKeyWords = replaceKeyWords.replace("#", "");// 替换中文双引号
			replaceKeyWords = replaceKeyWords.replace(";", "");// 替换中文双引号
			replaceKeyWords = replaceKeyWords.replace("；", "");// 替换中文双引号
			replaceKeyWords = replaceKeyWords.replace("@", "");// 替换中文双引号
			
			String[] keywords_array = replaceKeyWords.split(",");
			
			List<String> keywordList = new ArrayList<>();
			
			for(int i = keywords_array.length -1 ; i >=0 ; i--) {
				String str = keywords_array[i];
				if(StringUtils.isBlank(str) || ",".equals(str) || "!".equals(str) || "*".equals(str)
						|| "?".equals(str) || ";".equals(str) || ":".equals(str)) {
					continue;
				}
				keywordList.add(str);
			}
			
			return JSONObject.toJSONString(keywordList);
		}
		return null;
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
		File file = new File("D:\\apps\\template\\src\\20181219181857-zdsz2_45593_en\\zdsz2_45593_rec\\2.wav");
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
	
	
	public static Map<String, String> getSimtxtKeywordsByKeyword(List<List<String>> simList, String[] keys){
		Map<String, String> map = new HashMap<>();
		List<String> result = new ArrayList<>();
		if(null != simList && simList.size() > 0) {
			for(int i = 0 ; i < simList.size() ; i++) {
				List<String> line = simList.get(i);
				for(int j = 0 ; j < keys.length ; j++) {
					if(StringUtils.isNotBlank(keys[j]) && line.contains(keys[j])) {//当前行包含该关键词，则返回当前行的关键字列表
						for(int m = 0 ; m < line.size() ; m++) {
							if(map.containsKey(line.get(m))) {
								map.put(line.get(m), map.get(line.get(m)) +  "，" + keys[j]);
							}else {
								map.put(line.get(m), keys[j]);
							}
							//map.put(line.get(m), keys[j]);
						}
					}
				}
			}
		}
		return map;
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
	
	
	/**
	 * 把java对象转换成json字符串，并格式化
	 * @return
	 */
	public static String javaToJson(Object obj, Class type) {
		String jsonStr = JSON.toJSONString(obj);
		try {
			ObjectMapper mapper = new ObjectMapper();
			Object mapperObj = mapper.readValue(jsonStr, type);
		    return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(mapperObj);
		}catch(Exception e) {
			return jsonStr;
		}
	}

	public static int getMediaDuration(File source) throws Exception {
    	//创建媒体对象
       /* MultimediaObject multimediaObject = new MultimediaObject(source);
        //创建媒体信息对象
    	 MultimediaInfo m = multimediaObject.getInfo();*/
       //Long lsL = m.getDuration()/1000;
        //return lsL.intValue();
		return 0;
	}
	
	 /**
     * 获取视频总时间
	 * @throws IOException 
     */
    public static int getVideoTime(String video_path) throws IOException {
    	
    	// 调用python工具
		String os = System.getProperties().getProperty("os.name").toLowerCase();
		if (os.startsWith("win")) {
			logger.info("当前windows系统，不支持获取录音时长...");
			return 0;
		}
    	
    	try {
    		logger.info("开始获取录音时长...");
            List<String> commands = new ArrayList<>();
            commands.add("ffmpeg");
            commands.add("-i");
            commands.add(video_path);
            ProcessBuilder builder = new ProcessBuilder();
            builder.command(commands);
            Process p = builder.start();

            logger.info("执行命令: " + video_path);
            
            //从输入流中读取视频信息
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            StringBuilder stringBuilder = new StringBuilder();
            String line = "";
            while ((line = br.readLine()) != null) {
                stringBuilder.append(line);
            }
            br.close();
            
            logger.info("执行命令结果: " + stringBuilder.toString());
            
            //从视频信息中解析时长
            /*String regexDuration = "Duration: (.*?), bitrate: (\\d*) kb\\/s";
            Pattern pattern = Pattern.compile(regexDuration);
            Matcher m = pattern.matcher(stringBuilder.toString());
            if (m.find()) {
                int time = getTimelen(m.group(1));
                logger.info("解析获取录音时长：" + time + "s" + ", 比特率：" + m.group(2) + "kb/s");
                return time;
            }*/
            //先获取录音时长
    		//StringBuilder stringBuilder = new StringBuilder();
            //stringBuilder = new StringBuilder("ffmpeg version 2.8.15 Copyright (c) 2000-2018 the FFmpeg developers  built with gcc 4.8.5 (GCC) 20150623 (Red Hat 4.8.5-28)  configuration: --prefix=/usr --bindir=/usr/bin --datadir=/usr/share/ffmpeg --incdir=/usr/include/ffmpeg --libdir=/usr/lib64 --mandir=/usr/share/man --arch=x86_64 --optflags='-O2 -g -pipe -Wall -Wp,-D_FORTIFY_SOURCE=2 -fexceptions -fstack-protector-strong --param=ssp-buffer-size=4 -grecord-gcc-switches -m64 -mtune=generic' --extra-ldflags='-Wl,-z,relro ' --enable-libopencore-amrnb --enable-libopencore-amrwb --enable-libvo-amrwbenc --enable-version3 --enable-bzlib --disable-crystalhd --enable-gnutls --enable-ladspa --enable-libass --enable-libcdio --enable-libdc1394 --disable-indev=jack --enable-libfreetype --enable-libgsm --enable-libmp3lame --enable-openal --enable-libopenjpeg --enable-libopus --enable-libpulse --enable-libschroedinger --enable-libsoxr --enable-libspeex --enable-libtheora --enable-libvorbis --enable-libv4l2 --enable-libx264 --enable-libx265 --enable-libxvid --enable-x11grab --enable-avfilter --enable-avresample --enable-postproc --enable-pthreads --disable-static --enable-shared --enable-gpl --disable-debug --disable-stripping --shlibdir=/usr/lib64 --enable-runtime-cpudetect  libavutil      54. 31.100 / 54. 31.100  libavcodec     56. 60.100 / 56. 60.100  libavformat    56. 40.101 / 56. 40.101  libavdevice    56.  4.100 / 56.  4.100  libavfilter     5. 40.101 /  5. 40.101  libavresample   2.  1.  0 /  2.  1.  0  libswscale      3.  1.101 /  3.  1.101  libswresample   1.  2.101 /  1.  2.101  libpostproc    53.  3.100 / 53.  3.100Guessed Channel Layout for  Input Stream #0.0 : monoInput #0, wav, from '/home/botsentence/fileTemp/168716_1-15510664217987672122148787305737wav':  Duration: 00:00:06.18, bitrate: 128 kb/s    Stream #0:0: Audio: pcm_s16le ([1][0][0][0] / 0x0001), 8000 Hz, 1 channels, s16, 128 kb/sAt least one output file must be specified");
            
            String regexDuration = "Duration: (.*?),";
            Pattern pattern = Pattern.compile(regexDuration);
            Matcher m = pattern.matcher(stringBuilder.toString());
            int time = 0;
            if (m.find()) {
                time = getTimelen(m.group(1));
                logger.info("解析获取音频时长：" + time + "s");
                
            }
            
            String regexDuration2 = "bitrate: (\\d*) kb\\/s";
            Pattern pattern2 = Pattern.compile(regexDuration2);
            Matcher m2 = pattern2.matcher(stringBuilder.toString());
            if (m2.find()) {
                logger.info("解析获取音频比特率：" + m2.group(1) + "kb/s");
            }
            
            return time;
    	}catch(Exception e) {
    		logger.error("获取录音时长异常....", e);
    	}
    	return 0;
    }
	
    
 // 格式:"00:00:10.68"
    public static int getTimelen(String timelen) {
        int min = 0;
        String strs[] = timelen.split(":");
        if (strs[0].compareTo("0") > 0) {
            // 秒
            min += Integer.valueOf(strs[0]) * 60 * 60;
        }
        if (strs[1].compareTo("0") > 0) {
            min += Integer.valueOf(strs[1]) * 60;
        }
        if (strs[2].compareTo("0") > 0) {
            min += Math.round(Float.valueOf(strs[2]));
        }
        return min;
    }
    
    
    /**
	 * 遍历加密
	 * @param file
	 */
	public static void fileCrypter(File file){
		if(file.isDirectory()){
			File[] files=file.listFiles();
			for(File fileItem:files){
				fileCrypter(fileItem);
			}
		}else{
			String fileName=file.getName();
			String path = file.getPath();
			//if(path.indexOf("new_domain_cfg") > 0) {
				if(fileName.endsWith("json")){
					System.out.println(fileName);
					JsonBase64Crypter.encodeFile(file);
				}
			//}
		}
	}
	
	/**
	 * 根据字符串返回字符串列表
	 * @param str
	 * @return
	 */
	public static List<String> StringToList(String str){
		List<String> list = new ArrayList<>();
		if(StringUtils.isNotBlank(str)) {
			str = str.replace("，", ",");// 替换中文逗号
			str = str.replace("\n", "");// 替换换行符
			str = str.replace("\r", "");// 替换换行符
			String[] array = str.split(",");
			for(String temp : array) {
				if(StringUtils.isNotBlank(temp)) {
					list.add(temp.trim());
				}
			}
			
		}
		return list;
	}
	
	/**
	 * 根据字符串列表返回以逗号分隔的字符串
	 * @return
	 */
	public static String listToString(List<String> list) {
		String result = "";
		if(null != list && list.size() > 0) {
			for(String str : list) {
				result = result + "," + str;
			}
			result = result.substring(1, result.length());
		}
		return result;
	}
	
	/**
	 * 把java对象转换成json字符串，并格式化，根据模板要求并且替换某些关键字或者汉字
	 * @return
	 * @throws  
	 * @throws Exception 
	 */
	public static String formatJavaToJson(Object obj, Class type) throws Exception {
		String jsonStr = JSON.toJSONString(obj);

		List<String> formatList = new ArrayList<>();
		
		Map<String, String> map = new HashMap<>();
		
		Field [] fields = JSONConstant.class.getDeclaredFields();
		if(null != fields && fields.length > 0) {
			for(Field field : fields) {
				String name = field.getName();
				if(name.indexOf(JSONConstant.SUFFIX) > 0) {
					formatList.add(name);
				}
				
				map.put(name, (String)field.get(obj));
			}
		}
		
		
		
		try {
			ObjectMapper mapper = new ObjectMapper();
			Object mapperObj = mapper.readValue(jsonStr, type);
		    String result = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(mapperObj);
		    
		    
		    for(String name : formatList) {
				String regex="\"" + name  +  "\"";//使用非贪婪模式
				//System.out.println(regex);
				Matcher matcher=Pattern.compile(regex).matcher(result);  
				while(matcher.find())  
				{
					result = result.replaceAll(regex, "\""+map.get(name)+"\"");
				}
			}
		    return result;
		    
		}catch(Exception e) {
			e.printStackTrace();
			return jsonStr;
		}
	}
	
	 public static String formatJson(String jsonStr) {
	        if (null == jsonStr || "".equals(jsonStr)) return "";
	        //jsonStr = jsonStr.replace(",", "，");
	        StringBuilder sb = new StringBuilder();
	        char last = '\0';
	        char current = '\0';
	        int indent = 0;
	        for (int i = 0; i < jsonStr.length(); i++) {
	            last = current;
	            current = jsonStr.charAt(i);
	            switch (current) {
	                case '{':
	                case '[':
	                    sb.append(current);
	                    sb.append('\n');
	                    indent++;
	                    addIndentBlank(sb, indent);
	                    break;
	                case '}':
	                case ']':
	                    sb.append('\n');
	                    indent--;
	                    addIndentBlank(sb, indent);
	                    sb.append(current);
	                    break;
	                case ',':
	                	/*sb.append(current);
		        		if (last == '"' || last == ']' || last == '}') {
		                     sb.append('\n');
		                     addIndentBlank(sb, indent);
		                 }*/
	                	sb.append(current);
		                sb.append('\n');
		                addIndentBlank(sb, indent);
	                    break;
	                default:
	                    sb.append(current);
	            }
	        }

	        return sb.toString();
	    }
	 
	 /**
	     * 添加space
	     * @param sb
	     * @param indent
	     */
	    private static void addIndentBlank(StringBuilder sb, int indent) {
	        for (int i = 0; i < indent; i++) {
	            sb.append('\t');
	        }
	    }
}
