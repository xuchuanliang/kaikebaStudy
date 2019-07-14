package com.ant.aop.advise;

import org.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

/**
 * 使用spring aop简单代理方式：前置通知
 */
public class MyBeforeAdvise implements MethodBeforeAdvice {
    /**
     * 次要业务
     * @param method method being invoked
     * @param objects arguments to the method
     * @param target target of the method invocation. May be {@code null}.
     * @throws Throwable
     */
    @Override
    public void before(Method method, Object[] objects, Object target) throws Throwable {
        System.out.println("----洗手手----");
    }
}
