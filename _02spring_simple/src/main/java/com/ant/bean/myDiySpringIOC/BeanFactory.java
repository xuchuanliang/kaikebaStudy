package com.ant.bean.myDiySpringIOC;

import com.ant.bean.Teacher;
import lombok.Data;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 自定义bean工厂
 */
@Data
public class BeanFactory {
    private List<BeanDefined> beanDefinedList;
    private Map<String,Object> springIoc;

    public BeanFactory(List<BeanDefined> beanDefinedList){
        this.beanDefinedList = beanDefinedList;
        springIoc = new HashMap<>();
        //初始化单例对象
        beanDefinedList.stream().filter(b->"singleton".equals(b.getScope()) || StringUtils.isEmpty(b.getScope())).forEach(b->{
            try {
                springIoc.put(b.getBeanId(),Class.forName(b.getClasPath()).newInstance());
            } catch (InstantiationException e) {
            } catch (IllegalAccessException e) {
            } catch (ClassNotFoundException e) {
            }
        });
    }

    /**
     * 获取对象
     * @param beanId
     * @return
     */
    public Object getBean(String beanId) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Object obj = null;
        if(StringUtils.isNoneBlank(beanId)){
            for(BeanDefined beanDefined:beanDefinedList){
                if(beanDefined.getBeanId().equals(beanId)){
                    if("prototype".equals(beanDefined.getScope())){
                        obj = Class.forName(beanDefined.getClasPath()).newInstance();
                    }else{
                        obj = springIoc.get(beanId);
                    }
                }
            }
        }
        return obj;
    }
}
