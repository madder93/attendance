/*
 * @title com.enn.framework.micros.authorize.config.RedisConfig.java
 *
 * @Copy.Right (c)2017.好买气电子商务有限公司
 *
 * @Department 技术开发部
 *
 * @date 2017年3月15日 下午4:06:09
 *
 * @author Enn.HowMuch.yinshijie
 *
 * @version V0.1.0
 */
package com.madder.attendance.config;

import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.session.data.redis.config.ConfigureRedisAction;

import java.time.Duration;

/**
 * @ClassName RedisConfig
 * @Description TODO
 * @author Enn.HowMuch.yinshijie
 * @date 2017年3月15日 下午4:06:09
 *
 */
@Configuration
@ImportResource(locations = {"classpath:cache/spring-cache-redis.xml"})
public class RedisConfig {

    /**
     * HttpSessionConfig
     * 
     * @Description TODO
     * @Call com.enn.framework.micros.authorize.config.RedisConfig.configureRedisAction(...)
     *
     * @return
     */
    @Bean
    public static ConfigureRedisAction configureRedisAction() {
        return ConfigureRedisAction.NO_OP;
    }

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofHours(1)); // 设置缓存有效期一小时
        return RedisCacheManager
                .builder(RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory))
                .cacheDefaults(redisCacheConfiguration).build();
    }
}
