package capter02.mybatis;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.util.Properties;

/**
 * 代理模式包含一个代理对象和一个InvocationHandler对象，InvocationHandler是当执行代理对象方法时，可以增加自己的逻辑，该对象持有一个真实的对象引用
 */
@Intercepts({
        @Signature(type = Executor.class, method = "query",
                args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})
})
public class MyInterceptor implements Interceptor {
    /**
     * 参数：invocation 查看源码可知包含代理对象，被监控的对象方法，当前被监控方法运行时需要的参数
     *
     * @param invocation
     * @return
     * @throws Throwable
     */
    public Object intercept(Invocation invocation) throws Throwable {
        System.out.println("被拦截方法执行前");
        Object obj = invocation.proceed();
        System.out.println("被拦截方法执行后");
        return obj;
    }

    /**
     * 参数：target 表示被拦截的对象，也就是上方注解中要拦截的对象，此处是Executor接口的实例对象
     * 作用：如果被拦截对象所在的类有实现接口，就为当前拦截对象生成一个代理对象【$procy】
     * 如果被拦截的对象所在的类没有实现指定接口，这个对象之后行为就不会被代理操作
     * <p>
     * 在mybatis中有一个现成的InvocationHandler实现类，可以帮我们生成代理对象：org.apache.ibatis.plugin.Plugin，这样就不用我们自己去实现InvocationHandler了，此处也说明
     * mybatis使用的是jdk动态代理
     *
     * @param target
     * @return
     */
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    public void setProperties(Properties properties) {

    }
}
