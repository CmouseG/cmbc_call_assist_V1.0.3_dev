package com.guiji.cloud.zuul.config;

import com.guiji.auth.util.PermissionResolve;
import com.guiji.cloud.zuul.filter.ZuulAuthenticationFilter;
import com.guiji.cloud.zuul.filter.ZuulAuthorizationFilter;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.eis.CachingSessionDAO;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class ShiroConfig {
	
	@Autowired
	private CachingSessionDAO sessionDAO;
	
	@Autowired
	private List<Realm> realms;
	
	@Bean
    public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager, PermissionResolve resolve) {
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //设置自定义拦截器
        Map<String,Filter> filters=new HashMap<>();
        filters.put("zuulAuthc", new ZuulAuthenticationFilter());
        filters.put("zuulPerms", new ZuulAuthorizationFilter(resolve));
        
        
        shiroFilterFactoryBean.setFilters(filters);
        
        // 必须设置 SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        // 设置拦截器
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        //开放登陆接口
        filterChainDefinitionMap.put("/login", "anon");
        filterChainDefinitionMap.put("/loginOut", "anon");
        filterChainDefinitionMap.put("/getUserId", "zuulAuthc");
        //主要这行代码必须放在所有权限设置的最后，不然会导致所有 url 都被拦截
        filterChainDefinitionMap.put("/**", "zuulAuthc,zuulPerms");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    /**
     * 注入 securityManager
     */
    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 设置realm.
        	securityManager.setRealms(realms);
        securityManager.setSessionManager(sessionManager());
        return securityManager;
    }
    
    public DefaultWebSessionManager sessionManager(){
    	DefaultWebSessionManager sessionManager=new DefaultWebSessionManager();
    	sessionManager.setSessionIdCookie(new SimpleCookie("token"));
    	sessionManager.setSessionIdCookieEnabled(true);
    	
    	BaseSessionIdGenerator IdGenerator=new BaseSessionIdGenerator();
    	sessionDAO.setSessionIdGenerator(IdGenerator);
    	sessionManager.setSessionDAO(sessionDAO);
    	return sessionManager;
    }
    
}
