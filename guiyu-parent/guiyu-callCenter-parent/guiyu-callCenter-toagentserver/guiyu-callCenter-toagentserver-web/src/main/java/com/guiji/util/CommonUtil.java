package com.guiji.util;

import com.guiji.fs.xmlpojo.XCallCenter;
import com.google.common.base.Charsets;
import com.google.common.base.Strings;
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.core.io.ClassPathResource;
import sun.net.util.IPAddressUtil;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.net.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtil {
	static Logger log = LoggerFactory.getLogger(CommonUtil.class);
	/**
	 * 执行shell命令，并返回结果
	 */
	public static String doShCommand(String cmd) throws IOException {
		String[] cmdList = null;
		cmdList = new String[]{"/bin/sh", "-c", cmd};

		Process process = Runtime.getRuntime().exec(cmdList);
		BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream(), "UTF-8"));

		String str;
		StringBuilder builder = new StringBuilder();
		while ((str = stdInput.readLine()) != null) {
			builder.append(str);
		}

		return builder.toString();
	}
	/**
	 * 随机生成一个新的
	 * @return
	 */
	public static String RandomNewToken()
	{
		return  UUID.randomUUID().toString();
	}

    /**
     * xml to javabean
     *
     * @param file 输入文件
     * @param type 要转成的java类型
     */
    public static <T> T xmlToBean(File file, Type type){
        T obj = null;
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance((Class)type);
            Unmarshaller unmarshaller= jaxbContext.createUnmarshaller();
            obj = (T)unmarshaller.unmarshal(file);
        } catch (JAXBException e) {
            log.warn("将xml转为java出现异常" + file.getAbsolutePath(), e);
        }

        return  obj;
    }

	/**
	 * xml to javabean
	 *
	 * @param xmlContent 输入文件
	 * @param type 要转成的java类型
	 */
	public static <T> T xmlToBean(String xmlContent, Type type){
		T obj = null;
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance((Class)type);
			Unmarshaller unmarshaller= jaxbContext.createUnmarshaller();
			StringReader reader = new StringReader(xmlContent);
			obj = (T)unmarshaller.unmarshal(reader);
		} catch (JAXBException e) {
			log.warn("将xml转为java出现异常", e);
		}

		return  obj;
	}


	public static <T> T toObj(Class<T> clazz,InputStream buf) {
		try {
			JAXBContext context = JAXBContext.newInstance(new Class[] { clazz });
			return (T) context.createUnmarshaller().unmarshal(buf);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
            try {
                buf.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
		return null;
	}


    /**
     * bean转xml
     * @param obj  java对象
     * @param file 输出文件
     * @return
     */
    public static void beanToXML(Object obj, File file){
        Marshaller jaxbMarshaller = null;
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(obj.getClass());
            jaxbMarshaller = jaxbContext.createMarshaller();

            // output pretty printed
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            jaxbMarshaller.marshal(obj, file);
            //jaxbMarshaller.marshal(obj, System.out);
        } catch (JAXBException e) {
            log.warn("将java转为xml出现异常" + file.getAbsolutePath(), e);
        }
    }


    /**
     * json to javabean
     *
     * @param json
     */
	public static <T> T jsonToJavaBean(String json,Type type){
        Gson gson = new Gson();
        T obj = gson.fromJson(json,type);
        return  obj;
	}

	public static<T> T mapToJavaBean(Map<String,String> map, Type type){
        Gson gson = new Gson();
        JsonElement jsonElement = gson.toJsonTree(map);
        T pojo = gson.fromJson(jsonElement, type);
        return pojo;
    }

	/**
	 * bean转json
	 * @param obj
	 * @return
	 */
	public  static String  beanToJson(Object obj){
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		return gson.toJson(obj);
	}

    /**
     * bean 转化 Map
     * @param bean
     * @return
     */
    public static HashMap<String,Object> beanToMap(Object bean){
        HashMap<String,Object> map = new HashMap<String,Object>();
        if(null == bean){
            return map;
        }
        Class<?> clazz = bean.getClass();
        BeanInfo beanInfo;
        try {
            beanInfo = Introspector.getBeanInfo(clazz);
        } catch (IntrospectionException e) {
            e.printStackTrace();
			return map;
        }

        PropertyDescriptor[] descriptors = beanInfo.getPropertyDescriptors();
        for(PropertyDescriptor descriptor : descriptors){
            String propertyName = descriptor.getName();
            if(!"class".equals(propertyName)){
                Method method = descriptor.getReadMethod();
                Object result;
                try {
                    result = method.invoke(bean);
                    if(null != result){
                        map.put(propertyName, result);
                    }else{
                        map.put(propertyName, "");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return map;
    }

    /**
	 * 解析url查询字符串，并以map形式返回结果，如 name=weichi&age=17&school=bqxz 
	 * @param query
	 * @return
	 */
	public static Map<String, String> parseQuery(String query) {
	    Map<String, String> query_pairs = new LinkedHashMap<String, String>();
	    String[] pairs = query.split("&");
	    for (String pair : pairs) {
	        int idx = pair.indexOf("=");
	        try {
				query_pairs.put(URLDecoder.decode(pair.substring(0, idx), "UTF-8"), URLDecoder.decode(pair.substring(idx + 1), "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				log.error("在解析url查询字符串时出现异常", e);
			}
	    }
	    
	    return query_pairs;
	}
	
	/**
	 * 根据正则表达式，从字符串中截取需要的值
	 * 
	 * @param srcString
	 *            源字符串
	 * @param regex
	 *            正则表达式
	 * @param count
	 *            需要截取的字符串数量,即正则表达式中括号的个数
	 * @return
	 */
	public static List<String> getGroupStringByRegex(String srcString, String regex, int count) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(srcString);
		ArrayList<String> resultList = new ArrayList<String>();
		while (matcher.find()) {
			for (int i = 1; i < count + 1; i++) {
				String str = matcher.group(i);
				if (!isNullOrEmpty(str)) {
					resultList.add(str);
				}
			}
		}

		return resultList;
	}

	/**
	 * 判断原始字符串的头尾是否含有指定的字符，如果含有，则去掉。
	 * 
	 * @param strOriginal
	 *            原始字符串
	 * @param strSurround
	 *            用于包裹原始字符串的字符
	 * @return
	 */
	public static String trimEx(String strOriginal, String strSurround) {
		if (!isNullOrEmpty(strOriginal)) {
			if (strOriginal.startsWith(strSurround)) {
				strOriginal = strOriginal.substring(1);
			}

			if (strOriginal.endsWith(strSurround)) {
				strOriginal = strOriginal.substring(0, strOriginal.length() - 1);
			}
		}

		return strOriginal;
	}

	/**
	 * 判断字符串是否为数字类型
	 * 
	 * @param number
	 * @return
	 */
	public static boolean isNumber(String number) {
		boolean result = false;

		if (number != null && number.matches("\\d*")) {
			result = true;
		}

		return result;
	}

	/**
	 * 判断字符串是否是外部号码
	 * 
	 * @param number
	 * @return
	 */
	public static boolean isOuterNumber(String number) {
		boolean result = false;
		if (number.length() >= 8 && isNumber(number)) {
			result = true;
		}

		return result;
	}

	/**
	 * 判断字符串是否为空（空变量、或空字符）
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNullOrEmpty(String str) {
		return str == null || str.isEmpty() || str.trim().length() == 0 ? true : false;
	}

	/**
	 * 判断传入的一系列的字符串是否含有空串，如果有则返回true，否则返回false
	 * 
	 * @param list
	 * @return
	 */
	public static boolean isNullOrEmpty(String... list) {
		if (list == null || list.length == 0) {
			return true;
		}

		boolean result = false;
		for (String str : list) {
			if (isNullOrEmpty(str)) {
				result = true;
			}
		}

		return result;
	}
	
	/**
	 * 获取当前方法的调用者信息,便于定位问题
	 * @return
	 */
	public static String getCallerInfo(){
		StringBuilder builder = new StringBuilder();
        StackTraceElement stack[] = Thread.currentThread().getStackTrace();  
        builder.append("called by ");
        for (StackTraceElement ste:stack){   
        	 builder.append("..." + ste.getClassName()+"."+ste.getMethodName());
        }
        
        return builder.toString();
	}

	/**
	 * 打印异常堆栈中的信息到字符串中，以便调试
	 * 
	 * @param t
	 * @return
	 */
	public static String getStackTrace(Throwable t) {
		String result = "";

		StringWriter sw;
		PrintWriter pw;
		try {
			sw = new StringWriter();
			pw = new PrintWriter(sw, true);
			t.printStackTrace(pw);
			pw.flush();
			sw.flush();
			result = sw.toString();
			pw.close();
			sw.close();
			pw = null;
			sw = null;
		} catch (IOException e) {
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * 获取对象中值为空的属性名称列表
	 * @param source
	 * @return
	 */
	public static String[] getNullPropertyNames (Object source) {
		final BeanWrapper src = new BeanWrapperImpl(source);
		PropertyDescriptor[] pds = src.getPropertyDescriptors();

		Set<String> emptyNames = new HashSet<String>();
		for(PropertyDescriptor pd : pds) {
			Object srcValue = src.getPropertyValue(pd.getName());
			if (srcValue == null) emptyNames.add(pd.getName());
		}
		String[] result = new String[emptyNames.size()];
		return emptyNames.toArray(result);
	}

	/**
	 * 通过hashmap中的值获取键
	 *
	 * @param value
	 * @return
	 */
	public static String getKeyFromHashMapByValue(Map<String, String> map, String value) {
		for (Map.Entry<String, String> entry : map.entrySet()) {
			String key = entry.getKey();
			if (value.equals(entry.getValue())) {
				return key;
			}
		}

		return null;
	}


	/**
	 * 获取git最新的commitid和提交日志,返回结果保存到数组中，Array[0]保存的是id，Array[1]保存的是提交日志
	 * @param gitPath
	 * @return
	 * 			String[0]	commitid
	 * 			String[1]	commit log
	 * 			String[2]	commit time
	 * 			String[3]	commit user
	 * @throws IOException
	 */
	public static String[] getGitCommitIdAndLog(String gitPath) throws IOException{
		//构建获取cmd命令，其中以&&分隔连续执行cmd命令，出现错误停止执行
		String cmd = String.format("%s&&cd %s&&git --no-pager log -1 --pretty=format:%%H:_:%%s:_:%%ci:_:%%an --encoding=UTF-8", gitPath.substring(0,2), gitPath);
		String result = CommonUtil.doCommand(cmd);
		if(!Strings.isNullOrEmpty(result) && result.indexOf(":_:")>0){
			String[] resultArray = result.split(":_:");
			return resultArray;
		}else{
			log.warn("获取git提交信息命令[{}]失败，结果为[{}]", cmd, result);
		}

		return null;
	}

	/**
	 * 获取项目实际运行路径
	 * @param servletContext
	 * @return
	 */
	public static String getProjectPath(ServletContext servletContext){
		String projectPath = servletContext.getRealPath("/");
		if(projectPath.indexOf("\\") > 0){
			projectPath = projectPath.replace("\\","\\\\");
		}
		return projectPath;
	}

	/**
	 * 检查指定端口是否开启
	 *
	 * @param ip
	 *            目标机器IP
	 * @param port
	 *            需要检查的端口
	 * @return
	 */
	public static boolean portIsOpen(String ip, int port) {
		Socket socket = null;
		String reason = "";
		try {
			socket = new Socket();
			socket.setReuseAddress(true);
			socket.connect(new InetSocketAddress(ip, port), 2000);
			return true;
		} catch (IOException  e) {
            if ( e.getMessage().equals("Connection refused")) {
                reason = "port " + ip + " on " + port + " is closed.";
            };
            if ( e instanceof UnknownHostException ) {
                reason = "node " + ip + " is unresolved.";
            }
            if ( e instanceof SocketTimeoutException ) {
                reason = "timeout while attempting to reach node " + ip + " on port " + port;
            }
            
            if(CommonUtil.isNullOrEmpty(reason)){
            	reason = e.getMessage();
            }
		} finally{
			if(socket!=null){
				try {
					socket.close();
				} catch (IOException e) {
				}
			}
		}

		log.error("远程端口访问失败，原因为" + reason);
		return false;
	}

	private static Boolean blnisWindows = null;
	/**
	 * 判断当前操作系统是不是window
	 * 
	 * @return boolean
	 */
	public static boolean isWindows() {
		if(blnisWindows == null){
			if (System.getProperties().getProperty("os.name").toUpperCase().indexOf("WINDOWS") != -1) {
				blnisWindows = true;
			}else{
				blnisWindows = false;
			}
		}
		
		return blnisWindows;
	}
	
	/**
	 * 获取配置文件路径
	 * @param classz
	 * @param filename
	 * @return
	 */
	public static String getConfigFilePath(Class<?> classz,String filename) {
		URL url = classz.getClassLoader().getResource(filename);
		String configPath = null;
		if (url != null && url.getProtocol().equals("jar")) {
			File file = new File(".");
			if (file.exists()) {
				try {
					configPath = file.getCanonicalPath() + File.separator + filename;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}else if(url!=null){
			configPath = url.getPath().replaceAll("%20", " ");
			if (isWindows()) {
				if(configPath.startsWith("/")){
					configPath = configPath.substring(1);
				}
				configPath = configPath.toLowerCase();
			}
		}
		
		return configPath;
	}
	
	public static String getLocalIP() {
		Enumeration<NetworkInterface> en;
		try {
			en = NetworkInterface.getNetworkInterfaces();
			while (en.hasMoreElements()) {
				NetworkInterface i = (NetworkInterface) en.nextElement();
				//如果发现网卡为虚拟网卡，则跳过
				String name = i.getName();
				if(name!=null && name.contains("virbr")){
					continue;
				}
				for (Enumeration<InetAddress> en2 = i.getInetAddresses(); en2.hasMoreElements();) {
					InetAddress addr = (InetAddress) en2.nextElement();
					if (!addr.isLoopbackAddress()) {
						if (addr instanceof Inet4Address) {
							return addr.getHostAddress();
						}
					}
				}
			}
		} catch (SocketException e) {
			log.warn("获取本地ip出现异常", e);
		}
		
		return null;
	}
	

	/**
	 * 去除字符串列表中重复的字符串
	 * @param mylist
	 * @return
	 */
	public static List<String> removeDuplicteStrFromList(List<String> mylist){
		if(mylist == null || mylist.size() == 0){
			return mylist;
		}
		
		Set<String> myset = new HashSet<String>(mylist);
		return new ArrayList<String>(myset);
	}

	/**
	 * 从资源文件中读取json文件，并转为相应的对象
	 * @param filePath
	 * @param c
	 * @param <T>
	 * @return
	 * @throws IOException
	 */
	public static <T> T readJsonFileAsObject(String filePath, Class<T> c) throws IOException {
		File file = new ClassPathResource(filePath).getFile();
		String content = Files.toString(file, Charsets.UTF_8);
		T response = CommonUtil.jsonToJavaBean(content, c);
		return response;
	};

	
	
	/**
	 * 比较两个号码，比较时忽略掉号码前面的所有0
	 * @param number
	 * @param number2
	 * @return
	 */
	public static boolean equalIngoreZero(String number, String number2) {
		if(number.startsWith("0")){
			number = RegexUtil.getStringByRegex(number, "^0*(\\d*)$");
		}
		
		if(number2.startsWith("0")){
			number2 = RegexUtil.getStringByRegex(number2, "^0*(\\d*)$");
		}
		
		if(number.equals(number2)){
			return true;
		}
		
		return false;
	}
	
	/**
	 * 测试本地端口是否开启，不管是udp还是tcp
	 * @param port
	 * @return
	 */
	public static boolean isLocalPortOpen(int port){
		String cmd = null;
		if(isWindows()){
			cmd = String.format("netstat -ano|findstr  \":%s\\>\"", port);
		}else{
			cmd = String.format("netstat -anp|grep \":%s\\>\"", port);
		}
		
		String result = null;
		try {
			result = doCommand(cmd);
			//log.info("命令[{}],端口占用信息为：[{}]", cmdList, result);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(result!=null && result.contains(String.valueOf(port))){
			return true;
		}
		return false;
	}
	
	/**
	 * 执行系统命令，并返回结果
	 */
	public static String doCommand(String cmd) throws IOException {
		String[] cmdList = null;
		if(isWindows()){
			cmdList = new String[]{"cmd", "/c", cmd};
		}else{
			cmdList = new String[]{"/bin/sh", "-c", cmd};
		}
		
		Process process = Runtime.getRuntime().exec(cmdList);
		BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream(), "UTF-8"));
		
		String str;
		StringBuilder builder = new StringBuilder();
		while ((str = stdInput.readLine()) != null) {
			builder.append(str);
		}
		
		return builder.toString();
	}

	/**
	 * 获取客户端ip
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	
	/**
	 * 判断IP地址为内网IP还是公网IP
	 * @param ip
	 * @return
	 */
	public static boolean isInternalIp(String ip) {
		byte[] addr = IPAddressUtil.textToNumericFormatV4(ip);
		return internalIp(addr);
	}

	public static boolean internalIp(byte[] addr) {
		final byte b0 = addr[0];
		final byte b1 = addr[1];
		// 10.x.x.x/8
		final byte SECTION_1 = 0x0A;
		// 172.16.x.x/12
		final byte SECTION_2 = (byte) 0xAC;
		final byte SECTION_3 = (byte) 0x10;
		final byte SECTION_4 = (byte) 0x1F;
		// 192.168.x.x/16
		final byte SECTION_5 = (byte) 0xC0;
		final byte SECTION_6 = (byte) 0xA8;
		switch (b0) {
		case SECTION_1:
			return true;
		case SECTION_2:
			if (b1 >= SECTION_3 && b1 <= SECTION_4) {
				return true;
			}
		case SECTION_5:
			switch (b1) {
			case SECTION_6:
				return true;
			}
		default:
			return false;
		}
	}
	
	/**
	 * 把List<String>转化为以","隔开的字符串
	 * @param stringList
	 * @return
	 */
	 public static String listToString(List<String> stringList){
	        if (stringList==null) {
	            return null;
	        }
	        StringBuilder result=new StringBuilder();
	        boolean flag=false;
	        for (String string : stringList) {
	            if (flag) {
	                result.append(",");
	            }else {
	                flag=true;
	            }
	            result.append(string);
	        }
	        return result.toString();
	    }

	public  static  void  init(Object source,Object target) throws IllegalAccessException {
		Class c_source = source.getClass();
		List<Field> f_source = Arrays.asList(c_source.getDeclaredFields());

		Class   c_target = target.getClass();
		List<Field> t_target = Arrays.asList(c_target.getDeclaredFields());


		for (Field s_field:f_source) {
			for (Field t_field:t_target) {
				if(s_field.getName().equals(t_field.getName())) {
					t_field.setAccessible(true);
					s_field.setAccessible(true);
					if(s_field.get(source) != null)
					t_field.set(target,s_field.get(source));
				}
			}
		}
	}

	public static <T> List<T> copyIterator(Iterator<T> iter) {
		List<T> copy = new ArrayList<T>();
		while (iter.hasNext())
			copy.add(iter.next());
		return copy;
	}



	public static void main1(String[] args) throws IllegalAccessException {
		//System.out.println("端口开启情况19700：" + isLocalPortOpen(19700));
		//System.out.println("端口开启情况1970：" + isLocalPortOpen(1970));
		//System.out.println("端口开启情况445：" + isLocalPortOpen(445));
		//
		//System.out.println("本地ip地址：" + getLocalIP());
		//
		//try {
		//	String[] rtArray = getGitCommitIdAndLog("d:\\git\\egooucserver\\");
		//	for(int i=0;i<rtArray.length;i++){
		//		System.out.println("key:" + i + ",  value:" + rtArray[i]);
		//	}
		//} catch (IOException e) {
		//	e.printStackTrace();
		//}

//        File file = new File("/usr/local/freeswitch/conf/autoload_configs/event_socket.conf.xml");
//        XConfiguration config = xmlToBean(file, XConfiguration.class);
//        System.out.println(config);
//
//        config.getSettings().getParam()[1].setValue("0000000000000000");
//        File out = new File("/Users/toolwiz.com/Desktop/test2.xml");
//		beanToXML(config, out);

//        XConfiguration configuration = new XConfiguration();
//        configuration.setDescription("this is my desp");
//        configuration.setName("wchi");
//        XSettings settings = new XSettings();
//        XParam param1 = new XParam("name", "hahaha");
//		XParam param2 = new XParam("number", "18652003060");
//		XParam[] ps = {param1, param2};
//		settings.setParam(ps);
//		configuration.setSettings(settings);
//
//        File file2 = new File("/Users/toolwiz.com/Desktop/test.xml");
//        beanToXML(configuration, file2);


//		File file = new File("/Users/toolwiz.com/tmp/fs1/conf/directory/default/1000.xml");
//		XUserInclude user = xmlToBean(file, XUserInclude.class);
//		System.out.println(user);
//
//		user.getUser().getVariables().getVariable()[1].setValue("99999999999999999");
//		beanToXML(user, file);
//
//		File file = new File("/Users/toolwiz.com/tmp/fs1/conf/autoload_configs/callcenter.conf.xml");
//        XCallCenter cc = xmlToBean(file, XCallCenter.class);
//
//        File out = new File("/Users/toolwiz.com/tmp/fs1/conf/autoload_configs/callcenter.conf111.xml");
//        beanToXML(cc, out);
	}
}
