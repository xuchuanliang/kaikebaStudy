package com.ant.aop.advisor;

import org.springframework.aop.ClassFilter;
import org.springframework.aop.MethodMatcher;
import org.springframework.aop.Pointcut;

/**
 * 切入点
 */
public class MyPointCut implements Pointcut {
    /**
     * 使用spring DI进行依赖注入
     * <p>
     * <p>
     * 这里就是类似于我们在代理模式实现中的invoke()方法中针对方法名进行判断
     */
    private ClassFilter classFilter;
    private MethodMatcher methodMatcher;

    @Override
    public ClassFilter getClassFilter() {
        return this.classFilter;
    }

    @Override
    public MethodMatcher getMethodMatcher() {
        return this.methodMatcher;
    }

    public void setClassFilter(ClassFilter classFilter) {
        this.classFilter = classFilter;
    }

    public void setMethodMatcher(MethodMatcher methodMatcher) {
        this.methodMatcher = methodMatcher;
    }
}
