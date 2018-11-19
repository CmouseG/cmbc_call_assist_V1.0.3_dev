/** 
 *@Copyright:Copyright (c) 2008 - 2100 
 *@Company:guojaing
 */  
package com.guiji.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** 
 *@Description:JSON工具类  
 *@Author:weiyunbo
 *@date:2018年7月5日 下午2:17:19
 *@history:
 *@Version:v1.0 
 */
public class JsonUtils {

	//对象转JSON
	public static String bean2Json(Object obj) {
		return JSON.toJSONString(obj);
	}
	
	//JSON报文转对象
	public static <T> T json2Bean(String json,Class<T> clazz) {
		return JSON.parseObject(json, clazz);
	}
	
	// 转换为数组
    public static <T> Object[] json2Array(String json, Class<T> clazz) {
        return JSON.parseArray(json, clazz).toArray();
    }
	
    // 转换为List
    public static <T> List<T> json2List(String json, Class<T> clazz) {
        return JSON.parseArray(json, clazz);
    }
	
	/**
	 * listToTree
	 * <p>方法说明<p>
	 * 将JSONArray数组转为树状结构
	 * @param arr 需要转化的数据
	 * @param id 数据唯一的标识键值
	 * @param pid 父id唯一标识键值
	 * @param child 子节点键值
	 * @return JSONArray
	 */
	public static JSONArray listToTree(JSONArray arr,String id,String pid,String child){
	   JSONArray r = new JSONArray();
	   JSONObject hash = new JSONObject();
	   //将数组转为Object的形式，key为数组中的id
	   for(int i=0;i<arr.size();i++){
	      JSONObject json = (JSONObject) arr.get(i);
	      hash.put(json.getString(id), json);
	   }
	   //遍历结果集
	   for(int j=0;j<arr.size();j++){
	      //单条记录
	      JSONObject aVal = (JSONObject) arr.get(j);
	      //在hash中取出key为单条记录中pid的值
	      JSONObject hashVP = (JSONObject) hash.get(aVal.get(pid).toString());
	      //如果记录的pid存在，则说明它有父节点，将她添加到孩子节点的集合中
	      if(hashVP!=null){
	         //检查是否有child属性
	         if(hashVP.get(child)!=null){
	            JSONArray ch = (JSONArray) hashVP.get(child);
	            ch.add(aVal);
	            hashVP.put(child, ch);
	         }else{
	            JSONArray ch = new JSONArray();
	            ch.add(aVal);
	            hashVP.put(child, ch);
	         }
	      }else{
	         r.add(aVal);
	      }
	   }
	   return r;
	}
	
	public static void main(String[] args){
		   List<Map<String,Object>> data = new ArrayList<>();
		   Map<String,Object> map = new HashMap<>();
		   map.put("id",1);
		   map.put("pid",0);
		   map.put("name","甘肃省");
		   data.add(map);
		   Map<String,Object> map2 = new HashMap<>();
		   map2.put("id",2);
		   map2.put("pid",1);
		   map2.put("name","天水市");
		   data.add(map2);
		   Map<String,Object> map3 = new HashMap<>();
		   map3.put("id",3);
		   map3.put("pid",2);
		   map3.put("name","秦州区");
		   data.add(map3);
		   Map<String,Object> map4 = new HashMap<>();
		   map4.put("id",4);
		   map4.put("pid",0);
		   map4.put("name","北京市");
		   data.add(map4);
		   Map<String,Object> map5 = new HashMap<>();
		   map5.put("id",5);
		   map5.put("pid",4);
		   map5.put("name","昌平区");
		   data.add(map5);
		   System.out.println(JSON.toJSONString(data));
		   JSONArray result = listToTree(JSONArray.parseArray(JSON.toJSONString(data)),"id","pid","children");
		   System.out.println(JSON.toJSONString(result));
		}
}
  
