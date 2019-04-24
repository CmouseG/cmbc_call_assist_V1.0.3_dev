package com.guiji.sms.handler;

import java.util.List;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

/**
 * 解析excel处理类
 * @author Sun
 */
public class ParseExcelHandler extends AnalysisEventListener<Object>
{
	List<String> list = null;
	public ParseExcelHandler(List<String> list)
	{
		this.list = list;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public void invoke(Object object, AnalysisContext context)
	{
		List row = (List) object;
		list.add((String) row.get(0)); // 0：第一列
	}
	
	@Override
	public void doAfterAllAnalysed(AnalysisContext context)
	{}

}
