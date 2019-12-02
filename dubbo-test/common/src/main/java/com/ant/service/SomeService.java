package com.ant.service;

/**
 * 定义服务接口，给服务提供者实现，给服务消费者调用
 */
public interface SomeService {
    /**
     *
     * @param name
     * @return
     */
    String hello(String name);
}
