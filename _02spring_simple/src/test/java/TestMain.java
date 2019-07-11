import com.ant.bean.DI.DIBean;
import com.ant.bean.Student;
import com.ant.bean.Teacher;
import com.ant.bean.beanPostProcesser.MyPostProcesser;
import com.ant.bean.beanPostProcesser.SomeService;
import com.ant.bean.myDiySpringIOC.BeanDefined;
import com.ant.bean.myDiySpringIOC.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestMain {

    public static void main(String[] args) throws Exception {
//        testMySpring();
//        testScope();
//        getBeanByFactory();
//        getBeanDIYFactory();
//        testPostProcesser();
//        testMyPostProcesser();
//        testDI();
        testMyDI();
    }

    public static void testSpring(){
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring_config.xml");
        Teacher teacher = applicationContext.getBean(Teacher.class);
        Student student = applicationContext.getBean(Student.class);
        System.out.println(teacher);
        System.out.println(student);
    }

    public static void testMySpring() throws Exception {
        BeanDefined beanDefined = new BeanDefined();
        beanDefined.setBeanId("teacher");
        beanDefined.setClasPath("com.ant.bean.Teacher");
        List<BeanDefined> beanDefinedList = new ArrayList<BeanDefined>();
        beanDefinedList.add(beanDefined);
        BeanFactory beanFactory = new BeanFactory(beanDefinedList);
        beanFactory.setBeanDefinedList(beanDefinedList);
        Teacher teacher = (Teacher)beanFactory.getBean("teacher");
        System.out.println(teacher);
    }

    public static void testScope() throws Exception {
        BeanDefined beanDefined = new BeanDefined();
        beanDefined.setBeanId("teacher");
        beanDefined.setClasPath("com.ant.bean.Teacher");
//        beanDefined.setScope("prototype");
//        beanDefined.setScope("prototype");
        List<BeanDefined> beanDefinedList = new ArrayList<BeanDefined>();
        beanDefinedList.add(beanDefined);
        BeanFactory beanFactory = new BeanFactory(beanDefinedList);
        beanFactory.setBeanDefinedList(beanDefinedList);
        Teacher teacher = (Teacher)beanFactory.getBean("teacher");
        System.out.println(teacher);
        Teacher teacher2 = (Teacher)beanFactory.getBean("teacher");
        System.out.println(teacher2);
    }

    public static void getBeanByFactory(){
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring_config.xml");
        Teacher teacher = applicationContext.getBean(Teacher.class);
        System.out.println(teacher);
    }

    private static void getBeanDIYFactory() throws Exception {
        BeanDefined beanDefined = new BeanDefined();
        beanDefined.setBeanId("teacher");
        beanDefined.setClasPath("com.ant.bean.Teacher");
        beanDefined.setFactoryMethod("createTeacher");
        beanDefined.setBeanFactory("com.ant.bean.TeacheFactor");
//        beanDefined.setScope("prototype");
        List<BeanDefined> beanDefinedList = new ArrayList<BeanDefined>();
        beanDefinedList.add(beanDefined);
        BeanFactory beanFactory = new BeanFactory(beanDefinedList);
        beanFactory.setBeanDefinedList(beanDefinedList);
        Teacher teacher = (Teacher)beanFactory.getBean("teacher");
        System.out.println(teacher);
        Teacher teacher2 = (Teacher)beanFactory.getBean("teacher");
        System.out.println(teacher2);
    }

    /**
     * 调用getBean时先调用对象的构造方法、其次调用postProcessBeforeInitialization(),postProcessAfterInitialization(),
     */
    private static void testPostProcesser(){
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring_config.xml");
        SomeService someService = applicationContext.getBean(SomeService.class);
        System.out.println(someService.some());
    }

    /**
     * 自定义bean后置工程：BeanPostProcesser
     * @throws ClassNotFoundException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    private static void testMyPostProcesser() throws Exception {
        BeanDefined beanDefined = new BeanDefined();
        beanDefined.setClasPath("com.ant.bean.beanPostProcesser.SomeServiceImpl");
        beanDefined.setBeanId("someService");
        BeanDefined postProcesser = new BeanDefined();
        postProcesser.setBeanId("myPostProcesser");
        postProcesser.setClasPath("com.ant.bean.beanPostProcesser.MyPostProcesser");
        List<BeanDefined> beanDefineds = new ArrayList<>();
        beanDefineds.add(beanDefined);
        beanDefineds.add(postProcesser);
        BeanFactory beanFactory = new BeanFactory(beanDefineds);
        SomeService someService = (SomeService) beanFactory.getBean("someService");
        System.out.println(someService.some());
    }

    /**
     * 使用spring依赖注入
     */
    private static void testDI(){
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring_config.xml");
        DIBean bean = applicationContext.getBean(DIBean.class);
        System.out.println(bean);
    }

    private static void testMyDI() throws IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchFieldException, NoSuchMethodException, ClassNotFoundException {
        BeanDefined beanDefined = new BeanDefined();
        beanDefined.setClasPath("com.ant.bean.DI.DIBean");
        beanDefined.setBeanId("dIBean");
        Map<String,String> map = new HashMap<>();
        map.put("teacherName","xuchuanliang");
        map.put("friendArray","qg,xjjj,zzz");
        map.put("school","安徽财经大学，安徽大学，清华，北大");
        beanDefined.setPropertyMap(map);
        List<BeanDefined> beanDefineds = new ArrayList<>();
        beanDefineds.add(beanDefined);
        BeanFactory beanFactory = new BeanFactory(beanDefineds);
        Object dIBean = beanFactory.getBean("dIBean");
        System.out.println(dIBean);
    }
}
