package com.guiji.cloud.zuul.realm;

import com.guiji.cloud.zuul.config.JwtConfig;
import com.guiji.cloud.zuul.entity.JwtToken;
import com.guiji.user.dao.SysUserMapper;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Sha512Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Realm 的一个配置管理类 allRealm()方法得到所有的realm
 */
@Component
public class ShiroRealmConfig {
    @Resource
    private JwtConfig jwtConfig;
    @Autowired
    ApiJdbcRealm apiJdbcRealm;
    @Autowired
    private SysUserMapper userMapper;
//    @Autowired
//    JdbcRealm jdbcRealm;
    /**
     * 配置所有自定义的realm,方便起见,应对可能有多个realm的情况
     */
    public List<Realm> allRealm() {
        List<Realm> realmList = new LinkedList<>();
        AuthorizingRealm jwtRealm = jwtRealm();
        realmList.add(jwtRealm);
//        realmList.add(apiJdbcRealm);
//        realmList.add(jdbcRealm);
        return Collections.unmodifiableList(realmList);
    }
    /**
     * 自定义 JWT的 Realm
     * 重写 Realm 的 supports() 方法是通过 JWT 进行登录判断的关键
     */
    private AuthorizingRealm jwtRealm() {
        AuthorizingRealm jwtRealm = new AuthorizingRealm() {
            @PostConstruct
            public void initConstruct(){
                HashedCredentialsMatcher matcher=new HashedCredentialsMatcher(Sha512Hash.ALGORITHM_NAME);
                setCredentialsMatcher(matcher);
            }
            /**
             * 权限
             */
            @Override
            protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
                //null usernames are invalid
                if (principals == null) {
                    throw new AuthorizationException("PrincipalCollection method argument cannot be null.");
                }

                String username = (String) getAvailablePrincipal(principals);

                Set<String> permissions = userMapper.getPermissions(username);

                SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
                info.setStringPermissions(permissions);
                return info;
            }
            /**
             * 注意坑点 : 必须重写此方法，不然Shiro会报错
             * 因为创建了 JWTToken 用于替换Shiro原生 token,所以必须在此方法中显式的进行替换，否则在进行判断时会一直失败
             */
            @Override
            public boolean supports(AuthenticationToken token) {
                return token instanceof JwtToken;
            }
          /*  @Override
            protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
                return new SimpleAuthorizationInfo();
            }*/
            /**
             * 校验 验证token逻辑
             */
            @Override
            protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) {
                JwtToken jToken = (JwtToken)token;
                String jwtToken = jToken.getToken();
                Long userId = jwtConfig.getUserIdByToken(jwtToken);
                String orgCode = jwtConfig.getOrgCodeByToken(jwtToken);
                if (userId == null )
                    throw new AuthenticationException("userId not exits , please check your token");
                if (orgCode == null || orgCode.equals(""))
                    throw new AuthenticationException("orgCode is invalid , please check your token");
                if (!jwtConfig.verifyToken(jwtToken))
                    throw new AuthenticationException("token is invalid , please check your token");
                return new SimpleAuthenticationInfo(token, token, getName());
            }
        };
        jwtRealm.setCredentialsMatcher(credentialsMatcher());
        return jwtRealm;
    }
    /**
     * 注意坑点 : 密码校验 , 这里因为是JWT形式,就无需密码校验和加密,直接让其返回为true(如果不设置的话,该值默认为false,即始终验证不通过)
     */
    private CredentialsMatcher credentialsMatcher() {
        return (token, info) -> true;
    }
}
