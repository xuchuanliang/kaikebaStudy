package mybatis;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * mybatis中的类型转换器，可以将指定类型的javaType转成成特定的jdbcType
 *
 */
public class MyTypeHandler implements TypeHandler {
    /**
     * 在设置sql语句参数时调用的方法
     * 默认是2，true用2标识，falst用3表示
     * @param ps
     * @param i
     * @param parameter
     * @param jdbcType
     * @throws SQLException
     */
    public void setParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType) throws SQLException {
        if(parameter==null){
            ps.setInt(i,2);
            return;
        }
        Boolean flag = (Boolean) parameter;
        if(flag){
            ps.setInt(i,2);
        }else{
            ps.setInt(i,3);
        }
    }

    /**
     * 查询出结果，封装结果时调用的方法
     * @param rs
     * @param columnName
     * @return
     * @throws SQLException
     */
    public Object getResult(ResultSet rs, String columnName) throws SQLException {
        int i = rs.getInt(columnName);
        return i==2;
    }

    public Object getResult(ResultSet rs, int columnIndex) throws SQLException {
        return null;
    }

    public Object getResult(CallableStatement cs, int columnIndex) throws SQLException {
        return null;
    }
}
