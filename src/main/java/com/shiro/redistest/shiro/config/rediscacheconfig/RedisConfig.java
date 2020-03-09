package com.shiro.redistest.shiro.config.rediscacheconfig;

import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * redis配置
 *
 * */
@Configuration
public class RedisConfig {
    /**
     * redisManager
     *
     * @return
     */
    public RedisManager redisManager() {
        RedisManager redisManager = new RedisManager ( );
        //主鸡地址
        redisManager.setHost ("127.0.0.1");
        //端口
        redisManager.setPort (6379);
        // 配置过期时间
        redisManager.setExpire (1800);
        return redisManager;
    }

    /**
     * cacheManager配到shiro配置
     *
     * @return
     */
    public RedisCacheManager cacheManager() {
        RedisCacheManager redisCacheManager = new RedisCacheManager ( );
        redisCacheManager.setRedisManager (redisManager ( ));
        return redisCacheManager;
    }

    /**
     * redisSessionDAO
     */
    public RedisSessionDAO redisSessionDAO() {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO ( );
        redisSessionDAO.setRedisManager (redisManager ( ));

        return redisSessionDAO;
    }

    /**
     * sessionManager配到shiro配置
     */
    public DefaultWebSessionManager SessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager ( );
      //sessionManager.setDeleteInvalidSessions (true);
        //sessionManager.setDeleteInvalidSessions (true);
       // sessionManager.setGlobalSessionTimeout (1);

        sessionManager.setSessionDAO (redisSessionDAO ( ));

        return sessionManager;
    }
    /**
     * 配置会话ID生成器
     * @return
     */
    @Bean
    public SessionIdGenerator sessionIdGenerator() {
        return new JavaUuidSessionIdGenerator ();
    }
    /**
     * 配置session监听
     * @return
     */


}