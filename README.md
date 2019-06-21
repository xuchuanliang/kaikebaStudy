# 开课吧JavaEE架构师第一期学习记录

## 第一课
- 第一课总览的mybatis的源码，详情参见MainTest1的注释

## 第二课
- 自定义类型转换器：typeHandler
- 自定义对象工厂：DefaultObjectFactory
- 拦截器：org.apache.ibatis.plugin.Interceptor，其中plugin()方法中我们可以决定是否要进行拦截进而决定要返回一个什么样的目标对象；intercept()方法就是要进行拦截的时候要执行的方法。
- org.apache.ibatis.plugin.Plugin
- mybatis自定义拦截器，可以拦截接口只有四种：Executor.class，StatementHandler.class，ParameterHandler.class，ResultSetHandler.class

2019年6月13日 20:16:27 2小时22分钟，拦截器
2019年6月13日 22:56:12 学完第二课

## 第三课
- 代理模式
> 代理模式的作用：将主要业务与次要业务进行松耦合组装
本质：监控行为特征
JDK代理模式实现：
    1.接口角色：定义所有需要被监听的行为
    2.接口实现类
    3.通知类：1).次要业务进行具体实现；2).通知JVM，当前被拦截的主要业务方法与次要业务方法应该如何绑定执行
    4.监控对象（代理对象）：1)。被监控的实例对象；2）.需要被监控的监控行为；3）.具体通知类实例对象

- 设计表的时候尽量不要放null值，尽量放一个默认的解释型的数据

2019年6月18日 22:21:31 第三课50分钟 JDK动态代理
2019年6月19日 07:30:35 第三课1小时04分钟 JDK动态代理模式
2019年6月20日 07:28:28 第三课1小时26分钟 mybatis动态代理的实现
2019年6月21日 22:52:43 第三课结束，mybatis进入尾声
