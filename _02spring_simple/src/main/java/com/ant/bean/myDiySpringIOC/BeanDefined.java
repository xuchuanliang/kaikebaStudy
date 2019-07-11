package com.ant.bean.myDiySpringIOC;

import lombok.Data;

import java.util.Map;

/**
 * 自定义的bean定义对象
 */
@Data
public class BeanDefined {
    private String beanId;
    /**
     * 类路径
     */
    private String clasPath;
    /**
     * 作用域
     */
    private String scope;
    /**
     * 对象创建的自定义工厂类路径
     */
    private String beanFactory;
    /**
     * 对象创建的自定义工厂方法
     */
    private String factoryMethod;
    /**
     * 简单的依赖注入
     */
    private Map<String,String> propertyMap;

}
