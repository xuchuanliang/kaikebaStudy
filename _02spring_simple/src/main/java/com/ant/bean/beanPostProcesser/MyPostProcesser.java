package com.ant.bean.beanPostProcesser;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 使用BeanPostProcessor对用户从spring容器获取到的bean进行偷梁换柱，换成具有次要业务逻辑的代理类
 */
public class MyPostProcesser implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("before.."+beanName);
        System.out.println("before.."+bean);
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("after.."+beanName);
        System.out.println("after.."+bean);
        System.out.println(bean instanceof SomeServiceImpl);
        if(bean instanceof SomeServiceImpl){
            //如果是SomeServiceImpl，则创建将结果转成大写的代理对象
            Object proxy =  Proxy.newProxyInstance(bean.getClass().getClassLoader(), bean.getClass().getInterfaces(), new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    if(method.getName().equals("some")){
                        //将some返回的字符串转成大写
                        String result = (String) method.invoke(bean,args);
                        return result.toUpperCase();
                    }
                    return method.invoke(bean,args);
                }
            });
            System.out.println("proxy "+proxy);
            return proxy;
        }
        return bean;
    }
}
