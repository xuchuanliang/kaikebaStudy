package capter03;

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 这是jdk动态代理的通知类
 *通知类需要持有具体被代理类的一个引用，用于在代理完成后执行对应的具体方法
 */
public class MyInvocation implements InvocationHandler {

    private BaseMethods baseMethods;

    public MyInvocation(BaseMethods baseMethods){
        this.baseMethods = baseMethods;
    }

    /**
     * 在被监控行为将要执行时，会被JVM拦截，
     * 被监控行为和行为实现放会被当做参数输送invoke
     * 通知JVM，这个被拦截方法是如何与当前次要业务方法绑定实现
     *
     * @param proxy 代理对象
     * @param method 监控到执行的方法
     * @param args 执行方法的参数
     * @return
     * @throws Throwable
     */
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object o = null;
        //1.确认当前的被拦截行为
        String name = method.getName();
        //2.根据被拦截行为的不同，决定主要业务和次要业务如何绑定执行
        if("eat".equals(name)){
            wash();
            o = method.invoke(baseMethods,args);
        }else if ("wc".equals(name)){
            o = method.invoke(baseMethods,args);
            wash();
        }
        return o;
    }

    private void wash(){
        System.out.println("---洗手----");
    }

}
