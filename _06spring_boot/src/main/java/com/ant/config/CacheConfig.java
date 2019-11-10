package com.ant.config;

import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
public class CacheConfig extends CachingConfigurerSupport {
    @Override
    public KeyGenerator keyGenerator() {
        return (target, method, params) -> target.getClass().getName() + "_" + method.getName() + "_" + params[0].toString();
    }
}
