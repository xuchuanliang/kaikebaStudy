package capter03;

import capter03.simpleMybatis.DeptMapper;
import capter03.simpleMybatis.SimpleMybatisInvocation;
import capter03.simpleMybatis.SimpleMybatisProxyFactory;
import capter03.simpleMybatis.SqlSession;
import org.apache.ibatis.annotations.Mapper;

import java.lang.reflect.Proxy;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class Test {

    public static void main(String[] args) throws IllegalAccessException, InstantiationException, SQLException {
//        test2();
        test3();
    }
    public static void test1(){
        BaseMethods proxyInstance = (BaseMethods) Proxy.newProxyInstance(Person.class.getClassLoader(), new Class[]{BaseMethods.class}, new MyInvocation(new Person()));
        proxyInstance.eat();
        proxyInstance.wc();
    }
    public static void test2() throws InstantiationException, IllegalAccessException {
        BaseMethods baseMethods = ProxyFactory.createProxy(Person.class);
        baseMethods.wc();
        baseMethods.eat();
    }

    /**
     * 测试自己编写的简单的mybatis sqlSession
     */
    public static void test3() throws SQLException, InstantiationException, IllegalAccessException {
        SqlSession proxy = SimpleMybatisProxyFactory.crateProxy(DeptMapper.class);
        Map<String,String> mapperMap = new HashMap<>();
        mapperMap.put("capter03.simpleMybatis.DeptMapper.save","INSERT INTO `kaikeba`.`dept` (`id`, `name`, `created`, `flag`) VALUES ('4228683e-30df-45d3-88ee-1e56b0a533d2', '我是大良哥111', '2019-06-13 19:15:50', '3');");
        proxy.save(mapperMap.get("capter03.simpleMybatis.DeptMapper.save"));

    }
}
