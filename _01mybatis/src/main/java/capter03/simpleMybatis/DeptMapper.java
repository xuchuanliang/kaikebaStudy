package capter03.simpleMybatis;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeptMapper implements SqlSession {

    private PreparedStatement preparedStatement;

    public boolean save(String sql) throws SQLException {
        return preparedStatement.execute(sql);
    }
}
