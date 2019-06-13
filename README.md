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