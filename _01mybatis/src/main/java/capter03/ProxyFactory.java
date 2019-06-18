package capter03;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * 代理工厂
 */
public class ProxyFactory {
    /**
     * JDK动态代理模式下，代理对象的数据类型
     * 应该由监控行为来描述
     * 参数：Class<T>：需要被监控的类
     * @param t
     * @param <T>
     * @return
     */
    public static BaseMethods createProxy(Class<? extends BaseMethods> clazz) throws IllegalAccessException, InstantiationException {
        //1.创建被监控实例对象
        BaseMethods t = clazz.newInstance();
        //2.创建一个通知对象
        InvocationHandler invocationHandler = new MyInvocation(t);
        //3.创建代理对象
        /**
         * 参数说明
         * classloader:被监控对象隶属的类文件在内存中的真实地址
         * interfaces:被监控对象隶属的类文件在内存中的真实地址
         * invocationHandler：监控对象发现被监控的行为被调用时，应该通知哪个通知对象进行辅助操作
         */
        BaseMethods $proxy = (BaseMethods) Proxy.newProxyInstance(t.getClass().getClassLoader(),t.getClass().getInterfaces(),invocationHandler);
        return $proxy;
    }
}
