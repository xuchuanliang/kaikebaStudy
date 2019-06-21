package capter03.simpleMybatis;

import java.sql.SQLException;

/**
 * 使用JDK动态代理简单的mybatis原理实现
 */
public interface SqlSession {
    boolean save(String sql) throws SQLException;
}
