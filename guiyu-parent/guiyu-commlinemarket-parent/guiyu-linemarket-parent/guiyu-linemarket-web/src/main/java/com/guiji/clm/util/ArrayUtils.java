package com.guiji.clm.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/** 
* @Description: 数组工具
* @Author: weiyunbo
* @date 2019年1月31日 下午9:39:17 
* @version V1.0  
*/
public class ArrayUtils {

	public static void main(String[] args) {
		Integer[] assignedLine = {2,3,10};  
        Integer[] now = {1,2,4};
        List<Integer> list = getDiffIds(assignedLine,now);
        System.out.println(list);
	}
	
	/**
	 * 找出两个数组中相同的元素
	 * @param a
	 * @param b
	 * @return
	 */
	public static Set<Integer> getSameIds(Integer[] a, Integer[] b){  
		Set<Integer> same = new HashSet<Integer>();  //用来存放两个数组中相同的元素  
		Set<Integer> temp = new HashSet<Integer>();  //用来存放数组a中的元素  
		for (int i = 0; i < a.length; i++) {  
			temp.add(a[i]);   //把数组a中的元素放到Set中，可以去除重复的元素  
		}  
		for (int j = 0; j < b.length; j++) {  
			//把数组b中的元素添加到temp中  
			//如果temp中已存在相同的元素，则temp.add（b[j]）返回false  
			if(!temp.add(b[j]))  
				same.add(b[j]);  
		}  
		return same;   
	} 
	
	
	/**
	 * 找出两个数组中不相同的元素
	 * @param t1
	 * @param t2
	 * @return
	 */
	public static <T> List<T> getDiffIds(T[] t1, T[] t2) {    
	      List<T> list1 = Arrays.asList(t1); //将t1数组转成list数组   
	      List<T> list2 = new ArrayList<T>();//用来存放2个数组中不相同的元素    
	      for (T t : t2) {    
	          if (!list1.contains(t)) {    
	              list2.add(t);    
	          }    
	      }    
	      return list2;    
	  } 
}
