package com.guiji.cloud.zuul.config;

import com.guiji.cloud.zuul.filter.ApiFilter;
import com.guiji.cloud.zuul.filter.JwtFilter;
import com.guiji.cloud.zuul.filter.ZuulAuthorizationFilter;
import com.guiji.cloud.zuul.realm.ShiroRealmConfig;
import com.guiji.cloud.zuul.service.ApiCheckService;
import org.apache.shiro.mgt.SecurityManager;
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
import java.util.Map;

@Configuration
public class ShiroConfig {
	
	@Autowired
	private CachingSessionDAO sessionDAO;
	@Autowired
    JwtConfig jwtConfig;
	@Autowired
    ApiCheckService apiCheckService;

//	@Autowired
//	private List<Realm> realms;
	
	@Bean
    public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager, PermissionResolve resolve) {
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //设置自定义拦截器
        Map<String,Filter> filters=new HashMap<>();
        filters.put("zuulAuthc", new JwtFilter(jwtConfig));
        filters.put("zuulPerms", new ZuulAuthorizationFilter(resolve));

        shiroFilterFactoryBean.setFilters(filters);
        
        // 必须设置 SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        // 设置拦截器
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        //开放登陆接口
        filterChainDefinitionMap.put("/login", "anon");
        filterChainDefinitionMap.put("/refreshToken", "anon");
        filterChainDefinitionMap.put("/api/getCallDetailById", "anon");
        filterChainDefinitionMap.put("/api/getLineHangupDetail", "anon");
        filterChainDefinitionMap.put("/apiLogin", "anon");
        filterChainDefinitionMap.put("/loginOut", "anon");
//        filterChainDefinitionMap.put("/auth/menu/getMenus", "anon");
        filterChainDefinitionMap.put("/da/robot/receiveSellbotCallback", "anon");	//sellbot回调
        filterChainDefinitionMap.put("/da/robot/receiveFdCallback", "anon");	//飞龙回调
        filterChainDefinitionMap.put("/getUserId", "zuulAuthc");
        filterChainDefinitionMap.put("/api/getToken", "anon");  //第三方获取token
        filterChainDefinitionMap.put("/ai/callback", "anon");
        //对第三方api不进行拦截
        filterChainDefinitionMap.put("/v1/thirdApi/**", "anon");
        //主要这行代码必须放在所有权限设置的最后，不然会导致所有 url 都被拦截
        filterChainDefinitionMap.put("/**", "zuulAuthc,zuulPerms");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    /**
     * 注入 securityManager
     */
    @Bean
    public SecurityManager securityManager(ShiroRealmConfig shiroRealmConfig) {

        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 设置realm.
//        securityManager.setRealms(realms);
        securityManager.setRealms(shiroRealmConfig.allRealm());
        securityManager.setSessionManager(sessionManager());
        return securityManager;


   /*     DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealms(shiroRealmConfig.allRealm());
        //设置realm
        DefaultSubjectDAO subjectDAO = (DefaultSubjectDAO) securityManager.getSubjectDAO();
        // 关闭自带session
        DefaultSessionStorageEvaluator evaluator = (DefaultSessionStorageEvaluator) subjectDAO.getSessionStorageEvaluator();
        evaluator.setSessionStorageEnabled(Boolean.FALSE);
        subjectDAO.setSessionStorageEvaluator(evaluator);
        return securityManager;*/
    }



    public DefaultWebSessionManager sessionManager(){
        DefaultWebSessionManager sessionManager=new DefaultWebSessionManager();
//        sessionManager.setSessionIdCookie(new SimpleCookie("token"));
//        sessionManager.setSessionIdCookieEnabled(true);

        BaseSessionIdGenerator IdGenerator=new BaseSessionIdGenerator();
        sessionDAO.setSessionIdGenerator(IdGenerator);
        sessionManager.setSessionDAO(sessionDAO);
        return sessionManager;
    }

    /**
     * 添加注解支持
     */
/*    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true); // 强制使用cglib，防止重复代理和可能引起代理出错的问题
        return defaultAdvisorAutoProxyCreator;
    }*/

    /**
     * 添加注解依赖
     */

/*    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }*/

    /**
     * 开启注解验证
     */
/*    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }*/

    
}
