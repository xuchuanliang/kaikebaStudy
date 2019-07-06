package com.ant.bean.myDiySpringIOC;

import com.ant.bean.Teacher;
import lombok.Data;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * 自定义bean工厂
 */
@Data
public class BeanFactory {
    private List<BeanDefined> beanDefinedList;

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
                    obj = Class.forName(beanDefined.getClasPath()).newInstance();
                }
            }
        }
        return obj;
    }
}
