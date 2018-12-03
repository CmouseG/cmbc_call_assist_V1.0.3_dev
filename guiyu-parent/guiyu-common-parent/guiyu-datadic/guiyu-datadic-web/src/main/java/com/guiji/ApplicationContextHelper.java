/**
 * Copyright: Copyright (c) 2016 
 * Company:东方网力科技股份有限公司
 * 
 * @author wangkai
 * @date 2016年7月7日 下午16:16:26
 * @version V1.0
 */
package com.guiji;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class ApplicationContextHelper implements ApplicationContextAware {
    /**
     * spring容器上下文
     */
    private static ApplicationContext APPCONTEXT;

    /**
     * 此方法可以把ApplicationContext对象inject到当前类中作为一个静态成员变量。
     * 
     * @param applicationContext
     *            ApplicationContext 对象.
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        APPCONTEXT = applicationContext;
    }

    /**
     * 这是一个便利的方法，帮助我们快速得到一个BEAN
     * 
     * @param beanName
     *            bean的名字
     * @return 返回一个bean对象
     */
    public static Object getBean(String beanName) {
        return APPCONTEXT.getBean(beanName);
    }

    /**
     * 
     * @Title: getBean
     * @Description: 这是一个便利的方法，帮助我们快速得到一个BEAN
     * @param class1
     *            类
     * @return object
     */

    public static Object getBean(Class<?> class1) {

        return APPCONTEXT.getBean(class1);
    }
}