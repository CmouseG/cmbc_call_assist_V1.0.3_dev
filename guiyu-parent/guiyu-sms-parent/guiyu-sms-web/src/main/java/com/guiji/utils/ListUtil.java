package com.guiji.utils;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;

public class ListUtil
{
	/*
	 * 将List<String>集合 转化为'元素1','元素2','元素3','元素4','元素5'
	 */
	public static String convertListToString(List<String> phoneList)
	{
		StringBuffer sb = new StringBuffer();
		if (CollectionUtils.isNotEmpty(phoneList))
		{
			for (int i = 0; i < phoneList.size(); i++)
			{
				if(i==0){
					sb.append(phoneList.get(i));
				}else{
					sb.append(",").append(phoneList.get(i));
				}
			}
		}
		return sb.toString();
	}
}
