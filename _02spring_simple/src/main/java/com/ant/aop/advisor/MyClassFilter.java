package com.ant.aop.advisor;

import com.ant.aop.Person;
import org.springframework.aop.ClassFilter;

/**
 * 只对Person类的eat方法进行拦截，饭前要洗手
 */
public class MyClassFilter implements ClassFilter {
    @Override
    public boolean matches(Class<?> clazz) {
        return Person.class == clazz;
    }
}
