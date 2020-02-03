package com.xiayu.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisTemplate;

import java.lang.reflect.Method;

//@Configuration
//@EnableCaching
//public class RedisConfig extends CachingConfigurerSupport {
//    @Bean
//    public CacheManager cacheManager(RedisTemplate redisTemplate) {
//        return new RedisCacheManager(redisTemplate);
//    }
//
//    @Override
//    public KeyGenerator keyGenerator() {
//
//        return new KeyGenerator() {
//            @Override
//            public Object generate(Object o, Method method, Object... objects) {
//                StringBuilder stringBuilder = new StringBuilder();
//                stringBuilder.append(o.getClass().getName());
//                stringBuilder.append(method.getName());
//                for (Object object:objects){
//                    stringBuilder.append(object.toString());
//                }
//                return stringBuilder.toString();
//            }
//        };
//    }
//}
