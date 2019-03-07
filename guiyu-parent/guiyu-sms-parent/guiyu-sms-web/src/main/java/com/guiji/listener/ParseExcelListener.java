package com.guiji.listener;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

public class ParseExcelListener extends AnalysisEventListener<Object>
{
	private static final Logger logger = LoggerFactory.getLogger(ParseExcelListener.class);
	
	List<String> phoneList = new ArrayList<>();
	public ParseExcelListener(List<String> phoneList) {
		this.phoneList = phoneList;
	}

	@Override
	public void invoke(Object object, AnalysisContext context)
	{
		List row = (List) object;
		phoneList.add((String) row.get(0));
	}

	@Override
	public void doAfterAllAnalysed(AnalysisContext context)
	{
		logger.info("excel文件解析完成!");
	}

}
