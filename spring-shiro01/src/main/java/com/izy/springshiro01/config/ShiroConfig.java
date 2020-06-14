package com.izy.springshiro01.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.izy.springshiro01.shiro.ShiroRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author zouyu
 * @description
 * @date 2020/5/28
 */
@Configuration
public class ShiroConfig {
    //注解生效1
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor =new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }
    //注解生效2
    @Bean
    @ConditionalOnMissingBean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator app=new DefaultAdvisorAutoProxyCreator();
        app.setProxyTargetClass(true);
        return app;
    }
    //ShiroFilterFactoryBean
    @Bean
    public ShiroFilterFactoryBean shiroFilter(@Qualifier("securityManager") SecurityManager securityManager){
        /**shiro内置过滤器
         * anno 无需认证
         * authc 必须认证才能访问
         * user 必须记住我才能访问
         * perm  指定资源的权限才能访问
         * role  必须拥有角色才能访问
         */
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        shiroFilter.setSecurityManager(securityManager);

        Map<String, String> filterMap = new LinkedHashMap<>();
        //filterMap.put("/user/add","perms[user:add]");
        filterMap.put("/user/*","authc");
        shiroFilter.setFilterChainDefinitionMap(filterMap);
        shiroFilter.setLoginUrl("/toLogin");
        //shiroFilter.setUnauthorizedUrl("/unAuthronize");
        return shiroFilter;
    }


    //securityMange
    @Bean("securityManager")
    public DefaultWebSecurityManager getSecurityManager(@Qualifier("realm") ShiroRealm  realm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(realm);
        return securityManager;
    }
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher(){
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        // 使用md5 算法进行加密
        hashedCredentialsMatcher.setHashAlgorithmName("md5");
        // 设置散列次数： 意为加密几次
        hashedCredentialsMatcher.setHashIterations(2);

        return hashedCredentialsMatcher;
    }
    //自定义realm对象
    @Bean
   public ShiroRealm  realm(){
       ShiroRealm shiroRealm = new ShiroRealm();
        shiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
       return shiroRealm;
   }

    @Bean
    public ShiroDialect shiroDialect(){
        return new ShiroDialect();
    }
}
