package capter03.simpleMybatis;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 仿照mybatis的代理类
 */
public class SimpleMybatisInvocation implements InvocationHandler {

    Connection connection;
    PreparedStatement preparedStatement;
    private SqlSession sqlSession;
    public SimpleMybatisInvocation(SqlSession sqlSession){
        this.sqlSession = sqlSession;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("invoke");
        //初始化
        this.init();
        //给被代理类赋值
        Field field = sqlSession.getClass().getDeclaredField("preparedStatement");
        field.setAccessible(true);
        field.set(sqlSession,preparedStatement);
        //执行方法
        Object value = method.invoke(sqlSession,args);
        //关闭资源
        this.close();
        return value;
    }

    private void init() throws SQLException, ClassNotFoundException {
        System.out.println("init");
        Class.forName("com.mysql.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/kaikeba?characterEncoding=utf8","root","123456");
        preparedStatement = connection.prepareStatement("");
    }

    private void close() throws SQLException {
        System.out.println("close");
        if(null!=preparedStatement){
            preparedStatement.close();
        }
        if(null!=connection){
            connection.close();
        }
    }
}
