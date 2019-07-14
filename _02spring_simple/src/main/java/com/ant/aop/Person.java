package com.ant.aop;

/**
 * 被监控的实际对象
 */
public class Person implements BaseService {
    /**
     * 织入点
     */
    @Override
    public void eat() {
        System.out.println("吃饭饭");
    }

    /**
     * 织入点
     */
    @Override
    public void wc() {
        System.out.println("上厕所");
    }
}
