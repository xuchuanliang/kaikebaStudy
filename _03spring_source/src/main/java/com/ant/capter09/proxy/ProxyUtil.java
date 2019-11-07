package com.ant.capter09.proxy;

import com.ant.capter09.service.UserService;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 动态代理
 * 从以下案例中我认为jdk动态代理和cglib动态代理主要有以下两点区别
 * 1.jdk动态代理需要面向接口；cglib面向普通java类，采用继承方式
 * 2.jdk动态代理需要让用户先创建完实现类对象，之后进行增强；cglib则是只需要类型，帮助用户创建对象，创建对象之前就已经进行增强，也就是用户拿到的对象就是cglib已经继承的代理实例
 */
public class ProxyUtil {
    /**
     * jdk动态代理
     * 需要提前创建对象，对象需要实现接口，需要知道被拦截的方法
     *
     * @param userService
     * @return
     */
    public static UserService jdkProxy(UserService userService) {
        return (UserService) Proxy.newProxyInstance(userService.getClass().getClassLoader(), userService.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.print("执行方法前");
                Object o = method.invoke(userService, args);
                System.out.print("执行方法后");
                return o;
            }
        });
    }

    /**
     * cglib动态代理
     * 不需要提前创建对象，只需要知道需要被继承的类型，并且知道需要被拦截的方法就行
     *
     * @param userService
     * @return
     */
    public static UserService cglibProxy(Class clazz) {
        //创建增强器
        Enhancer enhancer = new Enhancer();
        //设置需要继承的父类类型，这里必须是实际类的类型，而能是接口的类型，否则cglib在执行方法时会报错误
        enhancer.setSuperclass(clazz);
        //设置回调逻辑，也就是切面逻辑
        enhancer.setCallback(new MethodInterceptor() {
            /**
             *
             * @param o：当前调用方法的对象引用
             * @param method 当前调用的实际方法
             * @param objects 调用当前调用方法参数
             * @param methodProxy 代理对象的方法
             * @return
             * @throws Throwable
             */
            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                System.out.print("执行方法前");
                return methodProxy.invokeSuper(o, objects);
            }
        });
        return (UserService) enhancer.create();
    }
}
