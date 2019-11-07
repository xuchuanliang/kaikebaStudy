package capter02.mybatis;

import capter02.mybatis.Mapper.DeptMapper;
import capter02.mybatis.bean.Dept;
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
import java.util.List;
import java.util.UUID;

public class MainTest2 {

    private SqlSession sqlSession;

    @Before
    public void init() throws IOException {
        InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        sqlSession = sqlSessionFactory.openSession();
    }

    /**
     * 阅读源码之mybatis时如何通过核心配置文件加载具体mapper的
     * 1.通过SqlSessionFactoryBuilder()的build方法，是用核心配置文件的输入流，构造org.apache.ibatis.builder.xml.XMLConfigBuilder，XML解析器
     * 创建了XMLConfigBuilder、XPathParser、Configuration
     * <p>
     * <p>
     * 2.通过XMLConfigBuilder 的parse方法（org.apache.ibatis.builder.xml.XMLConfigBuilder#parse()）解析核心配置文件的标签和内容，
     * 在核心配置文件xml的解析方法中（org.apache.ibatis.builder.xml.XMLConfigBuilder#parseConfiguration(org.apache.ibatis.parsing.XNode)），
     * 通过解析不同的标签，来不断封装和完善核心配置对象：org.apache.ibatis.session.Configuration
     * <p>
     * 3.在（org.apache.ibatis.builder.xml.XMLConfigBuilder#mapperElement(org.apache.ibatis.parsing.XNode)）方法中，根据<mappers><mappers/>中配置的
     * 是包还是mapper.xml文件，选择不同的加载方式，以配置包为例：进入到核心配置类的addMappers()方法中，该方法的主要目的是将配置的mapper解析并加载到核心配置
     * 对象中，在addMappers()方法中，是用了Mapper注册器MapperRegistry的（org.apache.ibatis.binding.MapperRegistry#addMappers(java.lang.String)）方法，
     * 4.在Mapper注册器的注册Mapper方法中，是用ResolverUtil根据我们在核心配置文件中配置的<mappers><mappers/>中，获取包的路径，并解析该路径下面的所有以
     * .class结尾的文件，
     * <p>
     * 5.将读取到的class文件，通过mapper注册器的方法（org.apache.ibatis.binding.MapperRegistry#addMapper(java.lang.Class)），以Class对象和解析的接口类型的代理工厂对象
     * 组成Key-Value存入Mapper注册器的knownMappers域中
     * <p>
     * 6.创建org.apache.ibatis.builder.annotation.MapperAnnotationBuilder对象，使用该对象的parse()方法，解析mapper.xml中的具体sql
     * <p>
     * 7.在MapperAnnotationBuilder对象的parse()方法中，使用该对象的loadXmlResource()方法，加载具体mapper.xml中的增删改查方法，具体如下：首先在loadXmlResource()方法中，获取接口的
     * 全路径类名，拼接上.xml字符串，作为要解析的mapper的文件地址，并且作为构成输入流加载进内存
     * <p>
     * 8.使用读取到的mapper.xml输入流构成对象org.apache.ibatis.builder.xml.XMLMapperBuilder，用于解析mapper.xml文件
     * <p>
     * 9.调用XMLMapperBuilder对象的parse()方法，在parse()方法中调用configurationElement()方法，在configurationElement()方法中解析具体mapper.xml文件中各个标签和属性，
     * 此处以解析我们的<insert><insert/>为例继续记录；
     * <p>
     * 10，在configurationElement()方法中调用buildStatementFromContext()方法，解析我们的mapper.xml中的增删改查语句，
     * <p>
     * 11.在buildStatementFromContext()方法中，构造XMLStatementBuilder对象，用于解析statement；调用org.apache.ibatis.builder.xml.XMLStatementBuilder#parseStatementNode()方法来解析具体
     * 的statement，此处我们以解析具体sql为目的进行继续跟进：在该方法中构造了org.apache.ibatis.scripting.LanguageDriver对象，使用该对象的createSqlSource()方法
     * <p>
     * 12.在org.apache.ibatis.scripting.xmltags.XMLLanguageDriver#createSqlSource(org.apache.ibatis.session.Configuration, org.apache.ibatis.parsing.XNode, java.lang.Class)构造了XMLScriptBuilder对象，
     * 使用该对象parseScriptNode()方法，在该方法中，针对静态sql构造RawSqlSource对象，在RawSqlSource对象的构造函数中使用了org.apache.ibatis.scripting.defaults.RawSqlSource#getSql(org.apache.ibatis.session.Configuration, org.apache.ibatis.scripting.xmltags.SqlNode)
     * 方法，将我们在mapper.xml中编写的sql进行解析，解析出我们的sql：insert into dept values (#{id},#{name},#{created})；在构造RawSqlSource中，同时构造了SqlSourceBuilder对象，使用该对象的parse()方法，将
     * 我们的sql字符串处理成insert into dept values (?,?,?)格式，到这一步，我们了解到最终解析出一个非常重要的对象：SqlSource，这个对象中包含了我们可以直接执行的sql语句，参数名称等信息
     */
    @Test
    public void testInsert() {
        Dept dept = new Dept();
        dept.setCreated(new Date());
        dept.setId(UUID.randomUUID().toString());
        dept.setName("我是大大哥哥");
        dept.setFlag(true);
        DeptMapper mapper = sqlSession.getMapper(DeptMapper.class);
        mapper.insertDept(dept);
        sqlSession.commit();
    }

    @Test
    public void testFindAll() {
        DeptMapper mapper = sqlSession.getMapper(DeptMapper.class);
        List<Dept> deptList = mapper.findAll();
        System.out.println(deptList);
    }

    @After
    public void after() {
        if (null != sqlSession) {
            sqlSession.close();
        }
        System.out.println("after");
    }
}
