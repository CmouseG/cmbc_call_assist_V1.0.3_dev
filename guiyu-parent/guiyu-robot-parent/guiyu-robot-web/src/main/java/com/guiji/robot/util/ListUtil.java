package com.guiji.robot.util;

import java.util.ArrayList;
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
	
	/**
	 * 将一组数据平均分成n组
	 * @param source 要分组的数据源
	 * @param n      平均分成n组
	 * @param <T>
	 * @return
	 */
	public static <T> List<List<T>> averageAssign(List<T> source, int n) {
	    List<List<T>> result = new ArrayList<List<T>>();
	    if(n==0 || n==1) {
	    	result.add(source);
			return result;
		}
	    int remainder = source.size() % n;  //(先计算出余数)
	    int number = source.size() / n;  //然后是商
	    int offset = 0;//偏移量
	    for (int i = 0; i < n; i++) {
	        List<T> value = null;
	        if (remainder > 0) {
	            value = source.subList(i * number + offset, (i + 1) * number + offset + 1);
	            remainder--;
	            offset++;
	        } else {
	            value = source.subList(i * number + offset, (i + 1) * number + offset);
	        }
	        result.add(value);
	    }
	    return result;
	}
	
	public static void main(String[] args) {
		List<String> list = new ArrayList<String>();
		list.add("1");
		list.add("2");
		list.add("3");
		list.add("4");
		list.add("5");
		list.add("6");
		list.add("7");
		list.add("8");
		list.add("9");
		list.add("10");
		list.add("11");
		List<List<String>> listGroup = ListUtil.averageAssign(list,3);
		for(List<String> group : listGroup) {
			System.out.println(group);
		}
	}
}
