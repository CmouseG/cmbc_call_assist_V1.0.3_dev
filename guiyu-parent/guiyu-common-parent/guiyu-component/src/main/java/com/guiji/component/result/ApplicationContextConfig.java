package com.guiji.component.result;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import java.util.ArrayList;
import java.util.List;

@Component
public class ApplicationContextConfig implements ApplicationContextAware {
	
	@Autowired
	private List<HttpMessageConverter<?>> converters;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		ResultReturnValueHandler handler= new ResultReturnValueHandler(converters);
		RequestMappingHandlerAdapter adapter=applicationContext.getBean(RequestMappingHandlerAdapter.class);
		List<HandlerMethodReturnValueHandler>  handlerList=adapter.getReturnValueHandlers();
		List<HandlerMethodReturnValueHandler> descHandlerList=new ArrayList<>(handlerList);
		int length=descHandlerList.size();
		for(int i=0;i<length;i++){
			if(descHandlerList.get(i) instanceof RequestResponseBodyMethodProcessor){
				descHandlerList.set(i, handler);
			}
		}
		adapter.setReturnValueHandlers(descHandlerList);
	}

}
