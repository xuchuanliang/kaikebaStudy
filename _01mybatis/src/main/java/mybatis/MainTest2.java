package mybatis;

import mybatis.Mapper.DeptMapper;
import mybatis.bean.Dept;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.UUID;

public class MainTest2 {

    private SqlSession sqlSession;

    @Before
    public void init() throws IOException {
        InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        sqlSession = sqlSessionFactory.openSession();
        System.out.println("befort");
    }

    @Test
    public void testInsert(){
        Dept dept = new Dept();
        dept.setCreated(new Date());
        dept.setId(UUID.randomUUID().toString());
        dept.setName("我是大哥哥");
        DeptMapper mapper = sqlSession.getMapper(DeptMapper.class);
        mapper.insertDept(dept);
    }

    @After
    public void after(){
        if(null!=sqlSession){
            sqlSession.close();
        }
        System.out.println("after");
    }
}
