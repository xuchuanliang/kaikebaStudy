package com.ant.bean.myDiySpringIOC;

import com.ant.bean.Teacher;
import lombok.Data;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private Map<String,Object> springIoc = new HashMap<>();

    public BeanFactory(List<BeanDefined> beanDefinedList){
        this.beanDefinedList = beanDefinedList;
        //初始化单例对象
        beanDefinedList.stream().filter(b->"singleton".equals(b.getScope()) || StringUtils.isEmpty(b.getScope())).forEach(b->{
            try {
                createNewBean(b);
            } catch (InstantiationException e) {
            } catch (IllegalAccessException e) {
            } catch (ClassNotFoundException e) {
            } catch (NoSuchMethodException e) {
            } catch (InvocationTargetException e) {
            }
        });
    }

    /**
     * 获取对象
     * @param beanId
     * @return
     */
    public Object getBean(String beanId) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        Object obj = null;
        if(StringUtils.isNoneBlank(beanId)){
            for(BeanDefined beanDefined:beanDefinedList){
                if(beanDefined.getBeanId().equals(beanId)){
                    obj = createNewBean(beanDefined);
                }
            }
        }
        return obj;
    }

    /**
     * 创建或获取bean的对象，考虑作用域、对象工厂
     * @param beanDefined
     * @return
     */
    private Object createNewBean(BeanDefined beanDefined) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        Object object = null;
        if("singleton".equals(beanDefined.getScope()) || StringUtils.isEmpty(beanDefined.getScope())){
            object = springIoc.get(beanDefined.getBeanId());
            if(null==object){
                object = createNewObjectAnyWay(beanDefined);
                springIoc.put(beanDefined.getBeanId(),object);
            }
        }else if("prototype".equals(beanDefined.getScope())){
            object = createNewObjectAnyWay(beanDefined);
        }
        return object;
    }

    /**
     * 始终创建一个新的对象，考虑对象工厂创建
     * @param beanDefined
     * @return
     */
    private Object createNewObjectAnyWay(BeanDefined beanDefined) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        Object object;
        if(StringUtils.isEmpty(beanDefined.getFactoryMethod()) || StringUtils.isEmpty(beanDefined.getBeanFactory())){
            //反射创建
            object = Class.forName(beanDefined.getClasPath()).newInstance();
        }else{
            //bean工厂创建
            object = createNewBeanByFactory(beanDefined);
        }
        return object;
    }

    /**
     * bean工厂创建对象
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

}
