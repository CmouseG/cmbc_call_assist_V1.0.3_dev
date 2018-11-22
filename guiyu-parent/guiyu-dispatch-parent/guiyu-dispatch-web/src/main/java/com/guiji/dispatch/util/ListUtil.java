package com.guiji.dispatch.util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class ListUtil {
	/**
	 * 分组依据接口，用于集合分组时，获取分组 T为要groupBy属性是类型，这个返回值为要groupBy的属性值
	 */
	public interface GroupBy<T> {
		T groupBy(Object obj);
	}

	/**
	 * 通过属性对集合分组
	 * 
	 * @param colls
	 * @param gb
	 * @return extends Comparable<T>
	 */
	public static final <T, D> Map<T, List<D>> groupBy(Collection<D> colls, GroupBy<T> gb) {
		Map<T, List<D>> map = new HashMap<T, List<D>>();

		Iterator<D> iter = colls.iterator();

		while (iter.hasNext()) {
			D d = iter.next();
			T t = gb.groupBy(d);
			if (map.containsKey(t)) {
				map.get(t).add(d);
			} else {
				List<D> list = new ArrayList<D>();
				list.add(d);
				map.put(t, list);
			}
		}
		return map;
	}

	/**
	 * 通过属性名称对集合分组
	 * 
	 * @param colls
	 * @param fieldName为集合中对象的属性名称
	 * @return extends Comparable<T>
	 */
	public static final <T, D> Map<T, List<D>> groupBy(Collection<D> colls, String fieldName) {
		return groupBy(colls, new GroupBy<T>() {
			@Override
			public T groupBy(Object obj) {
				Object v = getFieldValueByName(obj, fieldName);
				return (T) v;
			}
		});
	}

	/**
	 * 根据属性名称获取属性值
	 */
	public static Object getFieldValueByName(Object o, String fieldName) {
		try {
			String firstLetter = fieldName.substring(0, 1).toUpperCase();
			String getter = "get" + firstLetter + fieldName.substring(1);
			Method method = o.getClass().getMethod(getter, new Class[] {});
			Object value = method.invoke(o, new Object[] {});
			return value;
		} catch (Exception e) {
			return null;
		}
	}
	
	


}
