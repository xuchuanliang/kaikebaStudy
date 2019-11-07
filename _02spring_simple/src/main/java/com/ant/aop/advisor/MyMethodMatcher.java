package com.ant.aop.advisor;

import org.springframework.aop.MethodMatcher;

import java.lang.reflect.Method;

/**
 * 只对Person类的eat方法进行拦截，饭前要洗手
 */
public class MyMethodMatcher implements MethodMatcher {
    /**
     * 静态方法匹配：
     * 被监控对象 如BaseService没有重载方法，每个方法名称都是唯一
     * 此时可以采用静态检测方式，只根据方法名进行判断
     *
     * @param method
     * @param targetClass
     * @return
     */
    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        return "eat".equals(method.getName());
    }

    @Override
    public boolean isRuntime() {
        return false;
    }

    @Override
    public boolean matches(Method method, Class<?> targetClass, Object... args) {
        return false;
    }
}
