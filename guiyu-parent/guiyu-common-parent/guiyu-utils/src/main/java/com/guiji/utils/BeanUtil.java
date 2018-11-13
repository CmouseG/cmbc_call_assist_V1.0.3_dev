package com.guiji.utils;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * Bean2Map/map2Bean类
 * 按商盟接口要求进行转换
 *
 * @author 	yunbo.wei
 * @date 	2016年11月15日
 * @Copyright 
 *
 * <pre>
 * =================Modify Record=================
 * Modifier			date				Content
 * yunbo.wei		2016年11月15日			新增
 *
 * </pre>
 */
public class BeanUtil extends org.springframework.beans.BeanUtils{
	private static final Logger logger = LoggerFactory.getLogger(BeanUtil.class);
	private static Pattern linePattern = Pattern.compile("_(\\w)"); 
	
	/**
	 * 将bean对象中的属性和值转为Map，因为json传输，所以全部转为string
	 * @param javaBean
	 * @param patten 转换的类型
	 * 目前支持两种类型，转为map后的key，一种同属性名称，另一种驼峰中间以下划线“_”分隔
	 * 如：userName --- user_name
	 * @return
	 */
	@SuppressWarnings("unchecked")  
    public static <K, V> Map<K, V> bean2MapForSumpay(Object javaBean,String patten) {  
        Map<K, V> ret = new HashMap<K, V>();  
        try {  
            Method[] methods = javaBean.getClass().getDeclaredMethods();  
            for (Method method : methods) {  
                if (method.getName().startsWith("get")) {  
                    String field = method.getName();  
                    field = field.substring(field.indexOf("get") + 3);  
                    field = field.toLowerCase().charAt(0) + field.substring(1);  
                    Object value = method.invoke(javaBean, (Object[]) null);  
                    if("_".equals(patten)){
                    	//下划线分隔
                    	ret.put((K) humpToLine2(field), (V) (null == value ? "" : value));  
                    }else{
                    	ret.put((K)field, (V) (null == value ? "" : value));  
                    }
                    
                }  
            }  
        } catch (Exception e) {  
        }  
        return ret;  
    }
	
	/**
	 * 将bean对象中的属性和值转为Map，因为json传输，所以全部转为string
	 * @param javaBean
	 * @return
	 */
	@SuppressWarnings("unchecked")  
    public static <K, V> Map<K, V> bean2Map(Object javaBean) {  
        return bean2MapForSumpay(javaBean,null);
    }
	
	
	/**
	 * 将map中的键值对转为对象，因为json传输，所以全部转为string
	 * @param mp map
	 * @param beanCls 要转的class 
	 * @param patten 转换的类型
	 * 目前支持两种类型，转为map后的key，一种同属性名称，另一种驼峰中间以下划线“_”分隔
	 * 如：user_name --- userName
	 * @return
	 */
	@SuppressWarnings("unchecked")  
	public static <T, K, V> T map2BeanForSumpay(Map<K, V> mp, Class<T> beanCls,String patten){
	    T t = null;
	    try {
	    	
	        BeanInfo beanInfo = Introspector.getBeanInfo(beanCls);
	        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
	        t = beanCls.newInstance();
	        for (PropertyDescriptor property : propertyDescriptors) {
	            String key = property.getName();
	            if("_".equals(patten)){
	            	//map中间是以下划线分隔
	            	String key2 = humpToLine2(key);
	            	if (mp.containsKey(key2)) {
		                Object value = mp.get(key2);
		                Method setter = property.getWriteMethod();// Java中提供了用来访问某个属性的
		                setter.invoke(t, value);
		            }
	            }else{
	            	if (mp.containsKey(key)) {
		                Object value = mp.get(key);
		                Method setter = property.getWriteMethod();// Java中提供了用来访问某个属性的
		                setter.invoke(t, value);
		            }
	            }
	            
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return t;
	}
	
	/**
	 * 支持多层结构转换
	 * @param map
	 * @param bean
	 * @param propertyNamingStrategyType
	 * @return
	 */
	public static <T> T map2Bean4MultiLayers(Map<String,Object> map,T bean, int propertyNamingStrategyType){
		if(bean == null){
			return null;
		}
		Object obj = map2Bean4MultiLayers(map,bean.getClass(),propertyNamingStrategyType);
		org.springframework.beans.BeanUtils.copyProperties(obj, bean);
		return bean;
	}
	
	/**
	 * 支持多层结构转换(默认3层结构)
	 * @param map
	 * @param cls
	 * @return
	 */
	public static <T> T map2Bean4MultiLayers(Map<String,Object> map,Class<T> cls){
		return (T) map2Bean4MultiLayers(map,cls,3);
	}
	
	/**
	 * 将List转为map
	 * @param list
	 * @param cls
	 * @return
	 */
	public static List map2List(List<Map> list,Class cls){
		if(list == null || cls == null){
			return null;
		}
		List newList = new ArrayList();
		for(Map map : list){
			newList.add(map2Bean(map,cls));
		}
		return newList;
	}
	
	
	
	/**
	 * 将map中的键值对转为对象
	 * @param map map
	 * @param cls 要转的class
	 * @return
	 */
	@SuppressWarnings("unchecked")  
	public static <T> T map2Bean(Map map,Class<T> cls){
		if(map == null || cls ==null){
			return null;
		}
		if(null != cls.getName() && cls.isAssignableFrom(Map.class)){
			return (T)map;
		}
		T obj = null;
		try{
			obj = cls.newInstance();
		}catch(InstantiationException e){
			logger.error(e.getMessage(),e);
			throw new RuntimeException("BeanUtil.map2Bean出错，无法实例化"+cls.getName(),e);
		} catch (IllegalAccessException e) {
			logger.error(e.getMessage(),e);
			throw new RuntimeException("BeanUtil.map2Bean出错，无法实例化"+cls.getName(),e);
		}
		T t = (T) mapToBean(map,obj);
		return t;
	}
	
	
	/**
	 * 重写mapTo2Bean（内部使用）
	 * @param sourceMap
	 * @param toObject
	 * @return
	 */
	private static <T> T mapToBean(Map<String,?> sourceMap,T toObject){
		if(toObject == null || sourceMap ==null){
			return null;
		}
		Map<String,Object> newMap = new LinkedHashMap<String, Object>(sourceMap.size());
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat simpleTimeFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		Field field[] = toObject.getClass().getDeclaredFields();
		for(Entry<String, ?> entry : sourceMap.entrySet()){
			if(entry == null){
				continue;
			}
			String fieldName = entry.getKey();
			Object value = entry.getValue();
			if(value != null){
				for(int j=0;j<field.length;j++){
					if(fieldName.equals(field[j].getName())){
						/**类型转换**/
						Class<?> fieldType = field[j].getType();
						if(fieldType.equals(java.math.BigDecimal.class)){
							if(value instanceof String){
								//若是String
								if(StrUtils.isNotEmpty(String.valueOf(value))){
									value = new java.math.BigDecimal(String.valueOf(value));
								}else{
									value = null;
								}
							}else{
								try{
									if (null != value) {
										value = new java.math.BigDecimal(value.toString());
									}
								}catch(NumberFormatException e){
									if(null == value) {
										value = "";
									}
									illegalValue(entry.getKey(),value,java.math.BigDecimal.class,null,e);
								}
							}
						}else if(fieldType.equals(java.util.Date.class)){
							if(value instanceof String){
								if(StrUtils.isNotEmpty(String.valueOf(value))){
									try{
										value = new java.util.Date(simpleDateFormat.parse((String)value).getTime());
									}catch(ParseException e){
										if(null == value) {
											value = "";
										}
										illegalValue(entry.getKey(),value,java.util.Date.class,null,e);
									}
								}else{
									value = null;
								}
							}else{
								if(null == value) {
									value = "";
								}
								illegalValue(entry.getKey(),value,java.sql.Date.class,null,null);
							}
						}else if(fieldType.equals(java.sql.Date.class)){
							if(value instanceof String){
								if(StrUtils.isNotEmpty(String.valueOf(value))){
									try{
										value = new java.sql.Date(simpleDateFormat.parse((String)value).getTime());
									}catch(ParseException e){
										if(null == value) {
											value = "";
										}
										illegalValue(entry.getKey(),value,java.sql.Date.class,null,e);
									}
								}else{
									value = null;
								}
							}else if(value instanceof java.util.Date){
								if (null != value) {
									value = new java.util.Date(((java.util.Date)value).getTime());
								}
							}else{
								if(null == value) {
									value = "";
								}
								illegalValue(entry.getKey(),value,java.sql.Date.class,null,null);
							}
						}else if(fieldType.equals(java.sql.Timestamp.class)){
							//时间戳timestamp
							if(value instanceof String){
								if(StrUtils.isNotEmpty(String.valueOf(value))){
									try{
										if(((String)value).length() == 8){
											//yyyyMMdd
											value = new java.sql.Timestamp(simpleDateFormat.parse((String)value).getTime());
										}else{
											value = new java.sql.Timestamp(simpleTimeFormat.parse((String)value).getTime());
										}
									}catch(ParseException e){
										if(null == value) {
											value = "";
										}
										illegalValue(entry.getKey(),value,java.sql.Date.class,null,e);
									}
								}else{
									value = null;
								}
							}else if(value instanceof java.util.Date){
								if (null != value) {
									value = new java.sql.Timestamp(((java.util.Date)value).getTime());
								}
							}else{
								if(null == value) {
									value = "";
								}
								illegalValue(entry.getKey(),value,java.sql.Timestamp.class,null,null);
							}
						}else if(fieldType.equals(String.class)){
							//String
							if(StrUtils.isEmpty(String.valueOf(value))){
								value = "";
							}
						}else if(fieldType.equals(Long.class)){
							//Long
							try{
								if(value instanceof String){
									if (null != value) {
										value = value.toString();
										if(StrUtils.isNotEmpty(String.valueOf(value))){
											value = Long.valueOf((String)value);
										}else{
											value = null;
										}
									}
								}else{
									//容错处理，尝试其他类型
									if (value != null) {
										value = Long.valueOf(value.toString());
									}
								}
							}catch(NumberFormatException e){
								if(null == value) {
									value = "";
								}
								illegalValue(entry.getKey(),value,Long.class,null,e);
							}
						}else if(fieldType.equals(Integer.class)){
							//Integer
							if(value instanceof String){
								if(StrUtils.isNotEmpty(String.valueOf(value))){
									value = Integer.valueOf(String.valueOf(value));
								}else{
									value = null;
								}
							}else{
								//其他容错处理，尝试其他类型
								try{
									value = Integer.valueOf(String.valueOf(value));
								}catch(NumberFormatException e){
									if(null == value) {
										value = "";
									}
									illegalValue(entry.getKey(),value,Integer.class,null,e);
								}
							}
						}else if(fieldType.equals(Boolean.class)){
							//Boolean
							if(value instanceof String){
								if(StrUtils.isNotEmpty(String.valueOf(value))){
									value = Boolean.valueOf((String)value);
								}else{
									value = null;
								}
							}else{
								//尝试其他容错
								if (null != value) {
									value = Boolean.valueOf(value.toString());
								}
							}
						}else if(fieldType.equals(List.class)){
							//LIST处理
							//开始获取LIST的泛型类型
							if(value != null){
								Type fc = field[j].getGenericType(); // 关键的地方，如果是List类型，得到其Generic的类型  
								if(fc == null) continue;  
								if(fc instanceof ParameterizedType) // 【3】如果是泛型参数的类型   
								{   
									ParameterizedType pt = (ParameterizedType) fc;  
									Class genericClazz = (Class)pt.getActualTypeArguments()[0]; //【4】 得到泛型里的class类型对象。  
									if(value instanceof List){
										try {
											if(genericClazz.getName().startsWith("com.")){
												value = map2List((List<Map>)value, genericClazz);
											}else{
												//其他单类型，如String,int,Double等等
												//默认仍然为value，可以直接返回
											}
										} catch (ClassCastException e) {
											throw new RuntimeException("List 参数转换异常，fieldName:"+field[j].getName());
										}
									}else{
										throw new RuntimeException("List 参数转换异常，fieldName:"+field[j].getName());
									}
								}
							}
						}
						else{
							//其他类型，只能尝试转map，支持多层嵌套
							if(value instanceof Map){
								value = map2Bean((Map<String,?>)value, fieldType);
							}
						}
					}
				}
			}
			if(value != null){
				newMap.put(fieldName, value);
			}
		}
		try{
			org.apache.commons.beanutils.BeanUtils.copyProperties(toObject, newMap);
		}catch(IllegalAccessException e){
			logger.error(e.getMessage(),e);
			throw new RuntimeException("BeanUtils.mapToBean#copyProperties error",e);
		}catch(InvocationTargetException e){
			logger.error(e.getMessage(),e);
			throw new RuntimeException("BeanUtils.mapToBean#copyProperties error",e);
		}
		return toObject;
	}
	
	/**
	 * 如果有无法处理的类型，抛出异常
	 * @param key
	 * @param value
	 * @param toType
	 * @param message
	 * @param e
	 */
	public static void illegalValue(String key,Object value,Class<?> toType,String message,Exception e){
		StringBuffer sb = new StringBuffer();
		sb.append("无法处理类型：字段名：[");
		sb.append(key);
		sb.append("值类型:");
		sb.append(value.getClass());
		sb.append(",值:");
		sb.append(value);
		sb.append("]转成");
		sb.append(toType.getName());
		if(message != null){
			sb.append("message:");
			sb.append(message);
		}
		if(e!=null){
			logger.error(sb.toString(),e);
			throw new RuntimeException(sb.toString(),e);
		}else{
			logger.error(sb.toString(),e);
			throw new RuntimeException(sb.toString());
		}
	}
	
	
	
	 /**下划线转驼峰*/  
    public static String lineToHump(String str){  
        str = str.toLowerCase();  
        Matcher matcher = linePattern.matcher(str);  
        StringBuffer sb = new StringBuffer();  
        while(matcher.find()){  
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());  
        }  
        matcher.appendTail(sb);  
        return sb.toString();  
    }  
    /**驼峰转下划线(简单写法，效率低于{@link #humpToLine2(String)})*/  
    public static String humpToLine(String str){  
        return str.replaceAll("[A-Z]", "_$0").toLowerCase();  
    }  
    private static Pattern humpPattern = Pattern.compile("[A-Z]");  
    /**驼峰转下划线,效率比上面高*/  
    public static String humpToLine2(String str){  
        Matcher matcher = humpPattern.matcher(str);  
        StringBuffer sb = new StringBuffer();  
        while(matcher.find()){  
            matcher.appendReplacement(sb, "_"+matcher.group(0).toLowerCase());  
        }  
        matcher.appendTail(sb);  
        return sb.toString();  
    }
	
    
    /** 
     * 从org.springframework.beans.BeanUtils类中直接复制过来 
     * @param source 
     * @param target 
     * @throws BeansException 
     */  
    public static void copyProperties(Object source, Object target) throws BeansException {  
        copyProperties(source, target, null, (String[]) null);  
    }  
      
    /** 
     * 从org.springframework.beans.BeanUtils类中直接复制过来,修改部分代码 
     * 原copyProperties不能处理空值赋值的问题
     * @param source 
     * @param target 
     * @param editable 
     * @param ignoreProperties 
     * @throws BeansException 
     */  
    private static void copyProperties(Object source, Object target, Class<?> editable, String... ignoreProperties)  
            throws BeansException {  
  
        Assert.notNull(source, "Source must not be null");  
        Assert.notNull(target, "Target must not be null");  
  
        Class<?> actualEditable = target.getClass();  
        if (editable != null) {  
            if (!editable.isInstance(target)) {  
                throw new IllegalArgumentException("Target class [" + target.getClass().getName() +  
                        "] not assignable to Editable class [" + editable.getName() + "]");  
            }  
            actualEditable = editable;  
        }  
        PropertyDescriptor[] targetPds = PropertyUtils.getPropertyDescriptors(actualEditable);  
        List<String> ignoreList = (ignoreProperties != null) ? Arrays.asList(ignoreProperties) : null;  
  
        for (PropertyDescriptor targetPd : targetPds) {  
            Method writeMethod = targetPd.getWriteMethod();  
            if (writeMethod != null && (ignoreProperties == null || (!ignoreList.contains(targetPd.getName())))) {  
                PropertyDescriptor sourcePd = getPropertyDescriptor(source.getClass(), targetPd.getName());  
                if (sourcePd != null) {  
                    Method readMethod = sourcePd.getReadMethod();  
                    if (readMethod != null &&  
                            ClassUtils.isAssignable(writeMethod.getParameterTypes()[0], readMethod.getReturnType())) {  
                        try {  
                            if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {  
                                readMethod.setAccessible(true);  
                            }  
                            Object value = readMethod.invoke(source);  
                            // 判断被复制的属性是否为null, 如果不为null才复制  
                            if (value != null) {  
                                if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {  
                                    writeMethod.setAccessible(true);  
                                }  
                                writeMethod.invoke(target, value);  
                            }  
                        }  
                        catch (Exception ex) {  
                            throw new FatalBeanException(  
                                    "Could not copy property '" + targetPd.getName() + "' from source to target", ex);  
                        }  
                    }  
                }  
            }  
        }  
    }
    
    /**
     * 校验对象属性非空判断
     * @param obj
     * @param fields
     * @return
     */
    public static String checkFieldisNotNull(Object obj,String[] fields){
    	if(obj != null && fields != null){
    		StringBuilder sb = new StringBuilder();
    		List<String> list = new ArrayList<String>();
    		for(String s : fields){
    			list.add(s);
    		}
    		for (Field f : obj.getClass().getDeclaredFields()) {
    		    f.setAccessible(true);
    		    try {
    		    	if(list.contains(f.getName())){
    		    		if (f.get(obj) == null || f.get(obj).equals("")) { //判断字段是否为空，并且对象属性中的基本都会转为对象类型来判断
    		    			sb.append(","+f.getName());
    					}
    		    	}
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
    		}
    		if(sb != null && sb.length() >1){
    			return sb.toString().substring(1);
    		}
    	}
    	return null;
    }
    
    
    /**
     * 对象非空字段校验
     * @param obj
     * @param notNullFiles
     * @return
     */
    public static String isValidNull(Object obj,String[] notNullFiles){
		String nullFiles = BeanUtil.checkFieldisNotNull(obj, notNullFiles);
		if(StrUtils.isNotEmpty(nullFiles)){
			return nullFiles +" is not null";
		}
		return null;
    }
    
    public static Map objectToMap(Object obj){
        try{
            Class type = obj.getClass();
            Map returnMap = new HashMap();
            BeanInfo beanInfo = Introspector.getBeanInfo(type);

            PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors();
            for (int i = 0; i< propertyDescriptors.length; i++) {
                PropertyDescriptor descriptor = propertyDescriptors[i];
                String propertyName = descriptor.getName();
                if("requestRecords".equals(propertyName)){
                	System.out.println(111);
                }
                if (!propertyName.equals("class")) {
                    Method readMethod = descriptor.getReadMethod();
                    Object result = readMethod.invoke(obj, new Object[0]);
                    if(result == null){
                        continue;
                    }
                    //判断是否为 基础类型 String,Boolean,Byte,Short,Integer,Long,Float,Double
                    //判断是否集合类，COLLECTION,MAP              
                    if(result instanceof String 
                            || result instanceof Boolean 
                            || result instanceof Byte 
                            || result instanceof Short 
                            || result instanceof Integer 
                            || result instanceof Long 
                            || result instanceof Float 
                            || result instanceof Double 
                            || result instanceof Enum 
                            ){
                        if (result != null) {
                            returnMap.put(propertyName, result);
                        }
                    }else if(result instanceof Collection){                        
                        Collection<?> lstObj = arrayToMap((Collection<?>)result);
                        returnMap.put(propertyName, lstObj);
                    }else if(result.getClass().isArray()){
                    	//处理数组
                    	Collection<?> lstObj = arrayToMap((Object[])result);
                        returnMap.put(propertyName, lstObj);
                    }else if(result instanceof Map){
                        Map<Object,Object> lstObj = mapToMap((Map<Object,Object>)result);
                        returnMap.put(propertyName, lstObj);
                    } else {
                        Map mapResult = objectToMap(result);
                        returnMap.put(propertyName, mapResult);
                    }
                    
                }
            }
            return returnMap;
        }catch(Exception e){
            throw new RuntimeException(e);
        }
        
    }    
    
    private static Map<Object, Object> mapToMap(Map<Object, Object> orignMap) {
        Map<Object,Object> resultMap = new HashMap<Object,Object>();
        for(Entry<Object, Object> entry:orignMap.entrySet()){
            Object key = entry.getKey();
            Object resultKey = null;
            if(key instanceof Collection){
                resultKey = arrayToMap((Collection)key);
            }else if(key.getClass().isArray()){
                resultKey = arrayToMap((Object[])key);
            } else if(key instanceof Map){
                resultKey = mapToMap((Map)key);
            }
            else{
                if(key instanceof String 
                        || key instanceof Boolean 
                        || key instanceof Byte 
                        || key instanceof Short 
                        || key instanceof Integer 
                        || key instanceof Long 
                        || key instanceof Float 
                        || key instanceof Double 
                        || key instanceof Enum 
                        ){
                    if (key != null) {
                        resultKey = key;
                    }
                }else{
                    resultKey = objectToMap(key);
                }                
            }
            

            Object value = entry.getValue();
            Object resultValue = null;
            if(value instanceof Collection){
                resultValue = arrayToMap((Collection)value);
            }else if(value.getClass().isArray()){
            	resultValue = arrayToMap((Object[])value);
            }else if(value instanceof Map){
                resultValue = mapToMap((Map)value);
            }
            else{
                if(value instanceof String 
                        || value instanceof Boolean 
                        || value instanceof Byte 
                        || value instanceof Short 
                        || value instanceof Integer 
                        || value instanceof Long 
                        || value instanceof Float 
                        || value instanceof Double 
                        || value instanceof Enum 
                        ){
                    if (value != null) {
                        resultValue = value;
                    }
                }else{
                    resultValue = objectToMap(value);
                }                
            }
            
            resultMap.put(resultKey, resultValue);
        }        
        return resultMap;
    }


    private static Collection arrayToMap(Collection lstObj){
        ArrayList arrayList = new ArrayList();
        
        for (Object t : lstObj) {
            if(t instanceof Collection){
                Collection result = arrayToMap((Collection)t);
                arrayList.add(result);
            }else if(t.getClass().isArray()){
            	Collection result = arrayToMap((Object[])t);
            	arrayList.add(result);
            }else if(t instanceof Map){
                Map result = mapToMap((Map)t);
                arrayList.add(result);
            } else {
                if(t instanceof String 
                        || t instanceof Boolean 
                        || t instanceof Byte 
                        || t instanceof Short 
                        || t instanceof Integer 
                        || t instanceof Long 
                        || t instanceof Float 
                        || t instanceof Double 
                        || t instanceof Enum 
                        ){
                    if (t != null) {
                        arrayList.add(t);
                    }
                }else{
                    Object result = objectToMap(t);
                    Map objMap = new HashMap();
                    String classFullName = t.getClass().getName();
                    if(classFullName.lastIndexOf(".") >0){
                    	//只需要类名，不要com.***开头数据
                    	classFullName = classFullName.substring(classFullName.lastIndexOf(".")+1);
                    }
                    //首字母小写
                    String first = classFullName.substring(0, 1).toLowerCase();
                    String className = first+classFullName.substring(1);
                    objMap.put(className, result);
                    arrayList.add(objMap);    
                }                
            }
        }
        return arrayList;
    }
    
    private static Collection arrayToMap(Object[] lstObj){
        ArrayList arrayList = new ArrayList();
        for (Object t : lstObj) {
        	if(t==null) continue;
            if(t instanceof Collection){
                Collection result = arrayToMap((Collection)t);
                arrayList.add(result);
            }else if(t.getClass().isArray()){
            	Collection result = arrayToMap((Object[])t);
            	arrayList.add(result);
            }else if(t instanceof Map){
                Map result = mapToMap((Map)t);
                arrayList.add(result);
            } else {
                if(t instanceof String 
                        || t instanceof Boolean 
                        || t instanceof Byte 
                        || t instanceof Short 
                        || t instanceof Integer 
                        || t instanceof Long 
                        || t instanceof Float 
                        || t instanceof Double 
                        || t instanceof Enum 
                        ){
                    if (t != null) {
                        arrayList.add(t);
                    }
                }else{
                    Object result = objectToMap(t);
                    Map objMap = new HashMap();
                    String classFullName = t.getClass().getName();
                    if(classFullName.lastIndexOf(".") >0){
                    	//只需要类名，不要com.***开头数据
                    	classFullName = classFullName.substring(classFullName.lastIndexOf(".")+1);
                    }
                    //首字母小写
                    String first = classFullName.substring(0, 1).toLowerCase();
                    String className = first+classFullName.substring(1);
                    objMap.put(className, result);
                    arrayList.add(objMap);    
                }                
            }
        }
        return arrayList;
    }
	
	
	public static void main(String[] args) throws IllegalArgumentException, InvocationTargetException, Exception {
//		Person p = new Person();
//		p.setAge(20);
//		p.setUserName("张三");
//		p.setSexType("男");
//		System.out.println(BeanUtil.Bean2Map(p,"_"));
//		Map map = new HashMap();
//		map.put("user_name", "张三");
//		map.put("sex_type", "女");
//		map.put("age", 20);
//		Person pp = map2Bean(map, Person.class,"_");
//		System.out.println(pp.getUserName());	
//		System.out.println(pp.getAge());
//		System.out.println(pp.getSexType());
//		String lineToHump = lineToHump("f_parent_no_leader");  
//        System.out.println(lineToHump);//fParentNoLeader  
//        System.out.println(humpToLine(lineToHump));//f_parent_no_leader  
//        System.out.println(humpToLine2(lineToHump));//f_parent_no_leader  
		
	}
}
