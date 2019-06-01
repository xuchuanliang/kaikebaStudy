package mybatis;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.UUID;

/**
 * 使用mybatis插入数据，且跟踪源码
 */
public class MainTest {
    /**
     * 总结：mybatis执行总流程如下：
     * 1.Mybatis核心配置文件：配置数据源，事务管理方式，指定SQL映射文件位置
     * 2.SqlSessionFactory（会话工厂）：根据核心配置文件生成一个工厂对象，作用创建SqlSession
     * 3.SqlSession（接口）：提供开发人员一个接口作用：操作数据库（增删改查）
     * 4.Executor：是一个在SqlSession内部使用的接口，负责对数据库进行具体的操作
     * 5.mapped statement：（底层封装工具类），负责生成具体的SQL命令以及对查询结果进行二次封装
     * @param args
     */
    public static void main(String[] args){
        try {
            Dept dept = new Dept();
            dept.setCreated(new Date());
            dept.setId(UUID.randomUUID().toString());
            dept.setName("国际事业部");
            InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
            /**
             * 创建SqlSessionFactory
             * 1.根据mybatis配置文件流创建org.apache.ibatis.builder.xml.XMLConfigBuilder  xml解析器
             * 2.调用解析器的org.apache.ibatis.builder.xml.XMLConfigBuilder#parse()方法，解析配置文件，
             * 构造org.apache.ibatis.session.Configuration 配置对象，在xml解析器的org.apache.ibatis.builder.xml.XMLConfigBuilder#mapperElement(org.apache.ibatis.parsing.XNode)方法中
             * 根据mybatis-config.xml文件中<mappers><mappers/>标签下的不同标签，采取不同的加载xxxMapper.xml的方式，并解析xxxMapper.xml文件，
             * 3.调用org.apache.ibatis.session.SqlSessionFactoryBuilder#build(org.apache.ibatis.session.Configuration)方法，
             * 构造一个默认的sqlSessionFactory：org.apache.ibatis.session.defaults.DefaultSqlSessionFactory，将前面解析出的Configuration对象，存入DefaultSqlSessionFactory中名为configuration域中
             */
            SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            /**
             * 通过DefaultSqlSessionFactory创建SqlSession：
             * 1.创建org.apache.ibatis.transaction.Transaction
             * 2.通过Configuration对象创建执行器：默认是org.apache.ibatis.executor.SimpleExecutor
             * 3.创建默认的SqlSession：org.apache.ibatis.session.defaults.DefaultSqlSession，同时设置DefaultSqlSession中的配置configuration域，执行器executor域，设置dirty域默认为false，用于控制事务提交，设置autoCommit域
             */
            SqlSession sqlSession = sessionFactory.openSession();
            /**
             * 执行增删改查操作
             * 1.设置dirty为true
             * 2.根据传入的statement，从configurate对象的mappedStatements域中获取id对应的org.apache.ibatis.mapping.MappedStatement对象
             * 3.对参数parameter进行转换，如果参数是集合，则转成map对象，否则不做处理
             * 4.调用执行器executor的执行方法，最终调用到org.apache.ibatis.executor.SimpleExecutor#doUpdate(org.apache.ibatis.mapping.MappedStatement, java.lang.Object)的执行方法
             * 5.在执行器的doUpdate方法中，使用configuration创建一个org.apache.ibatis.executor.statement.StatementHandler对象：org.apache.ibatis.executor.statement.PreparedStatementHandler
             * 6.调用StatementHandler的执行方法：org.apache.ibatis.executor.statement.PreparedStatementHandler#update(java.sql.Statement)，在这个方法使用原生的java.sql的statement进行具体的执行操作
             */
            sqlSession.insert("insertDept",dept);
            /**
             * 进行事务提交
             */
            sqlSession.commit();
            sqlSession.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
