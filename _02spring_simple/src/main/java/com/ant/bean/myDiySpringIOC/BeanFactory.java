package com.ant.bean.myDiySpringIOC;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * 自定义bean工厂
 */
@Data
public class BeanFactory {
    /**
     * bean定义列表
     */
    private List<BeanDefined> beanDefinedList;
    /**
     * spring容器，beanId--对象  键值对
     */
    private Map<String, Object> springIoc = new HashMap<>();

    private List<BeanPostProcessor> beanPostProcessors = new ArrayList<>();

    /**
     * 构造对象工厂时
     * 1.创建所有对象
     * 2.创建并保存后置处理器
     *
     * @param beanDefinedList
     */
    public BeanFactory(List<BeanDefined> beanDefinedList) {
        this.beanDefinedList = beanDefinedList;
        //初始化单例对象
        beanDefinedList.stream().filter(b -> "singleton".equals(b.getScope()) || StringUtils.isEmpty(b.getScope())).forEach(b -> {
            try {
                //初始化对象
                Object obj = createNewBean(b);
                springIoc.put(b.getBeanId(), obj);
                //初始化对象中所有的后置处理器
                if (isProcesser(obj)) {
                    beanPostProcessors.add((BeanPostProcessor) obj);
                }
            } catch (InstantiationException e) {
            } catch (IllegalAccessException e) {
            } catch (ClassNotFoundException e) {
            } catch (NoSuchMethodException e) {
            } catch (InvocationTargetException e) {
            } catch (NoSuchFieldException e) {
            }
        });
    }

    /**
     * 获取对象
     *
     * @param beanId
     * @return
     */
    public Object getBean(String beanId) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException, NoSuchFieldException {
        Object obj = null;
        if (StringUtils.isNoneBlank(beanId)) {
            for (BeanDefined beanDefined : beanDefinedList) {
                if (beanDefined.getBeanId().equals(beanId)) {
                    obj = createNewBean(beanDefined);
                    //执行bean后工厂且返回对象
                    obj = this.invokAllPostProcesserBefore(beanId, obj);
                    obj = this.invokAllPostProcesserAfter(beanId, obj);
                }
            }
        }
        return obj;
    }

    /**
     * 创建或获取bean的对象，考虑作用域、对象工厂
     *
     * @param beanDefined
     * @return
     */
    private Object createNewBean(BeanDefined beanDefined) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException, NoSuchFieldException {
        Object object = null;
        if ("singleton".equals(beanDefined.getScope()) || StringUtils.isEmpty(beanDefined.getScope())) {
            object = springIoc.get(beanDefined.getBeanId());
            if (null == object) {
                object = createNewObjectAnyWay(beanDefined);
                springIoc.put(beanDefined.getBeanId(), object);
            }
        } else if ("prototype".equals(beanDefined.getScope())) {
            object = createNewObjectAnyWay(beanDefined);
        }
        return object;
    }

    /**
     * 始终创建一个新的对象，考虑对象工厂创建
     *
     * @param beanDefined
     * @return
     */
    private Object createNewObjectAnyWay(BeanDefined beanDefined) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException, NoSuchFieldException {
        Object object;
        if (StringUtils.isEmpty(beanDefined.getFactoryMethod()) || StringUtils.isEmpty(beanDefined.getBeanFactory())) {
            //反射创建
            object = Class.forName(beanDefined.getClasPath()).newInstance();
        } else {
            //bean工厂创建
            object = createNewBeanByFactory(beanDefined);
        }
        //对象创建完成后，均做一次依赖注入
        if (!beanDefined.getPropertyMap().isEmpty()) {
            putSimpleProperties(object, beanDefined.getPropertyMap());
        }
        return object;
    }

    /**
     * 判断是否是后置处理器
     *
     * @param object
     * @return
     */
    private boolean isProcesser(Object object) {
        Class<?>[] interfaces = object.getClass().getInterfaces();
        if (null != interfaces && interfaces.length > 0) {
            for (Class<?> clazz : interfaces) {
                if (clazz == BeanPostProcessor.class) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 获取对象前调用后置处理器中的前置方法
     *
     * @param beanId
     * @param bean
     */
    private Object invokAllPostProcesserBefore(String beanId, Object bean) {
        if (beanPostProcessors.size() > 0) {
            for (BeanPostProcessor beanPostProcessor : beanPostProcessors) {
                bean = beanPostProcessor.postProcessBeforeInitialization(bean, beanId);
            }
        }
        return bean;
    }

    /**
     * 获取对象前调用后置处理器中的后置方法
     *
     * @param beanId
     * @param bean
     */
    private Object invokAllPostProcesserAfter(String beanId, Object bean) {
        if (beanPostProcessors.size() > 0) {
            for (BeanPostProcessor beanPostProcessor : beanPostProcessors) {
                bean = beanPostProcessor.postProcessAfterInitialization(bean, beanId);
            }
        }
        return bean;
    }

    /**
     * bean工厂创建对象
     *
     * @param beanDefined
     * @return
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     */
    private Object createNewBeanByFactory(BeanDefined beanDefined) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        //bean工厂创建
        Class clazz = Class.forName(beanDefined.getBeanFactory());
        Object factory = clazz.newInstance();
        Method method = clazz.getDeclaredMethod(beanDefined.getFactoryMethod());
        method.setAccessible(true);
        return method.invoke(factory);
    }

    /**
     * 简单的模仿spring的DI，依赖注入
     *
     * @param object
     * @param propertyMap
     */
    private void putSimpleProperties(Object object, Map<String, String> propertyMap) throws NoSuchFieldException, InvocationTargetException, IllegalAccessException {
        if (propertyMap.isEmpty()) return;
        Class<?> clazz = object.getClass();
        Method[] methods = clazz.getDeclaredMethods();
        for (String key : propertyMap.keySet()) {
            Field field = clazz.getDeclaredField(key);
            if (null != field) {
                for (Method m : methods) {
                    if (m.getName().equalsIgnoreCase("set" + upperCase(key))) {
                        //调用set方法赋值
                        m.setAccessible(true);
                        String value = propertyMap.get(key);
                        if (field.getType() == String.class) {
                            m.invoke(object, value);
                        } else if (field.getType() == List.class) {
                            m.invoke(object, Arrays.asList(value.split(",")));
                        } else if (field.getType().isArray()) {
                            //todo 此处反射数组赋值未找到好的方式
                            String[] ss = value.split(",");
                            if(field.getType().getComponentType()==String.class){
                                m.invoke(object,new String[][]{ss});
                            }
                        } else if (field.getType() == Boolean.class) {
                            m.invoke(object, Boolean.valueOf(value));
                        } else if (field.getType() == Integer.class) {
                            m.invoke(object, Integer.valueOf(value));
                        } else {
                            m.invoke(object, value);
                        }
                    }
                }
            }
        }
    }

    /**
     * 首字符转大写
     *
     * @param str
     * @return
     */
    private static String upperCase(String str) {
        char[] ch = str.toCharArray();
        if (ch[0] >= 'a' && ch[0] <= 'z') {
            ch[0] = (char) (ch[0] - 32);
        }
        return new String(ch);
    }

}
