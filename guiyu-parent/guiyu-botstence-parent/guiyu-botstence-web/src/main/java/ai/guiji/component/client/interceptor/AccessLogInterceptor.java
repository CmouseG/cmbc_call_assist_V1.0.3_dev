package ai.guiji.component.client.interceptor;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component
public class AccessLogInterceptor extends HandlerInterceptorAdapter{
	static Logger log = LoggerFactory.getLogger(AccessLogInterceptor.class);
	//线程安全的请求开始时间
	ThreadLocal<Long> requestBeginTime = new ThreadLocal<Long>();

	@Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String url =request.getRequestURL().toString();
		log.info("访问地址："+url+"  ["+request.getMethod()+"]");
		Map<String, String[]> map =request.getParameterMap();
		if (map.size()>0){
			StringBuffer param=new StringBuffer("");
			for (java.util.Map.Entry entry :map.entrySet()){
				param.append("&"+(String)entry.getKey()+"=");
				String[] val=(String[])entry.getValue();
				if (val.length==1){
					param.append(val[0]);
				}else{
					param.append(Arrays.toString(val));
				}
			}
			log.info("访问参数："+param.toString());
		}
		long startTime = System.currentTimeMillis();
//        request.setAttribute("requestStartTime", startTime);
		requestBeginTime.set(startTime);
		return true;
	}

	// controller处理完成
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
//        long startTimeOld = (Long) request.getAttribute("requestStartTime");
        long startTime = requestBeginTime.get();
        long endTime = System.currentTimeMillis();
        long executeTime = endTime - startTime;
        // log it
        if (executeTime > 1000) {
            System.out.println("[" + method.getDeclaringClass().getName() + "." + method.getName() + "] 执行耗时 : "
                    + executeTime + "ms");
        } else {
            System.out.println("[" + method.getDeclaringClass().getSimpleName() + "." + method.getName() + "] 执行耗时 : "
                    + executeTime + "ms");
        }
    }

}
