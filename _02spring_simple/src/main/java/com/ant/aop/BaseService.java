package com.ant.aop;

/**
 * 被代理的对象接口
 */
public interface BaseService {
    /**
     * 连接点 joinPoint
     */
    void eat();

    /**
     * 连接点 joinPoint
     */
    void wc();
}
