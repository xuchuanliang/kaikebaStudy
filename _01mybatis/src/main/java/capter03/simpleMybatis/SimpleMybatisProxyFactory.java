package capter03.simpleMybatis;

import java.lang.reflect.Proxy;

public class SimpleMybatisProxyFactory<T extends SqlSession> {

    /**
     * 创建自定义SqlSession代理类的自定义工厂
     * @param clazz
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public static SqlSession crateProxy(Class<? extends SqlSession> clazz) throws IllegalAccessException, InstantiationException {
        return (SqlSession) Proxy.newProxyInstance(clazz.getClassLoader(),new Class[]{SqlSession.class},new SimpleMybatisInvocation(clazz.newInstance()));
    }
}
