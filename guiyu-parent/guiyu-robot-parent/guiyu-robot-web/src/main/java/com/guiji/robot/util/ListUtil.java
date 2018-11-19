package com.guiji.robot.util;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/** 
* @ClassName: ListUtil 
* @Description: 数组工具类
* @author: weiyunbo
* @date 2018年8月9日 下午4:40:14 
* @version V1.0  
*/
public class ListUtil {
	
	/**
	 * 判断list是否为空
	 * @param list
	 * @return
	 */
	public static boolean isNotEmpty(List list) {
		return list!=null&&!list.isEmpty();
	}
	
	/**
	 * 吊炸天的数组remove工具，可以支持千万级别的数组过滤重复，collections.removeAll测试过千万级别的数据，直接卡死。
	 * 如：src={"a","b","c"}  oth={"b","c"} ，返回结果：{"a"}
	 * @param src 源数组
	 * @param oth 要过滤掉的数据
	 * @return
	 */
	public static List removeAll(List src, List oth)
	  {
	    LinkedList result = new LinkedList(src);
	    HashSet othHash = new HashSet(oth);
	    Iterator iter = result.iterator();
	    while (iter.hasNext()) {
	      if (othHash.contains(iter.next())) {
	        iter.remove();
	      }
	    }
	    return result;
	  }
}
