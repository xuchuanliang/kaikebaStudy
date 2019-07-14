package com.ant.aop.advisor;

import org.aopalliance.aop.Advice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.PointcutAdvisor;

/**
 * 自定义顾问
 * 只对Person的eat方法进行拦截，饭前洗手
 */
public class MyPointcutAdvisor implements PointcutAdvisor {
    /**
     * 依赖注入
     */

    private Pointcut pointcut;//当前拦截对象和对象调用主要业务方法，person.eat()
    private Advice advice;//次要业务以及次要业务与主要业务执行顺序

    @Override
    public Pointcut getPointcut() {
        return pointcut;
    }

    public void setPointcut(Pointcut pointcut) {
        this.pointcut = pointcut;
    }

    @Override
    public Advice getAdvice() {
        return advice;
    }

    public void setAdvice(Advice advice) {
        this.advice = advice;
    }

    @Override
    public boolean isPerInstance() {
        return false;
    }
}
