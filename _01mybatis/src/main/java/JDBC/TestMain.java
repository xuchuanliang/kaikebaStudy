package JDBC;

import java.sql.*;
import java.util.UUID;

/**
 * 使用jdbc方式操作数据
 */
public class TestMain {
    public static void main(String[] args) {
        try {
            /**
             * 加载mysql驱动类
             */
            Class.forName("com.mysql.jdbc.Driver");

            /**
             * 创建连接
             */
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/kaikeba?useUnicode=true&characterEncoding=utf8", "root", "123456");
            /**
             * 设置手动提交事务
             */
            connection.setAutoCommit(false);
            /**
             * 创建statement 准备sql语句和参数
             */
            PreparedStatement preparedStatement = connection.prepareStatement("insert into dept values (?,?,?)");
            preparedStatement.setString(1, UUID.randomUUID().toString());
            preparedStatement.setString(2,"金融事业部");
            preparedStatement.setDate(3,new Date(System.currentTimeMillis()));
            /**
             * 执行新增语句
             */
            preparedStatement.executeUpdate();
            /**
             * 提交事务
             */
            connection.commit();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
