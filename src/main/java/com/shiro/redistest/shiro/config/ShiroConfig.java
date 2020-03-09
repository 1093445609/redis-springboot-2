package com.shiro.redistest.shiro.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.shiro.redistest.shiro.config.realmconfig.CustomRealm;
import com.shiro.redistest.shiro.config.rediscacheconfig.KickoutSessionControlFilter;
import com.shiro.redistest.shiro.config.rediscacheconfig.RedisConfig;
import com.shiro.redistest.shiro.config.rememberconfig.RememberMeConfig;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 常用的过滤器
 * anon:无认证
 * authc:必须认证 登陆即可
 * user: 使用记住我可以直接访问
 * perms: 必须有资源权限 比如crud
 * roles: 必须有角色权限
 */
@Configuration
public class ShiroConfig {
    /**
     * 创建自定义配置的Realm
     */
    @Bean
    CustomRealm myRealm() {
        CustomRealm customRealm = new CustomRealm();
        //注入加密算法
        customRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return customRealm;
    }

    /**
     * 创建DefaultWebSecurityManager管理器,使它管理自定义的Realm
     */
    @Bean
    DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        //自定义的Realm交给manager
        manager.setRealm(myRealm());
        // 自定义缓存实现 使用redis
        manager.setCacheManager(new RedisConfig().cacheManager());
        // 自定义session管理 使用redis
        manager.setSessionManager(new RedisConfig().SessionManager());
        // 使用记住我,注入配置
        manager.setRememberMeManager(new RememberMeConfig().rememberMeManager());
        return manager;
    }

    /**
     * 创建shiroFilterFactoryBean
     * 关联一个securityManager ( )管理器
     */
    @Bean
    ShiroFilterFactoryBean shiroFilterFactoryBean() {
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        bean.setSecurityManager(securityManager());
        //登陆页
        bean.setLoginUrl("/login");
        //登陆成功后界面
        bean.setSuccessUrl("/index");
        //未授权跳转到
        bean.setUnauthorizedUrl("/tip");
        Map<String, String> map = new LinkedHashMap<>();
        //anon是把限制权限改为无限制
        //map.put ("/index", "anon");
        //authc 登陆后可以访问
        // map.put ("/**", "authc");
        map.put("/add", "authc");
        //权限必须有addProduct才可以访问
        map.put("/update", "perms[addProduct]");
        //角色是admin 才可以访问超级管理员界面
        map.put("/admin", "roles[admin]");
        bean.setFilterChainDefinitionMap(map);
        return bean;
    }

    /**
     * 用于ShiroDialect和thymeleaf标签配合使用
     */
    @Bean(name = "shiroDialect")

    public ShiroDialect shiroDialect() {

        return new ShiroDialect();

    }


    /**
     * 密码加密算法设置
     * md5
     * @return
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("md5");
        //散列的次数
        hashedCredentialsMatcher.setHashIterations(2);
        return hashedCredentialsMatcher;
    }


}
